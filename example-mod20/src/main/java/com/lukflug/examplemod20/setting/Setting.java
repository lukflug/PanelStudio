package com.lukflug.examplemod20.setting;

import java.util.ArrayList;
import java.util.List;
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
		return subSettings.stream().filter(setting->setting instanceof ISetting).sorted((a,b)->a.displayName.compareTo(b.displayName)).map(setting->(ISetting<?>)setting);
	}
}
