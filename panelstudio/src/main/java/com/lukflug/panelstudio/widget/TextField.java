package com.lukflug.panelstudio.widget;

import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import com.lukflug.panelstudio.base.Context;
import com.lukflug.panelstudio.base.IInterface;
import com.lukflug.panelstudio.base.IToggleable;
import com.lukflug.panelstudio.component.FocusableComponent;
import com.lukflug.panelstudio.setting.IStringSetting;
import com.lukflug.panelstudio.theme.ITextFieldRenderer;

public abstract class TextField extends FocusableComponent {
	protected IStringSetting setting;
	protected ITextFieldKeys keys;
	private int position,select=-1;
	protected int boxPosition=0;
	protected IToggleable insertMode;
	protected ITextFieldRenderer renderer;
	
	public TextField (IStringSetting setting, ITextFieldKeys keys, int position, IToggleable insertMode, ITextFieldRenderer renderer) {
		super(setting);
		this.setting=setting;
		this.keys=keys;
		this.position=position;
		this.insertMode=insertMode;
		this.renderer=renderer;
	}
	
	@Override
	public void render (Context context) {
		super.render(context);
		boxPosition=renderer.renderTextField(context,getTitle(),hasFocus(context),setting.getValue(),getPosition(),getSelect(),boxPosition,insertMode.isOn());
	}
	
	@Override
	public void handleButton (Context context, int button) {
		super.handleButton(context,button);
		if (button==IInterface.LBUTTON && context.isClicked(button)) {
			int pos=renderer.transformToCharPos(context,getTitle(),setting.getValue(),boxPosition);
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
			if (keys.isBackspaceKey(scancode) && (pos>0||sel>=0)) {
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
			} else if (keys.isDeleteKey(scancode) && (pos<setting.getValue().length()||sel>=0)) {
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
			} else if (keys.isInsertKey(scancode)) insertMode.toggle();
			else if (keys.isLeftKey(scancode)) {
				if (sel<0||context.getInterface().getModifier(IInterface.SHIFT)) setPosition(context.getInterface(),pos-1);
				else setPosition(context.getInterface(),Math.min(pos,sel));
			} else if (keys.isRightKey(scancode)) {
				if (sel<0||context.getInterface().getModifier(IInterface.SHIFT)) setPosition(context.getInterface(),getPosition()+1);
				else setPosition(context.getInterface(),Math.max(pos,sel));
			} else if (keys.isHomeKey(scancode)) setPosition(context.getInterface(),0);
			else if (keys.isEndKey(scancode)) setPosition(context.getInterface(),setting.getValue().length());
			else if (context.getInterface().getModifier(IInterface.CTRL) && keys.isCopyKey(scancode) && sel>=0) {
				StringSelection selection=new StringSelection(s.substring(Math.min(pos,sel),Math.max(pos,sel)));
				Toolkit.getDefaultToolkit().getSystemClipboard().setContents(selection,selection);
			} else if (context.getInterface().getModifier(IInterface.CTRL) && keys.isPasteKey(scancode)) {
				try {
					Transferable t=Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null);
					if (t.isDataFlavorSupported(DataFlavor.stringFlavor)) {
						String selection=(String)t.getTransferData(DataFlavor.stringFlavor);
						if (sel<0) {
							setting.setValue(s.substring(0,pos)+selection+s.substring(pos));
						} else {
							if (pos>sel) {
								int temp=sel;
								sel=pos;
								pos=temp;
								setPosition(context.getInterface(),pos);
							}
							setting.setValue(s.substring(0,pos)+selection+s.substring(sel));
						}
						position=pos+selection.length();
						select=pos;
					}
				} catch (IOException e) {
				} catch (UnsupportedFlavorException e) {
				}
			} else if (context.getInterface().getModifier(IInterface.CTRL) && keys.isCutKey(scancode) && sel>=0) {
				StringSelection selection=new StringSelection(s.substring(Math.min(pos,sel),Math.max(pos,sel)));
				Toolkit.getDefaultToolkit().getSystemClipboard().setContents(selection,selection);
				if (pos>sel) {
					int temp=sel;
					sel=pos;
					pos=temp;
					setPosition(context.getInterface(),pos);
				}
				setting.setValue(s.substring(0,pos)+s.substring(sel));
			} else if (context.getInterface().getModifier(IInterface.CTRL) && keys.isAllKey(scancode)) {
				select=0;
				position=s.length();
			}
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
			position=pos+1;
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
	
	protected int getSelect() {
		if (select>setting.getValue().length()) select=setting.getValue().length();
		if (select==getPosition()) select=-1;
		return select;
	}
	
	protected void unselect() {
		select=-1;
	}
	
	public abstract boolean allowCharacter (char character);
}
