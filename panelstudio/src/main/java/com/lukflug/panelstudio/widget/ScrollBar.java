package com.lukflug.panelstudio.widget;

import com.lukflug.panelstudio.base.Context;
import com.lukflug.panelstudio.component.FocusableComponent;
import com.lukflug.panelstudio.setting.ILabeled;
import com.lukflug.panelstudio.theme.IScrollBarRenderer;

/**
 * Scroll bar for use in scrollable containers.
 * @author lukflug
 * @param <T> the state type
 */
public abstract class ScrollBar<T> extends FocusableComponent {
	/**
	 * Whether this scroll bar is horizontal or vertical.
	 */
	protected boolean horizontal;
	/**
	 * The renderer to be used.
	 */
	protected IScrollBarRenderer<T> renderer;

	/**
	 * Constructor.
	 * @param label the label for the component
	 * @param horizontal whether this component is horizontal
	 * @param renderer the renderer for this component
	 */
	public ScrollBar (ILabeled label, boolean horizontal, IScrollBarRenderer<T> renderer) {
		super(label);
		this.horizontal=horizontal;
	}
	
	@Override
	public void render (Context context) {
		super.render(context);
		setScrollPosition(renderer.renderScrollBar(context,hasFocus(context),getState(),horizontal,getContentHeight(),getScrollPosition()));
	}

	@Override
	protected int getHeight() {
		if (horizontal) return renderer.getThickness();
		else return getLength();
	}
	
	/**
	 * Get width the parent should allocate.
	 * @return the component width
	 */
	public int getWidth() {
		if (horizontal) return getLength();
		else return renderer.getThickness();
	}

	/**
	 * Function to get length of scroll bar.
	 * @return the scroll bar length
	 */
	protected abstract int getLength();
	
	/**
	 * Get height of the content to be scrolled.
	 * @return the content height
	 */
	protected abstract int getContentHeight();
	
	/**
	 * Get the current scroll position.
	 * @return the current scroll position
	 */
	protected abstract int getScrollPosition();
	
	/**
	 * Sets the scroll position.
	 * @param position the update scroll position
	 */
	protected abstract void setScrollPosition (int position);
	
	/**
	 * What render state the scroll bar should use.
	 * @return the scroll bar render state
	 */
	protected abstract T getState();
}
