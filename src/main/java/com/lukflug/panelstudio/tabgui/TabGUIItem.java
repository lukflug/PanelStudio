package com.lukflug.panelstudio.tabgui;

import com.lukflug.panelstudio.Context;
import com.lukflug.panelstudio.settings.Toggleable;

/**
 * Component representing leaf in TabGUI hierarchy.
 * @author lukflug
 */
public class TabGUIItem implements TabGUIComponent {
	/**
	 * Caption of the component.
	 */
	protected String title;
	/**
	 * Toggle indicating whether this component is active.
	 */
	protected Toggleable toggle;
	
	/**
	 * Constructor.
	 * @param title caption of the component
	 * @param toggle toggle for {@link #isActive()} state
	 */
	public TabGUIItem (String title, Toggleable toggle) {
		this.title=title;
		this.toggle=toggle;
	}
	
	/**
	 * Returns the component caption.
	 */
	@Override
	public String getTitle() {
		return title;
	}

	/**
	 * Does nothing.
	 */
	@Override
	public void render(Context context) {
	}

	/**
	 * Does nothing.
	 */
	@Override
	public void handleButton(Context context, int button) {
	}

	/**
	 * Does nothing.
	 */
	@Override
	public void handleKey(Context context, int scancode) {
	}
	
	/**
	 * Does nothing.
	 */
	@Override
	public void handleScroll (Context context, int diff) {
	}

	/**
	 * Does nothing.
	 */
	@Override
	public void getHeight (Context context) {
	}

	/**
	 * Does nothing.
	 */
	@Override
	public void enter (Context context) {
	}

	/**
	 * Does nothing.
	 */
	@Override
	public void exit (Context context) {
	}

	/**
	 * Returns the value of the {@link #toggle}.
	 */
	@Override
	public boolean isActive() {
		return toggle.isOn();
	}

	/**
	 * Toggles the {@link #toggle} and does not request focus.
	 */
	@Override
	public boolean select() {
		toggle.toggle();
		return false;
	}

	/**
	 * Does nothing.
	 */
	@Override
	public void releaseFocus() {
	}
}
