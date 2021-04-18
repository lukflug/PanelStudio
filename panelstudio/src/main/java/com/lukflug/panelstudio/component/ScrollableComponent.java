package com.lukflug.panelstudio.component;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.function.Consumer;

import com.lukflug.panelstudio.base.Context;

/**
 * A component that can scroll another component.
 * @author lukflug
 */
public abstract class ScrollableComponent<T extends IComponent> implements IComponentProxy<T>,IScrollSize {
	private Context tempContext;
	/**
	 * Current scrolling position.
	 */
	protected Point scrollPos=new Point(0,0);
	/**
	 * The next scroll position.
	 */
	protected Point nextScrollPos=null;
	/**
	 * Cached content size.
	 */
	protected Dimension contentSize=new Dimension(0,0);
	/**
	 * Cached scroll size.
	 */
	protected Dimension scrollSize=new Dimension(0,0);
	
	@Override
	public void render(Context context) {
		doOperation(context,subContext->{
			context.getInterface().window(context.getRect());
			getComponent().render(subContext);
			Rectangle a=context.getRect(),b=subContext.getRect();
			if (b.width<a.width) {
				fillEmptySpace(context,new Rectangle(a.x+b.width,a.y,a.width-b.width,b.height));
			}
			if (b.height<a.height) {
				fillEmptySpace(context,new Rectangle(a.x,a.y+b.height,b.width,a.height-b.height));
			}
			if (b.width<a.width && b.height<a.height) {
				fillEmptySpace(context,new Rectangle(a.x+b.width,a.y+b.height,a.width-b.width,a.height-b.height));
			}
			context.getInterface().restore();
		});
	}

	@Override
	public void handleScroll(Context context, int diff) {
		Context sContext=doOperation(context,subContext->getComponent().handleScroll(subContext,diff));
		if (context.isHovered()) {
			if (isScrollingY()) scrollPos.translate(0,diff);
			else if (isScrollingX()) scrollPos.translate(diff,0);
			clampScrollPos(context.getSize(),sContext.getSize());
		}
	}
	
	@Override
	public Context doOperation (Context context, Consumer<Context> operation) {
		tempContext=context;
		Context subContext=IComponentProxy.super.doOperation(context,operation);
		if (nextScrollPos!=null) {
			scrollPos=nextScrollPos;
			nextScrollPos=null;
		}
		clampScrollPos(context.getSize(),subContext.getSize());
		contentSize=subContext.getSize();
		scrollSize=context.getSize();
		return subContext;
	}
	
	@Override
	public Context getContext (Context context) {
		Context subContext=new Context(context,context.getSize().width,new Point(-scrollPos.x,-scrollPos.y),true,true);
		getComponent().getHeight(subContext);
		int height=getScrollHeight(context,subContext.getSize().height);
		context.setHeight(height);
		return new Context(context,getComponentWidth(context),new Point(-scrollPos.x,-scrollPos.y),true,context.isHovered());
	}
	
	/**
	 * Get the current scroll position.
	 * @return the current scroll position
	 */
	public Point getScrollPos() {
		return new Point(scrollPos);
	}
	
	/**
	 * Set the horizontal scroll position
	 * @param scrollPos the new scroll position
	 */
	public void setScrollPosX (int scrollPos) {
		if (nextScrollPos==null) nextScrollPos=new Point(scrollPos,this.scrollPos.y);
		else nextScrollPos.x=scrollPos;
	}
	
	/**
	 * Set the vertical scroll position
	 * @param scrollPos the new scroll position
	 */
	public void setScrollPosY (int scrollPos) {
		if (nextScrollPos==null) nextScrollPos=new Point(this.scrollPos.x,scrollPos);
		else nextScrollPos.y=scrollPos;
	}
	
	/**
	 * Get cached content size.
	 * @return the content size from the last operation
	 */
	public Dimension getContentSize() {
		return contentSize;
	}
	
	/**
	 * Get cached scroll size.
	 * @return the scroll size from the last operation
	 */
	public Dimension getScrollSize() {
		return scrollSize;
	}
	
	/**
	 * Returns whether horizontal scrolling is happening.
	 * @return whether horizontal scrolling is happening
	 */
	public boolean isScrollingX() {
		return contentSize.width>scrollSize.width;
	}
	
	/**
	 * Returns whether vertical scrolling is happening.
	 * @return whether vertical scrolling is happening
	 */
	public boolean isScrollingY() {
		return contentSize.height>scrollSize.height;
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
	
	@Override
	public final int getHeight (int height) {
		return getScrollHeight(tempContext,height);
	}
	
	public abstract void fillEmptySpace (Context context, Rectangle rect);
}
