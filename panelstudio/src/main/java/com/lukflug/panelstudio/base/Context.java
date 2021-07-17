package com.lukflug.panelstudio.base;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;

import com.lukflug.panelstudio.popup.IPopupDisplayer;

/**
 * A class for the communication between a component and its parent.
 * @author lukflug
 */
public final class Context {
	/**
	 * The current {@link Interface}.
	 */
	private final IInterface inter;
	/**
	 * The size of the component.
	 * The width is decided by the parent, while the height is decided by the component.
	 */
	private final Dimension size;
	/**
	 * The position of the component, determined by its parent.
	 */
	private final Point position;
	/**
	 * The focus state of the parent.
	 * The component cannot have focus, if the parent doesn't.
	 */
	private final boolean focus;
	/**
	 * Set to false, if another component is in front of another component, at the current mouse position.
	 * Only has meaning if the mouse cursor is hovering over the component.
	 */
	private final boolean onTop;
	/**
	 * Set to true by the child using {@link #requestFocus()}.
	 * Used to indicate that the focus within the parent should be given to the child component.
	 */
	private boolean focusRequested=false;
	/**
	 * Set to true by the child, if focus should not be requested.
	 */
	private boolean focusOverride=false;
	/**
	 * Description set by the child to be displayed when hovered.
	 */
	private Description description=null;
	private IPopupDisplayer popupDisplayer=null;
	
	/**
	 * Constructor that should be used when a parent is calling a method by the child.
	 * {@link #inter} and {@link #onTop} are inherited without modification.
	 * @param context the context of the parent
	 * @param width the width of the component
	 * @param offset the relative position of the component
	 * @param focus focus state of the parent
	 * @param onTop whether component is in the front
	 */
	public Context (Context context, int width, Point offset, boolean focus, boolean onTop) {
		inter=context.getInterface();
		size=new Dimension(width,0);
		this.position=context.getPos();
		position.translate(offset.x,offset.y);
		this.focus=context.hasFocus()&&focus;
		this.onTop=context.onTop()&&onTop;
		this.popupDisplayer=context.getPopupDisplayer();
	}
	
	/**
	 * Constructor that should be used by the root parent.
	 * @param inter the current {@link Interface}
	 * @param width the width of the component
	 * @param position the position of the component
	 * @param focus set to false, to disable the component from having focus
	 * @param onTop set to false, if a component is above another component at the current cursor position
	 */
	public Context (IInterface inter, int width, Point position, boolean focus, boolean onTop) {
		this.inter=inter;
		size=new Dimension(width,0);
		this.position=new Point(position);
		this.focus=focus;
		this.onTop=onTop;
	}
	
	/**
	 * Returns the current {@link Interface}.
	 * @return the current {@link Interface}
	 */
	public IInterface getInterface() {
		return inter;
	}
	
	/**
	 * Returns the component size.
	 * @return the component size
	 */
	public Dimension getSize() {
		return new Dimension(size);
	}
	
	/**
	 * Sets the height of the component.
	 * @param height the component's height
	 */
	public void setHeight (int height) {
		size.height=height;
	}
	
	/**
	 * Returns the component position.
	 * @return the component position
	 */
	public Point getPos() {
		return new Point(position);
	}
	
	/**
	 * Returns the focus state of the parent.
	 * @return the focus state of the parent
	 */
	public boolean hasFocus() {
		return focus;
	}
	
	/**
	 * Check if component is the highest at cursor position.
	 * @return set to false, if another component is above this component at the current mouse cursor position
	 */
	public boolean onTop() {
		return onTop;
	}

	/**
	 * Used to indicate to the parent that the current component has to have focus within the parent.
	 */
	public void requestFocus() {
		if (!focusOverride) focusRequested=true;
	}

	/**
	 * Reverses {@link #requestFocus()} and ask parent not to request focus.
	 */
	public void releaseFocus() {
		focusRequested=false;
		focusOverride=true;
	}

	/**
	 * Returns {@link #focusRequested}.
	 * @return whether the child is requesting focus.
	 */
	public boolean foucsRequested() {
		return focusRequested && !focusOverride;
	}
	
	/**
	 * Returns {@link #focusOverride}.
	 * @return whether the parent may request focus.
	 */
	public boolean focusReleased() {
		return focusOverride;
	}
	
	/**
	 * Get mouse hover state.
	 * @return set to true, if mouse is hovering and component isn't below another one 
	 */
	public boolean isHovered() {
		return new Rectangle(position,size).contains(inter.getMouse()) && onTop;
	}
	
	/**
	 * Get mouse click state.
	 * @return set to true, if {@link #isHovered()} is true and the left mouse button is clicked
	 */
	public boolean isClicked (int button) {
		return isHovered() && inter.getButton(button);
	}
	
	/**
	 * Get rectangle indicating component position.
	 * @return construct a rectangle out of the current component {@link #position} and {@link #size}
	 */
	public Rectangle getRect() {
		return new Rectangle(position,size);
	}
	
	/**
	 * Get the description set by the child.
	 * @return the current description
	 */
	public Description getDescription() {
		return description;
	}
	
	/**
	 * Set the description when rendering.
	 * @param description the new description
	 */
	public void setDescription (Description description) {
		this.description=description;
	}
	
	public IPopupDisplayer getPopupDisplayer() {
		return popupDisplayer;
	}
	
	public void setPopupDisplayer (IPopupDisplayer popupDisplayer) {
		this.popupDisplayer=popupDisplayer;
	}
}
