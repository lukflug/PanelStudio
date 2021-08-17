package com.lukflug.examplemod8forge;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.IntPredicate;
import java.util.function.IntSupplier;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

import org.lwjgl.input.Keyboard;

import com.lukflug.examplemod8forge.module.Category;
import com.lukflug.examplemod8forge.module.ClickGUIModule;
import com.lukflug.examplemod8forge.module.ClickGUIModule.Theme;
import com.lukflug.examplemod8forge.module.HUDEditorModule;
import com.lukflug.examplemod8forge.module.LogoModule;
import com.lukflug.examplemod8forge.module.TabGUIModule;
import com.lukflug.examplemod8forge.module.WatermarkModule;
import com.lukflug.examplemod8forge.setting.BooleanSetting;
import com.lukflug.examplemod8forge.setting.ColorSetting;
import com.lukflug.examplemod8forge.setting.IntegerSetting;
import com.lukflug.examplemod8forge.setting.Setting;
import com.lukflug.panelstudio.base.Animation;
import com.lukflug.panelstudio.base.ConstantToggleable;
import com.lukflug.panelstudio.base.Context;
import com.lukflug.panelstudio.base.IBoolean;
import com.lukflug.panelstudio.base.IInterface;
import com.lukflug.panelstudio.base.IToggleable;
import com.lukflug.panelstudio.base.SettingsAnimation;
import com.lukflug.panelstudio.base.SimpleToggleable;
import com.lukflug.panelstudio.component.IComponent;
import com.lukflug.panelstudio.component.IFixedComponent;
import com.lukflug.panelstudio.component.IResizable;
import com.lukflug.panelstudio.component.IScrollSize;
import com.lukflug.panelstudio.container.IContainer;
import com.lukflug.panelstudio.hud.HUDGUI;
import com.lukflug.panelstudio.layout.CSGOLayout;
import com.lukflug.panelstudio.layout.ChildUtil.ChildMode;
import com.lukflug.panelstudio.layout.ComponentGenerator;
import com.lukflug.panelstudio.layout.IComponentAdder;
import com.lukflug.panelstudio.layout.IComponentGenerator;
import com.lukflug.panelstudio.layout.ILayout;
import com.lukflug.panelstudio.layout.PanelAdder;
import com.lukflug.panelstudio.layout.PanelLayout;
import com.lukflug.panelstudio.layout.SearchableLayout;
import com.lukflug.panelstudio.layout.SinglePanelAdder;
import com.lukflug.panelstudio.layout.StackedPanelAdder;
import com.lukflug.panelstudio.mc8forge.MinecraftHUDGUI;
import com.lukflug.panelstudio.popup.CenteredPositioner;
import com.lukflug.panelstudio.popup.IPopupPositioner;
import com.lukflug.panelstudio.popup.MousePositioner;
import com.lukflug.panelstudio.popup.PanelPositioner;
import com.lukflug.panelstudio.popup.PopupTuple;
import com.lukflug.panelstudio.setting.IBooleanSetting;
import com.lukflug.panelstudio.setting.IClient;
import com.lukflug.panelstudio.setting.IColorSetting;
import com.lukflug.panelstudio.setting.IEnumSetting;
import com.lukflug.panelstudio.setting.IModule;
import com.lukflug.panelstudio.setting.INumberSetting;
import com.lukflug.panelstudio.setting.Labeled;
import com.lukflug.panelstudio.theme.ClearTheme;
import com.lukflug.panelstudio.theme.GameSenseTheme;
import com.lukflug.panelstudio.theme.IColorScheme;
import com.lukflug.panelstudio.theme.ITheme;
import com.lukflug.panelstudio.theme.IThemeMultiplexer;
import com.lukflug.panelstudio.theme.ImpactTheme;
import com.lukflug.panelstudio.theme.OptimizedTheme;
import com.lukflug.panelstudio.theme.RainbowTheme;
import com.lukflug.panelstudio.theme.ThemeTuple;
import com.lukflug.panelstudio.theme.Windows31Theme;
import com.lukflug.panelstudio.widget.ColorPickerComponent;
import com.lukflug.panelstudio.widget.CycleSwitch;
import com.lukflug.panelstudio.widget.DropDownList;
import com.lukflug.panelstudio.widget.ITextFieldKeys;
import com.lukflug.panelstudio.widget.Spinner;
import com.lukflug.panelstudio.widget.ToggleSwitch;

