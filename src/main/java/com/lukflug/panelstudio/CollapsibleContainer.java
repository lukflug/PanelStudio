package com.lukflug.panelstudio;

import java.awt.Rectangle;

import com.lukflug.panelstudio.settings.AnimatedToggleable;
import com.lukflug.panelstudio.settings.Toggleable;
import com.lukflug.panelstudio.theme.Renderer;

/**
 * Container that can be closed and scrolled, so that its children can be hidden.
 * @author lukflug
 */
public class CollapsibleContainer extends FocusableComponent implements Toggleable {
	/**
	 * {@link Container} containing the children.
	 */
	protected Container container;
	/**
	 * {@link Toggleable} indicating whether the container is open or closed. 
	 */
	protected AnimatedToggleable open;
	/**
	 * Cached render height.
	 */
	protected int renderHeight;
	
	/**
	 * Constructor.
	 * @param title the caption for the container
	 * @param renderer the {@link Renderer} for the container
	 * @param open the {@link Toggleable} for {@link #open}
	 * @param animation the animation for this container
	 */
	public CollapsibleContainer (String title, Renderer renderer, Toggleable open, Animation animation) {
		super(title,renderer);
		container=new Container(title,renderer);
		this.open=new AnimatedToggleable(open,animation);
	}
	
	/**
	 * Add a component to the container.
	 * @param component the component to be added
	 */
	public void addComponent (Component component) {
		container.addComponent(component);
	}
	
	/**
	 * Renders a background, title bar, border and, if the container is open, the components of the container.
	 */
	@Override
	public void render (Context context) {
		getHeight(context);
		renderer.renderBackground(context,hasFocus(context));
		super.render(context);
		renderer.renderTitle(context,title,hasFocus(context),isActive(),open.getValue()!=0);
		if (open.getValue()!=0) {
			Context subContext=new Context(context,0,getContainerOffset(),hasFocus(context),open.getValue()==1);
			container.getHeight(subContext);
			context.getInterface().window(getClipRect(context,subContext.getSize().height));
			container.render(subContext);
			context.getInterface().restore();
			context.setHeight(getRenderHeight(subContext.getSize().height));
		}
		renderer.renderBorder(context,hasFocus(context),isActive(),open.getValue()!=0);
	}
	
	/**
	 * Handles a mouse button state change.
	 */
	@Override
	public void handleButton (Context context, int button) {
		if (open.getValue()==1) {
			Context subContext=new Context(context,0,getContainerOffset(),hasFocus(context));
			container.getHeight(subContext);
			boolean onTop=getClipRect(context,subContext.getSize().height).contains(context.getInterface().getMouse());
			subContext=new Context(context,0,getContainerOffset(),hasFocus(context),onTop);
			container.handleButton(subContext,button);
			context.setHeight(getRenderHeight(subContext.getSize().height));
			updateFocus(context,button);
		} else super.handleButton(context,button);
		if (context.isHovered() && context.getInterface().getMouse().y<=context.getPos().y+renderer.getHeight() && button==Interface.RBUTTON && context.getInterface().getButton(Interface.RBUTTON)) {
			open.toggle();
		}
	}
	
	/**
	 * Handle a key being typed.
	 */
	@Override
	public void handleKey (Context context, int scancode) {
		if (open.getValue()==1) {
			Context subContext=new Context(context,0,getContainerOffset(),hasFocus(context));
			container.handleKey(subContext,scancode);
			context.setHeight(getRenderHeight(subContext.getSize().height));
		} else super.handleKey(context,scancode);
	}
	
	/**
	 * Returns the current height, accounting for whether the container is open or closed.
	 */
	@Override
	public void getHeight (Context context) {
		if (open.getValue()!=0) {
			Context subContext=new Context(context,0,getContainerOffset(),hasFocus(context));
			container.getHeight(subContext);
			context.setHeight(getRenderHeight(subContext.getSize().height));
		} else super.getHeight(context);
	}
	
	/**
	 * Handle the GUI being closed.
	 */
	@Override
	public void exit (Context context) {
		if (open.getValue()==1) {
			Context subContext=new Context(context,0,getContainerOffset(),hasFocus(context));
			container.exit(subContext);
			context.setHeight(getRenderHeight(subContext.getSize().height));
		} else super.exit(context);
	}
	
	/**
	 * Method to determine whether title bar is active or not.
	 * @return set to true, if title bar is active
	 */
	protected boolean isActive() {
		return true;
	}
	
	/**
	 * Returns the vertical container offset
	 * @return vertical offset
	 */
	protected int getContainerOffset() {
		return (int)(renderer.getHeight()-(1-open.getValue())*renderHeight);
	}
	
	/**
	 * Get the container height.
	 * @param containerHeight the total height of the children
	 * @return the visible height
	 */
	protected int getRenderHeight (int containerHeight) {
		renderHeight=containerHeight;
		return (int)(containerHeight*open.getValue()+renderer.getHeight());
	}
	
	/**
	 * Returns the clipping rectangle for the container
	 * @param context for this component
	 * @param height the height of the container
	 * @return the clipping rectangle
	 */
	protected Rectangle getClipRect (Context context, int height) {
		return new Rectangle(context.getPos().x,context.getPos().y+renderer.getHeight(),context.getSize().width,getRenderHeight(height)-renderer.getHeight());
	}

	/**
	 * Toggle the open state. And release focus of children if closing.
	 */
	@Override
	public void toggle() {
		open.toggle();
		if (!open.isOn()) container.releaseFocus();
	}

	/**
	 * Get the open state.
	 */
	@Override
	public boolean isOn() {
		return open.isOn();
	}
}
