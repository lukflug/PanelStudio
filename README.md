# PanelStudio
A simple yet flexible library to create ClickGUIs designed for use in Minecraft utility mods. It was originally designed for a private client, but made open source, so that it could be used for [GameSense](https://github.com/IUDevman/gamesense-client). Here are some screenshots of what is possible with this library:
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

## Features
* Ability to easily create new themes/skins.
* Overlapping Panels.
* Smooth animations and scrolling.
* Ability to have HUD components in panels.

## Implementation in Minecraft clients
A jar of this library is available in the Maven repository at https://lukflug.github.io/maven/ as `com.lukflug.panelstudio`.

### ClickGUI
To use the ClickGUI, following interfaces need to be implemented
* `Interface`
* `ColorScheme`
In addition:
* Use one of the supplied Themes or implement one yourself, to have a different look from GameSense (see `ClearTheme` and `GameSenseTheme` for reference).
* Populate the `ClickGUI` object with the desired components (i.e. adding a `DraggableContainer` for each category, adding a `CollapsibleContainer` for each module, and adding a settings component for each setting, this probably requires marking your settings objects with the interfaces in the `settings` package).
* It is recommended to have a class extending Minecraft's `GuiScreen` and implementing `Interface`, that has `ClickGUI` as a field, which gets populates in the constructor.
* For reference, consult the [javadoc](https://lukflug.github.io/javadoc/panelstudio/0.1.0/) and see the implementation in GameSense.
* Some custom classes may need to be created, for specific behaviour.

### HUD
Use `HUDClickGUI` instead of `ClickGUI`. Requries calling rendering functions even when the ClickGUI is closed, in order to render the HUD panels. HUD componets have to be `FixedComponent` (use `HUDComponent` as base class) and have to be added to the `HUDClickGUI` via a `HUDPanel`. This will make the HUD component a draggable panel when the ClickGUI is open. PanelStuudio provides `TabGUI` as a stock HUD component. The `TabGUI` requires passing key events when the ClickGUI is closed and has to be populated with categories and modules.

## Use in Gradle
Add following to your `build.gradle`:
```
repositories {
	maven {
		name = 'lukflug'
		url = 'https://lukflug.github.io/maven'
	}
}

dependencies {
	compile("com.lukflug:panelstudio:0.1.0")
}

shadowJar {
	dependencies {
		include(dependency('com.lukflug:panelstudio'))
	}
}
```

## Use as Gradle source dependency
To include the newest version in this repository in your gradle build, you have to add following to your `settings.gradle`:
```
sourceControl {
	gitRepository("https://github.com/lukflug/PanelStudio.git") {
		producesModule("com.lukflug:panelstudio")
	}
}
```
In addition you have to add following to your `build.gradle`:
```
dependencies {
	compile("com.lukflug:panelstudio") {
		version {
			branch='main'
		}
	}
}

shadowJar {
	dependencies {
		include(dependency('com.lukflug:panelstudio'))
	}
}
```
**Warning: the code in this repository may be subject to change and may cause your code to become incompatible, so fork this repo and include the fork in your project. The binaries in  the Maven repository are recommended for regular use.**
