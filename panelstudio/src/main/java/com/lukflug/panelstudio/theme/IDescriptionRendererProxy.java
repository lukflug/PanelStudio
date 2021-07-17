package com.lukflug.panelstudio.theme;

import java.awt.Point;

import com.lukflug.panelstudio.base.IInterface;

/**
 * Proxy redirecting calls.
 * @author lukflug
 */
@FunctionalInterface
public interface IDescriptionRendererProxy extends IDescriptionRenderer {
	@Override
	public default void renderDescription(IInterface inter, Point pos, String text) {
		getRenderer().renderDescription(inter,pos,text);
	}

	/**
	 * The renderer to be redirected to.
	 * @return the renderer
	 */
	public IDescriptionRenderer getRenderer();
}
