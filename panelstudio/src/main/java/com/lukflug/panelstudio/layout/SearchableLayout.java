package com.lukflug.panelstudio.layout;

import java.awt.Point;
import java.util.Comparator;
import java.util.function.Function;
import java.util.function.IntPredicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

import com.lukflug.panelstudio.base.Animation;
import com.lukflug.panelstudio.base.Context;
import com.lukflug.panelstudio.base.IBoolean;
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
import com.lukflug.panelstudio.setting.IModule;
import com.lukflug.panelstudio.setting.ISetting;
import com.lukflug.panelstudio.theme.ITheme;
import com.lukflug.panelstudio.theme.ThemeTuple;
import com.lukflug.panelstudio.widget.Button;
import com.lukflug.panelstudio.widget.ITextFieldKeys;
import com.lukflug.panelstudio.widget.ScrollBarComponent;
import com.lukflug.panelstudio.widget.SearchableRadioButton;

public class SearchableLayout implements ILayout,IScrollSize {
	protected ILabeled titleLabel,searchLabel;
	protected Point position;
	protected int width;
	protected Supplier<Animation> animation;
	protected String enabledButton;
	protected int weight;
	protected ChildMode colorType;
	protected ChildUtil util;
	protected Comparator<IModule> comparator;
	protected IntPredicate charFilter;
	protected ITextFieldKeys keys;
	
	public SearchableLayout (ILabeled titleLabel, ILabeled searchLabel, Point position, int width, int popupWidth, Supplier<Animation> animation, String enabledButton, int weight, ChildMode colorType, PopupTuple popupType, Comparator<IModule> comparator, IntPredicate charFilter, ITextFieldKeys keys) {
		this.titleLabel=titleLabel;
		this.searchLabel=searchLabel;
		this.position=position;
		this.width=width;
		this.animation=animation;
		this.enabledButton=enabledButton;
		this.weight=weight;
		this.colorType=colorType;
		this.comparator=comparator;
		this.charFilter=charFilter;
		this.keys=keys;
		util=new ChildUtil(popupWidth,animation,popupType);
	}
	
	@Override
	public void populateGUI (IComponentAdder gui, IComponentGenerator components, IClient client, ITheme theme) {
		Button<Void> title=new Button<Void>(titleLabel,()->null,theme.getButtonRenderer(Void.class,0,0,true));
		HorizontalContainer window=new HorizontalContainer(titleLabel,theme.getContainerRenderer(0,0,true));
		Supplier<Stream<IModule>> modules=()->client.getCategories().flatMap(cat->cat.getModules()).sorted(comparator);
		IEnumSetting modSelect=addContainer(searchLabel,modules.get().map(mod->mod),window,new ThemeTuple(theme,0,1),false,button->wrapColumn(button,new ThemeTuple(theme,0,1),1),()->true);
		gui.addComponent(title,window,new ThemeTuple(theme,0,0),position,width,animation);
		modules.get().forEach(module->{
			VerticalContainer container=new VerticalContainer(module,theme.getContainerRenderer(1,1,false));
			window.addComponent(wrapColumn(container,new ThemeTuple(theme,1,1),weight),()->modSelect.getValueName()==module.getDisplayName());
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
			},animation,gui,new ThemeTuple(theme,1,2),2,false));
			module.getSettings().forEach(setting->addSettingsComponent(setting,container,gui,components,new ThemeTuple(theme,2,2)));
		});
	}
	
	protected <T> void addSettingsComponent (ISetting<T> setting, VerticalContainer container, IComponentAdder gui, IComponentGenerator components, ThemeTuple theme) {
		int colorLevel=(colorType==ChildMode.DOWN)?theme.graphicalLevel:0;
		boolean isContainer=setting.getSubSettings()!=null;
		IComponent component=components.getComponent(setting,animation,gui,theme,colorLevel,isContainer);
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
	
	protected <T extends IComponent> IEnumSetting addContainer (ILabeled label, Stream<ILabeled> labels, IContainer<T> window, ThemeTuple theme, boolean horizontal, Function<SearchableRadioButton,T> container, IBoolean visible) {
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
		SearchableRadioButton button=new SearchableRadioButton(setting,theme,true,keys) {
			@Override
			protected Animation getAnimation() {
				return animation.get();
			}

			@Override
			public boolean allowCharacter(char character) {
				return charFilter.test(character);
			}
			
			@Override
			protected boolean isUpKey (int key) {
				if (horizontal) return isLeftKey(key);
				else return SearchableLayout.this.isUpKey(key);
			}

			@Override
			protected boolean isDownKey (int key) {
				if (horizontal) return isRightKey(key);
				else return SearchableLayout.this.isDownKey(key);
			}
		};
		window.addComponent(container.apply(button),visible);
		return setting;
	}
	
	protected HorizontalComponent<ScrollBarComponent<Void,IComponent>> wrapColumn (IComponent button, ThemeTuple theme, int weight) {
		return new HorizontalComponent<ScrollBarComponent<Void,IComponent>>(new ScrollBarComponent<Void,IComponent>(button,theme.getScrollBarRenderer(Void.class),theme.getEmptySpaceRenderer(Void.class,false),theme.getEmptySpaceRenderer(Void.class,true)) {
			@Override
			public int getScrollHeight (Context context, int componentHeight) {
				return SearchableLayout.this.getScrollHeight(context,componentHeight);
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
