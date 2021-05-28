package com.lukflug.panelstudio.layout;

import java.util.function.Supplier;

import com.lukflug.panelstudio.base.Animation;
import com.lukflug.panelstudio.base.SimpleToggleable;
import com.lukflug.panelstudio.component.IComponent;
import com.lukflug.panelstudio.setting.IBooleanSetting;
import com.lukflug.panelstudio.setting.IColorSetting;
import com.lukflug.panelstudio.setting.IEnumSetting;
import com.lukflug.panelstudio.setting.IKeybindSetting;
import com.lukflug.panelstudio.setting.INumberSetting;
import com.lukflug.panelstudio.setting.ISetting;
import com.lukflug.panelstudio.setting.IStringSetting;
import com.lukflug.panelstudio.theme.ThemeTuple;
import com.lukflug.panelstudio.widget.Button;
import com.lukflug.panelstudio.widget.ColorSliderComponent;
import com.lukflug.panelstudio.widget.CycleButton;
import com.lukflug.panelstudio.widget.ITextFieldKeys;
import com.lukflug.panelstudio.widget.KeybindComponent;
import com.lukflug.panelstudio.widget.NumberSlider;
import com.lukflug.panelstudio.widget.TextField;
import com.lukflug.panelstudio.widget.ToggleButton;

public interface IComponentGenerator {
	public default IComponent getComponent (ISetting<?> setting, Supplier<Animation> animation, IComponentAdder adder, ThemeTuple theme, int colorLevel, boolean isContainer) {
		if (setting instanceof IBooleanSetting) {
			return getBooleanComponent((IBooleanSetting)setting,animation,adder,theme,colorLevel,isContainer);
		} else if (setting instanceof INumberSetting) {
			return getNumberComponent((INumberSetting)setting,animation,adder,theme,colorLevel,isContainer);
		} else if (setting instanceof IEnumSetting) {
			return getEnumComponent((IEnumSetting)setting,animation,adder,theme,colorLevel,isContainer);
		} else if (setting instanceof IColorSetting) {
			return getColorComponent((IColorSetting)setting,animation,adder,theme,colorLevel,isContainer);
		} else if (setting instanceof IKeybindSetting) {
			return getKeybindComponent((IKeybindSetting)setting,animation,adder,theme,colorLevel,isContainer);
		} else if (setting instanceof IStringSetting) {
			return getStringComponent((IStringSetting)setting,animation,adder,theme,colorLevel,isContainer);
		} else {
			return new Button<Void>(setting,()->null,theme.getButtonRenderer(Void.class,isContainer));
		}
	}
	
	public default IComponent getBooleanComponent (IBooleanSetting setting, Supplier<Animation> animation, IComponentAdder adder, ThemeTuple theme, int colorLevel, boolean isContainer) {
		return new ToggleButton(setting,theme.getButtonRenderer(Boolean.class,isContainer));
	}
	
	public default IComponent getNumberComponent (INumberSetting setting, Supplier<Animation> animation, IComponentAdder adder, ThemeTuple theme, int colorLevel, boolean isContainer) {
		return new NumberSlider(setting,theme.getSliderRenderer(isContainer));
	}
	
	public default IComponent getEnumComponent (IEnumSetting setting, Supplier<Animation> animation, IComponentAdder adder, ThemeTuple theme, int colorLevel, boolean isContainer) {
		return new CycleButton(setting,theme.getButtonRenderer(String.class,isContainer));
	}
	
	public default IComponent getColorComponent (IColorSetting setting, Supplier<Animation> animation, IComponentAdder adder, ThemeTuple theme, int colorLevel, boolean isContainer) {
		return new ColorSliderComponent((IColorSetting)setting,new ThemeTuple(theme.theme,theme.logicalLevel,colorLevel));
	}
	
	public default IComponent getKeybindComponent (IKeybindSetting setting, Supplier<Animation> animation, IComponentAdder adder, ThemeTuple theme, int colorLevel, boolean isContainer) {
		return new KeybindComponent(setting,theme.getKeybindRenderer(isContainer));
	}
	
	public default IComponent getStringComponent (IStringSetting setting, Supplier<Animation> animation, IComponentAdder adder, ThemeTuple theme, int colorLevel, boolean isContainer) {
		return new TextField(setting, new ITextFieldKeys() {
			@Override
			public boolean isBackspaceKey(int scancode) {
				return false;
			}

			@Override
			public boolean isDeleteKey(int scancode) {
				return false;
			}

			@Override
			public boolean isInsertKey(int scancode) {
				return false;
			}

			@Override
			public boolean isLeftKey(int scancode) {
				return false;
			}

			@Override
			public boolean isRightKey(int scancode) {
				return false;
			}

			@Override
			public boolean isHomeKey(int scancode) {
				return false;
			}

			@Override
			public boolean isEndKey(int scancode) {
				return false;
			}

			@Override
			public boolean isCopyKey(int scancode) {
				return false;
			}

			@Override
			public boolean isPasteKey(int scancode) {
				return false;
			}

			@Override
			public boolean isCutKey(int scancode) {
				return false;
			}

			@Override
			public boolean isAllKey(int scancode) {
				return false;
			}
		},0,new SimpleToggleable(false),theme.getTextRenderer(false,isContainer)) {
			@Override
			public boolean allowCharacter(char character) {
				return false;
			}
		};
	}
}
