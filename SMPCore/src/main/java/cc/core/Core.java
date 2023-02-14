package cc.core;

import cc.core.SQliteManager.SQLite;
import cc.core.command.TeamCommand;
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
    }
    private void registerCommand() {
        Objects.requireNonNull(this.getCommand("team")).setExecutor(new TeamCommand());
    }
    @Override
    public void onDisable() {
        // Plugin shutdown logic
        Sqlite.unloadSQLite();
    }
}
