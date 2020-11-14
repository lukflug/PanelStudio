package com.lukflug.panelstudio.tabgui;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

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
	 * Constructor.
	 * @param title caption of the container
	 * @param renderer the {@link TabGUIRenderer} for this container
	 */
	public TabGUIContainer (String title, TabGUIRenderer renderer) {
		this.title=title;
		this.renderer=renderer;
		components=new ArrayList<TabGUIComponent>();
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
		renderer.renderBackground(context,selected*renderer.getHeight(),renderer.getHeight());
		for (int i=0;i<components.size();i++) {
			TabGUIComponent component=components.get(i);
			renderer.renderCaption(context,component.getTitle(),i,renderer.getHeight(),component.isActive());
		}
		if (childOpen) {
			Point p=context.getPos();
			p.translate(context.getSize().width+renderer.getBorder(),selected*renderer.getHeight());
			Context subContext=new Context(context.getInterface(),context.getSize().width,p,context.hasFocus(),context.onTop());
			components.get(selected).render(subContext);
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
			} else if (renderer.isDownKey(scancode)) {
				if (++selected>=components.size()) selected=0;
			} else if (renderer.isSelectKey(scancode)) {
				if (components.get(selected).select()) childOpen=true;
			}
		} else {
			Point p=context.getPos();
			p.translate(context.getSize().width+renderer.getBorder(),selected*renderer.getHeight());
			Context subContext=new Context(context.getInterface(),context.getSize().width,p,context.hasFocus(),context.onTop());
			components.get(selected).handleKey(subContext,scancode);
		}
	}

	/**
	 * Returns the container height.
	 */
	@Override
	public void getHeight(Context context) {
		context.setHeight(renderer.getHeight()*components.size());
	}

	/**
	 * Do nothing.
	 */
	@Override
	public void exit(Context context) {
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
}
