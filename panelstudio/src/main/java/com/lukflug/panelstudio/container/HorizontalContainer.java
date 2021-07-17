package com.lukflug.panelstudio.container;

import java.awt.Point;
import java.util.concurrent.atomic.AtomicInteger;

import com.lukflug.panelstudio.base.Context;
import com.lukflug.panelstudio.component.IHorizontalComponent;
import com.lukflug.panelstudio.setting.ILabeled;
import com.lukflug.panelstudio.theme.IContainerRenderer;

/**
 * Container with components arranged horizontally.
 * @author lukflug
 */
public class HorizontalContainer extends Container<IHorizontalComponent> {
	/**
	 * Constructor.
	 * @param label the label for the component
	 * @param renderer the renderer for this container
	 */
	public HorizontalContainer(ILabeled label, IContainerRenderer renderer) {
		super(label, renderer);
	}
	
	@Override
	protected void doContextSensitiveLoop (Context context, ContextSensitiveConsumer<IHorizontalComponent> function) {
		AtomicInteger availableWidth=new AtomicInteger(context.getSize().width-renderer.getLeft()-renderer.getRight()+renderer.getBorder());
		AtomicInteger totalWeight=new AtomicInteger(0);
		doContextlessLoop(component->{
		    availableWidth.addAndGet(-component.getWidth(context.getInterface())-renderer.getBorder());
		    totalWeight.addAndGet(component.getWeight());
		});
		double weightFactor=availableWidth.get()/(double)totalWeight.get();
		AtomicInteger x=new AtomicInteger(renderer.getLeft());
		AtomicInteger spentWeight=new AtomicInteger(0);
		AtomicInteger height=new AtomicInteger(0);
		doContextlessLoop(component->{
			int start=(int)Math.round(spentWeight.get()*weightFactor);
			int end=(int)Math.round((spentWeight.get()+component.getWeight())*weightFactor);
		    int componentWidth=component.getWidth(context.getInterface())+end-start;
		    int componentPosition=x.get()+start;
		    Context subContext=getSubContext(context,componentPosition,componentWidth);
			function.accept(subContext,component);
			if (subContext.focusReleased()) context.releaseFocus();
			else if (subContext.foucsRequested()) context.requestFocus();
		    x.addAndGet(component.getWidth(context.getInterface())+renderer.getBorder());
		    spentWeight.addAndGet(component.getWeight());
		    if (subContext.getSize().height>height.get()) height.set(subContext.getSize().height);
		});
		context.setHeight(height.get());
	}
	
	/**
	 * Create sub-context for child component.
	 * @param context the current context
	 * @param posx the horizontal position of the child component
	 * @param width the width of the child component
	 * @return the context for the child component
	 */
	protected Context getSubContext (Context context, int posx, int width) {
		return new Context(context,width,new Point(posx,renderer.getTop()),context.hasFocus(),true);
	}
}
