package com.rysian.ByInvitation;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

import java.io.IOException;
import java.net.URL;

import org.bukkit.craftbukkit.libs.org.apache.commons.io.IOUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;

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
             if(args[0].equalsIgnoreCase("inv"))
            {
                Player commander = (Player) sender;
                userManager user = new userManager(commander.getUniqueId());
                user.load();
                int invitesLeft = user.getConfig().getInt("inviteCount");
                if (invitesLeft >= 1)
                {


                    String playerName = args[1];
                    UUID playerAdd = resolvePlayer(playerName);
                    if(!playerAdd.toString().isEmpty())
                    {
                        if(Bukkit.getOfflinePlayer(playerAdd).isOnline()) {
                            if (Bukkit.getServer().getWhitelistedPlayers().contains(Bukkit.getOfflinePlayer(playerAdd)))
                                sender.sendMessage("User already whitelisted!");
                        }
                        else {
                            user.save(--invitesLeft);
                            sender.sendMessage(playerName);
                            Bukkit.getOfflinePlayer(playerAdd).setWhitelisted(true);
                            Bukkit.reloadWhitelist();
                        }
                    }

                }
                else
                {
                    sender.sendMessage("Invalid user!");
                }

                } else {
                    sender.sendMessage("You have no available invites!");
                }
        }


        //User types /winvite give *username* *number of invites*
        else if (args.length == 3)
        {
            if(args[0].equalsIgnoreCase("give"))
            {
                String playerName = args[1];
                userManager user = new userManager(resolvePlayer(playerName));
                user.load();

            }
        }


        // If the player (or console) uses our command correct, we can return true
        return true;


    }

    private UUID resolvePlayer(String playerName)
    {
        OfflinePlayer player = Bukkit.getServer().getOfflinePlayer(playerName);
        return player.getUniqueId();
    }


}
