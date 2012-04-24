package demo.OGL2Samples;

import java.util.LinkedList;

import android.opengl.GLES20;

public class TEShaderTexture extends TEShaderProgram {

	TEShaderTexture(String vertexSource, String fragmentSource) {
		super(vertexSource, fragmentSource);
	}

	public void run(TERenderTarget target, LinkedList<TERenderPrimative> primatives) {
	    activate(target);
	    int positionHandle = GLES20.glGetAttribLocation(mProgramId, "aVertices");
	    int textureHandle = GLES20.glGetAttribLocation(mProgramId, "aTextureCoords");
	    int coordsHandle = GLES20.glGetAttribLocation(mProgramId, "aPosition");
	    int alphaHandle = GLES20.glGetUniformLocation(mProgramId, "uAlpha");
	    
	    TEVec3 vec;
	    int primativeCount = primatives.size();
	    for (int i = 0;i < primativeCount;++i) {
	        vec = primatives.get(i).position;
	        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, primatives.get(i).textureName);
	        GLES20.glVertexAttrib2f(coordsHandle, vec.x, vec.y);
	        GLES20.glVertexAttribPointer(textureHandle, 2, GLES20.GL_FLOAT, false, 0, primatives.get(i).textureBuffer);
	        GLES20.glVertexAttribPointer(positionHandle, 2, GLES20.GL_FLOAT, false, 0, primatives.get(i).vertexBuffer);
	        GLES20.glUniform1f(alphaHandle, 1.0f);
	        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, primatives.get(i).vertexCount);
	    }
	    
	    deactivate();
	}
}
