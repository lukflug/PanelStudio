package com.lukflug.examplemod8forge.setting;

import java.awt.Color;

import com.lukflug.examplemod8forge.module.ClickGUIModule;
import com.lukflug.examplemod8forge.module.ClickGUIModule.ColorModel;
import com.lukflug.panelstudio.base.IBoolean;
import com.lukflug.panelstudio.setting.IColorSetting;

public class ColorSetting extends Setting<Color> implements IColorSetting {
	public final boolean hasAlpha,allowsRainbow;
	private boolean rainbow;
	
	public ColorSetting (String displayName, String configName, String description, IBoolean visible, boolean hasAlpha, boolean allowsRainbow, Color value, boolean rainbow) {
		super(displayName,configName,description,visible,value);
		this.hasAlpha=hasAlpha;
		this.allowsRainbow=allowsRainbow;
		this.rainbow=rainbow;
	}
	
	@Override
	public Color getValue() {
		if (rainbow) {
			int speed=ClickGUIModule.rainbowSpeed.getValue();
			Color color=Color.getHSBColor((System.currentTimeMillis()%(360*speed))/(float)(360*speed),1,1);
			return new Color(color.getRed(),color.getGreen(),color.getBlue(),super.getValue().getAlpha());
		}
		else return super.getValue();
	}

	@Override
	public Color getColor() {
		return super.getValue();
	}

	@Override
	public boolean getRainbow() {
		return rainbow;
	}

	@Override
	public void setRainbow (boolean rainbow) {
		this.rainbow=rainbow;
	}
	
	@Override
	public boolean hasAlpha() {
		return hasAlpha;
	}
	
	@Override
	public boolean allowsRainbow() {
		return allowsRainbow;
	}
	
	@Override
	public boolean hasHSBModel() {
		return ClickGUIModule.colorModel.getValue()==ColorModel.HSB;
	}
}
