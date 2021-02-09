package com.lukflug.panelstudio.widget;

import com.lukflug.panelstudio.base.Animation;
import com.lukflug.panelstudio.base.IBoolean;
import com.lukflug.panelstudio.base.IToggleable;
import com.lukflug.panelstudio.component.IComponent;
import com.lukflug.panelstudio.container.VerticalContainer;
import com.lukflug.panelstudio.theme.IButtonRenderer;
import com.lukflug.panelstudio.theme.IContainerRenderer;
import com.lukflug.panelstudio.theme.IEmptySpaceRenderer;
import com.lukflug.panelstudio.theme.IPanelRenderer;
import com.lukflug.panelstudio.theme.IScrollBarRenderer;

/**
 * Container that can be closed and scrolled, so that its children can be hidden.
 * @author lukflug
 */
public class CollapsibleContainer extends Panel {
	/**
	 * The container that is wrapped.
	 */
	protected final VerticalContainer contentContainer;
	
	/**
	 * Constructor.
	 * @param title the title of the panel
	 * @param description the description of the panel
	 * @param visible the visibility of the panel
	 * @param active whether the panel is active
	 * @param open the toggleable to be used to open and close the panel
	 * @param animation the animation for opening and closing the panel
	 * @param panelRenderer the renderer for the panel overlay
	 * @param titleRenderer the renderer for the panel title
	 * @param containerRenderer the renderer for the panel content container
	 * @param scrollRenderer the renderer for the scroll bars
	 * @param emptyRenderer the renderer for the scroll corner
	 */
	public CollapsibleContainer (String title, String description, IBoolean visible, IBoolean active, IToggleable open, Animation animation, IPanelRenderer panelRenderer, IButtonRenderer<Void> titleRenderer, IContainerRenderer containerRenderer, IScrollBarRenderer scrollRenderer, IEmptySpaceRenderer emptyRenderer) {
		this(new Button(title,description,visible,titleRenderer),new VerticalContainer(title,description,visible,containerRenderer),active,open,animation,panelRenderer,scrollRenderer,emptyRenderer);
	}
	
	/**
	 * Constructor.
	 * @param title the title of the panel
	 * @param content the content container of the panel
	 * @param active whether the panel is active
	 * @param open the toggleable to be used to open and close the panel
	 * @param animation the animation for opening and closing the panel
	 * @param panelRenderer the renderer for the panel overlay
	 * @param scrollRenderer the renderer for the scroll bars
	 * @param emptyRenderer the renderer for the scroll corner
	 */
	public CollapsibleContainer (IComponent title, VerticalContainer content, IBoolean active, IToggleable open, Animation animation, IPanelRenderer panelRenderer, IScrollBarRenderer scrollRenderer, IEmptySpaceRenderer emptyRenderer) {
		super(title,new ScrollableComponent(content,scrollRenderer,emptyRenderer) {
			@Override
			protected int getScrollHeight (int componentHeight) {
				return getScrollHeight(componentHeight);
			}

			@Override
			protected int getComponentWidth (int scrollWidth) {
				return getComponentWidth(scrollWidth);
			}

			@Override
			protected boolean isActive() {
				return active.isOn();
			}
		},active,open,animation,panelRenderer);
		contentContainer=content;
	}

	/**
	 * Add component to container.
	 * @param component the component to be added
	 */
	public void addComponent (IComponent component) {
		contentContainer.addComponent(component);
	}
	
	/**
	 * Add component to container with visibility.
	 * @param component the component to be added
	 * @param visible the visibility of the component
	 */
	public void addComponent (IComponent component, IBoolean visible) {
		contentContainer.addComponent(component,visible);
	}
	
	/**
	 * Remove component from container.
	 * @param component the component to be removed
	 */
	public void removeComponent (IComponent component) {
		contentContainer.removeComponent(component);
	}
	
	/**
	 * Get visible scroll height based on content container height.
	 * @param componentHeight the container height
	 * @return the scroll height
	 */
	protected int getScrollHeight (int componentHeight) {
		return componentHeight;
	}

	/**
	 * Get content container width based on scroll width.
	 * @param scrollWidth the scroll width
	 * @return the container width
	 */
	protected int getComponentWidth (int scrollWidth) {
		return scrollWidth;
	}
}
