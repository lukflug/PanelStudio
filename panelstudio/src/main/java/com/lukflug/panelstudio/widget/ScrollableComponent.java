package com.lukflug.panelstudio.widget;

import java.awt.Point;

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
 */
public abstract class ScrollableComponent extends HorizontalContainer {
	
	public ScrollableComponent (IComponent component, IScrollBarRenderer renderer, IEmptySpaceRenderer emptyRenderer) {
		super(new Labeled(component.getTitle(),null,()->component.isVisible()),new IContainerRenderer(){});
		// Component containing content
		ScrollComponent scrollComponent=new ScrollComponent(component) {
			@Override
			protected int getHeight (int height) {
				return getScrollHeight(height);
			}
			
			@Override
			protected int getComponentWidth(int scrollWidth) {
				return ScrollableComponent.this.getComponentWidth(scrollWidth);
			}
		};
		// Vertical scroll bar
		ScrollBar verticalBar=new ScrollBar(new Labeled(component.getTitle(),null,()->scrollComponent.isScrollingY()),false,renderer) {
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
				scrollComponent.setScrollPos(new Point(scrollComponent.getScrollPos().x,position));
			}

			@Override
			protected boolean isActive() {
				return ScrollableComponent.this.isActive();
			}
		};
		// Horizontal scroll bar
		ScrollBar horizontalBar=new ScrollBar(new Labeled(component.getTitle(),null,()->scrollComponent.isScrollingX()),true,renderer) {
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
				scrollComponent.setScrollPos(new Point(position,scrollComponent.getScrollPos().y));
			}

			@Override
			protected boolean isActive() {
				return ScrollableComponent.this.isActive();
			}
		};
		// Populate containers
		VerticalContainer leftContainer=new VerticalContainer(new Labeled(component.getTitle(),null,()->true),new IContainerRenderer(){});
		leftContainer.addComponent(scrollComponent);
		leftContainer.addComponent(horizontalBar);
		VerticalContainer rightContainer=new VerticalContainer(new Labeled(component.getTitle(),null,()->true),new IContainerRenderer(){});
		rightContainer.addComponent(verticalBar);
		rightContainer.addComponent(new EmptySpace(new Labeled("Empty",null,()->scrollComponent.isScrollingX()&&scrollComponent.isScrollingY()),()->renderer.getThickness(),emptyRenderer));
		addComponent(new HorizontalComponent(leftContainer,0,1));
		addComponent(new HorizontalComponent(rightContainer,0,0) {
			@Override
			public int getWidth (IInterface inter) {
				return renderer.getThickness();
			}
		},()->scrollComponent.isScrollingY());
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
	 * Whether component should be rendered as active.
	 * @return whether scroll bar is active
	 */
	protected abstract boolean isActive();
}
