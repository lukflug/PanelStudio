package com.lukflug.panelstudio.layout;

import java.util.function.Supplier;

import com.lukflug.panelstudio.base.AnimatedToggleable;
import com.lukflug.panelstudio.base.Animation;
import com.lukflug.panelstudio.base.Context;
import com.lukflug.panelstudio.base.IInterface;
import com.lukflug.panelstudio.base.IToggleable;
import com.lukflug.panelstudio.base.SimpleToggleable;
import com.lukflug.panelstudio.component.ComponentProxy;
import com.lukflug.panelstudio.component.IComponent;
import com.lukflug.panelstudio.component.IFixedComponent;
import com.lukflug.panelstudio.container.VerticalContainer;
import com.lukflug.panelstudio.popup.PopupTuple;
import com.lukflug.panelstudio.setting.ILabeled;
import com.lukflug.panelstudio.setting.Labeled;
import com.lukflug.panelstudio.theme.RendererTuple;
import com.lukflug.panelstudio.theme.ThemeTuple;
import com.lukflug.panelstudio.widget.Button;
import com.lukflug.panelstudio.widget.ClosableComponent;

/**
 * Utility to add child components.
 * @author lukflug
 */
public class ChildUtil {
	/**
	 * The pop-up width.
	 */
	protected int width;
	/**
	 * The animation supplier.
	 */
	protected Supplier<Animation> animation;
	/**
	 * The pop-up type.
	 */
	protected PopupTuple popupType;
	
	/**
	 * Construcotr.
	 * @param width pop-up width
	 * @param animation animation supplier
	 * @param popupType pop-up type
	 */
	public ChildUtil (int width, Supplier<Animation> animation, PopupTuple popupType) {
		this.width=width;
		this.animation=animation;
		this.popupType=popupType;
	}
	
	/**
	 * Add a child container.
	 * @param <T> the render state type
	 * @param label the container label
	 * @param title the title component
	 * @param container the container itself
	 * @param state the render state supplier
	 * @param stateClass the render state class
	 * @param parent the parent component
	 * @param gui the component adder for pop-ups
	 * @param theme the theme to be used
	 * @param mode the child mode to be used
	 */
	public <T> void addContainer (ILabeled label, IComponent title, IComponent container, Supplier<T> state, Class<T> stateClass, VerticalContainer parent, IComponentAdder gui, ThemeTuple theme, ChildMode mode) {
		IFixedComponent popup;
		IToggleable toggle;
		boolean drawTitle=mode==ChildMode.DRAG_POPUP;
		switch (mode) {
		case DOWN:
			parent.addComponent(new ClosableComponent<>(title,container,state,getAnimatedToggleable(animation.get()),theme.getPanelRenderer(stateClass),false));
			break;
		case POPUP:
		case DRAG_POPUP:
			toggle=new SimpleToggleable(false);
			Button<T> button=new Button<T>(new Labeled(label.getDisplayName(),label.getDescription(),()->drawTitle&&label.isVisible().isOn()),state,theme.getButtonRenderer(stateClass,true));
			if (popupType.dynamicPopup) popup=ClosableComponent.createDynamicPopup(button,container,state,animation.get(),new RendererTuple<T>(stateClass,theme),popupType.popupSize,toggle,width);
			else popup=ClosableComponent.createStaticPopup(button,container,state,animation.get(),new RendererTuple<T>(stateClass,theme),popupType.popupSize,toggle,()->width,false,"",false);
			parent.addComponent(new ComponentProxy<IComponent>(title) {
				@Override
				public void handleButton (Context context, int button) {
					super.handleButton(context,button);
					if (button==IInterface.RBUTTON && context.isClicked(button)) {
						context.getPopupDisplayer().displayPopup(popup,context.getRect(),toggle,popupType.popupPos);
						context.releaseFocus();
					}
				}
			});
			gui.addPopup(popup);
			break;
		}
	}
	
	/**
	 * Get animated toggleable.
	 * @param animation the animation to be used
	 * @return the animated toggleable to be used
	 */
	protected AnimatedToggleable getAnimatedToggleable (Animation animation) {
		return new AnimatedToggleable(new SimpleToggleable(false),animation);
	}
	
	
	/**
	 * Enum listing the ways a child component can be added.
	 * @author lukflug
	 */
	public static enum ChildMode {
		/**
		 * Component is added as a closable component with title bar.
		 */
		DOWN,
		/**
		 * Component is added as button that shows pop-up.
		 */
		POPUP,
		/**
		 * Component is added as button that shows draggable pop-up with title bar.
		 */
		DRAG_POPUP;
	}
}
