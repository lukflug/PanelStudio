package com.lukflug.panelstudio.settings;

import com.lukflug.panelstudio.Context;
import com.lukflug.panelstudio.FocusableComponent;
import com.lukflug.panelstudio.Interface;
import com.lukflug.panelstudio.theme.Renderer;

/**
 * Component representing an enumeration-valued setting.
 * @author lukflug
 */
public class EnumComponent extends FocusableComponent {
	/**
	 * The setting in question.
	 */
	protected EnumSetting setting;
	
	/**
	 * Constructor.
	 * @param title name of the setting
	 * @param description the description for this component
	 * @param renderer {@link Renderer} for the component
	 * @param setting the setting in question
	 */
	public EnumComponent(String title, String description, Renderer renderer, EnumSetting setting) {
		super(title,description,renderer);
		this.setting=setting;
	}

	/**
	 * Renders the component, by drawing a title bar containing the name and current value of the setting.
	 */
	@Override
	public void render (Context context) {
		super.render(context);
		renderer.renderTitle(context,title+": \u00A77"+setting.getValueName(),hasFocus(context));
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
