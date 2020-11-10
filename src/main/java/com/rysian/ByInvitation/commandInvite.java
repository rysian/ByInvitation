package com.rysian.ByInvitation;

import org.bukkit.Bukkit;
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
                sender.sendMessage(new String[]{"You have", Integer.toString(invitesLeft), "left!"});


            }

        }
        //If user types  /winvite inv *username*
        else if (args.length == 2)
        {
             if(args[0].equalsIgnoreCase("inv"))
            {
                Player commander = (Player) sender;
                userManager user = new userManager(commander.getUniqueId());
                if (user != null) {
                    user.load();
                    int invitesLeft = user.getConfig().getInt("inviteCount");
                    if (invitesLeft >= 1) {
                        user.save(--invitesLeft);
                        String playerName = args[1];

                        sender.sendMessage(playerName);

                        Bukkit.getOfflinePlayer(resolvePlayer(playerName)).setWhitelisted(true);
                        Bukkit.reloadWhitelist();
                    } else {
                        sender.sendMessage("You have no available invites!");
                    }
                }
                else {
                    sender.sendMessage("User doesn't exist!");
                }
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
        Player player = Bukkit.getServer().getPlayer(playerName);
        return player.getUniqueId();
    }


}
