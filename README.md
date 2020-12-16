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
In addition to the core PanelStudio library, there is the PanelStudio-MC library (`com.lukflug.panelstudio.mc` package), which is a source code library that includes Minecraft depedencies. It was tested on Minecraft Forge 1.12.2, but probably works on Fabric and may or may not work on other Minecraft versions. The PanelStudio core library works on any Minecraft version (and even on any non-Minecraft application).

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
	compile("com.lukflug:panelstudio:0.1.1")
}

shadowJar {
	dependencies {
		include(dependency('com.lukflug:panelstudio'))
	}
}
```
If you're planning to use PanelStudio-MC you have to also add this:
```
sourceSets {
	main {
		java {
			srcDir 'src/main/java'
			srcDir 'build/panelstudio'
		}
	}
}

task downloadPanelstudio {
	new URL("https://github.com/lukflug/PanelStudio/releases/download/v0.1.1/panelstudio-mc-0.1.1.jar").withInputStream{i->new File("${buildDir}/panelstudio-mc-0.1.1.jar").withOutputStream{it<<i}}
}

task unpackPanelstudio(dependsOn: downloadPanelstudio, type: Copy) {
    from zipTree("${buildDir}/panelstudio-mc-0.1.1.jar")
    into "${buildDir}/panelstudio"
}
```
Run the task `unpackPanelstudio` (which downloads and extracts the PanelStudio-MC source library for you) once before building.

### ClickGUI
The precise way PanelStudio is used in an utility mod depends on the module and setting manager. However the implementation should roughly follow follwing scheme. The main ClickGUI class should extend `MinecraftGUI` (if using PanelStudio-MC):
```
public class CoolGUI extends MinecraftGUI {
	private final Toggleable colorToggle;
	private final GUIInterface guiInterface;
	private final Theme theme;
	private final ClickGUI gui;

	public CoolGUI() {
		colorToggle=CoolSettings.colorModel // <-- Toggleable indicating whether to use the RGB or HSB model for color settings
		theme=new GameSenseTheme(new SettingsColorScheme(CoolSettings.activeColor,CoolSettings.inactiveColor,CoolSettings.backgroundColor,CoolSettings.outlineColor,CoolSettings.fontColor,CoolSettings.opacity),height,2); // <-- Can be replaced by another theme (could be a custom one)
		guiInterface=new GuiInterface() {
			// TODO: implement methods
		};
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
		CoolSettings.loadGUISettings();
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
TODO: Add explanation of Cool-- classes and settings

### HUD
Use `HUDClickGUI` instead of `ClickGUI`. Requries calling rendering functions even when the ClickGUI is closed, in order to render the HUD panels. HUD components have to be `FixedComponent` (use `HUDComponent` as base class) and have to be added to the `HUDClickGUI` via a `HUDPanel`. This will make the HUD component a draggable panel when the ClickGUI is open. PanelStudio provides `TabGUI` as a stock HUD component. The `TabGUI` requires passing key events when the ClickGUI is closed and has to be populated with categories and modules.
