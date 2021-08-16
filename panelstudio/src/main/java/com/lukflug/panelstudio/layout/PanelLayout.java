package com.lukflug.panelstudio.layout;

import java.awt.Point;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntFunction;
import java.util.function.Supplier;

import com.lukflug.panelstudio.base.Animation;
import com.lukflug.panelstudio.component.FocusableComponent;
import com.lukflug.panelstudio.component.IComponent;
import com.lukflug.panelstudio.container.VerticalContainer;
import com.lukflug.panelstudio.layout.ChildUtil.ChildMode;
import com.lukflug.panelstudio.popup.PopupTuple;
import com.lukflug.panelstudio.setting.IClient;
import com.lukflug.panelstudio.setting.ISetting;
import com.lukflug.panelstudio.theme.ITheme;
import com.lukflug.panelstudio.theme.ThemeTuple;
import com.lukflug.panelstudio.widget.Button;
import com.lukflug.panelstudio.widget.ToggleButton;

/**
 * A layout that organizes components in the traditional ClickGUI panels.
 * @author lukflug
 */
public class PanelLayout implements ILayout {
	/**
	 * The panel width.
	 */
	protected int width;
	/**
	 * The position of the first panel.
	 */
	protected Point start;
	/**
	 * The horizontal panel offset.
	 */
	protected int skipX;
	/**
	 * The vertical alternating panel offset.
	 */
	protected int skipY;
	/**
	 * The animation supplier.
	 */
	protected Supplier<Animation> animation;
	/**
	 * The child mode to use for sub-panels (modules,nested settings).
	 */
	protected IntFunction<ChildMode> layoutType;
	/**
	 * The child mode to use for setting components that are containers (e.g. color components).
	 */
	protected IntFunction<ChildMode> colorType;
	/**
	 * The child util instance.
	 */
	protected ChildUtil util;
	
	/**
	 * Constructor.
	 * @param width panel width
	 * @param start position of the first panel
	 * @param skipX horizontal panel offset
	 * @param skipY vertical alternating panel offset
	 * @param animation animation supplier
	 * @param layoutType child mode to use for sub-panels (modules,nested settings)
	 * @param colorType child mode to use for setting components that are containers (e.g. color components)
	 * @param popupType child util instance
	 */
	public PanelLayout (int width, Point start, int skipX, int skipY, Supplier<Animation> animation, IntFunction<ChildMode> layoutType, IntFunction<ChildMode> colorType, PopupTuple popupType) {
		this.width=width;
		this.start=start;
		this.skipX=skipX;
		this.skipY=skipY;
		this.animation=animation;
		this.layoutType=layoutType;
		this.colorType=colorType;
		util=new ChildUtil(width,animation,popupType);
	}
	
	@Override
	public void populateGUI (IComponentAdder gui, IComponentGenerator components, IClient client, ITheme theme) {
		Point pos=start;
		AtomicInteger skipY=new AtomicInteger(this.skipY);
		client.getCategories().forEach(category->{
			Button<Void> categoryTitle=new Button<Void>(category,()->null,theme.getButtonRenderer(Void.class,0,0,true));
			VerticalContainer categoryContent=new VerticalContainer(category,theme.getContainerRenderer(0,0,false));
			gui.addComponent(categoryTitle,categoryContent,new ThemeTuple(theme,0,0),new Point(pos),width,animation);
			pos.translate(skipX,skipY.get());
			skipY.set(-skipY.get());
			category.getModules().forEach(module->{
				ChildMode mode=layoutType.apply(0);
				int graphicalLevel=(mode==ChildMode.DOWN)?1:0;
				FocusableComponent moduleTitle;
				if (module.isEnabled()==null) moduleTitle=new Button<Void>(module,()->null,theme.getButtonRenderer(Void.class,1,1,mode==ChildMode.DOWN));
				else moduleTitle=new ToggleButton(module,module.isEnabled(),theme.getButtonRenderer(Boolean.class,1,1,mode==ChildMode.DOWN));
				VerticalContainer moduleContainer=new VerticalContainer(module,theme.getContainerRenderer(1,graphicalLevel,false));
				if (module.isEnabled()==null) util.addContainer(module,moduleTitle,moduleContainer,()->null,Void.class,categoryContent,gui,new ThemeTuple(theme,1,graphicalLevel),layoutType.apply(0));
				else util.addContainer(module,moduleTitle,moduleContainer,()->module.isEnabled().isOn(),Boolean.class,categoryContent,gui,new ThemeTuple(theme,1,graphicalLevel),layoutType.apply(0));
				module.getSettings().forEach(setting->addSettingsComponent(setting,moduleContainer,gui,components,new ThemeTuple(theme,2,graphicalLevel+1)));
			});
		});
	}
	
	/**
	 * Add a setting component.
	 * @param <T> the setting state type
	 * @param setting the setting to be added
	 * @param container the parent container
	 * @param gui the component adder for pop-ups
	 * @param components the component generator
	 * @param theme the theme to be used
	 */
	protected <T> void addSettingsComponent (ISetting<T> setting, VerticalContainer container, IComponentAdder gui, IComponentGenerator components, ThemeTuple theme) {
		int nextLevel=(layoutType.apply(theme.logicalLevel-1)==ChildMode.DOWN)?theme.graphicalLevel:0;
		int colorLevel=(colorType.apply(theme.logicalLevel-1)==ChildMode.DOWN)?theme.graphicalLevel:0;
		boolean isContainer=(setting.getSubSettings()!=null)&&(layoutType.apply(theme.logicalLevel-1)==ChildMode.DOWN);
		IComponent component=components.getComponent(setting,animation,gui,theme,colorLevel,isContainer);
		if (component instanceof VerticalContainer) {
			VerticalContainer colorContainer=(VerticalContainer)component;
			Button<T> button=new Button<T>(setting,()->setting.getSettingState(),theme.getButtonRenderer(setting.getSettingClass(),colorType.apply(theme.logicalLevel-1)==ChildMode.DOWN));
			util.addContainer(setting,button,colorContainer,()->setting.getSettingState(),setting.getSettingClass(),container,gui,new ThemeTuple(theme.theme,theme.logicalLevel,colorLevel),colorType.apply(theme.logicalLevel-1));
			if (setting.getSubSettings()!=null) setting.getSubSettings().forEach(subSetting->addSettingsComponent(subSetting,colorContainer,gui,components,new ThemeTuple(theme.theme,theme.logicalLevel+1,colorLevel+1)));
		} else if (setting.getSubSettings()!=null) {
			VerticalContainer settingContainer=new VerticalContainer(setting,theme.theme.getContainerRenderer(theme.logicalLevel,nextLevel,false));
			util.addContainer(setting,component,settingContainer,()->setting.getSettingState(),setting.getSettingClass(),container,gui,new ThemeTuple(theme.theme,theme.logicalLevel,nextLevel),layoutType.apply(theme.logicalLevel-1));
			setting.getSubSettings().forEach(subSetting->addSettingsComponent(subSetting,settingContainer,gui,components,new ThemeTuple(theme.theme,theme.logicalLevel+1,nextLevel+1)));
		} else {
			container.addComponent(component);
		}
	}
}
