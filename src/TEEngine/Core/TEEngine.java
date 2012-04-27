package TEEngine.Core;

import java.util.LinkedList;
import java.util.Vector;

import TEEngine.Manager.TEManagerRender;
import TEEngine.Manager.TEManagerTime;
import TEEngine.Render.TERenderer;

public final class TEEngine implements Runnable {
	private final int MAX_FRAME_TIME = 1000 / 30;//cap frame duration to 30fps
	private static TEEngine mSharedEngine;
	private Vector<TEGameObject> mGameObjects = new Vector<TEGameObject>();
	private TEGame mGame;
	private long mPreviousTime;
	private TERenderer mRenderer;
	
	public static final TEEngine sharedEngine() {
		if (mSharedEngine == null) {
			mSharedEngine = new TEEngine();
		}
		return mSharedEngine;
	}
	
	public final void start() {
		mGame.start();
	}
	
	public void run() {
		mRenderer.reset();
		TEManagerRender renderMgr = TEManagerRender.sharedManager();
		final long currentTime = TEManagerTime.currentTime();
		long dt = currentTime - mPreviousTime;
		mPreviousTime = currentTime;
		dt = (dt > MAX_FRAME_TIME) ? MAX_FRAME_TIME : dt;
		renderMgr.update(dt);
	}
	
	public void setGame(TEGame game) {
		mGame = game;
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
	
	public final void setRenderer(TERenderer renderer) {
		mRenderer = renderer;
	}
}
