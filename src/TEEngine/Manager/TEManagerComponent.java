package TEEngine.Manager;

import java.util.LinkedList;

import TEEngine.Core.TEComponent;


public abstract class TEManagerComponent {
	private LinkedList<TEComponent> mComponents;
	
	public TEManagerComponent() {
		mComponents = new LinkedList<TEComponent>();
	}

	public void update(long dt) {
		LinkedList<TEComponent> components = getComponents();
		final int size = components.size();
		for(int i = 0; i < size; ++i) {
			TEComponent component = components.get(i);
	    	component.update(dt);
	    }
	}
	
	public void addComponent(TEComponent component) {
		addComponent(component, -1);
	}
	
	public void addComponent(TEComponent component, int index) {
		if (index == -1) {
			mComponents.add(component);
		} else {
			mComponents.add(index, component);
		}
		component.setManager(this);
	}
	
	public final LinkedList<TEComponent> getComponents() {
		return mComponents;
	}

	public abstract void moveComponentToTop(TEComponent component);
}
