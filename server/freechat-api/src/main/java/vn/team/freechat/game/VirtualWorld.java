package vn.team.freechat.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tvd12.ezyfox.util.EzyLoggable;

import vn.team.freechat.constant.GameConstants;

public class VirtualWorld extends EzyLoggable {

	private Thread thread;
	private volatile boolean active;
	private final Object lock;
	private final Map<Object, VirtualItem> items;
	private static final VirtualWorld INSTANCE = new VirtualWorld();
	
	private VirtualWorld() {
		this.lock = new Object();
		this.items = new HashMap<>();
		this.start();
	}
	
	public static VirtualWorld getInstance() {
		return INSTANCE;
	}
	
	public void addItem(VirtualItem item) {
		synchronized (item) {
			this.items.put(item.getId(), item);
		}
	}
	
	public void removeItem(VirtualItem item) {
		synchronized (item) {
			this.items.remove(item.getId());
		}
	}
	
	public VirtualRoom getRoom(String id) {
		synchronized (lock) {
			if(items.containsKey(id))
				return (VirtualRoom) items.get(id);
			VirtualRoom room = new VirtualRoom(id);
			items.put(id, room);
			return room;
		}
	}
	
	public void start() {
		thread = new Thread(this::loop);
		thread.setName("space-game-virtual-world");
		active = true;
		thread.start();
	}
	
	private void loop() {
		try {
			while(active) {
				List<VirtualItem> list = getItemList();
				for(VirtualItem item : list)
					updateItem(item);
				Thread.sleep(GameConstants.WORLD_SLEEP_TIME);
			}
		}
		catch (Exception e) {
			logger.error("loop stopped", e);
		}
	}
	
	private void updateItem(VirtualItem item) {
		try {
			item.update();
		}
		catch(Exception e) {
			logger.error("item: " + item + " update error", e);
		}
	}
	
	private List<VirtualItem> getItemList() {
		synchronized (lock) {
			List<VirtualItem> list = new ArrayList<>(items.values());
			return list;
		}
	}
	
}
