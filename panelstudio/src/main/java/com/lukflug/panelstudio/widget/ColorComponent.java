package com.lukflug.panelstudio.widget;

import java.awt.Color;

import com.lukflug.panelstudio.base.Context;
import com.lukflug.panelstudio.base.IBoolean;
import com.lukflug.panelstudio.container.VerticalContainer;
import com.lukflug.panelstudio.setting.IBooleanSetting;
import com.lukflug.panelstudio.setting.IColorSetting;
import com.lukflug.panelstudio.setting.INumberSetting;
import com.lukflug.panelstudio.theme.ITheme;
import com.lukflug.panelstudio.theme.ThemeTuple;

/**
 * Component representing a color-valued setting.
 * @author lukflug
 */
public abstract class ColorComponent extends VerticalContainer {
	/**
	 * The setting in question.
	 */
	protected IColorSetting setting;
	/**
	 * The theme to use.
	 */
	protected ITheme theme;
	
	/**
	 * Constructor.
	 * @param setting the setting in question
	 * @param theme the theme to be used
	 */
	public ColorComponent (IColorSetting setting, ThemeTuple theme) {
		super(setting,theme.getContainerRenderer(false));
		this.setting=setting;
		this.theme=theme.theme;
		populate(new ThemeTuple(theme,0,1));
	}
	
	@Override
	public void render (Context context) {
		theme.overrideMainColor(setting.getValue());
		super.render(context);
		theme.restoreMainColor();
	}
	
	public abstract void populate (ThemeTuple theme);
	
	
	protected final class RainbowToggle implements IBooleanSetting {
		@Override
		public String getDisplayName() {
			return "Rainbow";
		}
		
		@Override
		public IBoolean isVisible() {
			return ()->setting.allowsRainbow();
		}
		
		@Override
		public boolean isOn() {
			return setting.getRainbow();
		}

		@Override
		public void toggle() {
			setting.setRainbow(!setting.getRainbow());
		}
	}
	
	
	protected final class ColorNumber implements INumberSetting {
		/**
		 * Number indicating the index of the component for the color model.
		 */
		private final int value;
		private final IBoolean model;
		
		public ColorNumber (int value, IBoolean model) {
			this.value=value;
			this.model=model;
		}
		
		@Override
		public String getDisplayName() {
			switch (value) {
			case 0:
				return (model.isOn()?"Hue":"Red");
			case 1:
				return (model.isOn()?"Saturation":"Green");
			case 2:
				return (model.isOn()?"Brightness":"Blue");
			case 3:
				return (model.isOn()?"Opacity":"Alpha");
			}
			return "";
		}
		
		@Override
		public IBoolean isVisible() {
			return ()->value!=3||setting.hasAlpha();
		}

		@Override
		public double getNumber() {
			Color c=setting.getColor();
			if (value<3) {
				if (model.isOn()) return Color.RGBtoHSB(c.getRed(),c.getGreen(),c.getBlue(),null)[value]*getMaximumValue();
				switch (value) {
				case 0:
					return c.getRed();
				case 1:
					return c.getGreen();
				case 2:
					return c.getBlue();
				}
			}
			return c.getAlpha()*getMaximumValue()/255;
		}

		@Override
		public void setNumber(double value) {
			Color c=setting.getColor();
			float[] color=Color.RGBtoHSB(c.getRed(),c.getGreen(),c.getBlue(),null);
			switch (this.value) {
			case 0:
				if (model.isOn()) c=Color.getHSBColor((float)value/360,color[1],color[2]);
				else c=new Color((int)Math.round(value),c.getGreen(),c.getBlue());
				if (setting.hasAlpha()) setting.setValue(new Color(c.getRed(),c.getGreen(),c.getBlue(),setting.getColor().getAlpha()));
				else setting.setValue(c);
				break;
			case 1:
				if (model.isOn()) c=Color.getHSBColor(color[0],(float)value/100,color[2]);
				else c=new Color(c.getRed(),(int)Math.round(value),c.getBlue());
				if (setting.hasAlpha()) setting.setValue(new Color(c.getRed(),c.getGreen(),c.getBlue(),setting.getColor().getAlpha()));
				else setting.setValue(c);
				break;
			case 2:
				if (model.isOn()) c=Color.getHSBColor(color[0],color[1],(float)value/100);
				else c=new Color(c.getRed(),c.getGreen(),(int)Math.round(value));
				if (setting.hasAlpha()) setting.setValue(new Color(c.getRed(),c.getGreen(),c.getBlue(),setting.getColor().getAlpha()));
				else setting.setValue(c);
				break;
			case 3:
				setting.setValue(new Color(c.getRed(),c.getGreen(),c.getBlue(),(int)Math.round(value/getMaximumValue()*255)));
				break;
			}
		}

		@Override
		public double getMaximumValue() {
			int max=100;
			if (!model.isOn()) max=255;
			else if (value==0) max=360;
			return max;
		}

		@Override
		public double getMinimumValue() {
			return 0;
		}

		@Override
		public int getPrecision() {
			return 0;
		}
	}
}
