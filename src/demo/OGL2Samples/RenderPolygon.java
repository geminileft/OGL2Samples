package demo.OGL2Samples;

import java.nio.FloatBuffer;

import TEEngine.Core.TEComponentRender;
import TEEngine.Render.TERenderPrimative;
import TEEngine.Util.TEUtilColor4;

class RenderPolygon extends TEComponentRender {
	private float mR;
	private float mG;
	private float mB;
	private float mA;
	private TERenderPrimative mRenderPrimative = new TERenderPrimative();

	
	public RenderPolygon() {
    mRenderPrimative.textureBuffer = null;
    mRenderPrimative.extraData = null;
    mRenderPrimative.vertexCount = 0;
}

	public void update(long dt) {
    
	    mRenderPrimative.color.r = mR;
	    mRenderPrimative.color.g = mG;
	    mRenderPrimative.color.b = mB;
	    mRenderPrimative.color.a = mA;
	    
	    mRenderPrimative.position.x = parent.position.x;
	    mRenderPrimative.position.y = parent.position.y;
	    mRenderPrimative.position.z = 0.0f;
	
	    mRenderPrimative.position.x = parent.position.x;
	    mRenderPrimative.position.y = parent.position.y;
	
	    mRenderTarget.addPrimative(mRenderPrimative);
	}

	public void draw() {}

	public void moveToTopListener() {
		//getManager()->moveComponentToTop(this);
	};

	public void setColor(TEUtilColor4 color) {
	    mRenderPrimative.color.r = color.r;
	    mRenderPrimative.color.g = color.g;
	    mRenderPrimative.color.b = color.b;
	    mRenderPrimative.color.a = color.a;
	    
	    mR = color.r;
	    mG = color.g;
	    mB = color.b;
	    mA = color.a;
	}

	public void setVertices(FloatBuffer vertexBuffer, int vertexCount) {
	    mRenderPrimative.vertexCount = vertexCount;
	    mRenderPrimative.vertexBuffer = vertexBuffer;
	}
}