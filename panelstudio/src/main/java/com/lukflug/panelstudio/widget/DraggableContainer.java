package com.lukflug.panelstudio.widget;

import java.awt.Point;
import java.util.function.IntFunction;
import java.util.function.Supplier;

import com.lukflug.panelstudio.base.Animation;
import com.lukflug.panelstudio.base.IBoolean;
import com.lukflug.panelstudio.base.IToggleable;
import com.lukflug.panelstudio.component.DraggableComponent;
import com.lukflug.panelstudio.component.FixedComponent;
import com.lukflug.panelstudio.component.IComponent;
import com.lukflug.panelstudio.component.IFixedComponent;
import com.lukflug.panelstudio.container.IContainer;
import com.lukflug.panelstudio.container.VerticalContainer;
import com.lukflug.panelstudio.setting.ILabeled;
import com.lukflug.panelstudio.theme.IContainerRenderer;
import com.lukflug.panelstudio.theme.IEmptySpaceRenderer;
import com.lukflug.panelstudio.theme.IPanelRenderer;
import com.lukflug.panelstudio.theme.IScrollBarRenderer;
import com.lukflug.panelstudio.theme.ITheme;

/**
 * Class for a {@link IFixedComponent} that is also a {@link CollapsibleContainer} (i.e. a Panel), that can be dragged by the user using the mouse.
 * @author lukflug
 */
public class DraggableContainer extends DraggableComponent implements IContainer<IComponent> {
	protected final CollapsibleContainer panel;
	
	/**
	 * Constructor using theme.
	 * @param label the label for the component
	 * @param active whether the panel is active
	 * @param open the toggleable to be used to open and close the panel
	 * @param animation the animation for opening and closing the panel
	 * @param theme the theme to be used
	 * @param position the initial position
	 * @param width the width of the panel
	 * @param savesState whether the panel saves the state
	 */
	public <T> DraggableContainer (ILabeled label, IComponent title, Class<T> type, Supplier<T> state, IToggleable open, Animation animation, ITheme theme, int level, Point position, int width, boolean savesState) {
		this(label,title,state,open,animation,theme.getPanelRenderer(type,level),theme.getContainerRenderer(level),theme.getScrollBarRenderer(type,level),theme.getEmptySpaceRenderer(type,level),position,width,savesState);
	}
	
	/**
	 * Constructor.
	 * @param label the label for the component
	 * @param active whether the panel is active
	 * @param open the toggleable to be used to open and close the panel
	 * @param animation the animation for opening and closing the panel
	 * @param panelRenderer the renderer for the panel overlay
	 * @param titleRenderer the renderer for the panel title
	 * @param containerRenderer the renderer for the panel content container
	 * @param scrollRenderer the renderer for the scroll bars
	 * @param emptyRenderer the renderer for the scroll corner
	 * @param position the initial position
	 * @param width the width of the panel
	 * @param savesState whether the panel saves the state
	 */
	public <T> DraggableContainer (ILabeled label, IComponent title, Supplier<T> state, IToggleable open, Animation animation, IPanelRenderer<T> panelRenderer, IContainerRenderer containerRenderer, IScrollBarRenderer<T> scrollRenderer, IEmptySpaceRenderer<T> emptyRenderer, Point position, int width, boolean savesState) {
		super(null);
		panel=new CollapsibleContainer(getWrappedDragComponent(title),new VerticalContainer(label,containerRenderer),state,open,animation,panelRenderer,scrollRenderer,emptyRenderer,height->getScrollHeight(height),cwidth->getComponentWidth(cwidth));
		setComponent(new FixedComponent(panel,position,width,panel.getToggle(),savesState));
	}
	
	public <T> DraggableContainer (IComponent title, VerticalContainer content, Supplier<T> state, IToggleable open, Animation animation, IPanelRenderer<T> panelRenderer, IScrollBarRenderer<T> scrollRenderer, IEmptySpaceRenderer<T> emptyRenderer, IntFunction<Integer> scrollHeight, IntFunction<Integer> componentWidth, Point position, int width, boolean savesState) {
		super(null);
		panel=new CollapsibleContainer(getWrappedDragComponent(title),content,state,open,animation,panelRenderer,scrollRenderer,emptyRenderer,height->getScrollHeight(height),cwidth->getComponentWidth(cwidth));
		setComponent(new FixedComponent(panel,position,width,panel.getToggle(),savesState));
	}

	@Override
	public boolean addComponent (IComponent component) {
		return panel.addComponent(component);
	}
	
	@Override
	public boolean addComponent (IComponent component, IBoolean visible) {
		return panel.addComponent(component,visible);
	}
	
	@Override
	public boolean removeComponent (IComponent component) {
		return panel.removeComponent(component);
	}
	
	/**
	 * Get visible scroll height based on content container height.
	 * @param componentHeight the container height
	 * @return the scroll height
	 */
	protected int getScrollHeight (int componentHeight) {
		return componentHeight;
	}

	/**
	 * Get content container width based on scroll width.
	 * @param scrollWidth the scroll width
	 * @return the container width
	 */
	protected int getComponentWidth (int scrollWidth) {
		return scrollWidth;
	}
}
