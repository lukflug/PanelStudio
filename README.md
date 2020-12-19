# PanelStudio
A simple yet flexible library to create ClickGUIs designed for use in Minecraft utility mods. It was originally designed for a private client, but made open source, so that it could be used for [GameSense](https://github.com/IUDevman/gamesense-client). Here are some screenshots of what is possible with this library (note that these themes are examples and that one can make the GUI look like basically anything):
* CyberHack Theme:
![cyberhack](https://cdn.discordapp.com/attachments/747111616407011389/779996603510947870/2020-11-21_20.23.26.png)
* GameSense 2.0 Theme:
![gamesense20](https://cdn.discordapp.com/attachments/747111616407011389/779996468730920960/2020-11-21_20.13.36.png)
* GameSense 2.1 Theme:
![gamesense21](https://cdn.discordapp.com/attachments/747111616407011389/779996509717659658/2020-11-21_20.16.09.png)
* PepsiMod Theme:
![pepsimod](https://cdn.discordapp.com/attachments/747111616407011389/779996535366090772/2020-11-21_20.20.43.png)
* GameSense 2.2 Theme:
![gamesense22](https://cdn.discordapp.com/attachments/747111616407011389/779996442285834240/2020-11-21_19.57.25.png)
* Future Theme:
![future](https://cdn.discordapp.com/attachments/747111616407011389/779996632334073896/2020-11-21_20.25.30.png)

This repostiory only includes the GameSense themes, however, since Cyber didn't want me to publish the other themes. The library has no depedencies (aside from the JRE itself), so it can be easily used for other purposes, aside from Minecraft utility mods. Thanks to Go_Hoosiers, for suggesting the name of this library. If you use this library, some attribution would be greatly appreciated. Consider visiting the PanelStudio discord server: (https://discord.gg/E3DrF4XvUE).

## Structure
This library contains following packages:
* `com.lukflug.panelstudio`: basic library and ClickGUI.
* `com.lukflug.panelstudio.settings`: common ClickGUI components and interfaces to mark module setting classes.
* `com.lukflug.panelstudio.theme`: contains base classes for themes and GameSense themes.
* `com.lukflug.panelstudio.tabgui`: TabGUI.
* `com.lukflug.panelstudio.hud`: HUD panels.

In addition to the core PanelStudio library, there is the PanelStudio-MC library (`com.lukflug.panelstudio.mc` package), which is a source code library that includes Minecraft depedencies. It was tested on Minecraft Forge 1.12.2, but may or may not work on Fabric or other Minecraft versions. The PanelStudio core library works on any Minecraft version (and even on any non-Minecraft application).

## Features
* Ability to easily create new themes/skins.
* Overlapping Panels.
* Smooth animations and scrolling.
* Ability to have HUD components in panels.

## Implementation in Minecraft clients
A jar of this library is available in the Maven repository at https://lukflug.github.io/maven/ as `com.lukflug.panelstudio`.

### Use in Gradle
Add following to your `build.gradle`:
```
repositories {
	maven {
		name = 'lukflug'
		url = 'https://lukflug.github.io/maven'
	}
}

dependencies {
	compile("com.lukflug:panelstudio:0.1.2")
}

shadowJar {
	dependencies {
		include(dependency('com.lukflug:panelstudio'))
	}
}
```
If you're planning to use PanelStudio-MC you have to also add this:
```
task downloadPanelstudio {
	doLast {
		new URL("https://github.com/lukflug/PanelStudio/releases/download/v0.1.2/panelstudio-mc-0.1.1.jar").withInputStream{i->new File("${buildDir}/panelstudio-mc-0.1.1.jar").withOutputStream{it<<i}}
	}
}

task unpackPanelstudio(dependsOn: downloadPanelstudio, type: Copy) {
	from zipTree("${buildDir}/panelstudio-mc-0.1.1.jar")
	into "src/main/java"
}
```
Run the task `unpackPanelstudio` (which downloads and extracts the PanelStudio-MC source library for you) once before building. If you're using git you may also want to ignore the PanelStudio-MC source in `.gitignore`:
```
src/main/java/com/lukflug/panelstudio
src/main/java/META-INF
```
You can also do the `unpackPanelstudio` automatically when running `setupDecompWorkspace`, by adding:
```
setupDecompWorkspace.dependsOn(unpackPanelstudio)
```

### ClickGUI
The precise way PanelStudio is used in an utility mod depends on the module and setting manager. However the implementation should roughly follow follwing scheme. The main ClickGUI class should extend `MinecraftGUI` (if using PanelStudio-MC):
```
public class CoolGUI extends MinecraftGUI {
	private final Toggleable colorToggle;
	private final GUIInterface guiInterface;
	private final Theme theme;
	private final ClickGUI gui;

	public CoolGUI() {
		// Intialize necessary fields
		colorToggle=CoolSettings.colorModel // <-- Toggleable indicating whether to use the RGB or HSB model for color settings
		guiInterface=new GuiInterface() {
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
		theme=new GameSenseTheme(new SettingsColorScheme(CoolSettings.activeColor,CoolSettings.inactiveColor,CoolSettings.backgroundColor,CoolSettings.outlineColor,CoolSettings.fontColor,CoolSettings.opacity),height,2); // <-- Can be replaced by another theme (could be a custom one)
		gui=new ClickGUI(guiInterface);
		// Populate the ClickGUI with modules and settings
		for (CoolCategory category: categories) {
			DraggableContainer panel=new DraggableContainer(category.name,theme.getPanelRenderer(),new SimpleToggleable(false),new SettingsAnimation(CoolSettings.animationSpeed),new Point(x,y),width); // <-- Width and default position of the panels needs to be defined
			gui.addComponent(panel);
			for (CoolModule module: category) {
				CollapsibleContainer container=new ToggleableContainer(module.name,theme.getContainerRenderer(),new SimpleToggleable(false),new SettingsAnimation(CoolSettings.animationSpeed),module); // <-- It is recommended that the module-class implements Toggleable
				panel.addComponent(container);
				for (CoolSetting setting: module) {
					if (setting instanceof Toggleable) cotainer.addComponent(setting.name,theme.getComponentRenderer(),setting);
					else if (setting instanceof NumberSetting) cotainer.addComponent(setting.name,theme.getComponentRenderer(),setting,setting.min,setting.max);
					else if (setting instanceof EnumSetting) cotainer.addComponent(setting.name,theme.getComponentRenderer(),setting);
					else if (setting instanceof ColorSetting) cotainer.addComponent(setting.name,theme.getComponentRenderer(),new SettingsAnimation(CoolSettings.animationSpeed),setting,setting.alpha,setting.rainbowEnabled,colorToggle);
				}
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
		return CoolSettings.scrollSpeed;
	}
}
```
The loops and methods involving `CoolCategory`, `CoolModule` and `CoolSetting` have to be replaced with the appropriate components of your module and settings manager. The fields in `CoolSettings` have to be replaced by appropriate settings object implementing the required PanelStudio interfaces. The methods in `CoolFont` have to be replaced by methods in your font manager.

In addition, if you want to save the GUI panel positions, the `gui.loadConfig` should be called after initializing the ClickGUI and `gui.saveConfig` should be called before closing the game. The `ConfigList` interface will need to be implemented.

To allow the user to open the GUI, a ClickGUI module can be created, which calls the `enterGUI` method when enabled.

### HUD
Use `MinecraftHUDGUI` instead of `MinecraftGUI` and `HUDClickGUI` instead of `ClickGUI` (override `getHUDGUI` instead of `getGUI`). Requries calling `render` and `handleKeyEvent` (provided HUD components have to react to keystrokes) when the ClickGUI is closed. HUD components have to be `FixedComponent` (use `HUDComponent` as base class) and have to be added to `HUDClickGUI` via a `HUDPanel`. This will make the HUD component a draggable panel when the ClickGUI is open. PanelStudio provides `TabGUI` as a stock HUD component.

It is recommended to have hud modules with a function that intitializes and returns a `FixedComponent` which is added in the constructor of the ClickGUI, just before populating the settings panels:
```
for (HUDModule hudModule: hudModules) {
	gui.addHUDComponent(new HUDPanel(hudModule.getComponent(),theme.getPanelRenderer(),module,new SettingsAnimation(CoolSettings.animationSpeed),hudToggle,border));
	
}
```
The `hudToggle` toggleable can be the `gui` (instance of `HUDClickGUI`) or, if you want to disable showing the HUD panel frames when the GUI is open you can have something like this:
```
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
For a list of classes and methods, consult the [javadoc](https://lukflug.github.io/javadoc/panelstudio/0.1.2/). For an example implementation, consult the GameSense source code.

## Creating custom themes
The components provided by PanelStudio use the methods in the `Renderer` interface to render. A `Theme` consist of three renderers: one for the single components (settings), one for the containers (modules) and one for the panels (categories). To see how themes are implemented, consult the package `com.lukflug.panelstudio.theme`.
