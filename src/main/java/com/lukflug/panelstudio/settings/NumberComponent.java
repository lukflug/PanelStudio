package com.lukflug.panelstudio.settings;

import com.lukflug.panelstudio.Context;
import com.lukflug.panelstudio.Slider;
import com.lukflug.panelstudio.theme.Renderer;

/**
 * Component that represents a number-valued setting through a {@link Slider}.
 * @author lukflug
 */
public class NumberComponent extends Slider {
	/**
	 * The setting in question.
	 */
	protected NumberSetting<?> setting;
	/**
	 * The minimum possible value for the setting.
	 */
	protected double min;
	/**
	 * The maximum possible value for the setting.
	 */
	protected double max;
	/**
	 * The name of the setting.
	 */
	protected String text;
	
	/**
	 * Constructor.
	 * @param text name of the setting
	 * @param renderer {@link Renderer} for the component.
	 * @param setting the setting in question
	 * @param min minimum value for the setting
	 * @param max maximum value for the setting
	 */
	public NumberComponent(String text, Renderer renderer, NumberSetting<?> setting, double min, double max) {
		super("",renderer);
		this.setting=setting;
		this.min=min;
		this.max=max;
		this.text=text;
	}
	
	/**
	 * Render the component, with the caption being composed of the setting name and current value.
	 */
	@Override
	public void render (Context context) {
		title=String.format("%s: \u00A77%.1f",text,setting.getValue().doubleValue());
		super.render(context);
	}

	/**
	 * Implementation of {@link Slider#getValue()}
	 */
	@Override
	protected double getValue() {
		return (setting.getValue().doubleValue()-min)/(max-min);
	}

	/**
	 * Implementation of {@link Slider#setValue(double)}
	 */
	@Override
	protected void setValue(double value) {
		setting.fromDouble(value*(max-min)+min);
	}
}
