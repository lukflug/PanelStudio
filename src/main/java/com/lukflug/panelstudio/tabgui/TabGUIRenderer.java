package com.lukflug.panelstudio.tabgui;

import com.lukflug.panelstudio.Context;
import com.lukflug.panelstudio.theme.ColorScheme;

/**
 * An Interface to abstract the rendering of TabGUI containers.
 * @author lukflug
 */
public interface TabGUIRenderer {
	/**
	 * Get the default component height.
	 * @return component height
	 */
	public int getHeight();
	
	/**
	 * Renders the background of a component.
	 * @param context the current context
	 * @param offset the vertical position of the text highlight
	 * @param height the height of the text highlight and of single components
	 */
	public void renderBackground (Context context, int offset, int height);
	
	/**
	 * Returns the default color scheme.
	 * @return the color scheme
	 */
	public ColorScheme getColorScheme();
	
	/**
	 * Check whether key scancode is up key.
	 * @param key scancode in question
	 * @return true if key is up key
	 */
	public boolean isUpKey (int key);
	
	/**
	 * Check whether key scancode is down key.
	 * @param key scancode in question
	 * @return true if key is down key
	 */
	public boolean isDownKey (int key);
	
	/**
	 * Check whether key scancode is select key.
	 * @param key scancode in question
	 * @return true if key is select key
	 */
	public boolean isSelectKey (int key);
	
	/**
	 * Check whether key scancode is escape key.
	 * @param key scancode in question
	 * @return true if key is escape key
	 */
	public boolean isEscapeKey (int key);
}
