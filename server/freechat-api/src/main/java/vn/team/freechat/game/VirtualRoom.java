package vn.team.freechat.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

import com.tvd12.ezyfox.binding.EzyAccessType;
import com.tvd12.ezyfox.binding.annotation.EzyObjectBinding;
import com.tvd12.ezyfox.binding.annotation.EzyValue;
import com.tvd12.ezyfox.util.EzyDestroyable;
import com.tvd12.ezyfox.util.EzyLoggable;
import com.tvd12.ezyfox.util.EzyResettable;
import com.tvd12.ezyfox.util.EzySafeStartable;
import com.tvd12.ezyfoxserver.support.factory.EzyResponseFactory;

import lombok.Getter;
import lombok.Setter;
import vn.team.freechat.constant.GameCommands;

@Getter
@EzyObjectBinding(
		read = false,
		accessType = EzyAccessType.NONE)
public class VirtualRoom 
		extends EzyLoggable 
		implements VirtualItem, EzyResettable {

	private final String id;
	private final Object lock;
	private volatile boolean valid;
	@Setter
	private volatile boolean stopped;
	private final Ship ship;
	private final Map<Object, Hazard> hazards;
	private final Map<Object, Bullet> bullets;
	private final BlockingQueue<BulletHit> bulletHits;
	private final BulletHitThread bulletHitThread;
	private final HazardRandomThread hazardRandomThread;
	@Setter
	private EzyResponseFactory responseFactory;
	
	public VirtualRoom(String id) {
		this.id = id;
		this.valid = true;
		this.lock = new Object();
		this.ship = new Ship();
		this.bullets = new ConcurrentHashMap<>();
		this.hazards = new ConcurrentHashMap<>();
		this.bulletHits = new LinkedBlockingQueue<>();
		this.bulletHitThread = new BulletHitThread();
		this.hazardRandomThread = new HazardRandomThread();
		this.bulletHitThread.start();
		this.hazardRandomThread.start();
	}
	
	@Override
	public void update() {
		if(stopped)
			return;
		ship.update();
		List<Hazard> hazards = getHazardList();
		List<Bullet> bullets = getBulletList();
		for(Hazard hazard : hazards)
			hazard.update();
		for(Bullet bullet : bullets)
			bullet.update();
		for(Hazard hazard : hazards) {
			for(Bullet bullet : bullets) {
				boolean intersect = CollisionDetector.check(bullet, hazard);
				if(intersect) {
					bullet.destroy();
					hazard.destroy();
					bulletHits.add(new BulletHit(bullet, hazard));
					break;
				}
			}
		}
		for(Hazard hazard : hazards) {
			boolean intersect = CollisionDetector.check(ship, hazard);
			if(intersect) {
				stopped = true;
				logger.info("\n\nship: {} intersected hazard: {}\n\n", ship.getPosition(), hazard.getPosition());
				sendGameOver(hazard);
				break;
			}
		}
	}
	
	private void sendGameOver(Hazard hazard) {
		responseFactory.newObjectResponse()
			.command(GameCommands.GAME_OVER)
			.username(getId())
			.param("hazardId", hazard.getId())
			.execute();
	}
	
	public Bullet addBullet() {
		Bullet bullet = new Bullet(ship.getBulletPos());
		synchronized (bullets) {
			bullets.put(bullet.getId(), bullet);
		}
		return bullet;
	}
	
	private void addHazard(Hazard hazard) {
		synchronized (lock) {
			this.hazards.put(hazard.getId(), hazard);
		}
	}
	
	private void removeBullet(Bullet bullet) {
		logger.info("destroy bullet:" + bullet);
		synchronized (bullets) {
			this.bullets.remove(bullet.getId());
		}
	}
	
	private void removeHazard(Hazard hazard) {
		logger.info("destroy hazard:" + hazard);
		synchronized (lock) {
			this.hazards.remove(hazard.getId());
		}
	}
	
	private void clearBullets() {
		List<Bullet> bullets = getBulletList();
		for(Bullet bullet : bullets) {
			if(!bullet.isValid())
				removeBullet(bullet);
		}
	}
	
	private int clearHarzards() {
		List<Hazard> list = getHazardList0();
		for(Hazard hazard : list) {
			if(!hazard.isValid())
				removeHazard(hazard);
		}
		int count = getHazardCount();
		return count;
	}
	
	private int getHazardCount() {
		int count = hazards.size();
		return count;
	}
	
	@Override
	public void reset() {
		hazards.clear();
		bullets.clear();
		ship.reset();
		stopped = false;
	}
	
	@Override
	public void destroy() {
		this.valid = false;
		this.hazardRandomThread.destroy();
	}
	
	private List<Bullet> getBulletList() {
		List<Bullet> list = new ArrayList<>();
		synchronized (bullets) {
			for(Bullet bullet : bullets.values())
				if(bullet.isValid())
					list.add(bullet);
			return list;
		}
	}
	
	@EzyValue("hazards")
	public List<Hazard> getHazardList() {
		List<Hazard> list = new ArrayList<>();
		synchronized (lock) {
			for(Hazard hazard : hazards.values())
				if(hazard.isValid())
					list.add(hazard);
			return list;
		}
	}
	
	public List<Hazard> getHazardList0() {
		synchronized (lock) {
			List<Hazard> list = new ArrayList<>(hazards.values());
			return list;
		}
	}
	
	public class BulletHitThread 
			extends EzyLoggable
			implements EzySafeStartable, EzyDestroyable {
		
		private Thread thread;
		private volatile boolean active;
		
		@Override
		public void start() {
			thread = new Thread(this::loop);
			thread.start();
		}
		
		private void loop() {
			this.active = true;
			while(active) {
				try {
					BulletHit hit = bulletHits.take();
					sendBulletHit(hit);
					logger.info("\n\n" + hit + "\n\n");
				} catch (InterruptedException e) {
					logger.error("thread has stopped", e);
				}
				
			}
		}
		
		private void sendBulletHit(BulletHit hit) {
			responseFactory.newObjectResponse()
				.command(GameCommands.HIT)
				.param("bulletId", hit.getBullet().getId())
				.param("hazardId", hit.getHazard().getId())
				.usernames(getId())
				.execute();
		}
		
		@Override
		public void destroy() {
			this.active = false;
		}
		
	}
	
	public class HazardRandomThread 
			extends EzyLoggable
			implements EzySafeStartable, EzyDestroyable {
		
		private Thread thread;
		private Random random;
		private final long spawnWait;
		private final int hazardCount;
		private final Vec3 spawnValues;
		private volatile boolean active;
		
		public HazardRandomThread() {
			this.spawnWait = 100;
			this.hazardCount = 1;
			this.random = new Random();
			this.spawnValues = new Vec3(6, 0, 16);
		}
		
		@Override
		public void start() {
			thread = new Thread(this::loop);
			thread.setName("random-hazards");
			active = true;
			thread.start();
		}
		
		private void loop() {
			while(active)
				handle();
		}
		
		private void handle() {
			try {
				if(stopped)
					return;
				long exWait = random.nextInt(500);
				long realWait = spawnWait + exWait;
				Thread.sleep(realWait);
				clearBullets();
				int count = clearHarzards();
				if(count >= hazardCount)
					return;
				Hazard hazard = spawnHazard();
				responseHazard(hazard);
				addHazard(hazard);
			}
			catch(Exception e) {
				logger.error("random hazards stopped", e);
			}
		}
		
		private void responseHazard(Hazard hazard) {
			responseFactory.newObjectResponse()
				.command(GameCommands.HAZARD)
				.username(getId())
				.data(hazard)
				.execute();
		}
		
		private Hazard spawnHazard() {
			Vec3 spawnPosition = new Vec3();
			spawnPosition.x = randomRange(-spawnValues.x, spawnValues.x);
			spawnPosition.y = spawnValues.y;
			spawnPosition.z = spawnValues.z;
			Hazard hazard = new Hazard(spawnPosition, 5);
			return hazard;
		}
		
		public int randomRange(int min, int max) {
		    int x = random.nextInt((max - min) + 1) + min;
		    return x;
		}
		
		public double randomRange(double min, double max) {
			return randomRange((int)min, (int)max);
		}	
		
		@Override
		public void destroy() {
			this.active = false;
		}
	}
}
