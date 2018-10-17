package vn.team.freechat.game;

import lombok.Getter;

@Getter
public class BulletHit {

	private final Bullet bullet;
	private final Hazard hazard;
	
	public BulletHit(Bullet bullet, Hazard hazard) {
		super();
		this.bullet = bullet;
		this.hazard = hazard;
	}
	
	@Override
	public String toString() {
		return new StringBuilder()
				.append("hit between bullet: ").append(bullet)
				.append(" and hazard: " ).append(hazard)
				.toString();
	}
}
