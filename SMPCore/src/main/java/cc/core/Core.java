package cc.core;

import cc.core.SQliteManager.SQLite;
import cc.core.Utilities.TabCompletion;
import cc.core.command.*;
import cc.core.events.playerDealDamage;
import cc.core.events.playerDeath;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;
import java.util.Objects;

import static cc.core.SQliteManager.YamlStorage.generateYMLFolder;

public final class Core extends JavaPlugin {
    private final SQLite Sqlite = new SQLite(this);
    public static Core plugin;

    public Core() {
        plugin = this;
    }
    @Override
    public void onEnable() {
        // Plugin startup logic
        Sqlite.loadSQLite();
        registerEvents();
        registerCommands();
        generateYMLFolder();
        loadConfig();
    }
    private void registerCommands() {
        Objects.requireNonNull(getCommand("team")).setTabCompleter(new TabCompletion());
        Objects.requireNonNull(getCommand("team")).setExecutor(new team());
        Objects.requireNonNull(getCommand("rtp")).setExecutor(new randomTeleport());
        Objects.requireNonNull(getCommand("spawn")).setExecutor(new spawn());
        Objects.requireNonNull(getCommand("bed")).setExecutor(new bed());
        Objects.requireNonNull(getCommand("pvp")).setExecutor(new pvp());
    }
    private void registerEvents() {
        Objects.requireNonNull(getServer().getPluginManager()).registerEvents(new playerDeath(), this);
        getServer().getPluginManager().registerEvents(new playerDealDamage(), this);
    }
    private void loadConfig() {
        getConfig().options().copyDefaults();
        saveDefaultConfig();
    }
    public static void registerEnchant(Enchantment enchantment) {
        boolean registered = true;
        try {
            Field f = Enchantment.class.getDeclaredField("acceptingNew");
            f.setAccessible(true);
            f.set(null, true);
            Enchantment.registerEnchantment(enchantment);
        } catch (Exception e) {
            registered = false;
            e.printStackTrace();
        } if (registered) {
            // registered
        }
    }
    @Override
    public void onDisable() {
        // Plugin shutdown logic
        Sqlite.unloadSQLite();
    }
}
