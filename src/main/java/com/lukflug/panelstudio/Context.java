package com.lukflug.panelstudio;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;

/**
 * A class for the communication between a component and its parent.
 * @author lukflug
 */
public final class Context {
	/**
	 * The current {@link Interface}.
	 */
	private Interface inter;
	/**
	 * The size of the component.
	 * The width is decided by the parent, while the height is decided by the component.
	 */
	private Dimension size;
	/**
	 * The position of the component, determined by its parent.
	 */
	private Point position;
	/**
	 * The focus state of the parent.
	 * The component cannot have focus, if the parent doesn't.
	 */
	private boolean focus;
	/**
	 * Set to false, if another component is in front of another component, at the current mouse position.
	 * Only has meaning if the mouse cursor is hovering over the component.
	 */
	private boolean onTop;
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
	private String description=null;
	
	/**
	 * Constructor that should be used when a parent is calling a method by the child.
	 * {@link #inter} and {@link #onTop} are inherited without modification.
	 * @param context the context of the parent
	 * @param left the left horizontal border for the child
	 * @param right the right horizontal border for the child
	 * @param offset the vertical position of the child relative to the parent's position
	 * @param focus focus state of the parent
	 * @param onTop whether component is in the front
	 */
	public Context (Context context, int left, int right, int offset, boolean focus, boolean onTop) {
		inter=context.getInterface();
		size=new Dimension(context.getSize().width-left-right,0);
		position=new Point(context.getPos());
		position.translate(left,offset);
		this.focus=context.hasFocus()&&focus;
		this.onTop=context.onTop()&&onTop;
	}
	
	/**
	 * Constructor that should be used by the root parent (i.e. {@link ClickGUI}).
	 * @param inter the current {@link Interface}
	 * @param width the width of the component
	 * @param position the position of the component
	 * @param focus set to false, to disable the component from having focus
	 * @param onTop set to false, if a component is above another component at the current cursor position
	 */
	public Context (Interface inter, int width, Point position, boolean focus, boolean onTop) {
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
	public Interface getInterface() {
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
		focusRequested=true;
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
		return focusRequested;
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
	public boolean isClicked() {
		return isHovered() && inter.getButton(Interface.LBUTTON);
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
	public String getDescription() {
		return description;
	}
	
	/**
	 * Set the description when rendering.
	 * @param description the new description
	 */
	public void setDescription (String description) {
		this.description=description;
	}
}
