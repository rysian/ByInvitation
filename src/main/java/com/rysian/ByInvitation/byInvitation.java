package com.rysian.ByInvitation;
import org.bukkit.plugin.java.JavaPlugin;

public class byInvitation extends JavaPlugin
{

    public void onEnable()
    {
        this.getCommand("winvite").setExecutor(new commandInvite());
        getServer().getPluginManager().registerEvents(new playerListener(), this);
    }


}
