package com.lukflug.panelstudio.container;

import java.util.ArrayList;
import java.util.List;

import com.lukflug.panelstudio.base.Context;
import com.lukflug.panelstudio.base.IBoolean;
import com.lukflug.panelstudio.base.IToggleable;
import com.lukflug.panelstudio.base.SimpleToggleable;
import com.lukflug.panelstudio.component.IComponent;

/**
 * Base class for containers.
 * @author lukflug
 * @param <T> the type of components that are members of this container
 */
public class Container<T extends IComponent> implements IComponent,IComponentManager {
	protected List<ComponentState> components=new ArrayList<ComponentState>();
	protected String title;
	private boolean lastVisible=false;

	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public void render(Context context) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleButton(Context context, int button) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleKey(Context context, int scancode) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleScroll(Context context, int diff) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void getHeight(Context context) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enter() {
		lastVisible=true;
	}

	@Override
	public void exit() {
		lastVisible=false;
	}

	@Override
	public void releaseFocus() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isVisible() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean lastVisible() {
		return lastVisible;
	}

	@Override
	public IToggleable getComponentToggleable(IComponent component) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void disposeComponent(IComponent component) {
		ComponentState state=components.get(component);
		if (state.transientVisibility.isOn()) state.transientVisibility.toggle();
		components.remove(component);
	}
	
	
	/**
	 * Class for the visibility state of a component.
	 * @author lukflug
	 */
	protected class ComponentState {
		/**
		 * The component.
		 */
		protected final IComponent component;
		/**
		 * The visibility defined by thing outside the component.
		 */
		protected final IBoolean externalVisibility;
		/**
		 * The visibility defined via {@link IComponentManager}.
		 */
		protected final IToggleable transientVisibility;
		
		/**
		 * Constructor.
		 * @param component the component
		 * @param externalVisibility the external visibility
		 * @param visible initial transient visbility
		 */
		public ComponentState (IComponent component, IBoolean externalVisibility, boolean visible) {
			this.component=component;
			this.externalVisibility=externalVisibility;
			transientVisibility=new SimpleToggleable(visible) {
				@Override
				public void toggle() {
					super.toggle();
					update();
				}
			};
			update();
		}
		
		public void update() {
			if (component.isVisible()&&externalVisibility.isOn()&&transientVisibility.isOn()!=component.lastVisible()) {
				if (component.lastVisible()) component.exit();
				else component.enter();
			}
		}
	}
}
