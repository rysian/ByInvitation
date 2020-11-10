package com.rysian.ByInvatation;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

public class playerListener implements Listener
{
    @EventHandler
    public void onPlayerJoin(AsyncPlayerPreLoginEvent e)
    {
        userManager user = new userManager(e.getUniqueId());
        user.load();
        user.save(0);

    }

}
