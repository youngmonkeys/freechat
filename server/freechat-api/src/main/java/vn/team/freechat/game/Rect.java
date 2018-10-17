package vn.team.freechat.game;

public class Rect {

	public double x;
	public double z;
	public double width;
	public double height;
	
	public Rect() {
	}
	
	public Rect(double x, double z, double width, double height) {
		this.x = x;
		this.z = z;
		this.width = width;
		this.height = height;
	}
	
	public double getTopZ() {
		double value = z + height;
		return value;
	}
	
	public double getRightX() {
		double value = x + width;
		return value;
	}
	
}
