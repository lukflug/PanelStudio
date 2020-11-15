package com.lukflug.panelstudio;

/**
 * Interface representing a drawable object in the GUI.
 * @author lukflug
 */
public interface Component {
	/**
	 * Get the caption of the component.
	 * String may also be empty.
	 * @return the caption of the component
	 */
	public String getTitle();
	
	/**
	 * Redraw component on screen.
	 * The current height of the component should be set by this method via {@link Context#setHeight(int)}.
	 * @param context the {@link Context} for the component
	 */
	public void render (Context context);
	
	/**
	 * Should be called by the parent when a mouse button state changes.
	 * The current height of the component should be set by this method via {@link Context#setHeight(int)}.
	 * @param context the {@link Context} for the component
	 * @param button the button that changed its state
	 * @see Interface#LBUTTON
	 * @see Interface#RBUTTON
	 */
	public void handleButton (Context context, int button);
	
	/**
	 * Should be called by the parent when a key is typed.
	 * The current height of the component should be set by this method via {@link Context#setHeight(int)}.
	 * @param context the {@link Context} for the component
	 * @param scancode the scancode for the key that was typed
	 */
	public void handleKey (Context context, int scancode);
	
	/**
	 * Get the current height via {@link Context#setHeight(int)}.
	 * @param context the {@link Context} for the component
	 */
	public void getHeight (Context context);
	
	/**
	 * Should be called by the parent when the GUI is closed.
	 * The current height of the component should be set by this method via {@link Context#setHeight(int)}.
	 * @param context the {@link Context} for the component
	 */
	public void exit (Context context);
	
	/**
	 * Called when a parent loses focus.
	 */
	public void releaseFocus();
}
