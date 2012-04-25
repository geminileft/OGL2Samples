package TEEngine.Render;

import java.nio.FloatBuffer;

import TEEngine.Render.TERenderTarget.TEShaderType;
import TEEngine.Util.TEUtilVec3;


public class TERenderPrimative {

	public int textureName;
	public TEUtilVec3 position = new TEUtilVec3();
	public int vertexCount;
	public FloatBuffer vertexBuffer;
	public FloatBuffer textureBuffer;
	public Object extraData;
	public TEShaderType extraType;
}
