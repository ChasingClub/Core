package cc;

import cc.cmd.randomTeleport;
import cc.cmd.spawn;
import cc.events.headDrops;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class Core extends JavaPlugin {

    public FileConfiguration config;
    public File cfile;
    private static Core plugin;

    // reload config
    public void reload() {

        // reload
        cfile = new File(getDataFolder(), "config.yml");
        config = YamlConfiguration.loadConfiguration(cfile);

    }

    @Override
    public void onEnable() {

        getLogger().info(ChatColor.GREEN + "SMP CORE" + ChatColor.YELLOW + " is loading...");

        File file = new File(getDataFolder() + File.separator + "config.yml"); //This will get the config file

        if (!file.exists()) { //This will check if the file exist
            getConfig().options().copyDefaults(true); //function to check the important settings
            saveConfig(); //saves the config
            reloadConfig(); //reloads the config
        }

        plugin = this;
        getCommand("rtp").setExecutor(new randomTeleport());
        getCommand("spawn").setExecutor(new spawn());
        getServer().getPluginManager().registerEvents(new headDrops(), this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static Core getPlugin() {
        return plugin;
    }

}