package core.itdragclick;

import core.itdragclick.Commands.*;
import core.itdragclick.Commands.Core.core;
import core.itdragclick.Utils.Cuboid;
import core.itdragclick.Utils.Database;
import core.itdragclick.Utils.DiscordWebhook;
import core.itdragclick.events.*;
import core.itdragclick.hook.PlaceholderExpansion;
import org.bukkit.ChatColor;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

import static core.itdragclick.API.Prefix.SetTeamPrefix;
import static core.itdragclick.Utils.Utils.*;

public class Core extends JavaPlugin implements Listener, CommandExecutor {
    public static String PLname = PluginName;
    public FileConfiguration config;
    public File cfile;
    public String SQL_username;
    public String SQL_password;
    public String SQL_ip;
    public String SQL_database;
    public String worldspawn;
    public double xspawn;
    public double yspawn;
    public double zspawn;
    public float spawnyaw;
    public float spawnpitch;
    public Location spawnloc;
    public static HashMap<String, String> anti = new HashMap<>();
    public static Set<UUID> sitting = new HashSet<>();
    public static HashMap<String, Integer> chatcooldowns = new HashMap<>();
    public static HashMap<String, Integer> cmdcooldowns = new HashMap<>();
    public static ArrayList<String> build = new ArrayList<>();
    public static Core plugin;
    public static String Webhook = "true";
    public static String webhookURL = "https://discord.com/api/webhooks/1001889150129152150/L6a_4y0kUKtP_OJ-JO2wnP--1ZBduqdhge4EcgAkZgmF-8bevBC7hUBxF9JVvLQDalYy";
    public void reload() {
        cfile = new File(getDataFolder(), "config.yml");
        config = YamlConfiguration.loadConfiguration(cfile);
        worldspawn = config.getString("spawn.world");
        xspawn = config.getDouble("spawn.x");
        yspawn = config.getDouble("spawn.y");
        zspawn = config.getDouble("spawn.z");
        spawnyaw = config.getInt("spawn.yaw");
        spawnpitch = config.getInt("spawn.pitch");
        SQL_ip = config.getString("database.IP");
        SQL_username = config.getString("database.username");
        SQL_password = config.getString("database.password");
        SQL_database = config.getString("database.database");
        spawnloc = new Location(Bukkit.getServer().getWorld(worldspawn), xspawn+0.5, yspawn, zspawn+0.5, spawnyaw, spawnpitch);
    }
    public static void msgconsole(String message){
        Bukkit.getConsoleSender().sendMessage(message);
    }
    @Override
    public void onEnable() {
        File file = new File(getDataFolder() + File.separator + "config.yml"); //This will get the config file

        if (!file.exists()) { //This will check if the file exist
            getConfig().options().copyDefaults(true); //function to check the important settings
            saveConfig(); //saves the config
            reloadConfig(); //reloads the config
        }
        reload();
        for (Player p : getServer().getOnlinePlayers()) {
            if (p.getGameMode() != GameMode.CREATIVE && p.getGameMode() != GameMode.SPECTATOR) {
                p.teleport(spawnloc);
                if(p.hasPermission("rank.vip")) {
                    p.setAllowFlight(true);
                }
                p.getInventory().clear();
                getserverselect(p);
                getprofileitem(p);
                getcosmeticitem(p);
                getsettingsitem(p);
            }
            p.sendMessage(PLname + "Core plugin have been reloaded!");
        }
        // Set Normal TimeZone
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Bangkok"));
        // Add HashMap
        anti.put("8a490d62-98bd-4f57-b4a6-e4738b0beb96", "1");
        anti.put("0ab56496-71f6-4205-8e16-ec21dd7bfd5e", "2");
        anti.put("30c8f2de-9dc6-450c-bc31-4c20db77a29b", "3");

        // Register Commands
        getCommand("feed").setExecutor(new feed());
        getCommand("heal").setExecutor(new health());
        getCommand("gmc").setExecutor(new gmc());
        getCommand("gma").setExecutor(new gma());
        getCommand("gms").setExecutor(new gms());
        getCommand("gmsp").setExecutor(new gmsp());
        getCommand("spawn").setExecutor(new spawn(this));
        getCommand("setspawn").setExecutor(new setspawn(this));
        getCommand("enderchest").setExecutor(new enderchest());
        getCommand("earape").setExecutor(new earape());
        getCommand("crash").setExecutor(new crash());
        getCommand("b").setExecutor(new build());
        getCommand("fling").setExecutor(new fling());
        getCommand("kaboom").setExecutor(new kaboom());
        getCommand("fly").setExecutor(new fly());
        getCommand("sudo").setExecutor(new sudo());
        getCommand("core").setExecutor(new core(this));
        getCommand("ping").setExecutor(new ping());
        getCommand("test").setExecutor(this);
        // Register Events
        getServer().getPluginManager().registerEvents(new slotitem(), this);
        getServer().getPluginManager().registerEvents(new onjoin(this), this);
        getServer().getPluginManager().registerEvents(new canceldrops(), this);
        getServer().getPluginManager().registerEvents(new LeaveClear(), this);
        getServer().getPluginManager().registerEvents(new JoinMessage(), this);
        getServer().getPluginManager().registerEvents(new cancelevent(), this);
        getServer().getPluginManager().registerEvents(new interactblock(), this);
        getServer().getPluginManager().registerEvents(new Vanish(), this);
        getServer().getPluginManager().registerEvents(new Respawn(), this);
        getServer().getPluginManager().registerEvents(new BADWords(), this);
        getServer().getPluginManager().registerEvents(new openmenu(), this);
        getServer().getPluginManager().registerEvents(new Bhopping(), this);
        getServer().getPluginManager().registerEvents(this, this);
        getServer().getPluginManager().registerEvents(new SitEvent(), this);
        getServer().getPluginManager().registerEvents(new adminfireball(), this);
        getServer().getPluginManager().registerEvents(new Chat(), this);
        //IDK
        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        //PlaceholderAPI Register
        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            new PlaceholderExpansion().register();
        } else {
            msgconsole(ChatColor.RED + "PlaceholderAPI is not enabled!");
        }
        // Plugin startup logic
        msgconsole(PLname + "has been enabled");
        // Discord Webhook Started
        if (Webhook.equals("true")) {
            DiscordWebhook webhook = new DiscordWebhook(webhookURL);
            webhook.addEmbed(new DiscordWebhook.EmbedObject()
                    .setDescription("Server Started.")
                    .addField("Time", getDate(), false)
                    .addField("Server", "Lobby", false)
                    .setColor(Color.GREEN)
            );
            try {
                webhook.execute();
            } catch (IOException e) {
                getLogger().severe(Arrays.toString(e.getStackTrace()));
            }
        }
        World world = Bukkit.getServer().getWorld("Hub");
        if (world != null) {
            world.setDifficulty(Difficulty.PEACEFUL);
        }
        Database database = new Database(this);
        msgconsole(PLname + "database is loading....\n\n");
        msgconsole(ChatColor.GREEN + "███╗░░░███╗██╗░░░██╗░██████╗░██████╗░██╗░░░░░");
        msgconsole(ChatColor.GREEN + "████╗░████║╚██╗░██╔╝██╔════╝██╔═══██╗██║░░░░░");
        msgconsole(ChatColor.GREEN + "██╔████╔██║░╚████╔╝░╚█████╗░██║██╗██║██║░░░░░");
        msgconsole(ChatColor.GREEN + "██║╚██╔╝██║░░╚██╔╝░░░╚═══██╗╚██████╔╝██║░░░░░");
        msgconsole(ChatColor.GREEN + "██║░╚═╝░██║░░░██║░░░██████╔╝░╚═██╔═╝░███████╗");
        msgconsole(ChatColor.GREEN + "╚═╝░░░░░╚═╝░░░╚═╝░░░╚═════╝░░░░╚═╝░░░╚══════╝");
        msgconsole(ChatColor.YELLOW + "=----------------------------------=");
        msgconsole(PLname + "Connecting to database.");
        msgconsole(ChatColor.YELLOW + "=----------------------------------=");
        try {
            database.initializeDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
            msgconsole(ChatColor.RED + "Could not initialize database.");
            msgconsole(ChatColor.YELLOW + "=----------------------------------=");
        }
        msgconsole(PLname + "Connected to database.");
        msgconsole(ChatColor.YELLOW + "=----------------------------------=");
        new BukkitRunnable() {
            @Override
            public void run() {
                TabList();
                Chat();
                CMD();
                for (Player ap : Bukkit.getServer().getOnlinePlayers()) {
                    if (ap.getGameMode() != GameMode.CREATIVE && ap.getGameMode() != GameMode.SPECTATOR) {
                        ap.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 200000, 0, false, false));
                    } else {
                        ap.removePotionEffect(PotionEffectType.HUNGER);
                    }
                }
            }
        }.runTaskTimer(this, 0, 20);
        new BukkitRunnable() {
            @Override
            public void run() {
                onRankRoom();
            }
        }.runTaskTimer(this, 0, 1);
        new BukkitRunnable() {
            @Override
            public void run() {
                try {
                    database.getConnection();
                    msgconsole(ChatColor.YELLOW + "=----------------------------------=");
                    msgconsole(PLname + "Reconnecting to database.");
                } catch (SQLException e) {
                    e.printStackTrace();
                    msgconsole(ChatColor.RED + "Could not initialize database.");
                    msgconsole(ChatColor.YELLOW + "=----------------------------------=");
                    return;
                }
                msgconsole(PLname + "Reconnected to database.");
                msgconsole(ChatColor.YELLOW + "=----------------------------------=");
            }
        }.runTaskTimer(this, 0L, 2400L);
    }
    public void onRankRoom(){
        for(Player p : Bukkit.getServer().getOnlinePlayers()){
            Cuboid cuboid = new Cuboid(Bukkit.getWorld(worldspawn),15, 71, 8, 7, 74, -1);
            Cuboid cuboid2 = new Cuboid(Bukkit.getWorld(worldspawn),6, 74, 6, -6, 71, 14);
            Location loctp = new Location(Bukkit.getWorld(worldspawn),14, 71, 10,180f, 0f);
            if(cuboid.contains(p.getLocation()) && !p.hasPermission("rank.vip")){
                p.teleport(loctp);
                p.sendMessage("§9§lGuard §8» §c§nOnly VIP rank or higher can come in this room!");
                p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO, 10, 1);
            }
            if(cuboid2.contains(p.getLocation()) && !p.hasPermission("rank.vip")){
                p.teleport(loctp);
                p.sendMessage("§9§lGuard §8» §c§nOnly VIP rank or higher can come in this room!");
                p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO, 10, 1);
            }
        }
    }
    public void Chat(){
        HashMap<String, Integer> temp = new HashMap<>();
        for (String plrname : chatcooldowns.keySet()){
            int timer = chatcooldowns.get(plrname) - 1;
            if (timer >= 0){
                temp.put(plrname, timer);
            }
        }
        chatcooldowns = temp;
    }
    public void CMD(){
        HashMap<String, Integer> temp = new HashMap<>();
        for (String plrname : cmdcooldowns.keySet()){
            int timer = cmdcooldowns.get(plrname) - 1;
            if (timer >= 0){
                temp.put(plrname, timer);
            }
        }
        cmdcooldowns = temp;
    }
    public void TabList(){
        SetTeamPrefix();
    }
    public String getDate(){
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Bangkok"));
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        return sdf.format(cal.getTime());
    }
