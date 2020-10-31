package com.lukflug.clickgui.settings;

import com.lukflug.clickgui.Context;
import com.lukflug.clickgui.FocusableComponent;
import com.lukflug.clickgui.Interface;
import com.lukflug.clickgui.theme.Renderer;

/**
 * Component representing an enumeration-valued setting.
 * @author lukflug
 */
public class EnumComponent extends FocusableComponent {
	/**
	 * The setting in question.
	 */
	protected EnumSetting<?> setting;
	
	/**
	 * Constructor.
	 * @param title name of the setting
	 * @param renderer {@link Renderer} for the component.
	 * @param setting the setting in question
	 */
	public EnumComponent(String title, Renderer renderer, EnumSetting<?> setting) {
		super(title,renderer);
		this.setting=setting;
	}

	/**
	 * Renders the component, by drawing a title bar containing the name and current value of the setting.
	 */
	@Override
	public void render (Context context) {
		super.render(context);
		renderer.renderTitle(context,title+": \u00A77"+setting.getValue().name(),hasFocus(context));
	}
	
	/**
	 * Cycles through the values of the enumeration when clicked.
	 */
	@Override
	public void handleButton (Context context, int button) {
		super.handleButton(context,button);
		if (button==Interface.LBUTTON && context.isClicked()) {
			setting.increment();
		}
	}
}
