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
	public Container (String title, Renderer renderer) {
		super(title,renderer);
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
	 * Vertical space is reserved for the container itself at the top based on {@link Renderer#getHeight()}.
	 * The horizontal border is defined by {@link Renderer#getBorder()}.
	 * The vertical space between to components is defined by {@link Renderer#getOffset()}. 
	 */
	@Override
	public void render (Context context) {
		int posy=renderer.getHeight()+renderer.getOffset();
		for (Component component: components) {
			Context subContext=new Context(context,renderer.getBorder(),posy,hasFocus(context));
			component.render(subContext);
			posy+=subContext.getSize().height+renderer.getOffset();
		}
		context.setHeight(posy);
	}

	/**
	 * Handle a mouse button state change.
	 */
	@Override
	public void handleButton (Context context, int button) {
		int posy=renderer.getHeight()+renderer.getOffset();
		for (Component component: components) {
			Context subContext=new Context(context,renderer.getBorder(),posy,hasFocus(context));
			component.handleButton(subContext,button);
			posy+=subContext.getSize().height+renderer.getOffset();
		}
		context.setHeight(posy);
		updateFocus(context,button);
	}

	/**
	 * Handle a key being typed.
	 */
	@Override
	public void handleKey (Context context, int scancode) {
		int posy=renderer.getHeight()+renderer.getOffset();
		for (Component component: components) {
			Context subContext=new Context(context,renderer.getBorder(),posy,hasFocus(context));
			component.handleKey(subContext,scancode);
			posy+=subContext.getSize().height+renderer.getOffset();
		}
		context.setHeight(posy);
	}
	
	/**
	 * Returns the total height of the container, accounting for the height of its child components.
	 */
	@Override
	public void getHeight (Context context) {
		int posy=renderer.getHeight()+renderer.getOffset();
		for (Component component: components) {
			Context subContext=new Context(context,renderer.getBorder(),posy,hasFocus(context));
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
		int posy=renderer.getHeight()+renderer.getOffset();
		for (Component component: components) {
			Context subContext=new Context(context,renderer.getBorder(),posy,hasFocus(context));
			component.exit(subContext);
			posy+=subContext.getSize().height+renderer.getOffset();
		}
		context.setHeight(posy);
	}
}
