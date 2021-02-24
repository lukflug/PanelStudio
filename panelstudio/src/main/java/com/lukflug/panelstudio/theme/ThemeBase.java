package com.lukflug.panelstudio.theme;

import java.awt.Color;

import com.lukflug.panelstudio.base.Context;
import com.lukflug.panelstudio.base.IInterface;

/**
 * Base class for themes.
 * @author lukflug
 */
public abstract class ThemeBase implements ITheme {
	/**
	 * The color scheme.
	 */
	protected final IColorScheme scheme;
	/**
	 * The overridden main color.
	 */
	private Color overrideColor=null;
	
	/**
	 * Constructor.
	 * @param scheme the color scheme to use
	 */
	public ThemeBase (IColorScheme scheme) {
		this.scheme=scheme;
	}
	
	@Override
	public <T> IPanelRenderer<T> getPanelRenderer (Class<T> type, int level) {
		IPanelRenderer<Void> renderer=getPanelRenderer(Void.class,level);
		return (context,focus,state)->renderer.renderPanelOverlay(context,focus,null);
	}
	
	@Override
	public <T> IScrollBarRenderer<T> getScrollBarRenderer (Class<T> type, int level) {
		IScrollBarRenderer<Void> renderer=getScrollBarRenderer(Void.class,level);
		return new IScrollBarRenderer<T>() {
			@Override
			public int renderScrollBar(Context context, boolean focus, T state, boolean horizontal, int height, int position) {
				return renderer.renderScrollBar(context,focus,null,horizontal,height,position);
			}

			@Override
			public int getThickness() {
				return renderer.getThickness();
			}
		};
	}
	
	@Override
	public <T> IEmptySpaceRenderer<T> getEmptySpaceRenderer (Class<T> type, int level) {
		IEmptySpaceRenderer<Void> renderer=getEmptySpaceRenderer(Void.class,level);
		return (context,focus,state)->renderer.renderSpace(context,focus,null);
	}
	
	@Override
	public <T> IButtonRenderer<T> getButtonRenderer (Class<T> type, int level, boolean container) {
		IButtonRenderer<Void> renderer=getButtonRenderer(Void.class,level,container);
		return new IButtonRenderer<T>() {
			@Override
			public void renderButton(Context context, String title, boolean focus, T state) {
				renderer.renderButton(context,title,focus,null);
			}

			@Override
			public int getDefaultHeight() {
				return renderer.getDefaultHeight();
			}
		};
	}
	
	@Override
	public void loadAssets (IInterface inter) {
	}
	
	@Override
	public void overrideMainColor (Color color) {
		overrideColor=color;
	}
	
	@Override
	public void restoreMainColor() {
		overrideColor=null;
	}
	
	/**
	 * Get overridden color.
	 * @param color the default color
	 * @return the main color
	 */
	protected Color getColor (Color color) {
		if (overrideColor==null) return color;
		else return overrideColor;
	}
}
