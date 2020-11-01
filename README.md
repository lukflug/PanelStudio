# PanelStudio
A simple yet flexible library to create ClickGUIs designed for use in Minecraft utility mods. It was originally designed for a private client, but made open source, so that it could be used for [GameSense](https://github.com/IUDevman/gamesense-client). Here are some screenshots of what is possible with this client:
* CyberHack Theme:
![cyberhack](https://cdn.discordapp.com/attachments/755077474861449277/770697901499744286/2020-10-27_18.16.50.png)
* GameSense 2.0 Theme:
![gamesense20](https://cdn.discordapp.com/attachments/755077474861449277/770697937234821170/2020-10-27_18.16.59.png)
* GameSense 2.1 Theme:
![gamesense21](https://cdn.discordapp.com/attachments/755077474861449277/770697959947239424/2020-10-27_18.17.12.png)
* PepsiMod Theme:
![pepsimod](https://cdn.discordapp.com/attachments/755077474861449277/770698000129327124/2020-10-27_18.17.22.png)
* GameSense 2.2 Theme:
![gamesense22](https://cdn.discordapp.com/attachments/767021200685400075/772018964414857246/unknown.png)
* Future Theme:
![future](https://cdn.discordapp.com/attachments/755077474861449277/771799117998718986/unknown.png)

This repostiory only includes the GameSense themes, however, since Cyber didn't want me to publish the other themes. The library has no depedencies (aside from the JRE itself), so it can be easily used for other purposes, aside from Minecraft utility mods. Thanks to Go_Hoosiers, for suggesting the name of this library. If you use this library, some attribution would be greatly appreciated.

## Implementation in Minecraft clients
A jar of this library is available in the Maven repository at https://lukflug.github.io/maven/ as `com.lukflug.panelstudio`.

To use this library in your Minecraft clients, you have to do following things:
* Implement the `Interface` interface.
* Implement the `ColorScheme` interface.
* Use one of the supplied Themes or implement one yourself, to have a different look from GameSense (see `ClearTheme` and `GameSenseTheme` for reference).
* Populate the `ClickGUI` object with the desired components (probably requires marking your settings objects with the interfaces in the `settings` package).
* It is recommended to have a class extending Minecraft's `GuiScreen` and implementing `Interface`, that has `ClickGUI` as a field and populates it in the constructor.
* For reference, consult the javadoc and see the implementation in GameSense.

## Use with Gradle
To include the project in your gradle build, you have to add following to your `settings.gradle`:
```
sourceControl {
	gitRepository("https://github.com/lukflug/PanelStudio.git") {
		producesModule("com.lukflug:panelstudio")
	}
}
```
In addition you have to add following to your `build.gradle` under `dependencies`:
```
implementation("com.lukflug:panelstudio") {
	version {
		branch='main'
	}
}
```
Note: you may need to use the deperecated `compile` instead of `implementation` for compatibility with Shadow. To have a valid mod jar when builidng a forge mod, add following to `build.gradle` under `shadowJar` under `dependencies`:
```
include(dependency('com.lukflug:panelstudio'))
```
**Warning: the code in this repository may be subject to change and may cause your code to become incompatible, so fork this repo and include the fork in your project.**
