package demo.OGL2Samples;

import TEEngine.Core.TEEngine;
import TEEngine.Core.TEGame;
import TEEngine.Core.TEGameObject;
import TEEngine.Util.TEColor4;
import TEEngine.Util.TEUtilPoint;
import TEEngine.Util.TEUtilSize;
import android.util.Log;

public class DemoGame extends TEGame {

	public void start() {
		//imageDemo();
		polyDemo();
		Log.v("DemoGame", "start");
	}
	
	public void imageDemo() {
		TEEngine engine = TEEngine.sharedEngine();
		TEGameObject go;
		TEUtilSize size;
	    go = new TEGameObject();
	    size = TEUtilSize.make(160, 160);
	    RenderImage ri = new RenderImage(R.raw.club_ace, TEUtilPoint.make(0, 0), size);

	    go.position.x = 0.0f;
	    go.position.y = 0.0f;

	    go.addComponent(ri);
	    engine.addGameObject(go);		
	}
	
	public void polyDemo() {
		TEEngine engine = TEEngine.sharedEngine();
		TEGameObject go;
		TEColor4 color;
		TEUtilSize size;
		RenderPolygon rp;
		int radius;
	    go = new TEGameObject();
	    color = TEColor4.make(0.0f, 0.0f, 0.0f, 1.0f);
	    size = TEUtilSize.make(164, 164);
	    radius = 5;
	    
	    rp = RenderPolygonFactory.roundedRect(size, color, (float)radius, radius);
	    //rp->setRenderTarget(rtt->getTargetFrameBuffer());
	    go.addComponent(rp);
	    
	    size.width -= 4;
	    size.height -= 4;
	    color.r = 1.0f;
	    color.g = 0.0f;
	    color.b = 0.0f;
	    color.a = 1.0f;
	    rp = RenderPolygonFactory.roundedRect(size, color, radius, radius);
	    //rp->setRenderTarget(rtt->getTargetFrameBuffer());
	    go.addComponent(rp);
	    
	    go.position.x = 0.0f;
	    go.position.y = 0.0f;
	    
	    engine.addGameObject(go);
	}
}
