package TEEngine.Util;

public class TEUtilSize {
	public int width;
	public int height;
	
	public TEUtilSize(int newWidth, int newHeight) {
		width = newWidth;
		height = newHeight;
	}
	
	public static TEUtilSize make(int newWidth, int newHeight) {
		return new TEUtilSize(newWidth, newHeight);
	}
	
	public static TEUtilSize zero() {
		return new TEUtilSize(0, 0);
	}
	
	public boolean isZero() {
		return (width == 0) && (height == 0);
	}
}
