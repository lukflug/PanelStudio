package com.lukflug.panelstudio;

import com.lukflug.panelstudio.settings.IToggleable;
import com.lukflug.panelstudio.theme.IRenderer;

/**
 * A button that displays a component when clicked.
 * @author lukflug
 */
public class TransientComponent extends FocusableComponent {
	/**
	 * {@link IToggleable} that can be toggled by the user.
	 */
	protected IToggleable toggle;
	/**
	 * Component to be displayed when clicked.
	 */
	protected IFixedComponent component;
	/**
	 * Panel manager to be used.
	 */
	protected IPanelManager manager;
	
	/**
	 * Constructor.
	 * @param title the title for this component
	 * @param description the description for this component
	 * @param renderer the renderer for this component
	 * @param toggle the {@link IToggleable} to be used by the user
	 * @param component component to be opened when clicked
	 * @param manager the {@link IPanelManager} to be used by this component
	 */
	public TransientComponent (String title, String description, IRenderer renderer, IToggleable toggle, IFixedComponent component, IPanelManager manager) {
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
		if (button==IInterface.LBUTTON && context.isClicked()) {
			toggle.toggle();
		} else if (context.isHovered() && button==IInterface.RBUTTON && context.getInterface().getButton(IInterface.RBUTTON)) {
			manager.getComponentToggleable(component).toggle();
			context.releaseFocus();
		}
	}
}
