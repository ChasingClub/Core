package chasingclub.server.core;

import chasingclub.server.core.Utils.*;
import chasingclub.server.core.command.*;
import chasingclub.server.core.events.*;
import chasingclub.server.core.command.core.core;
import chasingclub.server.core.hook.PlaceholderExpansion;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.command.CommandExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.scheduler.BukkitRunnable;
import chasingclub.server.core.commandTabComplete.kitsTabable;
import vanish.itdragclick.api.vanish.VanishAPI;


import java.awt.Color;
import java.io.File;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

import static chasingclub.server.core.API.Prefix.SetTeamPrefix;
import static chasingclub.server.core.Utils.Database.*;
import static chasingclub.server.core.Utils.Database.UpdateKit;
import static chasingclub.server.core.Utils.Utils.*;
import static chasingclub.server.core.command.givekit.givekite;

public class Core extends JavaPlugin implements Listener, CommandExecutor {
    public FileConfiguration config;
    public File cfile;
    public String SQL_username;
    public String SQL_password;
    public String SQL_ip;
    public String SQL_database;
    public String SQL_username2;
    public String SQL_password2;
    public String SQL_ip2;
    public String SQL_database2;
    public String webhookURL;
    public String webhookURLAC;
    public String Webhook;
    public String worldspawn;
    public double xspawn;
    public double yspawn;
    public double zspawn;
    public float spawnyaw;
    public float spawnpitch;
    public Location spawnloc;
    public World world;
    public Cuboid SW;
    public Cuboid NW;
    public Cuboid NE;
    public Cuboid SE;
    public static Cuboid spawn = new Cuboid(Bukkit.getServer().getWorld("world"), 281, 199, 280, 230, 161, 232);
    public static HashMap<String, String> anti = new HashMap<String, String>();
    public static ArrayList<String> build = new ArrayList<String>();
    public static HashMap<String, HashMap<String, String>> inviteList = new HashMap<String, HashMap<String, String>>();
    public static ArrayList<String> lore = new ArrayList<String>();
    public static HashMap<Player, String> kits = new HashMap<Player, String>();
    public static HashMap<String, Boolean> maps = new HashMap<String, Boolean>();
    public static HashMap<String, String> playerinmap = new HashMap<String, String>();
    public static HashMap<String, Integer> chatcooldowns = new HashMap<>();
    public static HashMap<String, Integer> cmdcooldowns = new HashMap<>();
    public static HashMap<String, Integer> playerkits = new HashMap<String, Integer>();
    public static HashMap<String, Boolean> gotkit = new HashMap<>();
    public static HashMap<String,String> duel = new HashMap<String,String>();
    public static HashMap<String,Integer> dueltimer = new HashMap<String,Integer>();
    public static HashMap<String, String> games = new HashMap<String, String>();
    public static HashMap<String, Integer> combatList;
    public static HashMap<String, Integer> bhopcooldown = new HashMap<String, Integer>();
    public static HashMap<String, String> ingame = new HashMap<String, String>();
    public void reload() {
        cfile = new File(getDataFolder(), "config.yml");
        config = YamlConfiguration.loadConfiguration(cfile);
        webhookURL = config.getString("DiscordWebhookURL");
        webhookURLAC = config.getString("AntiCheatHook");
        Webhook = config.getString("Webhook");
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
        SQL_ip2 = config.getString("database2.IP");
        SQL_username2 = config.getString("database2.username");
        SQL_password2 = config.getString("database2.password");
        SQL_database2 = config.getString("database2.database");
        spawnloc = new Location(Bukkit.getServer().getWorld(worldspawn), xspawn+0.5, yspawn, zspawn+0.5, spawnyaw, spawnpitch);
        world = Bukkit.getServer().getWorld(worldspawn);
        SW = new Cuboid(world,245, 180, 272, 240, 174, 267);
        NW = new Cuboid(world,240, 180, 245, 245, 174, 240);
        NE = new Cuboid(world,267, 180, 240, 272, 174, 245);
        SE = new Cuboid(world,272, 180, 267, 267, 174, 272);
    }
    public static ArrayList<String> IsLaunched = new ArrayList<>();
    private ReflectionUtils reflectionUtils;
    public Core plugin;
    public static void msgconsole(String message){
        Bukkit.getConsoleSender().sendMessage(message);
    }
    // ENABLED SERVER
    public KitSQLAPI GetPlayerKit(String uuid) {
        try {
            return FindKitByPlayerUUID(uuid);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void onEnable() {
        // Config
        File file = new File(getDataFolder() + File.separator + "config.yml"); //This will get the config file

        if (!file.exists()){ //This will check if the file exist
            getConfig().options().copyDefaults(true); //function to check the important settings
            saveConfig(); //saves the config
            reloadConfig(); //reloads the config
        }
        reload();
        HotbarGui.UI_initialize();
        // Add String Arraylist/HashMap
        maps.put("Colosseum", true);maps.put("Beach", true);maps.put("Abyss", true);maps.put("Colosseum2", true);
        anti.put("8a490d62-98bd-4f57-b4a6-e4738b0beb96", "1");anti.put("0ab56496-71f6-4205-8e16-ec21dd7bfd5e", "2");anti.put("30c8f2de-9dc6-450c-bc31-4c20db77a29b", "3");
        playerkits.put("Default", 1);playerkits.put("Trident", 2);playerkits.put("Viking", 3);playerkits.put("Archer", 4);playerkits.put("Admin", 5);
        duel.put("invite", "1");duel.put("accept", "2");duel.put("reject", "3");
        //"NetheriteStack, DodgeBall, Paintball, ClassicIron, ClassicDiamond, OP, Crystal"
        games.put("netheritestack", "1");
        games.put("classicsword", "2");
        // add lore
        lore.add("§r");
        lore.add("§7Right Click to open kit selector menu!");
        // Set Normal TimeZone
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Bangkok"));

        // register Command
        getCommand("spawn").setExecutor(new spawn(this));
        getCommand("setspawn").setExecutor(new setspawn(this));
        getCommand("core").setExecutor(new core(this));
        getCommand("invslot").setExecutor(new invslot());
        getCommand("getkit").setExecutor(new kits());
        getCommand("feed").setExecutor(new feed());
        getCommand("heal").setExecutor(new health());
        getCommand("f").setExecutor(new f());
        getCommand("fade").setExecutor(new fade());
        getCommand("gmc").setExecutor(new gmc());
        getCommand("gma").setExecutor(new gma());
        getCommand("gms").setExecutor(new gms());
        getCommand("gmsp").setExecutor(new gmsp());
        getCommand("sudo").setExecutor(new sudo());
        getCommand("fling").setExecutor(new fling());
        getCommand("crash").setExecutor(new crash());
        getCommand("earape").setExecutor(new earape());
        getCommand("enderchest").setExecutor(new enderchest());
        getCommand("b").setExecutor(new build());
        getCommand("duel").setExecutor(new duel(this));
        getCommand("givekit").setExecutor(new givekit());
        getCommand("setkit").setExecutor(new setkit());
        getCommand("kaboom").setExecutor(new kaboom());
        getCommand("map").setExecutor(new map());
        getCommand("fly").setExecutor(new fly());
        getCommand("ping").setExecutor(new ping());
//        getCommand("perm").setExecutor(new givePermission());

        // register Tab Argrument for Command
        getCommand("getkit").setTabCompleter(new kitsTabable());
        getCommand("duel").setTabCompleter(new duel(this));
//        getCommand("perm").setTabCompleter(new PermsList());

        //PlaceholderAPI Register
        if(Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")){
            new PlaceholderExpansion().register();
        }else{
            msgconsole(ChatColor.RED+"PlaceholderAPI is not enabled!");
        }

        // register Event
        getServer().getPluginManager().registerEvents(new Vanish(), this);
        getServer().getPluginManager().registerEvents(new classic_sword(this), this);
        getServer().getPluginManager().registerEvents(new dia_to_netherite(this), this);
        getServer().getPluginManager().registerEvents(new slotitem(), this);
        getServer().getPluginManager().registerEvents(new launcher(this), this);
        getServer().getPluginManager().registerEvents(new canceldrops(), this);
        getServer().getPluginManager().registerEvents(new joinEvent(this), this);
        getServer().getPluginManager().registerEvents(new Bhopping(), this);
        getServer().getPluginManager().registerEvents(new Chat(), this);
        getServer().getPluginManager().registerEvents(new GUI(), this);
        getServer().getPluginManager().registerEvents(new Respawn(this), this);
        getServer().getPluginManager().registerEvents(new heal(), this);
        getServer().getPluginManager().registerEvents(new LeaveEvent(this), this);
        getServer().getPluginManager().registerEvents(new JoinMessage(), this);
        getServer().getPluginManager().registerEvents(new tellwhenkillwitharrow(), this);
        getServer().getPluginManager().registerEvents(new cancelevent(), this);
        getServer().getPluginManager().registerEvents(new BADWords(), this);
        getServer().getPluginManager().registerEvents(new openkitmenu(), this);
        getServer().getPluginManager().registerEvents(new adminfireball(), this);
        getServer().getPluginManager().registerEvents(new consumeCooldown(), this);
//        getServer().getPluginManager().registerEvents(new cancelcombat(), this);
//        this.getServer().getPluginManager().registerEvents(this, this);
        Database database = new Database(this);
        msgconsole(PluginName+"database is loading....\n\n");
        msgconsole(ChatColor.GREEN+"███╗░░░███╗██╗░░░██╗░██████╗░██████╗░██╗░░░░░");
        msgconsole(ChatColor.GREEN+"████╗░████║╚██╗░██╔╝██╔════╝██╔═══██╗██║░░░░░");
        msgconsole(ChatColor.GREEN+"██╔████╔██║░╚████╔╝░╚█████╗░██║██╗██║██║░░░░░");
        msgconsole(ChatColor.GREEN+"██║╚██╔╝██║░░╚██╔╝░░░╚═══██╗╚██████╔╝██║░░░░░");
        msgconsole(ChatColor.GREEN+"██║░╚═╝░██║░░░██║░░░██████╔╝░╚═██╔═╝░███████╗");
        msgconsole(ChatColor.GREEN+"╚═╝░░░░░╚═╝░░░╚═╝░░░╚═════╝░░░░╚═╝░░░╚══════╝");
        msgconsole(ChatColor.YELLOW+"=----------------------------------=");
        msgconsole(PluginName+"Connecting to database.");
        msgconsole(ChatColor.YELLOW+"=----------------------------------=");
        try {
            database.initializeDatabase();
            database.initializeDatabase2();
            database.initializeDatabase3();
            database.initializeDatabase4();
        } catch (SQLException e) {
            e.printStackTrace();
            msgconsole(ChatColor.RED+"Could not initialize database.");
            msgconsole(ChatColor.YELLOW+"=----------------------------------=");
        }
        // First Load Player
        for (Player p : getServer().getOnlinePlayers()){
            KitSQLAPI data = GetPlayerKit(p.getUniqueId().toString());
            if(data == null){
                try {
                    AddKit(p);
                } catch (SQLException ex) {
                    p.sendMessage(error);
                }
                Core.kits.put(p,"Default");
                p.sendMessage(PluginName+"You are currently select "+ChatColor.GREEN+"Default");
            }else{
                int kit = data.GetKit();
                String KitName = "Default";
                if (kit == 0) {
                    KitName = "Default";
                }else if (kit == 1) {
                    KitName = "Trident";
                }else if (kit == 2) {
                    KitName = "Viking";
                }else if (kit == 3) {
                    KitName = "Archer";
                }else if (kit == 4) {
                    KitName = "Admin";
                }
                Core.kits.put(p, KitName);
                p.sendMessage(PluginName+"You are currently select "+ChatColor.GREEN+KitName);
            }
            if (p.getGameMode() != GameMode.CREATIVE && p.getGameMode() != GameMode.SPECTATOR && !VanishAPI.isInvisible(p)){
                p.teleport(spawnloc);
                p.getInventory().clear();
                GetKitSelect(p);
            }
            p.sendMessage(PluginName+"Core plugin have been reloaded!");
        }
        msgconsole(PluginName+"Connected to database.");
        msgconsole(ChatColor.YELLOW+"=----------------------------------=");
        // start output
        msgconsole(PluginName + "CustomPlugin Been Loaded!");

        // Discord Webhook Started
        if (Webhook.equals("true")) {
            DiscordWebhook webhook = new DiscordWebhook(webhookURL);
            webhook.addEmbed(new DiscordWebhook.EmbedObject()
                    .setDescription("Server Started.")
                    .addField("Time", getDate(), false)
                    .addField("Server", "Main", false)
                    .setColor(Color.GREEN)
            );
            try {
                webhook.execute();
            }catch (java.io.IOException e){
                getLogger().severe(e.getStackTrace().toString());
            }
        }

        // combat thing
        this.combatList = new HashMap<>();
        getServer().getPluginManager().registerEvents(this, this);
        new BukkitRunnable()
        {
            @Override
            public void run(){
                GetKit();
                pariclespawn();
                onDelay();
                onDelay2();
                onDelay3();
                ClearTrident();
                Chat();
                CMD();
                SetTeamPrefix();
                nightvision();
                for (Player p : Bukkit.getServer().getOnlinePlayers()) {
                    int playerX = p.getLocation().getBlockX();
                    int playerY = p.getLocation().getBlockY() - 1;
                    int playerZ = p.getLocation().getBlockZ();
                    Location blockbelow = new Location(p.getWorld(), playerX, playerY, playerZ);
                    Material type = blockbelow.getBlock().getType();
                    if (!SW.contains(p.getLocation()) && !NW.contains(p.getLocation()) && !NE.contains(p.getLocation()) && !SE.contains(p.getLocation()) && type != Material.SPONGE) {
                        IsLaunched.remove(p.getName());
                    }
                    if (spawn.contains(p.getLocation())) {
                        p.setFoodLevel(20);
                        p.setSaturation(20f);
                    }
                }
            }
        }.runTaskTimer(this, 0, 20);
        new BukkitRunnable()
        {
            @Override
            public void run(){
                try {
                    database.getConnection();
                    database.getConnection2();
                    database.getConnection3();
                    msgconsole(ChatColor.YELLOW+"=----------------------------------=");
                    msgconsole(PluginName+"Reconnecting to database.");
                } catch (SQLException e) {
                    e.printStackTrace();
                    msgconsole(ChatColor.RED+"Could not connect to database.");
                    msgconsole(ChatColor.YELLOW+"=----------------------------------=");
                    return;
                }
                msgconsole(PluginName+"Reconnected to database.");
                msgconsole(ChatColor.YELLOW+"=----------------------------------=");
            }
        }.runTaskTimer(this, 0L, 2400L);
    }
    public void pariclespawn(){
        World world = Bukkit.getServer().getWorld("world");
        if(world == null){return;}
        Location loc1 = new Location(world, 270.5, 177, 270.5);
        Location loc2 = new Location(world, 270.5, 177, 242.5);
        Location loc3 = new Location(world, 242.5, 177, 242.5);
        Location loc4 = new Location(world, 242.5, 177, 270.5);
        for (Player ap : Bukkit.getServer().getOnlinePlayers()) {
            ap.spawnParticle(Particle.VILLAGER_HAPPY, loc1, 12, 1, 0.5, 1);
            ap.spawnParticle(Particle.VILLAGER_HAPPY, loc2, 12, 1, 0.5, 1);
            ap.spawnParticle(Particle.VILLAGER_HAPPY, loc3, 12, 1, 0.5, 1);
            ap.spawnParticle(Particle.VILLAGER_HAPPY, loc4, 12, 1, 0.5, 1);
        }
    }
    public void GetKit(){
        for(Player p : Bukkit.getServer().getOnlinePlayers()){
            if(
               p.getGameMode() != GameMode.CREATIVE &&
               p.getGameMode() != GameMode.SPECTATOR &&
               p.getWorld().getName().equals("world") &&
               !(spawn.contains(p.getLocation()))
            ){
                if(!gotkit.get(p.getName()) && !VanishAPI.isInvisible(p)) {
                    p.getInventory().clear();
                    p.setFoodLevel(20);
                    p.setSaturation(20f);
                    p.setHealth(p.getMaxHealth());
                    for (PotionEffect effect : p.getActivePotionEffects())
                        p.removePotionEffect(effect.getType());
                    givekite(p);
                    gotkit.put(p.getName(), true);
                }
            }
            if(
               p.getGameMode() == GameMode.CREATIVE ||
               p.getGameMode() == GameMode.SPECTATOR ||
               VanishAPI.isInvisible(p) ||
               !p.getWorld().getName().equals("world") ||
               (spawn.contains(p.getLocation()))
            ){
                gotkit.put(p.getName(), false);
            }
        }
    }
    // DISABLED SERVER
    @Override
    public void onDisable() {
        for(Player p : getServer().getOnlinePlayers()){
            String kit = kits.get(p);
            try {
                switch (kit) {
                    case "Default":
                        UpdateKit(p, 0);
                        break;
                    case "Trident":
                        UpdateKit(p, 1);
                        break;
                    case "Viking":
                        UpdateKit(p, 2);
                        break;
                    case "Archer":
                        UpdateKit(p, 3);
                        break;
                    case "Admin":
                        UpdateKit(p, 4);
                        break;
                }
            } catch (SQLException ex) {
                msgconsole(error);
            }
        }
        msgconsole(PluginName + "Shutdown CustomPlugin");
        // Discord Webhook Started
        DiscordWebhook webhook = new DiscordWebhook(webhookURL);
        webhook.addEmbed(new DiscordWebhook.EmbedObject()
                .setDescription("Server Stopped.")
                .addField("Time", getDate(), false)
                .addField("Server", "Main", false)
                .setColor(Color.GRAY)
        );
        try {
            webhook.execute();
        }catch (java.io.IOException e){
            getLogger().severe(e.getStackTrace().toString());
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
    public void nightvision(){
        for (Player p : getServer().getOnlinePlayers()){
            if (p.getGameMode() != GameMode.CREATIVE && p.getGameMode() != GameMode.SPECTATOR && p.getLocation().getWorld().getName().endsWith("world")) {
                p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 1000000, 1, false, false));
            }
        }
    }
    public void ClearTrident() {
        World SessionWorld = Bukkit.getServer().getWorld("world");
        if (SessionWorld != null) {
            for(Entity t : SessionWorld.getEntities()) {
                if(t instanceof Trident) {
                    ProjectileSource p = ((Trident) t).getShooter();
                    if (p instanceof Player) {
                        Player checkp = getServer().getPlayerExact(((Player) p).getName());
                        if (checkp == null){
                            t.remove();
                            msgconsole("Removed "+((Player) p).getName()+"'s Trident Because they left.");
                            return;
                        }
                        if (((Player) p).getWorld() == Bukkit.getServer().getWorld("world")) {
                            if (spawn.contains(((Player) p).getLocation())) {
                                t.remove();
                            }
                        }
                    }
                }
            }
        }
    }
    @EventHandler(ignoreCancelled = true)
    private void onProjectileLaunch(ProjectileLaunchEvent event)
    {
        if(event.getEntityType() == EntityType.TRIDENT && (event.getEntity().getShooter() instanceof Player))
        {
            Player p = (Player) event.getEntity().getShooter();
            ItemStack tridentItem = null;
            boolean fromOffhand = false;
            // If the player is holding two tridents, only the one from the main hand will be thrown
            // (Unless the player starts charging in the offhand then equips a trident in the main hand)
            if(p.getInventory().getItemInMainHand().getType() == Material.TRIDENT)
            {
                tridentItem = p.getInventory().getItemInMainHand();
            }
            else if(p.getInventory().getItemInOffHand().getType() == Material.TRIDENT)
            {
                fromOffhand = true;
                tridentItem = p.getInventory().getItemInOffHand();
            }

            // The trident could be thrown "artificially"
            if(tridentItem != null)
            {
                // Check if it was thrown from the offhand
                if(fromOffhand)
                {
                    event.getEntity().setMetadata("offhand", new FixedMetadataValue(this, true));
                }

                if(tridentItem.getEnchantmentLevel(Enchantment.IMPALING) != 0)
                {
                    event.getEntity().setMetadata("impaling", new FixedMetadataValue(this,
                            tridentItem.getEnchantmentLevel(Enchantment.IMPALING)));
                }

                if(tridentItem.getEnchantmentLevel(Enchantment.LOYALTY) != 0)
                {
                    event.getEntity().setMetadata("loyalty", new FixedMetadataValue(this,
                            tridentItem.getEnchantmentLevel(Enchantment.LOYALTY)));

                    LoyaltyTridentTrackerTask trackerTask = new LoyaltyTridentTrackerTask((Trident) event.getEntity(), reflectionUtils);
                    trackerTask.runTaskTimer(this, 0, 1);
                }
            }

        }
    }
    @EventHandler(ignoreCancelled = true)
    private void onPlayerPickupArrow(PlayerPickupArrowEvent event)
    {
        if(
                event.getArrow() instanceof Trident &&
                event.getArrow().hasMetadata("offhand") &&
                event.getPlayer().getInventory().getItemInOffHand().getType() == Material.AIR)
        {
            // The itemstack gets modified after the event so it must be cloned for future comparison
            ItemStack item = event.getItem().getItemStack().clone();
            Player p = event.getPlayer();

            // The item isn't in the inventory yet so schedule a checker
            getServer().getScheduler().scheduleSyncDelayedTask(this, () ->
            {
                // Double-check to ensure offhand item is still empty
                if(event.getPlayer().getInventory().getItemInOffHand().getType() != Material.AIR)
                {
                    return;
                }

                // Start from end of inventory to get the most recently added trident in case duplicates exist
                ItemStack[] contents = p.getInventory().getContents();
                for(int i = contents.length - 1; i >= 0; i--)
                {
                    ItemStack current = contents[i];
                    if(current != null && current.equals(item))
                    {
                        // If we find the trident and the offhand is clear, put it in the offhand
                        p.getInventory().setItemInOffHand(current.clone());
                        current.setAmount(current.getAmount() - 1);
                        break;
                    }
                }
                p.updateInventory();
            });
        }
    }
    @EventHandler
    public void PickupTrident(PlayerPickupItemEvent e){
        Player p = e.getPlayer();
        PlayerInventory inv = p.getInventory();
        ItemStack it = e.getItem().getItemStack();
        ItemStack tri = new ItemStack(Material.TRIDENT, 1);
        if (it.getType() == Material.TRIDENT){
            if (inv.getItemInOffHand().getType() == Material.TRIDENT){
                p.sendMessage("Have another TriDENT!!!!");
                inv.removeItem(tri);
                p.updateInventory();
            }if (inv.contains(Material.TRIDENT)){
                p.sendMessage("Have another TriDENT!!!!");
                inv.removeItem(tri);
                p.updateInventory();
            }
        }
    }
    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event)
    {
        if (event.getEntity() instanceof Player && event.getDamager() instanceof Player){
            Player target = (Player) event.getEntity();
            Player damager = (Player) event.getDamager();
            if(!(event.isCancelled())) {
                if (damager.getGameMode() != GameMode.CREATIVE){
                    combatList.put(damager.getName(), 11);
                }if (target.getGameMode() != GameMode.CREATIVE) {
                    combatList.put(target.getName(), 11);
                }
            }
        }
        if (event.getEntity() instanceof Player && event.getDamager() instanceof Projectile){
            Player target = (Player) event.getEntity();
            Player shooter = (Player) ((Projectile) event.getDamager()).getShooter();
            if (shooter == null){return;}
            if(!(event.isCancelled())) {
                if (shooter.getGameMode() != GameMode.CREATIVE){
                    combatList.put(shooter.getName(), 11);
                }if (target.getGameMode() != GameMode.CREATIVE) {
                    combatList.put(target.getName(), 11);
                }
            }
        }
    }
    public static void GetKitSelect(Player p){
        PlayerInventory inv = p.getInventory();

        ItemStack Kit = new ItemStack(Material.BOOK, 1);

        ItemMeta im = Kit.getItemMeta();

        im.setDisplayName("§aKit Selector §7(Right Click)");
        im.setLore(lore);
        im.setUnbreakable(true);
        im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        im.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        Kit.setItemMeta(im);
        Kit.addUnsafeEnchantment(Enchantment.KNOCKBACK, 1);
        inv.setItem(4, Kit);
    }
    public String getDate(){
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Bangkok"));
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        return sdf.format(cal.getTime());
    }
    @EventHandler
    public void ACLogged(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        if (Core.combatList.containsKey(e.getPlayer().getName())) {
            Bukkit.broadcastMessage(ChatColor.RED + p.getName() + " has combat logged.");
            p.damage(21.0D);
            Core.combatList.put(p.getName(), -1);
            //////////////////////////////////////////////////////////
            if (Webhook.equals("true")) {
                DiscordWebhook webhookAC = new DiscordWebhook(webhookURLAC);
                webhookAC.addEmbed(new DiscordWebhook.EmbedObject()
                        .setTitle("Combat Logged!")
                        .setThumbnail("https://minotar.net/armor/body/" + p.getName() + "/4096.png")
                        .addField("Name", p.getName()+" / "+p.getAddress().getAddress(), false)
                        .addField("Time", getDate(), false)
                        .addField("Server", "main", false)
                        .setFooter("------------------------------------------------------------------------------------", "")
                        .setColor(Color.YELLOW)
                );
                try {
                    webhookAC.execute();
                } catch (java.io.IOException event) {
                    getLogger().severe(event.getStackTrace().toString());
                }
            }
            //////////////////////////////////////////////////////////
        }
    }
    public void onDelay(){
        HashMap<String, Integer> temp = new HashMap<>();
        for (String id : combatList.keySet())
        {
            Player p = Bukkit.getPlayer(id);
            int timer = combatList.get(id) - 1;
            if (timer == 0){
                if (p != null) {
                    p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GRAY + "You are no longer combat"));
                }
            }
            if (timer > 0){
                if (p != null) {
                    p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.RED + "You are in combat"));
                }
                temp.put(id, timer);
            }
        }
            combatList = temp;
    }
    public void onDelay2(){
        HashMap<String, Integer> temp2 = new HashMap<>();
        for (String id2 : bhopcooldown.keySet())
        {
            Player p = Bukkit.getPlayer(id2);
            int timer2 = bhopcooldown.get(id2) - 1;
            if (timer2 == 0){
                if (p != null) {
                    p.sendMessage(PluginName+ChatColor.GREEN + "Your b-hop is ready to use.");
                }
            }
            if (timer2 > 0){
                temp2.put(id2, timer2);
            }        }

        bhopcooldown = temp2;
    }
    public void onDelay3(){
        HashMap<String, Integer> temp3 = new HashMap<>();
        for (String id3 : dueltimer.keySet())
        {
            Player p = Bukkit.getPlayer(id3);
            int timer3 = dueltimer.get(id3) - 1;
            if (timer3 == 0){
                if (p != null) {
                    inviteList.remove(p.getName());
                    p.sendMessage(PluginName+ChatColor.RED + "Invite has been rejected.");
                }
            }
            if (timer3 > 0){
                temp3.put(id3, timer3);
            }
        }
        dueltimer = temp3;
    }
    @EventHandler
    public void onKill(PlayerDeathEvent e) {
        Player p = e.getEntity();
        Player killer = p.getKiller();
        Random r = new Random();
        List<String> sl = config.getStringList("deadmsg");
        String s = sl.get(r.nextInt(sl.size()));
        if (killer != null) {
            if(killer.getWorld().getName().equals("world")) {
                killer.getInventory().remove(Material.ARROW);
                killer.getInventory().addItem(new ItemStack(Material.GOLDEN_APPLE, 1));
                killer.getInventory().setItem(9, new ItemStack(Material.ARROW, 48));
            }
            World SessionWorldNeth = Bukkit.getServer().getWorld("Netherite_game");
            if (killer.getName().equals(p.getName())) {
                if (p.getWorld() != SessionWorldNeth) {
                    p.setBedSpawnLocation(spawnloc);
                    p.getInventory().clear();
                    for (PotionEffect effect : p.getActivePotionEffects())
                        p.removePotionEffect(effect.getType());
                }
                Bukkit.broadcastMessage(GetPrefixPlayer(p)+ChatColor.RED + p.getName() + ChatColor.YELLOW + " has killed themself by accident.");
                return;
            }
            Bukkit.broadcastMessage(GetPrefixPlayer(p)+ChatColor.RED + p.getName() + ChatColor.YELLOW + s + GetPrefixPlayer(killer)+ChatColor.DARK_AQUA + killer.getName());
        }
    }
    @EventHandler
    public void CancelCmd(PlayerCommandPreprocessEvent event){
        if (combatList.containsKey(event.getPlayer().getName()))
        {
            List<String> commands = Arrays.asList("/spawn");

            String[] parts = event.getMessage().split(" ");

            String cmd = parts[0].toLowerCase();

            if (commands.contains(cmd))
            {
                event.setCancelled(true);
                event.getPlayer().sendMessage(ChatColor.RED + "You are still in combat!");
            }
        }
    }
}