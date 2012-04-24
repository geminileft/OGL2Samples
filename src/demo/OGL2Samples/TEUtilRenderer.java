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
	private TERenderPrimative mRenderPrimative;
	
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
		TEManagerTexture texMgr = TEManagerTexture.sharedInstance();
		mRenderPrimative = new TERenderPrimative();
	    mRenderPrimative.textureName = texMgr.getTexture2D(R.raw.club_ace);
	    mRenderPrimative.position.x = 0;
	    mRenderPrimative.position.y = 0;
	    mRenderPrimative.position.z = 0;
	    mRenderPrimative.vertexCount = 4;
	    mRenderPrimative.vertexBuffer = TEManagerTexture.getPositionBuffer(TESize.make(100, 100));
	    mRenderPrimative.textureBuffer = TEManagerTexture.getCoordsBuffer(null);
		engine.start();
    }

    public void onSurfaceChanged(GL10 glUnused, int width, int height) {
    	mScreenTarget.setSize(TESize.make(width, height));
    }

    public void onDrawFrame(GL10 glUnused) {
    	mScreenTarget.resetPrimatives();
    	mScreenTarget.addPrimative(mRenderPrimative);
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
