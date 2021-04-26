package com.lukflug.panelstudio.component;

import com.lukflug.panelstudio.base.Context;

/**
 * Interface representing a drawable object in the GUI.
 * @author lukflug
 */
public interface IComponent {
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
	
	public void handleChar (Context context, char character);
	
	/**
	 * Should be called by the parent when the mouse wheel is scrolled.
	 * The current height of the component should be set by this method via {@link Context#setHeight(int)}.
	 * @param context the {@link Context} for the component
	 * @param diff the amount by which the wheel was moved
	 */
	public void handleScroll (Context context, int diff);
	
	/**
	 * Get the current height via {@link Context#setHeight(int)}.
	 * @param context the {@link Context} for the component
	 */
	public void getHeight (Context context);
	
	/**
	 * Should be called by the parent when the component is shown.
	 */
	public void enter();
	
	/**
	 * Should be called by the parent when the component is hidden.
	 */
	public void exit();
	
	/**
	 * Called when a parent loses focus.
	 */
	public void releaseFocus();
	
	/**
	 * Check if component should be visible.
	 */
	public boolean isVisible();
}
