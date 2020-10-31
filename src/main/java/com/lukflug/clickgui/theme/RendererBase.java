package com.lukflug.clickgui.theme;

import java.awt.Color;

import com.lukflug.clickgui.Context;

/**
 * Base class for constructing renderers.
 * @author lukflug
 */
public abstract class RendererBase implements Renderer {
	/**
	 * Field to store default component dimensions.
	 */
	protected final int height,offset,border;
	/**
	 * Custom color scheme.
	 */
	protected ColorScheme scheme=null;
	
	/**
	 * Constructor
	 * @param height default component height
	 * @param offset default vertical offset
	 * @param border default horizontal border
	 */
	public RendererBase (int height, int offset, int border) {
		this.height=height;
		this.offset=offset;
		this.border=border;
	}
	
	@Override
	public int getHeight() {
		return height;
	}

	@Override
	public int getOffset() {
		return offset;
	}

	@Override
	public int getBorder() {
		return border;
	}

	/**
	 * Renders an inactive title bar.
	 */
	@Override
	public void renderTitle(Context context, String text, boolean focus) {
		renderTitle(context,text,focus,false);
	}

	/**
	 * Renders using {@link #renderRect(Context, String, boolean, boolean, java.awt.Rectangle, boolean)} with the rectangle being specified by {@link com.lukflug.clickgui.Context#getRect()}.
	 */
	@Override
	public void renderTitle(Context context, String text, boolean focus, boolean active) {
		renderRect(context,text,focus,active,context.getRect(),true);		
	}

	/**
	 * Renders a title bar using {@link #renderTitle(Context, String, boolean, boolean)}.
	 */
	@Override
	public void renderTitle(Context context, String text, boolean focus, boolean active, boolean open) {
		renderTitle(context,text,focus,active);
	}

	/**
	 * Returns the font color specified by the color scheme.
	 */
	@Override
	public Color getFontColor(boolean focus) {
		return getColorScheme().getFontColor();
	}

	/**
	 * Overwrites the default color scheme, by setting {@link #scheme}.
	 */
	@Override
	public void overrideColorScheme(ColorScheme scheme) {
		this.scheme=scheme;
	}

	/**
	 * Restores the default color scheme, by setting {@link #scheme} to null.
	 */
	@Override
	public void restoreColorScheme() {
		scheme=null;
	}
	
	/**
	 * Returns the default color scheme, if {@link #scheme} is null, otherwise returns {@link #scheme}.
	 * @return the current color scheme
	 */
	protected ColorScheme getColorScheme() {
		if (scheme==null) return getDefaultColorScheme();
		return scheme;
	}
}
