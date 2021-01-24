package com.lukflug.panelstudio.settings;

import com.lukflug.panelstudio.Context;
import com.lukflug.panelstudio.FocusableComponent;
import com.lukflug.panelstudio.IInterface;
import com.lukflug.panelstudio.theme.IRenderer;

/**
 * Component representing a boolean-valued setting.
 * @author lukflug
 */
public class BooleanComponent extends FocusableComponent {
	/**
	 * The setting in question.
	 */
	protected IToggleable setting;
	
	/**
	 * Constructor.
	 * @param title name of the setting
	 * @param description the description for this component
	 * @param renderer {@link IRenderer} for the component
	 * @param setting the setting in question
	 */
	public BooleanComponent(String title, String description, IRenderer renderer, IToggleable setting) {
		super(title,description,renderer);
		this.setting=setting;
	}
	
	/**
	 * Render the component, by drawing an active box, if the boolean is true, and an inactive one, if the boolean is false. 
	 */
	@Override
	public void render (Context context) {
		super.render(context);
		renderer.renderTitle(context,title,hasFocus(context),setting.isOn());
	}
	
	/**
	 * Handle a mouse button state change.
	 * Inverts the boolean, if clicked.
	 */
	@Override
	public void handleButton (Context context, int button) {
		super.handleButton(context,button);
		if (button==IInterface.LBUTTON && context.isClicked()) {
			setting.toggle();
		}
	}
}
