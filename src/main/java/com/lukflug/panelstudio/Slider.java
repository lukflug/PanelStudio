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
	protected boolean attached=false; 
	
	/**
	 * Constructor.
	 * @param title caption of the slider
	 * @param description the description for this component
	 * @param renderer renderer for the slider
	 */
	public Slider(String title, String description, Renderer renderer) {
		super(title,description,renderer);
	}

	/**
	 * Renders the slider.
	 */
	@Override
	public void render (Context context) {
		super.render(context);
		if (attached) {
			double value=(context.getInterface().getMouse().x-context.getPos().x)/(double)(context.getSize().width-1);
			if (value<0) value=0;
			else if (value>1) value=1;
			setValue(value);
		}
		if (!context.getInterface().getButton(Interface.LBUTTON)) {
			attached=false;
		}
		renderer.renderRect(context,"",hasFocus(context),false,new Rectangle(new Point(context.getPos().x+(int)(context.getSize().width*getValue()),context.getPos().y),new Dimension((int)(context.getSize().width*(1-getValue())),renderer.getHeight(false))),false);
		renderer.renderRect(context,title,hasFocus(context),true,new Rectangle(context.getPos(),new Dimension((int)(context.getSize().width*getValue()),renderer.getHeight(false))),true);
	}
	
	/**
	 * Sets {@link #attached}, when clicked.
	 */
	@Override
	public void handleButton (Context context, int button) {
		super.handleButton(context,button);
		if (button==Interface.LBUTTON && context.isClicked()) {
			attached=true;
		}
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
