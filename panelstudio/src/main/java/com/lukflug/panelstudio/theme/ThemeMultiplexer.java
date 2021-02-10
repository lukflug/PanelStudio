package com.lukflug.panelstudio.theme;

/**
 * Base class used to enable switch themes "on-the-fly".
 * It provides {@link RendererProxy} to be used as renderers for the components.
 * In this way, the renderers can effectively be switched, without changing the field in {@link com.lukflug.panelstudio.FocusableComponent#renderer}.
 * @author lukflug
 */
public abstract class ThemeMultiplexer implements ITheme {
	/**
	 * Initializes the renderer fields.
	 */
	public ThemeMultiplexer() {
	}
	
	/**
	 * Abstract method that returns the current theme.
	 * @return the current theme
	 */
	protected abstract ITheme getTheme();
}
