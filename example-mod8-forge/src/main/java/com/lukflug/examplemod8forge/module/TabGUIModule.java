package com.lukflug.examplemod8forge.module;

import java.awt.Color;
import java.awt.Point;
import java.util.function.IntPredicate;
import java.util.function.Predicate;
import java.util.function.Supplier;

import org.lwjgl.input.Keyboard;

import com.lukflug.examplemod8forge.setting.ColorSetting;
import com.lukflug.examplemod8forge.setting.Setting;
import com.lukflug.panelstudio.base.Animation;
import com.lukflug.panelstudio.base.ConstantToggleable;
import com.lukflug.panelstudio.base.IToggleable;
import com.lukflug.panelstudio.component.IFixedComponent;
import com.lukflug.panelstudio.container.IContainer;
import com.lukflug.panelstudio.setting.IClient;
import com.lukflug.panelstudio.setting.ILabeled;
import com.lukflug.panelstudio.tabgui.ITabGUITheme;
import com.lukflug.panelstudio.tabgui.StandardTheme;
import com.lukflug.panelstudio.tabgui.TabGUI;
import com.lukflug.panelstudio.theme.IColorScheme;
import com.lukflug.panelstudio.theme.ITheme;

public class TabGUIModule extends Module {
	private static TabGUIModule instance;
	private static ITabGUITheme theme;
	
	public TabGUIModule() {
		super("TabGUI","HUD module that lets toggle modules.",new ConstantToggleable(true),true);
		instance=this;
		theme=new StandardTheme(new IColorScheme() {
			@Override
			public void createSetting(ITheme theme, String name, String description, boolean hasAlpha, boolean allowsRainbow, Color color, boolean rainbow) {
				ColorSetting setting=new ColorSetting(name,name,description,new ConstantToggleable(true),allowsRainbow,hasAlpha,color,rainbow);
				instance.settings.add(setting);
			}

			@Override
			public Color getColor (final String name) {
				return (Color)instance.settings.stream().filter(new Predicate<Setting<?>>() {
					@Override
					public boolean test (Setting<?> t) {
						return t.configName.equals(name);
					}
				}).findFirst().orElse(null).getValue();
			}
		},75,9,2,5);
	}

	public static IFixedComponent getComponent (IClient client, IContainer<IFixedComponent> container, Supplier<Animation> animation) {
		return new TabGUI(new ILabeled() {
			@Override
			public String getDisplayName() {
				return "TabGUI";
			}
		},client,theme,container,animation,new IntPredicate() {
			@Override
			public boolean test (int value) {
				return value==Keyboard.KEY_UP;
			}
		},new IntPredicate() {
			@Override
			public boolean test (int value) {
				return value==Keyboard.KEY_DOWN;
			}
		},new IntPredicate() {
			@Override
			public boolean test (int value) {
				return value==Keyboard.KEY_RETURN||value==Keyboard.KEY_RIGHT;
			}
		},new IntPredicate() {
			@Override
			public boolean test (int value) {
				return value==Keyboard.KEY_LEFT;
			}
		},new Point(10,10),"tabGUI").getWrappedComponent();
	}
	
	public static IToggleable getToggle() {
		return instance.isEnabled();
	}
}
