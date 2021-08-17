package com.lukflug.examplemod8forge.setting;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

import com.lukflug.panelstudio.base.IBoolean;
import com.lukflug.panelstudio.setting.ILabeled;
import com.lukflug.panelstudio.setting.ISetting;

public abstract class Setting<T> implements ILabeled {
	public final String displayName,configName,description;
	public final IBoolean visible;
	public final List<Setting<?>> subSettings=new ArrayList<Setting<?>>();
	private T value;
	
	public Setting (String displayName, String configName, String description, IBoolean visible, T value) {
		this.displayName=displayName;
		this.configName=configName;
		this.description=description;
		this.visible=visible;
		this.value=value;
	}

	public T getValue() {
		return value;
	}
	
	public void setValue (T value) {
		this.value=value;
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
	
	public Stream<ISetting<?>> getSubSettings() {
		if (subSettings.size()==0) return null;
		return subSettings.stream().filter(new Predicate<Setting<?>>() {
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
