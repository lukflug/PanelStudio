package com.lukflug.panelstudio.hud;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;

import com.lukflug.panelstudio.Animation;
import com.lukflug.panelstudio.Context;
import com.lukflug.panelstudio.DraggableContainer;
import com.lukflug.panelstudio.FixedComponent;
import com.lukflug.panelstudio.Interface;
import com.lukflug.panelstudio.settings.Toggleable;
import com.lukflug.panelstudio.theme.ColorScheme;
import com.lukflug.panelstudio.theme.Renderer;

/**
 * Panel holding an HUD component.
 * @author lukflug
 */
public class HUDPanel extends DraggableContainer {
	/**
	 * Renderer to be used when GUI is open.
	 */
	protected Renderer actualRenderer;
	/**
	 * Dummy renderer to be used when GUI is cloed and container is hidden.
	 */
	protected NullRenderer nullRenderer=new NullRenderer();
	/**
	 * Whether GUI is open.
	 */
	protected Toggleable guiOpen;
	/**
	 * The HUD component.
	 */
	protected FixedComponent component;
	
	/**
	 * Constructor.
	 * @param component the component
	 * @param renderer the renderer for this container
	 * @param open toggleable indicating whether the container is open or closed
	 * @param animation the animation for opening and closing this container
	 * @param guiOpen whether to accept input and render container itself or not
	 */
	public HUDPanel(FixedComponent component, Renderer renderer, Toggleable open, Animation animation, Toggleable guiOpen) {
		super(component.getTitle(),renderer,open,animation,new Point(0,0),0);
		addComponent(component);
		this.actualRenderer=renderer;
		this.guiOpen=guiOpen;
		this.component=component;
	}
	
	/**
	 * Does not renderer container itself, when GUI is closed.
	 */
	@Override
	public void render (Context context) {
		if (guiOpen.isOn()) renderer=actualRenderer;
		else renderer=nullRenderer;
		super.render(context);
	}
	
	/**
	 * Mask out input, if GUI is turned off.
	 */
	@Override
	public void handleButton (Context context, int button) {
		if (guiOpen.isOn()) super.handleButton(context,button);
	}

	/**
	 * Mask out input, if GUI is turned off.
	 */
	@Override
	public void handleScroll (Context context, int diff) {
		if (guiOpen.isOn()) super.handleScroll(context,diff);
	}
	
	/**
	 * Gets position from child component.
	 */
	@Override
	public Point getPosition (Interface inter) {
		return component.getPosition(inter);
	}

	/**
	 * Sets position of child component.
	 */
	@Override
	public void setPosition (Interface inter, Point position) {
		component.setPosition(inter,position);
	}
	
	/**
	 * Get the child component width.
	 */
	public int getWidth() {
		return component.getWidth()+actualRenderer.getBorder()*2;
	}
	
	
	/**
	 * Dummy for a {@link Renderer}.
	 * @author lukflug
	 */
	protected class NullRenderer implements Renderer {
		@Override
		public int getHeight() {
			return actualRenderer.getHeight();
		}

		@Override
		public int getOffset() {
			return actualRenderer.getOffset();
		}

		@Override
		public int getBorder() {
			return actualRenderer.getBorder();
		}

		@Override
		public void renderTitle(Context context, String text, boolean focus) {
		}

		@Override
		public void renderTitle(Context context, String text, boolean focus, boolean active) {
		}

		@Override
		public void renderTitle(Context context, String text, boolean focus, boolean active, boolean open) {
		}

		@Override
		public void renderRect(Context context, String text, boolean focus, boolean active, Rectangle rectangle, boolean overlay) {
		}

		@Override
		public void renderBackground(Context context, boolean focus) {
		}

		@Override
		public void renderBorder(Context context, boolean focus, boolean active, boolean open) {
		}

		@Override
		public Color getMainColor(boolean focus, boolean active) {
			return new Color(0,0,0,0);
		}

		@Override
		public Color getBackgroundColor(boolean focus) {
			return new Color(0,0,0,0);
		}

		@Override
		public Color getFontColor(boolean focus) {
			return new Color(0,0,0,0);
		}

		@Override
		public ColorScheme getDefaultColorScheme() {
			return new ColorScheme() {
				@Override
				public Color getActiveColor() {
					return new Color(0,0,0,0);
				}

				@Override
				public Color getInactiveColor() {
					return new Color(0,0,0,0);
				}

				@Override
				public Color getBackgroundColor() {
					return new Color(0,0,0,0);
				}

				@Override
				public Color getOutlineColor() {
					return new Color(0,0,0,0);
				}

				@Override
				public Color getFontColor() {
					return new Color(0,0,0,0);
				}

				@Override
				public int getOpacity() {
					return 0;
				}
			};
		}

		@Override
		public void overrideColorScheme(ColorScheme scheme) {
		}

		@Override
		public void restoreColorScheme() {
		}
	}
}
