package TEEngine.Render;

import java.util.HashMap;
import java.util.LinkedList;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

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
		mScreenTarget = new TERenderTarget(params[0]);
		
		TEEngine engine = TEEngine.sharedEngine();
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
        TEShaderProgram rp;

        target.activate();
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        shaderData = target.getShaderData();
		if (shaderData != null) {
			for (TEShaderType type : shaderData.keySet()) {
				rp = mShaderPrograms.get(type);
				if (rp != null) {
					for (LinkedList<TERenderPrimative> primatives : shaderData.values()) {
					    rp.activate(target);
					    rp.run(target, primatives);
					}        	
				} else {
					Log.v("No Shader", "hrm");
				}
			}
		}
    }
}
