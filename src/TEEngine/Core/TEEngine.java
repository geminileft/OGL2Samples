package TEEngine.Core;

import java.util.LinkedList;
import java.util.Vector;

import TEEngine.Manager.TEManagerRender;
import TEEngine.Manager.TEManagerTime;
import TEEngine.Render.TERenderTarget;
import android.opengl.GLSurfaceView;
import android.util.Log;

public final class TEEngine implements Runnable {
	private final int MAX_FRAME_TIME = 1000 / 30;//cap frame duration to 30fps
	private static TEEngine mSharedEngine;
	private Vector<TEGameObject> mGameObjects = new Vector<TEGameObject>();
	private TEGame mGame;
	private GLSurfaceView mView;
	private long mPreviousTime;
	private TERenderTarget mScreenTarget;
	
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
	
	public final void addGameObject(TEGameObject gameObject) {
		TEManagerRender renderManager = TEManagerRender.sharedManager();
		LinkedList<TEComponent> components = gameObject.getComponents();
		final int size = components.size();
		TEComponent component;
		for(int i = 0; i < size; ++i) {
			component = components.get(i);
			if (component instanceof TEComponentRender) {
				renderManager.addComponent(component);
			}
		}
		mGameObjects.add(gameObject);
	}

	public TERenderTarget getScreenTarget() {
		return mScreenTarget;
	}
	
	public void setScreenTarget(TERenderTarget target) {
		mScreenTarget = target;
	}
}
