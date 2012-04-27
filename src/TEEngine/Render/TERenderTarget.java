package TEEngine.Render;

import java.util.HashMap;
import java.util.LinkedList;

import TEEngine.Shader.TEShaderData;
import TEEngine.Util.TEUtilSize;
import android.opengl.GLES20;
import android.opengl.Matrix;
import android.util.Log;

public class TERenderTarget {
	private int mFrameBuffer;
	private int mFrameWidth;
    private int mFrameHeight;
    float mProjMatrix[] = new float[16];
    float mViewMatrix[] = new float[16];
    public enum TEShaderType {
    	ShaderTexture
    	, ShaderPolygon
    	, ShaderKernel
    	, ShaderTransparentColor
    	, ShaderGrayscale
    };
    
	HashMap<TEShaderType, LinkedList<TERenderPrimative>> mShaders = new HashMap<TEShaderType, LinkedList<TERenderPrimative>>();
	HashMap<TEShaderType, LinkedList<TERenderPrimative>> mShaderBuffer = new HashMap<TEShaderType, LinkedList<TERenderPrimative>>();
	TEShaderData mShaderData;

	public TERenderTarget(int frameBuffer) {
		mFrameBuffer = frameBuffer;
	}

	public void setSize(TEUtilSize size) {
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

	public int getFrameBuffer() {
		return mFrameBuffer;
	}

	public int getFrameWidth()  {
		return mFrameWidth;
	}

	public int getFrameHeight() {
	    return mFrameHeight;
	}

	public void resetPrimatives() {
	    mShaders.clear();
	}

	public void activate() {
	    GLES20.glViewport(0, 0, mFrameWidth, mFrameHeight);
	    GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, mFrameBuffer);
	}

	public float[] getProjMatrix() {
	    return mProjMatrix;
	}

	public float[] getViewMatrix() {
	    return mViewMatrix;
	}

	public void addPrimative(TERenderPrimative primative) {
	    
		TEShaderType type;
	    LinkedList<TERenderPrimative> primatives;
	    
	    if (primative.textureBuffer == null) {
	        type = TEShaderType.ShaderPolygon;
	    } else {
	        if (primative.extraData != null) {
	            type = primative.extraType;
	        } else {
	            type = TEShaderType.ShaderTexture;
	        }
	    }
	    if (mShaders.containsKey(type))
	        primatives = mShaders.get(type);
	    else {
	    	primatives = new LinkedList<TERenderPrimative>();
	    	mShaders.put(type, primatives);
	    }
	    primatives.add(primative);
	}

	public HashMap<TEShaderType, LinkedList<TERenderPrimative>> getShaderData() {
		Log.v("getShaderData", "trying to locate error");
		synchronized(mShaderBuffer) {
			mShaderBuffer.clear();
			for (TEShaderType key : mShaders.keySet()) {
				LinkedList<TERenderPrimative> list = new LinkedList<TERenderPrimative>();
				for (TERenderPrimative primative : mShaders.get(key)) {
					list.add(primative);
				}
				mShaderBuffer.put(key, list);
			}
		}
		mShaders.clear();
	    return mShaderBuffer;
	}
}