import net.minecraft.util.EnumChatFormatting;

public class ClickGUI extends MinecraftHUDGUI {
	private final GUIInterface inter;
	private final HUDGUI gui;
	public static final int WIDTH=120,HEIGHT=12,DISTANCE=6,BORDER=2;
	
	public ClickGUI() {
		// Getting client structure ...
		IClient client=Category.getClient();
		/* Set to false to disable horizontal clipping, this may cause graphical glitches,
		 * but will let you see long text, even if it is too long to fit in the panel. */
		inter=new GUIInterface(true) {
			@Override
			protected String getResourcePrefix() {
				return "examplemod:";
			}
		};
		// Instantiating theme ...
		ITheme theme=new OptimizedTheme(new ThemeSelector(inter));
		// Instantiating GUI ...
		final IToggleable guiToggle=new SimpleToggleable(false);
		IToggleable hudToggle=new SimpleToggleable(false) {
			@Override
			public boolean isOn() {
				return guiToggle.isOn()?HUDEditorModule.showHUD.isOn():super.isOn();
			}
		};
		gui=new HUDGUI(inter,theme.getDescriptionRenderer(),(IPopupPositioner)new MousePositioner(new Point(10,10)),guiToggle,hudToggle);
		// Creating animation ...
		final Supplier<Animation> animation=new Supplier<Animation>() {
			@Override
			public Animation get() {
				return new SettingsAnimation(new Supplier<Integer>() {
					@Override
					public Integer get() {
						return ClickGUIModule.animationSpeed.getValue();
					}
				},new Supplier<Long>() {
					@Override
					public Long get() {
						return inter.getTime();
					}
				});
			}
		};
		// Populating HUD ...
		gui.addHUDComponent(TabGUIModule.getComponent(client,new IContainer<IFixedComponent>() {
			@Override
			public boolean addComponent (IFixedComponent component) {
				return gui.addHUDComponent(component,new ConstantToggleable(true));
			}

			@Override
			public boolean addComponent (IFixedComponent component, IBoolean visible) {
				return gui.addHUDComponent(component,visible);
			}

			@Override
			public boolean removeComponent (IFixedComponent component) {
				return gui.removeComponent(component);
			}
		},animation),TabGUIModule.getToggle(),animation.get(),theme,BORDER);
		gui.addHUDComponent(WatermarkModule.getComponent(),WatermarkModule.getToggle(),animation.get(),theme,BORDER);
		gui.addHUDComponent(LogoModule.getComponent(inter),LogoModule.getToggle(),animation.get(),theme,BORDER);
		
		// Creating popup types ...
		final BiFunction<Context,Integer,Integer> scrollHeight=new BiFunction<Context,Integer,Integer>() {
			@Override
			public Integer apply (Context t, Integer u) {
				return Math.min(u,Math.max(HEIGHT*4,ClickGUI.this.height-t.getPos().y-HEIGHT));
			}
		};
		PopupTuple popupType=new PopupTuple(new PanelPositioner(new Point(0,0)),false,new IScrollSize() {
			@Override
			public int getScrollHeight (Context context, int componentHeight) {
				return scrollHeight.apply(context,componentHeight);
			}
		});
		PopupTuple colorPopup=new PopupTuple(new CenteredPositioner(new Supplier<Rectangle>() {
			@Override
			public Rectangle get() {
				return new Rectangle(new Point(0,0),inter.getWindowSize());
			}
		}),true,new IScrollSize() {
			@Override
			public int getScrollHeight (Context context, int componentHeight) {
				return scrollHeight.apply(context,componentHeight);
			}
		});
		// Defining resize behavior ...
		final IntFunction<IResizable> resizable=new IntFunction<IResizable>() {
			@Override
			public IResizable apply (final int value) {
				return new IResizable() {
					Dimension size=new Dimension(value,320);
					
					@Override
					public Dimension getSize() {
						return new Dimension(size);
					}

					@Override
					public void setSize (Dimension size) {
						this.size.width=size.width;
						this.size.height=size.height;
						if (size.width<75) this.size.width=75;
						if (size.height<50) this.size.height=50;
					}
				};
			}
		};
		// Defining scroll behavior ...
		final Function<IResizable,IScrollSize> resizableHeight=new Function<IResizable,IScrollSize>() {
			@Override
			public IScrollSize apply (final IResizable t) {
				return new IScrollSize() {
					@Override
					public int getScrollHeight (Context context, int componentHeight) {
						return t.getSize().height;
					}
				};
			}
		};
		// Defining function keys ...
		IntPredicate keybindKey=new IntPredicate() {
			@Override
			public boolean test (int value) {
				return value==Keyboard.KEY_DELETE;
			}
		};
		IntPredicate charFilter=new IntPredicate() {
			@Override
			public boolean test (int value) {
				return value>=' ';
			}
		};
		ITextFieldKeys keys=new ITextFieldKeys() {
			@Override
			public boolean isBackspaceKey (int scancode) {
				return scancode==Keyboard.KEY_BACK;
			}

			@Override
			public boolean isDeleteKey (int scancode) {
				return scancode==Keyboard.KEY_DELETE;
			}

			@Override
			public boolean isInsertKey (int scancode) {
				return scancode==Keyboard.KEY_INSERT;
			}

			@Override
			public boolean isLeftKey (int scancode) {
				return scancode==Keyboard.KEY_LEFT;
			}

			@Override
			public boolean isRightKey (int scancode) {
				return scancode==Keyboard.KEY_RIGHT;
			}

			@Override
			public boolean isHomeKey (int scancode) {
				return scancode==Keyboard.KEY_HOME;
			}

			@Override
			public boolean isEndKey (int scancode) {
				return scancode==Keyboard.KEY_END;
			}

			@Override
			public boolean isCopyKey (int scancode) {
				return scancode==Keyboard.KEY_C;
			}

			@Override
			public boolean isPasteKey (int scancode) {
				return scancode==Keyboard.KEY_V;
			}

			@Override
			public boolean isCutKey (int scancode) {
				return scancode==Keyboard.KEY_X;
			}

			@Override
			public boolean isAllKey (int scancode) {
				return scancode==Keyboard.KEY_A;
			}			
		};
		
		// Normal generator
		IComponentGenerator generator=new ComponentGenerator(keybindKey,charFilter,keys);
		// Use cycle switches instead of buttons
		IComponentGenerator cycleGenerator=new ComponentGenerator(keybindKey,charFilter,keys) {
			@Override
			public IComponent getEnumComponent (IEnumSetting setting, Supplier<Animation> animation, IComponentAdder adder, ThemeTuple theme, int colorLevel, boolean isContainer) {
				return new CycleSwitch(setting,theme.getCycleSwitchRenderer(isContainer));
			}
		};
		// Use all the fancy widgets with text boxes
		IComponentGenerator csgoGenerator=new ComponentGenerator(keybindKey,charFilter,keys) {
			@Override
			public IComponent getBooleanComponent (IBooleanSetting setting, Supplier<Animation> animation, IComponentAdder adder, ThemeTuple theme, int colorLevel, boolean isContainer) {
				return new ToggleSwitch(setting,theme.getToggleSwitchRenderer(isContainer));
			}
			
			@Override
			public IComponent getEnumComponent (IEnumSetting setting, final Supplier<Animation> animation, final IComponentAdder adder, ThemeTuple theme, int colorLevel, boolean isContainer) {
				return new DropDownList(setting,theme,isContainer,false,keys,new IScrollSize(){},new Consumer<IFixedComponent>() {
					@Override
					public void accept (IFixedComponent t) {
						adder.addPopup(t);
					}
				}) {
					@Override
					protected Animation getAnimation() {
						return animation.get();
					}

					@Override
					public boolean allowCharacter (char character) {
						return charFilter.test(character);
					}

					@Override
					protected boolean isUpKey (int key) {
						return key==Keyboard.KEY_UP;
					}

					@Override
					protected boolean isDownKey (int key) {
						return key==Keyboard.KEY_DOWN;
					}

					@Override
					protected boolean isEnterKey (int key) {
						return key==Keyboard.KEY_RETURN;
					}
				};
			}
			
			@Override
			public IComponent getNumberComponent (INumberSetting setting, Supplier<Animation> animation, IComponentAdder adder, ThemeTuple theme, int colorLevel, boolean isContainer) {
				return new Spinner(setting,theme,isContainer,true,keys);
			}
			
			@Override
			public IComponent getColorComponent (IColorSetting setting, Supplier<Animation> animation, IComponentAdder adder, ThemeTuple theme, int colorLevel, boolean isContainer) {
				return new ColorPickerComponent(setting,new ThemeTuple(theme.theme,theme.logicalLevel,colorLevel));
			}
		};
		
		// Classic Panel
		IComponentAdder classicPanelAdder=new PanelAdder(gui,false,new IBoolean() {
			@Override
			public boolean isOn() {
				return ClickGUIModule.layout.getValue()==ClickGUIModule.Layout.ClassicPanel;
			}
		},new UnaryOperator<String>() {
			@Override
			public String apply (String t) {
				return "classicPanel_"+t;
			}
		}) {
			@Override
			protected IResizable getResizable (int width) {
				return resizable.apply(width);
			}
			
			@Override
			protected IScrollSize getScrollSize (IResizable size) {
				return resizableHeight.apply(size);
			}
		};
		ILayout classicPanelLayout=new PanelLayout(WIDTH,new Point(DISTANCE,DISTANCE),(WIDTH+DISTANCE)/2,HEIGHT+DISTANCE,animation,new IntFunction<ChildMode>() {
			@Override
			public ChildMode apply (int level) {
				return ChildMode.DOWN;
			}
		},new IntFunction<ChildMode>() {
			@Override
			public ChildMode apply (int level) {
				return ChildMode.DOWN;
			}
		},popupType);
		classicPanelLayout.populateGUI(classicPanelAdder,generator,client,theme);
		// Pop-up Panel
		IComponentAdder popupPanelAdder=new PanelAdder(gui,false,new IBoolean() {
			@Override
			public boolean isOn() {
				return ClickGUIModule.layout.getValue()==ClickGUIModule.Layout.PopupPanel;
			}
		},new UnaryOperator<String>() {
			@Override
			public String apply (String t) {
				return "popupPanel_"+t;
			}
		}) {
			@Override
			protected IResizable getResizable (int width) {
				return resizable.apply(width);
			}
			
			@Override
			protected IScrollSize getScrollSize (IResizable size) {
				return resizableHeight.apply(size);
			}
		};
		ILayout popupPanelLayout=new PanelLayout(WIDTH,new Point(DISTANCE,DISTANCE),(WIDTH+DISTANCE)/2,HEIGHT+DISTANCE,animation,new IntFunction<ChildMode>() {
			@Override
			public ChildMode apply (int level) {
				return ChildMode.POPUP;
			}
		},new IntFunction<ChildMode>() {
			@Override
			public ChildMode apply (int level) {
				return ChildMode.DOWN;
			}
		},popupType);
		popupPanelLayout.populateGUI(popupPanelAdder,generator,client,theme);
		// Draggable Panel
		IComponentAdder draggablePanelAdder=new PanelAdder(gui,false,new IBoolean() {
			@Override
			public boolean isOn() {
				return ClickGUIModule.layout.getValue()==ClickGUIModule.Layout.DraggablePanel;
			}
		},new UnaryOperator<String>() {
			@Override
			public String apply (String t) {
				return "drggablePanel_"+t;
			}
		}) {
			@Override
			protected IResizable getResizable (int width) {
				return resizable.apply(width);
			}
			
			@Override
			protected IScrollSize getScrollSize (IResizable size) {
				return resizableHeight.apply(size);
			}
		};
		ILayout draggablePanelLayout=new PanelLayout(WIDTH,new Point(DISTANCE,DISTANCE),(WIDTH+DISTANCE)/2,HEIGHT+DISTANCE,animation,new IntFunction<ChildMode>() {
			@Override
			public ChildMode apply (int level) {
				return level==0?ChildMode.DRAG_POPUP:ChildMode.DOWN;
			}
		},new IntFunction<ChildMode>() {
			@Override
			public ChildMode apply (int level) {
				return ChildMode.DOWN;
			}
		},popupType);
		draggablePanelLayout.populateGUI(draggablePanelAdder,generator,client,theme);
		// Single Panel
		IComponentAdder singlePanelAdder=new SinglePanelAdder(gui,new Labeled("Example Menu",null,new ConstantToggleable(true)),theme,new Point(10,10),WIDTH*Category.values().length,animation,new IBoolean() {
			@Override
			public boolean isOn() {
				return ClickGUIModule.layout.getValue()==ClickGUIModule.Layout.SinglePanel;
			}
		},"singlePanel") {
			@Override
			protected IResizable getResizable (int width) {
				return resizable.apply(width);
			}
			
			@Override
			protected IScrollSize getScrollSize (IResizable size) {
				return resizableHeight.apply(size);
			}
		};
		ILayout singlePanelLayout=new PanelLayout(WIDTH,new Point(DISTANCE,DISTANCE),(WIDTH+DISTANCE)/2,HEIGHT+DISTANCE,animation,new IntFunction<ChildMode>() {
			@Override
			public ChildMode apply (int level) {
				return ChildMode.DOWN;
			}
		},new IntFunction<ChildMode>() {
			@Override
			public ChildMode apply (int level) {
				return ChildMode.DOWN;
			}
		},popupType);
		singlePanelLayout.populateGUI(singlePanelAdder,generator,client,theme);
		// Panel Menu
		IComponentAdder panelMenuAdder=new StackedPanelAdder(gui,new Labeled("Example Menu",null,new ConstantToggleable(true)),theme,new Point(10,10),WIDTH,animation,ChildMode.POPUP,new PanelPositioner(new Point(0,0)),new IBoolean() {
			@Override
			public boolean isOn() {
				return ClickGUIModule.layout.getValue()==ClickGUIModule.Layout.PanelMenu;
			}
		},"panelMenu");
		ILayout panelMenuLayout=new PanelLayout(WIDTH,new Point(DISTANCE,DISTANCE),(WIDTH+DISTANCE)/2,HEIGHT+DISTANCE,animation,new IntFunction<ChildMode>() {
			@Override
			public ChildMode apply (int level) {
				return ChildMode.POPUP;
			}
		},new IntFunction<ChildMode>() {
			@Override
			public ChildMode apply (int level) {
				return ChildMode.POPUP;
			}
		},popupType);
		panelMenuLayout.populateGUI(panelMenuAdder,generator,client,theme);
		// Color Panel
		IComponentAdder colorPanelAdder=new PanelAdder(gui,false,new IBoolean() {
			@Override
			public boolean isOn() {
				return ClickGUIModule.layout.getValue()==ClickGUIModule.Layout.ColorPanel;
			}
		},new UnaryOperator<String>() {
			@Override
			public String apply (String title) {
				return "colorPanel_"+title;
			}
		}) {
			@Override
			protected IResizable getResizable (int width) {
				return resizable.apply(width);
			}
			
			@Override
			protected IScrollSize getScrollSize (IResizable size) {
				return resizableHeight.apply(size);
			}
		};
		ILayout colorPanelLayout=new PanelLayout(WIDTH,new Point(DISTANCE,DISTANCE),(WIDTH+DISTANCE)/2,HEIGHT+DISTANCE,animation,new IntFunction<ChildMode>() {
			@Override
			public ChildMode apply (int level) {
				return ChildMode.DOWN;
			}
		},new IntFunction<ChildMode>() {
			@Override
			public ChildMode apply (int level) {
				return ChildMode.POPUP;
			}
		},colorPopup);
		colorPanelLayout.populateGUI(colorPanelAdder,cycleGenerator,client,theme);
		// Horizontal CSGO
		final AtomicReference<IResizable> horizontalResizable=new AtomicReference<IResizable>(null);
		IComponentAdder horizontalCSGOAdder=new PanelAdder(gui,true,new IBoolean() {
			@Override
			public boolean isOn() {
				return ClickGUIModule.layout.getValue()==ClickGUIModule.Layout.CSGOHorizontal;
			}
		},new UnaryOperator<String>() {
			@Override
			public String apply (String title) {
				return "horizontalCSGO_"+title;
			}
		}) {
			@Override
			protected IResizable getResizable (int width) {
				horizontalResizable.set(resizable.apply(width));
				return horizontalResizable.get();
			}
		};
		ILayout horizontalCSGOLayout=new CSGOLayout(new Labeled("Example",null,new ConstantToggleable(true)),new Point(100,100),480,WIDTH,animation,"Enabled",true,true,2,ChildMode.POPUP,colorPopup) {
			@Override
			public int getScrollHeight (Context context, int componentHeight) {
				return resizableHeight.apply(horizontalResizable.get()).getScrollHeight(null,height);
			}
		};
		horizontalCSGOLayout.populateGUI(horizontalCSGOAdder,csgoGenerator,client,theme);
		// Vertical CSGO
		final AtomicReference<IResizable> verticalResizable=new AtomicReference<IResizable>(null);
		IComponentAdder verticalCSGOAdder=new PanelAdder(gui,true,new IBoolean() {
			@Override
			public boolean isOn() {
				return ClickGUIModule.layout.getValue()==ClickGUIModule.Layout.CSGOVertical;
			}
		},new UnaryOperator<String>() {
			@Override
			public String apply (String title) {
				return "verticalCSGO_"+title;
			}
		}) {
			@Override
			protected IResizable getResizable (int width) {
				verticalResizable.set(resizable.apply(width));
				return verticalResizable.get();
			}
		};
		ILayout verticalCSGOLayout=new CSGOLayout(new Labeled("Example",null,new ConstantToggleable(true)),new Point(100,100),480,WIDTH,animation,"Enabled",false,true,2,ChildMode.POPUP,colorPopup) {
			@Override
			public int getScrollHeight (Context context, int componentHeight) {
				return resizableHeight.apply(verticalResizable.get()).getScrollHeight(null,height);
			}
		};
		verticalCSGOLayout.populateGUI(verticalCSGOAdder,csgoGenerator,client,theme);
		// Category CSGO
		final AtomicReference<IResizable> categoryResizable=new AtomicReference<IResizable>(null);
		IComponentAdder categoryCSGOAdder=new PanelAdder(gui,true,new IBoolean() {
			@Override
			public boolean isOn() {
				return ClickGUIModule.layout.getValue()==ClickGUIModule.Layout.CSGOCategory;
			}
		},new UnaryOperator<String>() {
			@Override
			public String apply (String title) {
				return "categoryCSGO_"+title;
			}
		}) {
			@Override
			protected IResizable getResizable (int width) {
				categoryResizable.set(resizable.apply(width));
				return categoryResizable.get();
			}
		};
		ILayout categoryCSGOLayout=new CSGOLayout(new Labeled("Example",null,new ConstantToggleable(true)),new Point(100,100),480,WIDTH,animation,"Enabled",false,false,2,ChildMode.POPUP,colorPopup) {
			@Override
			public int getScrollHeight (Context context, int componentHeight) {
				return resizableHeight.apply(categoryResizable.get()).getScrollHeight(null,height);
			}
		};
		categoryCSGOLayout.populateGUI(categoryCSGOAdder,csgoGenerator,client,theme);
		// Searchable CSGO
		final AtomicReference<IResizable> searchableResizable=new AtomicReference<IResizable>(null);
		IComponentAdder searchableCSGOAdder=new PanelAdder(gui,true,new IBoolean() {
			@Override
			public boolean isOn() {
				return ClickGUIModule.layout.getValue()==ClickGUIModule.Layout.SearchableCSGO;
			}
		},new UnaryOperator<String>() {
			@Override
			public String apply (String title) {
				return "searchableCSGO_"+title;
			}
		}) {
			@Override
			protected IResizable getResizable (int width) {
				searchableResizable.set(resizable.apply(width));
				return searchableResizable.get();
			}
		};
		ILayout searchableCSGOLayout=new SearchableLayout(new Labeled("Example",null,new ConstantToggleable(true)),new Labeled("Search",null,new ConstantToggleable(true)),new Point(100,100),480,WIDTH,animation,"Enabled",2,ChildMode.POPUP,colorPopup,new Comparator<IModule>() {
			@Override
			public int compare (IModule a, IModule b) {
				return a.getDisplayName().compareTo(b.getDisplayName());
			}
		},charFilter,keys) {
			@Override
			public int getScrollHeight (Context context, int componentHeight) {
				return resizableHeight.apply(searchableResizable.get()).getScrollHeight(null,height);
			}
		};
		searchableCSGOLayout.populateGUI(searchableCSGOAdder,csgoGenerator,client,theme);
	}

