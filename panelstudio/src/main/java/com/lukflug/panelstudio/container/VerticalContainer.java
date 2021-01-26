package com.lukflug.panelstudio.container;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

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
	 * List of child component.
	 */
	protected List<IComponent> components;
	
	/**
	 * Constructor.
	 * @param title the caption for this component
	 * @param description the description for this component
	 * @param visible whether this component is visible
	 * @param renderer the renderer for this container
	 */
	public VerticalContainer (String title, String description, IBoolean visible, ContainerRenderer renderer) {
		super(title,description,visible,renderer);
		components=new ArrayList<IComponent>();
	}
	
	/**
	 * Add a component to the container.
	 * @param component the component to be added
	 */
	public void addComponent (IComponent component) {
		components.add(component);
	}
	
	@Override
	protected void doContextSensitiveLoop (Context context, ContextSensitiveConsumer<IComponent> function) {
		int posy[]={renderer.getTop()};
		doContextlessLoop(component->{
			Context subContext=getSubContext(context,posy[0]);
			function.accept(context,component);
			posy[0]+=subContext.getSize().height;
		});
	}
	
	/**
	 * Create sub-context for child component.
	 * @param context the current context
	 * @param posy the vertical position of the child component
	 * @return the context for the child component
	 */
	protected Context getSubContext (Context context, int posy) {
		return new Context(context,context.getSize().width-renderer.getLeft()-renderer.getRight(),new Point(renderer.getTop(),posy),context.hasFocus(),true);
	}
}
