package demo.OGL2Samples;

import java.nio.FloatBuffer;

import TEEngine.Util.TEUtilVec3;

import demo.OGL2Samples.TERenderTarget.TEShaderType;

public class TERenderPrimative {

	public int textureName;
	public TEUtilVec3 position = new TEUtilVec3();
	public int vertexCount;
	public FloatBuffer vertexBuffer;
	public FloatBuffer textureBuffer;
	public Object extraData;
	public TEShaderType extraType;
}
