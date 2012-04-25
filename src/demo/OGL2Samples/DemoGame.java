package demo.OGL2Samples;

import TEEngine.Core.TEEngine;
import TEEngine.Core.TEGame;
import TEEngine.Core.TEGameObject;
import TEEngine.Util.TEUtilPoint;
import TEEngine.Util.TEUtilSize;
import android.util.Log;

public class DemoGame extends TEGame {

	public void start() {
		TEEngine engine = TEEngine.sharedEngine();
		TEGameObject go;
		TEUtilSize size;
	    go = new TEGameObject();
	    size = TEUtilSize.make(160, 160);
	    RenderImage ri = new RenderImage(R.raw.club_ace, TEUtilPoint.make(0, 0), size);

	    go.position.x = 0.0f;
	    go.position.y = 0.0f;

	    go.addComponent(ri);
	    //engine.addGameObject(go);
		Log.v("DemoGame", "start");
	}
}
