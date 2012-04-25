package demo.OGL2Samples;

import TEEngine.Core.TEEngine;
import TEEngine.Manager.TEManagerFile;
import TEEngine.Manager.TEManagerTexture;
import TEEngine.Render.TERenderer;
import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.Window;

public class OGL2SampleMainActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TEManagerFile.setContext(this);
        TEManagerTexture texMgr = TEManagerTexture.sharedInstance();
        texMgr.setContext(this);
        GLSurfaceView view = new GLSurfaceView(this);
        view.setEGLContextClientVersion(2);
   		view.setRenderer(new TERenderer());
   		requestWindowFeature(Window.FEATURE_NO_TITLE);
   		setContentView(view);
   		DemoGame game = new DemoGame();
   		TEEngine engine = TEEngine.sharedEngine();
   		engine.setGame(game);
   		engine.setView(view);
    }
}