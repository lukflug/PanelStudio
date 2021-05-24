package com.lukflug.panelstudio.widget;

import com.lukflug.panelstudio.component.IComponent;
import com.lukflug.panelstudio.setting.IBooleanSetting;
import com.lukflug.panelstudio.setting.IColorSetting;
import com.lukflug.panelstudio.setting.INumberSetting;
import com.lukflug.panelstudio.theme.ThemeTuple;

public class ColorSliderComponent extends ColorComponent {
	public ColorSliderComponent (IColorSetting setting, ThemeTuple theme) {
		super(setting, theme);
	}

	@Override
	public void populate (ThemeTuple theme) {
		addComponent(getRainbowComponent(theme,new RainbowToggle()));
		addComponent(getColorComponent(theme,0,new ColorNumber(0,()->setting.hasHSBModel())));
		addComponent(getColorComponent(theme,1,new ColorNumber(1,()->setting.hasHSBModel())));
		addComponent(getColorComponent(theme,2,new ColorNumber(2,()->setting.hasHSBModel())));
		addComponent(getColorComponent(theme,3,new ColorNumber(3,()->setting.hasHSBModel())));
	}
	
	public IComponent getRainbowComponent (ThemeTuple theme, IBooleanSetting toggle) {
		return new ToggleButton(toggle,theme.getButtonRenderer(Boolean.class,false));
	}
	
	public IComponent getColorComponent (ThemeTuple theme, int value, INumberSetting number) {
		return new NumberSlider(number,theme.getSliderRenderer(false));
	}
}
