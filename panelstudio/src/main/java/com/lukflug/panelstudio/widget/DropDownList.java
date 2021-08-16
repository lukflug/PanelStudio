package com.lukflug.panelstudio.widget;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

import com.lukflug.panelstudio.base.Animation;
import com.lukflug.panelstudio.base.Context;
import com.lukflug.panelstudio.base.IBoolean;
import com.lukflug.panelstudio.base.IInterface;
import com.lukflug.panelstudio.base.IToggleable;
import com.lukflug.panelstudio.base.SimpleToggleable;
import com.lukflug.panelstudio.component.HorizontalComponent;
import com.lukflug.panelstudio.component.IFixedComponent;
import com.lukflug.panelstudio.component.IScrollSize;
import com.lukflug.panelstudio.container.HorizontalContainer;
import com.lukflug.panelstudio.popup.IPopupPositioner;
import com.lukflug.panelstudio.setting.IEnumSetting;
import com.lukflug.panelstudio.setting.ILabeled;
import com.lukflug.panelstudio.setting.IStringSetting;
import com.lukflug.panelstudio.setting.Labeled;
import com.lukflug.panelstudio.theme.IContainerRenderer;
import com.lukflug.panelstudio.theme.ITheme;
import com.lukflug.panelstudio.theme.RendererTuple;
import com.lukflug.panelstudio.theme.ThemeTuple;

/**
 * Drop-down list widget.
 * @author lukflug
 */
public abstract class DropDownList extends HorizontalContainer {
	/**
	 * Cached input area.
	 */
	private Rectangle rect=new Rectangle();
	/**
	 * Whether focus has to be transfered to list pop-up.
	 */
	private boolean transferFocus=false;
	/**
	 * Toggle for whether the list pop-up is being displayed.
	 */
	protected IToggleable toggle=new SimpleToggleable(false);
	
	/**
	 * Constructor.
	 * @param setting the enum setting to be used
	 * @param theme the theme to be used
	 * @param container whether this is a title bar
	 * @param allowSearch whether typing in the text box is allowed
	 * @param keys key predicates for the text box
	 * @param popupSize the scroll behavior of the list
	 * @param popupAdder consumer to handle adding list pop-up
	 */
	public DropDownList (IEnumSetting setting, ThemeTuple theme, boolean container, boolean allowSearch, ITextFieldKeys keys, IScrollSize popupSize, Consumer<IFixedComponent> popupAdder) {
		super(setting,new IContainerRenderer(){});
		AtomicReference<String> searchTerm=new AtomicReference<String>(null);
		TextField textField=new TextField(new IStringSetting() {
			@Override
			public String getDisplayName() {
				return setting.getDisplayName();
			}

			@Override
			public String getValue() {
				String returnValue=(allowSearch&&toggle.isOn())?searchTerm.get():setting.getValueName();
				searchTerm.set(returnValue);
				return returnValue;
			}

			@Override
			public void setValue(String string) {
				searchTerm.set(string);
			}
		},keys,0,new SimpleToggleable(false),theme.getTextRenderer(true,container)) {
			@Override
			public void handleButton (Context context, int button) {
				super.handleButton(context,button);
				rect=renderer.getTextArea(context,getTitle());
				if (button==IInterface.LBUTTON && context.isClicked(button)) transferFocus=true;
			}
			
			@Override
			public boolean hasFocus (Context context) {
				return super.hasFocus(context)||toggle.isOn();
			}
			
			@Override
			public boolean allowCharacter(char character) {
				return DropDownList.this.allowCharacter(character);
			}
		};
		addComponent(new HorizontalComponent<>(textField,0,1));
		ThemeTuple popupTheme=new ThemeTuple(theme.theme,theme.logicalLevel,0);
		Button<Void> title=new Button<Void>(new Labeled("",null,()->false),()->null,popupTheme.getButtonRenderer(Void.class,false));
		RadioButton content=new RadioButton(new IEnumSetting() {
			ILabeled[] values=Arrays.stream(setting.getAllowedValues()).map(value->new Labeled(value.getDisplayName(),value.getDescription(),()->{
				if (!value.isVisible().isOn()) return false;
				else if (!allowSearch) return true;
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
		},popupTheme.getRadioRenderer(false),getAnimation(),false) {
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
		Button<Void> button=new Button<Void>(new Labeled(null,null,()->true),()->null,theme.getSmallButtonRenderer(ITheme.DOWN,container)) {
			@Override
			public void handleButton (Context context, int button) {
				super.handleButton(context,button);
				rect=new Rectangle(rect.x,context.getPos().y,context.getPos().x+context.getSize().width-rect.x,context.getSize().height);
				if ((button==IInterface.LBUTTON && context.isClicked(button)) || transferFocus) {
					context.getPopupDisplayer().displayPopup(popup,rect,toggle,positioner);
					transferFocus=false;
				}
			}
			
			@Override
			public int getHeight() {
				return textField.getHeight();
			}
		};
		addComponent(new HorizontalComponent<Button<Void>>(button,textField.getHeight(),0) {
			@Override
			public int getWidth(IInterface inter) {
				return textField.getHeight();
			}
		});
	}
	
	@Override
	public void handleKey (Context context, int scancode) {
		super.handleKey(context,scancode);
		if (toggle.isOn() && isEnterKey(scancode)) {
			toggle.toggle();
		}
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
	
	/**
	 * Scancode predicate for selecting selection.
	 * @param key key scancode
	 * @return whether this key is to be interpreted as select
	 */
	protected abstract boolean isEnterKey (int key);
}
