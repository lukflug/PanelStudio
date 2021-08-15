package com.lukflug.panelstudio.component;

import com.lukflug.panelstudio.base.Context;

/**
 * Interface representing scroll behavior.
 * @author lukflug
 */
public interface IScrollSize {
	/**
	 * Get visible height after scrolling based on original component height.
	 * @param context the current context
	 * @param componentHeight the height for the component
	 * @return the scroll height
	 */
	public default int getScrollHeight (Context context, int componentHeight) {
		return componentHeight;
	}
	
	/**
	 * Get the available width for components based on visible width
	 * @param context the current context
	 * @return the component width
	 */
	public default int getComponentWidth (Context context) {
		return context.getSize().width;
	}
}
