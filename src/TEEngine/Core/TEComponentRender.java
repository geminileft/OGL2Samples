package TEEngine.Core;

public abstract class TEComponentRender extends TEComponent {
	public static int mLastTexture;
	public static long mLastPositionHash;
	public static long mLastCropHash;
	public static int mLastProgram;
	public abstract void draw();
}
