package com.lukflug.panelstudio.widget;

import com.lukflug.panelstudio.base.Context;
import com.lukflug.panelstudio.base.IToggleable;
import com.lukflug.panelstudio.component.FocusableComponent;
import com.lukflug.panelstudio.setting.IStringSetting;
import com.lukflug.panelstudio.theme.ITextFieldRenderer;

public abstract class TextField extends FocusableComponent {
	protected IStringSetting setting;
	private int position;
	protected int boxPosition=0;
	protected IToggleable insertMode;
	protected ITextFieldRenderer renderer;
	
	public TextField (IStringSetting setting, int position, IToggleable insertMode, ITextFieldRenderer renderer) {
		super(setting);
		this.setting=setting;
		this.position=position;
		this.insertMode=insertMode;
		this.renderer=renderer;
	}
	
	@Override
	public void render (Context context) {
		super.render(context);
		boxPosition=renderer.renderTextField(context,title,hasFocus(context),setting.getValue(),position,boxPosition,insertMode.isOn());
	}
	
	@Override
	public void handleKey (Context context, int scancode) {
		super.handleKey(context,scancode);
		if (isBackspaceKey(scancode) && getPosition()>0) {
			int pos=getPosition();
			setPosition(pos-1);
			String s=setting.getValue();
			setting.setValue(s.substring(0,pos-1)+s.substring(pos));
		} else if (isDeleteKey(scancode) && getPosition()>0) {
			int pos=getPosition();
			String s=setting.getValue();
			setting.setValue(s.substring(0,pos-1)+s.substring(pos));
		} else if (isInsertKey(scancode)) insertMode.toggle();
		else if (isLeftKey(scancode)) setPosition(getPosition()-1);
		else if (isRightKey(scancode)) setPosition(getPosition()+1);
		else if (isHomeKey(scancode)) setPosition(setting.getValue().length());
		else if (isEndKey(scancode)) setPosition(0);
	}
	
	@Override
	public void handleChar (Context context, char character) {
		super.handleChar(context,character);
		if (allowCharacter(character)) {
			String s=setting.getValue();
			int pos=getPosition();
			setPosition(getPosition()+1);
			if (insertMode.isOn() && pos<s.length()) setting.setValue(s.substring(0,pos)+character+s.substring(pos+1));
			else setting.setValue(s.substring(0,pos)+character+s.substring(pos));
		}
	}

	@Override
	protected int getHeight() {
		return renderer.getDefaultHeight();
	}
	
	protected int getPosition() {
		if (position<0) position=0;
		else if (position>setting.getValue().length()) position=setting.getValue().length();
		return position;
	}
	
	protected void setPosition (int position) {
		this.position=position;
	}
	
	public abstract boolean allowCharacter (char character);
	
	public abstract boolean isBackspaceKey (int scancode);
	
	public abstract boolean isDeleteKey (int scancode);
	
	public abstract boolean isInsertKey (int scancode);
	
	public abstract boolean isLeftKey (int scancode);
	
	public abstract boolean isRightKey (int scancode);
	
	public abstract boolean isHomeKey (int scancode);
	
	public abstract boolean isEndKey (int scancode);
}
