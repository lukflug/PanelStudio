package com.lukflug.panelstudio.component;

import java.awt.Dimension;
import java.awt.Point;
import java.util.function.Consumer;

import com.lukflug.panelstudio.base.Context;

/**
 * A component that can scroll another component.
 * @author lukflug
 */
public abstract class ScrollComponent extends ComponentProxy {
	/**
	 * Current scrolling position.
	 */
	protected Point scrollPos=new Point(0,0);
	/**
	 * The next scroll position.
	 */
	protected Point nextScrollPos=null;
	
	/**
	 * Constructor.
	 * @param component the component to scroll
	 */
	public ScrollComponent(IComponent component) {
		super(component);
	}
	
	@Override
	public void render(Context context) {
		getHeight(context);
		context.getInterface().window(context.getRect());
		doOperation(context,component::render);
		context.getInterface().restore();
	}

	@Override
	public void handleScroll(Context context, int diff) {
		Context sContext=doOperation(context,subContext->component.handleScroll(subContext,diff));
		if (context.isHovered()) {
			if (sContext.getSize().height>context.getSize().height) scrollPos.translate(0,diff);
			else if (sContext.getSize().width>context.getSize().width) scrollPos.translate(diff,0);
			clampScrollPos(context.getSize(),sContext.getSize());
		}
	}
	
	@Override
	protected Context doOperation (Context context, Consumer<Context> operation) {
		Context subContext=super.doOperation(context,operation);
		if (nextScrollPos!=null) {
			scrollPos=nextScrollPos;
			clampScrollPos(context.getSize(),subContext.getSize());
			nextScrollPos=null;
		}
		return subContext;
	}
	
	@Override
	protected Context getContext (Context context) {
		return new Context(context,getComponentWidth(context.getSize().width),new Point(-scrollPos.x,-scrollPos.y),context.hasFocus(),context.onTop());
	}
	
	/**
	 * Get the current scroll position.
	 * @return the current scroll position
	 */
	public Point getScrollPos() {
		return new Point(scrollPos);
	}
	
	/**
	 * Set the scroll position;
	 * @param scrollPos the new scroll position
	 */
	public void setScrollPos (Point scrollPos) {
		nextScrollPos=new Point(scrollPos);
	}
	
	/**
	 * Clamp scroll position.
	 * @param scrollSize the dimensions of the scroll component
	 * @param contentSize the dimensions of the content
	 */
	protected void clampScrollPos (Dimension scrollSize, Dimension contentSize) {
		if (scrollPos.x>contentSize.width-scrollSize.width) scrollPos.x=contentSize.width-scrollSize.width;
		if (scrollPos.x<0) scrollPos.x=0;
		if (scrollPos.y>contentSize.height-scrollSize.height) scrollPos.y=contentSize.height-scrollSize.height;
		if (scrollPos.y<0) scrollPos.y=0;
	}
	
	/**
	 * Function to determine the width allocated to the child component.
	 * @param scrollWidth the visible width
	 * @return the component width
	 */
	protected abstract int getComponentWidth (int scrollWidth);
}
