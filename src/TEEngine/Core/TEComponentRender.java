package TEEngine.Core;

import TEEngine.Render.TERenderTarget;

public abstract class TEComponentRender extends TEComponent {
	public static int mLastTexture;
	public static long mLastPositionHash;
	public static long mLastCropHash;
	public static int mLastProgram;
	public abstract void draw();
	public TERenderTarget mRenderTarget;
	
	public TEComponentRender() {
		TEEngine engine = TEEngine.sharedEngine();
	    setRenderTarget(engine.getScreenTarget());
	}
	
	public void setRenderTarget(TERenderTarget target) {
	    mRenderTarget = target;
	}
}