	@Override
	protected HUDGUI getGUI() {
		return gui;
	}

	@Override
	protected GUIInterface getInterface() {
		return inter;
	}

	@Override
	protected int getScrollSpeed() {
		return ClickGUIModule.scrollSpeed.getValue();
	}

	
	private class ThemeSelector implements IThemeMultiplexer {
		protected Map<ClickGUIModule.Theme,ITheme> themes=new EnumMap<ClickGUIModule.Theme,ITheme>(ClickGUIModule.Theme.class);
		
		public ThemeSelector (IInterface inter) {
			final BooleanSetting clearGradient=new BooleanSetting("Gradient","gradient","Whether the title bars should have a gradient.",new IBoolean() {
				@Override
				public boolean isOn() {
					return ClickGUIModule.theme.getValue()==Theme.Clear;
				}
			},true);
			final BooleanSetting ignoreDisabled=new BooleanSetting("Ignore Disabled","ignoreDisabled","Have the rainbow drawn for disabled containers.",new IBoolean() {
				@Override
				public boolean isOn() {
					return ClickGUIModule.theme.getValue()==Theme.Rainbow;
				}
			},false);
			final BooleanSetting buttonRainbow=new BooleanSetting("Button Rainbow","buttonRainbow","Have a separate rainbow for each component.",new IBoolean() {
				@Override
				public boolean isOn() {
					return ClickGUIModule.theme.getValue()==Theme.Rainbow;
				}
			},false);
			final IntegerSetting rainbowGradient=new IntegerSetting("Rainbow Gradient","rainbowGradient","How fast the rainbow should repeat.",new IBoolean() {
				@Override
				public boolean isOn() {
					return ClickGUIModule.theme.getValue()==Theme.Rainbow;
				}
			},150,50,300);
			ClickGUIModule.theme.subSettings.add(clearGradient);
			ClickGUIModule.theme.subSettings.add(ignoreDisabled);
			ClickGUIModule.theme.subSettings.add(buttonRainbow);
			ClickGUIModule.theme.subSettings.add(rainbowGradient);
			addTheme(Theme.Clear,new ClearTheme(new ThemeScheme(Theme.Clear),new IBoolean() {
				@Override
				public boolean isOn() {
					return clearGradient.getValue();
				}
			},9,3,1,": "+EnumChatFormatting.GRAY));
			addTheme(Theme.GameSense,new GameSenseTheme(new ThemeScheme(Theme.GameSense),9,4,5,": "+EnumChatFormatting.GRAY));
			addTheme(Theme.Rainbow,new RainbowTheme(new ThemeScheme(Theme.Rainbow),new IBoolean() {
				@Override
				public boolean isOn() {
					return ignoreDisabled.getValue();
				}
			},new IBoolean() {
				@Override
				public boolean isOn() {
					return buttonRainbow.getValue();
				}
			},new IntSupplier() {
				@Override
				public int getAsInt() {
					return rainbowGradient.getValue();
				}
			},9,3,": "+EnumChatFormatting.GRAY));
			addTheme(Theme.Windows31,new Windows31Theme(new ThemeScheme(Theme.Windows31),9,2,9,": "+EnumChatFormatting.DARK_GRAY));
			addTheme(Theme.Impact,new ImpactTheme(new ThemeScheme(Theme.Impact),9,4));
		}
		
