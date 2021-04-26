package com.lukflug.panelstudio.container;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import com.lukflug.panelstudio.base.Context;
import com.lukflug.panelstudio.base.Description;
import com.lukflug.panelstudio.base.IBoolean;
import com.lukflug.panelstudio.component.ComponentBase;
import com.lukflug.panelstudio.component.IComponent;
import com.lukflug.panelstudio.setting.ILabeled;
import com.lukflug.panelstudio.theme.IContainerRenderer;

/**
 * Base class for containers.
 * @author lukflug
 * @param <T> the type of components that are members of this container
 */
public abstract class Container<T extends IComponent> extends ComponentBase implements IContainer<T> {
	/**
	 * List of components.
	 */
	protected List<ComponentState> components=new ArrayList<ComponentState>();
	/**
	 * The renderer to use.
	 */
	protected IContainerRenderer renderer;
	/**
	 * The container visiblity.
	 */
	private boolean visible;
	
	/**
	 * Constructor.
	 * @param label the label for the component
	 * @param renderer the renderer for this container
	 */
	public Container(ILabeled label, IContainerRenderer renderer) {
		super(label);
		this.renderer=renderer;
	}
	
	@Override
	public boolean addComponent (T component) {
		if (getComponentState(component)==null) {
			components.add(new ComponentState(component,getDefaultVisibility()));
			return true;
		}
		return false;
	}
	
	@Override
	public boolean addComponent (T component, IBoolean visible) {
		if (getComponentState(component)==null) {
			components.add(new ComponentState(component,visible));
			return true;
		}
		return false;
	}
	
	@Override
	public boolean removeComponent (T component) {
		ComponentState state=getComponentState(component);
		if (state!=null) {
			components.remove(state);
			if (state.lastVisible) state.component.exit();
			return true;
		}
		return false;
	}
	
	@Override
	public void render (Context context) {
		getHeight(context);
		if (renderer!=null) renderer.renderBackground(context,context.hasFocus());
		doContextSensitiveLoop(context,(subContext,component)->{
			component.render(subContext);
			if (subContext.isHovered() && subContext.getDescription()!=null) context.setDescription(new Description(subContext.getDescription(),subContext.getRect()));
		});
		if (context.getDescription()==null && description!=null) context.setDescription(new Description(context.getRect(),description));
	}

	@Override
	public void handleButton (Context context, int button) {
		doContextSensitiveLoop(context,(subContext,component)->{
			component.handleButton(subContext,button);
		});
	}

	@Override
	public void handleKey (Context context, int scancode) {
		doContextSensitiveLoop(context,(subContext,component)->component.handleKey(subContext,scancode));
	}
	
	@Override
	public void handleChar (Context context, char character) {
		doContextSensitiveLoop(context,(subContext,component)->component.handleChar(subContext,character));
	}

	@Override
	public void handleScroll (Context context, int diff) {
		doContextSensitiveLoop(context,(subContext,component)->component.handleScroll(subContext,diff));
	}
	
	@Override
	public void getHeight (Context context) {
		doContextSensitiveLoop(context,(subContext,component)->component.getHeight(subContext));
	}

	@Override
	public void enter() {
		visible=true;
		doContextlessLoop(component->{});
	}

	@Override
	public void exit() {
		visible=false;
		doContextlessLoop(component->{});
	}

	@Override
	public void releaseFocus() {
		doContextlessLoop(IComponent::releaseFocus);
	}
	
	@Override
	protected int getHeight() {
		return 0;
	}
	
	/**
	 * Find component state.
	 * @param component the component
	 * @return component state corresponding to component
	 */
	protected ComponentState getComponentState (T component) {
		for (ComponentState state: components) {
			if (state.component==component) return state;
		}
		return null;
	}
	
	/**
	 * Do loop through components, which does not involve a context.
	 * @param function the payload function to execute
	 */
	protected void doContextlessLoop (Consumer<T> function) {
		List<ComponentState> components=new ArrayList<ComponentState>();
		for (ComponentState state: this.components) components.add(state);
		for (ComponentState state: components) state.update();
		for (ComponentState state: components) {
			if (state.lastVisible()) function.accept(state.component);
		}
	}
	
	/**
	 * Do loop through components with a certain context.
	 * @param context the context to use
	 * @param function the payload function to execute
	 */
	protected abstract void doContextSensitiveLoop (Context context, ContextSensitiveConsumer<T> function);
	
	/**
	 * Get the default external visibility boolean.
	 * @return the visibility boolean
	 */
	protected IBoolean getDefaultVisibility() {
		return ()->true;
	}
	
	
	/**
	 * Class for the visibility state of a component.
	 * @author lukflug
	 */
	protected final class ComponentState {
		/**
		 * The component.
		 */
		public final T component;
		/**
		 * The visibility defined by thing outside the component.
		 */
		public final IBoolean externalVisibility;
		/**
		 * Last visibility state.
		 */
		private boolean lastVisible=false;
		
		/**
		 * Constructor.
		 * @param component the component
		 * @param externalVisibility the external visibility
		 */
		public ComponentState (T component, IBoolean externalVisibility) {
			this.component=component;
			this.externalVisibility=externalVisibility;
			update();
		}
		
		/**
		 * Updates the visibility state of the component.
		 */
		public void update() {
			if ((component.isVisible()&&externalVisibility.isOn()&&visible)!=lastVisible) {
				if (lastVisible) {
					lastVisible=false;
					component.exit();
				} else {
					lastVisible=true;
					component.enter();
				}
			}
		}
		
		/**
		 * Get last visibility state.
		 * @return the last visiblity state
		 */
		public boolean lastVisible() {
			return lastVisible;
		}
	}
	
	
	/**
	 * Version of a component consumer that also takes in a context.
	 * @author lukflug
	 * @param <T> the type of component
	 */
	@FunctionalInterface
	protected interface ContextSensitiveConsumer<T extends IComponent> {
		/**
		 * Accept the context and component.
		 */
		public void accept (Context context, T component);
	}
}
