package com.lukflug.panelstudio.widget;

import com.lukflug.panelstudio.base.IInterface;
import com.lukflug.panelstudio.component.HorizontalComponent;
import com.lukflug.panelstudio.component.IComponent;
import com.lukflug.panelstudio.component.ScrollComponent;
import com.lukflug.panelstudio.container.HorizontalContainer;
import com.lukflug.panelstudio.container.VerticalContainer;
import com.lukflug.panelstudio.setting.Labeled;
import com.lukflug.panelstudio.theme.IContainerRenderer;
import com.lukflug.panelstudio.theme.IEmptySpaceRenderer;
import com.lukflug.panelstudio.theme.IScrollBarRenderer;

/**
 * Composite container containing a scroll component, which can be scrolled using scroll bars.
 * @author lukflug
 * @param <T> the state type
 */
public abstract class ScrollableComponent<S,T extends IComponent> extends HorizontalContainer {
	protected final T component;
	
	/**
	 * Constructor.
	 * @param component the component to be wrapped
	 * @param renderer the renderer to use for the scroll bars
	 * @param emptyRenderer the renderer to use for the corners
	 */
	public ScrollableComponent (T component, IScrollBarRenderer<S> renderer, IEmptySpaceRenderer<S> emptyRenderer) {
		super(new Labeled(component.getTitle(),null,()->component.isVisible()),new IContainerRenderer(){});
		this.component=component;
		// Component containing content
		ScrollComponent<T> scrollComponent=new ScrollComponent<T>() {
			@Override
			public T getComponent() {
				return component;
			}
			
			@Override
			public int getHeight (int height) {
				return getScrollHeight(height);
			}
			
			@Override
			protected int getComponentWidth(int scrollWidth) {
				return ScrollableComponent.this.getComponentWidth(scrollWidth);
			}
		};
		// Vertical scroll bar
		ScrollBar<S> verticalBar=new ScrollBar<S>(new Labeled(component.getTitle(),null,()->scrollComponent.isScrollingY()),false,renderer) {
			@Override
			protected int getLength() {
				return scrollComponent.getScrollSize().height;
			}

			@Override
			protected int getContentHeight() {
				return scrollComponent.getContentSize().height;
			}

			@Override
			protected int getScrollPosition() {
				return scrollComponent.getScrollPos().y;
			}

			@Override
			protected void setScrollPosition(int position) {
				scrollComponent.setScrollPosY(position);
			}

			@Override
			protected S getState() {
				return ScrollableComponent.this.getState();
			}
		};
		// Horizontal scroll bar
		ScrollBar<S> horizontalBar=new ScrollBar<S>(new Labeled(component.getTitle(),null,()->scrollComponent.isScrollingX()),true,renderer) {
			@Override
			protected int getLength() {
				return scrollComponent.getScrollSize().width;
			}

			@Override
			protected int getContentHeight() {
				return scrollComponent.getContentSize().width;
			}

			@Override
			protected int getScrollPosition() {
				return scrollComponent.getScrollPos().x;
			}

			@Override
			protected void setScrollPosition(int position) {
				scrollComponent.setScrollPosX(position);
			}

			@Override
			protected S getState() {
				return ScrollableComponent.this.getState();
			}
		};
		// Populate containers
		VerticalContainer leftContainer=new VerticalContainer(new Labeled(component.getTitle(),null,()->true),new IContainerRenderer(){});
		leftContainer.addComponent(scrollComponent);
		leftContainer.addComponent(horizontalBar);
		VerticalContainer rightContainer=new VerticalContainer(new Labeled(component.getTitle(),null,()->true),new IContainerRenderer(){});
		rightContainer.addComponent(verticalBar);
		rightContainer.addComponent(new EmptySpace<S>(new Labeled("Empty",null,()->scrollComponent.isScrollingX()&&scrollComponent.isScrollingY()),()->renderer.getThickness(),emptyRenderer) {
			@Override
			protected S getState() {
				return ScrollableComponent.this.getState();
			}
		});
		addComponent(new HorizontalComponent<VerticalContainer>(leftContainer,0,1));
		addComponent(new HorizontalComponent<VerticalContainer>(rightContainer,0,0) {
			@Override
			public int getWidth (IInterface inter) {
				return renderer.getThickness();
			}
		},()->scrollComponent.isScrollingY());
	}
	
	public T getContentComponent() {
		return component;
	}

	/**
	 * Function to determine the visible scroll height.
	 * @param componentHeight the component height
	 * @return the vsiible height
	 */
	protected abstract int getScrollHeight (int componentHeight);

	/**
	 * Function to determine the width allocated to the child component.
	 * @param scrollWidth the visible width
	 * @return the component width
	 */
	protected abstract int getComponentWidth (int scrollWidth);
	
	/**
	 * What render state the scroll bar should use.
	 * @return the scroll bar render state
	 */
	protected abstract S getState();
}
