package com.lukflug.panelstudio.popup;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;

import com.lukflug.panelstudio.base.IInterface;

/**
 * Interface representing how pop-ups should be positioned.
 * There exist static and dynamic positioners.
 * Static pop-up positioners may not rely on the current size of the pop-up component and are usually only called when displaying the pop-up.
 * The position of a static pop-up doesn't change while the pop-up is being displayed.
 * Static positioners may be used as dynamic positioners.
 * Dynamic positioners depend on the dimensions of the pop-up and therefore this argument must not be null.
 * Dynamic positioners are used to update the position of a pop-up each frame.
 * @author lukflug
 */
@FunctionalInterface
public interface IPopupPositioner {
	/**
	 * Get position.
	 * @param inter the {@link IInterface} to be used
	 * @param popup dimensions of the pop-up to be positioned, may be null for static positioners
	 * @param component the position of the component causing the pop-up
	 * @param panel the position of the panel containing the component
	 * @return the position of the pop-up
	 */
	public Point getPosition (IInterface inter, Dimension popup, Rectangle component, Rectangle panel);
}
