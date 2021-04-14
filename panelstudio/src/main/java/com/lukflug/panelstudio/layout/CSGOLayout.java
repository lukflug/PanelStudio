package com.lukflug.panelstudio.layout;

import java.awt.Point;
import java.util.function.Function;
import java.util.function.IntPredicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

import com.lukflug.panelstudio.base.Animation;
import com.lukflug.panelstudio.base.IBoolean;
import com.lukflug.panelstudio.component.HorizontalComponent;
import com.lukflug.panelstudio.component.IComponent;
import com.lukflug.panelstudio.container.HorizontalContainer;
import com.lukflug.panelstudio.container.IContainer;
import com.lukflug.panelstudio.container.VerticalContainer;
import com.lukflug.panelstudio.setting.IClient;
import com.lukflug.panelstudio.setting.IEnumSetting;
import com.lukflug.panelstudio.setting.ILabeled;
import com.lukflug.panelstudio.setting.Labeled;
import com.lukflug.panelstudio.theme.ITheme;
import com.lukflug.panelstudio.theme.ThemeTuple;
import com.lukflug.panelstudio.widget.Button;
import com.lukflug.panelstudio.widget.RadioButton;
import com.lukflug.panelstudio.widget.ScrollBarComponent;

public class CSGOLayout implements ILayout {
	protected ILabeled label;
	protected Point position;
	protected int width;
	protected Supplier<Animation> animation;
	protected IntPredicate deleteKey;
	protected boolean horizontal,moduleColumn;
	protected int weight;
	
	public CSGOLayout (ILabeled label, Point position, int width, Supplier<Animation> animation, IntPredicate deleteKey, boolean horizontal, boolean moduleColumn, int weight) {
		this.label=label;
		this.position=position;
		this.width=width;
		this.animation=animation;
		this.deleteKey=deleteKey;
		this.horizontal=horizontal;
		this.moduleColumn=moduleColumn;
		this.weight=weight;
	}
	
	@Override
	public void populateGUI (IComponentAdder gui, IClient client, ITheme theme) {
		Button title=new Button(label,theme.getButtonRenderer(Void.class,0,0,true));
		HorizontalContainer window=new HorizontalContainer(label,theme.getContainerRenderer(0,0,true));
		IEnumSetting catSelect;
		if (horizontal) {
			VerticalContainer container=new VerticalContainer(label,theme.getContainerRenderer(0,0,false));
			catSelect=addContainer(label,client.getCategories().map(cat->cat),container,new ThemeTuple(theme,0,1),true,button->button);
			container.addComponent(window);
		} else {
			catSelect=addContainer(label,client.getCategories().map(cat->cat),window,new ThemeTuple(theme,0,1),false,button->wrapColumn(button,new ThemeTuple(theme,0,1),1));
			gui.addComponent(title,window,new ThemeTuple(theme,0,0),position,width,animation);
		}
		client.getCategories().forEach(category->{
			ILabeled label=new Labeled(category.getDisplayName(),category.getDescription(),()->category.isVisible().isOn()&&catSelect.getValueName()==category.getDisplayName());
			// TODO add module settings method and weight
			if (moduleColumn) {
				IEnumSetting modSelect=addContainer(label,category.getModules().map(mod->mod),window,new ThemeTuple(theme,1,1),false,button->wrapColumn(button,new ThemeTuple(theme,1,1),1));
				category.getModules().forEach(module->{
				});
			} else {
				category.getModules().forEach(module->{
				});
			}
		});
	}
	
	protected <T extends IComponent> IEnumSetting addContainer (ILabeled label, Stream<ILabeled> labels, IContainer<T> window, ThemeTuple theme, boolean horizontal, Function<RadioButton,T> container) {
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
		RadioButton button=new RadioButton(setting,theme.getRadioRenderer(true),animation.get(),horizontal);
		window.addComponent(container.apply(button));
		return setting;
	}
	
	protected static HorizontalComponent<ScrollBarComponent<Void,RadioButton>> wrapColumn (RadioButton button, ThemeTuple theme, int weight) {
		return new HorizontalComponent<ScrollBarComponent<Void,RadioButton>>(new ScrollBarComponent<Void,RadioButton>(button,theme.getScrollBarRenderer(Void.class),theme.getEmptySpaceRenderer(Void.class)) {
			@Override
			protected Void getState() {
				return null;
			}
		},0,weight);
	}
}
