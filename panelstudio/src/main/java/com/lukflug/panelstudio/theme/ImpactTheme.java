package com.lukflug.panelstudio.theme;

import java.awt.Color;
import java.awt.Point;

import com.lukflug.panelstudio.base.Context;
import com.lukflug.panelstudio.base.IInterface;

public class ImpactTheme extends ThemeBase {
	int height,padding;

	public ImpactTheme (IColorScheme scheme, int height, int padding) {
		super(scheme);
		this.height=height;
		this.padding=padding;
		scheme.createSetting(this,"Title Color","The color for panel titles.",true,true,new Color(20,20,20,72),false);
		scheme.createSetting(this,"Background Color","The panel background color.",true,true,new Color(20,20,20,48),false);
		scheme.createSetting(this,"Hovered Color","The background color for hovered components.",true,true,new Color(20,20,20,64),false);
		scheme.createSetting(this,"Outline Color","The main color for panel outlines.",true,true,new Color(20,20,20,72),false);
		scheme.createSetting(this,"Active Font Color","The color for active text.",false,true,new Color(255,255,255),false);
		scheme.createSetting(this,"Hovered Font Color","The color for hovered text.",false,true,new Color(192,192,192),false);
		scheme.createSetting(this,"Inactive Font Color","The color for inactive text.",false,true,new Color(128,128,128),false);
		scheme.createSetting(this,"Enabled Color","The color for enabled modules.",false,true,new Color(0,255,0),false);
		scheme.createSetting(this,"Disabled Color","The  color for disabled modules.",false,true,new Color(255,0,0),false);
		scheme.createSetting(this,"Highlight Color","The color for highlighted text.",false,true,new Color(0,0,255),false);
		scheme.createSetting(this,"Tooltip Color","The color for description tooltips.",false,true,new Color(0,0,0),false);
	}

	@Override
	public IDescriptionRenderer getDescriptionRenderer() {
		return new IDescriptionRenderer() {
			@Override
			public void renderDescription(IInterface inter, Point pos, String text) {
				// TODO Auto-generated method stub
				
			}
		};
	}

	@Override
	public IContainerRenderer getContainerRenderer(int logicalLevel, int graphicalLevel, boolean horizontal) {
		return new IContainerRenderer() {
		};
	}

	@Override
	public <T> IPanelRenderer<T> getPanelRenderer(Class<T> type, int logicalLevel, int graphicalLevel) {
		return new IPanelRenderer<T>() {
			@Override
			public void renderPanelOverlay(Context context, boolean focus, T state, boolean open) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void renderTitleOverlay(Context context, boolean focus, T state, boolean open) {
				// TODO Auto-generated method stub
				
			}
		};
	}

	@Override
	public <T> IScrollBarRenderer<T> getScrollBarRenderer(Class<T> type, int logicalLevel, int graphicalLevel) {
		return new IScrollBarRenderer<T>() {
			@Override
			public int renderScrollBar(Context context, boolean focus, T state, boolean horizontal, int height, int position) {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public int getThickness() {
				// TODO Auto-generated method stub
				return 0;
			}
		};
	}

	@Override
	public <T> IEmptySpaceRenderer<T> getEmptySpaceRenderer(Class<T> type, int logicalLevel, int graphicalLevel, boolean container) {
		return new IEmptySpaceRenderer<T>() {
			@Override
			public void renderSpace(Context context, boolean focus, T state) {
				// TODO Auto-generated method stub
				
			}
		};
	}

	@Override
	public <T> IButtonRenderer<T> getButtonRenderer(Class<T> type, int logicalLevel, int graphicalLevel, boolean container) {
		return new IButtonRenderer<T>() {
			@Override
			public void renderButton(Context context, String title, boolean focus, T state) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public int getDefaultHeight() {
				// TODO Auto-generated method stub
				return 0;
			}
		};
	}

	@Override
	public IButtonRenderer<Void> getSmallButtonRenderer(int symbol, int logicalLevel, int graphicalLevel, boolean container) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IButtonRenderer<String> getKeybindRenderer(int logicalLevel, int graphicalLevel, boolean container) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ISliderRenderer getSliderRenderer(int logicalLevel, int graphicalLevel, boolean container) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IRadioRenderer getRadioRenderer(int logicalLevel, int graphicalLevel, boolean container) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IResizeBorderRenderer getResizeRenderer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ITextFieldRenderer getTextRenderer(boolean embed, int logicalLevel, int graphicalLevel, boolean container) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ISwitchRenderer<Boolean> getToggleSwitchRenderer(int logicalLevel, int graphicalLevel, boolean container) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ISwitchRenderer<String> getCycleSwitchRenderer(int logicalLevel, int graphicalLevel, boolean container) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IColorPickerRenderer getColorPickerRenderer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getBaseHeight() {
		return height+2*padding;
	}

	@Override
	public Color getMainColor(boolean focus, boolean active) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Color getBackgroundColor(boolean focus) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Color getFontColor(boolean focus) {
		return scheme.getColor("");
	}
}
