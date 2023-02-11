package cc;

import cc.cmd.randomTeleport;
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
    public String worldspawn;
    public double xspawn;
    public double yspawn;
    public double zspawn;
    public float spawnyaw;
    public float spawnpitch;
    public Location spawnloc;
    private static Core plugin;

    // reload config
    public void reload() {
        // reload
        cfile = new File(getDataFolder(), "config.yml");
        config = YamlConfiguration.loadConfiguration(cfile);
        worldspawn = config.getString("spawn.world");
        xspawn = config.getDouble("spawn.x");
        yspawn = config.getDouble("spawn.y");
        zspawn = config.getDouble("spawn.z");
        spawnyaw = config.getInt("spawn.yaw");
        spawnpitch = config.getInt("spawn.pitch");
        spawnloc = new Location(Bukkit.getServer().getWorld(worldspawn), xspawn+0.5, yspawn, zspawn+0.5, spawnyaw, spawnpitch);
    }

    @Override
    public void onEnable() {

        getLogger().info(ChatColor.GREEN + "SMP CORE" + ChatColor.YELLOW + " is loading...");

        plugin = this;
        getCommand("rtp").setExecutor(new randomTeleport());

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static Core getPlugin() {
        return plugin;
    }

}