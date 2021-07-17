package com.lukflug.panelstudio.setting;

import com.lukflug.panelstudio.base.IBoolean;

/**
 * Simple implementation of the ILabeled interface.
 * @author lukflug
 */
public class Labeled implements ILabeled {
	/**
	 * The name of the label.
	 */
	protected String title;
	/**
	 * The description.
	 */
	protected String description;
	/**
	 * The visibility.
	 */
	protected IBoolean visible;

	/**
	 * Constructor.
	 * @param title the title of the label
	 * @param description the description
	 * @param visible the visibility
	 */
	public Labeled (String title, String description, IBoolean visible) {
		this.title=title;
		this.description=description;
		this.visible=visible;
	}
	
	@Override
	public String getDisplayName() {
		return title;
	}
	
	@Override
	public String getDescription() {
		return description;
	}
	
	@Override
	public IBoolean isVisible() {
		return visible;
	}
}
