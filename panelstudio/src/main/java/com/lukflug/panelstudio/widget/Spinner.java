package com.lukflug.panelstudio.widget;

import com.lukflug.panelstudio.base.SimpleToggleable;
import com.lukflug.panelstudio.component.HorizontalComponent;
import com.lukflug.panelstudio.container.HorizontalContainer;
import com.lukflug.panelstudio.setting.INumberSetting;
import com.lukflug.panelstudio.setting.IStringSetting;
import com.lukflug.panelstudio.theme.ThemeTuple;

public class Spinner extends HorizontalContainer {
	public Spinner(INumberSetting setting, ThemeTuple theme) {
		super(setting,theme.getContainerRenderer(true));
		new IStringSetting() {
			@Override
			public String getDisplayName() {
				return null;
			}

			@Override
			public String getValue() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public void setValue(String string) {
				// TODO Auto-generated method stub
				
			}
		};
		this.addComponent(new HorizontalComponent<TextField>(new TextField(null,0,new SimpleToggleable(false),theme.getTextRenderer(false)) {
			@Override
			public boolean allowCharacter(char character) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean isBackspaceKey(int scancode) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean isDeleteKey(int scancode) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean isInsertKey(int scancode) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean isLeftKey(int scancode) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean isRightKey(int scancode) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean isHomeKey(int scancode) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean isEndKey(int scancode) {
				// TODO Auto-generated method stub
				return false;
			}
		},0,1));
	}

}
