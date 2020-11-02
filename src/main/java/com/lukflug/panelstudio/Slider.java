package com.lukflug.panelstudio;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;

import com.lukflug.panelstudio.theme.Renderer;

/**
 * Base class for components that are sliders.
 * @author lukflug
 */
public abstract class Slider extends FocusableComponent {
	/**
	 * Constructor.
	 * @param title caption of the slider
	 * @param renderer renderer for the slider
	 */
	public Slider(String title, Renderer renderer) {
		super(title, renderer);
	}

	/**
	 * Renderers the slider.
	 */
	@Override
	public void render (Context context) {
		super.render(context);
		if (context.isClicked()) {
			double value=(context.getInterface().getMouse().x-context.getPos().x)/(double)context.getSize().width;
			if (value<0) value=0;
			else if (value>1) value=1;
			setValue(value);
		}
		renderer.renderRect(context,"",hasFocus(context),false,new Rectangle(new Point((int)(context.getPos().x+context.getSize().width*getValue()),context.getPos().y),new Dimension((int)(context.getSize().width*(1-getValue())),renderer.getHeight())),false);
		renderer.renderRect(context,title,hasFocus(context),true,new Rectangle(context.getPos(),new Dimension((int)(context.getSize().width*getValue()),renderer.getHeight())),true);
	}

	/**
	 * Abstract method to get the current slider value.
	 * @return the slider value between 0 (empty) and 1 (full)
	 */
	protected abstract double getValue();
	/**
	 * Abstract method to update the slider value.
	 * @param value the slider value between 0 (empty) and 1 (full)
	 */
	protected abstract void setValue (double value);
}
