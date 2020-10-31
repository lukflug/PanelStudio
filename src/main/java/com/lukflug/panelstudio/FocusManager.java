package com.lukflug.panelstudio;

/**
 * Interface to prevent cyclical dependency between ClickGUI and {@link FixedComponent} requesting focus.
 * @author lukflug
 */
public interface FocusManager {
	/**
	 * Function to request the focus within a GUI.
	 * @param component the component requesting focus
	 */
	public void requestFocus (Focusable component);
}
