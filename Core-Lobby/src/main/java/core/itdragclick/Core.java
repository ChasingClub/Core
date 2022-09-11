package core.itdragclick;

//import core.itdragclick.API.ChairsConfig;
//import core.itdragclick.API.PlayerSitData;
//import core.itdragclick.API.SitUtils;
import core.itdragclick.Commands.*;
import core.itdragclick.Commands.Core.core;
import core.itdragclick.events.*;
import org.bukkit.ChatColor;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerResourcePackStatusEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import org.jetbrains.annotations.NotNull;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

import static core.itdragclick.API.Prefix.SetTeamPrefix;

public class Core extends JavaPlugin implements Listener, CommandExecutor {
    public static HashMap<String, String> anti = new HashMap<>();
    public static Set<UUID> sitting = new HashSet<>();
    public static HashMap<String, Integer> chatcooldowns = new HashMap<>();
    public static HashMap<String, Integer> cmdcooldowns = new HashMap<>();
    public static ArrayList<String> build = new ArrayList<>();
    public static HashMap<String, Integer> resoruce = new HashMap<>();
    public static Core plugin;
    public FileConfiguration config = this.getConfig();
    public String pack = config.getString("pack");
    public String hash = config.getString("hash");
    public static String Webhook = "true";
    public static String webhookURL = "https://discord.com/api/webhooks/1001889150129152150/L6a_4y0kUKtP_OJ-JO2wnP--1ZBduqdhge4EcgAkZgmF-8bevBC7hUBxF9JVvLQDalYy";

    public static void msgconsole(String message){
        Bukkit.getConsoleSender().sendMessage(message);
    }
    public static String PLname = (ChatColor.YELLOW + "C" + ChatColor.LIGHT_PURPLE + "C" + ChatColor.DARK_GRAY + " » "+ChatColor.GRAY);
    @Override
    public void onEnable() {
//        createNPC("ItDragClick");
//        createNPC("Pinont_");
//        createNPC("RichterYT");
        for (Player p : getServer().getOnlinePlayers()){
            if (p.getGameMode() != GameMode.CREATIVE && p.getGameMode() != GameMode.SPECTATOR){
                World SessionWorld = Bukkit.getServer().getWorld("Lobby");
                Location SessionWorldSpawn = new Location(SessionWorld, 100.5, 101, 100.5, 180f, 0f);
                p.teleport(SessionWorldSpawn);
                p.getInventory().clear();
                getserverselect(p);
            }
            p.sendMessage(PLname+"Core plugin have been reloaded!");
        }
        // Set Normal TimeZone
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Bangkok"));
        // Add HashMap
        anti.put("8a490d62-98bd-4f57-b4a6-e4738b0beb96", "1");anti.put("0ab56496-71f6-4205-8e16-ec21dd7bfd5e", "2");anti.put("30c8f2de-9dc6-450c-bc31-4c20db77a29b", "3");
        // Add Arraylist
        File file = new File(getDataFolder() + File.separator + "config.yml"); //This will get the config file

        if (!file.exists()){ //This will check if the file exist
            getConfig().options().copyDefaults(true); //function to check the important settings
            saveConfig(); //saves the config
            reloadConfig(); //reloads the config
        }
        // Register Commands
        getCommand("feed").setExecutor(new feed());
        getCommand("heal").setExecutor(new health());
        getCommand("gmc").setExecutor(new gmc());
        getCommand("gma").setExecutor(new gma());
        getCommand("gms").setExecutor(new gms());
        getCommand("gmsp").setExecutor(new gmsp());
        getCommand("spawn").setExecutor(new spawn());
        getCommand("earape").setExecutor(new earape());
        getCommand("crash").setExecutor(new crash());
        getCommand("b").setExecutor(new build());
        getCommand("fling").setExecutor(new fling());
        getCommand("kaboom").setExecutor(new kaboom());
        getCommand("fly").setExecutor(new fly());
        getCommand("sudo").setExecutor(new sudo());
        getCommand("core").setExecutor(new core());
        getCommand("test").setExecutor(this);
        // Register Events
        getServer().getPluginManager().registerEvents(new slotitem(), this);
        getServer().getPluginManager().registerEvents(new onjoin(), this);
        getServer().getPluginManager().registerEvents(new canceldrops(), this);
        getServer().getPluginManager().registerEvents(new LeaveClear(), this);
        getServer().getPluginManager().registerEvents(new JoinMessage(), this);
        getServer().getPluginManager().registerEvents(new cancelevent(), this);
        getServer().getPluginManager().registerEvents(new interactblock(), this);
        getServer().getPluginManager().registerEvents(new Respawn(), this);
        getServer().getPluginManager().registerEvents(new BADWords(), this);
        getServer().getPluginManager().registerEvents(new openmenu(), this);
        getServer().getPluginManager().registerEvents(new Bhopping(), this);
        getServer().getPluginManager().registerEvents(this,this);
        getServer().getPluginManager().registerEvents(new SitEvent(), this);
        getServer().getPluginManager().registerEvents(new adminfireball(), this);
        getServer().getPluginManager().registerEvents(new Chat(), this);
        //IDK
        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        // Plugin startup logic
        msgconsole(PLname+"has been enabled");
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
            }catch (IOException e){
                getLogger().severe(Arrays.toString(e.getStackTrace()));
            }
        }
        World world = Bukkit.getServer().getWorld("Lobby");
        if (world != null) {
            world.setDifficulty(Difficulty.PEACEFUL);
        }
        new BukkitRunnable()
        {
            @Override
            public void run(){
                adminregion();
            }
        }.runTaskTimer(this, 0, 5);
        new BukkitRunnable()
        {
            @Override
            public void run(){
//                checktexture();
                TabList();
                Chat();
                CMD();
            }
        }.runTaskTimer(this, 0, 20);
    }
