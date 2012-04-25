package TEEngine.Util;

public class TEUtilPoint {
	public float x;
	public float y;
	
	public TEUtilPoint(float newX, float newY) {
		x = newX;
		y = newY;
	}
	
	public void update(TEUtilPoint point) {
		x = point.x;
		y = point.y;
	}
	
	public static TEUtilPoint make(float newX, float newY) {
		return new TEUtilPoint(newX, newY);
	}
	
	public static TEUtilPoint zero() {
		return new TEUtilPoint(0, 0);
	}
	
	public boolean isZero() {
		return (x == 0) && (y == 0);
	}
}
