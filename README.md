# PanelStudio
A simple yet flexible library to create ClickGUIs designed for use in Minecraft utility mods. It was originally designed for a private client, but made open source, so that it could be used for [GameSense](https://github.com/IUDevman/gamesense-client). Here are some screenshots of what is possible with this library (note that these themes are examples and that one can make the GUI look like basically anything):

<details open>
<summary>Themes</summary> <br>
CyberHack Theme:
<img alt="cyberhack" src=https://cdn.discordapp.com/attachments/780094001331961901/796061322261168198/2021-01-05_17.57.02.png> <br>
GameSense 2.0 Theme:
<img alt="gamesense20" src=https://cdn.discordapp.com/attachments/780094001331961901/796061336156373062/2021-01-05_17.57.07.png> <br>
GameSense 2.1 Theme:
<img alt="gamesense21" src=https://cdn.discordapp.com/attachments/780094001331961901/796061343086149662/2021-01-05_17.57.11.png> <br>
PepsiMod Theme:
<img alt="pepsimod" src=https://cdn.discordapp.com/attachments/780094001331961901/796061349981323274/2021-01-05_17.57.19.png> <br>
GameSense 2.2 Theme:
<img alt="gamesense22" src=https://cdn.discordapp.com/attachments/780094001331961901/796061357858488450/2021-01-05_17.57.23.png> <br> 
Future Theme:
<img alt="future" src=https://cdn.discordapp.com/attachments/780094001331961901/796061366057435166/2021-01-05_17.57.27.png> <br>
KAMI Blue Theme:
<img alt="kamiblue" src=https://cdn.discordapp.com/attachments/780094001331961901/796061373058646058/2021-01-05_17.57.36.png> <br>
Windows Theme:
<img alt="windows" src=https://cdn.discordapp.com/attachments/780094001331961901/796061378686615572/2021-01-05_17.57.42.png> <br>
</details>

This repository only includes the GameSense themes, however, since Cyber didn't want me to publish the other themes. The library has no depedencies (aside from Java 8), so it can be easily used for other purposes, aside from Minecraft utility mods. Thanks to Go_Hoosiers, for suggesting the name of this library. If you use this library, some attribution would be greatly appreciated. Consider visiting the PanelStudio discord server: https://discord.gg/E3DrF4XvUE.

In addition to the core PanelStudio library, there are the PanelStudio-MC libraries. The PanelStudio-MC12 library is designed for Minecraft Forge 1.12.2 (it may or may not work for other versions and mods that use MCP only). The PanelStudio-MC16 library is designed for Fabric 1.16.5 and also works on 1.16.4 (works on anything using Fabric's Yarn mappings, it probably works on older versions, this has however not been tested). The PanelStudio-MC8-Fabric library is designed for Legacy Fabric 1.8.9 (as with MC16, it only requires Yarn) and the PanelStudio-MC8-Forge library is designed for Minecraft Forge 1.8.9 (as with MC12, it may also work on MCP-only projects). The PanelStudio core library works on any Minecraft version (and even on any non-Minecraft application).

**Thanks to NirvanaNevermind for porting PanelStudio-MC to Fabric 1.8.9!**

## Features
* Ability to easily create new themes/skins.
* Overlapping Panels.
* Smooth animations and scrolling.
* Ability to have HUD components in panels.

## Implementation in Minecraft clients
A jar of this library is available in the Maven repository at https://lukflug.github.io/maven/ as `com.lukflug.panelstudio`.

### Use in Gradle 4.10.3
Add following to your `build.gradle`:
```groovy
repositories {
	maven {
		name = 'lukflug'
		url = 'https://lukflug.github.io/maven'
	}
}

dependencies {
	compile('com.lukflug:panelstudio:0.1.11')
}

shadowJar {
	dependencies {
		include(dependency('com.lukflug:panelstudio'))
	}
}
```
If you're planning to use PanelStudio-MC12 you have to also add this:
```groovy
dependencies {
	compile('com.lukflug:panelstudio-mc12:0.1.8')
}

shadowJar {
	dependencies {
		include(dependency('com.lukflug:panelstudio-mc12'))
	}
}
```
If you're planning to use PanelStudio-MC16 you have to also add this:
```groovy
dependencies {
	modCompile('com.lukflug:panelstudio-mc16:0.1.9')
}

shadowJar {
	dependencies {
		include(dependency('com.lukflug:panelstudio-mc16'))
	}
}
```
If you're planning to use PanelStudio-MC8-Fabric you have to also add this:
```groovy
dependencies {
	modCompile('com.lukflug:panelstudio-mc8-fabric:0.1.10')
}

shadowJar {
	dependencies {
		include(dependency('com.lukflug:panelstudio-mc8-fabric'))
	}
}
```
If you're planning to use PanelStudio-MC8-Forge you have to also add this:
```groovy
dependencies {
	compile('com.lukflug:panelstudio-mc8-forge:0.1.10')
}

shadowJar {
	dependencies {
		include(dependency('com.lukflug:panelstudio-mc8-forge'))
	}
}
```

### ClickGUI
The precise way PanelStudio is used in an utility mod depends on the module and setting manager. However the implementation should roughly follow follwing scheme. The main ClickGUI class should extend `MinecraftGUI` (if using PanelStudio-MC):
```java
public class CoolGUI extends MinecraftGUI {
	private final Toggleable colorToggle;
	private final GUIInterface guiInterface;
	private final Theme theme;
	private final ClickGUI gui;

	public CoolGUI() {
		// Intialize necessary fields
		colorToggle=CoolSettings.colorModel // <-- Toggleable indicating whether to use the RGB or HSB model for color settings
		guiInterface=new GUIInterface(true) {
			@Override
			protected String getResourcePrefix() {
				return "coolhack:gui/";
			}
			
			@Override
			public void drawString(Point pos, String s, Color c) {
				end();
				CoolFont.drawString(s,pos.x,pos.y,c);
				begin();
			}
			
			@Override
			public int getFontWidth(String s) {
				return CoolFont.getFontWidth(s);
			}

			@Override
			public int getFontHeight() {
				return CoolFont.getFontHeight();
			}
		};
		theme=new GameSenseTheme(new SettingsColorScheme(CoolSettings.activeColor,CoolSettings.inactiveColor,CoolSettings.backgroundColor,CoolSettings.outlineColor,CoolSettings.fontColor,CoolSettings.opacity),height,2,5); // <-- Can be replaced by another theme (could be a custom one)
		gui=new ClickGUI(guiInterface,null);
		// Populate the ClickGUI with modules and settings
		for (CoolCategory category: categories) {
			DraggableContainer panel=new DraggableContainer(category.name,null,theme.getPanelRenderer(),new SimpleToggleable(false),new SettingsAnimation(CoolSettings.animationSpeed),null,new Point(x,y),width); // <-- Width and default position of the panels needs to be defined
			gui.addComponent(panel);
			for (CoolModule module: category) {
				CollapsibleContainer container=new CollapsibleContainer(module.name,null,theme.getContainerRenderer(),new SimpleToggleable(false),new SettingsAnimation(CoolSettings.animationSpeed),module); // <-- It is recommended that the module-class implements Toggleable
				panel.addComponent(container);
				for (CoolSetting setting: module) {
					if (setting instanceof Toggleable) container.addComponent(new BooleanComponent(setting.name,null,theme.getComponentRenderer(),(Toggleable)setting));
					else if (setting instanceof NumberSetting) container.addComponent(new NumberComponent(setting.name,null,theme.getComponentRenderer(),(NumberSetting)setting,setting.min,setting.max));
					else if (setting instanceof EnumSetting) container.addComponent(new EnumComponent(setting.name,null,theme.getComponentRenderer(),(EnumSetting)setting));
					else if (setting instanceof ColorSetting) container.addComponent(new ColorComponent(setting.name,null,theme.getContainerRenderer(),new SettingsAnimation(CoolSettings.animationSpeed),theme.getComponentRenderer(),(ColorSetting)setting,setting.alpha,setting.rainbowEnabled,colorToggle));
				}
				container.addComponent(new KeybindComponent(theme.getComponentRenderer(),module.getKeybind()));
			}
		}
	}

	@Override
	protected ClickGUI getGUI() {
		return gui;
	}

	@Override
	protected GUIInterface getInterface() {
		return guiInterface;
	}

	@Override
	protected int getScrollSpeed() {
		return CoolSettings.scrollSpeed.getValue();
	}
}
```
The loops and methods involving `CoolCategory`, `CoolModule` and `CoolSetting` have to be replaced with the appropriate components of your module and settings manager. The fields in `CoolSettings` have to be replaced by appropriate settings object implementing the required PanelStudio interfaces. The methods in `CoolFont` have to be replaced by methods in your font manager.

In addition, if you want to save the GUI panel positions, the `gui.loadConfig` should be called after initializing the ClickGUI and `gui.saveConfig` should be called before closing the game. The `ConfigList` interface will need to be implemented.

To allow the user to open the GUI, a ClickGUI module can be created, which calls the `enterGUI` method when enabled.

### HUD
Use `MinecraftHUDGUI` instead of `MinecraftGUI` and `HUDClickGUI` instead of `ClickGUI` (override `getHUDGUI` instead of `getGUI`). Requires calling `render` and `handleKeyEvent` (provided HUD components have to react to keystrokes) when the ClickGUI is closed. HUD components have to be `FixedComponent` (use `HUDComponent` as base class) and have to be added to `HUDClickGUI` via a `HUDPanel`. This will make the HUD component a draggable panel when the ClickGUI is open. PanelStudio provides `TabGUI` as a stock HUD component.

It is recommended to have HUD modules with a function that initializes and returns a `FixedComponent` which is added in the constructor of the ClickGUI, just before populating the settings panels:
```java
for (HUDModule hudModule: hudModules) {
	gui.addHUDComponent(new HUDPanel(hudModule.getComponent(),theme.getPanelRenderer(),module,new SettingsAnimation(CoolSettings.animationSpeed),hudToggle,border));
	
}
```
The `hudToggle` toggleable can be the `gui` (instance of `HUDClickGUI`) or, if you want to disable showing the HUD panel frames when the GUI is open you can have something like this:
```java
Toggleable hudToggle=new Toggleable() {
	@Override
	public void toggle() {
	}

	@Override
	public boolean isOn() {
		return gui.isOn() && CoolSettings.showHUDPanels.isOn();
	}
}
```

## Reference
For a list of classes and methods, consult the [javadoc](https://lukflug.github.io/javadoc/panelstudio/0.1.11/overview-summary.html). For an example implementation, consult the GameSense source code.

## Creating custom themes
The components provided by PanelStudio use the methods in the `Renderer` interface to render. A `Theme` consist of three renderers: one for the single components (settings), one for the containers (modules) and one for the panels (categories). To see how themes are implemented, consult the package `com.lukflug.panelstudio.theme`.
