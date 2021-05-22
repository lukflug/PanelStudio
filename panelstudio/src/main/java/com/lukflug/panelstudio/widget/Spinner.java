package com.lukflug.panelstudio.widget;

import java.util.function.IntPredicate;

import com.lukflug.panelstudio.base.Context;
import com.lukflug.panelstudio.base.IInterface;
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
	public Spinner (INumberSetting setting, ThemeTuple theme, IntPredicate backspace, IntPredicate delete, IntPredicate insert, IntPredicate left, IntPredicate right, IntPredicate home, IntPredicate end) {
		super(setting,new IContainerRenderer(){});
		TextField textField=new TextField(new IStringSetting() {
			String value=null;
			long lastTime;
			
			@Override
			public String getDisplayName() {
				return setting.getDisplayName();
			}

			@Override
			public String getValue() {
				if (value!=null && System.currentTimeMillis()-lastTime>500) {
					if (value.isEmpty()) value="0";
					if (value.endsWith(".")) value+='0';
					double number=Double.parseDouble(value);
					if (number>setting.getMaximumValue()) number=setting.getMaximumValue();
					else if (number<setting.getMinimumValue()) number=setting.getMinimumValue();
					setting.setNumber(number);
					value=null;
				}
				if (value==null) return setting.getSettingState();
				else return value;
			}

			@Override
			public void setValue(String string) {
				if (value==null) lastTime=System.currentTimeMillis();
				value=new String(string);
			}
		},0,new SimpleToggleable(false),theme.getTextRenderer(true,false)) {
			@Override
			public boolean allowCharacter(char character) {
				return (character>='0' && character<='9') || (character=='.'&&!setting.getSettingState().contains("."));
			}

			@Override
			public boolean isBackspaceKey(int scancode) {
				return backspace.test(scancode);
			}

			@Override
			public boolean isDeleteKey(int scancode) {
				return delete.test(scancode);
			}

			@Override
			public boolean isInsertKey(int scancode) {
				return insert.test(scancode);
			}

			@Override
			public boolean isLeftKey(int scancode) {
				return left.test(scancode);
			}

			@Override
			public boolean isRightKey(int scancode) {
				return right.test(scancode);
			}

			@Override
			public boolean isHomeKey(int scancode) {
				return home.test(scancode);
			}

			@Override
			public boolean isEndKey(int scancode) {
				return end.test(scancode);
			}
		};
		addComponent(new HorizontalComponent<>(textField,0,1));
		VerticalContainer buttons=new VerticalContainer(setting,new IContainerRenderer(){});
		buttons.addComponent(new Button<Void>(new Labeled(null,null,()->true),()->null,theme.getSmallButtonRenderer(ITheme.UP,false)) {
			@Override
			public void handleButton (Context context, int button) {
				super.handleButton(context,button);
				if (button==IInterface.LBUTTON && context.isClicked()) {
					double number=setting.getNumber();
					number+=Math.pow(10,-setting.getPrecision());
					if (number<=setting.getMaximumValue()) setting.setNumber(number);
				}
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
				if (button==IInterface.LBUTTON && context.isClicked()) {
					double number=setting.getNumber();
					number-=Math.pow(10,-setting.getPrecision());
					if (number>=setting.getMinimumValue()) setting.setNumber(number);
				}
			}
			
			@Override
			public int getHeight() {
				return textField.getHeight()/2;
			}
		});
		addComponent(new HorizontalComponent<>(buttons,textField.getHeight(),0));
	}

}
