package com.lukflug.panelstudio.settings;

import com.lukflug.panelstudio.Animation;
import com.lukflug.panelstudio.CollapsibleContainer;
import com.lukflug.panelstudio.Context;
import com.lukflug.panelstudio.Interface;
import com.lukflug.panelstudio.theme.Renderer;

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
	 * @param animation the animation for opening and closing the container
	 * @param toggle the {@link Toggleable} to be toggled by the user
	 */
	public ToggleableContainer(String title, Renderer renderer, Toggleable open, Animation animation, Toggleable toggle) {
		super(title,renderer,open,animation);
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
		return toggle.isOn();
	}
}
