package TEEngine.Core;

import TEEngine.Render.TERenderTarget;
import TEEngine.Render.TERenderTarget.TEShaderType;
import TEEngine.Render.TERenderer;

public abstract class TEComponentRender extends TEComponent {
	private static TERenderer mSharedRenderer;
	private Object mExtra;
	private TEShaderType mExtraType;

	public static int mLastTexture;
	public static long mLastPositionHash;
	public static long mLastCropHash;
	public static int mLastProgram;
	public abstract void draw();
	public TERenderTarget mRenderTarget;
	
	public TEComponentRender() {
	    setRenderTarget(getSharedRenderer().getScreenTarget());
	}
	
	public void setRenderTarget(TERenderTarget target) {
	    mRenderTarget = target;
	}

	public TERenderTarget getRenderTarget() {
	    return mRenderTarget;
	}


	public TERenderer getSharedRenderer() {
	    return mSharedRenderer;
	}

	public static void setSharedRenderer(TERenderer renderer) {
	    mSharedRenderer = renderer;
	}

	public Object getExtraData()  {
	    return mExtra;
	}

	public TEShaderType getExtraType() {
	    return mExtraType;
	}

}
