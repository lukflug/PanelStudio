package com.lukflug.panelstudio.theme;

import java.awt.Color;
import java.awt.Point;

import com.lukflug.panelstudio.base.Context;
import com.lukflug.panelstudio.base.IInterface;

public class ImpactTheme extends ThemeBase {

	public ImpactTheme(IColorScheme scheme) {
		super(scheme);
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
		// TODO Auto-generated method stub
		return 0;
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
		// TODO Auto-generated method stub
		return null;
	}
}
