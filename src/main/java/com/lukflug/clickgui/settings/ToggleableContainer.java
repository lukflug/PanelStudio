package com.lukflug.clickgui.settings;

import com.lukflug.clickgui.CollapsibleContainer;
import com.lukflug.clickgui.Context;
import com.lukflug.clickgui.Interface;
import com.lukflug.clickgui.theme.Renderer;

/**
 * A {@link CollapsibleContainer} that can be toggled by the user.
 * @author lukflug
 */
public class ToggleableContainer extends CollapsibleContainer {
	/**
	 * {@link Toggleable} that can be toggled by the user.
	 */
	protected Toggleable toggle;
	
	/**
	 * Constructor.
	 * @param title caption of the container
	 * @param renderer the {@link Renderer} for the container
	 * @param open the {@link Toggleable} indicating whether the container is open or closed
	 * @param toggle the {@link Toggleable} to be toggled by the user
	 */
	public ToggleableContainer(String title, Renderer renderer, Toggleable open, Toggleable toggle) {
		super(title,renderer,open);
		this.toggle=toggle;
	}
	
	/**
	 * Switches the toggle when clicked.
	 */
	@Override
	public void handleButton (Context context, int button) {
		context.setHeight(renderer.getHeight());
		if (context.isClicked() && context.getInterface().getMouse().y<=context.getPos().y+context.getSize().height && button==Interface.LBUTTON) {
			toggle.toggle();
		}
		super.handleButton(context,button);
	}

	/**
	 * The title bar for the container is active, depending on the state of the toggle.
	 */
	@Override
	protected boolean isActive() {
		return toggle.isRunning();
	}
}
