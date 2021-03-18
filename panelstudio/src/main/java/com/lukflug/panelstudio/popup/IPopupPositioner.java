package com.lukflug.panelstudio.popup;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;

import com.lukflug.panelstudio.base.IInterface;

/**
 * Interface representing how pop-ups should be positioned.
 * @author lukflug
 */
@FunctionalInterface
public interface IPopupPositioner {
	/**
	 * Get position.
	 * @param inter the interface to be used
	 * @param component the position of the component causing the pop-up
	 * @param panel the position of the panel containing the component
	 * @return the position of the pop-up
	 */
	public Point getPosition (IInterface inter, Dimension popup, Rectangle component, Rectangle panel);
}
