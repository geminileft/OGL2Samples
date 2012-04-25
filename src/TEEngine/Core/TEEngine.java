package TEEngine.Core;

import TEEngine.Manager.TEManagerRender;
import TEEngine.Manager.TEManagerTime;
import android.opengl.GLSurfaceView;
import android.util.Log;

public final class TEEngine implements Runnable {
	private final int MAX_FRAME_TIME = 1000 / 30;//cap frame duration to 30fps
	private static TEEngine mSharedEngine;
	private TEGame mGame;
	private GLSurfaceView mView;
	private long mPreviousTime;
	
	public static final TEEngine sharedEngine() {
		if (mSharedEngine == null) {
			mSharedEngine = new TEEngine();
		}
		return mSharedEngine;
	}
	
	public final void start() {
		Log.v("start", "Start");
		mGame.start();
		Thread thread = new Thread(this);
		thread.start();
	}
	
	public void run() {
		TEManagerRender renderMgr = TEManagerRender.sharedManager();
		while(true) {
			final long currentTime = TEManagerTime.currentTime();
			long dt = currentTime - mPreviousTime;
			mPreviousTime = currentTime;
			dt = (dt > MAX_FRAME_TIME) ? MAX_FRAME_TIME : dt;
			renderMgr.update(dt);
			mView.postInvalidate();			
		}
	}
	
	public void setGame(TEGame game) {
		mGame = game;
	}
	
	public void setView(GLSurfaceView view) {
		mView = view;
	}
}
