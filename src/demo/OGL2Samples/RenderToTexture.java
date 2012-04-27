package demo.OGL2Samples;

import java.nio.FloatBuffer;

import TEEngine.Core.TEComponentRender;
import TEEngine.Manager.TEManagerTexture;
import TEEngine.Render.TERenderPrimative;
import TEEngine.Render.TERenderTarget;
import TEEngine.Util.TEUtilSize;
import android.opengl.GLES20;

class RenderToTexture extends TEComponentRender {

	private TERenderTarget mTarget;
	int mTextureHandle;
	int mSize;
	FloatBuffer mVertexBuffer;
	FloatBuffer mTextureBuffer;
	TERenderPrimative mRenderPrimative = new TERenderPrimative();

	public RenderToTexture(int size)  {
		super();
		mSize = size;
		int[] handle = new int[1];
		mTarget = getSharedRenderer().createRenderTarget(handle, size);
		mTextureHandle = handle[0];
		getSharedRenderer().setTarget(mTarget.getFrameBuffer(), mTarget);
		int currentFrameBuffer = getSharedRenderer().getScreenFrameBuffer();
		GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, currentFrameBuffer);
		
		mTextureBuffer = TEManagerTexture.getCoordsBuffer(null);
		mVertexBuffer = TEManagerTexture.getPositionBuffer(TEUtilSize.make(size, size));
	}

	@Override
	public void update(long dt) {}

	public void draw() {
	    mRenderPrimative.textureName = mTextureHandle;
	    mRenderPrimative.position.x = parent.position.x;
	    mRenderPrimative.position.y = parent.position.y;
	    mRenderPrimative.position.z = 0;
	    mRenderPrimative.vertexCount = 4;
	    mRenderPrimative.vertexBuffer = mVertexBuffer;
	    mRenderPrimative.textureBuffer = mTextureBuffer;
	    mRenderPrimative.extraData = getExtraData();
	    mRenderPrimative.extraType = getExtraType();
	    getRenderTarget().addPrimative(mRenderPrimative);
	}

	public TERenderTarget getTargetFrameBuffer() {
		return mTarget;
	}

	public int getTextureHandle() {
		return mTextureHandle;
	}
}