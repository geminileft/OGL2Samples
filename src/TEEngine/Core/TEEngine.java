package TEEngine.Core;

import android.opengl.GLSurfaceView;
import android.util.Log;

public final class TEEngine implements Runnable {
	private static TEEngine mSharedEngine;
	private TEGame mGame;
	private GLSurfaceView mView;
	
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
		while(true) {
			
			//Log.v("here", "here");
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
