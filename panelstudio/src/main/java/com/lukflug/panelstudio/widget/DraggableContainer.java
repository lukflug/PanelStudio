package com.lukflug.panelstudio.widget;

import java.awt.Point;

import com.lukflug.panelstudio.base.Animation;
import com.lukflug.panelstudio.base.IBoolean;
import com.lukflug.panelstudio.base.IToggleable;
import com.lukflug.panelstudio.component.DraggableComponent;
import com.lukflug.panelstudio.component.FixedComponent;
import com.lukflug.panelstudio.component.IComponent;
import com.lukflug.panelstudio.component.IFixedComponent;
import com.lukflug.panelstudio.container.VerticalContainer;
import com.lukflug.panelstudio.theme.IButtonRenderer;
import com.lukflug.panelstudio.theme.IContainerRenderer;
import com.lukflug.panelstudio.theme.IEmptySpaceRenderer;
import com.lukflug.panelstudio.theme.IPanelRenderer;
import com.lukflug.panelstudio.theme.IScrollBarRenderer;

/**
 * Class for a {@link IFixedComponent} that is also a {@link CollapsibleContainer} (i.e. a Panel), that can be dragged by the user using the mouse.
 * @author lukflug
 */
public class DraggableContainer extends DraggableComponent {
	protected final CollapsibleContainer panel;
	
	public DraggableContainer (String title, String description, IBoolean visible, IBoolean active, IToggleable open, Animation animation, IPanelRenderer panelRenderer, IButtonRenderer<Void> titleRenderer, IContainerRenderer containerRenderer, IScrollBarRenderer scrollRenderer, IEmptySpaceRenderer emptyRenderer, Point position, int width, boolean savesState) {
		super(null);
		panel=new CollapsibleContainer(getWrappedDragComponent(new Button(title,description,visible,titleRenderer)),new VerticalContainer(title,description,visible,containerRenderer),active,open,animation,panelRenderer,scrollRenderer,emptyRenderer) {
			@Override
			protected int getScrollHeight(int componentHeight) {
				return DraggableContainer.this.getScrollHeight(componentHeight);
			}
			
			@Override
			protected int getComponentWidth (int scrollWidth) {
				return DraggableContainer.this.getComponentWidth(scrollWidth);
			}
		};
		this.fixedComponent=new FixedComponent(panel,position,width,open,savesState);
		this.component=fixedComponent;
	}

	/**
	 * Add component to container.
	 * @param component the component to be added
	 */
	public void addComponent (IComponent component) {
		panel.addComponent(component);
	}
	
	/**
	 * Add component to container with visibility.
	 * @param component the component to be added
	 * @param visible the visibility of the component
	 */
	public void addComponent (IComponent component, IBoolean visible) {
		panel.addComponent(component,visible);
	}
	
	/**
	 * Remove component from container.
	 * @param component the component to be removed
	 */
	public void removeComponent (IComponent component) {
		panel.removeComponent(component);
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
