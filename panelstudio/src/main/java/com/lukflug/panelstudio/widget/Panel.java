package com.lukflug.panelstudio.widget;

import java.awt.Point;
import java.util.function.IntFunction;
import java.util.function.Supplier;

import com.lukflug.panelstudio.base.Animation;
import com.lukflug.panelstudio.base.Context;
import com.lukflug.panelstudio.base.IInterface;
import com.lukflug.panelstudio.base.IToggleable;
import com.lukflug.panelstudio.component.CollapsibleComponent;
import com.lukflug.panelstudio.component.ComponentProxy;
import com.lukflug.panelstudio.component.DraggableComponent;
import com.lukflug.panelstudio.component.FixedComponent;
import com.lukflug.panelstudio.component.FocusableComponentProxy;
import com.lukflug.panelstudio.component.IComponent;
import com.lukflug.panelstudio.container.VerticalContainer;
import com.lukflug.panelstudio.setting.ILabeled;
import com.lukflug.panelstudio.setting.Labeled;
import com.lukflug.panelstudio.theme.IContainerRenderer;
import com.lukflug.panelstudio.theme.IEmptySpaceRenderer;
import com.lukflug.panelstudio.theme.IPanelRenderer;
import com.lukflug.panelstudio.theme.IScrollBarRenderer;
import com.lukflug.panelstudio.theme.ITheme;

/**
 * A panel that can be opened and closed.
 * @author lukflug
 */
public class Panel<T extends IComponent> extends FocusableComponentProxy<VerticalContainer> {
	protected final IComponent title;
	/**
	 * The current collapsible component.
	 */
	protected final CollapsibleComponent<T> collapsible;
	protected final VerticalContainer container;
	
	/**
	 * Creates a generic panel.
	 * @param title the title component of the panel
	 * @param content the content of the panel
	 * @param state the state of this panel
	 * @param open the toggleable to use for opening and closing the panel 
	 * @param animation the animation to use for opening and closing the panel
	 * @param panelRenderer the render to use for the overlay of this panel
	 * @return a vertical container having the functionality of a panel
	 */
	public <S> Panel (IComponent title, T content, Supplier<S> state, IToggleable open, Animation animation, IPanelRenderer<S> panelRenderer) {
		this.title=title;
		container=new VerticalContainer(new Labeled(title.getTitle(),null,()->content.isVisible()),panelRenderer) {
			@Override
			public void render (Context context) {
				super.render(context);
				panelRenderer.renderPanelOverlay(context,hasFocus(context),state.get(),open.isOn());
			}
		};
		collapsible=new CollapsibleComponent<T>(open,animation) {
			@Override
			public T getComponent() {
				return content;
			}
		};
		container.addComponent(new ComponentProxy<IComponent>(title) {
			@Override
			public void render (Context context) {
				super.render(context);
				panelRenderer.renderTitleOverlay(context,hasFocus(context),state.get(),open.isOn());
			}
			
			@Override
			public void handleButton (Context context, int button) {
				super.handleButton(context,button);
				if (button==IInterface.RBUTTON && context.isHovered() && context.getInterface().getButton(IInterface.RBUTTON)) {
					collapsible.getToggle().toggle();
				}
			}
		});
		container.addComponent(collapsible);
	}

	@Override
	public final VerticalContainer getComponent() {
		return container;
	}
	
	public IComponent getTitleBar() {
		return title;
	}
	
	public CollapsibleComponent<T> getCollapsible() {
		return collapsible;
	}
	
	public static <S,T extends IComponent> DraggableComponent<FixedComponent<Panel<T>>> createDraggableComponent (Panel<T> panel, Point position, int width, boolean savesState) {
		return new DraggableComponent<FixedComponent<Panel<T>>>() {
			@Override
			public FixedComponent<Panel<T>> getComponent() {
				return new FixedComponent<Panel<T>>(panel,position,width,panel.getCollapsible().getToggle(),savesState);
			}
		};
	}
	
	public static <S> Panel<ScrollableComponent<S,VerticalContainer>> createContainer (ILabeled label, IComponent title, Class<S> type, Supplier<S> state, IToggleable open, Animation animation, ITheme theme, int level) {
		return createContainer(label,title,state,open,animation,theme.getPanelRenderer(type,level),theme.getContainerRenderer(level),theme.getScrollBarRenderer(type,level),theme.getEmptySpaceRenderer(type,level));
	}
	
	public static <S> Panel<ScrollableComponent<S,VerticalContainer>> createContainer (ILabeled label, IComponent title, Supplier<S> state, IToggleable open, Animation animation, IPanelRenderer<S> panelRenderer, IContainerRenderer containerRenderer, IScrollBarRenderer<S> scrollRenderer, IEmptySpaceRenderer<S> emptyRenderer) {
		return createScrollablePanel(title,new VerticalContainer(label,containerRenderer),state,open,animation,panelRenderer,scrollRenderer,emptyRenderer,height->height,width->width);
	}
	
	public static <S,T extends IComponent> Panel<ScrollableComponent<S,T>> createScrollablePanel (IComponent title, T content, Supplier<S> state, IToggleable open, Animation animation, IPanelRenderer<S> panelRenderer, IScrollBarRenderer<S> scrollRenderer, IEmptySpaceRenderer<S> emptyRenderer, IntFunction<Integer> scrollHeight, IntFunction<Integer> componentWidth) {
		return new Panel<ScrollableComponent<S,T>>(title,new ScrollableComponent<S,T>(content,scrollRenderer,emptyRenderer) {
			@Override
			protected int getScrollHeight (int componentHeight) {
				return scrollHeight.apply(componentHeight); 
			}

			@Override
			protected int getComponentWidth (int scrollWidth) {
				return componentWidth.apply(scrollWidth);
			}

			@Override
			protected S getState() {
				return state.get();
			}
		},state,open,animation,panelRenderer);
	}
}
