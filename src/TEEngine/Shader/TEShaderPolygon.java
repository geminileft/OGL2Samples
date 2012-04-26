package TEEngine.Shader;

import java.util.LinkedList;

import TEEngine.Render.TERenderPrimative;
import TEEngine.Render.TERenderTarget;
import android.opengl.GLES20;

public class TEShaderPolygon extends TEShaderProgram {

	public TEShaderPolygon(String vertexSource, String fragmentSource) {
		super(vertexSource, fragmentSource);
	}

	public void run(TERenderTarget target, LinkedList<TERenderPrimative> primatives) {
	    int vertexHandle = GLES20.glGetAttribLocation(mProgramId, "aVertices");
	    int colorHandle = GLES20.glGetUniformLocation(mProgramId, "aColor");
	    int posHandle = GLES20.glGetAttribLocation(mProgramId, "aPosition");
	    
	    TERenderPrimative p;
	    
	    int primativeCount = primatives.size();
	    for (int i = 0;i < primativeCount;++i) {
	        p = primatives.get(i);
	        
	        GLES20.glVertexAttribPointer(vertexHandle, 2, GLES20.GL_FLOAT, false, 0, p.vertexBuffer);
	        GLES20.glUniform4f(colorHandle, p.color.r, p.color.g, p.color.b, p.color.a);
	        GLES20.glVertexAttrib2f(posHandle, p.position.x, p.position.y);
	        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, p.vertexCount);        
	    }
	    deactivate();
	}
}
