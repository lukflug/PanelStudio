package com.lukflug.panelstudio.widget;

import com.lukflug.panelstudio.base.Context;
import com.lukflug.panelstudio.base.IInterface;
import com.lukflug.panelstudio.component.FocusableComponent;
import com.lukflug.panelstudio.setting.IEnumSetting;
import com.lukflug.panelstudio.theme.IRenderer;

/**
 * Component representing an enumeration-valued setting.
 * @author lukflug
 */
public class EnumComponent extends FocusableComponent {
	/**
	 * The setting in question.
	 */
	protected IEnumSetting setting;
	
	/**
	 * Constructor.
	 * @param title name of the setting
	 * @param description the description for this component
	 * @param renderer {@link IRenderer} for the component
	 * @param setting the setting in question
	 */
	public EnumComponent(String title, String description, IRenderer renderer, IEnumSetting setting) {
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
		if (button==IInterface.LBUTTON && context.isClicked()) {
			setting.increment();
		}
	}
}
