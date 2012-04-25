package TEEngine.Render;

import java.util.HashMap;
import java.util.LinkedList;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import TEEngine.Core.TEEngine;
import TEEngine.Manager.TEManagerFile;
import TEEngine.Render.TERenderTarget.TEShaderType;
import TEEngine.Shader.TEShaderProgram;
import TEEngine.Shader.TEShaderTexture;
import TEEngine.Util.TEUtilSize;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

public class TERenderer implements GLSurfaceView.Renderer {
	
	private TEShaderProgram mProgram;
	private TERenderTarget mScreenTarget;
	
    public void onSurfaceCreated(GL10 glUnused, EGLConfig config) {
		TEEngine engine = TEEngine.sharedEngine();
        GLES20.glEnable(GL10.GL_BLEND);
		GLES20.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		String vertexSource = TEManagerFile.readFileContents("texture.vs");
		String fragmentSource = TEManagerFile.readFileContents("texture.fs");
		mProgram = new TEShaderTexture(vertexSource, fragmentSource);
	    mProgram.addAttribute("aVertices");
	    mProgram.addAttribute("aTextureCoords");
        GLES20.glClearColor(0.0f, 0.0f, 1.0f, 1.0f);
		mProgram.create();
		int[] params = new int[1];
		GLES20.glGetIntegerv(GLES20.GL_FRAMEBUFFER_BINDING, params, 0);
		mScreenTarget = new TERenderTarget(params[0]);
		engine.setScreenTarget(mScreenTarget);
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
        
        target.activate();
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        shaderData = target.getShaderData();
        if (shaderData != null) {
            for (LinkedList<TERenderPrimative> primatives : shaderData.values()) {
                mProgram.activate(target);
                mProgram.run(target, primatives);
            }        	
        }
    }
}
