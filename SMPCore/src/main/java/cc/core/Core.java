package cc.core;

import cc.core.SQliteManager.SQLite;
import cc.core.command.TeamCommand;
import cc.core.command.randomTeleport;
import cc.core.command.spawn;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;
import java.util.Objects;

public final class Core extends JavaPlugin {
    private final SQLite Sqlite = new SQLite(this);
    private Connection connection;
    public static Core plugin;
    public Core() {
        plugin = this;
    }
    @Override
    public void onEnable() {
        // Plugin startup logic
        Sqlite.loadSQLite();
        registerCommands();
        loadConfig();
    }
    private void registerCommands() {
        Objects.requireNonNull(getCommand("team")).setExecutor(new TeamCommand());
        Objects.requireNonNull(getCommand("rtp")).setExecutor(new randomTeleport());
        Objects.requireNonNull(getCommand("spawn")).setExecutor(new spawn());
    }
    private void loadConfig() {
        getConfig().options().copyDefaults();
        saveDefaultConfig();
        int a = getConfig().getInt("border");
        Bukkit.getLogger().info(String.valueOf(a));
    }
    @Override
    public void onDisable() {
        // Plugin shutdown logic
        Sqlite.unloadSQLite();
    }
}
