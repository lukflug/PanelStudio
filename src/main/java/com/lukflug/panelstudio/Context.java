package com.lukflug.panelstudio;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;

/**
 * A class for the communication between a component and its parent.
 * @author lukflug
 */
public class Context {
	/**
	 * The current {@link Interface}.
	 */
	private Interface inter;
	/**
	 * The current {@link FoucsManager}.
	 */
	private FocusManager manager;
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
	 * Constructor that should be used when a parent is calling a method by the child.
	 * {@link #inter}, {@link #manager} and {@link #onTop} are inherited without modification.
	 * @param context the context of the parent
	 * @param border the horizontal border (left and right) for the child
	 * @param offset the vertical position of the child relative to the parent's position
	 * @param focus focus state of the parent
	 */
	public Context (Context context, int border, int offset, boolean focus) {
		inter=context.getInterface();
		manager=context.getFocusManager();
		size=new Dimension(context.getSize().width-border*2,0);
		position=new Point(context.getPos());
		position.translate(border,offset);
		this.focus=context.hasFocus()&&focus;
		onTop=context.onTop();
	}
	
	/**
	 * Constructor that should be used by the root parent (i.e. {@link ClickGUI}).
	 * @param inter the current {@link Interface}
	 * @param manager the current {@link FocusManager}
	 * @param width the width of the component
	 * @param position the position of the component
	 * @param focus set to false, to disable the component from having focus
	 * @param onTop set to false, if a component is above another component at the current cursor position
	 */
	public Context (Interface inter, FocusManager manager, int width, Point position, boolean focus, boolean onTop) {
		this.inter=inter;
		this.manager=manager;
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
	 * Returns the current {@link FocusManager}.
	 * @return the current {@link FocusManager}
	 */
	public FocusManager getFocusManager() {
		return manager;
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
	 * Get mouse hover state.
	 * @return set to true, if mouse is hovering and component isn't below another one 
	 */
	public boolean isHovered() {
		return inter.getMouse().x>=position.x && inter.getMouse().x<=position.x+size.width && inter.getMouse().y>=position.y && inter.getMouse().y<=position.y+size.height && onTop;
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
}
