package com.lukflug.panelstudio.container;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

import com.lukflug.panelstudio.base.Context;
import com.lukflug.panelstudio.base.Description;
import com.lukflug.panelstudio.base.IInterface;
import com.lukflug.panelstudio.base.IToggleable;
import com.lukflug.panelstudio.component.IFixedComponent;
import com.lukflug.panelstudio.config.IConfigList;
import com.lukflug.panelstudio.config.IPanelConfig;
import com.lukflug.panelstudio.popup.IPopup;
import com.lukflug.panelstudio.popup.IPopupDisplayer;
import com.lukflug.panelstudio.popup.IPopupPositioner;
import com.lukflug.panelstudio.setting.ILabeled;
import com.lukflug.panelstudio.theme.IContainerRenderer;

/**
 * Container with contents arranged at will.
 * @author lukflug
 */
public class FixedContainer extends Container<IFixedComponent> implements IPopupDisplayer {
	/**
	 * Whether to clip container.
	 */
	protected boolean clip;
	protected List<PopupPair> popups=new ArrayList<PopupPair>();
	
	/**
	 * Constructor.
	 * @param label the label for the component
	 * @param renderer the renderer for this container
	 * @param clip whether to clip container
	*/
	public FixedContainer(ILabeled label, IContainerRenderer renderer, boolean clip) {
		super(label, renderer);
		this.clip=clip;
	}

	@Override
	public void displayPopup(IPopup popup, Rectangle rect, IToggleable visible, IPopupPositioner positioner) {
		popups.add(new PopupPair(popup,rect,visible,positioner));
	}
	
	@Override
	public void render (Context context) {
		// Set context height
		context.setHeight(getHeight());
		// Clip rectangle
		if (clip) context.getInterface().window(context.getRect());
		if (renderer!=null) renderer.renderBackground(context,context.hasFocus());
		// Find highest component
		AtomicReference<IFixedComponent> highest=new AtomicReference<IFixedComponent>(null);
		doContextlessLoop(component->{
			Context subContext=getSubContext(context,component,true);
			component.getHeight(subContext);
			if (subContext.isHovered() && highest.get()==null) highest.set(component);
		});
		// Render loop in right order (lowest panel first)
		AtomicBoolean highestReached=new AtomicBoolean(false);
		if (highest.get()==null) highestReached.set(true);
		AtomicReference<IFixedComponent> focusComponent=new AtomicReference<IFixedComponent>(null);
		super.doContextlessLoop(component->{
			// Check onTop state
			if (component==highest.get()) highestReached.set(true);
			// Render component
			Context subContext=getSubContext(context,component,highestReached.get());
			component.render(subContext);
			// Check focus state
			if (subContext.focusReleased()) context.releaseFocus();
			else if (subContext.foucsRequested()) {
				focusComponent.set(component);
				context.requestFocus();
			}
			// Check description state
			if (subContext.isHovered() && subContext.getDescription()!=null) context.setDescription(new Description(subContext.getDescription(),subContext.getRect()));
			// Deal with popups
			for (PopupPair popup: popups) {
				popup.popup.setPosition(context.getInterface(),popup.rect,subContext.getRect(),popup.positioner);
				if (!popup.visible.isOn()) popup.visible.toggle();
				if (popup.popup instanceof IFixedComponent) focusComponent.set((IFixedComponent)popup.popup);
			}
			popups.clear();
		});
		// Update focus state
		if (focusComponent.get()!=null) {
			if (removeComponent(focusComponent.get())) addComponent(focusComponent.get());
		}
		// Use container description, if necessary
		if (context.getDescription()==null && description!=null) context.setDescription(new Description(context.getRect(),description));
		// Restore clipping
		if (clip) context.getInterface().restore();
	}
	
	@Override
	protected void doContextlessLoop (Consumer<IFixedComponent> function) {
		List<ComponentState> components=new ArrayList<ComponentState>();
		for (ComponentState state: this.components) components.add(state);
		for (ComponentState state: components) state.update();
		for (int i=components.size()-1;i>=0;i--) {
			ComponentState state=components.get(i);
			if (state.lastVisible()) function.accept(state.component);
		}
	}

	@Override
	protected void doContextSensitiveLoop(Context context, ContextSensitiveConsumer<IFixedComponent> function) {
		// Set context height
		context.setHeight(getHeight());
		// Do loop in inverse order
		AtomicBoolean highest=new AtomicBoolean(true);
		AtomicReference<IFixedComponent> focusComponent=new AtomicReference<IFixedComponent>(null);
		doContextlessLoop(component->{
			// Do payload operation
			Context subContext=getSubContext(context,component,highest.get());
			function.accept(subContext,component);
			// Check focus state
			if (subContext.focusReleased()) context.releaseFocus();
			else if (subContext.foucsRequested()) {
				focusComponent.set(component);
				context.requestFocus();
			}
			// Check onTop state
			if (subContext.isHovered()) highest.set(false);
			// Deal with popups
			for (PopupPair popup: popups) {
				popup.popup.setPosition(context.getInterface(),popup.rect,subContext.getRect(),popup.positioner);
				if (!popup.visible.isOn()) popup.visible.toggle();
				if (popup.popup instanceof IFixedComponent) focusComponent.set((IFixedComponent)popup.popup);
			}
			popups.clear();
		});
		// Update focus state
		if (focusComponent.get()!=null) {
			ComponentState focusState=components.stream().filter(state->state.component==focusComponent.get()).findFirst().orElse(null);
			if (focusState!=null) {
				components.remove(focusState);
				components.add(focusState);
			}
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
		Context subContext=new Context(context,component.getWidth(context.getInterface()),component.getPosition(context.getInterface()),context.hasFocus()&&highest,highest);
		subContext.setPopupDisplayer(this);
		return subContext;
	}
	
	/**
	 * Store the GUI state.
	 * @param inter the interface to be used
	 * @param config the configuration list to be used
	 */
	public void saveConfig (IInterface inter, IConfigList config) {
		config.begin(false);
		for (ComponentState state: components) {
			if (state.component.savesState()) {
				IPanelConfig cf=config.addPanel(state.component.getConfigName());
				if (cf!=null) state.component.saveConfig(inter,cf);
			}
		};
		config.end(false);
	}
	
	/**
	 * Load the GUI state.
	 * @param inter the interface to be used
	 * @param config the configuration list to be used
	 */
	public void loadConfig (IInterface inter, IConfigList config) {
		config.begin(true);
		for (ComponentState state: components) {
			if (state.component.savesState()) {
				IPanelConfig cf=config.getPanel(state.component.getConfigName());
				if (cf!=null) state.component.loadConfig(inter,cf);
			}
		};
		config.end(true);
	}
	
	
	protected final class PopupPair {
		public final IPopup popup;
		public final Rectangle rect;
		public final IToggleable visible;
		public final IPopupPositioner positioner;
		
		public PopupPair (IPopup popup, Rectangle rect, IToggleable visible, IPopupPositioner positioner) {
			this.popup=popup;
			this.rect=rect;
			this.visible=visible;
			this.positioner=positioner;
		}
	}
}
