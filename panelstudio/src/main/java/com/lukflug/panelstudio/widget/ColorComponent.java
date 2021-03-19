package com.lukflug.panelstudio.widget;

import java.awt.Color;

import com.lukflug.panelstudio.base.Animation;
import com.lukflug.panelstudio.base.Context;
import com.lukflug.panelstudio.base.IBoolean;
import com.lukflug.panelstudio.base.IToggleable;
import com.lukflug.panelstudio.container.VerticalContainer;
import com.lukflug.panelstudio.setting.IColorSetting;
import com.lukflug.panelstudio.setting.Labeled;
import com.lukflug.panelstudio.theme.ISliderRenderer;
import com.lukflug.panelstudio.theme.ITheme;
import com.lukflug.panelstudio.theme.ThemeTuple;

/**
 * Component representing a color-valued setting.
 * @author lukflug
 */
public class ColorComponent extends VerticalContainer {
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
	 * @param animation the animation to be used
	 * @param theme the theme to be used
	 */
	public ColorComponent (IColorSetting setting, Animation animation, ThemeTuple theme) {
		super(setting,theme.getContainerRenderer(false));
		this.setting=setting;
		this.theme=theme.theme;
		addComponent(new ToggleButton(new Labeled("Rainbow",null,()->setting.allowsRainbow()),new IToggleable() {
			@Override
			public boolean isOn() {
				return setting.getRainbow();
			}

			@Override
			public void toggle() {
				setting.setRainbow(!setting.getRainbow());
			}
		},theme.theme.getButtonRenderer(Boolean.class,theme.logicalLevel,theme.graphicalLevel+1,false)));
		ISliderRenderer sliderRenderer=theme.theme.getSliderRenderer(theme.logicalLevel,theme.graphicalLevel+1,false);
		addComponent(new ColorSlider(()->true,sliderRenderer,0));
		addComponent(new ColorSlider(()->true,sliderRenderer,1));
		addComponent(new ColorSlider(()->true,sliderRenderer,2));
		addComponent(new ColorSlider(()->setting.hasAlpha(),sliderRenderer,3));
	}
	
	@Override
	public void render (Context context) {
		theme.overrideMainColor(setting.getValue());
		super.render(context);
		theme.restoreMainColor();
	}
	
	/**
	 * Class to render the sliders in the color container.
	 * @author lukflug
	 */
	protected class ColorSlider extends Slider {
		/**
		 * Number indicating the index of the component for the color model.
		 */
		private final int value;
		
		/**
		 * Constructor.
		 * @param visible the visiblity of the component
		 * @param renderer the renderer to be used
		 * @param value the index of the slider inside the color component
		 */
		public ColorSlider (IBoolean visible, ISliderRenderer renderer, int value) {
			super(new Labeled("",null,visible),renderer);
			this.value=value;
		}
		
		@Override
		public String getTitle() {
			switch (value) {
			case 0:
				return (setting.hasHSBModel()?"Hue":"Red");
			case 1:
				return (setting.hasHSBModel()?"Saturation":"Green");
			case 2:
				return (setting.hasHSBModel()?"Brightness":"Blue");
			case 3:
				return (setting.hasHSBModel()?"Opacity":"Alpha");
			}
			return "";
		}

		@Override
		protected double getValue() {
			Color c=setting.getColor();
			if (value<3) {
				if (setting.hasHSBModel()) return Color.RGBtoHSB(c.getRed(),c.getGreen(),c.getBlue(),null)[value];
				switch (value) {
				case 0:
					return c.getRed()/255.0;
				case 1:
					return c.getGreen()/255.0;
				case 2:
					return c.getBlue()/255.0;
				}
			}
			return c.getAlpha()/255.0;
		}

		@Override
		protected void setValue(double value) {
			Color c=setting.getColor();
			float[] color=Color.RGBtoHSB(c.getRed(),c.getGreen(),c.getBlue(),null);
			switch (this.value) {
			case 0:
				if (setting.hasHSBModel()) c=Color.getHSBColor((float)value,color[1],color[2]);
				else c=new Color((int)(255*value),c.getGreen(),c.getBlue());
				if (setting.hasAlpha()) setting.setValue(new Color(c.getRed(),c.getGreen(),c.getBlue(),setting.getColor().getAlpha()));
				else setting.setValue(c);
				break;
			case 1:
				if (setting.hasHSBModel()) c=Color.getHSBColor(color[0],(float)value,color[2]);
				else c=new Color(c.getRed(),(int)(255*value),c.getBlue());
				if (setting.hasAlpha()) setting.setValue(new Color(c.getRed(),c.getGreen(),c.getBlue(),setting.getColor().getAlpha()));
				else setting.setValue(c);
				break;
			case 2:
				if (setting.hasHSBModel()) c=Color.getHSBColor(color[0],color[1],(float)value);
				else c=new Color(c.getRed(),c.getGreen(),(int)(255*value));
				if (setting.hasAlpha()) setting.setValue(new Color(c.getRed(),c.getGreen(),c.getBlue(),setting.getColor().getAlpha()));
				else setting.setValue(c);
				break;
			case 3:
				setting.setValue(new Color(c.getRed(),c.getGreen(),c.getBlue(),(int)(255*value)));
				break;
			}
		}

		@Override
		protected String getDisplayState() {
			int max=100;
			if (!setting.hasHSBModel()) max=255;
			else if (value==0) max=360;
			return ""+(int)(getValue()*max);
		}
	}
}
