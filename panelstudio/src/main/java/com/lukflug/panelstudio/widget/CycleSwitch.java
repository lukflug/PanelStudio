package com.lukflug.panelstudio.widget;

import com.lukflug.panelstudio.base.Context;
import com.lukflug.panelstudio.base.IInterface;
import com.lukflug.panelstudio.component.FocusableComponent;
import com.lukflug.panelstudio.setting.IEnumSetting;
import com.lukflug.panelstudio.theme.ISwitchRenderer;

public class CycleSwitch extends FocusableComponent {
	/**
	 * The setting in question.
	 */
	protected IEnumSetting setting;
	/**
	 * The renderer to be used.
	 */
	protected ISwitchRenderer<String> renderer;
	
	/**
	 * Constructor.
	 * @param setting the setting in question
	 * @param renderer the renderer for this component
	 */
	public CycleSwitch (IEnumSetting setting, ISwitchRenderer<String> renderer) {
		super(setting);
		this.setting=setting;
		this.renderer=renderer;
	}

	/**
	 * Renders the component, by drawing a title bar containing the name and current value of the setting.
	 */
	@Override
	public void render (Context context) {
		super.render(context);
		renderer.renderButton(context,getTitle(),isVisible(),setting.getValueName());
	}
	
	/**
	 * Cycles through the values of the enumeration when clicked.
	 */
	@Override
	public void handleButton (Context context, int button) {
		super.handleButton(context,button);
		if (button==IInterface.LBUTTON && context.isClicked()) {
			if (renderer.getOnField(context).contains(context.getInterface().getMouse())) setting.increment();
			else if (renderer.getOffField(context).contains(context.getInterface().getMouse())) setting.decrement();
		}
	}

	@Override
	protected int getHeight() {
		return renderer.getDefaultHeight();
	}
}
