package demo.OGL2Samples;

import java.nio.FloatBuffer;

import demo.OGL2Samples.TERenderTarget.TEShaderType;

public class TERenderPrimative {

	public int textureName;
	public TEVec3 position = new TEVec3();
	public int vertexCount;
	public FloatBuffer vertexBuffer;
	public FloatBuffer textureBuffer;
	public Object extraData;
	public TEShaderType extraType;
}
