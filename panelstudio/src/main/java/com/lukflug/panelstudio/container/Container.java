package com.lukflug.panelstudio.container;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import com.lukflug.panelstudio.base.Context;
import com.lukflug.panelstudio.base.IBoolean;
import com.lukflug.panelstudio.component.ComponentBase;
import com.lukflug.panelstudio.component.IComponent;
import com.lukflug.panelstudio.theme.ContainerRenderer;

/**
 * Base class for containers.
 * @author lukflug
 * @param <T> the type of components that are members of this container
 */
public abstract class Container<T extends IComponent> extends ComponentBase {
	/**
	 * List of components.
	 */
	protected List<ComponentState> components=new ArrayList<ComponentState>();
	/**
	 * The renderer to use.
	 */
	protected ContainerRenderer renderer;
	
	/**
	 * Constructor.
	 * @param title the caption for this component
	 * @param description the description for this component
	 * @param visible whether this component is visible
	 * @param renderer the renderer for this container
	 */
	public Container(String title, String description, IBoolean visible, ContainerRenderer renderer) {
		super(title,description,visible);
		this.renderer=renderer;
	}
	
	/**
	 * Add component to GUI.
	 * @param component the component to be added
	 */
	public void addComponent (T component) {
		if (getComponentState(component)==null) {
			components.add(new ComponentState(component,getDefaultVisibility()));
		}
	}
	
	/**
	 * Remove component from GUI.
	 * @param component the component to be removed
	 */
	public void removeComponent (T component) {
		ComponentState state=getComponentState(component);
		if (state!=null) {
			components.remove(state);
		}
	}
	
	@Override
	public void render (Context context) {
		String tempDescription[]={null};
		doContextSensitiveLoop(context,(subContext,component)->{
			component.render(subContext);
			if (subContext.isHovered() && subContext.getDescription()!=null) tempDescription[0]=subContext.getDescription();
		});
		if (tempDescription[0]==null) tempDescription[0]=description;
		context.setDescription(tempDescription[0]);
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
	public void handleScroll (Context context, int diff) {
		doContextSensitiveLoop(context,(subContext,component)->component.handleScroll(subContext,diff));
	}
	
	@Override
	public void getHeight (Context context) {
		doContextSensitiveLoop(context,(subContext,component)->component.getHeight(subContext));
	}

	@Override
	public void enter() {
		super.enter();
		doContextlessLoop(IComponent::enter);
	}

	@Override
	public void exit() {
		super.exit();
		doContextlessLoop(IComponent::exit);
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
		for (ComponentState state: this.components) {
			components.add(state);
		}
		for (ComponentState state: components) {
			state.update();
			if (state.component.lastVisible()) function.accept(state.component);
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
		return ()->lastVisible();
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
		 * Constructor.
		 * @param component the component
		 * @param externalVisibility the external visibility
		 */
		public ComponentState (T component, IBoolean externalVisibility) {
			this.component=component;
			this.externalVisibility=externalVisibility;
			update();
		}
		
		public void update() {
			if (component.isVisible()&&externalVisibility.isOn()!=component.lastVisible()) {
				if (component.lastVisible()) component.exit();
				else component.enter();
			}
		}
	}
	
	
	/**
	 * Version of a component consumer that also takes in a context.
	 * @author lukflug
	 * @param <T> the type of component
	 */
	protected interface ContextSensitiveConsumer<T extends IComponent> {
		/**
		 * Accept the context and component.
		 */
		public void accept (Context context, T component);
	}
}
