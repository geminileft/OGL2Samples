package TEEngine.Core;

import java.util.HashMap;

import TEEngine.Manager.TEManagerComponent;

public abstract class TEComponent {
	public interface EventListener {
		public abstract void invoke();
	}
	
	public static enum Event {
		EVENT_TOUCH_STARTED
		, EVENT_TOUCH_REJECT
		, EVENT_TOUCH_ACCEPT
		, EVENT_TOUCH_ENDED
		, EVENT_MOVE_TO_TOP
		, EVENT_ACCEPT_MOVE
		, EVENT_REJECT_MOVE
		, EVENT_MOVE_TO_FOUNDATION
	}
	
	public TEGameObject parent;
	private TEManagerComponent mManager;
	
	private HashMap<TEComponent.Event, EventListener> mEventSubscriptions = new HashMap<TEComponent.Event, EventListener>();

	public abstract void update(long dt);
	
	public final void setParent(TEGameObject parentGameObject) {
		parent = parentGameObject;
	}
	
	public final void setManager(TEManagerComponent manager) {
		mManager = manager;
	}
	
	public final TEManagerComponent getManager() {
		return mManager;
	}
	
	public final void addEventSubscription(TEComponent.Event event, EventListener listener) {
		mEventSubscriptions.put(event, listener);
	}
	
	public final HashMap<TEComponent.Event, EventListener> getEventSubscriptions() {
		return mEventSubscriptions;
	}
}
