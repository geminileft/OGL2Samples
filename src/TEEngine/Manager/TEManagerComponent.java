package TEEngine.Manager;

import TEEngine.Core.TEComponent;
import TEEngine.Util.TEComponentContainer;


public abstract class TEManagerComponent extends TEManagerAbstract {
	private TEComponentContainer mComponents;
	
	public TEManagerComponent() {
		mComponents = new TEComponentContainer();
	}

	public void update(long dt) {
		TEComponentContainer components = getComponents();
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
	
	public final TEComponentContainer getComponents() {
		return mComponents;
	}

	public abstract void moveComponentToTop(TEComponent component);
}
