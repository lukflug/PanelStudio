package com.lukflug.panelstudio.widget;

import com.lukflug.panelstudio.setting.INumberSetting;
import com.lukflug.panelstudio.theme.ISliderRenderer;

/**
 * Component that represents a number-valued setting through a {@link Slider}.
 * @author lukflug
 */
public class NumberSlider extends Slider {
	/**
	 * The setting in question.
	 */
	protected INumberSetting setting;
	
	/**
	 * Constructor.
	 * @param setting the setting in question
	 * @param renderer the renderer for the component
	 */
	public NumberSlider (INumberSetting setting, ISliderRenderer renderer) {
		super(setting,renderer);
		this.setting=setting;
	}

	@Override
	protected double getValue() {
		return (setting.getNumber()-setting.getMinimumValue())/(setting.getMaximumValue()-setting.getMinimumValue());
	}

	@Override
	protected void setValue(double value) {
		setting.setNumber(value*(setting.getMaximumValue()-setting.getMinimumValue())+setting.getMinimumValue());
	}

	@Override
	protected String getDisplayState() {
		return setting.getSettingState();
	}
}
