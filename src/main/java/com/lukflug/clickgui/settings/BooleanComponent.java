package com.lukflug.clickgui.settings;

import com.lukflug.clickgui.Context;
import com.lukflug.clickgui.FocusableComponent;
import com.lukflug.clickgui.Interface;
import com.lukflug.clickgui.theme.Renderer;

/**
 * Component representing a boolean-valued setting.
 * @author lukflug
 */
public class BooleanComponent extends FocusableComponent {
	/**
	 * The setting in question.
	 */
	protected Setting<Boolean> setting;
	
	/**
	 * Constructor.
	 * @param title name of the setting
	 * @param renderer {@link Renderer} for the component.
	 * @param setting the setting in question
	 */
	public BooleanComponent(String title, Renderer renderer, Setting<Boolean> setting) {
		super(title,renderer);
		this.setting=setting;
	}
	
	/**
	 * Render the component, by drawing an active box, if the boolean is true, and an inactive one, if the boolean is false. 
	 */
	@Override
	public void render (Context context) {
		super.render(context);
		renderer.renderTitle(context,title,hasFocus(context),setting.getValue());
	}
	
	/**
	 * Handle a mouse button state change.
	 * Inverts the boolean, if clicked.
	 */
	@Override
	public void handleButton (Context context, int button) {
		super.handleButton(context,button);
		if (button==Interface.LBUTTON && context.isClicked()) {
			setting.setValue(!setting.getValue());
		}
	}
}
