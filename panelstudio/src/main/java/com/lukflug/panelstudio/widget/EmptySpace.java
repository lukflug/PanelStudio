package com.lukflug.panelstudio.widget;

import java.util.function.Supplier;

import com.lukflug.panelstudio.base.Context;
import com.lukflug.panelstudio.component.ComponentBase;
import com.lukflug.panelstudio.setting.ILabeled;
import com.lukflug.panelstudio.theme.IEmptySpaceRenderer;

/**
 * Component that is supposed to function as the corner for scrollable components or other sorts of blank spaces.
 * @author lukflug
 * @param <T> the state type
 */
public abstract class EmptySpace<T> extends ComponentBase {
	/**
	 * The height of the space.
	 */
	protected Supplier<Integer> height;
	/**
	 * The renderer to be used.
	 */
	protected IEmptySpaceRenderer<T> renderer;
	
	/**
	 * Constructor.
	 * @param label the label for the component
	 * @param height the height of the component
	 * @param renderer the renderer to be used
	 */
	public EmptySpace (ILabeled label, Supplier<Integer> height, IEmptySpaceRenderer<T> renderer) {
		super(label);
		this.height=height;
		this.renderer=renderer;
	}
	
	@Override
	public void render (Context context) {
		super.getHeight(context);
		renderer.renderSpace(context,isVisible(),getState());
	}

	@Override
	public void releaseFocus() {
	}

	@Override
	protected int getHeight() {
		return height.get();
	}

	/**
	 * What render state the space should use.
	 * @return the render state
	 */
	protected abstract T getState();
}
