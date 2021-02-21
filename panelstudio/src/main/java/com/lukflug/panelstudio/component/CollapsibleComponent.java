package com.lukflug.panelstudio.component;

import java.awt.Point;

import com.lukflug.panelstudio.base.AnimatedToggleable;
import com.lukflug.panelstudio.base.Animation;
import com.lukflug.panelstudio.base.Context;
import com.lukflug.panelstudio.base.IToggleable;

/**
 * A component that can be collapsed.
 * @author lukflug
 */
public class CollapsibleComponent extends ComponentProxy {
	/**
	 * Animation to be used.
	 */
	protected AnimatedToggleable toggle;
	
	/**
	 * Constructor.
	 * @param component the component to be wrapped
	 * @param toggle the toggleable indicating whether the component is open
	 */
	public CollapsibleComponent (IComponent component, IToggleable toggle, Animation animation) {
		super(component);
		this.toggle=new AnimatedToggleable(toggle,animation);
	}
	
	@Override
	public void render(Context context) {
		doOperation(context,subContext->{
			context.getInterface().window(context.getRect());
			component.render(subContext);
			context.getInterface().restore();
		});
	}
	
	@Override
	public boolean isVisible() {
		return component.isVisible()&&(toggle.getValue()!=0);
	}
	
	@Override
	protected Context getContext (Context context) {
		Context subContext=new Context(context,context.getSize().width,new Point(0,0),context.hasFocus(),context.onTop(),this);
		component.getHeight(subContext);
		int height=getHeight(subContext.getSize().height);
		int offset=height-subContext.getSize().height;
		context.setHeight(height);
		return new Context(context,context.getSize().width,new Point(0,offset),context.hasFocus(),context.onTop(),this);
	}
	
	@Override
	protected int getHeight (int height) {
		return (int)(toggle.getValue()*height);
	}
	
	/**
	 * Returns the current toggleable used.
	 * @return the current {@link #toggle}
	 */
	public AnimatedToggleable getToggle() {
		return toggle;
	}
}
