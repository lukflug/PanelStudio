package com.lukflug.examplemod8forge.module;

import java.awt.Color;
import java.awt.Point;
import java.util.function.Supplier;

import org.lwjgl.input.Keyboard;

import com.lukflug.examplemod8forge.setting.ColorSetting;
import com.lukflug.panelstudio.base.Animation;
import com.lukflug.panelstudio.base.IToggleable;
import com.lukflug.panelstudio.component.IFixedComponent;
import com.lukflug.panelstudio.container.IContainer;
import com.lukflug.panelstudio.setting.IClient;
import com.lukflug.panelstudio.tabgui.ITabGUITheme;
import com.lukflug.panelstudio.tabgui.StandardTheme;
import com.lukflug.panelstudio.tabgui.TabGUI;
import com.lukflug.panelstudio.theme.IColorScheme;
import com.lukflug.panelstudio.theme.ITheme;

public class TabGUIModule extends Module {
	private static TabGUIModule instance;
	private static ITabGUITheme theme;
	
	public TabGUIModule() {
		super("TabGUI","HUD module that lets toggle modules.",()->true,true);
		instance=this;
		theme=new StandardTheme(new IColorScheme() {
			@Override
			public void createSetting(ITheme theme, String name, String description, boolean hasAlpha, boolean allowsRainbow, Color color, boolean rainbow) {
				ColorSetting setting=new ColorSetting(name,name,description,()->true,allowsRainbow,hasAlpha,color,rainbow);
				instance.settings.add(setting);
			}

			@Override
			public Color getColor (String name) {
				return (Color)instance.settings.stream().filter(setting->setting.configName.equals(name)).findFirst().orElse(null).getValue();
			}
		},75,9,2,5);
	}

	public static IFixedComponent getComponent (IClient client, IContainer<IFixedComponent> container, Supplier<Animation> animation) {
		return new TabGUI(()->"TabGUI",client,theme,container,animation,key->key==Keyboard.KEY_UP,key->key==Keyboard.KEY_DOWN,key->key==Keyboard.KEY_RETURN||key==Keyboard.KEY_RIGHT,key->key==Keyboard.KEY_LEFT,new Point(10,10),"tabGUI").getWrappedComponent();
	}
	
	public static IToggleable getToggle() {
		return instance.isEnabled();
	}
}
