package com.lukflug.panelstudio.widget;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;

import com.lukflug.panelstudio.base.Animation;
import com.lukflug.panelstudio.base.Context;
import com.lukflug.panelstudio.base.IBoolean;
import com.lukflug.panelstudio.base.SimpleToggleable;
import com.lukflug.panelstudio.container.VerticalContainer;
import com.lukflug.panelstudio.setting.IEnumSetting;
import com.lukflug.panelstudio.setting.ILabeled;
import com.lukflug.panelstudio.setting.IStringSetting;
import com.lukflug.panelstudio.setting.Labeled;
import com.lukflug.panelstudio.theme.IContainerRenderer;
import com.lukflug.panelstudio.theme.ThemeTuple;

public abstract class SearchableRadioButton extends VerticalContainer {
	protected boolean transferFocus=false;
	
	public SearchableRadioButton(IEnumSetting setting, ThemeTuple theme, boolean container, ITextFieldKeys keys) {
		super(setting,new IContainerRenderer(){});
		AtomicReference<String> searchTerm=new AtomicReference<String>("");
		TextField textField=new TextField(new IStringSetting() {
			@Override
			public String getDisplayName() {
				return setting.getDisplayName();
			}

			@Override
			public String getValue() {
				return searchTerm.get();
			}

			@Override
			public void setValue(String string) {
				searchTerm.set(string);
			}
		},keys,0,new SimpleToggleable(false),theme.getTextRenderer(true,container)) {
			@Override
			public void handleButton (Context context, int button) {
				super.handleButton(context,button);
				if (hasFocus(context)) transferFocus=true;
			}
			
			@Override
			public boolean allowCharacter(char character) {
				return SearchableRadioButton.this.allowCharacter(character);
			}
		};
		addComponent(textField);
		RadioButton content=new RadioButton(new IEnumSetting() {
			ILabeled[] values=Arrays.stream(setting.getAllowedValues()).map(value->new Labeled(value.getDisplayName(),value.getDescription(),()->{
				if (!value.isVisible().isOn()) return false;
				else return value.getDisplayName().toUpperCase().contains(searchTerm.get().toUpperCase());
			})).toArray(ILabeled[]::new);
			
			@Override
			public String getDisplayName() {
				return setting.getDisplayName();
			}
			
			@Override
			public String getDescription() {
				return setting.getDescription();
			}
			
			
			@Override
			public IBoolean isVisible() {
				return setting.isVisible();
			}

			@Override
			public void increment() {
				setting.increment();
			}

			@Override
			public void decrement() {
				setting.decrement();
			}

			@Override
			public String getValueName() {
				return setting.getValueName();
			}

			@Override
			public void setValueIndex(int index) {
				setting.setValueIndex(index);
			}

			@Override
			public ILabeled[] getAllowedValues() {
				return values;
			}
		},theme.getRadioRenderer(container),getAnimation(),false) {
			@Override
			protected boolean isUpKey(int key) {
				return SearchableRadioButton.this.isUpKey(key);
			}

			@Override
			protected boolean isDownKey(int key) {
				return SearchableRadioButton.this.isDownKey(key);
			}
		};
		addComponent(content);
	}

	protected abstract Animation getAnimation();
	
	public abstract boolean allowCharacter (char character);
	
	protected abstract boolean isUpKey (int key);

	protected abstract boolean isDownKey (int key);
}
