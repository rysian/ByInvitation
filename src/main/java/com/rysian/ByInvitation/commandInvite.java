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
        if(args.length == 0)
            sender.sendMessage("ByInvitation v0.1 - Whitelisting via invitation! Type /help ByInvitation for a list of commands.");
        //If user types /winvite bal
        else if(args.length == 1)
        {
            if(args[0].equalsIgnoreCase("bal"))
            {
                if (sender instanceof Player)
                {
                    Player commander = (Player) sender;
                    userManager user = new userManager(commander.getUniqueId());
                    user.load();
                    int invitesLeft = user.getConfig().getInt("inviteCount");
                    sender.sendMessage("You have " + invitesLeft + " invite(s) left!");
                }
                else
                {
                    sender.sendMessage("Please run /winvite bal <player> when executing the command from the console");
                }


            }
            else
                return false;

        }
        //If user types  /winvite inv *username* or /winvite bal *username*
        else if (args.length == 2)
        {
            //Account for invalid usernames, users that have already been added
             if(args[0].equalsIgnoreCase("inv")) {


                 if (sender instanceof Player) {
                     Player commander = (Player) sender;
                     if (!commander.hasPermission("winvite.user")) {
                         sender.sendMessage("Insufficient permissions.");
                         return true;
                     }

                     userManager user = new userManager(commander.getUniqueId());
                     user.load();
                     int invitesLeft = user.getConfig().getInt("inviteCount");
                     if (invitesLeft >= 1) {
                         String playerName = args[1];
                         UUID playerAdd = resolvePlayer(playerName);
                         if (!playerAdd.toString().isEmpty()) {
                             if (Bukkit.getServer().getWhitelistedPlayers().contains(Bukkit.getOfflinePlayer(playerAdd)))
                                 sender.sendMessage("User already whitelisted!");
                             else {
                                 user.save(--invitesLeft);
                                 Bukkit.getOfflinePlayer(playerAdd).setWhitelisted(true);
                                 Bukkit.reloadWhitelist();
                                 sender.sendMessage(playerName + " has been invited!");
                             }
                         }

                     } else {
                         sender.sendMessage("You have no invites available.");
                     }
                 }
                 //Command executed from console
                 else
                 {
                     String playerName = args[1];
                     UUID playerAdd = resolvePlayer(playerName);
                     if (!playerAdd.toString().isEmpty()) {
                         if (Bukkit.getServer().getWhitelistedPlayers().contains(Bukkit.getOfflinePlayer(playerAdd)))
                             sender.sendMessage("User already whitelisted!");
                         else {
                             Bukkit.getOfflinePlayer(playerAdd).setWhitelisted(true);
                             Bukkit.reloadWhitelist();
                             sender.sendMessage(playerName + " has been invited!");
                         }
                     }
                 }

             }
             else if(args[0].equalsIgnoreCase("bal"))
             {

                 if (sender instanceof Player) {
                     Player commander = (Player) sender;
                     if (!commander.hasPermission("winvite.*"))
                         sender.sendMessage("Insufficient permissions.");
                     else if (Bukkit.getServer().getWhitelistedPlayers().contains(Bukkit.getOfflinePlayer(args[1]))) {
                         UUID playerCheck = resolvePlayer(args[1]);
                         userManager user = new userManager(playerCheck);
                         user.load();
                         int invitesLeft = user.getConfig().getInt("inviteCount");
                         sender.sendMessage(args[1] + " has " + invitesLeft + " invite(s) left.");
                     } else {
                         sender.sendMessage("Invalid user.");
                     }
                 }
                 //Running from console
                 else {
                     if (Bukkit.getServer().getWhitelistedPlayers().contains(Bukkit.getOfflinePlayer(args[1]))) {
                         UUID playerCheck = resolvePlayer(args[1]);
                         userManager user = new userManager(playerCheck);
                         user.load();
                         int invitesLeft = user.getConfig().getInt("inviteCount");
                         sender.sendMessage(args[1] + " has " + invitesLeft + " invite(s) left.");
                     } else {
                         sender.sendMessage("Invalid user.");
                     }
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

                if (sender instanceof Player) {
                    Player commander = (Player) sender;
                    if (!commander.hasPermission("winvite.*")) {
                        sender.sendMessage("Insufficient permissions.");
                        return true;
                    }
                }
                if(Bukkit.getServer().getWhitelistedPlayers().contains(Bukkit.getOfflinePlayer(args[1]))) {
                    String playerName = args[1];
                    userManager user = new userManager(resolvePlayer(playerName));
                    user.load();
                    try {
                        int newVal = Integer.parseInt(args[2]);
                        if(newVal < 0)
                            sender.sendMessage("Please specify an invite amount of zero or more.");
                        else {
                            int currentInviteBal = user.getConfig().getInt("inviteCount");
                            user.save(newVal + currentInviteBal);
                            sender.sendMessage(args[2] + " invite(s) have been given to " + args[1]);
                        }
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
                if (sender instanceof Player) {
                    Player commander = (Player) sender;
                    if (!commander.hasPermission("winvite.*")) {
                        sender.sendMessage("Insufficient permissions.");
                        return true;
                    }
                }
                if(Bukkit.getServer().getWhitelistedPlayers().contains(Bukkit.getOfflinePlayer(args[1]))) {
                    String playerName = args[1];
                    userManager user = new userManager(resolvePlayer(playerName));
                    user.load();
                    try {
                        int newVal = Integer.parseInt(args[2]);
                        if(newVal < 0)
                            sender.sendMessage("Please specify an invite amount of zero or more.");
                        else
                        {
                            user.save(newVal);
                            sender.sendMessage(args[1] + "'s invites has been set to " + args[2]);
                        }
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
