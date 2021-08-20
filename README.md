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
| panelstudio-mc17        | example-mod17        | FabricMC 1.17.1        |

Minecraft versions or APIs not in this table can also be used with PanelStudio, but the small PanelStudio-MC library has to be ported, which shouldn't be hard in most cases.

## Credits
* Thanks to NirvanaNevermind for making the original port of PanelStudio-MC to Fabric 1.8.9!
* Thanks to Go_Hoosiers (aka. GooberTown or IUDevman) for designing the original PanelStudio logo!
* Thanks to Chomp for making a pull request to change the readme, I guess.

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
* The included example mods.
