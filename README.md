# PanelStudio
An extensible and customizable GUI API/library to create ClickGUIs, HUDEditors and TabGUIs designed for use in Minecraft utility mods. It was originally designed for a private client, but made open source, so that it could be used for [GameSense](https://github.com/IUDevman/gamesense-client).

The library has no depedencies (aside from Java 8), so it can be easily used for other purposes, aside from Minecraft utility mods. Thanks to Go_Hoosiers, for suggesting the name of this library. If you use this library, some attribution would be greatly appreciated. Also consider visiting the PanelStudio discord server: https://discord.gg/E3DrF4XvUE.

In addition to the core PanelStudio library, there are the PanelStudio-MC support libraries, which depend on Minecraft:
| Library                 | Example Mod          | Target                 |
| ----------------------- | -------------------- | ---------------------- |
| panelstudio-mc12        | example-mod12        | Minecraft Forge 1.12.2 |
| panelstudio-mc16-fabric | example-mod16-fabric | FabricMC 1.16.5        |
| panelstudio-mc16-forge  | example-mod16-forge  | Minecraft Forge 1.16.5 |
| panelstudio-mc8-fabric  | example-mod8-fabric  | Legacy Fabric 1.8.9    |
| panelstudio-mc8-forge   | example-mod8-forge   | Minecraft Forge 1.8.9  |
| panelstudio-mc17        | example-mod17 & example-mod18 | FabricMC 1.17.1 & 1.18.2 |
| panelstudio-mc19        | example-mod19        | FabricMC 1.19.2        |

Minecraft versions or APIs not in this table can also be used with PanelStudio, but the small PanelStudio-MC library has to be ported, which shouldn't be hard in most cases.

## Credits
* Thanks to NirvanaNevermind for making the original port of PanelStudio-MC to Fabric 1.8.9!
* Thanks to Go_Hoosiers (aka. GooberTown or IUDevman) for designing the original PanelStudio logo!
* Thanks to Chomp for making a pull request to change the readme, I guess.
* Thanks to Diliard for porting PanelStudio-MC to Fabric 1.19.2!

## Features
* Everything is flexible and can be extended.
* Wide variety of widgets.
* Ability to easily create new themes/skins.
* A large amount of combinations of layouts.
* Any combination of theme and layout is possible, thus having a huge amount of options, even with the default themes and layouts.
* Smooth animations and scrolling.
* Ability to have HUD components in panels.
Unfortunately, performance isn't the best, so not every combination makes sense.

## Documentation and Help
Here are some useful resources to figure out PanelStudio:
* The [JavaDoc](https://lukflug.github.io/panelstudio.html).
* The [wiki tutorial](https://github.com/lukflug/PanelStudio/wiki).
* The example mods included in this repository.

## Screenshots
Here are some examples of what PanelStudio can accomplish (note: some of the presented themes are private an not available on this repository):
![a](https://raw.githubusercontent.com/lukflug/PanelStudio/main/screenshots/2021-08-20_19.44.27.png)
![b](https://raw.githubusercontent.com/lukflug/PanelStudio/main/screenshots/2021-08-20_19.44.35.png)
![c](https://raw.githubusercontent.com/lukflug/PanelStudio/main/screenshots/2021-08-20_19.44.38.png)
![d](https://raw.githubusercontent.com/lukflug/PanelStudio/main/screenshots/2021-08-20_19.44.42.png)
![e](https://raw.githubusercontent.com/lukflug/PanelStudio/main/screenshots/2021-08-20_19.45.38.png)
![f](https://raw.githubusercontent.com/lukflug/PanelStudio/main/screenshots/2021-08-20_19.45.49.png)
![g](https://raw.githubusercontent.com/lukflug/PanelStudio/main/screenshots/2021-08-20_19.46.03.png)
![h](https://raw.githubusercontent.com/lukflug/PanelStudio/main/screenshots/2021-08-20_19.46.17.png)
![i](https://raw.githubusercontent.com/lukflug/PanelStudio/main/screenshots/2021-08-20_19.46.35.png)
![j](https://raw.githubusercontent.com/lukflug/PanelStudio/main/screenshots/2021-08-20_19.48.45.png)
![k](https://raw.githubusercontent.com/lukflug/PanelStudio/main/screenshots/2021-08-20_19.48.56.png)
![l](https://raw.githubusercontent.com/lukflug/PanelStudio/main/screenshots/2021-08-20_19.49.03.png)
![m](https://raw.githubusercontent.com/lukflug/PanelStudio/main/screenshots/2021-08-20_19.49.07.png)
![n](https://raw.githubusercontent.com/lukflug/PanelStudio/main/screenshots/2021-08-20_19.55.32.png)
![o](https://raw.githubusercontent.com/lukflug/PanelStudio/main/screenshots/2021-08-20_19.55.47.png)
![p](https://raw.githubusercontent.com/lukflug/PanelStudio/main/screenshots/2021-08-20_19.55.54.png)
