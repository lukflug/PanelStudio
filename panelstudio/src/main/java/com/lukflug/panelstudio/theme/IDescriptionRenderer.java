package com.lukflug.panelstudio.theme;

import java.awt.Point;

import com.lukflug.panelstudio.base.IInterface;

/**
 * Interface used to render descriptions.
 * @author lukflug
 */
@FunctionalInterface
public interface IDescriptionRenderer {
	/**
	 * Render a description.
	 * @param inter the interface to be used
	 * @param pos the position of the description
	 * @param text the text to be renderered
	 */
	public void renderDescription (IInterface inter, Point pos, String text);
}
