package chasingclub.server.core.command;

import chasingclub.server.core.Core;
import chasingclub.server.core.Utils.DuelStatsSQLAPI;
import chasingclub.server.core.Utils.SlotSQL;
import chasingclub.server.core.events.classic_sword;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static chasingclub.server.core.Core.combatList;
import static chasingclub.server.core.Utils.Database.*;
import static chasingclub.server.core.Utils.Utils.*;
import static org.bukkit.Bukkit.getServer;

public class duel implements CommandExecutor, TabCompleter {
    public Core plugin;
    public duel(Core plugin){
        this.plugin = plugin;
    }
    /*
     * args[0] = duels options -> { invite, accept, reject }
     * args[1] = player target
     * args[2] = games -> { NetheriteStack, DodgeBall, Paintball, ClassicIron, ClassicDiamond, OP, Crystal }
     * args[3].. error with arguments
     * */
    public DuelStatsSQLAPI GetPlayerDuelStats(String uuid){
        try {
            return FindDuelStatsByPlayerUUID(uuid);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static SlotSQL GetPlayerSlot(String uuid, String table){
        try {
            return FindSlotByUUID(uuid,table);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public String usage = PluginName + ChatColor.YELLOW + "/duel <invite/accept/reject> <player>";
    public String game = "NetheriteStack, ClassicSword";
//    public String gameinc = "NetheriteStack, ClassicSword, ClassicIron, ClassicDiamond, OP, Crystal";

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (sender instanceof Player) { // player

            Player player = (Player) sender;
            String playerName = player.getName();
            World NetheriteStack = Bukkit.getServer().getWorld("netherite_game");
            World ClassicSword = Bukkit.getServer().getWorld("classic_sword");

            ItemStack[] armor = new ItemStack[4];
            armor[0] = new ItemStack(Material.DIAMOND_BOOTS);
            armor[1] = new ItemStack(Material.DIAMOND_LEGGINGS);
            armor[2] = new ItemStack(Material.DIAMOND_CHESTPLATE);
            armor[3] = new ItemStack(Material.DIAMOND_HELMET);
            ItemMeta imh = armor[0].getItemMeta();
            ItemMeta imc = armor[1].getItemMeta();
            ItemMeta iml = armor[2].getItemMeta();
            ItemMeta imb = armor[3].getItemMeta();
            imh.setUnbreakable(true);
            imc.setUnbreakable(true);
            iml.setUnbreakable(true);
            imb.setUnbreakable(true);
            armor[0].setItemMeta(imh);
            armor[1].setItemMeta(imc);
            armor[2].setItemMeta(iml);
            armor[3].setItemMeta(imb);

            ItemStack arrow = new ItemStack(Material.ARROW, 32);

            ItemStack[] weapon = new ItemStack[5];
            weapon[0] = new ItemStack(Material.DIAMOND_SWORD);
            weapon[1] = new ItemStack(Material.DIAMOND_AXE);
            weapon[2] = new ItemStack(Material.BOW);
            weapon[3] = new ItemStack(Material.CROSSBOW);
            weapon[4] = new ItemStack(Material.COOKED_BEEF, 64);

            ItemStack shield = new ItemStack(Material.SHIELD);


            if (args.length == 0) {

                player.sendMessage(PluginName + ChatColor.RED + "You need to provide an options to duels");
                player.sendMessage(usage);

            } else if (args.length == 1) { // if se
                // nd only options
                if (args[0].equalsIgnoreCase("leave")){
                    if (Core.ingame.get(player.getName()) == null) {
                        player.sendMessage(PluginName+ChatColor.RED+"You are not in the game!");
                        return true;
                    }
                    Player target = getServer().getPlayer(Core.ingame.get(player.getName()));
                    // run duel leave
                    if (target == null) {
                        try {
                            UpdateDuel(player, "loses");
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                            player.sendMessage(error+" Please report staff");
                        }
                        player.getInventory().clear();
                        combatList.put(player.getName(), 0);
                        player.teleport(plugin.spawnloc);
                        player.sendMessage(PluginName + "You lost because you left the game!");
                        Core.GetKitSelect(player);
                        if (Core.playerinmap.get(player.getName()).equals("Colosseum2")){
                            Core.maps.put("Colosseum2", true);
                        }if (Core.playerinmap.get(player.getName()).equals("Colosseum")){
                            Core.maps.put("Colosseum", true);
                        }if (Core.playerinmap.get(player.getName()).equals("Beach")){
                            Core.maps.put("Beach", true);
                        }if (Core.playerinmap.get(player.getName()).equals("Abyss")){
                            Core.maps.put("Abyss", true);
                        }
                        Core.playerinmap.remove(player.getName());
                        Core.ingame.remove(player.getName());
                        return true;
                    }
                    try {
                        UpdateDuel(player, "loses");
                        UpdateDuel(target, "wins");
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        target.sendMessage(error+" Please report staff");
                        player.sendMessage(error+" Please report staff");
                    }
                    target.getInventory().clear();
                    player.getInventory().clear();
                    combatList.put(player.getName(), 0);
                    combatList.put(target.getName(), 0);
                    target.teleport(plugin.spawnloc);
                    target.sendMessage(PluginName + "GG! you have defeated " + player.getName());
                    player.teleport(plugin.spawnloc);
                    player.sendMessage(PluginName + "You lost because you left the game!");
                    Core.GetKitSelect(target);
                    Core.GetKitSelect(player);
                    if (Core.playerinmap.get(player.getName()).equals("Colosseum")){
                        Core.maps.put("Colosseum", true);
                    }else if (Core.playerinmap.get(player.getName()).equals("Colosseum2")){
                        Core.maps.put("Colosseum2", true);
                    }else if (Core.playerinmap.get(player.getName()).equals("Beach")){
                        Core.maps.put("Beach", true);
                    }else if (Core.playerinmap.get(player.getName()).equals("Abyss")){
                        Core.maps.put("Abyss", true);
                    }
                    classic_sword.Lives.remove(player.getName());
                    classic_sword.Lives.remove(target.getName());
                    Core.playerinmap.remove(player.getName());
                    Core.playerinmap.remove(target.getName());
                    Core.ingame.remove(target.getName());
                    Core.ingame.remove(player.getName());
                    return true;
                }
                if (args[0].equalsIgnoreCase("stats")) {
                    DuelStatsSQLAPI data = GetPlayerDuelStats(player.getUniqueId().toString());
                    double wins = data.GetWins();
                    double loses = data.GetLoses();
                    double winrate = ((wins*100)/(wins+loses));
                    DecimalFormat decimalFormat = new DecimalFormat("#.##");
                    decimalFormat.setRoundingMode(RoundingMode.CEILING);
                    player.sendMessage("§7§m                          ");
                    player.sendMessage("§eYour Duels Stats§7:");
                    player.sendMessage("§7Kills: §f§l» §b"+data.GetKills());
                    player.sendMessage("§7Deaths: §f§l» §b"+data.GetDeaths());
                    player.sendMessage("§7Wins: §f§l» §b"+data.GetWins());
                    player.sendMessage("§7Loses: §f§l» §b"+data.GetLoses());
                    player.sendMessage("§7Win Rate: §f§l» §b"+decimalFormat.format(winrate)+"%");
                    player.sendMessage("§7§m                          ");
                    return true;
                }
                if (Core.duel.get(args[0]) != null) {

                    player.sendMessage(PluginName + ChatColor.RED + "You need to specify a player to duels!");
                    player.sendMessage(PluginName + ChatColor.YELLOW + "/duel "+args[0]+" <player>");

                }else{
                    player.sendMessage(PluginName + ChatColor.RED + "You need to provide an options to duels");
                    player.sendMessage(usage);
                }
            } else if (args.length == 2) {// if send until player

                Player target = getServer().getPlayer(args[1]);
                if (args[0].equalsIgnoreCase("stats")) {
                    OfflinePlayer target2 = Bukkit.getOfflinePlayer(args[1]);
                    DuelStatsSQLAPI data = GetPlayerDuelStats(target2.getUniqueId().toString());
                    if(data == null){
                        player.sendMessage(PluginName+"Player data was not found!");
                        return true;
                    }
                    double wins = data.GetWins();
                    double loses = data.GetLoses();
                    double winrate = ((wins*100)/(wins+loses));
                    DecimalFormat decimalFormat = new DecimalFormat("#.##");
                    decimalFormat.setRoundingMode(RoundingMode.CEILING);
                    player.sendMessage("§7§m                          ");
                    player.sendMessage("§a"+target2.getName()+"§e Duels Stats§7:");
                    player.sendMessage("§7Kills: §f§l» §b"+data.GetKills());
                    player.sendMessage("§7Deaths: §f§l» §b"+data.GetDeaths());
                    player.sendMessage("§7Wins: §f§l» §b"+data.GetWins());
                    player.sendMessage("§7Loses: §f§l» §b"+data.GetLoses());
                    player.sendMessage("§7Win Rate: §f§l» §b"+decimalFormat.format(winrate)+"%");
                    player.sendMessage("§7§m                          ");
                    return true;
                }
                if (Core.duel.get(args[0]) == null) {

                    player.sendMessage(PluginName + ChatColor.RED + "You need to provide an options to duels");
                    player.sendMessage(usage);

                }
                if (args[0].equalsIgnoreCase("invite")) {
                    if(combatList.containsKey(player.getName())) {
                        player.sendMessage(ChatColor.RED + "You are still in combat!");
                        return true;
                    }
                    if (target == null) {

                        player.sendMessage(PluginName + ChatColor.RED + "That player is offline!");
                        return true;

                    }
                    if (target.getName().equals(player.getName())) {

                        player.sendMessage(PluginName + ChatColor.RED + "You can't duel yourself!");
                        return true;

                    } else { // not specify game

                        player.sendMessage(PluginName + "You need to specify the game!");
                        player.sendMessage(PluginName + game);

                    }
                } else if (args[0].equalsIgnoreCase("accept")) { // accept
                    if(combatList.containsKey(player.getName())) {
                        player.sendMessage(ChatColor.RED + "You are still in combat!");
                        return true;
                    }
                    if (target == null) {

                        player.sendMessage(PluginName + ChatColor.RED + "That player is offline!");
                        return true;

                    }
                    if (Core.inviteList.get(player.getName()) == null) { // if target doest invite the player

                        player.sendMessage(PluginName + target.getName() + " doesn't invite you to duel!");
                        return true;

                    }
                    if (Core.ingame.containsKey(target.getName())) {

                        player.sendMessage(PluginName + ChatColor.RED + "That player is currently in a game!");
                        Core.dueltimer.remove(player.getName());
                        Core.inviteList.remove(player.getName());
                        return true;

                    }
                    Core.dueltimer.remove(player.getName());
                    if (Core.inviteList.get(player.getName()) != null) {
                        if(!Core.inviteList.get(player.getName()).containsKey(target.getName())){
                            player.sendMessage(PluginName+target.getName()+" doesn't invite you!");
                            return true;
                        }
                        if (Core.inviteList.get(player.getName()).containsValue("netheritestack")) {

                            if (Boolean.TRUE.equals(Core.maps.get("Colosseum"))) {
                                player.sendTitle("\uEF00", null, 10, 30, 20);
                                target.sendTitle("\uEF00", null, 10, 30, 20);
                                target.sendMessage(PluginName + player.getName() + ChatColor.GREEN + " has accept the duel"); // accept
                                Location TeamA = new Location(NetheriteStack, 0.5, 66, 17.5, 180f, 0f);
                                Location TeamB = new Location(NetheriteStack, 0.5, 66, -16.5, 0f, 0f);
                                player.closeInventory();
                                target.closeInventory();
                                player.teleport(TeamA);
                                target.teleport(TeamB);
                                player.setBedSpawnLocation(TeamA, true);
                                target.setBedSpawnLocation(TeamB, true);
                                player.getInventory().clear();
                                target.getInventory().clear();
                                player.getInventory().setArmorContents(armor);
                                target.getInventory().setArmorContents(armor);
                                SlotSQL data = GetPlayerSlot(player.getUniqueId().toString(), "netherite_stack");
                                SlotSQL data2 = GetPlayerSlot(target.getUniqueId().toString(), "netherite_stack");
                                if(data != null) {
                                    Inventory inv = player.getInventory();
                                    if (data.Get1().equalsIgnoreCase("DIAMOND_SWORD")) {
                                        inv.setItem(0, new ItemStack(Material.DIAMOND_SWORD, 1));
                                    } else if (data.Get1().equalsIgnoreCase("DIAMOND_AXE")) {
                                        inv.setItem(0, new ItemStack(Material.DIAMOND_AXE, 1));
                                    } else if (data.Get1().equalsIgnoreCase("BOW")) {
                                        inv.setItem(0, new ItemStack(Material.BOW, 1));
                                    } else if (data.Get1().equalsIgnoreCase("COOKED_BEEF")) {
                                        inv.setItem(0, new ItemStack(Material.COOKED_BEEF, 64));
                                    } else if (data.Get1().equalsIgnoreCase("CROSSBOW")) {
                                        inv.setItem(0, new ItemStack(Material.CROSSBOW, 1));
                                    } else if (data.Get1().equalsIgnoreCase("SHIELD")) {
                                        inv.setItem(0, new ItemStack(Material.SHIELD, 1));
                                    } else if (data.Get1().equalsIgnoreCase("AIR")) {
                                        inv.setItem(0, new ItemStack(Material.AIR, 1));
                                    }

                                    if (data.Get2().equalsIgnoreCase("DIAMOND_SWORD")) {
                                        inv.setItem(1, new ItemStack(Material.DIAMOND_SWORD, 1));
                                    } else if (data.Get2().equalsIgnoreCase("DIAMOND_AXE")) {
                                        inv.setItem(1, new ItemStack(Material.DIAMOND_AXE, 1));
                                    } else if (data.Get2().equalsIgnoreCase("BOW")) {
                                        inv.setItem(1, new ItemStack(Material.BOW, 1));
                                    } else if (data.Get2().equalsIgnoreCase("COOKED_BEEF")) {
                                        inv.setItem(1, new ItemStack(Material.COOKED_BEEF, 64));
                                    } else if (data.Get2().equalsIgnoreCase("CROSSBOW")) {
                                        inv.setItem(1, new ItemStack(Material.CROSSBOW, 1));
                                    } else if (data.Get2().equalsIgnoreCase("SHIELD")) {
                                        inv.setItem(1, new ItemStack(Material.SHIELD, 1));
                                    } else if (data.Get2().equalsIgnoreCase("AIR")) {
                                        inv.setItem(1, new ItemStack(Material.AIR, 1));
                                    }

                                    if (data.Get3().equalsIgnoreCase("DIAMOND_SWORD")) {
                                        inv.setItem(2, new ItemStack(Material.DIAMOND_SWORD, 1));
                                    } else if (data.Get3().equalsIgnoreCase("DIAMOND_AXE")) {
                                        inv.setItem(2, new ItemStack(Material.DIAMOND_AXE, 1));
                                    } else if (data.Get3().equalsIgnoreCase("BOW")) {
                                        inv.setItem(2, new ItemStack(Material.BOW, 1));
                                    } else if (data.Get3().equalsIgnoreCase("COOKED_BEEF")) {
                                        inv.setItem(2, new ItemStack(Material.COOKED_BEEF, 64));
                                    } else if (data.Get3().equalsIgnoreCase("CROSSBOW")) {
                                        inv.setItem(2, new ItemStack(Material.CROSSBOW, 1));
                                    } else if (data.Get3().equalsIgnoreCase("SHIELD")) {
                                        inv.setItem(2, new ItemStack(Material.SHIELD, 1));
                                    } else if (data.Get3().equalsIgnoreCase("AIR")) {
                                        inv.setItem(2, new ItemStack(Material.AIR, 1));
                                    }

                                    if (data.Get4().equalsIgnoreCase("DIAMOND_SWORD")) {
                                        inv.setItem(3, new ItemStack(Material.DIAMOND_SWORD, 1));
                                    } else if (data.Get4().equalsIgnoreCase("DIAMOND_AXE")) {
                                        inv.setItem(3, new ItemStack(Material.DIAMOND_AXE, 1));
                                    } else if (data.Get4().equalsIgnoreCase("BOW")) {
                                        inv.setItem(3, new ItemStack(Material.BOW, 1));
                                    } else if (data.Get4().equalsIgnoreCase("COOKED_BEEF")) {
                                        inv.setItem(3, new ItemStack(Material.COOKED_BEEF, 64));
                                    } else if (data.Get4().equalsIgnoreCase("CROSSBOW")) {
                                        inv.setItem(3, new ItemStack(Material.CROSSBOW, 1));
                                    } else if (data.Get4().equalsIgnoreCase("SHIELD")) {
                                        inv.setItem(3, new ItemStack(Material.SHIELD, 1));
                                    } else if (data.Get4().equalsIgnoreCase("AIR")) {
                                        inv.setItem(3, new ItemStack(Material.AIR, 1));
                                    }

                                    if (data.Get5().equalsIgnoreCase("DIAMOND_SWORD")) {
                                        inv.setItem(4, new ItemStack(Material.DIAMOND_SWORD, 1));
                                    } else if (data.Get5().equalsIgnoreCase("DIAMOND_AXE")) {
                                        inv.setItem(4, new ItemStack(Material.DIAMOND_AXE, 1));
                                    } else if (data.Get5().equalsIgnoreCase("BOW")) {
                                        inv.setItem(4, new ItemStack(Material.BOW, 1));
                                    } else if (data.Get5().equalsIgnoreCase("COOKED_BEEF")) {
                                        inv.setItem(4, new ItemStack(Material.COOKED_BEEF, 64));
                                    } else if (data.Get5().equalsIgnoreCase("CROSSBOW")) {
                                        inv.setItem(4, new ItemStack(Material.CROSSBOW, 1));
                                    } else if (data.Get5().equalsIgnoreCase("SHIELD")) {
                                        inv.setItem(4, new ItemStack(Material.SHIELD, 1));
                                    } else if (data.Get5().equalsIgnoreCase("AIR")) {
                                        inv.setItem(4, new ItemStack(Material.AIR, 1));
                                    }

                                    if (data.Get6().equalsIgnoreCase("DIAMOND_SWORD")) {
                                        inv.setItem(5, new ItemStack(Material.DIAMOND_SWORD, 1));
                                    } else if (data.Get6().equalsIgnoreCase("DIAMOND_AXE")) {
                                        inv.setItem(5, new ItemStack(Material.DIAMOND_AXE, 1));
                                    } else if (data.Get6().equalsIgnoreCase("BOW")) {
                                        inv.setItem(5, new ItemStack(Material.BOW, 1));
                                    } else if (data.Get6().equalsIgnoreCase("COOKED_BEEF")) {
                                        inv.setItem(5, new ItemStack(Material.COOKED_BEEF, 64));
                                    } else if (data.Get6().equalsIgnoreCase("CROSSBOW")) {
                                        inv.setItem(5, new ItemStack(Material.CROSSBOW, 1));
                                    } else if (data.Get6().equalsIgnoreCase("SHIELD")) {
                                        inv.setItem(5, new ItemStack(Material.SHIELD, 1));
                                    } else if (data.Get6().equalsIgnoreCase("AIR")) {
                                        inv.setItem(5, new ItemStack(Material.AIR, 1));
                                    }

                                    if (data.Get7().equalsIgnoreCase("DIAMOND_SWORD")) {
                                        inv.setItem(6, new ItemStack(Material.DIAMOND_SWORD, 1));
                                    } else if (data.Get7().equalsIgnoreCase("DIAMOND_AXE")) {
                                        inv.setItem(6, new ItemStack(Material.DIAMOND_AXE, 1));
                                    } else if (data.Get7().equalsIgnoreCase("BOW")) {
                                        inv.setItem(6, new ItemStack(Material.BOW, 1));
                                    } else if (data.Get7().equalsIgnoreCase("COOKED_BEEF")) {
                                        inv.setItem(6, new ItemStack(Material.COOKED_BEEF, 64));
                                    } else if (data.Get7().equalsIgnoreCase("CROSSBOW")) {
                                        inv.setItem(6, new ItemStack(Material.CROSSBOW, 1));
                                    } else if (data.Get7().equalsIgnoreCase("SHIELD")) {
                                        inv.setItem(6, new ItemStack(Material.SHIELD, 1));
                                    } else if (data.Get7().equalsIgnoreCase("AIR")) {
                                        inv.setItem(6, new ItemStack(Material.AIR, 1));
                                    }

                                    if (data.Get8().equalsIgnoreCase("DIAMOND_SWORD")) {
                                        inv.setItem(7, new ItemStack(Material.DIAMOND_SWORD, 1));
                                    } else if (data.Get8().equalsIgnoreCase("DIAMOND_AXE")) {
                                        inv.setItem(7, new ItemStack(Material.DIAMOND_AXE, 1));
                                    } else if (data.Get8().equalsIgnoreCase("BOW")) {
                                        inv.setItem(7, new ItemStack(Material.BOW, 1));
                                    } else if (data.Get8().equalsIgnoreCase("COOKED_BEEF")) {
                                        inv.setItem(7, new ItemStack(Material.COOKED_BEEF, 64));
                                    } else if (data.Get8().equalsIgnoreCase("CROSSBOW")) {
                                        inv.setItem(7, new ItemStack(Material.CROSSBOW, 1));
                                    } else if (data.Get8().equalsIgnoreCase("SHIELD")) {
                                        inv.setItem(7, new ItemStack(Material.SHIELD, 1));
                                    } else if (data.Get8().equalsIgnoreCase("AIR")) {
                                        inv.setItem(7, new ItemStack(Material.AIR, 1));
                                    }

                                    if (data.Get9().equalsIgnoreCase("DIAMOND_SWORD")) {
                                        inv.setItem(8, new ItemStack(Material.DIAMOND_SWORD, 1));
                                    } else if (data.Get9().equalsIgnoreCase("DIAMOND_AXE")) {
                                        inv.setItem(8, new ItemStack(Material.DIAMOND_AXE, 1));
                                    } else if (data.Get9().equalsIgnoreCase("BOW")) {
                                        inv.setItem(8, new ItemStack(Material.BOW, 1));
                                    } else if (data.Get9().equalsIgnoreCase("COOKED_BEEF")) {
                                        inv.setItem(8, new ItemStack(Material.COOKED_BEEF, 64));
                                    } else if (data.Get9().equalsIgnoreCase("CROSSBOW")) {
                                        inv.setItem(8, new ItemStack(Material.CROSSBOW, 1));
                                    } else if (data.Get9().equalsIgnoreCase("SHIELD")) {
                                        inv.setItem(8, new ItemStack(Material.SHIELD, 1));
                                    } else if (data.Get9().equalsIgnoreCase("AIR")) {
                                        inv.setItem(8, new ItemStack(Material.AIR, 1));
                                    }

                                    if (data.GetOffhand().equalsIgnoreCase("DIAMOND_SWORD")) {
                                        player.getInventory().setItemInOffHand(new ItemStack(Material.DIAMOND_SWORD, 1));
                                    } else if (data.GetOffhand().equalsIgnoreCase("DIAMOND_AXE")) {
                                        player.getInventory().setItemInOffHand(new ItemStack(Material.DIAMOND_AXE, 1));
                                    } else if (data.GetOffhand().equalsIgnoreCase("BOW")) {
                                        player.getInventory().setItemInOffHand(new ItemStack(Material.BOW, 1));
                                    } else if (data.GetOffhand().equalsIgnoreCase("COOKED_BEEF")) {
                                        player.getInventory().setItemInOffHand(new ItemStack(Material.COOKED_BEEF, 64));
                                    } else if (data.GetOffhand().equalsIgnoreCase("CROSSBOW")) {
                                        player.getInventory().setItemInOffHand(new ItemStack(Material.CROSSBOW, 1));
                                    } else if (data.GetOffhand().equalsIgnoreCase("SHIELD")) {
                                        player.getInventory().setItemInOffHand(new ItemStack(Material.SHIELD, 1));
                                    } else if (data.GetOffhand().equalsIgnoreCase("AIR")) {
                                        player.getInventory().setItemInOffHand(new ItemStack(Material.AIR, 1));
                                    }
                                } else {
                                    player.getInventory().setStorageContents(weapon);
                                    player.getInventory().setItemInOffHand(shield);
                                }
                                if(data2 != null) {
                                    Inventory inv = target.getInventory();
                                    if (data2.Get1().equalsIgnoreCase("DIAMOND_SWORD")) {
                                        inv.setItem(0, new ItemStack(Material.DIAMOND_SWORD, 1));
                                    } else if (data2.Get1().equalsIgnoreCase("DIAMOND_AXE")) {
                                        inv.setItem(0, new ItemStack(Material.DIAMOND_AXE, 1));
                                    } else if (data2.Get1().equalsIgnoreCase("BOW")) {
                                        inv.setItem(0, new ItemStack(Material.BOW, 1));
                                    } else if (data2.Get1().equalsIgnoreCase("COOKED_BEEF")) {
                                        inv.setItem(0, new ItemStack(Material.COOKED_BEEF, 64));
                                    } else if (data2.Get1().equalsIgnoreCase("CROSSBOW")) {
                                        inv.setItem(0, new ItemStack(Material.CROSSBOW, 1));
                                    } else if (data2.Get1().equalsIgnoreCase("SHIELD")) {
                                        inv.setItem(0, new ItemStack(Material.SHIELD, 1));
                                    } else if (data2.Get1().equalsIgnoreCase("AIR")) {
                                        inv.setItem(0, new ItemStack(Material.AIR, 1));
                                    }

                                    if (data2.Get2().equalsIgnoreCase("DIAMOND_SWORD")) {
                                        inv.setItem(1, new ItemStack(Material.DIAMOND_SWORD, 1));
                                    } else if (data2.Get2().equalsIgnoreCase("DIAMOND_AXE")) {
                                        inv.setItem(1, new ItemStack(Material.DIAMOND_AXE, 1));
                                    } else if (data2.Get2().equalsIgnoreCase("BOW")) {
                                        inv.setItem(1, new ItemStack(Material.BOW, 1));
                                    } else if (data2.Get2().equalsIgnoreCase("COOKED_BEEF")) {
                                        inv.setItem(1, new ItemStack(Material.COOKED_BEEF, 64));
                                    } else if (data2.Get2().equalsIgnoreCase("CROSSBOW")) {
                                        inv.setItem(1, new ItemStack(Material.CROSSBOW, 1));
                                    } else if (data2.Get2().equalsIgnoreCase("SHIELD")) {
                                        inv.setItem(1, new ItemStack(Material.SHIELD, 1));
                                    } else if (data2.Get2().equalsIgnoreCase("AIR")) {
                                        inv.setItem(1, new ItemStack(Material.AIR, 1));
                                    }

                                    if (data2.Get3().equalsIgnoreCase("DIAMOND_SWORD")) {
                                        inv.setItem(2, new ItemStack(Material.DIAMOND_SWORD, 1));
                                    } else if (data2.Get3().equalsIgnoreCase("DIAMOND_AXE")) {
                                        inv.setItem(2, new ItemStack(Material.DIAMOND_AXE, 1));
                                    } else if (data2.Get3().equalsIgnoreCase("BOW")) {
                                        inv.setItem(2, new ItemStack(Material.BOW, 1));
                                    } else if (data2.Get3().equalsIgnoreCase("COOKED_BEEF")) {
                                        inv.setItem(2, new ItemStack(Material.COOKED_BEEF, 64));
                                    } else if (data2.Get3().equalsIgnoreCase("CROSSBOW")) {
                                        inv.setItem(2, new ItemStack(Material.CROSSBOW, 1));
                                    } else if (data2.Get3().equalsIgnoreCase("SHIELD")) {
                                        inv.setItem(2, new ItemStack(Material.SHIELD, 1));
                                    } else if (data2.Get3().equalsIgnoreCase("AIR")) {
                                        inv.setItem(2, new ItemStack(Material.AIR, 1));
                                    }

                                    if (data2.Get4().equalsIgnoreCase("DIAMOND_SWORD")) {
                                        inv.setItem(3, new ItemStack(Material.DIAMOND_SWORD, 1));
                                    } else if (data2.Get4().equalsIgnoreCase("DIAMOND_AXE")) {
                                        inv.setItem(3, new ItemStack(Material.DIAMOND_AXE, 1));
                                    } else if (data2.Get4().equalsIgnoreCase("BOW")) {
                                        inv.setItem(3, new ItemStack(Material.BOW, 1));
                                    } else if (data2.Get4().equalsIgnoreCase("COOKED_BEEF")) {
                                        inv.setItem(3, new ItemStack(Material.COOKED_BEEF, 64));
                                    } else if (data2.Get4().equalsIgnoreCase("CROSSBOW")) {
                                        inv.setItem(3, new ItemStack(Material.CROSSBOW, 1));
                                    } else if (data2.Get4().equalsIgnoreCase("SHIELD")) {
                                        inv.setItem(3, new ItemStack(Material.SHIELD, 1));
                                    } else if (data2.Get4().equalsIgnoreCase("AIR")) {
                                        inv.setItem(3, new ItemStack(Material.AIR, 1));
                                    }

                                    if (data2.Get5().equalsIgnoreCase("DIAMOND_SWORD")) {
                                        inv.setItem(4, new ItemStack(Material.DIAMOND_SWORD, 1));
                                    } else if (data2.Get5().equalsIgnoreCase("DIAMOND_AXE")) {
                                        inv.setItem(4, new ItemStack(Material.DIAMOND_AXE, 1));
                                    } else if (data2.Get5().equalsIgnoreCase("BOW")) {
                                        inv.setItem(4, new ItemStack(Material.BOW, 1));
                                    } else if (data2.Get5().equalsIgnoreCase("COOKED_BEEF")) {
                                        inv.setItem(4, new ItemStack(Material.COOKED_BEEF, 64));
                                    } else if (data2.Get5().equalsIgnoreCase("CROSSBOW")) {
                                        inv.setItem(4, new ItemStack(Material.CROSSBOW, 1));
                                    } else if (data2.Get5().equalsIgnoreCase("SHIELD")) {
                                        inv.setItem(4, new ItemStack(Material.SHIELD, 1));
                                    } else if (data2.Get5().equalsIgnoreCase("AIR")) {
                                        inv.setItem(4, new ItemStack(Material.AIR, 1));
                                    }

                                    if (data2.Get6().equalsIgnoreCase("DIAMOND_SWORD")) {
                                        inv.setItem(5, new ItemStack(Material.DIAMOND_SWORD, 1));
                                    } else if (data2.Get6().equalsIgnoreCase("DIAMOND_AXE")) {
                                        inv.setItem(5, new ItemStack(Material.DIAMOND_AXE, 1));
                                    } else if (data2.Get6().equalsIgnoreCase("BOW")) {
                                        inv.setItem(5, new ItemStack(Material.BOW, 1));
                                    } else if (data2.Get6().equalsIgnoreCase("COOKED_BEEF")) {
                                        inv.setItem(5, new ItemStack(Material.COOKED_BEEF, 64));
                                    } else if (data2.Get6().equalsIgnoreCase("CROSSBOW")) {
                                        inv.setItem(5, new ItemStack(Material.CROSSBOW, 1));
                                    } else if (data2.Get6().equalsIgnoreCase("SHIELD")) {
                                        inv.setItem(5, new ItemStack(Material.SHIELD, 1));
                                    } else if (data2.Get6().equalsIgnoreCase("AIR")) {
                                        inv.setItem(5, new ItemStack(Material.AIR, 1));
                                    }

                                    if (data2.Get7().equalsIgnoreCase("DIAMOND_SWORD")) {
                                        inv.setItem(6, new ItemStack(Material.DIAMOND_SWORD, 1));
                                    } else if (data2.Get7().equalsIgnoreCase("DIAMOND_AXE")) {
                                        inv.setItem(6, new ItemStack(Material.DIAMOND_AXE, 1));
                                    } else if (data2.Get7().equalsIgnoreCase("BOW")) {
                                        inv.setItem(6, new ItemStack(Material.BOW, 1));
                                    } else if (data2.Get7().equalsIgnoreCase("COOKED_BEEF")) {
                                        inv.setItem(6, new ItemStack(Material.COOKED_BEEF, 64));
                                    } else if (data2.Get7().equalsIgnoreCase("CROSSBOW")) {
                                        inv.setItem(6, new ItemStack(Material.CROSSBOW, 1));
                                    } else if (data2.Get7().equalsIgnoreCase("SHIELD")) {
                                        inv.setItem(6, new ItemStack(Material.SHIELD, 1));
                                    } else if (data2.Get7().equalsIgnoreCase("AIR")) {
                                        inv.setItem(6, new ItemStack(Material.AIR, 1));
                                    }

                                    if (data2.Get8().equalsIgnoreCase("DIAMOND_SWORD")) {
                                        inv.setItem(7, new ItemStack(Material.DIAMOND_SWORD, 1));
                                    } else if (data2.Get8().equalsIgnoreCase("DIAMOND_AXE")) {
                                        inv.setItem(7, new ItemStack(Material.DIAMOND_AXE, 1));
                                    } else if (data2.Get8().equalsIgnoreCase("BOW")) {
                                        inv.setItem(7, new ItemStack(Material.BOW, 1));
                                    } else if (data2.Get8().equalsIgnoreCase("COOKED_BEEF")) {
                                        inv.setItem(7, new ItemStack(Material.COOKED_BEEF, 64));
                                    } else if (data2.Get8().equalsIgnoreCase("CROSSBOW")) {
                                        inv.setItem(7, new ItemStack(Material.CROSSBOW, 1));
                                    } else if (data2.Get8().equalsIgnoreCase("SHIELD")) {
                                        inv.setItem(7, new ItemStack(Material.SHIELD, 1));
                                    } else if (data2.Get8().equalsIgnoreCase("AIR")) {
                                        inv.setItem(7, new ItemStack(Material.AIR, 1));
                                    }

                                    if (data2.Get9().equalsIgnoreCase("DIAMOND_SWORD")) {
                                        inv.setItem(8, new ItemStack(Material.DIAMOND_SWORD, 1));
                                    } else if (data2.Get9().equalsIgnoreCase("DIAMOND_AXE")) {
                                        inv.setItem(8, new ItemStack(Material.DIAMOND_AXE, 1));
                                    } else if (data2.Get9().equalsIgnoreCase("BOW")) {
                                        inv.setItem(8, new ItemStack(Material.BOW, 1));
                                    } else if (data2.Get9().equalsIgnoreCase("COOKED_BEEF")) {
                                        inv.setItem(8, new ItemStack(Material.COOKED_BEEF, 64));
                                    } else if (data2.Get9().equalsIgnoreCase("CROSSBOW")) {
                                        inv.setItem(8, new ItemStack(Material.CROSSBOW, 1));
                                    } else if (data2.Get9().equalsIgnoreCase("SHIELD")) {
                                        inv.setItem(8, new ItemStack(Material.SHIELD, 1));
                                    } else if (data2.Get9().equalsIgnoreCase("AIR")) {
                                        inv.setItem(8, new ItemStack(Material.AIR, 1));
                                    }

                                    if (data2.GetOffhand().equalsIgnoreCase("DIAMOND_SWORD")) {
                                        target.getInventory().setItemInOffHand(new ItemStack(Material.DIAMOND_SWORD, 1));
                                    } else if (data2.GetOffhand().equalsIgnoreCase("DIAMOND_AXE")) {
                                        target.getInventory().setItemInOffHand(new ItemStack(Material.DIAMOND_AXE, 1));
                                    } else if (data2.GetOffhand().equalsIgnoreCase("BOW")) {
                                        target.getInventory().setItemInOffHand(new ItemStack(Material.BOW, 1));
                                    } else if (data2.GetOffhand().equalsIgnoreCase("COOKED_BEEF")) {
                                        target.getInventory().setItemInOffHand(new ItemStack(Material.COOKED_BEEF, 64));
                                    } else if (data2.GetOffhand().equalsIgnoreCase("CROSSBOW")) {
                                        target.getInventory().setItemInOffHand(new ItemStack(Material.CROSSBOW, 1));
                                    } else if (data2.GetOffhand().equalsIgnoreCase("SHIELD")) {
                                        target.getInventory().setItemInOffHand(new ItemStack(Material.SHIELD, 1));
                                    } else if (data2.GetOffhand().equalsIgnoreCase("AIR")) {
                                        target.getInventory().setItemInOffHand(new ItemStack(Material.AIR, 1));
                                    }
                                } else {
                                    target.getInventory().setStorageContents(weapon);
                                    target.getInventory().setItemInOffHand(shield);
                                }
                                player.getInventory().setItem(9, arrow);
                                target.getInventory().setItem(9, arrow);
                                player.setGameMode(GameMode.ADVENTURE);
                                target.setGameMode(GameMode.ADVENTURE);
                                player.setHealth(player.getMaxHealth());
                                player.setFoodLevel(20);
                                player.setSaturation(20f);
                                target.setHealth(target.getMaxHealth());
                                target.setFoodLevel(20);
                                target.setSaturation(20f);
                                player.sendMessage(PluginName+"You are currently playing on "+ChatColor.GREEN+"\"Colosseum\"");
                                target.sendMessage(PluginName+"You are currently playing on "+ChatColor.GREEN+"\"Colosseum\"");
                                Core.ingame.put(player.getName(), target.getName());
                                Core.ingame.put(target.getName(), player.getName());
                                Core.playerinmap.put(player.getName(), "Colosseum");
                                Core.playerinmap.put(target.getName(), "Colosseum");
                                Core.maps.put("Colosseum", false);
                                Core.inviteList.remove(player.getName());
                                return true;
                            }else if (Boolean.TRUE.equals(Core.maps.get("Beach"))) {
                                player.sendTitle("\uEF00", null, 10, 30, 20);
                                target.sendTitle("\uEF00", null, 10, 30, 20);
                                target.sendMessage(PluginName + player.getName() + ChatColor.GREEN + " has accept the duel"); // accept
                                Location TeamA = new Location(NetheriteStack, -99.5, 66, -80.5, 180f, 0f);
                                Location TeamB = new Location(NetheriteStack, -99.5, 66, -118.5, 0f, 0f);
                                player.closeInventory();
                                target.closeInventory();
                                player.teleport(TeamA);
                                target.teleport(TeamB);
                                player.setBedSpawnLocation(TeamA, true);
                                target.setBedSpawnLocation(TeamB, true);
                                player.getInventory().clear();
                                target.getInventory().clear();
                                player.getInventory().setArmorContents(armor);
                                target.getInventory().setArmorContents(armor);
                                SlotSQL data = GetPlayerSlot(player.getUniqueId().toString(), "netherite_stack");
                                SlotSQL data2 = GetPlayerSlot(target.getUniqueId().toString(), "netherite_stack");
                                if(data != null) {
                                    Inventory inv = player.getInventory();
                                    if (data.Get1().equalsIgnoreCase("DIAMOND_SWORD")) {
                                        inv.setItem(0, new ItemStack(Material.DIAMOND_SWORD, 1));
                                    } else if (data.Get1().equalsIgnoreCase("DIAMOND_AXE")) {
                                        inv.setItem(0, new ItemStack(Material.DIAMOND_AXE, 1));
                                    } else if (data.Get1().equalsIgnoreCase("BOW")) {
                                        inv.setItem(0, new ItemStack(Material.BOW, 1));
                                    } else if (data.Get1().equalsIgnoreCase("COOKED_BEEF")) {
                                        inv.setItem(0, new ItemStack(Material.COOKED_BEEF, 64));
                                    } else if (data.Get1().equalsIgnoreCase("CROSSBOW")) {
                                        inv.setItem(0, new ItemStack(Material.CROSSBOW, 1));
                                    } else if (data.Get1().equalsIgnoreCase("SHIELD")) {
                                        inv.setItem(0, new ItemStack(Material.SHIELD, 1));
                                    } else if (data.Get1().equalsIgnoreCase("AIR")) {
                                        inv.setItem(0, new ItemStack(Material.AIR, 1));
                                    }

                                    if (data.Get2().equalsIgnoreCase("DIAMOND_SWORD")) {
                                        inv.setItem(1, new ItemStack(Material.DIAMOND_SWORD, 1));
                                    } else if (data.Get2().equalsIgnoreCase("DIAMOND_AXE")) {
                                        inv.setItem(1, new ItemStack(Material.DIAMOND_AXE, 1));
                                    } else if (data.Get2().equalsIgnoreCase("BOW")) {
                                        inv.setItem(1, new ItemStack(Material.BOW, 1));
                                    } else if (data.Get2().equalsIgnoreCase("COOKED_BEEF")) {
                                        inv.setItem(1, new ItemStack(Material.COOKED_BEEF, 64));
                                    } else if (data.Get2().equalsIgnoreCase("CROSSBOW")) {
                                        inv.setItem(1, new ItemStack(Material.CROSSBOW, 1));
                                    } else if (data.Get2().equalsIgnoreCase("SHIELD")) {
                                        inv.setItem(1, new ItemStack(Material.SHIELD, 1));
                                    } else if (data.Get2().equalsIgnoreCase("AIR")) {
                                        inv.setItem(1, new ItemStack(Material.AIR, 1));
                                    }

                                    if (data.Get3().equalsIgnoreCase("DIAMOND_SWORD")) {
                                        inv.setItem(2, new ItemStack(Material.DIAMOND_SWORD, 1));
                                    } else if (data.Get3().equalsIgnoreCase("DIAMOND_AXE")) {
                                        inv.setItem(2, new ItemStack(Material.DIAMOND_AXE, 1));
                                    } else if (data.Get3().equalsIgnoreCase("BOW")) {
                                        inv.setItem(2, new ItemStack(Material.BOW, 1));
                                    } else if (data.Get3().equalsIgnoreCase("COOKED_BEEF")) {
                                        inv.setItem(2, new ItemStack(Material.COOKED_BEEF, 64));
                                    } else if (data.Get3().equalsIgnoreCase("CROSSBOW")) {
                                        inv.setItem(2, new ItemStack(Material.CROSSBOW, 1));
                                    } else if (data.Get3().equalsIgnoreCase("SHIELD")) {
                                        inv.setItem(2, new ItemStack(Material.SHIELD, 1));
                                    } else if (data.Get3().equalsIgnoreCase("AIR")) {
                                        inv.setItem(2, new ItemStack(Material.AIR, 1));
                                    }

                                    if (data.Get4().equalsIgnoreCase("DIAMOND_SWORD")) {
                                        inv.setItem(3, new ItemStack(Material.DIAMOND_SWORD, 1));
                                    } else if (data.Get4().equalsIgnoreCase("DIAMOND_AXE")) {
                                        inv.setItem(3, new ItemStack(Material.DIAMOND_AXE, 1));
                                    } else if (data.Get4().equalsIgnoreCase("BOW")) {
                                        inv.setItem(3, new ItemStack(Material.BOW, 1));
                                    } else if (data.Get4().equalsIgnoreCase("COOKED_BEEF")) {
                                        inv.setItem(3, new ItemStack(Material.COOKED_BEEF, 64));
                                    } else if (data.Get4().equalsIgnoreCase("CROSSBOW")) {
                                        inv.setItem(3, new ItemStack(Material.CROSSBOW, 1));
                                    } else if (data.Get4().equalsIgnoreCase("SHIELD")) {
                                        inv.setItem(3, new ItemStack(Material.SHIELD, 1));
                                    } else if (data.Get4().equalsIgnoreCase("AIR")) {
                                        inv.setItem(3, new ItemStack(Material.AIR, 1));
                                    }

                                    if (data.Get5().equalsIgnoreCase("DIAMOND_SWORD")) {
                                        inv.setItem(4, new ItemStack(Material.DIAMOND_SWORD, 1));
                                    } else if (data.Get5().equalsIgnoreCase("DIAMOND_AXE")) {
                                        inv.setItem(4, new ItemStack(Material.DIAMOND_AXE, 1));
                                    } else if (data.Get5().equalsIgnoreCase("BOW")) {
                                        inv.setItem(4, new ItemStack(Material.BOW, 1));
                                    } else if (data.Get5().equalsIgnoreCase("COOKED_BEEF")) {
                                        inv.setItem(4, new ItemStack(Material.COOKED_BEEF, 64));
                                    } else if (data.Get5().equalsIgnoreCase("CROSSBOW")) {
                                        inv.setItem(4, new ItemStack(Material.CROSSBOW, 1));
                                    } else if (data.Get5().equalsIgnoreCase("SHIELD")) {
                                        inv.setItem(4, new ItemStack(Material.SHIELD, 1));
                                    } else if (data.Get5().equalsIgnoreCase("AIR")) {
                                        inv.setItem(4, new ItemStack(Material.AIR, 1));
                                    }

                                    if (data.Get6().equalsIgnoreCase("DIAMOND_SWORD")) {
                                        inv.setItem(5, new ItemStack(Material.DIAMOND_SWORD, 1));
                                    } else if (data.Get6().equalsIgnoreCase("DIAMOND_AXE")) {
                                        inv.setItem(5, new ItemStack(Material.DIAMOND_AXE, 1));
                                    } else if (data.Get6().equalsIgnoreCase("BOW")) {
                                        inv.setItem(5, new ItemStack(Material.BOW, 1));
                                    } else if (data.Get6().equalsIgnoreCase("COOKED_BEEF")) {
                                        inv.setItem(5, new ItemStack(Material.COOKED_BEEF, 64));
                                    } else if (data.Get6().equalsIgnoreCase("CROSSBOW")) {
                                        inv.setItem(5, new ItemStack(Material.CROSSBOW, 1));
                                    } else if (data.Get6().equalsIgnoreCase("SHIELD")) {
                                        inv.setItem(5, new ItemStack(Material.SHIELD, 1));
                                    } else if (data.Get6().equalsIgnoreCase("AIR")) {
                                        inv.setItem(5, new ItemStack(Material.AIR, 1));
                                    }

                                    if (data.Get7().equalsIgnoreCase("DIAMOND_SWORD")) {
                                        inv.setItem(6, new ItemStack(Material.DIAMOND_SWORD, 1));
                                    } else if (data.Get7().equalsIgnoreCase("DIAMOND_AXE")) {
                                        inv.setItem(6, new ItemStack(Material.DIAMOND_AXE, 1));
                                    } else if (data.Get7().equalsIgnoreCase("BOW")) {
                                        inv.setItem(6, new ItemStack(Material.BOW, 1));
                                    } else if (data.Get7().equalsIgnoreCase("COOKED_BEEF")) {
                                        inv.setItem(6, new ItemStack(Material.COOKED_BEEF, 64));
                                    } else if (data.Get7().equalsIgnoreCase("CROSSBOW")) {
                                        inv.setItem(6, new ItemStack(Material.CROSSBOW, 1));
                                    } else if (data.Get7().equalsIgnoreCase("SHIELD")) {
                                        inv.setItem(6, new ItemStack(Material.SHIELD, 1));
                                    } else if (data.Get7().equalsIgnoreCase("AIR")) {
                                        inv.setItem(6, new ItemStack(Material.AIR, 1));
                                    }

                                    if (data.Get8().equalsIgnoreCase("DIAMOND_SWORD")) {
                                        inv.setItem(7, new ItemStack(Material.DIAMOND_SWORD, 1));
                                    } else if (data.Get8().equalsIgnoreCase("DIAMOND_AXE")) {
                                        inv.setItem(7, new ItemStack(Material.DIAMOND_AXE, 1));
                                    } else if (data.Get8().equalsIgnoreCase("BOW")) {
                                        inv.setItem(7, new ItemStack(Material.BOW, 1));
                                    } else if (data.Get8().equalsIgnoreCase("COOKED_BEEF")) {
                                        inv.setItem(7, new ItemStack(Material.COOKED_BEEF, 64));
                                    } else if (data.Get8().equalsIgnoreCase("CROSSBOW")) {
                                        inv.setItem(7, new ItemStack(Material.CROSSBOW, 1));
                                    } else if (data.Get8().equalsIgnoreCase("SHIELD")) {
                                        inv.setItem(7, new ItemStack(Material.SHIELD, 1));
                                    } else if (data.Get8().equalsIgnoreCase("AIR")) {
                                        inv.setItem(7, new ItemStack(Material.AIR, 1));
                                    }

                                    if (data.Get9().equalsIgnoreCase("DIAMOND_SWORD")) {
                                        inv.setItem(8, new ItemStack(Material.DIAMOND_SWORD, 1));
                                    } else if (data.Get9().equalsIgnoreCase("DIAMOND_AXE")) {
                                        inv.setItem(8, new ItemStack(Material.DIAMOND_AXE, 1));
                                    } else if (data.Get9().equalsIgnoreCase("BOW")) {
                                        inv.setItem(8, new ItemStack(Material.BOW, 1));
                                    } else if (data.Get9().equalsIgnoreCase("COOKED_BEEF")) {
                                        inv.setItem(8, new ItemStack(Material.COOKED_BEEF, 64));
                                    } else if (data.Get9().equalsIgnoreCase("CROSSBOW")) {
                                        inv.setItem(8, new ItemStack(Material.CROSSBOW, 1));
                                    } else if (data.Get9().equalsIgnoreCase("SHIELD")) {
                                        inv.setItem(8, new ItemStack(Material.SHIELD, 1));
                                    } else if (data.Get9().equalsIgnoreCase("AIR")) {
                                        inv.setItem(8, new ItemStack(Material.AIR, 1));
                                    }

                                    if (data.GetOffhand().equalsIgnoreCase("DIAMOND_SWORD")) {
                                        player.getInventory().setItemInOffHand(new ItemStack(Material.DIAMOND_SWORD, 1));
                                    } else if (data.GetOffhand().equalsIgnoreCase("DIAMOND_AXE")) {
                                        player.getInventory().setItemInOffHand(new ItemStack(Material.DIAMOND_AXE, 1));
                                    } else if (data.GetOffhand().equalsIgnoreCase("BOW")) {
                                        player.getInventory().setItemInOffHand(new ItemStack(Material.BOW, 1));
                                    } else if (data.GetOffhand().equalsIgnoreCase("COOKED_BEEF")) {
                                        player.getInventory().setItemInOffHand(new ItemStack(Material.COOKED_BEEF, 64));
                                    } else if (data.GetOffhand().equalsIgnoreCase("CROSSBOW")) {
                                        player.getInventory().setItemInOffHand(new ItemStack(Material.CROSSBOW, 1));
                                    } else if (data.GetOffhand().equalsIgnoreCase("SHIELD")) {
                                        player.getInventory().setItemInOffHand(new ItemStack(Material.SHIELD, 1));
                                    } else if (data.GetOffhand().equalsIgnoreCase("AIR")) {
                                        player.getInventory().setItemInOffHand(new ItemStack(Material.AIR, 1));
                                    }
                                } else {
                                    player.getInventory().setStorageContents(weapon);
                                    player.getInventory().setItemInOffHand(shield);
                                }
                                if(data2 != null) {
                                    Inventory inv = target.getInventory();
                                    if (data2.Get1().equalsIgnoreCase("DIAMOND_SWORD")) {
                                        inv.setItem(0, new ItemStack(Material.DIAMOND_SWORD, 1));
                                    } else if (data2.Get1().equalsIgnoreCase("DIAMOND_AXE")) {
                                        inv.setItem(0, new ItemStack(Material.DIAMOND_AXE, 1));
                                    } else if (data2.Get1().equalsIgnoreCase("BOW")) {
                                        inv.setItem(0, new ItemStack(Material.BOW, 1));
                                    } else if (data2.Get1().equalsIgnoreCase("COOKED_BEEF")) {
                                        inv.setItem(0, new ItemStack(Material.COOKED_BEEF, 64));
                                    } else if (data2.Get1().equalsIgnoreCase("CROSSBOW")) {
                                        inv.setItem(0, new ItemStack(Material.CROSSBOW, 1));
                                    } else if (data2.Get1().equalsIgnoreCase("SHIELD")) {
                                        inv.setItem(0, new ItemStack(Material.SHIELD, 1));
                                    } else if (data2.Get1().equalsIgnoreCase("AIR")) {
                                        inv.setItem(0, new ItemStack(Material.AIR, 1));
                                    }

                                    if (data2.Get2().equalsIgnoreCase("DIAMOND_SWORD")) {
                                        inv.setItem(1, new ItemStack(Material.DIAMOND_SWORD, 1));
                                    } else if (data2.Get2().equalsIgnoreCase("DIAMOND_AXE")) {
                                        inv.setItem(1, new ItemStack(Material.DIAMOND_AXE, 1));
                                    } else if (data2.Get2().equalsIgnoreCase("BOW")) {
                                        inv.setItem(1, new ItemStack(Material.BOW, 1));
                                    } else if (data2.Get2().equalsIgnoreCase("COOKED_BEEF")) {
                                        inv.setItem(1, new ItemStack(Material.COOKED_BEEF, 64));
                                    } else if (data2.Get2().equalsIgnoreCase("CROSSBOW")) {
                                        inv.setItem(1, new ItemStack(Material.CROSSBOW, 1));
                                    } else if (data2.Get2().equalsIgnoreCase("SHIELD")) {
                                        inv.setItem(1, new ItemStack(Material.SHIELD, 1));
                                    } else if (data2.Get2().equalsIgnoreCase("AIR")) {
                                        inv.setItem(1, new ItemStack(Material.AIR, 1));
                                    }

                                    if (data2.Get3().equalsIgnoreCase("DIAMOND_SWORD")) {
                                        inv.setItem(2, new ItemStack(Material.DIAMOND_SWORD, 1));
                                    } else if (data2.Get3().equalsIgnoreCase("DIAMOND_AXE")) {
                                        inv.setItem(2, new ItemStack(Material.DIAMOND_AXE, 1));
                                    } else if (data2.Get3().equalsIgnoreCase("BOW")) {
                                        inv.setItem(2, new ItemStack(Material.BOW, 1));
                                    } else if (data2.Get3().equalsIgnoreCase("COOKED_BEEF")) {
                                        inv.setItem(2, new ItemStack(Material.COOKED_BEEF, 64));
                                    } else if (data2.Get3().equalsIgnoreCase("CROSSBOW")) {
                                        inv.setItem(2, new ItemStack(Material.CROSSBOW, 1));
                                    } else if (data2.Get3().equalsIgnoreCase("SHIELD")) {
                                        inv.setItem(2, new ItemStack(Material.SHIELD, 1));
                                    } else if (data2.Get3().equalsIgnoreCase("AIR")) {
                                        inv.setItem(2, new ItemStack(Material.AIR, 1));
                                    }

                                    if (data2.Get4().equalsIgnoreCase("DIAMOND_SWORD")) {
                                        inv.setItem(3, new ItemStack(Material.DIAMOND_SWORD, 1));
                                    } else if (data2.Get4().equalsIgnoreCase("DIAMOND_AXE")) {
                                        inv.setItem(3, new ItemStack(Material.DIAMOND_AXE, 1));
                                    } else if (data2.Get4().equalsIgnoreCase("BOW")) {
                                        inv.setItem(3, new ItemStack(Material.BOW, 1));
                                    } else if (data2.Get4().equalsIgnoreCase("COOKED_BEEF")) {
                                        inv.setItem(3, new ItemStack(Material.COOKED_BEEF, 64));
                                    } else if (data2.Get4().equalsIgnoreCase("CROSSBOW")) {
                                        inv.setItem(3, new ItemStack(Material.CROSSBOW, 1));
                                    } else if (data2.Get4().equalsIgnoreCase("SHIELD")) {
                                        inv.setItem(3, new ItemStack(Material.SHIELD, 1));
                                    } else if (data2.Get4().equalsIgnoreCase("AIR")) {
                                        inv.setItem(3, new ItemStack(Material.AIR, 1));
                                    }

                                    if (data2.Get5().equalsIgnoreCase("DIAMOND_SWORD")) {
                                        inv.setItem(4, new ItemStack(Material.DIAMOND_SWORD, 1));
                                    } else if (data2.Get5().equalsIgnoreCase("DIAMOND_AXE")) {
                                        inv.setItem(4, new ItemStack(Material.DIAMOND_AXE, 1));
                                    } else if (data2.Get5().equalsIgnoreCase("BOW")) {
                                        inv.setItem(4, new ItemStack(Material.BOW, 1));
                                    } else if (data2.Get5().equalsIgnoreCase("COOKED_BEEF")) {
                                        inv.setItem(4, new ItemStack(Material.COOKED_BEEF, 64));
                                    } else if (data2.Get5().equalsIgnoreCase("CROSSBOW")) {
                                        inv.setItem(4, new ItemStack(Material.CROSSBOW, 1));
                                    } else if (data2.Get5().equalsIgnoreCase("SHIELD")) {
                                        inv.setItem(4, new ItemStack(Material.SHIELD, 1));
                                    } else if (data2.Get5().equalsIgnoreCase("AIR")) {
                                        inv.setItem(4, new ItemStack(Material.AIR, 1));
                                    }

                                    if (data2.Get6().equalsIgnoreCase("DIAMOND_SWORD")) {
                                        inv.setItem(5, new ItemStack(Material.DIAMOND_SWORD, 1));
                                    } else if (data2.Get6().equalsIgnoreCase("DIAMOND_AXE")) {
                                        inv.setItem(5, new ItemStack(Material.DIAMOND_AXE, 1));
                                    } else if (data2.Get6().equalsIgnoreCase("BOW")) {
                                        inv.setItem(5, new ItemStack(Material.BOW, 1));
                                    } else if (data2.Get6().equalsIgnoreCase("COOKED_BEEF")) {
                                        inv.setItem(5, new ItemStack(Material.COOKED_BEEF, 64));
                                    } else if (data2.Get6().equalsIgnoreCase("CROSSBOW")) {
                                        inv.setItem(5, new ItemStack(Material.CROSSBOW, 1));
                                    } else if (data2.Get6().equalsIgnoreCase("SHIELD")) {
                                        inv.setItem(5, new ItemStack(Material.SHIELD, 1));
                                    } else if (data2.Get6().equalsIgnoreCase("AIR")) {
                                        inv.setItem(5, new ItemStack(Material.AIR, 1));
                                    }

                                    if (data2.Get7().equalsIgnoreCase("DIAMOND_SWORD")) {
                                        inv.setItem(6, new ItemStack(Material.DIAMOND_SWORD, 1));
                                    } else if (data2.Get7().equalsIgnoreCase("DIAMOND_AXE")) {
                                        inv.setItem(6, new ItemStack(Material.DIAMOND_AXE, 1));
                                    } else if (data2.Get7().equalsIgnoreCase("BOW")) {
                                        inv.setItem(6, new ItemStack(Material.BOW, 1));
                                    } else if (data2.Get7().equalsIgnoreCase("COOKED_BEEF")) {
                                        inv.setItem(6, new ItemStack(Material.COOKED_BEEF, 64));
                                    } else if (data2.Get7().equalsIgnoreCase("CROSSBOW")) {
                                        inv.setItem(6, new ItemStack(Material.CROSSBOW, 1));
                                    } else if (data2.Get7().equalsIgnoreCase("SHIELD")) {
                                        inv.setItem(6, new ItemStack(Material.SHIELD, 1));
                                    } else if (data2.Get7().equalsIgnoreCase("AIR")) {
                                        inv.setItem(6, new ItemStack(Material.AIR, 1));
                                    }

                                    if (data2.Get8().equalsIgnoreCase("DIAMOND_SWORD")) {
                                        inv.setItem(7, new ItemStack(Material.DIAMOND_SWORD, 1));
                                    } else if (data2.Get8().equalsIgnoreCase("DIAMOND_AXE")) {
                                        inv.setItem(7, new ItemStack(Material.DIAMOND_AXE, 1));
                                    } else if (data2.Get8().equalsIgnoreCase("BOW")) {
                                        inv.setItem(7, new ItemStack(Material.BOW, 1));
                                    } else if (data2.Get8().equalsIgnoreCase("COOKED_BEEF")) {
                                        inv.setItem(7, new ItemStack(Material.COOKED_BEEF, 64));
                                    } else if (data2.Get8().equalsIgnoreCase("CROSSBOW")) {
                                        inv.setItem(7, new ItemStack(Material.CROSSBOW, 1));
                                    } else if (data2.Get8().equalsIgnoreCase("SHIELD")) {
                                        inv.setItem(7, new ItemStack(Material.SHIELD, 1));
                                    } else if (data2.Get8().equalsIgnoreCase("AIR")) {
                                        inv.setItem(7, new ItemStack(Material.AIR, 1));
                                    }

                                    if (data2.Get9().equalsIgnoreCase("DIAMOND_SWORD")) {
                                        inv.setItem(8, new ItemStack(Material.DIAMOND_SWORD, 1));
                                    } else if (data2.Get9().equalsIgnoreCase("DIAMOND_AXE")) {
                                        inv.setItem(8, new ItemStack(Material.DIAMOND_AXE, 1));
                                    } else if (data2.Get9().equalsIgnoreCase("BOW")) {
                                        inv.setItem(8, new ItemStack(Material.BOW, 1));
                                    } else if (data2.Get9().equalsIgnoreCase("COOKED_BEEF")) {
                                        inv.setItem(8, new ItemStack(Material.COOKED_BEEF, 64));
                                    } else if (data2.Get9().equalsIgnoreCase("CROSSBOW")) {
                                        inv.setItem(8, new ItemStack(Material.CROSSBOW, 1));
                                    } else if (data2.Get9().equalsIgnoreCase("SHIELD")) {
                                        inv.setItem(8, new ItemStack(Material.SHIELD, 1));
                                    } else if (data2.Get9().equalsIgnoreCase("AIR")) {
                                        inv.setItem(8, new ItemStack(Material.AIR, 1));
                                    }

                                    if (data2.GetOffhand().equalsIgnoreCase("DIAMOND_SWORD")) {
                                        target.getInventory().setItemInOffHand(new ItemStack(Material.DIAMOND_SWORD, 1));
                                    } else if (data2.GetOffhand().equalsIgnoreCase("DIAMOND_AXE")) {
                                        target.getInventory().setItemInOffHand(new ItemStack(Material.DIAMOND_AXE, 1));
                                    } else if (data2.GetOffhand().equalsIgnoreCase("BOW")) {
                                        target.getInventory().setItemInOffHand(new ItemStack(Material.BOW, 1));
                                    } else if (data2.GetOffhand().equalsIgnoreCase("COOKED_BEEF")) {
                                        target.getInventory().setItemInOffHand(new ItemStack(Material.COOKED_BEEF, 64));
                                    } else if (data2.GetOffhand().equalsIgnoreCase("CROSSBOW")) {
                                        target.getInventory().setItemInOffHand(new ItemStack(Material.CROSSBOW, 1));
                                    } else if (data2.GetOffhand().equalsIgnoreCase("SHIELD")) {
                                        target.getInventory().setItemInOffHand(new ItemStack(Material.SHIELD, 1));
                                    } else if (data2.GetOffhand().equalsIgnoreCase("AIR")) {
                                        target.getInventory().setItemInOffHand(new ItemStack(Material.AIR, 1));
                                    }
                                } else {
                                    target.getInventory().setStorageContents(weapon);
                                    target.getInventory().setItemInOffHand(shield);
                                }
                                player.getInventory().setItem(9, arrow);
                                target.getInventory().setItem(9, arrow);
                                player.setGameMode(GameMode.ADVENTURE);
                                target.setGameMode(GameMode.ADVENTURE);
                                player.setHealth(player.getMaxHealth());
                                player.setFoodLevel(20);
                                player.setSaturation(20f);
                                target.setHealth(target.getMaxHealth());
                                target.setFoodLevel(20);
                                target.setSaturation(20f);
                                player.sendMessage(PluginName+"You are currently playing on "+ChatColor.GREEN+"\"Beach\"");
                                target.sendMessage(PluginName+"You are currently playing on "+ChatColor.GREEN+"\"Beach\"");
                                Core.ingame.put(player.getName(), target.getName());
                                Core.ingame.put(target.getName(), player.getName());
                                Core.playerinmap.put(player.getName(), "Beach");
                                Core.playerinmap.put(target.getName(), "Beach");
                                Core.maps.put("Beach", false);
                                Core.inviteList.remove(player.getName());
                                return true;
                            }else if (Boolean.TRUE.equals(Core.maps.get("Abyss"))) {
                                player.sendTitle("\uEF00", null, 10, 30, 20);
                                target.sendTitle("\uEF00", null, 10, 30, 20);
                                target.sendMessage(PluginName + player.getName() + ChatColor.GREEN + " has accept the duel"); // accept
                                Location TeamA = new Location(NetheriteStack, -99.5, 65, 107.5, 180f, 0f);
                                Location TeamB = new Location(NetheriteStack, -99.5, 65, 93.5, 0f, 0f);
                                player.closeInventory();
                                target.closeInventory();
                                player.teleport(TeamA);
                                target.teleport(TeamB);
                                player.setBedSpawnLocation(TeamA, true);
                                target.setBedSpawnLocation(TeamB, true);
                                player.getInventory().clear();
                                target.getInventory().clear();
                                player.getInventory().setArmorContents(armor);
                                target.getInventory().setArmorContents(armor);
                                SlotSQL data = GetPlayerSlot(player.getUniqueId().toString(), "netherite_stack");
                                SlotSQL data2 = GetPlayerSlot(target.getUniqueId().toString(), "netherite_stack");
                                if(data != null) {
                                    Inventory inv = player.getInventory();
                                    if (data.Get1().equalsIgnoreCase("DIAMOND_SWORD")) {
                                        inv.setItem(0, new ItemStack(Material.DIAMOND_SWORD, 1));
                                    } else if (data.Get1().equalsIgnoreCase("DIAMOND_AXE")) {
                                        inv.setItem(0, new ItemStack(Material.DIAMOND_AXE, 1));
                                    } else if (data.Get1().equalsIgnoreCase("BOW")) {
                                        inv.setItem(0, new ItemStack(Material.BOW, 1));
                                    } else if (data.Get1().equalsIgnoreCase("COOKED_BEEF")) {
                                        inv.setItem(0, new ItemStack(Material.COOKED_BEEF, 64));
                                    } else if (data.Get1().equalsIgnoreCase("CROSSBOW")) {
                                        inv.setItem(0, new ItemStack(Material.CROSSBOW, 1));
                                    } else if (data.Get1().equalsIgnoreCase("SHIELD")) {
                                        inv.setItem(0, new ItemStack(Material.SHIELD, 1));
                                    } else if (data.Get1().equalsIgnoreCase("AIR")) {
                                        inv.setItem(0, new ItemStack(Material.AIR, 1));
                                    }

                                    if (data.Get2().equalsIgnoreCase("DIAMOND_SWORD")) {
                                        inv.setItem(1, new ItemStack(Material.DIAMOND_SWORD, 1));
                                    } else if (data.Get2().equalsIgnoreCase("DIAMOND_AXE")) {
                                        inv.setItem(1, new ItemStack(Material.DIAMOND_AXE, 1));
                                    } else if (data.Get2().equalsIgnoreCase("BOW")) {
                                        inv.setItem(1, new ItemStack(Material.BOW, 1));
                                    } else if (data.Get2().equalsIgnoreCase("COOKED_BEEF")) {
                                        inv.setItem(1, new ItemStack(Material.COOKED_BEEF, 64));
                                    } else if (data.Get2().equalsIgnoreCase("CROSSBOW")) {
                                        inv.setItem(1, new ItemStack(Material.CROSSBOW, 1));
                                    } else if (data.Get2().equalsIgnoreCase("SHIELD")) {
                                        inv.setItem(1, new ItemStack(Material.SHIELD, 1));
                                    } else if (data.Get2().equalsIgnoreCase("AIR")) {
                                        inv.setItem(1, new ItemStack(Material.AIR, 1));
                                    }

                                    if (data.Get3().equalsIgnoreCase("DIAMOND_SWORD")) {
                                        inv.setItem(2, new ItemStack(Material.DIAMOND_SWORD, 1));
                                    } else if (data.Get3().equalsIgnoreCase("DIAMOND_AXE")) {
                                        inv.setItem(2, new ItemStack(Material.DIAMOND_AXE, 1));
                                    } else if (data.Get3().equalsIgnoreCase("BOW")) {
                                        inv.setItem(2, new ItemStack(Material.BOW, 1));
                                    } else if (data.Get3().equalsIgnoreCase("COOKED_BEEF")) {
                                        inv.setItem(2, new ItemStack(Material.COOKED_BEEF, 64));
                                    } else if (data.Get3().equalsIgnoreCase("CROSSBOW")) {
                                        inv.setItem(2, new ItemStack(Material.CROSSBOW, 1));
                                    } else if (data.Get3().equalsIgnoreCase("SHIELD")) {
                                        inv.setItem(2, new ItemStack(Material.SHIELD, 1));
                                    } else if (data.Get3().equalsIgnoreCase("AIR")) {
                                        inv.setItem(2, new ItemStack(Material.AIR, 1));
                                    }

                                    if (data.Get4().equalsIgnoreCase("DIAMOND_SWORD")) {
                                        inv.setItem(3, new ItemStack(Material.DIAMOND_SWORD, 1));
                                    } else if (data.Get4().equalsIgnoreCase("DIAMOND_AXE")) {
                                        inv.setItem(3, new ItemStack(Material.DIAMOND_AXE, 1));
                                    } else if (data.Get4().equalsIgnoreCase("BOW")) {
                                        inv.setItem(3, new ItemStack(Material.BOW, 1));
                                    } else if (data.Get4().equalsIgnoreCase("COOKED_BEEF")) {
                                        inv.setItem(3, new ItemStack(Material.COOKED_BEEF, 64));
                                    } else if (data.Get4().equalsIgnoreCase("CROSSBOW")) {
                                        inv.setItem(3, new ItemStack(Material.CROSSBOW, 1));
                                    } else if (data.Get4().equalsIgnoreCase("SHIELD")) {
                                        inv.setItem(3, new ItemStack(Material.SHIELD, 1));
                                    } else if (data.Get4().equalsIgnoreCase("AIR")) {
                                        inv.setItem(3, new ItemStack(Material.AIR, 1));
                                    }

                                    if (data.Get5().equalsIgnoreCase("DIAMOND_SWORD")) {
                                        inv.setItem(4, new ItemStack(Material.DIAMOND_SWORD, 1));
                                    } else if (data.Get5().equalsIgnoreCase("DIAMOND_AXE")) {
                                        inv.setItem(4, new ItemStack(Material.DIAMOND_AXE, 1));
                                    } else if (data.Get5().equalsIgnoreCase("BOW")) {
                                        inv.setItem(4, new ItemStack(Material.BOW, 1));
                                    } else if (data.Get5().equalsIgnoreCase("COOKED_BEEF")) {
                                        inv.setItem(4, new ItemStack(Material.COOKED_BEEF, 64));
                                    } else if (data.Get5().equalsIgnoreCase("CROSSBOW")) {
                                        inv.setItem(4, new ItemStack(Material.CROSSBOW, 1));
                                    } else if (data.Get5().equalsIgnoreCase("SHIELD")) {
                                        inv.setItem(4, new ItemStack(Material.SHIELD, 1));
                                    } else if (data.Get5().equalsIgnoreCase("AIR")) {
                                        inv.setItem(4, new ItemStack(Material.AIR, 1));
                                    }

                                    if (data.Get6().equalsIgnoreCase("DIAMOND_SWORD")) {
                                        inv.setItem(5, new ItemStack(Material.DIAMOND_SWORD, 1));
                                    } else if (data.Get6().equalsIgnoreCase("DIAMOND_AXE")) {
                                        inv.setItem(5, new ItemStack(Material.DIAMOND_AXE, 1));
                                    } else if (data.Get6().equalsIgnoreCase("BOW")) {
                                        inv.setItem(5, new ItemStack(Material.BOW, 1));
                                    } else if (data.Get6().equalsIgnoreCase("COOKED_BEEF")) {
                                        inv.setItem(5, new ItemStack(Material.COOKED_BEEF, 64));
                                    } else if (data.Get6().equalsIgnoreCase("CROSSBOW")) {
                                        inv.setItem(5, new ItemStack(Material.CROSSBOW, 1));
                                    } else if (data.Get6().equalsIgnoreCase("SHIELD")) {
                                        inv.setItem(5, new ItemStack(Material.SHIELD, 1));
                                    } else if (data.Get6().equalsIgnoreCase("AIR")) {
                                        inv.setItem(5, new ItemStack(Material.AIR, 1));
                                    }

                                    if (data.Get7().equalsIgnoreCase("DIAMOND_SWORD")) {
                                        inv.setItem(6, new ItemStack(Material.DIAMOND_SWORD, 1));
                                    } else if (data.Get7().equalsIgnoreCase("DIAMOND_AXE")) {
                                        inv.setItem(6, new ItemStack(Material.DIAMOND_AXE, 1));
                                    } else if (data.Get7().equalsIgnoreCase("BOW")) {
                                        inv.setItem(6, new ItemStack(Material.BOW, 1));
                                    } else if (data.Get7().equalsIgnoreCase("COOKED_BEEF")) {
                                        inv.setItem(6, new ItemStack(Material.COOKED_BEEF, 64));
                                    } else if (data.Get7().equalsIgnoreCase("CROSSBOW")) {
                                        inv.setItem(6, new ItemStack(Material.CROSSBOW, 1));
                                    } else if (data.Get7().equalsIgnoreCase("SHIELD")) {
                                        inv.setItem(6, new ItemStack(Material.SHIELD, 1));
                                    } else if (data.Get7().equalsIgnoreCase("AIR")) {
                                        inv.setItem(6, new ItemStack(Material.AIR, 1));
                                    }

                                    if (data.Get8().equalsIgnoreCase("DIAMOND_SWORD")) {
                                        inv.setItem(7, new ItemStack(Material.DIAMOND_SWORD, 1));
                                    } else if (data.Get8().equalsIgnoreCase("DIAMOND_AXE")) {
                                        inv.setItem(7, new ItemStack(Material.DIAMOND_AXE, 1));
                                    } else if (data.Get8().equalsIgnoreCase("BOW")) {
                                        inv.setItem(7, new ItemStack(Material.BOW, 1));
                                    } else if (data.Get8().equalsIgnoreCase("COOKED_BEEF")) {
                                        inv.setItem(7, new ItemStack(Material.COOKED_BEEF, 64));
                                    } else if (data.Get8().equalsIgnoreCase("CROSSBOW")) {
                                        inv.setItem(7, new ItemStack(Material.CROSSBOW, 1));
                                    } else if (data.Get8().equalsIgnoreCase("SHIELD")) {
                                        inv.setItem(7, new ItemStack(Material.SHIELD, 1));
                                    } else if (data.Get8().equalsIgnoreCase("AIR")) {
                                        inv.setItem(7, new ItemStack(Material.AIR, 1));
                                    }

                                    if (data.Get9().equalsIgnoreCase("DIAMOND_SWORD")) {
                                        inv.setItem(8, new ItemStack(Material.DIAMOND_SWORD, 1));
                                    } else if (data.Get9().equalsIgnoreCase("DIAMOND_AXE")) {
                                        inv.setItem(8, new ItemStack(Material.DIAMOND_AXE, 1));
                                    } else if (data.Get9().equalsIgnoreCase("BOW")) {
                                        inv.setItem(8, new ItemStack(Material.BOW, 1));
                                    } else if (data.Get9().equalsIgnoreCase("COOKED_BEEF")) {
                                        inv.setItem(8, new ItemStack(Material.COOKED_BEEF, 64));
                                    } else if (data.Get9().equalsIgnoreCase("CROSSBOW")) {
                                        inv.setItem(8, new ItemStack(Material.CROSSBOW, 1));
                                    } else if (data.Get9().equalsIgnoreCase("SHIELD")) {
                                        inv.setItem(8, new ItemStack(Material.SHIELD, 1));
                                    } else if (data.Get9().equalsIgnoreCase("AIR")) {
                                        inv.setItem(8, new ItemStack(Material.AIR, 1));
                                    }

                                    if (data.GetOffhand().equalsIgnoreCase("DIAMOND_SWORD")) {
                                        player.getInventory().setItemInOffHand(new ItemStack(Material.DIAMOND_SWORD, 1));
                                    } else if (data.GetOffhand().equalsIgnoreCase("DIAMOND_AXE")) {
                                        player.getInventory().setItemInOffHand(new ItemStack(Material.DIAMOND_AXE, 1));
                                    } else if (data.GetOffhand().equalsIgnoreCase("BOW")) {
                                        player.getInventory().setItemInOffHand(new ItemStack(Material.BOW, 1));
                                    } else if (data.GetOffhand().equalsIgnoreCase("COOKED_BEEF")) {
                                        player.getInventory().setItemInOffHand(new ItemStack(Material.COOKED_BEEF, 64));
                                    } else if (data.GetOffhand().equalsIgnoreCase("CROSSBOW")) {
                                        player.getInventory().setItemInOffHand(new ItemStack(Material.CROSSBOW, 1));
                                    } else if (data.GetOffhand().equalsIgnoreCase("SHIELD")) {
                                        player.getInventory().setItemInOffHand(new ItemStack(Material.SHIELD, 1));
                                    } else if (data.GetOffhand().equalsIgnoreCase("AIR")) {
                                        player.getInventory().setItemInOffHand(new ItemStack(Material.AIR, 1));
                                    }
                                } else {
                                    player.getInventory().setStorageContents(weapon);
                                    player.getInventory().setItemInOffHand(shield);
                                }
                                if(data2 != null) {
                                    Inventory inv = target.getInventory();
                                    if (data2.Get1().equalsIgnoreCase("DIAMOND_SWORD")) {
                                        inv.setItem(0, new ItemStack(Material.DIAMOND_SWORD, 1));
                                    } else if (data2.Get1().equalsIgnoreCase("DIAMOND_AXE")) {
                                        inv.setItem(0, new ItemStack(Material.DIAMOND_AXE, 1));
                                    } else if (data2.Get1().equalsIgnoreCase("BOW")) {
                                        inv.setItem(0, new ItemStack(Material.BOW, 1));
                                    } else if (data2.Get1().equalsIgnoreCase("COOKED_BEEF")) {
                                        inv.setItem(0, new ItemStack(Material.COOKED_BEEF, 64));
                                    } else if (data2.Get1().equalsIgnoreCase("CROSSBOW")) {
                                        inv.setItem(0, new ItemStack(Material.CROSSBOW, 1));
                                    } else if (data2.Get1().equalsIgnoreCase("SHIELD")) {
                                        inv.setItem(0, new ItemStack(Material.SHIELD, 1));
                                    } else if (data2.Get1().equalsIgnoreCase("AIR")) {
                                        inv.setItem(0, new ItemStack(Material.AIR, 1));
                                    }

                                    if (data2.Get2().equalsIgnoreCase("DIAMOND_SWORD")) {
                                        inv.setItem(1, new ItemStack(Material.DIAMOND_SWORD, 1));
                                    } else if (data2.Get2().equalsIgnoreCase("DIAMOND_AXE")) {
                                        inv.setItem(1, new ItemStack(Material.DIAMOND_AXE, 1));
                                    } else if (data2.Get2().equalsIgnoreCase("BOW")) {
                                        inv.setItem(1, new ItemStack(Material.BOW, 1));
                                    } else if (data2.Get2().equalsIgnoreCase("COOKED_BEEF")) {
                                        inv.setItem(1, new ItemStack(Material.COOKED_BEEF, 64));
                                    } else if (data2.Get2().equalsIgnoreCase("CROSSBOW")) {
                                        inv.setItem(1, new ItemStack(Material.CROSSBOW, 1));
                                    } else if (data2.Get2().equalsIgnoreCase("SHIELD")) {
                                        inv.setItem(1, new ItemStack(Material.SHIELD, 1));
                                    } else if (data2.Get2().equalsIgnoreCase("AIR")) {
                                        inv.setItem(1, new ItemStack(Material.AIR, 1));
                                    }

                                    if (data2.Get3().equalsIgnoreCase("DIAMOND_SWORD")) {
                                        inv.setItem(2, new ItemStack(Material.DIAMOND_SWORD, 1));
                                    } else if (data2.Get3().equalsIgnoreCase("DIAMOND_AXE")) {
                                        inv.setItem(2, new ItemStack(Material.DIAMOND_AXE, 1));
                                    } else if (data2.Get3().equalsIgnoreCase("BOW")) {
                                        inv.setItem(2, new ItemStack(Material.BOW, 1));
                                    } else if (data2.Get3().equalsIgnoreCase("COOKED_BEEF")) {
                                        inv.setItem(2, new ItemStack(Material.COOKED_BEEF, 64));
                                    } else if (data2.Get3().equalsIgnoreCase("CROSSBOW")) {
                                        inv.setItem(2, new ItemStack(Material.CROSSBOW, 1));
                                    } else if (data2.Get3().equalsIgnoreCase("SHIELD")) {
                                        inv.setItem(2, new ItemStack(Material.SHIELD, 1));
                                    } else if (data2.Get3().equalsIgnoreCase("AIR")) {
                                        inv.setItem(2, new ItemStack(Material.AIR, 1));
                                    }

                                    if (data2.Get4().equalsIgnoreCase("DIAMOND_SWORD")) {
                                        inv.setItem(3, new ItemStack(Material.DIAMOND_SWORD, 1));
                                    } else if (data2.Get4().equalsIgnoreCase("DIAMOND_AXE")) {
                                        inv.setItem(3, new ItemStack(Material.DIAMOND_AXE, 1));
                                    } else if (data2.Get4().equalsIgnoreCase("BOW")) {
                                        inv.setItem(3, new ItemStack(Material.BOW, 1));
                                    } else if (data2.Get4().equalsIgnoreCase("COOKED_BEEF")) {
                                        inv.setItem(3, new ItemStack(Material.COOKED_BEEF, 64));
                                    } else if (data2.Get4().equalsIgnoreCase("CROSSBOW")) {
                                        inv.setItem(3, new ItemStack(Material.CROSSBOW, 1));
                                    } else if (data2.Get4().equalsIgnoreCase("SHIELD")) {
                                        inv.setItem(3, new ItemStack(Material.SHIELD, 1));
                                    } else if (data2.Get4().equalsIgnoreCase("AIR")) {
                                        inv.setItem(3, new ItemStack(Material.AIR, 1));
                                    }

                                    if (data2.Get5().equalsIgnoreCase("DIAMOND_SWORD")) {
                                        inv.setItem(4, new ItemStack(Material.DIAMOND_SWORD, 1));
                                    } else if (data2.Get5().equalsIgnoreCase("DIAMOND_AXE")) {
                                        inv.setItem(4, new ItemStack(Material.DIAMOND_AXE, 1));
                                    } else if (data2.Get5().equalsIgnoreCase("BOW")) {
                                        inv.setItem(4, new ItemStack(Material.BOW, 1));
                                    } else if (data2.Get5().equalsIgnoreCase("COOKED_BEEF")) {
                                        inv.setItem(4, new ItemStack(Material.COOKED_BEEF, 64));
                                    } else if (data2.Get5().equalsIgnoreCase("CROSSBOW")) {
                                        inv.setItem(4, new ItemStack(Material.CROSSBOW, 1));
                                    } else if (data2.Get5().equalsIgnoreCase("SHIELD")) {
                                        inv.setItem(4, new ItemStack(Material.SHIELD, 1));
                                    } else if (data2.Get5().equalsIgnoreCase("AIR")) {
                                        inv.setItem(4, new ItemStack(Material.AIR, 1));
                                    }

                                    if (data2.Get6().equalsIgnoreCase("DIAMOND_SWORD")) {
                                        inv.setItem(5, new ItemStack(Material.DIAMOND_SWORD, 1));
                                    } else if (data2.Get6().equalsIgnoreCase("DIAMOND_AXE")) {
                                        inv.setItem(5, new ItemStack(Material.DIAMOND_AXE, 1));
                                    } else if (data2.Get6().equalsIgnoreCase("BOW")) {
                                        inv.setItem(5, new ItemStack(Material.BOW, 1));
                                    } else if (data2.Get6().equalsIgnoreCase("COOKED_BEEF")) {
                                        inv.setItem(5, new ItemStack(Material.COOKED_BEEF, 64));
                                    } else if (data2.Get6().equalsIgnoreCase("CROSSBOW")) {
                                        inv.setItem(5, new ItemStack(Material.CROSSBOW, 1));
                                    } else if (data2.Get6().equalsIgnoreCase("SHIELD")) {
                                        inv.setItem(5, new ItemStack(Material.SHIELD, 1));
                                    } else if (data2.Get6().equalsIgnoreCase("AIR")) {
                                        inv.setItem(5, new ItemStack(Material.AIR, 1));
                                    }

                                    if (data2.Get7().equalsIgnoreCase("DIAMOND_SWORD")) {
                                        inv.setItem(6, new ItemStack(Material.DIAMOND_SWORD, 1));
                                    } else if (data2.Get7().equalsIgnoreCase("DIAMOND_AXE")) {
                                        inv.setItem(6, new ItemStack(Material.DIAMOND_AXE, 1));
                                    } else if (data2.Get7().equalsIgnoreCase("BOW")) {
                                        inv.setItem(6, new ItemStack(Material.BOW, 1));
                                    } else if (data2.Get7().equalsIgnoreCase("COOKED_BEEF")) {
                                        inv.setItem(6, new ItemStack(Material.COOKED_BEEF, 64));
                                    } else if (data2.Get7().equalsIgnoreCase("CROSSBOW")) {
                                        inv.setItem(6, new ItemStack(Material.CROSSBOW, 1));
                                    } else if (data2.Get7().equalsIgnoreCase("SHIELD")) {
                                        inv.setItem(6, new ItemStack(Material.SHIELD, 1));
                                    } else if (data2.Get7().equalsIgnoreCase("AIR")) {
                                        inv.setItem(6, new ItemStack(Material.AIR, 1));
                                    }

                                    if (data2.Get8().equalsIgnoreCase("DIAMOND_SWORD")) {
                                        inv.setItem(7, new ItemStack(Material.DIAMOND_SWORD, 1));
                                    } else if (data2.Get8().equalsIgnoreCase("DIAMOND_AXE")) {
                                        inv.setItem(7, new ItemStack(Material.DIAMOND_AXE, 1));
                                    } else if (data2.Get8().equalsIgnoreCase("BOW")) {
                                        inv.setItem(7, new ItemStack(Material.BOW, 1));
                                    } else if (data2.Get8().equalsIgnoreCase("COOKED_BEEF")) {
                                        inv.setItem(7, new ItemStack(Material.COOKED_BEEF, 64));
                                    } else if (data2.Get8().equalsIgnoreCase("CROSSBOW")) {
                                        inv.setItem(7, new ItemStack(Material.CROSSBOW, 1));
                                    } else if (data2.Get8().equalsIgnoreCase("SHIELD")) {
                                        inv.setItem(7, new ItemStack(Material.SHIELD, 1));
                                    } else if (data2.Get8().equalsIgnoreCase("AIR")) {
                                        inv.setItem(7, new ItemStack(Material.AIR, 1));
                                    }

                                    if (data2.Get9().equalsIgnoreCase("DIAMOND_SWORD")) {
                                        inv.setItem(8, new ItemStack(Material.DIAMOND_SWORD, 1));
                                    } else if (data2.Get9().equalsIgnoreCase("DIAMOND_AXE")) {
                                        inv.setItem(8, new ItemStack(Material.DIAMOND_AXE, 1));
                                    } else if (data2.Get9().equalsIgnoreCase("BOW")) {
                                        inv.setItem(8, new ItemStack(Material.BOW, 1));
                                    } else if (data2.Get9().equalsIgnoreCase("COOKED_BEEF")) {
                                        inv.setItem(8, new ItemStack(Material.COOKED_BEEF, 64));
                                    } else if (data2.Get9().equalsIgnoreCase("CROSSBOW")) {
                                        inv.setItem(8, new ItemStack(Material.CROSSBOW, 1));
                                    } else if (data2.Get9().equalsIgnoreCase("SHIELD")) {
                                        inv.setItem(8, new ItemStack(Material.SHIELD, 1));
                                    } else if (data2.Get9().equalsIgnoreCase("AIR")) {
                                        inv.setItem(8, new ItemStack(Material.AIR, 1));
                                    }

                                    if (data2.GetOffhand().equalsIgnoreCase("DIAMOND_SWORD")) {
                                        target.getInventory().setItemInOffHand(new ItemStack(Material.DIAMOND_SWORD, 1));
                                    } else if (data2.GetOffhand().equalsIgnoreCase("DIAMOND_AXE")) {
                                        target.getInventory().setItemInOffHand(new ItemStack(Material.DIAMOND_AXE, 1));
                                    } else if (data2.GetOffhand().equalsIgnoreCase("BOW")) {
                                        target.getInventory().setItemInOffHand(new ItemStack(Material.BOW, 1));
                                    } else if (data2.GetOffhand().equalsIgnoreCase("COOKED_BEEF")) {
                                        target.getInventory().setItemInOffHand(new ItemStack(Material.COOKED_BEEF, 64));
                                    } else if (data2.GetOffhand().equalsIgnoreCase("CROSSBOW")) {
                                        target.getInventory().setItemInOffHand(new ItemStack(Material.CROSSBOW, 1));
                                    } else if (data2.GetOffhand().equalsIgnoreCase("SHIELD")) {
                                        target.getInventory().setItemInOffHand(new ItemStack(Material.SHIELD, 1));
                                    } else if (data2.GetOffhand().equalsIgnoreCase("AIR")) {
                                        target.getInventory().setItemInOffHand(new ItemStack(Material.AIR, 1));
                                    }
                                } else {
                                    target.getInventory().setStorageContents(weapon);
                                    target.getInventory().setItemInOffHand(shield);
                                }
                                player.getInventory().setItem(9, arrow);
                                target.getInventory().setItem(9, arrow);
                                player.setGameMode(GameMode.ADVENTURE);
                                target.setGameMode(GameMode.ADVENTURE);
                                player.setHealth(player.getMaxHealth());
                                player.setFoodLevel(20);
                                player.setSaturation(20f);
                                target.setHealth(target.getMaxHealth());
                                target.setFoodLevel(20);
                                target.setSaturation(20f);
                                player.sendMessage(PluginName+"You are currently playing on "+ChatColor.GREEN+"\"Abyss\"");
                                target.sendMessage(PluginName+"You are currently playing on "+ChatColor.GREEN+"\"Abyss\"");
                                Core.ingame.put(player.getName(), target.getName());
                                Core.ingame.put(target.getName(), player.getName());
                                Core.playerinmap.put(player.getName(), "Abyss");
                                Core.playerinmap.put(target.getName(), "Abyss");
                                Core.maps.put("Abyss", false);
                                Core.inviteList.remove(player.getName());
                                return true;
                            }else{
                                player.sendMessage(PluginName+ChatColor.RED+"No area available.");
                                Core.inviteList.remove(player.getName());
                                Core.dueltimer.remove(player.getName());
                                return true;
                            }
                        }else if(Core.inviteList.get(player.getName()).containsValue("classicsword")){
                            if (Boolean.TRUE.equals(Core.maps.get("Colosseum2"))) {
                                player.sendTitle("\uEF00", null, 10, 30, 20);
                                target.sendTitle("\uEF00", null, 10, 30, 20);
                                target.sendMessage(PluginName + player.getName() + ChatColor.GREEN + " has accept the duel"); // accept
                                Location TeamA = new Location(ClassicSword, 116.5, 65, -86.5, 180f, 0f);
                                Location TeamB = new Location(ClassicSword, 116.5, 65, -120.5, 0f, 0f);
                                player.closeInventory();
                                target.closeInventory();
                                player.teleport(TeamA);
                                target.teleport(TeamB);
                                player.setBedSpawnLocation(TeamA, true);
                                target.setBedSpawnLocation(TeamB, true);
                                player.getInventory().clear();
                                target.getInventory().clear();
                                player.getInventory().setArmorContents(armor);
                                target.getInventory().setArmorContents(armor);
                                SlotSQL data = GetPlayerSlot(player.getUniqueId().toString(), "classic_sword");
                                SlotSQL data2 = GetPlayerSlot(target.getUniqueId().toString(), "classic_sword");
                                if(data != null) {
                                    Inventory inv = player.getInventory();
                                    if (data.Get1().equalsIgnoreCase("DIAMOND_SWORD")) {
                                        inv.setItem(0, new ItemStack(Material.DIAMOND_SWORD, 1));
                                    } else if (data.Get1().equalsIgnoreCase("COOKED_BEEF")) {
                                        inv.setItem(0, new ItemStack(Material.COOKED_BEEF, 48));
                                    } else if (data.Get1().equalsIgnoreCase("AIR")) {
                                        inv.setItem(0, new ItemStack(Material.AIR, 1));
                                    }

                                    if (data.Get2().equalsIgnoreCase("DIAMOND_SWORD")) {
                                        inv.setItem(1, new ItemStack(Material.DIAMOND_SWORD, 1));
                                    } else if (data.Get2().equalsIgnoreCase("COOKED_BEEF")) {
                                        inv.setItem(1, new ItemStack(Material.COOKED_BEEF, 48));
                                    } else if (data.Get2().equalsIgnoreCase("AIR")) {
                                        inv.setItem(1, new ItemStack(Material.AIR, 1));
                                    }

                                    if (data.Get3().equalsIgnoreCase("DIAMOND_SWORD")) {
                                        inv.setItem(2, new ItemStack(Material.DIAMOND_SWORD, 1));
                                    } else if (data.Get3().equalsIgnoreCase("COOKED_BEEF")) {
                                        inv.setItem(2, new ItemStack(Material.COOKED_BEEF, 48));
                                    } else if (data.Get3().equalsIgnoreCase("AIR")) {
                                        inv.setItem(2, new ItemStack(Material.AIR, 1));
                                    }

                                    if (data.Get4().equalsIgnoreCase("DIAMOND_SWORD")) {
                                        inv.setItem(3, new ItemStack(Material.DIAMOND_SWORD, 1));
                                    } else if (data.Get4().equalsIgnoreCase("COOKED_BEEF")) {
                                        inv.setItem(3, new ItemStack(Material.COOKED_BEEF, 48));
                                    } else if (data.Get4().equalsIgnoreCase("AIR")) {
                                        inv.setItem(3, new ItemStack(Material.AIR, 1));
                                    }

                                    if (data.Get5().equalsIgnoreCase("DIAMOND_SWORD")) {
                                        inv.setItem(4, new ItemStack(Material.DIAMOND_SWORD, 1));
                                    } else if (data.Get5().equalsIgnoreCase("COOKED_BEEF")) {
                                        inv.setItem(4, new ItemStack(Material.COOKED_BEEF, 48));
                                    } else if (data.Get5().equalsIgnoreCase("AIR")) {
                                        inv.setItem(4, new ItemStack(Material.AIR, 1));
                                    }

                                    if (data.Get6().equalsIgnoreCase("DIAMOND_SWORD")) {
                                        inv.setItem(5, new ItemStack(Material.DIAMOND_SWORD, 1));
                                    } else if (data.Get6().equalsIgnoreCase("COOKED_BEEF")) {
                                        inv.setItem(5, new ItemStack(Material.COOKED_BEEF, 48));
                                    } else if (data.Get6().equalsIgnoreCase("AIR")) {
                                        inv.setItem(5, new ItemStack(Material.AIR, 1));
                                    }

                                    if (data.Get7().equalsIgnoreCase("DIAMOND_SWORD")) {
                                        inv.setItem(6, new ItemStack(Material.DIAMOND_SWORD, 1));
                                    } else if (data.Get7().equalsIgnoreCase("COOKED_BEEF")) {
                                        inv.setItem(6, new ItemStack(Material.COOKED_BEEF, 48));
                                    } else if (data.Get7().equalsIgnoreCase("AIR")) {
                                        inv.setItem(6, new ItemStack(Material.AIR, 1));
                                    }

                                    if (data.Get8().equalsIgnoreCase("DIAMOND_SWORD")) {
                                        inv.setItem(7, new ItemStack(Material.DIAMOND_SWORD, 1));
                                    } else if (data.Get8().equalsIgnoreCase("COOKED_BEEF")) {
                                        inv.setItem(7, new ItemStack(Material.COOKED_BEEF, 48));
                                    } else if (data.Get8().equalsIgnoreCase("AIR")) {
                                        inv.setItem(7, new ItemStack(Material.AIR, 1));
                                    }

                                    if (data.Get9().equalsIgnoreCase("DIAMOND_SWORD")) {
                                        inv.setItem(8, new ItemStack(Material.DIAMOND_SWORD, 1));
                                    } else if (data.Get9().equalsIgnoreCase("COOKED_BEEF")) {
                                        inv.setItem(8, new ItemStack(Material.COOKED_BEEF, 48));
                                    } else if (data.Get9().equalsIgnoreCase("AIR")) {
                                        inv.setItem(8, new ItemStack(Material.AIR, 1));
                                    }

                                    if (data.GetOffhand().equalsIgnoreCase("DIAMOND_SWORD")) {
                                        player.getInventory().setItemInOffHand(new ItemStack(Material.DIAMOND_SWORD, 1));
                                    } else if (data.GetOffhand().equalsIgnoreCase("COOKED_BEEF")) {
                                        player.getInventory().setItemInOffHand(new ItemStack(Material.COOKED_BEEF, 48));
                                    } else if (data.GetOffhand().equalsIgnoreCase("AIR")) {
                                        player.getInventory().setItemInOffHand(new ItemStack(Material.AIR, 1));
                                    }
                                } else {
                                    player.getInventory().setItemInOffHand(new ItemStack(Material.COOKED_BEEF, 48));
                                    player.getInventory().setItem(0, new ItemStack(Material.DIAMOND_SWORD, 1));
                                }
                                if(data2 != null) {
                                    Inventory inv = target.getInventory();
                                    if (data2.Get1().equalsIgnoreCase("DIAMOND_SWORD")) {
                                        inv.setItem(0, new ItemStack(Material.DIAMOND_SWORD, 1));
                                    } else if (data2.Get1().equalsIgnoreCase("COOKED_BEEF")) {
                                        inv.setItem(0, new ItemStack(Material.COOKED_BEEF, 48));
                                    } else if (data2.Get1().equalsIgnoreCase("AIR")) {
                                        inv.setItem(0, new ItemStack(Material.AIR, 1));
                                    }

                                    if (data2.Get2().equalsIgnoreCase("DIAMOND_SWORD")) {
                                        inv.setItem(1, new ItemStack(Material.DIAMOND_SWORD, 1));
                                    } else if (data2.Get2().equalsIgnoreCase("COOKED_BEEF")) {
                                        inv.setItem(1, new ItemStack(Material.COOKED_BEEF, 48));
                                    } else if (data2.Get2().equalsIgnoreCase("AIR")) {
                                        inv.setItem(1, new ItemStack(Material.AIR, 1));
                                    }

                                    if (data2.Get3().equalsIgnoreCase("DIAMOND_SWORD")) {
                                        inv.setItem(2, new ItemStack(Material.DIAMOND_SWORD, 1));
                                    } else if (data2.Get3().equalsIgnoreCase("COOKED_BEEF")) {
                                        inv.setItem(2, new ItemStack(Material.COOKED_BEEF, 48));
                                    } else if (data2.Get3().equalsIgnoreCase("AIR")) {
                                        inv.setItem(2, new ItemStack(Material.AIR, 1));
                                    }

                                    if (data2.Get4().equalsIgnoreCase("DIAMOND_SWORD")) {
                                        inv.setItem(3, new ItemStack(Material.DIAMOND_SWORD, 1));
                                    } else if (data2.Get4().equalsIgnoreCase("COOKED_BEEF")) {
                                        inv.setItem(3, new ItemStack(Material.COOKED_BEEF, 48));
                                    } else if (data2.Get4().equalsIgnoreCase("AIR")) {
                                        inv.setItem(3, new ItemStack(Material.AIR, 1));
                                    }

                                    if (data2.Get5().equalsIgnoreCase("DIAMOND_SWORD")) {
                                        inv.setItem(4, new ItemStack(Material.DIAMOND_SWORD, 1));
                                    } else if (data2.Get5().equalsIgnoreCase("COOKED_BEEF")) {
                                        inv.setItem(4, new ItemStack(Material.COOKED_BEEF, 48));
                                    } else if (data2.Get5().equalsIgnoreCase("AIR")) {
                                        inv.setItem(4, new ItemStack(Material.AIR, 1));
                                    }

                                    if (data2.Get6().equalsIgnoreCase("DIAMOND_SWORD")) {
                                        inv.setItem(5, new ItemStack(Material.DIAMOND_SWORD, 1));
                                    } else if (data2.Get6().equalsIgnoreCase("COOKED_BEEF")) {
                                        inv.setItem(5, new ItemStack(Material.COOKED_BEEF, 48));
                                    } else if (data2.Get6().equalsIgnoreCase("AIR")) {
                                        inv.setItem(5, new ItemStack(Material.AIR, 1));
                                    }

                                    if (data2.Get7().equalsIgnoreCase("DIAMOND_SWORD")) {
                                        inv.setItem(6, new ItemStack(Material.DIAMOND_SWORD, 1));
                                    } else if (data2.Get7().equalsIgnoreCase("COOKED_BEEF")) {
                                        inv.setItem(6, new ItemStack(Material.COOKED_BEEF, 48));
                                    } else if (data2.Get7().equalsIgnoreCase("AIR")) {
                                        inv.setItem(6, new ItemStack(Material.AIR, 1));
                                    }

                                    if (data2.Get8().equalsIgnoreCase("DIAMOND_SWORD")) {
                                        inv.setItem(7, new ItemStack(Material.DIAMOND_SWORD, 1));
                                    } else if (data2.Get8().equalsIgnoreCase("COOKED_BEEF")) {
                                        inv.setItem(7, new ItemStack(Material.COOKED_BEEF, 48));
                                    } else if (data2.Get8().equalsIgnoreCase("AIR")) {
                                        inv.setItem(7, new ItemStack(Material.AIR, 1));
                                    }

                                    if (data2.Get9().equalsIgnoreCase("DIAMOND_SWORD")) {
                                        inv.setItem(8, new ItemStack(Material.DIAMOND_SWORD, 1));
                                    } else if (data2.Get9().equalsIgnoreCase("COOKED_BEEF")) {
                                        inv.setItem(8, new ItemStack(Material.COOKED_BEEF, 48));
                                    } else if (data2.Get9().equalsIgnoreCase("AIR")) {
                                        inv.setItem(8, new ItemStack(Material.AIR, 1));
                                    }

                                    if (data2.GetOffhand().equalsIgnoreCase("DIAMOND_SWORD")) {
                                        target.getInventory().setItemInOffHand(new ItemStack(Material.DIAMOND_SWORD, 1));
                                    } else if (data2.GetOffhand().equalsIgnoreCase("COOKED_BEEF")) {
                                        target.getInventory().setItemInOffHand(new ItemStack(Material.COOKED_BEEF, 48));
                                    } else if (data2.GetOffhand().equalsIgnoreCase("AIR")) {
                                        target.getInventory().setItemInOffHand(new ItemStack(Material.AIR, 1));
                                    }
                                } else {
                                    target.getInventory().setItemInOffHand(new ItemStack(Material.COOKED_BEEF, 48));
                                    target.getInventory().setItem(0, new ItemStack(Material.DIAMOND_SWORD, 1));
                                }
                                player.setGameMode(GameMode.ADVENTURE);
                                target.setGameMode(GameMode.ADVENTURE);
                                player.setHealth(player.getMaxHealth());
                                player.setFoodLevel(20);
                                player.setSaturation(20f);
                                target.setHealth(target.getMaxHealth());
                                target.setFoodLevel(20);
                                target.setSaturation(20f);
                                player.sendMessage(PluginName+"You are currently playing on "+ChatColor.GREEN+"\"Colosseum\"");
                                target.sendMessage(PluginName+"You are currently playing on "+ChatColor.GREEN+"\"Colosseum\"");
                                Core.ingame.put(player.getName(), target.getName());
                                Core.ingame.put(target.getName(), player.getName());
                                classic_sword.Lives.put(player.getName(), 3);
                                classic_sword.Lives.put(target.getName(), 3);
                                Core.playerinmap.put(player.getName(), "Colosseum2");
                                Core.playerinmap.put(target.getName(), "Colosseum2");
                                Core.maps.put("Colosseum2", false);
                                Core.inviteList.remove(player.getName());
                                return true;
                            }else{
                                player.sendMessage(PluginName+ChatColor.RED+"No area available.");
                                Core.inviteList.remove(player.getName());
                                Core.dueltimer.remove(player.getName());
                            }
                        } else {
                            player.sendMessage(PluginName + ChatColor.RED + "Game: " + game);
                        }
                    }
                } else if (args[0].equalsIgnoreCase("reject")) { // reject
                    if (target == null) {
                        player.sendMessage(PluginName + ChatColor.RED + "That player is offline!");
                        return true;
                    }
                    if (!(Core.inviteList.containsKey(player.getName()))) {

                        player.sendMessage(PluginName + target.getName() + " doesn't invite you to duel!");
                        return true;

                    } else if (Core.inviteList.containsKey(player.getName())) {

                        player.sendMessage(PluginName +ChatColor.GREEN+ "You have rejected the duels"); // reject
                        target.sendMessage(PluginName + player.getName() +ChatColor.RED+ " has reject the duels"); // reject
                        Core.inviteList.remove(player.getName());
                        Core.dueltimer.remove(player.getName());
                        return true;
                    }
                    sender.sendMessage(ChatColor.YELLOW + " Plugin help:");
                    sender.sendMessage(ChatColor.RED + "/duel " + args[0].toLowerCase() + " <player> <game>");
                    return true;
                }
            } else if (args.length == 3) {
                if (Core.duel.get(args[0].toLowerCase()) == null) {
                    sender.sendMessage(ChatColor.YELLOW + " Plugin help:");
                    sender.sendMessage(ChatColor.RED + "/duel <invite/accept/reject> <player> <game>");
                    return true;
                }
                Player argplayer = getServer().getPlayer(args[1]);
                if (argplayer == null) {
                    sender.sendMessage("Player " + ChatColor.GRAY + args[1].toLowerCase() + ChatColor.RESET + " could not be found");
                    return true;
                }
                if (Core.games.get(args[2].toLowerCase()) == null) {
                    player.sendMessage(PluginName + "You need to specify the game!");
                    player.sendMessage(PluginName + game);
                    return true;
                }
                    Player target = Bukkit.getPlayer(args[1]);
                    if (args[0].equalsIgnoreCase("invite")) {

                        if (target == null) {

                            player.sendMessage(PluginName + ChatColor.RED + "That player is offline!");
                            return true;

                        }if (target.getName().equals(player.getName())) {

                            player.sendMessage(PluginName + ChatColor.RED + "You can't duel yourself!");
                            return true;

                        }if (Core.ingame.get(target.getName()) != null){

                            player.sendMessage(PluginName + ChatColor.RED + "That player is currently in a game!");
                            return true;

                        }if (Core.inviteList.get(target.getName()) != null){

                            player.sendMessage(PluginName + ChatColor.RED + "That player already has invite!");
                            return true;

                        } else {

                            // send to player with usage
                            TextComponent message = new TextComponent(PluginName+"§r    §7⟫⟫ §r");
                            TextComponent message2 = new TextComponent(PluginName+"§r    §7⟫⟫ §r");
                            message.setColor(net.md_5.bungee.api.ChatColor.GRAY);
                            message2.setColor(net.md_5.bungee.api.ChatColor.GRAY);
                            ComponentBuilder click_to_accept = new ComponentBuilder("Click To ").bold(false).color(net.md_5.bungee.api.ChatColor.GRAY).append("Accept").bold(false).color(net.md_5.bungee.api.ChatColor.GREEN);
                            ComponentBuilder click_to_reject = new ComponentBuilder("Click To ").bold(false).color(net.md_5.bungee.api.ChatColor.GRAY).append("Reject").bold(false).color(net.md_5.bungee.api.ChatColor.RED);
                            TextComponent accept = new TextComponent("Accept");
                            TextComponent reject = new TextComponent("Reject");
                            accept.setColor(net.md_5.bungee.api.ChatColor.GREEN);
                            accept.setBold(true);
                            accept.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, click_to_accept.create()));
                            accept.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/duel accept "+playerName));
                            reject.setColor(net.md_5.bungee.api.ChatColor.RED);
                            reject.setBold(true);
                            reject.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, click_to_reject.create()));
                            reject.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/duel reject "+playerName));
                            message.addExtra(accept);
                            message2.addExtra(reject);
                            target.sendMessage(PluginName + GetGroupColorPlayer(player) + playerName + "§e has invited you to §7"+args[2].toUpperCase()+"§e Duels!");
                            target.sendMessage(PluginName + ChatColor.YELLOW + "You have 20 seconds to accept.");
                            target.sendMessage(message);
                            target.sendMessage(message2);
                            // send Clickable Message to player
                            Core.dueltimer.put(target.getName(), 20);
                            HashMap<String, String> values = new HashMap<>();
                            values.put(player.getName(), args[2].toLowerCase());
                            Core.inviteList.put(target.getName(), values); // adds player to duels List
                            player.sendMessage(PluginName+"Duel have been sent to "+target.getName());

                        }

                    } else {

                        player.sendMessage(usage);
                        player.sendMessage(PluginName + "Game -> " + game);
                    }
                    // when send invite done

                    return true;
                } else {
                    sender.sendMessage(ChatColor.YELLOW + " Plugin help:");
                    sender.sendMessage(ChatColor.RED + "/duel <invite/accept/reject> <player> <game>");
                    return true;
                }
            }
        return true;
    }
    @Override
    public java.util.List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String alias, String[] args) {
        Player p = (Player) sender;
        if (args.length == 1) {
            List<String> argruments = new ArrayList<>();
            argruments.add("invite");
            argruments.add("accept");
            argruments.add("reject");
            argruments.add("stats");
            if(p.hasPermission("rank.admin")){
                argruments.add("kick");
            }
            argruments.add("leave");

            return argruments;
        } else if (args.length == 2) {
            ArrayList<String> playerNames = new ArrayList<>();
            Player[] players = new Player[Bukkit.getServer().getOnlinePlayers().size()];
            Bukkit.getServer().getOnlinePlayers().toArray(players);

            return playerNames;
        } else if (args.length == 3) {
            List<String> argruments = new ArrayList<>();
            argruments.add("NetheriteStack");
            argruments.add("ClassicSword");

            return argruments;
        }
        return null;
    }
}