package com.lukflug.panelstudio;

import java.util.ArrayList;
import java.util.List;

import com.lukflug.panelstudio.theme.Renderer;

/**
 * Base class for components containing other components (i.e. containers).
 * @author lukflug
 */
public class Container extends FocusableComponent {
	/**
	 * List of child component.
	 */
	protected List<Component> components;
	
	/**
	 * Constructor for a container.
	 * @param title the caption of the container
	 * @param renderer the renderer used by the container
	 */
	public Container (String title, String description, Renderer renderer) {
		super(title,description,renderer);
		components=new ArrayList<Component>();
	}
	
	/**
	 * Add a component to the container.
	 * @param component the component to be added
	 */
	public void addComponent (Component component) {
		components.add(component);
	}
	
	/**
	 * Render the container.
	 * Components are rendered in a column based on the height they specify via {@link Context#setHeight(int)}.
	 * The horizontal border is defined by {@link Renderer#getBorder()}.
	 * The vertical space between to components is defined by {@link Renderer#getOffset()}. 
	 */
	@Override
	public void render (Context context) {
		String description=null;
		int posy=renderer.getOffset();
		for (Component component: components) {
			Context subContext=getSubContext(context,posy);
			component.render(subContext);
			if (subContext.isHovered() && subContext.getDescription()!=null) description=subContext.getDescription();
			posy+=subContext.getSize().height+renderer.getOffset();
		}
		if (description==null) description=this.description;
		context.setDescription(description);
		context.setHeight(posy);
	}

	/**
	 * Handle a mouse button state change.
	 */
	@Override
	public void handleButton (Context context, int button) {
		int posy=renderer.getOffset();
		getHeight(context);
		updateFocus(context,button);
		for (Component component: components) {
			Context subContext=getSubContext(context,posy);
			component.handleButton(subContext,button);
			posy+=subContext.getSize().height+renderer.getOffset();
		}
		context.setHeight(posy);
	}

	/**
	 * Handle a key being typed.
	 */
	@Override
	public void handleKey (Context context, int scancode) {
		int posy=renderer.getOffset();
		for (Component component: components) {
			Context subContext=getSubContext(context,posy);
			component.handleKey(subContext,scancode);
			posy+=subContext.getSize().height+renderer.getOffset();
		}
		context.setHeight(posy);
	}

	/**
	 * Handle mouse wheel being scrolled.
	 */
	@Override
	public void handleScroll (Context context, int diff) {
		int posy=renderer.getOffset();
		for (Component component: components) {
			Context subContext=getSubContext(context,posy);
			component.handleKey(subContext,diff);
			posy+=subContext.getSize().height+renderer.getOffset();
		}
		context.setHeight(posy);
	}
	
	/**
	 * Returns the total height of the container, accounting for the height of its child components.
	 */
	@Override
	public void getHeight (Context context) {
		int posy=renderer.getOffset();
		for (Component component: components) {
			Context subContext=getSubContext(context,posy);
			component.getHeight(subContext);
			posy+=subContext.getSize().height+renderer.getOffset();
		}
		context.setHeight(posy);
	}

	/**
	 * Handle the GUI being closed.
	 */
	@Override
	public void exit (Context context) {
		int posy=renderer.getOffset();
		for (Component component: components) {
			Context subContext=getSubContext(context,posy);
			component.exit(subContext);
			posy+=subContext.getSize().height+renderer.getOffset();
		}
		context.setHeight(posy);
	}
	
	/**
	 * Reset focus state of self and children.
	 */
	@Override
	public void releaseFocus() {
		super.releaseFocus();
		for (Component component: components) {
			component.releaseFocus();
		}
	}
	
	/**
	 * Releases focus of children when called.
	 */
	protected void handleFocus (Context context, boolean focus) {
		if (!focus) releaseFocus();
	}
	
	/**
	 * Create sub-context for child component.
	 * @param context the current context
	 * @param posy the vertical position of the child component
	 * @return the context for the child component
	 */
	protected Context getSubContext (Context context, int posy) {
		return new Context(context,renderer.getBorder(),renderer.getBorder(),posy,hasFocus(context),true);
	}
}
