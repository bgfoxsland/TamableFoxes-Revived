<h1 align="center">Tamable Foxes</h1>
<p align="center">
SpigotMC Plugin that gives you the ability to tame foxes!
</p>

 **Version Support**
 
 Due to limited development resources, this project **only supports the latest Minecraft 26.3**. All legacy version adapters (1.20.x ~ 1.21.x) have been removed. If you are still on an older Minecraft version, please use the original [TamableFoxes](https://github.com/SeanOMik/TamableFoxes).

### WARNING: Do not reload the plugin, you may loose foxes!!

### Default configuration files:
* <a href="https://github.com/SeanOMik/TamableFoxes/blob/master/Plugin/src/main/resources/config.yml">config.yml</a>
* <a href="https://github.com/SeanOMik/TamableFoxes/blob/master/Plugin/src/main/resources/language.yml">language.yml</a>
<br>

If you get any errors, <a href="https://github.com/SeanOMik/TamableFoxes/issues/new">create an issue!</a><br><br>

Have you ever wanted to tame foxes? Well, now you can! <b>Use chicken to tame</b> and sweet berries to breed them!<br><br>

## Features:
* 33% Chance of taming
* Breeding
* Wild foxes pick berry bushes
* Leaping on targets
* Tamed foxes sleep when their owner does
* Foxes follow owner
* You can shift + right-click to let the fox hold items
* Right-click to make the fox sit
* Shift Right-click with an empty hand to make the fox sleep
* If the fox is holding a totem of undying, the fox will consume it and be reborn.
* Tamed foxes **never** attack creatures on their own — they only attack owner-provided targets:
  * the thing the owner is attacking
  * the thing that attacked the owner
* Wild (untamed) foxes keep vanilla behavior: they hunt chickens, rabbits, fish and turtle eggs.
* Foxes are automatically spawned inside the world. (Same areas as vanilla foxes)
* Snow and red foxes.
* Language.yml
* Message when a tamed fox dies
* /givefox command to give foxes to other players.
* Disabling certain gameplay messages
  * You can do this by changing certain fields in `language.yml` to "disabled". The fields that can be disabled are:
    * `taming-tamed-message`
    * `taming-asking-for-name-message`
    * `taming-chosen-name-perfect`
    * `fox-doesnt-trust`

## Commands:
* /spawntamablefox [red/snow]: Spawns a tamable fox at the players' location.
* /tamablefoxes reload: Reloads
* /givefox [player name]: Give a fox to another player.

## Permissions:
* `tamablefoxes.reload`: Reloads the plugin config. Default: `op`
* `tamablefoxes.spawn`: Gives permission to run the command /spawntamablefox. Default: `op`
* `tamablefoxes.tame`: Gives the player the ability to tame a fox. Default: `Everybody`
* `tamablefoxes.tame.unlimited`: Lets players bypass the tame limit. Default: `op`
* `tamablefoxes.tame.anywhere`: Lets players bypass the banned worlds in config.yml (so they can tame in any world). Default: `op`
* `tamablefoxes.givefox.give.others`: Allows the player to give another players fox to a player with /givefox. This will ignore if the other receiving has the `tamablefoxes.givefox.receive` permission. Default: `op`
* `tamablefoxes.givefox.give`: Gives the player the ability to give foxes to other players with /givefox. Default: `Everybody`
* `tamablefoxes.givefox.receive`: Gives the player the ability to receive foxes from other players from /givefox. Default: `Everybody`

<br>

![foxes sleeping](Screenshots/foxes-sleeping-with-player.png)
![foxes sitting player holding sword](Screenshots/foxes-sitting-sword.png)
![foxes with baby looking at player](Screenshots/foxes-baby-looking-at-player.png)
![giving fox totem](Screenshots/giving-fox-item.gif)
![fox leaping towards chicken](Screenshots/fox-pouncing.gif)

## Building
Only Minecraft 26.3 is supported, so building is straightforward:
* **JDK 25** or newer (26.3 is compiled with Java 25).
* The `org.spigotmc:spigot:26.3-R0.1-SNAPSHOT` dependency is downloaded automatically from the CodeMC NMS repository. Since Minecraft 26.1+ is no longer obfuscated, no remapping (SpecialSource) is needed anymore.

Then just run:
```sh
mvn clean package
```
