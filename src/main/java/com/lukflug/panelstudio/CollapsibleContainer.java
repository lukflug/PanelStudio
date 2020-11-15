package com.lukflug.panelstudio;

import com.lukflug.panelstudio.settings.Toggleable;
import com.lukflug.panelstudio.theme.Renderer;

/**
 * Container that can be closed and scrolled, so that its children can be hidden.
 * @author lukflug
 */
public class CollapsibleContainer extends FocusableComponent {
	/**
	 * {@link Container} containing the children.
	 */
	protected Container container;
	/**
	 * {@link Toggleable} indicating whether the container is open or closed. 
	 */
	public Toggleable open;
	
	/**
	 * Constructor.
	 * @param title the caption for the container
	 * @param renderer the {@link Renderer} for the container
	 * @param open the {@link Toggleable} for {@link #open}
	 */
	public CollapsibleContainer (String title, Renderer renderer, Toggleable open) {
		super(title,renderer);
		container=new Container(title,renderer);
		this.open=open;
	}
	
	/**
	 * Add a component to the container.
	 * @param component the component to be added
	 */
	public void addComponent (Component component) {
		container.addComponent(component);
	}
	
	/**
	 * Renders a background, title bar, border and, if the container is open, the components of the container.
	 */
	@Override
	public void render (Context context) {
		getHeight(context);
		renderer.renderBackground(context,hasFocus(context));
		super.render(context);
		renderer.renderTitle(context,title,hasFocus(context),isActive(),open.isOn());
		if (open.isOn()) {
			Context subContext=new Context(context,0,renderer.getHeight(),hasFocus(context));
			container.render(subContext);
			context.setHeight(subContext.getSize().height+renderer.getHeight());
		}
		renderer.renderBorder(context,hasFocus(context),isActive(),open.isOn());
	}
	
	/**
	 * Handles a mouse button state change.
	 */
	@Override
	public void handleButton (Context context, int button) {
		if (open.isOn()) {
			Context subContext=new Context(context,0,renderer.getHeight(),hasFocus(context));
			container.handleButton(subContext,button);
			context.setHeight(subContext.getSize().height+renderer.getHeight());
		} else super.handleButton(context,button);
		if (context.isHovered() && context.getInterface().getMouse().y<=context.getPos().y+renderer.getHeight() && button==Interface.RBUTTON && context.getInterface().getButton(Interface.RBUTTON)) {
			open.toggle();
		}
	}
	
	/**
	 * Handle a key being typed.
	 */
	@Override
	public void handleKey (Context context, int scancode) {
		if (open.isOn()) {
			Context subContext=new Context(context,0,renderer.getHeight(),hasFocus(context));
			container.handleKey(subContext,scancode);
			context.setHeight(subContext.getSize().height+renderer.getHeight());
		} else super.handleKey(context,scancode);
	}
	
	/**
	 * Returns the current height, accounting for whether the container is open or closed.
	 */
	@Override
	public void getHeight (Context context) {
		if (open.isOn()) {
			Context subContext=new Context(context,0,renderer.getHeight(),hasFocus(context));
			container.getHeight(subContext);
			context.setHeight(subContext.getSize().height+renderer.getHeight());
		} else super.getHeight(context);
	}
	
	/**
	 * Handle the GUI being closed.
	 */
	@Override
	public void exit (Context context) {
		if (open.isOn()) {
			Context subContext=new Context(context,0,renderer.getHeight(),hasFocus(context));
			container.exit(subContext);
			context.setHeight(subContext.getSize().height+renderer.getHeight());
		} else super.exit(context);
	}
	
	/**
	 * Method to determine whether title bar is active or not.
	 * @return set to true, if title bar is active
	 */
	protected boolean isActive() {
		return true;
	}
}
