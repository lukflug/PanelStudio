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

public class ChildUtil {
	protected int width;
	protected Supplier<Animation> animation;
	protected PopupTuple popupType;
	
	public ChildUtil (int width, Supplier<Animation> animation, PopupTuple popupType) {
		this.width=width;
		this.animation=animation;
		this.popupType=popupType;
	}
	
	protected <T> void addContainer (ILabeled label, IComponent title, IComponent container, Supplier<T> state, Class<T> stateClass, VerticalContainer parent, IComponentAdder gui, ThemeTuple theme, ChildMode mode) {
		IFixedComponent popup;
		IToggleable toggle;
		boolean drawTitle=mode==ChildMode.DRAG_POPUP;
		switch (mode) {
		case DOWN:
			parent.addComponent(new ClosableComponent<>(title,container,state,new AnimatedToggleable(new SimpleToggleable(false),animation.get()),theme.getPanelRenderer(stateClass)));
			break;
		case POPUP:
		case DRAG_POPUP:
			toggle=new SimpleToggleable(false);
			Button<T> button=new Button<T>(new Labeled(label.getDisplayName(),label.getDescription(),()->drawTitle&&label.isVisible().isOn()),state,theme.getButtonRenderer(stateClass,true));
			if (popupType.dynamicPopup) popup=ClosableComponent.createDynamicPopup(button,container,state,animation.get(),new RendererTuple<T>(stateClass,theme),popupType.popupSize,toggle,width);
			else popup=ClosableComponent.createStaticPopup(button,container,state,animation.get(),new RendererTuple<T>(stateClass,theme),popupType.popupSize,toggle,width,false,"");
			parent.addComponent(new ComponentProxy<IComponent>(title) {
				@Override
				public void handleButton (Context context, int button) {
					super.handleButton(context,button);
					if (button==IInterface.RBUTTON && context.isHovered() && !context.getInterface().getButton(IInterface.RBUTTON)) {
						context.getPopupDisplayer().displayPopup(popup,context.getRect(),toggle,popupType.popupPos);
						context.releaseFocus();
					}
				}
			});
			gui.addPopup(popup);
			break;
		}
	}
	
	public static enum ChildMode {
		DOWN,POPUP,DRAG_POPUP;
	}
}
