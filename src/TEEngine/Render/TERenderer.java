package TEEngine.Render;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Set;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import TEEngine.Core.TEComponentRender;
import TEEngine.Core.TEEngine;
import TEEngine.Manager.TEManagerFile;
import TEEngine.Render.TERenderTarget.TEShaderType;
import TEEngine.Shader.TEShaderPolygon;
import TEEngine.Shader.TEShaderProgram;
import TEEngine.Shader.TEShaderTexture;
import TEEngine.Util.TEUtilSize;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.util.Log;

public class TERenderer implements GLSurfaceView.Renderer {
	
	private TERenderTarget mScreenTarget;
    private HashMap<TEShaderType, TEShaderProgram> mShaderPrograms = new HashMap<TEShaderType, TEShaderProgram>();
    private HashMap<Integer, TERenderTarget> mTargets;
    private int mScreenFrameBuffer;

    public void onSurfaceCreated(GL10 glUnused, EGLConfig config) {
        GLES20.glEnable(GL10.GL_BLEND);
		GLES20.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
        GLES20.glClearColor(0.0f, 0.0f, 1.0f, 1.0f);
        String vertexSource;
        String fragmentSource;
		TEShaderProgram program;
		
		vertexSource = TEManagerFile.readFileContents("texture.vs");
		fragmentSource = TEManagerFile.readFileContents("texture.fs");
		program = new TEShaderTexture(vertexSource, fragmentSource);
	    program.addAttribute("aVertices");
	    program.addAttribute("aTextureCoords");
		program.create();
		mShaderPrograms.put(TEShaderType.ShaderTexture, program);
		
		vertexSource = TEManagerFile.readFileContents("colorbox.vs");
		fragmentSource = TEManagerFile.readFileContents("colorbox.fs");
		program = new TEShaderPolygon(vertexSource, fragmentSource);
	    program.addAttribute("aVertices");
		program.create();
		mShaderPrograms.put(TEShaderType.ShaderPolygon, program);
		
		int[] params = new int[1];
		GLES20.glGetIntegerv(GLES20.GL_FRAMEBUFFER_BINDING, params, 0);
		mScreenFrameBuffer = params[0];
		mScreenTarget = new TERenderTarget(mScreenFrameBuffer);
		
		TEEngine engine = TEEngine.sharedEngine();
		TEComponentRender.setSharedRenderer(this);
		engine.start();
    }

    public void onSurfaceChanged(GL10 glUnused, int width, int height) {
    	mScreenTarget.setSize(TEUtilSize.make(width, height));
    }

    public void onDrawFrame(GL10 glUnused) {
    	mScreenTarget.resetPrimatives();
    	TEEngine engine = TEEngine.sharedEngine();
    	engine.run();
        GLES20.glClear( GLES20.GL_COLOR_BUFFER_BIT);
        runTargetShaders(mScreenTarget);
    }
    
    public void runTargetShaders(TERenderTarget target) {

    	HashMap<TEShaderType, LinkedList<TERenderPrimative>> shaderData;
        TEShaderProgram rp;

        target.activate();
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        shaderData = target.getShaderData();
        Set<TEShaderType> keySet;
		if (shaderData != null) {
			keySet = shaderData.keySet();
			for (TEShaderType type : keySet) {
				rp = mShaderPrograms.get(type);
				if (rp != null) {
					    rp.activate(target);
					    rp.run(target, shaderData.get(type));
				} else {
					Log.v("No Shader", "hrm");
				}
			}
		}
    }
    
    public TERenderTarget createRenderTarget(int[] textureHandle, int size) {
        int[] frameBuffer = new int[1];
        GLES20.glGenTextures(1, textureHandle, 0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureHandle[0]);
        GLES20.glTexImage2D(GLES20.GL_TEXTURE_2D, 0, GLES20.GL_RGBA, size, size, 0, GLES20.GL_RGBA, GLES20.GL_UNSIGNED_BYTE, null);
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);    
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE);
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE);
        GLES20.glGenFramebuffers(1, frameBuffer, 0);
        GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, frameBuffer[0]);
        GLES20.glFramebufferTexture2D(GLES20.GL_FRAMEBUFFER, GLES20.GL_COLOR_ATTACHMENT0, GLES20.GL_TEXTURE_2D, textureHandle[0], 0);
        int status = GLES20.glCheckFramebufferStatus(GLES20.GL_FRAMEBUFFER); 
        if(status != GLES20.GL_FRAMEBUFFER_COMPLETE) {
        	Log.v("Failed", "Frame buffer creation");
        }

        TERenderTarget target = new TERenderTarget(frameBuffer[0]);
        target.setSize(TEUtilSize.make(size, size));
        return target;
    }

    public void setTarget(int frameBuffer, TERenderTarget target) {
        mTargets.put(frameBuffer, target);
    }

	public TERenderTarget getScreenTarget() {
		return mScreenTarget;
	}
	
	public int getScreenFrameBuffer() {
	    return mScreenFrameBuffer;
	}

}
