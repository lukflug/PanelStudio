package com.lukflug.panelstudio.tabgui;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import com.lukflug.panelstudio.Context;
import com.lukflug.panelstudio.theme.Renderer;

/**
 * Element of TabGUI.
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
	 * Current child component having focus.
	 */
	protected TabGUIComponent component=null;
	
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
		renderer.renderBackground(context,0,renderer.getHeight());
		Point p=context.getPos();
		p.translate(1,0);
		for (TabGUIComponent component: components) {
			context.getInterface().drawString(p,component.getTitle(),renderer.getColorScheme().getFontColor());
			p.translate(0,renderer.getHeight());
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
		// TODO Auto-generated method stub
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
