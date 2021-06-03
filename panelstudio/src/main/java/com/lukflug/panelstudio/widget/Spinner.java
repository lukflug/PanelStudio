package com.lukflug.panelstudio.widget;

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
	public Spinner (INumberSetting setting, ThemeTuple theme, boolean container, boolean allowInput, ITextFieldKeys keys) {
		super(setting,new IContainerRenderer(){});
		TextField textField=new TextField(new IStringSetting() {
			private String value=null;
			private long lastTime;
			
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
		},keys,0,new SimpleToggleable(false),theme.getTextRenderer(true,container)) {
			@Override
			public boolean allowCharacter(char character) {
				if (!allowInput) return false;
				return (character>='0' && character<='9') || (character=='.'&&!setting.getSettingState().contains("."));
			}
		};
		addComponent(new HorizontalComponent<>(textField,0,1));
		VerticalContainer buttons=new VerticalContainer(setting,new IContainerRenderer(){});
		buttons.addComponent(new Button<Void>(new Labeled(null,null,()->true),()->null,theme.getSmallButtonRenderer(ITheme.UP,container)) {
			@Override
			public void handleButton (Context context, int button) {
				super.handleButton(context,button);
				if (button==IInterface.LBUTTON && context.isClicked(button)) {
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
		buttons.addComponent(new Button<Void>(new Labeled(null,null,()->true),()->null,theme.getSmallButtonRenderer(ITheme.DOWN,container)) {
			@Override
			public void handleButton (Context context, int button) {
				super.handleButton(context,button);
				if (button==IInterface.LBUTTON && context.isClicked(button)) {
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
