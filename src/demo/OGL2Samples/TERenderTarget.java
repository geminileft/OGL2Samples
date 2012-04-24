package demo.OGL2Samples;

import java.util.HashMap;
import java.util.LinkedList;

import android.opengl.GLES20;
import android.opengl.Matrix;

class TERenderTarget {
	
	private int mFrameBuffer;
	private int mFrameWidth;
    private int mFrameHeight;
    float mProjMatrix[] = new float[16];
    float mViewMatrix[] = new float[16];
    public enum TEShaderType {
    	ShaderTexture
    };
    
	HashMap<TEShaderType, LinkedList<TERenderPrimative>> mShaders;
	TEShaderData mShaderData;

	TERenderTarget(int frameBuffer) {
		mFrameBuffer = frameBuffer;
	}

	void setSize(TESize size) {
	    mFrameWidth = size.width;
	    mFrameHeight = size.height;
	    
	    float zDepth;
	    float ratio;
	    
	    zDepth = (float)mFrameHeight / 2;
	    ratio = (float)mFrameWidth/(float)mFrameHeight;
	    
	    Matrix.frustumM(mProjMatrix, 0, -ratio, ratio, -1, 1, 1.0f, 1000.0f);
	    Matrix.setIdentityM(mViewMatrix, 0);
	    Matrix.translateM(mViewMatrix, 0, 0.0f, 0.0f, -zDepth);    
	}

	int getFrameBuffer() {
		return mFrameBuffer;
	}

	int getFrameWidth()  {
		return mFrameWidth;
	}

	int getFrameHeight() {
	    return mFrameHeight;
	}

	void resetPrimatives() {
	    mShaders.clear();
	}

	void activate() {
	    GLES20.glViewport(0, 0, mFrameWidth, mFrameHeight);
	    GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, mFrameBuffer);
	}

	float[] getProjMatrix() {
	    return mProjMatrix;
	}

	float[] getViewMatrix() {
	    return mViewMatrix;
	}

	void addPrimative(TERenderPrimative primative) {
	    TEShaderType type;
	    LinkedList<TERenderPrimative> primatives;
	    
	    type = TEShaderType.ShaderTexture;
	
	    if (mShaders.containsKey(type))
	        primatives = mShaders.get(type);
	    else {
	    	primatives = new LinkedList<TERenderPrimative>();
	    	mShaders.put(type, primatives);
	    }
	    primatives.add(primative);
	}

	HashMap<TEShaderType, LinkedList<TERenderPrimative>> getShaderData() {
	    return mShaders;
	}
}
