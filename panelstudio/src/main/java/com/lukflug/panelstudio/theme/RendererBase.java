package com.lukflug.panelstudio.theme;

import java.awt.Color;

import com.lukflug.panelstudio.Context;

/**
 * Base class for constructing renderers.
 * @author lukflug
 */
public abstract class RendererBase implements Renderer {
	/**
	 * Field to store default component dimensions.
	 */
	protected final int height,offset,border,left,right;
	/**
	 * Custom color scheme.
	 */
	protected ColorScheme scheme=null;
	
	/**
	 * Constructor.
	 * @param height default component height
	 * @param offset default vertical offset
	 * @param border default horizontal border
	 * @param left default left border
	 * @param right default right border
	 */
	public RendererBase (int height, int offset, int border, int left, int right) {
		this.height=height;
		this.offset=offset;
		this.border=border;
		this.left=left;
		this.right=right;
	}
	
	/**
	 * Get default component height.
	 */
	@Override
	public int getHeight (boolean open) {
		return height;
	}

	/**
	 * Get default component vertical offset.
	 */
	@Override
	public int getOffset() {
		return offset;
	}
	
	/**
	 * Get default component horizontal border.
	 */
	@Override
	public int getBorder() {
		return border;
	}
	
	/**
	 * Returns zero.
	 */
	@Override
	public int getBottomBorder() {
		return 0;
	}

	/**
	 * Get default container left horizontal border.
	 */
	@Override
	public int getLeftBorder (boolean scroll) {
		if (scroll) return left;
		else return 0;
	}

	/**
	 * Get default container right horizontal border.
	 */
	@Override
	public int getRightBorder (boolean scroll) {
		if (scroll) return right;
		else return 0;
	}

	/**
	 * Renders an inactive title bar.
	 */
	@Override
	public void renderTitle(Context context, String text, boolean focus) {
		renderTitle(context,text,focus,false);
	}

	/**
	 * Renders using {@link #renderRect(Context, String, boolean, boolean, java.awt.Rectangle, boolean)} with the rectangle being specified by {@link com.lukflug.panelstudio.Context#getRect()}.
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
	 * Does nothing.
	 */
	@Override
	public int renderScrollBar (Context context, boolean focus, boolean active, boolean scroll, int childHeight, int scrollPosition) {
		return scrollPosition;
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
