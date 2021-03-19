package com.lukflug.panelstudio.layout;

import java.util.function.BiFunction;
import java.util.function.Supplier;

import com.lukflug.panelstudio.base.Animation;
import com.lukflug.panelstudio.base.Context;
import com.lukflug.panelstudio.base.IInterface;
import com.lukflug.panelstudio.base.IToggleable;
import com.lukflug.panelstudio.base.SimpleToggleable;
import com.lukflug.panelstudio.component.ComponentProxy;
import com.lukflug.panelstudio.component.IComponent;
import com.lukflug.panelstudio.component.IFixedComponent;
import com.lukflug.panelstudio.container.VerticalContainer;
import com.lukflug.panelstudio.popup.IPopupPositioner;
import com.lukflug.panelstudio.setting.ILabeled;
import com.lukflug.panelstudio.setting.Labeled;
import com.lukflug.panelstudio.theme.ITheme;
import com.lukflug.panelstudio.widget.Button;
import com.lukflug.panelstudio.widget.ClosableComponent;

public class ChildUtil {
	protected int width;
	protected Supplier<Animation> animation;
	protected IPopupPositioner popupPos;
	protected BiFunction<Context,Integer,Integer> popupHeight;
	protected boolean dynamicPopup;
	
	public ChildUtil (int width, Supplier<Animation> animation, IPopupPositioner popupPos, boolean dynamicPopup, BiFunction<Context,Integer,Integer> popupHeight) {
		this.width=width;
		this.animation=animation;
		this.popupPos=popupPos;
		this.popupHeight=popupHeight;
		this.dynamicPopup=dynamicPopup;
	}
	
	protected <T> void addContainer (ILabeled label, IComponent title, IComponent container, Supplier<T> state, Class<T> stateClass, VerticalContainer parent, IComponentAdder gui, ITheme theme, int logicalLevel, int graphicalLevel, ChildMode mode) {
		IFixedComponent popup;
		IToggleable toggle;
		boolean drawTitle=mode==ChildMode.DRAG_POPUP;
		switch (mode) {
		case DOWN:
			parent.addComponent(new ClosableComponent<IComponent,IComponent>(title,container,state,new SimpleToggleable(false),animation.get(),theme.getPanelRenderer(stateClass,logicalLevel,graphicalLevel)));
			break;
		case POPUP:
		case DRAG_POPUP:
			toggle=new SimpleToggleable(false);
			if (dynamicPopup) popup=ClosableComponent.createDynamicPopup(new Button(new Labeled(label.getDisplayName(),label.getDescription(),()->drawTitle&&label.isVisible().isOn()),theme.getButtonRenderer(Void.class,logicalLevel,graphicalLevel,true)),container,state,animation.get(),theme.getPanelRenderer(stateClass,logicalLevel,graphicalLevel),theme.getScrollBarRenderer(stateClass,logicalLevel,graphicalLevel),theme.getEmptySpaceRenderer(stateClass,logicalLevel,graphicalLevel),popupHeight,toggle,width);
			else popup=ClosableComponent.createStaticPopup(new Button(new Labeled(label.getDisplayName(),label.getDescription(),()->drawTitle&&label.isVisible().isOn()),theme.getButtonRenderer(Void.class,logicalLevel,graphicalLevel,true)),container,state,animation.get(),theme.getPanelRenderer(stateClass,logicalLevel,graphicalLevel),theme.getScrollBarRenderer(stateClass,logicalLevel,graphicalLevel),theme.getEmptySpaceRenderer(stateClass,logicalLevel,graphicalLevel),popupHeight,toggle,width,false,"");
			parent.addComponent(new ComponentProxy<IComponent>(title) {
				@Override
				public void handleButton (Context context, int button) {
					super.handleButton(context,button);
					if (button==IInterface.RBUTTON && context.isHovered() && !context.getInterface().getButton(IInterface.RBUTTON)) {
						context.getPopupDisplayer().displayPopup(popup,context.getRect(),toggle,popupPos);
						context.releaseFocus();
					}
				}
			});
			gui.addPopup(popup);
			break;
		}
	}
	
	public enum ChildMode {
		DOWN,POPUP,DRAG_POPUP;
	}
}
