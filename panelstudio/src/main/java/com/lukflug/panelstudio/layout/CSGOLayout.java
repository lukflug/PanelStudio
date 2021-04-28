package com.lukflug.panelstudio.layout;

import java.awt.Point;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

import com.lukflug.panelstudio.base.Animation;
import com.lukflug.panelstudio.base.Context;
import com.lukflug.panelstudio.base.IBoolean;
import com.lukflug.panelstudio.component.FocusableComponent;
import com.lukflug.panelstudio.component.HorizontalComponent;
import com.lukflug.panelstudio.component.IComponent;
import com.lukflug.panelstudio.component.IScrollSize;
import com.lukflug.panelstudio.container.HorizontalContainer;
import com.lukflug.panelstudio.container.IContainer;
import com.lukflug.panelstudio.container.VerticalContainer;
import com.lukflug.panelstudio.layout.ChildUtil.ChildMode;
import com.lukflug.panelstudio.popup.PopupTuple;
import com.lukflug.panelstudio.setting.IBooleanSetting;
import com.lukflug.panelstudio.setting.IClient;
import com.lukflug.panelstudio.setting.IEnumSetting;
import com.lukflug.panelstudio.setting.ILabeled;
import com.lukflug.panelstudio.setting.ISetting;
import com.lukflug.panelstudio.theme.ITheme;
import com.lukflug.panelstudio.theme.ThemeTuple;
import com.lukflug.panelstudio.widget.Button;
import com.lukflug.panelstudio.widget.RadioButton;
import com.lukflug.panelstudio.widget.ScrollBarComponent;
import com.lukflug.panelstudio.widget.ToggleButton;

public class CSGOLayout implements ILayout,IScrollSize {
	protected ILabeled label;
	protected Point position;
	protected int width;
	protected Supplier<Animation> animation;
	protected String enabledButton;
	protected boolean horizontal,moduleColumn;
	protected int weight;
	protected ChildMode colorType;
	protected ChildUtil util;
	
	public CSGOLayout (ILabeled label, Point position, int width, int popupWidth, Supplier<Animation> animation, String enabledButton, boolean horizontal, boolean moduleColumn, int weight, ChildMode colorType, PopupTuple popupType) {
		this.label=label;
		this.position=position;
		this.width=width;
		this.animation=animation;
		this.enabledButton=enabledButton;
		this.horizontal=horizontal;
		this.moduleColumn=moduleColumn;
		this.weight=weight;
		this.colorType=colorType;
		util=new ChildUtil(popupWidth,animation,popupType);
	}
	
	@Override
	public void populateGUI (IComponentAdder gui, IComponentGenerator components, IClient client, ITheme theme) {
		Button<Void> title=new Button<Void>(label,()->null,theme.getButtonRenderer(Void.class,0,0,true));
		HorizontalContainer window=new HorizontalContainer(label,theme.getContainerRenderer(0,horizontal?1:0,true));
		IEnumSetting catSelect;
		if (horizontal) {
			VerticalContainer container=new VerticalContainer(label,theme.getContainerRenderer(0,0,false));
			catSelect=addContainer(label,client.getCategories().map(cat->cat),container,new ThemeTuple(theme,0,1),true,button->button,()->true);
			container.addComponent(window);
			gui.addComponent(title,container,new ThemeTuple(theme,0,0),position,width,animation);
		} else {
			catSelect=addContainer(label,client.getCategories().map(cat->cat),window,new ThemeTuple(theme,0,1),false,button->wrapColumn(button,new ThemeTuple(theme,0,1),1),()->true);
			gui.addComponent(title,window,new ThemeTuple(theme,0,0),position,width,animation);
		}
		client.getCategories().forEach(category->{
			if (moduleColumn) {
				IEnumSetting modSelect=addContainer(category,category.getModules().map(mod->mod),window,new ThemeTuple(theme,1,1),false,button->wrapColumn(button,new ThemeTuple(theme,0,1),1),()->catSelect.getValueName()==category.getDisplayName());
				category.getModules().forEach(module->{
					VerticalContainer container=new VerticalContainer(module,theme.getContainerRenderer(1,1,false));
					window.addComponent(wrapColumn(container,new ThemeTuple(theme,1,1),weight),()->catSelect.getValueName()==category.getDisplayName()&&modSelect.getValueName()==module.getDisplayName());
					if (module.isEnabled()!=null) container.addComponent(components.getComponent(new IBooleanSetting() {
						@Override
						public String getDisplayName() {
							return enabledButton;
						}

						@Override
						public void toggle() {
							module.isEnabled().toggle();
						}

						@Override
						public boolean isOn() {
							return module.isEnabled().isOn();
						}
					},animation,new ThemeTuple(theme,1,2),2,false));
					module.getSettings().forEach(setting->addSettingsComponent(setting,container,gui,components,new ThemeTuple(theme,2,2)));
				});
			} else {
				VerticalContainer categoryContent=new VerticalContainer(category,theme.getContainerRenderer(0,1,false));
				window.addComponent(wrapColumn(categoryContent,new ThemeTuple(theme,0,1),1),()->catSelect.getValueName()==category.getDisplayName());
				category.getModules().forEach(module->{
					int graphicalLevel=1;
					FocusableComponent moduleTitle;
					if (module.isEnabled()==null) moduleTitle=new Button<Void>(module,()->null,theme.getButtonRenderer(Void.class,1,1,true));
					else moduleTitle=new ToggleButton(module,module.isEnabled(),theme.getButtonRenderer(Boolean.class,1,1,true));
					VerticalContainer moduleContainer=new VerticalContainer(module,theme.getContainerRenderer(1,graphicalLevel,false));
					if (module.isEnabled()==null) util.addContainer(module,moduleTitle,moduleContainer,()->null,Void.class,categoryContent,gui,new ThemeTuple(theme,1,graphicalLevel),ChildMode.DOWN);
					else util.addContainer(module,moduleTitle,moduleContainer,()->module.isEnabled(),IBoolean.class,categoryContent,gui,new ThemeTuple(theme,1,graphicalLevel),ChildMode.DOWN);
					module.getSettings().forEach(setting->addSettingsComponent(setting,moduleContainer,gui,components,new ThemeTuple(theme,2,graphicalLevel+1)));
				});
			}
		});
	}
	
