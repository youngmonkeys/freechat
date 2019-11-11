package vn.team.freechat.test.game;

public final class CollisionDetector {
	
	private CollisionDetector() {
	}

	public static boolean check(Body a, Body b) {
		Vec3 pa = a.getPosition();
		Vec3 pb = b.getPosition();
		if(pa.z > pb.z)
			return check0(a, b);
		else
			return check0(b, a);
	}
	
	private static boolean check0(Body a, Body b) {
		Vec3 pa = a.getPosition();
		Vec3 pb = b.getPosition();
		double disz = pa.z - pb.z;
		if(disz > b.getHeight())
			return false;
		if(pa.x > pb.x) 
			return check1(a, b);
		else
			return check1(b, a);
	}
	
	private static boolean check1(Body a, Body b) {
		Vec3 pa = a.getPosition();
		Vec3 pb = b.getPosition();
		double disx = pa.x - pb.x;
		if(disx > b.getWidth())
			return false;
		return true;
	}
	
}
