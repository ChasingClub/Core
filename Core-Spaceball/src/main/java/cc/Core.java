package cc;

import cc.Machanic.BouncingArrow;
import cc.Machanic.hopDown;
import cc.commands.feed;
import cc.commands.heal;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class Core extends JavaPlugin {

    public static String Plname = (ChatColor.YELLOW + "C" + ChatColor.LIGHT_PURPLE + "C" + ChatColor.DARK_GRAY + " » " + ChatColor.GRAY);

    @Override
    public void onEnable() {

        // Config
        File file = new File(getDataFolder() + File.separator + "config.yml"); //This will get the config file

        if (!file.exists()) { //This will check if the file exist
            getConfig().options().copyDefaults(true); //function to check the important settings
            saveConfig(); //saves the config
            reloadConfig(); //reloads the config
        }

        // EventLoad
        getServer().getPluginManager().registerEvents(new hopDown(), this);
        getServer().getPluginManager().registerEvents(new BouncingArrow(), this);
//        getServer().getPluginManager().registerEvents(new lemonJuice(), this);
//        getServer().getPluginManager().registerEvents(this, this);

        // Comamnd Load
        getCommand("heal").setExecutor(new heal());
        getCommand("feed").setExecutor(new feed());

        Bukkit.getLogger().info(Plname + "Plugin Loaded");

    }

    @Override
    public void onDisable() {
        Bukkit.getLogger().info(Plname + "Shutting down Plugin");
    }
}