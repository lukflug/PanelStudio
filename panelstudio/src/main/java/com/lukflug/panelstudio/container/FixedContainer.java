package com.lukflug.panelstudio.container;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import com.lukflug.panelstudio.base.Context;
import com.lukflug.panelstudio.base.IBoolean;
import com.lukflug.panelstudio.base.IInterface;
import com.lukflug.panelstudio.component.IFixedComponent;
import com.lukflug.panelstudio.config.IConfigList;
import com.lukflug.panelstudio.config.IPanelConfig;
import com.lukflug.panelstudio.theme.ContainerRenderer;

/**
 * Container with contents arranged at will.
 * @author lukflug
 */
public class FixedContainer extends Container<IFixedComponent> {
	/**
	 * The height of the container.
	 */
	protected int height;
	
	/**
	 * Constructor.
	 * @param title the caption for this component
	 * @param description the description for this component
	 * @param visible whether this component is visible
	 * @param renderer the renderer for this container
	 * @param height the height of the container
	*/
	public FixedContainer(String title, String description, IBoolean visible, ContainerRenderer renderer, int height) {
		super(title, description, visible, renderer);
		this.height=height;
	}
	
	@Override
	public void render (Context context) {
		// Clip rectangle
		context.getInterface().window(context.getRect());
		// Set context height
		context.setHeight(height);
		// Find highest component
		IFixedComponent highest[]={null};
		doContextlessLoop(component->{
			Context subContext=getSubContext(context,component,true);
			component.getHeight(subContext);
			if (subContext.isHovered() && highest[0]==null) highest[0]=component;
		});
		// Render loop in right order (lowest panel first)
		String tempDescription[]={null};
		boolean highestReached[]={false};
		IFixedComponent focusComponent[]={null};
		super.doContextlessLoop(component->{
			// Render component
			Context subContext=getSubContext(context,component,!highestReached[0]);
			component.render(subContext);
			// Check focus state
			if (subContext.focusReleased()) context.releaseFocus();
			else if (subContext.foucsRequested()) {
				focusComponent[0]=component;
				context.requestFocus();
			}
			// Check description state
			if (subContext.isHovered() && subContext.getDescription()!=null) tempDescription[0]=subContext.getDescription();
			// Check onTop state
			if (component==highest[0]) highestReached[0]=true;
		});
		// Update focus state
		if (focusComponent[0]!=null) {
			if (removeComponent(focusComponent[0])) addComponent(focusComponent[0]);
		}
		// Pass description
		if (tempDescription[0]==null) tempDescription[0]=description;
		context.setDescription(tempDescription[0]);
		// Restore clipping
		context.getInterface().restore();
	}
	
	@Override
	protected void doContextlessLoop (Consumer<IFixedComponent> function) {
		List<ComponentState> components=new ArrayList<ComponentState>();
		for (ComponentState state: this.components) {
			components.add(state);
		}
		for (int i=components.size()-1;i>=0;i--) {
			ComponentState state=components.get(i);
			state.update();
			if (state.component.lastVisible()) function.accept(state.component);
		}
	}

	@Override
	protected void doContextSensitiveLoop(Context context, ContextSensitiveConsumer<IFixedComponent> function) {
		// Set context height
		context.setHeight(height);
		// Do loop in inverse order
		boolean highest[]= {true};
		IFixedComponent focusComponent[]={null};
		doContextlessLoop(component->{
			// Do payload operation
			Context subContext=getSubContext(context,component,highest[0]);
			function.accept(subContext,component);
			// Check focus state
			if (subContext.focusReleased()) context.releaseFocus();
			else if (subContext.foucsRequested()) {
				focusComponent[0]=component;
				context.requestFocus();
			}
			// Check onTop state
			if (subContext.isHovered()) highest[0]=false;
		});
		// Update focus state
		if (focusComponent[0]!=null) {
			if (removeComponent(focusComponent[0])) addComponent(focusComponent[0]);
		}
	}
	
	/**
	 * Create sub-context for child component.
	 * @param context the current context
	 * @param component the component
	 * @param highest whether this component is the highest
	 * @return the context for the child component
	 */
	protected Context getSubContext (Context context, IFixedComponent component, boolean highest) {
		return new Context(context,component.getWidth(context.getInterface()),component.getPosition(context.getInterface()),context.hasFocus(),highest);
	}
	
	/**
	 * Store the GUI state.
	 * @param inter the interface to be used
	 * @param config the configuration list to be used
	 */
	public void saveConfig (IInterface inter, IConfigList config) {
		config.begin(false);
		doContextlessLoop(component->{
			IPanelConfig cf=config.addPanel(component.getTitle());
			if (cf!=null && component.savesState()) component.saveConfig(inter,cf);
		});
		config.end(false);
	}
	
	/**
	 * Load the GUI state.
	 * @param inter the interface to be used
	 * @param config the configuration list to be used
	 */
	public void loadConfig (IInterface inter, IConfigList config) {
		config.begin(true);
		doContextlessLoop(component->{
			IPanelConfig cf=config.getPanel(component.getTitle());
			if (cf!=null && component.savesState()) component.loadConfig(inter,cf);
		});
		config.end(true);
	}
}
