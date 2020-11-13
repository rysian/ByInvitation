# ByInvitation
Whitelisted server with a user invite system!
 
This plugin allows for players on a whitelisted server to add other players with an invitation system similar to what's used for private forums/groups or closed betas. This lets current whitelisted players add their friends easily without admin intervention, while admins have peace of mind knowing the whitelisting is done on a finite basis. Users save time while the administrative overhead regrading whitelisting is reduced, saving time for everyone!

Current players on a server can be given a certain number of "invites" that can be used to invite other players not currently on the whitelist by entering their username. The invited player gets added onto the whitelist, while an "invite" is consumed for the person who sent the invite. The invite sender can add any username they wish, provided they have invites available to give. Upon depletion, the invite sender can no longer invite anyone else unless given more invites.

## Use Cases:
* Pairing this plugin with AutoRank (https://www.spigotmc.org/resources/autorank.3239/) or a similar plugin on a whitelisted server so that players who join are given 5 invites after 15 hours of total non-AFK playtime on the server. This deters bad players or greifers just looking to cause trouble, while allowing for legitimate players who enjoy spending time on the server to invite others, where they will likely invite people they trust. This can grow the server's userbase with a lower change of dealing with greifers.

* Incentivize players on a large, whitelisted server to donate in exchange for a certain number of invites that they can use to bring others on board. This could be paired in conjunction with a website or Discord bot that accepts applications for a whitelisted server so that invited users can skip the application process and jump straight on.

## User Commands:
Command | Description
------------ | -------------
*/winvite* | Display info screen.
*/winvite bal* | Display the current number of invites you can give.
*/winvite inv <username>* | Invites a player onto the server, using 1 invite of the issuer.

## Admin Commands:
Command | Description
------------ | -------------
*/winvite bal <username>* | Display the current number of invites of a given user.
*/winvite give <username> <num>* | Give a user a set number of invites in addition to any they may have currently.
*/winvite set <username> <num>* | Overwrite the current balance of invites of a user to a new set value. (Ex. resetting the invite balance of a player to 0).

## Installation:

Download the plugin and add to the server’s plugin folder. Upon next startup, a plugin folder is created.

## Permissions:
The default behavior is for server ops to have admin commands and everyone else having user command access. In case a permissions system is used, the following nodes may be used:
Permission | Description
------------ | -------------
*winvite.user* | Grants access to the user commands.
*winvite.\** | Grants access to the user and admin commands.

## Compile Instructions
Ensure Maven is installed. Clone this repo, and run
```mvn clean install```.
This outputs the plugin .jar file within the target folder.
