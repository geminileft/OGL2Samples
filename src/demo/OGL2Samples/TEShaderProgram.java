package demo.OGL2Samples;

import android.opengl.GLES20;
import android.util.Log;

public abstract class TEShaderProgram {
	private String mVertexSource;
	private String mFragmentSource;
	private int mProgramId;
	
	TEShaderProgram(String vertexSource, String fragmentSource) {
		setVertexSource(vertexSource);
		setFragmentSource(fragmentSource);
	}
	
	public final void setVertexSource(String source) {
		mVertexSource = source;
	}
	
	public final void setFragmentSource(String source) {
		mFragmentSource = source;
	}

    public final void create() {
        mProgramId = GLES20.glCreateProgram();
        int vertexShader = loadShader(GLES20.GL_VERTEX_SHADER, mVertexSource);
        GLES20.glAttachShader(mProgramId, vertexShader);
        int fragmentShader = loadShader(GLES20.GL_FRAGMENT_SHADER, mFragmentSource);
        GLES20.glAttachShader(mProgramId, fragmentShader);
        GLES20.glLinkProgram(mProgramId);
        int[] linkStatus = new int[1];
        GLES20.glGetProgramiv(mProgramId, GLES20.GL_LINK_STATUS, linkStatus, 0);
        if (linkStatus[0] != GLES20.GL_TRUE) {
            Log.e("Error", "Could not link program: ");
            Log.e("Error", GLES20.glGetProgramInfoLog(mProgramId));
            GLES20.glDeleteProgram(mProgramId);
            mProgramId = 0;
        }
    }

    private int loadShader(int shaderType, String source) {
        int shader = GLES20.glCreateShader(shaderType);
        GLES20.glShaderSource(shader, source);
        checkGlError("glShaderSource");
        GLES20.glCompileShader(shader);
        checkGlError("glCompileShader");
        return shader;
    }

    private void checkGlError(String op) {
        int error;
        while ((error = GLES20.glGetError()) != GLES20.GL_NO_ERROR) {
        	String errorMsg = GLES20.glGetProgramInfoLog(mProgramId);
            Log.e("info", op + ": glError " + error + errorMsg);
            throw new RuntimeException(op + ": glError " + error);
        }
    }

}
