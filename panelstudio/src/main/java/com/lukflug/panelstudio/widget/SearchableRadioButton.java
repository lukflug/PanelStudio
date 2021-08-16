package com.lukflug.panelstudio.widget;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;

import com.lukflug.panelstudio.base.Animation;
import com.lukflug.panelstudio.base.IBoolean;
import com.lukflug.panelstudio.base.SimpleToggleable;
import com.lukflug.panelstudio.container.VerticalContainer;
import com.lukflug.panelstudio.setting.IEnumSetting;
import com.lukflug.panelstudio.setting.ILabeled;
import com.lukflug.panelstudio.setting.IStringSetting;
import com.lukflug.panelstudio.setting.Labeled;
import com.lukflug.panelstudio.theme.IContainerRenderer;
import com.lukflug.panelstudio.theme.ThemeTuple;

/**
 * Radio list that can be searched via a search bar.
 * @author lukflug
 */
public abstract class SearchableRadioButton extends VerticalContainer {
	/**
	 * Constructor.
	 * @param setting the enum setting to be used
	 * @param theme the theme to be used
	 * @param container whether this is part of a layout container
	 * @param keys key predicates for the text box
	 */
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
			public void setValueIndex (int index) {
				setting.setValueIndex(index);
			}

			@Override
			public ILabeled[] getAllowedValues() {
				return values;
			}
		},theme.getRadioRenderer(container),getAnimation(),false) {
			@Override
			protected boolean isUpKey (int key) {
				return SearchableRadioButton.this.isUpKey(key);
			}

			@Override
			protected boolean isDownKey (int key) {
				return SearchableRadioButton.this.isDownKey(key);
			}
		};
		addComponent(content);
	}

	/**
	 * Returns the animation to be used.
	 * @return the animation to be used.
	 */
	protected abstract Animation getAnimation();
	
	/**
	 * Character filter.
	 * @param character the character to check
	 * @return whether this character is allowed
	 */
	public abstract boolean allowCharacter (char character);
	
	/**
	 * Scancode predicate for moving selection up.
	 * @param key key scancode
	 * @return whether this key is to be interpreted as up
	 */
	protected abstract boolean isUpKey (int key);

	/**
	 * Scancode predicate for moving selection down.
	 * @param key key scancode
	 * @return whether this key is to be interpreted as down
	 */
	protected abstract boolean isDownKey (int key);
}
