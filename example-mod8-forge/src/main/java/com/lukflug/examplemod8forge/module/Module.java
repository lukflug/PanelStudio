package com.lukflug.examplemod8forge.module;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

import com.lukflug.examplemod8forge.setting.Setting;
import com.lukflug.panelstudio.base.IBoolean;
import com.lukflug.panelstudio.base.IToggleable;
import com.lukflug.panelstudio.setting.IModule;
import com.lukflug.panelstudio.setting.ISetting;

public class Module implements IModule {
	public final String displayName,description;
	public final IBoolean visible;
	public final List<Setting<?>> settings=new ArrayList<Setting<?>>();
	public final boolean toggleable;
	private boolean enabled=false;
	
	public Module (String displayName, String description, IBoolean visible, boolean toggleable) {
		this.displayName=displayName;
		this.description=description;
		this.visible=visible;
		this.toggleable=toggleable;
	}
	
	@Override
	public String getDisplayName() {
		return displayName;
	}
	
	@Override
	public String getDescription() {
		return description;
	}
	
	@Override
	public IBoolean isVisible() {
		return visible;
	}

	@Override
	public IToggleable isEnabled() {
		if (!toggleable) return null;
		return new IToggleable() {
			@Override
			public boolean isOn() {
				return enabled;
			}

			@Override
			public void toggle() {
				enabled=!enabled;
			}
		};
	}

	@Override
	public Stream<ISetting<?>> getSettings() {
		return settings.stream().filter(new Predicate<Setting<?>>() {
			@Override
			public boolean test (Setting<?> t) {
				return t instanceof ISetting;
			}
		}).sorted(new Comparator<Setting<?>>() {
			@Override
			public int compare (Setting<?> o1, Setting<?> o2) {
				return o1.displayName.compareTo(o2.displayName);
			}
		}).map(new Function<Setting<?>,ISetting<?>>() {
			@Override
			public ISetting<?> apply (Setting<?> t) {
				return (ISetting<?>)t;
			}
		});
	}
}
