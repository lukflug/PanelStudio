package com.lukflug.panelstudio.popup;

import java.awt.Rectangle;

import com.lukflug.panelstudio.base.IInterface;

/**
 * Interface representing a pop-up, e.g. a tooltip.
 * @author lukflug
 */
@FunctionalInterface
public interface IPopup {
	/**
	 * Set the pop-up position based on the information given in the arguments.
	 * @param inter the current {@link IInterface}
	 * @param component the position of the component displaying the pop-up
	 * @param panel the position of that component's panel
	 * @param positioner the {@link IPopupPositioner} to be used
	 */
	public void setPosition (IInterface inter, Rectangle component, Rectangle panel, IPopupPositioner positioner);
}
