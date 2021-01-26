package com.lukflug.panelstudio.container;

import java.awt.Point;

import com.lukflug.panelstudio.base.Context;
import com.lukflug.panelstudio.base.IBoolean;
import com.lukflug.panelstudio.component.IComponent;
import com.lukflug.panelstudio.theme.ContainerRenderer;

/**
 * Container with contents arranged vertically.
 * @author lukflug
 */
public class VerticalContainer extends Container<IComponent> {
	/**
	 * Constructor.
	 * @param title the caption for this component
	 * @param description the description for this component
	 * @param visible whether this component is visible
	 * @param renderer the renderer for this container
	 */
	public VerticalContainer (String title, String description, IBoolean visible, ContainerRenderer renderer) {
		super(title,description,visible,renderer);
	}
	
	@Override
	protected void doContextSensitiveLoop (Context context, ContextSensitiveConsumer<IComponent> function) {
		int posy[]={renderer.getTop()};
		doContextlessLoop(component->{
			Context subContext=getSubContext(context,posy[0]);
			function.accept(context,component);
			if (subContext.focusReleased()) context.releaseFocus();
			else if (subContext.foucsRequested()) context.requestFocus();
			posy[0]+=subContext.getSize().height+renderer.getBorder();
		});
		context.setHeight(posy[0]-renderer.getBorder()+renderer.getBottom());
	}
	
	/**
	 * Create sub-context for child component.
	 * @param context the current context
	 * @param posy the vertical position of the child component
	 * @return the context for the child component
	 */
	protected Context getSubContext (Context context, int posy) {
		return new Context(context,context.getSize().width-renderer.getLeft()-renderer.getRight(),new Point(renderer.getLeft(),posy),context.hasFocus(),true);
	}
}
