package chasingclub.server.core.command;

import static chasingclub.server.core.Core.combatList;
import static chasingclub.server.core.Utils.Utils.PluginName;
import static org.bukkit.Bukkit.getServer;

import chasingclub.server.core.Core;
import org.bukkit.*;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import vanish.itdragclick.api.vanish.VanishAPI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

    public String usage = PluginName + ChatColor.YELLOW + "/duel <invite/accept/reject> <player>";
    public String game = "NetheriteStack, DodgeBall, Paintball, ClassicIron, ClassicDiamond, OP, Crystal";

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) { // player

            Player player = (Player) sender;
            String playerName = player.getName();
            World NetheriteStack = Bukkit.getServer().getWorld("netherite_game");

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
                    }if (Core.playerinmap.get(player.getName()).equals("Beach")){
                        Core.maps.put("Beach", true);
                    }if (Core.playerinmap.get(player.getName()).equals("Abyss")){
                        Core.maps.put("Abyss", true);
                    }
                    Core.playerinmap.remove(player.getName());
                    Core.playerinmap.remove(target.getName());
                    Core.ingame.remove(target.getName());
                    Core.ingame.remove(player.getName());
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
                    if (VanishAPI.isInvisible(target)) {

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
                    Core.dueltimer.remove(player.getName());
                    if (Core.inviteList.get(player.getName()) != null) {

                        if (Core.inviteList.get(player.getName()).containsValue("netheritestack")) {

                            if (Boolean.TRUE.equals(Core.maps.get("Colosseum"))) {
                                target.sendMessage(PluginName + player.getName() + ChatColor.GREEN + " has accept the duel"); // accept
                                Location TeamA = new Location(NetheriteStack, 0.5, 66, 17.5, 180f, 0f);
                                Location TeamB = new Location(NetheriteStack, 0.5, 66, -16.5, 0f, 0f);
                                player.teleport(TeamA);
                                target.teleport(TeamB);
                                player.setBedSpawnLocation(TeamA, true);
                                target.setBedSpawnLocation(TeamB, true);
                                player.getInventory().setArmorContents(armor);
                                target.getInventory().setArmorContents(armor);
                                player.getInventory().setStorageContents(weapon);
                                target.getInventory().setStorageContents(weapon);
                                player.getInventory().setItemInOffHand(shield);
                                target.getInventory().setItemInOffHand(shield);
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
                                target.sendMessage(PluginName + player.getName() + ChatColor.GREEN + " has accept the duel"); // accept
                                Location TeamA = new Location(NetheriteStack, -99.5, 66, -80.5, 180f, 0f);
                                Location TeamB = new Location(NetheriteStack, -99.5, 66, -118.5, 0f, 0f);
                                player.teleport(TeamA);
                                target.teleport(TeamB);
                                player.setBedSpawnLocation(TeamA, true);
                                target.setBedSpawnLocation(TeamB, true);
                                player.getInventory().setArmorContents(armor);
                                target.getInventory().setArmorContents(armor);
                                player.getInventory().setStorageContents(weapon);
                                target.getInventory().setStorageContents(weapon);
                                player.getInventory().setItemInOffHand(shield);
                                target.getInventory().setItemInOffHand(shield);
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
                                target.sendMessage(PluginName + player.getName() + ChatColor.GREEN + " has accept the duel"); // accept
                                Location TeamA = new Location(NetheriteStack, -99.5, 65, 107.5, 180f, 0f);
                                Location TeamB = new Location(NetheriteStack, -99.5, 65, 93.5, 0f, 0f);
                                player.teleport(TeamA);
                                target.teleport(TeamB);
                                player.setBedSpawnLocation(TeamA, true);
                                target.setBedSpawnLocation(TeamB, true);
                                player.getInventory().setArmorContents(armor);
                                target.getInventory().setArmorContents(armor);
                                player.getInventory().setStorageContents(weapon);
                                target.getInventory().setStorageContents(weapon);
                                player.getInventory().setItemInOffHand(shield);
                                target.getInventory().setItemInOffHand(shield);
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
                                return true;
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

                        player.sendMessage(PluginName + target.getName() +ChatColor.GREEN+ " has reject the duels"); // reject
                        target.sendMessage(PluginName + player.getName() +ChatColor.GRAY+ " has reject the duels"); // reject
                        Core.inviteList.remove(player.getName());
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

                        }if (VanishAPI.isInvisible(target)) {

                            player.sendMessage(PluginName + ChatColor.RED + "That player is offline!");
                            return true;

                        }if (target.getName().equals(player.getName())) {

                            player.sendMessage(PluginName + ChatColor.RED + "You can't duel yourself!");
                            return true;

                        }if (Core.ingame.get(target.getName()) != null){

                            player.sendMessage(PluginName + ChatColor.RED + "That player is currently in a game!");
                            return true;

                        }if (Core.inviteList.get(target.getName()) != null){

                            player.sendMessage(PluginName + ChatColor.RED + "You already invite that player!");
                            return true;

                        } else {

                            // send to player with usage
                            target.sendMessage(PluginName + ChatColor.GREEN + " " + playerName + " invites you for duels");
                            target.sendMessage(PluginName + ChatColor.GREEN + " invite will be remove in 20s");
                            target.sendMessage(PluginName + ChatColor.GREEN + "Use /duel <accept/reject> " + playerName);

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
    public java.util.List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args) {
        Player p = (Player) sender;
        if (args.length == 1) {
            List<String> argruments = new ArrayList<>();
            argruments.add("invite");
            argruments.add("accept");
            argruments.add("reject");
            argruments.add("leave");

            return argruments;
        } else if (args.length == 2) {
            ArrayList<String> playerNames = new ArrayList<>();
            Player[] players = new Player[Bukkit.getServer().getOnlinePlayers().size()];
            Bukkit.getServer().getOnlinePlayers().toArray(players);
            for (Player player : players) {
                if (!(player.getName().equals(p.getName()))) {
                    playerNames.add(player.getName());
                }
            }

            return playerNames;
        } else if (args.length == 3) {
            List<String> argruments = new ArrayList<>();
            argruments.add("NetheriteStack");
            argruments.add("ComingSoon...");

            return argruments;
        }
        return null;
    }
}