package com.lukflug.panelstudio.layout;

import java.awt.Point;
import java.util.function.IntPredicate;
import java.util.function.Supplier;

import com.lukflug.panelstudio.base.AnimatedToggleable;
import com.lukflug.panelstudio.base.Animation;
import com.lukflug.panelstudio.base.Context;
import com.lukflug.panelstudio.base.IInterface;
import com.lukflug.panelstudio.base.SimpleToggleable;
import com.lukflug.panelstudio.component.ComponentProxy;
import com.lukflug.panelstudio.component.IComponent;
import com.lukflug.panelstudio.container.HorizontalContainer;
import com.lukflug.panelstudio.container.VerticalContainer;
import com.lukflug.panelstudio.setting.IClient;
import com.lukflug.panelstudio.setting.ILabeled;
import com.lukflug.panelstudio.setting.Labeled;
import com.lukflug.panelstudio.theme.ITheme;
import com.lukflug.panelstudio.theme.RendererTuple;
import com.lukflug.panelstudio.theme.ThemeTuple;
import com.lukflug.panelstudio.widget.Button;
import com.lukflug.panelstudio.widget.ClosableComponent;

public class CSGOLayout implements ILayout {
	protected ILabeled label;
	protected Point position;
	protected int width;
	protected Supplier<Animation> animation;
	protected IntPredicate deleteKey;
	protected IntPredicate layoutType;
	
	public CSGOLayout (ILabeled label, Point position, int width, Supplier<Animation> animation, IntPredicate deleteKey, IntPredicate layoutType) {
		this.label=label;
		this.position=position;
		this.width=width;
		this.animation=animation;
		this.deleteKey=deleteKey;
		this.layoutType=layoutType;
	}
	
	@Override
	public void populateGUI(IComponentAdder gui, IClient client, ITheme theme) {
		Button title=new Button(label,theme.getButtonRenderer(Void.class,0,0,true));
		HorizontalContainer container=new HorizontalContainer(label,theme.getContainerRenderer(0,0,true));
		gui.addComponent(title,container,new ThemeTuple(theme,0,0),position,width,animation);
		client.getCategories().forEach(category->{
		});
	}
	
	protected <T> VerticalContainer addContainer (ILabeled label, Supplier<T> state, Class<T> stateClass, IComponent title, VerticalContainer parent, HorizontalContainer window, ThemeTuple theme, boolean mode) {
		VerticalContainer container=new VerticalContainer(label,theme.getContainerRenderer(false));
		if (mode) {
			parent.addComponent(new ClosableComponent<>(title,container,state,new AnimatedToggleable(new SimpleToggleable(false),animation.get()),theme.getPanelRenderer(stateClass)));
		} else {
			toggle=new SimpleToggleable(false);
			Button button=new Button(new Labeled(label.getDisplayName(),label.getDescription(),()->drawTitle&&label.isVisible().isOn()),theme.getButtonRenderer(Void.class,true));
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
		}
		return container;
	}
}
