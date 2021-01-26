package com.lukflug.panelstudio.container;

import java.awt.Point;

import com.lukflug.panelstudio.base.Context;
import com.lukflug.panelstudio.base.IBoolean;
import com.lukflug.panelstudio.component.IHorizontalComponent;
import com.lukflug.panelstudio.theme.ContainerRenderer;

/**
 * Container with components arranged horizontally.
 * @author lukflug
 */
public class HorizontalContainer extends Container<IHorizontalComponent> {
	/**
	 * Constructor.
	 * @param title the caption for this component
	 * @param description the description for this component
	 * @param visible whether this component is visible
	 * @param renderer the renderer for this container
	 */
	public HorizontalContainer(String title, String description, IBoolean visible, ContainerRenderer renderer) {
		super(title, description, visible, renderer);
	}
	
	@Override
	protected void doContextSensitiveLoop (Context context, ContextSensitiveConsumer<IHorizontalComponent> function) {
		int availableWidth[]= {context.getSize().width-renderer.getLeft()-renderer.getRight()};
		int totalWeight[]= {0};
		doContextlessLoop(component->{
		    availableWidth[0]-=component.getWidth(context.getInterface())+renderer.getBorder();
		    totalWeight[0]+=component.getWeight();
		});
		double weightFactor=availableWidth[0]/(double)totalWeight[0];
		int x[]= {renderer.getLeft()};
		int spentWeight[]={0};
		int height[]={0};
		doContextlessLoop(component->{
		    int componentWidth=(int)(component.getWidth(context.getInterface())-component.getWeight()*weightFactor);
		    int componentPosition=(int)(x[0]+spentWeight[0]*weightFactor);
		    Context subContext=getSubContext(context,componentPosition,componentWidth);
			function.accept(subContext,component);
			if (subContext.focusReleased()) context.releaseFocus();
			else if (subContext.foucsRequested()) context.requestFocus();
		    x[0]+=component.getWidth(context.getInterface())+renderer.getBorder();
		    spentWeight[0]+=component.getWeight();
		    if (subContext.getSize().height>height[0]) height[0]=subContext.getSize().height;
		});
		context.setHeight(height[0]);
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
