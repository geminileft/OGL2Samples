package TEEngine.Core;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

import TEEngine.Manager.TEManagerComponent;
import TEEngine.Util.TEPoint;
import TEEngine.Util.TEUtilSize;

public class TEGameObject extends TEManagerComponent {
	public enum ObjectState {
		NORMAL
		, MOVING
	}
	
	public enum ObjectDirection {
		NORMAL
		, REVERSE
	}
	
	public ObjectState state;
	public ObjectDirection direction; 
	
	private HashMap<TEComponent.Event, Vector<TEComponent.EventListener>> mEventSubscribers = new HashMap<TEComponent.Event, Vector<TEComponent.EventListener>>(); 
	
	public TEUtilSize size = TEUtilSize.make(0, 0);
	public TEPoint position = TEPoint.make(0, 0);
	
	public TEGameObject() {
		super();
		state = ObjectState.NORMAL;
		direction = ObjectDirection.NORMAL;
	}
	
	@Override
	public void update(long dt) {
		// TODO Auto-generated method stub	
	}
	
	@Override
	public void addComponent(TEComponent component) {
		super.addComponent(component);
		HashMap<TEComponent.Event, TEComponent.EventListener> eventSubscriptions = component.getEventSubscriptions();
		if (!eventSubscriptions.isEmpty()) {
			Set<TEComponent.Event> keys = eventSubscriptions.keySet();
			Iterator<TEComponent.Event> iterator = keys.iterator();
			while (iterator.hasNext()) {
				TEComponent.Event event = iterator.next();
				addEventSubscription(event, eventSubscriptions.get(event));
			}
		}
		component.setParent(this);		
	}

	public void invokeEvent(TEComponent.Event event) {
		Vector<TEComponent.EventListener> subscribers = mEventSubscribers.get(event);
		if (subscribers != null) {
			Iterator<TEComponent.EventListener> iterator = subscribers.iterator();
			while (iterator.hasNext()) {
				TEComponent.EventListener eventListener = iterator.next();
				eventListener.invoke();
			}
		}
	}
	
	public final void addEventSubscription(TEComponent.Event event, TEComponent.EventListener eventListener) {
		Vector<TEComponent.EventListener> subscribers = mEventSubscribers.get(event);
		if (subscribers == null) {
			subscribers = new Vector<TEComponent.EventListener>();
		}
		subscribers.add(eventListener);
		mEventSubscribers.put(event, subscribers);
	}

	public final void moveComponentToTop(TEComponent component) {}
}
