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
	 * {@link Toggleable} that can be toggled by the user.
	 */
	protected Toggleable toggle;
	/**
	 * Cached combined height of children.
	 */
	protected int childHeight=0;
	/**
	 * Cached container scroll height.
	 */
	protected int containerHeight=0;
	/**
	 * Cached value of whether scrolling is happening.
	 */
	protected boolean scroll=false;
	/**
	 * Current scroll offset.
	 */
	protected int scrollPosition=0;
	
	/**
	 * Constructor.
	 * @param title the caption for the container
	 * @param description the description for this component
	 * @param renderer the {@link Renderer} for the container
	 * @param open the {@link Toggleable} for {@link #open}
	 * @param animation the animation for this container
	 * @param toggle the {@link Toggleable} to be toggled by the user
	 */
	public CollapsibleContainer (String title, String description, Renderer renderer, Toggleable open, Animation animation, Toggleable toggle) {
		super(title,description,renderer);
		container=new Container(title,null,renderer);
		this.open=new AnimatedToggleable(open,animation);
		this.toggle=toggle;
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
			// Pre-calculate clipping rectangle
			Context subContext=getSubContext(context,open.getValue()==1);
			container.getHeight(subContext);
			Rectangle rect=getClipRect(context,subContext.getSize().height);
			boolean onTop=open.getValue()==1;
			if (rect!=null) {
				onTop=rect.contains(context.getInterface().getMouse());
				context.getInterface().window(rect);
			}
			subContext=getSubContext(context,onTop);
			// Render component
			container.render(subContext);
			if (rect!=null) context.getInterface().restore();
			if (subContext.isHovered()) context.setDescription(subContext.getDescription());
			context.setHeight(getRenderHeight(subContext.getSize().height));
			scrollPosition=renderer.renderScrollBar(context,hasFocus(context),isActive(),scroll,childHeight,scrollPosition);
			if (scrollPosition>childHeight-containerHeight) scrollPosition=childHeight-containerHeight;
			if (scrollPosition<0) scrollPosition=0;
		}
		renderer.renderBorder(context,hasFocus(context),isActive(),open.getValue()!=0);
	}
	
	/**
	 * Handles a mouse button state change.
	 */
	@Override
	public void handleButton (Context context, int button) {
		context.setHeight(renderer.getHeight(open.getValue()!=0));
		if (context.isClicked() && button==Interface.LBUTTON) {
			if (toggle!=null) toggle.toggle();
		} else if (context.isHovered() && button==Interface.RBUTTON && context.getInterface().getButton(Interface.RBUTTON)) {
			open.toggle();
		}
		if (open.getValue()==1) {
			// Pre-calculate clipping rectangle and update focus state
			Context subContext=getSubContext(context,true);
			container.getHeight(subContext);
			context.setHeight(getRenderHeight(subContext.getSize().height));
			updateFocus(context,button);
			// Handle button click with proper onTop masking
			boolean onTop=true;
			Rectangle rect=getClipRect(context,subContext.getSize().height);
			if (rect!=null) onTop=rect.contains(context.getInterface().getMouse());
			subContext=getSubContext(context,onTop);
			container.handleButton(subContext,button);
			context.setHeight(getRenderHeight(subContext.getSize().height));
			if (subContext.focusReleased()) context.releaseFocus();
		} else super.handleButton(context,button);
	}
	
	/**
	 * Handle a key being typed.
	 */
	@Override
	public void handleKey (Context context, int scancode) {
		if (open.getValue()==1) {
			Context subContext=getSubContext(context,true);
			container.handleKey(subContext,scancode);
			context.setHeight(getRenderHeight(subContext.getSize().height));
		} else super.handleKey(context,scancode);
	}
	
	/**
	 * Scroll scroll bar.
	 */
	@Override
	public void handleScroll (Context context, int diff) {
		if (open.getValue()==1) {
			Context subContext=getSubContext(context,true);
			container.handleKey(subContext,diff);
			context.setHeight(getRenderHeight(subContext.getSize().height));
			if (subContext.isHovered()) {
				scrollPosition+=diff;
				if (scrollPosition>childHeight-containerHeight) scrollPosition=childHeight-containerHeight;
				if (scrollPosition<0) scrollPosition=0;
			}
		} else super.handleKey(context,diff);
	}
	
	/**
	 * Returns the current height, accounting for whether the container is open or closed.
	 */
	@Override
	public void getHeight (Context context) {
		if (open.getValue()!=0) {
			Context subContext=getSubContext(context,true);
			container.getHeight(subContext);
			context.setHeight(getRenderHeight(subContext.getSize().height));
		} else super.getHeight(context);
	}
	
	/**
	 * Handle the GUI being opened.
	 */
	@Override
	public void enter (Context context) {
		if (open.getValue()==1) {
			Context subContext=getSubContext(context,true);
			container.enter(subContext);
			context.setHeight(getRenderHeight(subContext.getSize().height));
		} else super.enter(context);
	}
	
	/**
	 * Handle the GUI being closed.
	 */
	@Override
	public void exit (Context context) {
		if (open.getValue()==1) {
			Context subContext=getSubContext(context,true);
			container.exit(subContext);
			context.setHeight(getRenderHeight(subContext.getSize().height));
		} else super.exit(context);
	}
	
	/**
	 * Method to determine whether title bar is active or not.
	 * @return set to true, if title bar is active
	 */
	protected boolean isActive() {
		if (toggle==null) return true;
		return toggle.isOn();
	}
	
	/**
	 * Returns the vertical container offset.
	 * @return vertical offset
	 */
	protected int getContainerOffset() {
		if (scrollPosition>childHeight-containerHeight) scrollPosition=childHeight-containerHeight;
		if (scrollPosition<0) scrollPosition=0;
		return (int)(renderer.getHeight(open.getValue()!=0)-scrollPosition-(1-open.getValue())*containerHeight);
	}
	
	/**
	 * Get the height of the container, accounting for scrolling.
	 * @param childHeight the total height of the children
	 * @return the scroll height
	 */
	protected int getScrollHeight (int childHeight) {
		return childHeight;
	}
	
	/**
	 * Get the visible container height.
	 * @param childHeight the total height of the children
	 * @return the visible height
	 */
	protected int getRenderHeight (int childHeight) {
		this.childHeight=childHeight;
		containerHeight=getScrollHeight(childHeight);
		scroll=childHeight>containerHeight;
		if (scrollPosition>childHeight-containerHeight) scrollPosition=childHeight-containerHeight;
		if (scrollPosition<0) scrollPosition=0;
		return (int)(containerHeight*open.getValue()+renderer.getHeight(open.getValue()!=0)+renderer.getBottomBorder());
	}
	
	/**
	 * Returns the clipping rectangle for the container.
	 * @param context the context for this component
	 * @param height the height of the container
	 * @return the clipping rectangle
	 */
	protected Rectangle getClipRect (Context context, int height) {
		return new Rectangle(context.getPos().x+renderer.getLeftBorder(scroll),context.getPos().y+renderer.getHeight(open.getValue()!=0),context.getSize().width-renderer.getLeftBorder(scroll)-renderer.getRightBorder(scroll),getRenderHeight(height)-renderer.getHeight(open.getValue()!=0)-renderer.getBottomBorder());
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

	/**
	 * Create sub-context for container.
	 * @param context the current context
	 * @param onTop whether the context should be on top
	 * @return the context for the container
	 */
	protected Context getSubContext (Context context, boolean onTop) {
		return new Context(context,renderer.getLeftBorder(scroll),renderer.getRightBorder(scroll),getContainerOffset(),hasFocus(context),onTop);
	}
}
