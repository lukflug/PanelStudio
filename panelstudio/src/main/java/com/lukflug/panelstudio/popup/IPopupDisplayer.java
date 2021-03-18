package com.lukflug.panelstudio.popup;

import java.awt.Rectangle;

import com.lukflug.panelstudio.base.IToggleable;

/**
 * Object that can display a pop-up.
 * @author lukflug
 */
public interface IPopupDisplayer {
	public void displayPopup (IPopup popup, Rectangle rect, IToggleable visible, IPopupPositioner positioner);
}
