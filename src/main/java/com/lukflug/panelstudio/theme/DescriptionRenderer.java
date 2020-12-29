package com.lukflug.panelstudio.theme;

import com.lukflug.panelstudio.Context;

/**
 * Interface used to render descriptions.
 * @author lukflug
 */
public interface DescriptionRenderer {
	/**
	 * Render a description
	 * @param context the context for the panel creating the description
	 */
	public void renderDescription (Context context);
}
