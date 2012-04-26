package demo.OGL2Samples;

import TEEngine.Core.TEComponentRender;
import TEEngine.Render.TERenderPrimative;
import TEEngine.Util.TEColor4;

class RenderPolygon extends TEComponentRender {
	private float mR;
	private float mG;
	private float mB;
	private float mA;
	private TERenderPrimative mRenderPrimative;

	
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

	public void setColor(TEColor4 color) {
	    mRenderPrimative.color.r = color.r;
	    mRenderPrimative.color.g = color.g;
	    mRenderPrimative.color.b = color.b;
	    mRenderPrimative.color.a = color.a;
	    
	    mR = color.r;
	    mG = color.g;
	    mB = color.b;
	    mA = color.a;
	}

	/*
void setVertices(float[] vertices, int vertexCount) {
    int memSize = vertexCount * 2 * sizeof(float);
    if (mRenderPrimative.vertexCount > 0) {
        free(mRenderPrimative.vertexBuffer);
    }
    mRenderPrimative.vertexCount = vertexCount;
    mRenderPrimative.vertexBuffer = (float*)malloc(memSize);
    mVertices = mRenderPrimative.vertexBuffer;
    memcpy(mRenderPrimative.vertexBuffer, vertices, memSize);
    mVertexCount = vertexCount;
    mRenderPrimative.vertexCount = vertexCount;
}
*/
}