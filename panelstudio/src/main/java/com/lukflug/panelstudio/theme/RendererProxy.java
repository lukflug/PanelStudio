package com.lukflug.panelstudio.theme;

import java.awt.Color;
import java.awt.Rectangle;

import com.lukflug.panelstudio.Context;

/**
 * Implementation of {@link Renderer} that simply redirects methods to another renderer.
 * @author lukflug
 */
public abstract class RendererProxy implements Renderer {
	/**
	 * Redirects the method to renderer in {@link #getRenderer()}.
	 */
	@Override
	public int getHeight (boolean open) {
		return getRenderer().getHeight(open);
	}

	/**
	 * Redirects the method to renderer in {@link #getRenderer()}.
	 */
	@Override
	public int getOffset() {
		return getRenderer().getOffset();
	}

	/**
	 * Redirects the method to renderer in {@link #getRenderer()}.
	 */
	@Override
	public int getBorder() {
		return getRenderer().getBorder();
	}

	/**
	 * Redirects the method to renderer in {@link #getRenderer()}.
	 */
	@Override
	public int getBottomBorder() {
		return getRenderer().getBottomBorder();
	}

	/**
	 * Redirects the method to renderer in {@link #getRenderer()}.
	 */
	@Override
	public int getLeftBorder(boolean scroll) {
		return getRenderer().getLeftBorder(scroll);
	}

	/**
	 * Redirects the method to renderer in {@link #getRenderer()}.
	 */
	@Override
	public int getRightBorder(boolean scroll) {
		return getRenderer().getRightBorder(scroll);
	}

	/**
	 * Redirects the method to renderer in {@link #getRenderer()}.
	 */
	@Override
	public void renderTitle(Context context, String text, boolean focus) {
		getRenderer().renderTitle(context,text,focus);
	}

	/**
	 * Redirects the method to renderer in {@link #getRenderer()}.
	 */
	@Override
	public void renderTitle(Context context, String text, boolean focus, boolean active) {
		getRenderer().renderTitle(context,text,focus,active);
	}

	/**
	 * Redirects the method to renderer in {@link #getRenderer()}.
	 */
	@Override
	public void renderTitle(Context context, String text, boolean focus, boolean active, boolean open) {
		getRenderer().renderTitle(context,text,focus,active,open);
	}

	/**
	 * Redirects the method to renderer in {@link #getRenderer()}.
	 */
	@Override
	public void renderRect(Context context, String text, boolean focus, boolean active, Rectangle rectangle, boolean overlay) {
		getRenderer().renderRect(context,text,focus,active,rectangle,overlay);
	}

	/**
	 * Redirects the method to renderer in {@link #getRenderer()}.
	 */
	@Override
	public void renderBackground(Context context, boolean focus) {
		getRenderer().renderBackground(context,focus);
	}

	/**
	 * Redirects the method to renderer in {@link #getRenderer()}.
	 */
	@Override
	public void renderBorder(Context context, boolean focus, boolean active, boolean open) {
		getRenderer().renderBorder(context,focus,active,open);
	}

	/**
	 * Redirects the method to renderer in {@link #getRenderer()}.
	 */
	@Override
	public int renderScrollBar(Context context, boolean focus, boolean active, boolean scroll, int childHeight, int scrollPosition) {
		return getRenderer().renderScrollBar(context,focus,active,scroll,childHeight,scrollPosition);
	}

	/**
	 * Redirects the method to renderer in {@link #getRenderer()}.
	 */
	@Override
	public Color getMainColor(boolean focus, boolean active) {
		return getRenderer().getMainColor(focus,active);
	}

	/**
	 * Redirects the method to renderer in {@link #getRenderer()}.
	 */
	@Override
	public Color getBackgroundColor(boolean focus) {
		return getRenderer().getBackgroundColor(focus);
	}

	/**
	 * Redirects the method to renderer in {@link #getRenderer()}.
	 */
	@Override
	public Color getFontColor(boolean focus) {
		return getRenderer().getFontColor(focus);
	}

	/**
	 * Redirects the method to renderer in {@link #getRenderer()}.
	 */
	@Override
	public ColorScheme getDefaultColorScheme() {
		return getRenderer().getDefaultColorScheme();
	}

	/**
	 * Redirects the method to renderer in {@link #getRenderer()}.
	 */
	@Override
	public void overrideColorScheme(ColorScheme scheme) {
		getRenderer().overrideColorScheme(scheme);
	}

	/**
	 * Redirects the method to renderer in {@link #getRenderer()}.
	 */
	@Override
	public void restoreColorScheme() {
		getRenderer().restoreColorScheme();
	}
	
	/**
	 * Abstract method to get the renderer that is the target of the redirect.
	 * @return target renderer
	 */
	protected abstract Renderer getRenderer();
}
