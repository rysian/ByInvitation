package com.rysian.ByInvatation;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.event.player.PlayerJoinEvent;
public class byInvatation extends JavaPlugin
{

    public void onEnable()
    {
       // this.getCommand("invite").setExecutor(new commandInvite());
        getServer().getPluginManager().registerEvents(new playerListener(), this);
    }


}
