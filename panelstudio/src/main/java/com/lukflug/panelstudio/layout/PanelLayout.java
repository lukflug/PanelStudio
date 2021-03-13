package com.lukflug.panelstudio.layout;

import java.awt.Point;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntFunction;
import java.util.function.IntPredicate;
import java.util.function.Supplier;

import com.lukflug.panelstudio.base.Animation;
import com.lukflug.panelstudio.base.Context;
import com.lukflug.panelstudio.base.IBoolean;
import com.lukflug.panelstudio.base.IInterface;
import com.lukflug.panelstudio.base.IToggleable;
import com.lukflug.panelstudio.base.SimpleToggleable;
import com.lukflug.panelstudio.component.ComponentProxy;
import com.lukflug.panelstudio.component.DraggableComponent;
import com.lukflug.panelstudio.component.FixedComponent;
import com.lukflug.panelstudio.component.FocusableComponent;
import com.lukflug.panelstudio.component.IComponent;
import com.lukflug.panelstudio.container.VerticalContainer;
import com.lukflug.panelstudio.setting.IBooleanSetting;
import com.lukflug.panelstudio.setting.IClient;
import com.lukflug.panelstudio.setting.IColorSetting;
import com.lukflug.panelstudio.setting.IEnumSetting;
import com.lukflug.panelstudio.setting.IKeybindSetting;
import com.lukflug.panelstudio.setting.INumberSetting;
import com.lukflug.panelstudio.setting.ISetting;
import com.lukflug.panelstudio.setting.Labeled;
import com.lukflug.panelstudio.theme.ITheme;
import com.lukflug.panelstudio.widget.Button;
import com.lukflug.panelstudio.widget.ClosableComponent;
import com.lukflug.panelstudio.widget.ColorComponent;
import com.lukflug.panelstudio.widget.CycleButton;
import com.lukflug.panelstudio.widget.KeybindComponent;
import com.lukflug.panelstudio.widget.NumberSlider;
import com.lukflug.panelstudio.widget.ToggleButton;

public class PanelLayout implements ILayout {
	protected final int width;
	protected final Point start;
	protected final int skipX,skipY;
	protected final Supplier<Animation> animation;
	protected final IntPredicate deleteKey;
	protected final IntFunction<ChildMode> layoutType;
	protected final IPopupPositioner popupPos;
	
	public PanelLayout (int width, Point start, int skipX, int skipY, Supplier<Animation> animation, IntPredicate deleteKey, IntFunction<ChildMode> layoutType, IPopupPositioner popupPos) {
		this.width=width;
		this.start=start;
		this.skipX=skipX;
		this.skipY=skipY;
		this.animation=animation;
		this.deleteKey=deleteKey;
		this.layoutType=layoutType;
		this.popupPos=popupPos;
	}
	
	@Override
	public void populateGUI(IComponentAdder gui, IClient client, ITheme theme) {
		Point pos=start;
		AtomicInteger skipY=new AtomicInteger(this.skipY);
		client.getCategories().forEach(category->{
			Button categoryTitle=new Button(category,theme.getButtonRenderer(Void.class,0,true));
			VerticalContainer categoryContent=new VerticalContainer(category,theme.getContainerRenderer(0));
			gui.addComponent(categoryTitle,categoryContent,theme,0,new Point(pos),width,animation);
			pos.translate(skipX,skipY.get());
			skipY.set(-skipY.get());
			category.getModules().forEach(module->{
				ChildMode mode=layoutType.apply(0);
				FocusableComponent moduleTitle;
				if (module.isEnabled()==null) moduleTitle=new Button(module,theme.getButtonRenderer(Void.class,1,mode==ChildMode.DOWN));
				else moduleTitle=new ToggleButton(module,module.isEnabled(),theme.getButtonRenderer(IBoolean.class,1,mode==ChildMode.DOWN));
				VerticalContainer moduleContainer=new VerticalContainer(module,theme.getContainerRenderer(1));
				if (module.isEnabled()==null) addContainer(moduleTitle,moduleContainer,()->null,Void.class,categoryContent,gui,theme,1);
				else addContainer(moduleTitle,moduleContainer,()->module.isEnabled(),IBoolean.class,categoryContent,gui,theme,1);
				module.getSettings().forEach(setting->addSettingsComponent(setting,moduleContainer,gui,theme,2));
			});
		});
	}
	
