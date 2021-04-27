package com.lukflug.panelstudio.widget;

import com.lukflug.panelstudio.base.Context;
import com.lukflug.panelstudio.base.IInterface;
import com.lukflug.panelstudio.base.IToggleable;
import com.lukflug.panelstudio.component.FocusableComponent;
import com.lukflug.panelstudio.setting.IStringSetting;
import com.lukflug.panelstudio.theme.ITextFieldRenderer;

public abstract class TextField extends FocusableComponent {
	protected IStringSetting setting;
	private int position,select=-1;
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
		boxPosition=renderer.renderTextField(context,title,hasFocus(context),setting.getValue(),getPosition(),getSelect(),boxPosition,insertMode.isOn());
	}
	
	@Override
	public void handleButton (Context context, int button) {
		super.handleButton(context,button);
		if (context.isClicked()) {
			int pos=renderer.transformToCharPos(context,setting.getValue(),boxPosition);
			if (pos>=0) setPosition(context.getInterface(),pos);
			unselect();
		}
		if (!hasFocus(context)) unselect();
	}
	
	@Override
	public void handleKey (Context context, int scancode) {
		super.handleKey(context,scancode);
		if (hasFocus(context)) {
			int pos=getPosition();
			int sel=getSelect();
			String s=setting.getValue();
			if (isBackspaceKey(scancode) && (getPosition()>0||sel>=0)) {
				if (sel<0) {
					setPosition(context.getInterface(),pos-1);
					setting.setValue(s.substring(0,pos-1)+s.substring(pos));
				} else {
					if (pos>sel) {
						int temp=sel;
						sel=pos;
						pos=temp;
						setPosition(context.getInterface(),pos);
					}
					setting.setValue(s.substring(0,pos)+s.substring(sel));
				}
				unselect();
			} else if (isDeleteKey(scancode) && (getPosition()<setting.getValue().length()||sel>=0)) {
				if (sel<0) {
					setting.setValue(s.substring(0,pos)+s.substring(pos+1));
				} else {
					if (pos>sel) {
						int temp=sel;
						sel=pos;
						pos=temp;
						setPosition(context.getInterface(),pos);
					}
					setting.setValue(s.substring(0,pos)+s.substring(sel));
				}
				unselect();
			} else if (isInsertKey(scancode)) insertMode.toggle();
			else if (isLeftKey(scancode)) setPosition(context.getInterface(),getPosition()-1);
			else if (isRightKey(scancode)) setPosition(context.getInterface(),getPosition()+1);
			else if (isHomeKey(scancode)) setPosition(context.getInterface(),0);
			else if (isEndKey(scancode)) setPosition(context.getInterface(),setting.getValue().length());
		}
	}
	
	@Override
	public void handleChar (Context context, char character) {
		super.handleChar(context,character);
		if (hasFocus(context) && allowCharacter(character)) {
			int pos=getPosition();
			int sel=getSelect();
			String s=setting.getValue();
			if (sel<0) {
				if (insertMode.isOn() && pos<s.length()) setting.setValue(s.substring(0,pos)+character+s.substring(pos+1));
				else setting.setValue(s.substring(0,pos)+character+s.substring(pos));
			} else {
				if (pos>sel) {
					int temp=sel;
					sel=pos;
					pos=temp;
				}
				setting.setValue(s.substring(0,pos)+character+s.substring(sel));
				unselect();
			}
			setPositionNoSelect(pos+1);
		}
	}
	
	@Override
	public void releaseFocus() {
		super.releaseFocus();
		unselect();
	}
	
	@Override
	public void exit() {
		super.exit();
		unselect();
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
	
	protected void setPosition (IInterface inter, int position) {
		if (inter.getModifier(IInterface.SHIFT)) {
			if (select<0) select=this.position;
		} else select=-1;
		this.position=position;
	}
	
	protected void setPositionNoSelect (int position) {
		this.position=position;
	}
	
	protected int getSelect() {
		if (select>setting.getValue().length()) select=setting.getValue().length();
		if (select==getPosition()) select=-1;
		return select;
	}
	
	protected void unselect() {
		select=-1;
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
