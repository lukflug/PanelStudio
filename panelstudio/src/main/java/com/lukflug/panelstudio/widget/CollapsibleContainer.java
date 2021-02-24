package com.lukflug.panelstudio.widget;

import java.util.function.IntFunction;
import java.util.function.Supplier;

import com.lukflug.panelstudio.base.Animation;
import com.lukflug.panelstudio.base.Context;
import com.lukflug.panelstudio.base.IBoolean;
import com.lukflug.panelstudio.base.IToggleable;
import com.lukflug.panelstudio.component.IComponent;
import com.lukflug.panelstudio.container.IContainer;
import com.lukflug.panelstudio.container.VerticalContainer;
import com.lukflug.panelstudio.setting.ILabeled;
import com.lukflug.panelstudio.theme.IContainerRenderer;
import com.lukflug.panelstudio.theme.IEmptySpaceRenderer;
import com.lukflug.panelstudio.theme.IPanelRenderer;
import com.lukflug.panelstudio.theme.IScrollBarRenderer;
import com.lukflug.panelstudio.theme.ITheme;

/**
 * Container that can be closed and scrolled, so that its children can be hidden.
 * @author lukflug
 * @param <T> the type representing the state
 */
public class CollapsibleContainer extends Panel implements IContainer<IComponent> {
	/**
	 * The container that is wrapped.
	 */
	protected final VerticalContainer contentContainer;
	
	/**
	 * Constructor using theme.
	 * @param label the label for the component
	 * @param title the title for the component
	 * @param state the state of this panel
	 * @param open the toggleable to be used to open and close the panel
	 * @param animation the animation for opening and closing the panel
	 * @param theme the theme to be used
	 * @param level the nesting level to use
	 */
	public <T> CollapsibleContainer (ILabeled label, IComponent title, Class<T> type, Supplier<T> state, IToggleable open, Animation animation, ITheme theme, int level) {
		this(label,title,state,open,animation,theme.getPanelRenderer(type,level),theme.getContainerRenderer(level),theme.getScrollBarRenderer(type,level),theme.getEmptySpaceRenderer(type,level));
	}
	
	/**
	 * Constructor.
	 * @param label the label for the component
	 * @param title the title for the component
	 * @param state the state of this panel
	 * @param open the toggleable to be used to open and close the panel
	 * @param animation the animation for opening and closing the panel
	 * @param panelRenderer the renderer for the panel overlay
	 * @param containerRenderer the renderer for the panel content container
	 * @param scrollRenderer the renderer for the scroll bars
	 * @param emptyRenderer the renderer for the scroll corner
	 */
	public <T> CollapsibleContainer (ILabeled label, IComponent title, Supplier<T> state, IToggleable open, Animation animation, IPanelRenderer<T> panelRenderer, IContainerRenderer containerRenderer, IScrollBarRenderer<T> scrollRenderer, IEmptySpaceRenderer<T> emptyRenderer) {
		this(title,new VerticalContainer(label,containerRenderer),state,open,animation,panelRenderer,scrollRenderer,emptyRenderer,height->height,width->width);
	}
	
	/**
	 * Constructor.
	 * @param title the title of the panel
	 * @param content the content container of the panel
	 * @param state the state of this panel
	 * @param open the toggleable to be used to open and close the panel
	 * @param animation the animation for opening and closing the panel
	 * @param panelRenderer the renderer for the panel overlay
	 * @param scrollRenderer the renderer for the scroll bars
	 * @param emptyRenderer the renderer for the scroll corner
	 * @param scrollHeight function for the scroll height
	 * @param componentWidth function for the component width
	 */
	public <T> CollapsibleContainer (IComponent title, VerticalContainer content, Supplier<T> state, IToggleable open, Animation animation, IPanelRenderer<T> panelRenderer, IScrollBarRenderer<T> scrollRenderer, IEmptySpaceRenderer<T> emptyRenderer, IntFunction<Integer> scrollHeight, IntFunction<Integer> componentWidth) {
		super(title,new ScrollableComponent<T>(content,scrollRenderer,emptyRenderer) {
			@Override
			protected int getScrollHeight (int componentHeight) {
				return scrollHeight.apply(componentHeight); 
			}

			@Override
			protected int getComponentWidth (int scrollWidth) {
				return componentWidth.apply(scrollWidth);
			}

			@Override
			protected T getState() {
				return state.get();
			}
		},state,open,animation,panelRenderer);
		contentContainer=content;
	}
	
	@Override
	public void render (Context context) {
		super.render(context);
	}

	@Override
	public boolean addComponent (IComponent component) {
		return contentContainer.addComponent(component);
	}
	
	@Override
	public boolean addComponent (IComponent component, IBoolean visible) {
		return contentContainer.addComponent(component,visible);
	}
	
	@Override
	public boolean removeComponent (IComponent component) {
		return contentContainer.removeComponent(component);
	}
}
