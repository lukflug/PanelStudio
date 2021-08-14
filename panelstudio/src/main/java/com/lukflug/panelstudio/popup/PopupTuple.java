package com.lukflug.panelstudio.popup;

import com.lukflug.panelstudio.component.IScrollSize;

/**
 * Data structure used to reduce argument count.
 * Describes what type of pop-up a certain layout should use.
 * @author lukflug
 */
public class PopupTuple {
	/**
	 * The {@link IPopupPositioner} to be used.
	 */
	public final IPopupPositioner popupPos;
	/**
	 * Whether the {@link IPopupPositioner} is dynamic.
	 */
	public final boolean dynamicPopup;
	/**
	 * The scroll behavior for the pop-up component.
	 */
	public final IScrollSize popupSize;
	
	/**
	 * Constructor.
	 * @param popupPos the value for {@link #popupPos}
	 * @param dynamicPopup the value for {@link #dynamicPopup}
	 * @param popupSize the value for {@link #popupSize}
	 */
	public PopupTuple (IPopupPositioner popupPos, boolean dynamicPopup, IScrollSize popupSize) {
		this.popupPos=popupPos;
		this.dynamicPopup=dynamicPopup;
		this.popupSize=popupSize;
	}
}
