package demo.OGL2Samples;

import java.util.HashMap;
import java.util.LinkedList;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import demo.OGL2Samples.TERenderTarget.TEShaderType;

public class TEUtilRenderer implements GLSurfaceView.Renderer {
	
	private TEShaderProgram mProgram;
	private TERenderTarget mScreenTarget;
	
    public void onSurfaceCreated(GL10 glUnused, EGLConfig config) {
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
		TEEngine engine = TEEngine.sharedEngine();
		engine.start();
    }

    public void onSurfaceChanged(GL10 glUnused, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
    }

    public void onDrawFrame(GL10 glUnused) {
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
