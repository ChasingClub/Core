package cc.core;

import cc.core.SQliteManager.SQLite;
import cc.core.command.TeamCommand;
import cc.core.command.randomTeleport;
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
        registerCommand();
        loadConfig();
    }
    private void registerCommand() {
        Objects.requireNonNull(this.getCommand("rtp")).setExecutor(new randomTeleport());
        Objects.requireNonNull(this.getCommand("team")).setExecutor(new TeamCommand());
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
