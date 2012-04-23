package demo.OGL2Samples;

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
        GLSurfaceView view = new GLSurfaceView(this);
        view.setEGLContextClientVersion(2);
   		view.setRenderer(new TEUtilRenderer());
   		requestWindowFeature(Window.FEATURE_NO_TITLE);
   		setContentView(view);
    }
}