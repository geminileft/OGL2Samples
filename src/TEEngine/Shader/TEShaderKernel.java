package TEEngine.Shader;

import java.util.LinkedList;

import TEEngine.Render.TERenderPrimative;
import TEEngine.Render.TERenderTarget;
import TEEngine.Util.TEUtilVec3;
import android.opengl.GLES20;

public class TEShaderKernel extends TEShaderProgram {

	public TEShaderKernel(String vertexSource, String fragmentSource) {
		super(vertexSource, fragmentSource);
	}

	public void run(TERenderTarget target, LinkedList<TERenderPrimative> primatives) {
	    activate(target);
	    int positionHandle = GLES20.glGetAttribLocation(mProgramId, "aVertices");
	    int textureHandle = GLES20.glGetAttribLocation(mProgramId, "aTextureCoords");
	    int coordsHandle = GLES20.glGetAttribLocation(mProgramId, "aPosition");
	    int offsetHandle = GLES20.glGetUniformLocation(mProgramId, "uOffsets");
	    int kernelHandle = GLES20.glGetUniformLocation(mProgramId, "uKernel");

	    final float TEXTURE_SIZE = 256.0f;
	    float step_w = 1.0f / TEXTURE_SIZE;
	    float step_h = 1.0f / TEXTURE_SIZE;
	    
	    final int OFFSET_COUNT = 9;
	    float offsets[] = {
	        -step_w, -step_h
	        , 0.0f, -step_h
	        , step_w, -step_h
	        , -step_w, 0.0f
	        , 0.0f, 0.0f
	        , step_w, 0.0f
	        , -step_w, step_h
	        , 0.0f, step_h
	        , step_w, step_h
	    };
	    
	    TEUtilVec3 vec;
	    TERenderPrimative primative;
	    int primativeCount = primatives.size();
	    for (int i = 0;i < primativeCount;++i) {
	        primative = primatives.get(i);
	        vec = primative.position;
	        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, primative.textureName);
	        GLES20.glVertexAttrib2f(coordsHandle, vec.x, vec.y);
	        GLES20.glVertexAttribPointer(textureHandle, 2, GLES20.GL_FLOAT, false, 0, primative.textureBuffer);
	        GLES20.glVertexAttribPointer(positionHandle, 2, GLES20.GL_FLOAT, false, 0, primative.vertexBuffer);
	        GLES20.glUniform2fv(offsetHandle, OFFSET_COUNT, offsets, 0);
	        GLES20.glUniform1fv(kernelHandle, OFFSET_COUNT, (float[])primative.extraData, 0);
	        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 4);
	    }
	    deactivate();
	}
}
