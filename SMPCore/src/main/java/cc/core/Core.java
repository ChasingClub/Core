package cc.core;

import cc.core.SQliteManager.SQLite;
import cc.core.Utilities.TabCompletion;
import cc.core.artifacts.bookOfFuture;
import cc.core.command.*;
import cc.core.enchants.bouncing;
import cc.core.enchants.dash;
import cc.core.enchants.phantomArrow;
import cc.core.events.HeartUnlocker;
import cc.core.events.playerDealDamage;
import cc.core.events.playerDeath;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Objects;

import static cc.core.SQliteManager.YamlStorage.generateYMLFolder;

public final class Core extends JavaPlugin {
    private final SQLite Sqlite = new SQLite(this);
    public static Core plugin;
    public static dash dash;
    public static phantomArrow phantomArrow;
    public static bouncing bouncing;
    public static String fixer = "§r§7";

    public Core() {
        plugin = this;
    }
    @Override
    public void onEnable() {

        // set Enchantments namespaces
        dash = new dash("dash");
        phantomArrow = new phantomArrow("homing");
        bouncing = new bouncing("bouncing");


        Sqlite.loadSQLite();
        registerEvents();
        registerCommands();
        registerEnchant(dash);
        registerEnchant(phantomArrow);
        registerEnchant(bouncing);
        generateYMLFolder();
        loadConfig();

    }

    @Override
    public void onDisable() {

        unregisterEnchant(dash);
        unregisterEnchant(phantomArrow);
        unregisterEnchant(bouncing);
        Sqlite.unloadSQLite();

    }

    private void registerCommands() {
        Objects.requireNonNull(getCommand("team")).setTabCompleter(new TabCompletion());
        Objects.requireNonNull(getCommand("team")).setExecutor(new team());
        Objects.requireNonNull(getCommand("rtp")).setExecutor(new randomTeleport());
        Objects.requireNonNull(getCommand("spawn")).setExecutor(new spawn());
        Objects.requireNonNull(getCommand("bed")).setExecutor(new bed());
        Objects.requireNonNull(getCommand("pvp")).setExecutor(new pvp());
        Objects.requireNonNull(getCommand("srcTake")).setExecutor(new secretTake());
    }
    private void registerEvents() {
        Objects.requireNonNull(getServer().getPluginManager()).registerEvents(new playerDeath(), this);
        getServer().getPluginManager().registerEvents(new playerDealDamage(), this);
        getServer().getPluginManager().registerEvents(dash, this);
        getServer().getPluginManager().registerEvents(phantomArrow, this);
        getServer().getPluginManager().registerEvents(bouncing, this);
        getServer().getPluginManager().registerEvents(new HeartUnlocker(), this);
        getServer().getPluginManager().registerEvents(new bookOfFuture(), this);
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
            Bukkit.getLogger().info("Successfully registered enchantment " + enchantment.getName());
        }
    }

    private void unregisterEnchant(Enchantment enchantment) {
        try {
            Field keyField = Enchantment.class.getDeclaredField("byKey");
            keyField.setAccessible(true);
            @SuppressWarnings("unchecked")
            HashMap<NamespacedKey, Enchantment> byKey = (HashMap<NamespacedKey, Enchantment>) keyField.get(null);
            if (byKey.containsKey(enchantment.getKey())) {
                byKey.remove(enchantment.getKey());
            }

            Field nameField = Enchantment.class.getDeclaredField("byName");
            nameField.setAccessible(true);
            @SuppressWarnings("unchecked")
            HashMap<String, Enchantment> byName = (HashMap<String, Enchantment>) nameField.get(null);
            if (byName.containsKey(enchantment.getName())) {
                byName.remove(enchantment.getName());
            }
        } catch (Exception ignored) {
            Bukkit.getLogger().info(ignored.getMessage());
        }
    }
}