	protected <T> void addSettingsComponent (ISetting<T> setting, VerticalContainer container, IComponentAdder gui, IComponentGenerator components, ThemeTuple theme) {
		int colorLevel=(colorType==ChildMode.DOWN)?theme.graphicalLevel:0;
		boolean isContainer=setting.getSubSettings()!=null;
		IComponent component=components.getComponent(setting,animation,theme,colorLevel,isContainer);
		if (component instanceof VerticalContainer) {
			VerticalContainer colorContainer=(VerticalContainer)component;
			Button<T> button=new Button<T>(setting,()->setting.getSettingState(),theme.getButtonRenderer(setting.getSettingClass(),colorType==ChildMode.DOWN));
			util.addContainer(setting,button,colorContainer,()->setting.getSettingState(),setting.getSettingClass(),container,gui,new ThemeTuple(theme.theme,theme.logicalLevel,colorLevel),colorType);
			if (setting.getSubSettings()!=null) setting.getSubSettings().forEach(subSetting->addSettingsComponent(subSetting,colorContainer,gui,components,new ThemeTuple(theme.theme,theme.logicalLevel+1,colorLevel+1)));
		} else if (setting.getSubSettings()!=null) {
			VerticalContainer settingContainer=new VerticalContainer(setting,theme.getContainerRenderer(false));
			util.addContainer(setting,component,settingContainer,()->setting.getSettingState(),setting.getSettingClass(),container,gui,theme,ChildMode.DOWN);
			setting.getSubSettings().forEach(subSetting->addSettingsComponent(subSetting,settingContainer,gui,components,new ThemeTuple(theme,1,1)));
		} else {
			container.addComponent(component);
		}
	}
	
	protected <T extends IComponent> IEnumSetting addContainer (ILabeled label, Stream<ILabeled> labels, IContainer<T> window, ThemeTuple theme, boolean horizontal, Function<RadioButton,T> container, IBoolean visible) {
		IEnumSetting setting=new IEnumSetting() {
			private int state=0;
			private ILabeled array[]=labels.toArray(ILabeled[]::new);
			
			@Override
			public String getDisplayName() {
				return label.getDisplayName();
			}
			
			@Override
			public String getDescription() {
				return label.getDescription();
			}
			
			@Override
			public IBoolean isVisible() {
				return label.isVisible();
			}

			@Override
			public void increment() {
				state=(state+1)%array.length;
			}
			
			@Override
			public void decrement() {
				state-=1;
				if (state<0) state=array.length-1;
			}

			@Override
			public String getValueName() {
				return array[state].getDisplayName();
			}

			@Override
			public void setValueIndex (int index) {
				state=index;
			}
			
			@Override
			public int getValueIndex() {
				return state;
			}

			@Override
			public ILabeled[] getAllowedValues() {
				return array;
			}
		};
		RadioButton button=new RadioButton(setting,theme.getRadioRenderer(true),animation.get(),horizontal) {
			@Override
			protected boolean isUpKey (int key) {
				if (horizontal) return isLeftKey(key);
				else return CSGOLayout.this.isUpKey(key);
			}

			@Override
			protected boolean isDownKey (int key) {
				if (horizontal) return isRightKey(key);
				else return CSGOLayout.this.isDownKey(key);
			}
		};
		window.addComponent(container.apply(button),visible);
		return setting;
	}
	
	protected HorizontalComponent<ScrollBarComponent<Void,IComponent>> wrapColumn (IComponent button, ThemeTuple theme, int weight) {
		return new HorizontalComponent<ScrollBarComponent<Void,IComponent>>(new ScrollBarComponent<Void,IComponent>(button,theme.getScrollBarRenderer(Void.class),theme.getEmptySpaceRenderer(Void.class,false),theme.getEmptySpaceRenderer(Void.class,true)) {
			@Override
			public int getScrollHeight (Context context, int componentHeight) {
				return CSGOLayout.this.getScrollHeight(context,componentHeight);
			}
			
			@Override
			protected Void getState() {
				return null;
			}
		},0,weight);
	}
	
	protected boolean isUpKey (int key) {
		return false;
	}
	
	protected boolean isDownKey (int key) {
		return false;
	}
	
	protected boolean isLeftKey (int key) {
		return false;
	}
	
	protected boolean isRightKey (int key) {
		return false;
	}
}
