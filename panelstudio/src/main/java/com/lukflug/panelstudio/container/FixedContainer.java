package com.lukflug.panelstudio.container;

import com.lukflug.panelstudio.base.Context;
import com.lukflug.panelstudio.base.IBoolean;
import com.lukflug.panelstudio.component.IFixedComponent;
import com.lukflug.panelstudio.theme.ContainerRenderer;

/**
 * Container with contents arranged at will.
 * @author lukflug
 */
public class FixedContainer extends Container<IFixedComponent> {
	/**
	 * Constructor.
	 * @param title the caption for this component
	 * @param description the description for this component
	 * @param visible whether this component is visible
	 * @param renderer the renderer for this container
	 */
	public FixedContainer(String title, String description, IBoolean visible, ContainerRenderer renderer) {
		super(title, description, visible, renderer);
	}

	@Override
	protected void doContextSensitiveLoop(Context context, ContextSensitiveConsumer<IFixedComponent> function) {
		
	}
}
