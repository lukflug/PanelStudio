package com.lukflug.clickgui;

import com.lukflug.clickgui.settings.Toggleable;
import com.lukflug.clickgui.theme.Renderer;

/**
 * Container that can be closed, so that its child component.
 * @author lukflug
 */
public class CollapsibleContainer extends Container {
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
		this.open=open;
	}
	
	/**
	 * Renders a background, title bar, border and, if the container is open, the components of the container.
	 */
	@Override
	public void render (Context context) {
		getHeight(context);
		renderer.renderBackground(context,hasFocus(context));
		context.setHeight(renderer.getHeight());
		renderer.renderTitle(context,title,hasFocus(context),isActive(),open.isRunning());
		if (open.isRunning()) {
			super.render(context);
		}
		renderer.renderBorder(context,hasFocus(context),isActive(),open.isRunning());
	}
	
	/**
	 * Handles a mouse button state change.
	 */
	@Override
	public void handleButton (Context context, int button) {
		if (open.isRunning()) super.handleButton(context,button);
		else {
			context.setHeight(renderer.getHeight());
			updateFocus(context,button);
		}
		if (context.isHovered() && context.getInterface().getMouse().y<=context.getPos().y+renderer.getHeight() && button==Interface.RBUTTON && context.getInterface().getButton(Interface.RBUTTON)) {
			open.toggle();
		}
	}
	
	/**
	 * Handle a key being typed.
	 */
	@Override
	public void handleKey (Context context, int scancode) {
		if (open.isRunning()) super.handleKey(context,scancode);
		else context.setHeight(renderer.getHeight());
	}
	
	/**
	 * Returns the current height, accounting for whether the container is open or closed.
	 */
	@Override
	public void getHeight (Context context) {
		if (open.isRunning()) super.getHeight(context);
		else context.setHeight(renderer.getHeight());
	}
	
	/**
	 * Handle the GUI being closed.
	 */
	@Override
	public void exit (Context context) {
		if (open.isRunning()) super.exit(context);
		else context.setHeight(renderer.getHeight());
	}
	
	protected boolean isActive() {
		return true;
	}
}
