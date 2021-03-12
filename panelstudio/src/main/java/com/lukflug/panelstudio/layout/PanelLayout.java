package com.lukflug.panelstudio.layout;

import java.awt.Point;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntFunction;
import java.util.function.IntPredicate;
import java.util.function.Supplier;

import com.lukflug.panelstudio.base.Animation;
import com.lukflug.panelstudio.base.IBoolean;
import com.lukflug.panelstudio.base.SimpleToggleable;
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
	
	public PanelLayout (int width, Point start, int skipX, int skipY, Supplier<Animation> animation, IntPredicate deleteKey, IntFunction<ChildMode> layoutType) {
		this.width=width;
		this.start=start;
		this.skipX=skipX;
		this.skipY=skipY;
		this.animation=animation;
		this.deleteKey=deleteKey;
		this.layoutType=layoutType;
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
				FocusableComponent moduleTitle;
				if (module.isEnabled()==null) moduleTitle=new Button(module,theme.getButtonRenderer(Void.class,1,true));
				else moduleTitle=new ToggleButton(module,module.isEnabled(),theme.getButtonRenderer(IBoolean.class,1,true));
				ClosableComponent<FocusableComponent,VerticalContainer> moduleContainer=new ClosableComponent<FocusableComponent,VerticalContainer>(moduleTitle,new VerticalContainer(module,theme.getContainerRenderer(1)),()->module.isEnabled(),new SimpleToggleable(false),animation.get(),theme.getPanelRenderer(IBoolean.class,1));
				categoryContent.addComponent(moduleContainer);
				module.getSettings().forEach(setting->addSettingsComponent(setting,moduleContainer.getCollapsible().getComponent(),theme,2));
			});
		});
	}
	
	protected <T> void addSettingsComponent (ISetting<T> setting, VerticalContainer container, ITheme theme, int level) {
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
			ClosableComponent<IComponent,VerticalContainer> settingContainer=new ClosableComponent<IComponent,VerticalContainer>(component,new VerticalContainer(setting,theme.getContainerRenderer(level)),()->setting.getSettingState(),new SimpleToggleable(false),animation.get(),theme.getPanelRenderer(setting.getSettingClass(),level));
			setting.getSubSettings().forEach(subSetting->addSettingsComponent(subSetting,settingContainer.getCollapsible().getComponent(),theme,level+1));
			
		} else {
			container.addComponent(component);
		}
	}
	
	public enum ChildMode {
		DOWN,POPUP,DRAG_POPUP;
	}
}
