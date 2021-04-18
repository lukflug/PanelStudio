package com.lukflug.panelstudio.widget;

import java.awt.Point;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

import com.lukflug.panelstudio.base.AnimatedToggleable;
import com.lukflug.panelstudio.base.Animation;
import com.lukflug.panelstudio.base.ConstantToggleable;
import com.lukflug.panelstudio.base.Context;
import com.lukflug.panelstudio.base.IInterface;
import com.lukflug.panelstudio.base.IToggleable;
import com.lukflug.panelstudio.component.CollapsibleComponent;
import com.lukflug.panelstudio.component.ComponentProxy;
import com.lukflug.panelstudio.component.DraggableComponent;
import com.lukflug.panelstudio.component.FixedComponent;
import com.lukflug.panelstudio.component.FocusableComponentProxy;
import com.lukflug.panelstudio.component.IComponent;
import com.lukflug.panelstudio.component.IScrollSize;
import com.lukflug.panelstudio.component.PopupComponent;
import com.lukflug.panelstudio.container.VerticalContainer;
import com.lukflug.panelstudio.setting.Labeled;
import com.lukflug.panelstudio.theme.IPanelRenderer;
import com.lukflug.panelstudio.theme.RendererTuple;

/**
 * A panel that can be opened and closed.
 * @author lukflug
 */
public class ClosableComponent<S extends IComponent,T extends IComponent> extends FocusableComponentProxy<VerticalContainer> {
	protected final S title;
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
	public <U> ClosableComponent (S title, T content, Supplier<U> state, AnimatedToggleable open, IPanelRenderer<U> panelRenderer) {
		this.title=title;
		container=new VerticalContainer(new Labeled(content.getTitle(),null,()->content.isVisible()),panelRenderer) {
			@Override
			public void render (Context context) {
				super.render(context);
				panelRenderer.renderPanelOverlay(context,hasFocus(context),state.get(),open.isOn());
			}
		};
		collapsible=new CollapsibleComponent<T>(open) {
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
	
	
	public static <S extends IComponent,T extends IComponent,U> DraggableComponent<FixedComponent<ClosableComponent<ComponentProxy<S>,ScrollBarComponent<U,T>>>> createStaticPopup (S title, T content, Supplier<U> state, Animation animation, RendererTuple<U> renderer, IScrollSize popupSize, IToggleable shown, int width, boolean savesState, String configName) {
		AtomicReference<ClosableComponent<ComponentProxy<S>,ScrollBarComponent<U,T>>> panel=new AtomicReference<>(null);
		DraggableComponent<FixedComponent<ClosableComponent<ComponentProxy<S>,ScrollBarComponent<U,T>>>> draggable=new DraggableComponent<FixedComponent<ClosableComponent<ComponentProxy<S>,ScrollBarComponent<U,T>>>>() {
			FixedComponent<ClosableComponent<ComponentProxy<S>,ScrollBarComponent<U,T>>> fixedComponent=null;
			
			@Override
			public void handleButton (Context context, int button) {
				super.handleButton(context,button);
				if (context.getInterface().getButton(button) && !context.isHovered() && shown.isOn()) shown.toggle();
			}
			
			@Override
			public boolean isVisible() {
				return super.isVisible()&&shown.isOn();
			}
			
			@Override
			public FixedComponent<ClosableComponent<ComponentProxy<S>,ScrollBarComponent<U,T>>> getComponent() {
				if (fixedComponent==null) fixedComponent=new FixedComponent<ClosableComponent<ComponentProxy<S>,ScrollBarComponent<U,T>>>(panel.get(),new Point(0,0),width,panel.get().getCollapsible().getToggle(),savesState,configName);
				return fixedComponent;
			}
		};
		panel.set(createScrollableComponent(draggable.getWrappedDragComponent(title),content,state,new AnimatedToggleable(new ConstantToggleable(true),animation),renderer,popupSize));
		return draggable;
	}
	
	public static <S extends IComponent,T extends IComponent,U> PopupComponent<ClosableComponent<S,ScrollBarComponent<U,T>>> createDynamicPopup (S title, T content, Supplier<U> state, Animation animation, RendererTuple<U> renderer, IScrollSize popupSize, IToggleable shown, int width) {
		ClosableComponent<S,ScrollBarComponent<U,T>> panel=createScrollableComponent(title,content,state,new AnimatedToggleable(new ConstantToggleable(true),animation),renderer,popupSize);
		return new PopupComponent<ClosableComponent<S,ScrollBarComponent<U,T>>>(panel,width) {
			@Override
			public void handleButton (Context context, int button) {
				doOperation(context,subContext->getComponent().handleButton(subContext,button));
				if (context.getInterface().getButton(button) && !context.isHovered() && shown.isOn()) shown.toggle();
			}
			
			@Override
			public boolean isVisible() {
				return getComponent().isVisible()&&shown.isOn();
			}
		};
	}
	
	public static <S extends IComponent,T extends IComponent,U> DraggableComponent<FixedComponent<ClosableComponent<ComponentProxy<S>,ScrollBarComponent<U,T>>>> createDraggableComponent (S title, T content, Supplier<U> state, AnimatedToggleable open, RendererTuple<U> renderer, IScrollSize scrollSize, Point position, int width, boolean savesState, String configName) {
		AtomicReference<ClosableComponent<ComponentProxy<S>,ScrollBarComponent<U,T>>> panel=new AtomicReference<>(null);
		DraggableComponent<FixedComponent<ClosableComponent<ComponentProxy<S>,ScrollBarComponent<U,T>>>> draggable=createDraggableComponent(()->panel.get(),position,width,savesState,configName);
		panel.set(createScrollableComponent(draggable.getWrappedDragComponent(title),content,state,open,renderer,scrollSize));
		return draggable;
	}
	
	public static <S extends IComponent,T extends IComponent,U> DraggableComponent<FixedComponent<ClosableComponent<S,T>>> createDraggableComponent (Supplier<ClosableComponent<S,T>> panel, Point position, int width, boolean savesState, String configName) {
		return new DraggableComponent<FixedComponent<ClosableComponent<S,T>>>() {
			FixedComponent<ClosableComponent<S,T>> fixedComponent=null;
			
			@Override
			public FixedComponent<ClosableComponent<S,T>> getComponent() {
				if (fixedComponent==null) fixedComponent=new FixedComponent<ClosableComponent<S,T>>(panel.get(),position,width,panel.get().getCollapsible().getToggle(),savesState,configName);
				return fixedComponent;
			}
		};
	}
	
	public static <S extends IComponent,T extends IComponent,U> ClosableComponent<S,ScrollBarComponent<U,T>> createScrollableComponent (S title, T content, Supplier<U> state, AnimatedToggleable open, RendererTuple<U> renderer, IScrollSize scrollSize) {
		return new ClosableComponent<S,ScrollBarComponent<U,T>>(title,new ScrollBarComponent<U,T>(content,renderer.scrollRenderer,renderer.cornerRenderer,renderer.emptyRenderer) {
			@Override
			public int getScrollHeight (Context context, int componentHeight) {
				return scrollSize.getScrollHeight(context,componentHeight); 
			}

			@Override
			public int getComponentWidth (Context context) {
				return scrollSize.getComponentWidth(context);
			}

			@Override
			protected U getState() {
				return state.get();
			}
		},state,open,renderer.panelRenderer);
	}
}
