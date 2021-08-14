package com.lukflug.panelstudio.popup;

import java.awt.Rectangle;

import com.lukflug.panelstudio.base.IToggleable;

/**
 * Interface representing a container that can display a pop-up.
 * @author lukflug
 */
public interface IPopupDisplayer {
	/**
	 * Display a {@link IPopup}.
	 * @param popup the {@link IPopup} to be used
	 * @param rect the location of the component displaying the pop-up
	 * @param visible predicate indicating to the pop-up whether it is visible
	 * @param positioner the {@link IPopupPositioner} to be used
	 */
	public void displayPopup (IPopup popup, Rectangle rect, IToggleable visible, IPopupPositioner positioner);
}
