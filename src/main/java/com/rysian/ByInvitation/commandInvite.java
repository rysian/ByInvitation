package com.rysian.ByInvitation;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;



public class commandInvite implements CommandExecutor
{
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        //If user types /winvite bal
        if(args.length == 1)
        {
            if(args[0].equalsIgnoreCase("bal"))
            {
                Player commander = (Player)sender;
                userManager user = new userManager(commander.getUniqueId());
                user.load();
                int invitesLeft = user.getConfig().getInt("inviteCount");
                sender.sendMessage("You have " + invitesLeft + " invite(s) left!");


            }

        }
        //If user types  /winvite inv *username*
        else if (args.length == 2)
        {
            //Account for invalid usernames, users that have already been added
             if(args[0].equalsIgnoreCase("inv")) {
                 Player commander = (Player) sender;
                 userManager user = new userManager(commander.getUniqueId());
                 user.load();
                 int invitesLeft = user.getConfig().getInt("inviteCount");
                 if (invitesLeft >= 1)
                 {
                     String playerName = args[1];
                     UUID playerAdd = resolvePlayer(playerName);
                     if (!playerAdd.toString().isEmpty()) {
                         if (Bukkit.getOfflinePlayer(playerAdd).isOnline()) {
                             if (Bukkit.getServer().getWhitelistedPlayers().contains(Bukkit.getOfflinePlayer(playerAdd)))
                                 sender.sendMessage("User already whitelisted!");
                         } else {
                             user.save(--invitesLeft);
                             Bukkit.getOfflinePlayer(playerAdd).setWhitelisted(true);
                             Bukkit.reloadWhitelist();
                             sender.sendMessage(playerName + " has been invited!");
                         }
                     }

                 }
                 else {
                     sender.sendMessage("You have no invites available.");
                 }

             }
             else
                 return false;
        }


        //User types /winvite give *username* *number of invites*
        else if (args.length == 3)
        {
            if(args[0].equalsIgnoreCase("give"))
            {
                String playerName = args[1];
                if(Bukkit.getServer().getWhitelistedPlayers().contains(Bukkit.getOfflinePlayer(args[1]))) {
                    userManager user = new userManager(resolvePlayer(playerName));
                    user.load();
                    try {
                        int currentInviteBal = user.getConfig().getInt("inviteCount");
                        user.save((Integer.parseInt(args[2]) + currentInviteBal));
                        sender.sendMessage(args[2] + " invite(s) have been given to " + args[1]);
                    } catch(NumberFormatException | NullPointerException e) {
                        return false;
                    }
                }
                else
                {
                    sender.sendMessage("Invalid user.");
                }

            }
            else if(args[0].equalsIgnoreCase("set"))
            {
                String playerName = args[1];
                if(Bukkit.getServer().getWhitelistedPlayers().contains(Bukkit.getOfflinePlayer(args[1]))) {
                    userManager user = new userManager(resolvePlayer(playerName));
                    user.load();
                    try {
                        user.save(Integer.parseInt(args[2]));
                        sender.sendMessage(args[1] + "'s invite(s) has been set to " + args[2]);
                    } catch(NumberFormatException | NullPointerException e) {
                        return false;
                    }
                }
                else
                {
                    sender.sendMessage("Invalid user.");
                }

            }
            else
                return false;
        }
        else
            return false;

        // If the player (or console) uses our command correctly, we can return true.
        return true;


    }

    private UUID resolvePlayer(String playerName)
    {

        OfflinePlayer player = Bukkit.getServer().getOfflinePlayer(playerName);
        return player.getUniqueId();

    }


}