		@Override
		public ITheme getTheme() {
			return themes.getOrDefault(ClickGUIModule.theme.getValue(),themes.get(Theme.GameSense));
		}
		
		private void addTheme (Theme key, ITheme value) {
			themes.put(key,new OptimizedTheme(value));
			value.loadAssets(inter);
		}
		
		
		private class ThemeScheme implements IColorScheme {
			private final Theme themeValue;
			private final String themeName;
			
			public ThemeScheme (Theme themeValue) {
				this.themeValue=themeValue;
				this.themeName=themeValue.toString().toLowerCase();
			}
			
			@Override
			public void createSetting (ITheme theme, String name, String description, boolean hasAlpha, boolean allowsRainbow, Color color, boolean rainbow) {
				ClickGUIModule.theme.subSettings.add(new ColorSetting(name,themeName+"-"+name,description,new IBoolean() {
					@Override
					public boolean isOn() {
						return ClickGUIModule.theme.getValue()==themeValue;
					}
				},hasAlpha,allowsRainbow,color,rainbow));
			}

			@Override
			public Color getColor (final String name) {
				return ((ColorSetting)ClickGUIModule.theme.subSettings.stream().filter(new Predicate<Setting<?>>() {
					@Override
					public boolean test(Setting<?> t) {
						return t.configName.equals(themeName+"-"+name);
					}
				}).findFirst().orElse(null)).getValue();
			}
		}
	}
}
