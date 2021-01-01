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
	 * Temporary storage for child description.
	 */
	private String tempDescription;
	
	/**
	 * Constructor for a container.
	 * @param title the caption of the container
	 * @param description the description for this component
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
		tempDescription=null;
		doComponentLoop(context,(subContext,component)->{
			component.render(subContext);
			if (subContext.isHovered() && subContext.getDescription()!=null) tempDescription=subContext.getDescription();
		});
		if (tempDescription==null) tempDescription=this.description;
		context.setDescription(tempDescription);
	}

	/**
	 * Handle a mouse button state change.
	 */
	@Override
	public void handleButton (Context context, int button) {
		getHeight(context);
		updateFocus(context,button);
		doComponentLoop(context,(subContext,component)->{
			component.handleButton(subContext,button);
			if (subContext.focusReleased()) context.releaseFocus();
		});
	}

	/**
	 * Handle a key being typed.
	 */
	@Override
	public void handleKey (Context context, int scancode) {
		doComponentLoop(context,(subContext,component)->component.handleKey(subContext,scancode));
	}

	/**
	 * Handle mouse wheel being scrolled.
	 */
	@Override
	public void handleScroll (Context context, int diff) {
		doComponentLoop(context,(subContext,component)->component.handleScroll(subContext,diff));
	}
	
	/**
	 * Returns the total height of the container, accounting for the height of its child components.
	 */
	@Override
	public void getHeight (Context context) {
		doComponentLoop(context,(subContext,component)->component.getHeight(subContext));
	}

	/**
	 * Handle the GUI being closed.
	 */
	@Override
	public void enter (Context context) {
		doComponentLoop(context,(subContext,component)->component.enter(subContext));
	}

	/**
	 * Handle the GUI being closed.
	 */
	@Override
	public void exit (Context context) {
		doComponentLoop(context,(subContext,component)->component.exit(subContext));
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
	
	/**
	 * Loop through all components in reverse order and check for focus requests.
	 * @param context for the container
	 * @param function the function to execute in the loop
	 */
	protected void doComponentLoop (Context context, LoopFunction function) {
		int posy=renderer.getOffset();
		for (Component component: components) {
			Context subContext=getSubContext(context,posy);
			function.loop(subContext,component);
			posy+=subContext.getSize().height+renderer.getOffset();
		}
		context.setHeight(posy);
	}
	
	
	/**
	 * Interface used by the loop.
	 * @author lukflug
	 */
	protected interface LoopFunction {
		/**
		 * Function to execute in the loop.
		 * @param context the context for the component
		 * @param component the component
		 */
		public void loop (Context context, Component component);
	}
}
