package com.lukflug.panelstudio.widget;

import java.util.function.Supplier;

import com.lukflug.panelstudio.component.ComponentBase;
import com.lukflug.panelstudio.setting.ILabeled;
import com.lukflug.panelstudio.theme.IEmptySpaceRenderer;

/**
 * Component that is supposed to function as the corner for scrollable components or other sorts of blank spaces.
 * @author lukflug
 */
public class EmptySpace extends ComponentBase {
	/**
	 * The height of the space.
	 */
	protected Supplier<Integer> height;
	/**
	 * The renderer to be used.
	 */
	protected IEmptySpaceRenderer renderer;
	
	/**
	 * Constructor.
	 * @param label the label for the component
	 * @param height the height of the component
	 * @param renderer the renderer to be used
	 */
	public EmptySpace (ILabeled label, Supplier<Integer> height, IEmptySpaceRenderer renderer) {
		super(label);
		this.height=height;
		this.renderer=renderer;
	}

	@Override
	public void releaseFocus() {
	}

	@Override
	protected int getHeight() {
		return height.get();
	}

}