	protected <T> void addContainer (IComponent title, VerticalContainer container, Supplier<T> state, Class<T> stateClass, VerticalContainer parent, IComponentAdder gui, ITheme theme, int level) {
		DraggableComponent<FixedComponent<ClosableComponent<IComponent,VerticalContainer>>> popup;
		IToggleable toggle;
		boolean drawTitle=layoutType.apply(level-1)==ChildMode.DRAG_POPUP;
		switch (layoutType.apply(level-1)) {
		case DOWN:
			parent.addComponent(new ClosableComponent<IComponent,VerticalContainer>(title,container,state,new SimpleToggleable(false),animation.get(),theme.getPanelRenderer(stateClass,1)));
			break;
		case POPUP:
		case DRAG_POPUP:
			toggle=new SimpleToggleable(false);
			popup=ClosableComponent.createPopup(new Button(new Labeled(container.getTitle(),null,()->drawTitle),theme.getButtonRenderer(Void.class,level,true)),container,animation.get(),theme.getPanelRenderer(Void.class,level),toggle,level,false);
			parent.addComponent(new ComponentProxy<IComponent>(title) {
				@Override
				public void handleButton (Context context, int button) {
					super.handleButton(context,button);
					if (button==IInterface.RBUTTON && context.isHovered() && context.getInterface().getButton(IInterface.RBUTTON)) {
						System.out.println("Blah!");
						popup.setPosition(context.getInterface(),popupPos.getPosition(context.getInterface(),context.getRect(),null));
						if (!toggle.isOn()) toggle.toggle();
					}
				}
			});
			gui.addPopup(popup);
			break;
		}
	}
	
	protected <T> void addSettingsComponent (ISetting<T> setting, VerticalContainer container, IComponentAdder gui, ITheme theme, int level) {
		IComponent component;
		boolean isContainer=setting.getSubSettings()!=null;
		if (setting instanceof IBooleanSetting) {
			component=new ToggleButton((IBooleanSetting)setting,theme.getButtonRenderer(IBoolean.class,level,isContainer));
		} else if (setting instanceof INumberSetting) {
			component=new NumberSlider((INumberSetting)setting,theme.getSliderRenderer(level,isContainer));
		} else if (setting instanceof IEnumSetting) {
			component=new CycleButton((IEnumSetting)setting,theme.getButtonRenderer(String.class,level,isContainer));
		} else if (setting instanceof IColorSetting) {
			component=new ColorComponent((IColorSetting)setting,animation.get(),theme,level);
		} else if (setting instanceof IKeybindSetting) {
			component=new KeybindComponent((IKeybindSetting)setting,theme.getKeybindRenderer(level,isContainer)) {
				@Override
				public int transformKey (int scancode) {
					return deleteKey.test(scancode)?0:scancode;
				}
			};
		} else {
			component=new Button(setting,theme.getButtonRenderer(Void.class,level,isContainer));
		}
		if (isContainer) {
			VerticalContainer settingContainer=new VerticalContainer(setting,theme.getContainerRenderer(level));
			addContainer(component,settingContainer,()->setting.getSettingState(),setting.getSettingClass(),container,gui,theme,level);
			setting.getSubSettings().forEach(subSetting->addSettingsComponent(subSetting,settingContainer,gui,theme,level+1));
		} else {
			container.addComponent(component);
		}
	}
	
	public enum ChildMode {
		DOWN,POPUP,DRAG_POPUP;
	}
}