//    public Player[] checktexture(){
//        Player[] players = new Player[Bukkit.getOnlinePlayers().size()];
//        Bukkit.getOnlinePlayers().toArray(players);
//        for (Player p : players){
//            if (p.getResourcePackStatus() == PlayerResourcePackStatusEvent.Status.ACCEPTED){
//                return players;
//            }else if (p.getResourcePackStatus() == null){
//                p.setResourcePack(pack, hash, true);
//                if (resoruce.containsKey(p.getName())){
//                    return players;
//                }
//                resoruce.put(p.getName(), 3);
//            }
//        }
//        return players;
//    }
//    public void onDelay(){
//        HashMap<String, Integer> temp = new HashMap<>();
//        for (String plrname : resoruce.keySet()){
//            int timer = resoruce.get(plrname) - 1;
//            System.out.println(resoruce);
//            Player p = Bukkit.getPlayer(plrname);
//            if (timer == 0 && p != null && p.getResourcePackStatus() == null){
//                p.kickPlayer("You need to install resource pack.");
//            }
//            if (timer > 0){
//                temp.put(plrname, timer);
//            }
//        }
//        resoruce = temp;
//    }
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
    public static void getserverselect(Player p){

        PlayerInventory inv = p.getInventory();

        ItemStack compass = new ItemStack(Material.COMPASS, 1);

        ItemMeta im = compass.getItemMeta();

        ArrayList<String> lore = new ArrayList<>();
        lore.add("§7");
        lore.add("§7Right Click to open the Server Selector!");
        im.setDisplayName("§a§lServer Selector §7(Right Click)");
        im.setLore(lore);

        compass.setItemMeta(im);
        if (p.getGameMode() != GameMode.CREATIVE && p.getGameMode() != GameMode.SPECTATOR){
            inv.clear();
            inv.setItem(4, compass);
        }
    }
    public String getDate(){
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Bangkok"));
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        return sdf.format(cal.getTime());
    }
    public void adminregion(){
        Cuboid cuboid = new Cuboid(Bukkit.getServer().getWorld("Lobby") ,82, 109, 74, 92, 112, 47);
        for (Player ap : getServer().getOnlinePlayers()){
            if (cuboid.contains(ap.getLocation()) && !(ap.hasPermission("rank.admin"))){
                ap.sendMessage(PLname+ChatColor.RED+"You don't have permission to enter this room!");
                ap.teleport(new Location(Bukkit.getServer().getWorld("Lobby"), 94.5, 109, 51.5, 90f, 0f));
            }
        }
    }
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
//    private final SitUtils utils = new SitUtils(this);
//    public SitUtils getSitUtils() {
//        return utils;
//    }
//    private final PlayerSitData psitdata = new PlayerSitData(this);
//
//    public PlayerSitData getPlayerSitData() {
//        return psitdata;
//    }
//    private final ChairsConfig config2 = new ChairsConfig(this);
//
//    public ChairsConfig getChairsConfig() {
//        return config2;
//    }
}
