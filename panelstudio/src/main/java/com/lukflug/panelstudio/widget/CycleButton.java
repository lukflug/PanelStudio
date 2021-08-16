package com.lukflug.panelstudio.widget;

import com.lukflug.panelstudio.base.Context;
import com.lukflug.panelstudio.base.IInterface;
import com.lukflug.panelstudio.component.FocusableComponent;
import com.lukflug.panelstudio.setting.IEnumSetting;
import com.lukflug.panelstudio.theme.IButtonRenderer;

/**
 * Component representing an enumeration-valued setting which cycles.
 * @author lukflug
 */
public class CycleButton extends FocusableComponent {
	/**
	 * The setting in question.
	 */
	protected IEnumSetting setting;
	/**
	 * The renderer to be used.
	 */
	protected IButtonRenderer<String> renderer;
	
	/**
	 * Constructor.
	 * @param setting the setting in question
	 * @param renderer the renderer for this component
	 */
	public CycleButton (IEnumSetting setting, IButtonRenderer<String> renderer) {
		super(setting);
		this.setting=setting;
		this.renderer=renderer;
	}

	@Override
	public void render (Context context) {
		super.render(context);
		renderer.renderButton(context,getTitle(),hasFocus(context),setting.getValueName());
	}
	
	@Override
	public void handleButton (Context context, int button) {
		super.handleButton(context,button);
		if (button==IInterface.LBUTTON && context.isClicked(button)) {
			setting.increment();
		}
	}

	@Override
	protected int getHeight() {
		return renderer.getDefaultHeight();
	}
}