//    public void adminregion(){
//        Cuboid cuboid = new Cuboid(Bukkit.getServer().getWorld("Lobby") ,82, 109, 74, 92, 112, 47);
//        for (Player ap : getServer().getOnlinePlayers()){
//            if (cuboid.contains(ap.getLocation()) && !(ap.hasPermission("rank.admin"))){
//                ap.sendMessage(PLname+ChatColor.RED+"You don't have permission to enter this room!");
//                ap.teleport(new Location(Bukkit.getServer().getWorld("Lobby"), 94.5, 109, 51.5, 90f, 0f));
//            }
//        }
//    }
    @Override
    public void onDisable() {
        // IDK
        this.getServer().getMessenger().unregisterOutgoingPluginChannel(this);
        this.getServer().getMessenger().unregisterIncomingPluginChannel(this);
        // Plugin shutdown logic
        msgconsole(PLname+"has been disabled");
        // Webhook disabled
        DiscordWebhook webhook = new DiscordWebhook(webhookURL);
        webhook.addEmbed(new DiscordWebhook.EmbedObject()
                .setDescription("Server Stopped.")
                .addField("Time", getDate(), false)
                .addField("Server", "Lobby", false)
                .setColor(Color.GRAY)
        );
        try {
            webhook.execute();
        }catch (java.io.IOException e){
            getLogger().severe(Arrays.toString(e.getStackTrace()));
        }
    }
    @Override
    public boolean onCommand(@NotNull CommandSender sender, Command command, @NotNull String label, String[] args) {
        if (command.getName().equalsIgnoreCase("test")) {
            if (!sender.hasPermission("rank.owner") || !(sender instanceof Player)){return true;}
            Player p = (Player) sender;
            ByteArrayOutputStream b = new ByteArrayOutputStream();
            DataOutputStream out = new DataOutputStream(b);
            try {
                out.writeUTF("MessageRaw");
                out.writeUTF("ALL");
                out.writeUTF("{\"text\":\"Hello, World!\"}");
            } catch(Exception e) {
                e.printStackTrace();
            }

            p.sendPluginMessage(this, "BungeeCord", b.toByteArray());
        }
        return true;
    }
}
