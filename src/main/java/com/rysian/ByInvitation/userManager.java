package com.rysian.ByInvitation;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.UUID;

public class userManager
{
    private UUID uuid;
    private byInvitation plugin = byInvitation.getPlugin(byInvitation.class);

    private File file;
    private FileConfiguration config;

    public userManager(UUID uuid)
    {
        this.uuid = uuid;
    }

    public userManager(UUID uuid, File file, FileConfiguration config)
    {
        this.uuid = uuid;
        this.file = file;
        this.config = config;
    }

    public void load()
    {

        File folder = new File(plugin.getDataFolder(), "players");

        if (!folder.exists())
        {
            folder.mkdirs();
        }

        this.file = new File(folder, this.uuid.toString() + ".yml");
        if (!this.file.exists())
        {
            try
            {
                //Create a new user file, load config and write a 0 invite count.
                this.file.createNewFile();
                this.config = YamlConfiguration.loadConfiguration(this.file);
                save(0);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        else
        {
            //User file exists, just load config
            this.config = YamlConfiguration.loadConfiguration(this.file);
        }

    }

    public File getFile()
    {
        return this.file;
    }

    public FileConfiguration getConfig()
    {
        return this.config;
    }

    public void save(int inviteCount)
    {
        try
        {
            this.config.set("inviteCount", inviteCount);
            this.config.save(this.file);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}


