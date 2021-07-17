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
public abstract class CollapsibleComponent<T extends IComponent> implements IComponentProxy<T> {
	/**
	 * Animation to be used.
	 */
	protected AnimatedToggleable toggle;
	
	/**
	 * Constructor.
	 * @param component the component to be wrapped
	 * @param toggle the toggleable indicating whether the component is open
	 */
	public CollapsibleComponent (IToggleable toggle, Animation animation) {
		this.toggle=new AnimatedToggleable(toggle,animation);
	}
	
	public CollapsibleComponent (AnimatedToggleable toggle) {
		this.toggle=toggle;
	}
	
	@Override
	public void render(Context context) {
		doOperation(context,subContext->{
			context.getInterface().window(context.getRect());
			getComponent().render(subContext);
			context.getInterface().restore();
		});
	}
	
	@Override
	public boolean isVisible() {
		return getComponent().isVisible()&&(toggle.getValue()!=0);
	}
	
	@Override
	public Context getContext (Context context) {
		Context subContext=new Context(context,context.getSize().width,new Point(0,0),true,true);
		getComponent().getHeight(subContext);
		int height=getHeight(subContext.getSize().height);
		int offset=height-subContext.getSize().height;
		context.setHeight(height);
		return new Context(context,context.getSize().width,new Point(0,offset),true,context.isHovered());
	}
	
	@Override
	public int getHeight (int height) {
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
