package demo.OGL2Samples;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

public class TEUtilRenderer implements GLSurfaceView.Renderer {
	
	private TEShaderProgram mProgram;
	
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
    }

    public void onSurfaceChanged(GL10 glUnused, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
    }

    public void onDrawFrame(GL10 glUnused) {
        GLES20.glClear( GLES20.GL_COLOR_BUFFER_BIT);
/*
    TEShaderData* shaderData;
    TEShaderData shader;
    uint count;
    TERendererProgram* rp;
    
    target->activate();
    glClear(GL_COLOR_BUFFER_BIT);
    shaderData = target->getShaderData(count);
    for (uint i = 0; i < count; ++i) {
        shader = shaderData[i];
        rp = mShaderPrograms[shader.type];
        if (rp != NULL) {
            rp->activate(target);
            rp->run(target, shader.primatives, shader.primativeCount);
        } else {
            NSLog(@"Hrm.");
        }
    }
 */
    }
}
