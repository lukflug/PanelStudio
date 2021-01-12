package com.lukflug.panelstudio.tabgui;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import com.lukflug.panelstudio.Animation;
import com.lukflug.panelstudio.Context;
import com.lukflug.panelstudio.theme.Renderer;

/**
 * Element of TabGUI. Renders a list of child components.
 * @author lukflug
 */
public class TabGUIContainer implements TabGUIComponent {
	/**
	 * The caption of this component.
	 */
	protected String title;
	/**
	 * The {@link Renderer} for this component.
	 */
	protected TabGUIRenderer renderer;
	/**
	 * Child components.
	 */
	protected List<TabGUIComponent> components;
	/**
	 * Whether child component having focus.
	 */
	protected boolean childOpen=false;
	/**
	 * Index of the currently selected component.
	 */
	protected int selected=0;
	/**
	 * Object handling highlight animation.
	 */
	protected Animation selectedAnimation=null;
	
	/**
	 * Constructor.
	 * @param title caption of the container
	 * @param renderer the {@link TabGUIRenderer} for this container
	 * @param animation the animation for {@link #selectedAnimation}, may be null
	 */
	public TabGUIContainer (String title, TabGUIRenderer renderer, Animation animation) {
		this.title=title;
		this.renderer=renderer;
		components=new ArrayList<TabGUIComponent>();
		if (animation!=null) {
			animation.initValue(selected);
			selectedAnimation=animation;
		}
	}
	
	/**
	 * Add a component to this container.
	 * @param component the new component
	 */
	public void addComponent (TabGUIComponent component) {
		components.add(component);
	}
	
	/**
	 * Returns the component caption.
	 */
	@Override
	public String getTitle() {
		return title;
	}

	/**
	 * Render the container.
	 */
	@Override
	public void render(Context context) {
		getHeight(context);
		int offset=selected*renderer.getHeight();
		if (selectedAnimation!=null) offset=(int)(selectedAnimation.getValue()*renderer.getHeight());
		renderer.renderBackground(context,offset,renderer.getHeight());
		for (int i=0;i<components.size();i++) {
			TabGUIComponent component=components.get(i);
			renderer.renderCaption(context,component.getTitle(),i,renderer.getHeight(),component.isActive());
		}
		if (childOpen) {
			components.get(selected).render(getSubContext(context));
		}
	}

	/**
	 * Do nothing.
	 */
	@Override
	public void handleButton(Context context, int button) {
		getHeight(context);
	}

	/**
	 * Handle keyboard input.
	 */
	@Override
	public void handleKey(Context context, int scancode) {
		getHeight(context);
		if (renderer.isEscapeKey(scancode)) {
			childOpen=false;
		} else if (!childOpen) {
			if (renderer.isUpKey(scancode)) {
				if (--selected<0) selected=components.size()-1;
				if (selectedAnimation!=null) selectedAnimation.setValue(selected);
			} else if (renderer.isDownKey(scancode)) {
				if (++selected>=components.size()) selected=0;
				if (selectedAnimation!=null) selectedAnimation.setValue(selected);
			} else if (renderer.isSelectKey(scancode)) {
				if (components.get(selected).select()) childOpen=true;
			}
		} else {
			components.get(selected).handleKey(getSubContext(context),scancode);
		}
	}
	
	/**
	 * Do nothing.
	 */
	@Override
	public void handleScroll (Context context, int diff) {
		getHeight(context);
	}

	/**
	 * Returns the container height.
	 */
	@Override
	public void getHeight (Context context) {
		context.setHeight(renderer.getHeight()*components.size());
	}

	/**
	 * Do nothing.
	 */
	@Override
	public void enter (Context context) {
		getHeight(context);
	}

	/**
	 * Do nothing.
	 */
	@Override
	public void exit (Context context) {
		getHeight(context);
	}

	/**
	 * TabGUI container is inactive by default.
	 */
	@Override
	public boolean isActive() {
		return false;
	}

	/**
	 * Requests focus, if selected.
	 */
	@Override
	public boolean select() {
		return true;
	}
	
	/**
	 * Create a sub-context.
	 * @param context the current context
	 * @return a context for a child-component
	 */
	protected Context getSubContext (Context context) {
		Point p=context.getPos();
		p.translate(context.getSize().width+renderer.getBorder(),selected*renderer.getHeight());
		return new Context(context.getInterface(),context.getSize().width,p,context.hasFocus(),context.onTop());
	}

	/**
	 * Does nothing.
	 */
	@Override
	public void releaseFocus() {
	}
}
