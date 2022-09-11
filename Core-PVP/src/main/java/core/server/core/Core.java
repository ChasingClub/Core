package core.server.core;

import core.server.core.command.*;
import core.server.core.events.*;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.command.CommandExecutor;
import org.bukkit.configuration.file.FileConfiguration;
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
import core.server.core.commandTabComplete.kitsTabable;


import java.awt.Color;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

import static core.server.core.API.Prefix.SetTeamPrefix;

public class Core extends JavaPlugin implements Listener, CommandExecutor {
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
    public static HashMap<String,String> duel = new HashMap<String,String>();
    public static HashMap<String,Integer> dueltimer = new HashMap<String,Integer>();
    public static HashMap<String, String> games = new HashMap<String, String>();
    public static String Plname = (ChatColor.YELLOW + "C" + ChatColor.LIGHT_PURPLE + "C" + ChatColor.DARK_GRAY + " » "+ChatColor.GRAY);
    public static HashMap<String, Integer> combatList;
    public static HashMap<String, Integer> bhopcooldown = new HashMap<String, Integer>();
    public static HashMap<String, String> ingame = new HashMap<String, String>();
    public FileConfiguration config = this.getConfig();
    public String webhookURL = config.getString("DiscordWebhookURL");
    public String webhookURLAC = config.getString("AntiCheatHook");
    public String Webhook = config.getString("Webhook");
    private ReflectionUtils reflectionUtils;
    private Boolean enableVoidSaving = true;
    public Core plugin;

