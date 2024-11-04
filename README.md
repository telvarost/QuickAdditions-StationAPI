# QuickAdditions StationAPI Edition for Minecraft Beta 1.7.3

A StationAPI mod for Minecraft Beta 1.7.3 that adds in features that were requested, but didn't have enough time to make a full fledged mod for the request.

# Quick Additions
Mod now works on Multiplayer with GlassConfigAPI version 2.0+ used to sync configs!

* Adds ability to give the player missing achievements that they may have already achieved.
  * Load into a world and access mod config from pause menu using [ModMenu](https://modrinth.com/mod/modmenu-beta).
  * Adding back missing achievements will not work from the main menu or when game is not running.
* Adds ability to force display active even when tabbing away from the screen.
* Adds ability to change background music countdown timer min and max interval values.
* Adds ability to add custom background music to the game.
  * On first run of the mod a folder named `custom-music` will be created.
  * Add `.ogg`, `.mus`, or `.wav` files to this folder, and they will have a random chance to play in-game.
    * No other file types are supported by default in Minecraft or by this mod.
* Adds ability to disable default Minecraft background music.
* Adds ability to add a main menu song.
  * On first run of the mod a folder named `main-menu-theme` will be created.
  * Add `mainmenu.ogg`, `mainmenu.mus`, or a `mainmenu.wav` file to this folder, this song will play on the title screen.
    * No other file types are supported by default in Minecraft or by this mod.
  * Adds ability to have main menu song override background music.
    * (Vanilla b1.7.3 has Minecraft background music continue to play on main menu if started in-game)
* Adds ability to speed up night rather than skip it.
  * (Takes about 30 seconds to sleep the entire night away using this method)
* Adds ability to exit beds by clicking or swinging the hand.
* Adds ability to change max mob spawn capacity for different creature types.
  * Monster, passive (animals), and squid max spawn capacity can be changed.
* Adds ability to change mob spawn group size.

## List of added recipes

* Craftable soul sand.

## Images of recipes

### v1.0.0 Recipes
![soul_sand craft recipe](https://github.com/telvarost/QuickAdditions-StationAPI/blob/main/images/SoulSandRecipe.PNG)

## Installation using Prism Launcher

1. Download an instance of Babric for Prism Launcher: https://github.com/telvarost/prism-instance/releases/tag/v0.5.0-candidate
2. Install Java 17 and set the instance to use it: https://adoptium.net/temurin/releases/
3. Add StationAPI to the mod folder for the instance: https://modrinth.com/mod/stationapi
4. (Optional) Add Mod Menu to the mod folder for the instance: https://modrinth.com/mod/modmenu-beta
5. (Optional) Add GlassConfigAPI 2.0+ to the mod folder for the instance: https://modrinth.com/mod/glass-config-api
6. Add this mod to the mod folder for the instance: https://github.com/telvarost/QuickAdditions-StationAPI/releases
7. Run and enjoy! 👍

## Feedback

Got any suggestions on what should be added next? Feel free to share it by [creating an issue](https://github.com/telvarost/QuickAdditions-StationAPI/issues/new). Know how to code and want to do it yourself? Then look below on how to get started.

## Contributing

Thanks for considering contributing! To get started fork this repository, make your changes, and create a PR. 

If you are new to StationAPI consider watching the following videos on Babric/StationAPI Minecraft modding: https://www.youtube.com/watch?v=9-sVGjnGJ5s&list=PLa2JWzyvH63wGcj5-i0P12VkJG7PDyo9T
