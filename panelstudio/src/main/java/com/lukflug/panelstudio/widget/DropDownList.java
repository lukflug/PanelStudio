package com.lukflug.panelstudio.widget;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.function.Consumer;

import com.lukflug.panelstudio.base.Animation;
import com.lukflug.panelstudio.base.Context;
import com.lukflug.panelstudio.base.IInterface;
import com.lukflug.panelstudio.base.IToggleable;
import com.lukflug.panelstudio.base.SimpleToggleable;
import com.lukflug.panelstudio.component.HorizontalComponent;
import com.lukflug.panelstudio.component.IFixedComponent;
import com.lukflug.panelstudio.component.IScrollSize;
import com.lukflug.panelstudio.container.HorizontalContainer;
import com.lukflug.panelstudio.popup.IPopupPositioner;
import com.lukflug.panelstudio.setting.IEnumSetting;
import com.lukflug.panelstudio.setting.IStringSetting;
import com.lukflug.panelstudio.setting.Labeled;
import com.lukflug.panelstudio.theme.IContainerRenderer;
import com.lukflug.panelstudio.theme.ITheme;
import com.lukflug.panelstudio.theme.RendererTuple;
import com.lukflug.panelstudio.theme.ThemeTuple;

public abstract class DropDownList extends HorizontalContainer {
	protected Rectangle rect;
	
	public DropDownList (IEnumSetting setting, ThemeTuple theme, ITextFieldKeys keys, IScrollSize popupSize, Consumer<IFixedComponent> popupAdder) {
		super(setting,new IContainerRenderer(){});
		TextField textField=new TextField(new IStringSetting() {
			/*String value=null;
			long lastTime;*/
			
			@Override
			public String getDisplayName() {
				return setting.getDisplayName();
			}

			@Override
			public String getValue() {
				/*if (value!=null && System.currentTimeMillis()-lastTime>500) {
					if (value.isEmpty()) value="0";
					if (value.endsWith(".")) value+='0';
					double number=Double.parseDouble(value);
					if (number>setting.getMaximumValue()) number=setting.getMaximumValue();
					else if (number<setting.getMinimumValue()) number=setting.getMinimumValue();
					setting.setNumber(number);
					value=null;
				}
				if (value==null) return setting.getSettingState();
				else return value;*/
				return setting.getValueName();
			}

			@Override
			public void setValue(String string) {
				/*if (value==null) lastTime=System.currentTimeMillis();
				value=new String(string);*/
			}
		},keys,0,new SimpleToggleable(false),theme.getTextRenderer(true,false)) {
			@Override
			public boolean allowCharacter(char character) {
				return DropDownList.this.allowCharacter(character);
			}
		};
		addComponent(new HorizontalComponent<>(textField,0,1));
		ThemeTuple popupTheme=new ThemeTuple(theme.theme,theme.logicalLevel,0);
		IToggleable toggle=new SimpleToggleable(false);
		Button<Void> title=new Button<Void>(new Labeled("",null,()->false),()->null,popupTheme.getButtonRenderer(Void.class,false));
		RadioButton content=new RadioButton(setting,popupTheme.getRadioRenderer(false),getAnimation(),false) {
			@Override
			protected boolean isUpKey(int key) {
				return DropDownList.this.isUpKey(key);
			}

			@Override
			protected boolean isDownKey(int key) {
				return DropDownList.this.isDownKey(key);
			}
		};
		IFixedComponent popup=ClosableComponent.createStaticPopup(title,content,()->null,getAnimation(),new RendererTuple<Void>(Void.class,popupTheme),popupSize,toggle,()->rect.width,false,"",true);
		popupAdder.accept(popup);
		IPopupPositioner positioner=new IPopupPositioner() {
			@Override
			public Point getPosition (IInterface inter, Dimension popup, Rectangle component, Rectangle panel) {
				return new Point(component.x,component.y+component.height);
			}
		};
		Button<Void> button=new Button<Void>(new Labeled(null,null,()->true),()->null,theme.getSmallButtonRenderer(ITheme.DOWN,false)) {
			@Override
			public void handleButton (Context context, int button) {
				super.handleButton(context,button);
				if (button==IInterface.LBUTTON && context.isClicked(button)) {
					context.getPopupDisplayer().displayPopup(popup,rect,toggle,positioner);
				}
			}
			
			@Override
			public int getHeight() {
				return textField.getHeight();
			}
		};
		addComponent(new HorizontalComponent<>(button,textField.getHeight(),0));
	}
	
	@Override
	public void handleButton (Context context, int button) {
		getHeight(context);
		rect=context.getRect();
		super.handleButton(context,button);
	}
	
	protected abstract Animation getAnimation();
	
	public abstract boolean allowCharacter(char character);
	
	protected abstract boolean isUpKey(int key);

	protected abstract boolean isDownKey(int key);
}
