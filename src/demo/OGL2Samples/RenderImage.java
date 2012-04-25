package demo.OGL2Samples;

import TEEngine.Core.TEComponent;
import TEEngine.Core.TEComponentRender;
import TEEngine.Manager.TEManagerTexture;
import TEEngine.Render.TERenderPrimative;
import TEEngine.Util.TEUtilPoint;
import TEEngine.Util.TEUtilSize;

public class RenderImage extends TEComponentRender {

	private int mWidth;
	private int mHeight;
    TERenderPrimative mRenderPrimative;
    
	private TEComponent.EventListener mMoveToTopListener = new TEComponent.EventListener() {
		
		public void invoke() {
			RenderImage.this.getManager().moveComponentToTop(RenderImage.this);
		}
	};
	
	public RenderImage(int resourceId) {
		this(resourceId, TEUtilPoint.zero(), TEUtilSize.zero());
	}

	public RenderImage(int resourceId, TEUtilPoint position, TEUtilSize size) {
		super();
		TEManagerTexture texMgr = TEManagerTexture.sharedInstance();
		mRenderPrimative = new TERenderPrimative();
	    mRenderPrimative.textureName = texMgr.getTexture2D(R.raw.club_ace);
	    mRenderPrimative.position.x = 0;
	    mRenderPrimative.position.y = 0;
	    mRenderPrimative.position.z = 0;
	    mRenderPrimative.vertexCount = 4;
	    mRenderPrimative.vertexBuffer = TEManagerTexture.getPositionBuffer(TEUtilSize.make(size.width, size.height));
	    mRenderPrimative.textureBuffer = TEManagerTexture.getCoordsBuffer(null);
		addEventSubscription(TEComponent.Event.EVENT_MOVE_TO_TOP, mMoveToTopListener);
	}

	public void draw() {
	}

	@Override
	public void update(long dt) {
	    mRenderTarget.addPrimative(mRenderPrimative);		
	}
	
	public TEUtilSize getSize() {
		return new TEUtilSize(mWidth, mHeight);
	}
}