package com.lukflug.panelstudio;

import com.lukflug.panelstudio.settings.Toggleable;
import com.lukflug.panelstudio.theme.Renderer;

/**
 * A button that displays a component when clicked.
 * @author lukflug
 */
public class TransientComponent extends FocusableComponent {
	/**
	 * {@link Toggleable} that can be toggled by the user.
	 */
	protected Toggleable toggle;
	/**
	 * Component to be displayed when clicked.
	 */
	protected FixedComponent component;
	/**
	 * Panel manager to be used.
	 */
	protected PanelManager manager;
	
	/**
	 * Constructor.
	 * @param title the title for this component
	 * @param description the description for this component
	 * @param renderer the renderer for this component
	 * @param toggle the {@link Toggleable} to be used by the user
	 * @param component component to be opened when clicked
	 * @param manager the {@link PanelManager} to be used by this component
	 */
	public TransientComponent (String title, String description, Renderer renderer, Toggleable toggle, FixedComponent component, PanelManager manager) {
		super(title,description,renderer);
		this.toggle=toggle;
		this.component=component;
		this.manager=manager;
	}
	
	/**
	 * Render the component, by drawing an active box, if the boolean is true, and an inactive one, if the boolean is false. 
	 */
	@Override
	public void render (Context context) {
		super.render(context);
		renderer.renderTitle(context,title,hasFocus(context),toggle.isOn(),manager.getComponentToggleable(component).isOn());
	}
	
	/**
	 * Handle a mouse button state change.
	 * Inverts the boolean, if clicked.
	 */
	@Override
	public void handleButton (Context context, int button) {
		super.handleButton(context,button);
		if (button==Interface.LBUTTON && context.isClicked()) {
			toggle.toggle();
		} else if (context.isHovered() && button==Interface.RBUTTON && context.getInterface().getButton(Interface.RBUTTON)) {
			manager.getComponentToggleable(component).toggle();
			context.releaseFocus();
		}
	}
}
