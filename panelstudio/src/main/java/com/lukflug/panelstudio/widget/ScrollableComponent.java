package com.lukflug.panelstudio.widget;

import java.awt.Dimension;
import java.util.concurrent.atomic.AtomicReference;

import com.lukflug.panelstudio.component.IComponent;
import com.lukflug.panelstudio.component.ScrollComponent;
import com.lukflug.panelstudio.container.HorizontalContainer;
import com.lukflug.panelstudio.theme.IContainerRenderer;
import com.lukflug.panelstudio.theme.IScrollBarRenderer;

/**
 * Composite container containing a scroll component, which can be scrolled using scroll bars.
 * @author lukflug
 */
public abstract class ScrollableComponent extends HorizontalContainer {
	
	public ScrollableComponent (IComponent component, IScrollBarRenderer renderer) {
		super(component.getTitle(),null,()->component.isVisible(),new IContainerRenderer(){});
		AtomicReference<Dimension> scrollSize=new AtomicReference<Dimension>();
		AtomicReference<Dimension> contentSize=new AtomicReference<Dimension>();
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
		ScrollBar verticalBar=new ScrollBar(component.getTitle(),null,()->component.isVisible(),false,renderer) {
			@Override
			protected int getLength() {
				return scrollSize.get().height;
			}

			@Override
			protected int getContentHeight() {
				return contentSize.get().height;
			}

			@Override
			protected int getScrollPosition() {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			protected void setScrollPosition(int position) {
				// TODO Auto-generated method stub
				
			}

			@Override
			protected boolean isActive() {
				return ScrollableComponent.this.isActive();
			}
			
		};
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
