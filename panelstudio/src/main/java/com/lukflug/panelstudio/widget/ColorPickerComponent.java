package com.lukflug.panelstudio.widget;

import com.lukflug.panelstudio.setting.IColorSetting;
import com.lukflug.panelstudio.theme.ThemeTuple;

public class ColorPickerComponent extends ColorComponent {
	public ColorPickerComponent (IColorSetting setting, ThemeTuple theme) {
		super(setting,theme);
	}

	@Override
	public void populate (ThemeTuple theme) {
		addComponent(new ColorPicker(setting,theme.theme.getColorPickerRenderer()));
		addComponent(new NumberSlider(new ColorNumber(0,()->true),theme.getSliderRenderer(false)));
		addComponent(new NumberSlider(new ColorNumber(3,()->true),theme.getSliderRenderer(false)));
	}

}