    public static void msgconsole(String message){
        Bukkit.getConsoleSender().sendMessage(message);
    }
    // ENABLED SERVER
    @Override
    public void onEnable() {
        // First Load
        for (Player p : getServer().getOnlinePlayers()){
            kits.put(p,"Default");
            if (p.getGameMode() != GameMode.CREATIVE && p.getGameMode() != GameMode.SPECTATOR){
                World SessionWorld = Bukkit.getServer().getWorld("world");
                Location SessionWorldSpawn = new Location(SessionWorld, 64.5, 180, 26.5);
                p.teleport(SessionWorldSpawn);
                p.getInventory().clear();
                GetKitSelect(p);
            }
            p.sendMessage(Plname+"Core plugin have been reloaded!");
        }
        // Add String Arraylist/HashMap
        maps.put("Colosseum", true);maps.put("Beach", true);maps.put("Abyss", true);
        anti.put("8a490d62-98bd-4f57-b4a6-e4738b0beb96", "1");anti.put("0ab56496-71f6-4205-8e16-ec21dd7bfd5e", "2");anti.put("30c8f2de-9dc6-450c-bc31-4c20db77a29b", "3");
        playerkits.put("Default", 1);playerkits.put("trident", 2);playerkits.put("viking", 3);playerkits.put("bow", 4);playerkits.put("admin", 5);
        duel.put("invite", "1");duel.put("accept", "2");duel.put("reject", "3");
        //"NetheriteStack, DodgeBall, Paintball, ClassicIron, ClassicDiamond, OP, Crystal"
        games.put("netheritestack", "1");
        // add lore
        lore.add("§r");
        lore.add("§7Right Click to open kit selector menu!");
        // Set Normal TimeZone
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Bangkok"));
        // Config
        File file = new File(getDataFolder() + File.separator + "config.yml"); //This will get the config file

        if (!file.exists()){ //This will check if the file exist
            getConfig().options().copyDefaults(true); //function to check the important settings
            saveConfig(); //saves the config
            reloadConfig(); //reloads the config
        }

        // register Command
        getCommand("spawn").setExecutor(new spawn());
        getCommand("getkit").setExecutor(new kits());
        getCommand("feed").setExecutor(new feed());
        getCommand("heal").setExecutor(new health());
        getCommand("gmc").setExecutor(new gmc());
        getCommand("gma").setExecutor(new gma());
        getCommand("gms").setExecutor(new gms());
        getCommand("gmsp").setExecutor(new gmsp());
        getCommand("fling").setExecutor(new fling());
        getCommand("crash").setExecutor(new crash());
        getCommand("earape").setExecutor(new earape());
        getCommand("b").setExecutor(new build());
        getCommand("duel").setExecutor(new duel());
        getCommand("givekit").setExecutor(new givekit());
        getCommand("setkit").setExecutor(new setkit());
        getCommand("kaboom").setExecutor(new kaboom());
        getCommand("map").setExecutor(new map());
        getCommand("fly").setExecutor(new fly());
//        getCommand("perm").setExecutor(new givePermission());

        // register Tab Argrument for Command
        getCommand("getkit").setTabCompleter(new kitsTabable());
        getCommand("duel").setTabCompleter(new duel());
//        getCommand("perm").setTabCompleter(new PermsList());


        // register Event
        getServer().getPluginManager().registerEvents(new dia_to_netherite(), this);
        getServer().getPluginManager().registerEvents(new slotitem(), this);
        getServer().getPluginManager().registerEvents(new canceldrops(), this);
        getServer().getPluginManager().registerEvents(new joinEvent(), this);
        getServer().getPluginManager().registerEvents(new Bhopping(), this);
        getServer().getPluginManager().registerEvents(new Chat(), this);
        getServer().getPluginManager().registerEvents(new Respawn(), this);
        getServer().getPluginManager().registerEvents(new heal(), this);
        getServer().getPluginManager().registerEvents(new LeaveClear(), this);
        getServer().getPluginManager().registerEvents(new JoinMessage(), this);
        getServer().getPluginManager().registerEvents(new tellwhenkillwitharrow(), this);
        getServer().getPluginManager().registerEvents(new cancelevent(), this);
        getServer().getPluginManager().registerEvents(new BADWords(), this);
        getServer().getPluginManager().registerEvents(new openkitmenu(), this);
        getServer().getPluginManager().registerEvents(new adminfireball(), this);
        getServer().getPluginManager().registerEvents(new consumeCooldown(), this);
//        getServer().getPluginManager().registerEvents(new cancelcombat(), this);
//        this.getServer().getPluginManager().registerEvents(this, this);

        // start output
        msgconsole(Plname + "CustomPlugin Been Loaded!");

        // Discord Webhook Started
        if (Webhook == "true") {
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
                onDelay();
                onDelay2();
                onDelay3();
                ClearTrident();
                Chat();
                CMD();
                SetTeamPrefix();
//                System.out.println(maps);
//                System.out.println(inviteList);
//                System.out.println(bhopcooldown);
//                System.out.println(ingame);
//                System.out.println(combatList);
            }
        }.runTaskTimer(this, 0, 20);
        new BukkitRunnable()
        {
            @Override
            public void run(){
                nightvision();
            }
        }.runTaskTimer(this, 0, 100);
    }
    // DISABLED SERVER
    @Override
    public void onDisable() {
        msgconsole(Plname + "Shutdown CustomPlugin");
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
                        Cuboid cuboid = new Cuboid(Bukkit.getServer().getWorld("world"), 160, 50, -66, -24, 2, 121);
                        if (!(cuboid.contains(((Player) p).getLocation()))) {
                            if (((Player) p).getWorld() == Bukkit.getServer().getWorld("world")) {
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

                    if(enableVoidSaving)
                    {
                        LoyaltyTridentTrackerTask trackerTask = new LoyaltyTridentTrackerTask((Trident) event.getEntity(), reflectionUtils);
                        trackerTask.runTaskTimer(this, 0, 1);
                    }
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
            if (Webhook == "true") {
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
                p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GRAY + "You are no longer combat"));
            }
            if (timer > 0){
                p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.RED + "You are in combat"));
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
                    p.sendMessage(Plname+ChatColor.GREEN + "Your b-hop is ready to use.");
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
                    p.sendMessage(Plname+ChatColor.RED + "Invite has been rejected.");
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
        if (killer instanceof Player && p instanceof Player) {
            World SessionWorldNeth = Bukkit.getServer().getWorld("Netherite_game");
            if (killer.getName() == p.getName()) {
                World SessionWorld = Bukkit.getServer().getWorld("world");
                Location SessionWorldSpawn = new Location(SessionWorld, 64.5, 180.5, 26.5);
                if (p.getWorld() != SessionWorldNeth) {
                    p.teleport(SessionWorldSpawn);
                    p.getInventory().clear();
                    for (PotionEffect effect : p.getActivePotionEffects())
                        p.removePotionEffect(effect.getType());
                }
                Bukkit.broadcastMessage(ChatColor.RED + p.getName() + ChatColor.YELLOW + " has killed themself by accident.");
                return;
            }
            Bukkit.broadcastMessage(ChatColor.RED + p.getName() + ChatColor.YELLOW + s + ChatColor.DARK_AQUA + killer.getName());
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