package com.lukflug.panelstudio.widget;

import com.lukflug.panelstudio.base.Context;
import com.lukflug.panelstudio.base.SimpleToggleable;
import com.lukflug.panelstudio.component.HorizontalComponent;
import com.lukflug.panelstudio.container.HorizontalContainer;
import com.lukflug.panelstudio.container.VerticalContainer;
import com.lukflug.panelstudio.setting.INumberSetting;
import com.lukflug.panelstudio.setting.IStringSetting;
import com.lukflug.panelstudio.setting.Labeled;
import com.lukflug.panelstudio.theme.IContainerRenderer;
import com.lukflug.panelstudio.theme.ITheme;
import com.lukflug.panelstudio.theme.ThemeTuple;

public class Spinner extends HorizontalContainer {
	public Spinner (INumberSetting setting, ThemeTuple theme) {
		super(setting,new IContainerRenderer(){});
		TextField textField=new TextField(new IStringSetting() {
			@Override
			public String getDisplayName() {
				return null;
			}

			@Override
			public String getValue() {
				return setting.getSettingState();
			}

			@Override
			public void setValue(String string) {
			}
		},0,new SimpleToggleable(false),theme.getTextRenderer(false)) {
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
		};
		addComponent(new HorizontalComponent<>(textField,0,1));
		VerticalContainer buttons=new VerticalContainer(setting,new IContainerRenderer(){});
		buttons.addComponent(new Button<Void>(new Labeled(null,null,()->true),()->null,theme.getSmallButtonRenderer(ITheme.UP,false)) {
			@Override
			public void handleButton (Context context, int button) {
				super.handleButton(context,button);
				double number=setting.getNumber();
				number+=Math.pow(10,-setting.getPrecision());
				if (number<=setting.getMaximumValue()) setting.setNumber(number);
			}
			
			@Override
			public int getHeight() {
				return textField.getHeight()/2;
			}
		});
		buttons.addComponent(new Button<Void>(new Labeled(null,null,()->true),()->null,theme.getSmallButtonRenderer(ITheme.DOWN,false)) {
			@Override
			public void handleButton (Context context, int button) {
				super.handleButton(context,button);
				double number=setting.getNumber();
				number-=Math.pow(10,-setting.getPrecision());
				if (number>=setting.getMinimumValue()) setting.setNumber(number);
			}
			
			@Override
			public int getHeight() {
				return textField.getHeight()/2;
			}
		});
		addComponent(new HorizontalComponent<>(buttons,textField.getHeight(),0));
	}

}
