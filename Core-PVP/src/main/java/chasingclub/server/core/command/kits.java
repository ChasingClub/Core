package chasingclub.server.core.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import static chasingclub.server.core.Utils.Utils.PluginName;

public class kits implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (p.hasPermission("ranks.admin")) {
                if (args.length > 0) {
                    Player target = Bukkit.getPlayer(args[0]);
                    if (args[1].equalsIgnoreCase("netherite")) {

                        ItemStack[] armor = new ItemStack[4];
                        armor[0] = new ItemStack(Material.NETHERITE_BOOTS);
                        armor[1] = new ItemStack(Material.NETHERITE_LEGGINGS);
                        armor[2] = new ItemStack(Material.NETHERITE_CHESTPLATE);
                        armor[3] = new ItemStack(Material.NETHERITE_HELMET);

                        ItemStack[] weapon = new ItemStack[6];
                        weapon[0] = new ItemStack(Material.NETHERITE_SWORD);
                        weapon[1] = new ItemStack(Material.NETHERITE_AXE);
                        weapon[2] = new ItemStack(Material.BOW);
                        weapon[3] = new ItemStack(Material.CROSSBOW);
                        weapon[4] = new ItemStack(Material.ARROW, 32);
                        weapon[5] = new ItemStack(Material.COOKED_BEEF, 64);

                        target.getInventory().setArmorContents(armor);
                        target.getInventory().setStorageContents(weapon);
                        p.sendMessage(PluginName + args[1] + " kit has been equiped!");

                    } else if (args[1].equalsIgnoreCase("diamond")) {

                        ItemStack[] armor = new ItemStack[4];
                        armor[0] = new ItemStack(Material.DIAMOND_BOOTS);
                        armor[1] = new ItemStack(Material.DIAMOND_LEGGINGS);
                        armor[2] = new ItemStack(Material.DIAMOND_CHESTPLATE);
                        armor[3] = new ItemStack(Material.DIAMOND_HELMET);

                        ItemStack[] weapon = new ItemStack[6];
                        weapon[0] = new ItemStack(Material.DIAMOND_SWORD);
                        weapon[1] = new ItemStack(Material.DIAMOND_AXE);
                        weapon[2] = new ItemStack(Material.BOW);
                        weapon[3] = new ItemStack(Material.CROSSBOW);
                        weapon[4] = new ItemStack(Material.ARROW, 32);
                        weapon[5] = new ItemStack(Material.COOKED_BEEF, 64);

                        target.getInventory().setArmorContents(armor);
                        target.getInventory().setStorageContents(weapon);
                        p.sendMessage(PluginName + args[1] + " kit has been equiped!");

                    } else if (args[1].equalsIgnoreCase("gold")) {

                        ItemStack[] armor = new ItemStack[4];
                        armor[0] = new ItemStack(Material.GOLDEN_BOOTS);
                        armor[1] = new ItemStack(Material.GOLDEN_LEGGINGS);
                        armor[2] = new ItemStack(Material.GOLDEN_CHESTPLATE);
                        armor[3] = new ItemStack(Material.GOLDEN_HELMET);

                        ItemStack[] weapon = new ItemStack[6];
                        weapon[0] = new ItemStack(Material.GOLDEN_SWORD);
                        weapon[1] = new ItemStack(Material.GOLDEN_AXE);
                        weapon[2] = new ItemStack(Material.BOW);
                        weapon[3] = new ItemStack(Material.CROSSBOW);
                        weapon[4] = new ItemStack(Material.ARROW, 32);
                        weapon[5] = new ItemStack(Material.COOKED_BEEF, 64);

                        target.getInventory().setArmorContents(armor);
                        target.getInventory().setStorageContents(weapon);
                        p.sendMessage(PluginName + args[1] + " kit has been equiped!");


                    } else if (args[1].equalsIgnoreCase("iron")) {

                        ItemStack[] armor = new ItemStack[4];
                        armor[0] = new ItemStack(Material.IRON_BOOTS);
                        armor[1] = new ItemStack(Material.IRON_LEGGINGS);
                        armor[2] = new ItemStack(Material.IRON_CHESTPLATE);
                        armor[3] = new ItemStack(Material.IRON_HELMET);

                        ItemStack[] weapon = new ItemStack[6];
                        weapon[0] = new ItemStack(Material.IRON_SWORD);
                        weapon[1] = new ItemStack(Material.IRON_AXE);
                        weapon[2] = new ItemStack(Material.BOW);
                        weapon[3] = new ItemStack(Material.CROSSBOW);
                        weapon[4] = new ItemStack(Material.ARROW, 32);
                        weapon[5] = new ItemStack(Material.COOKED_BEEF, 64);

                        target.getInventory().setArmorContents(armor);
                        target.getInventory().setStorageContents(weapon);
                        p.sendMessage(PluginName + args[1] + " kit has been equiped!");

                    } else if (args[1].equalsIgnoreCase("leather")) {

                        ItemStack[] armor = new ItemStack[4];
                        armor[0] = new ItemStack(Material.LEATHER_BOOTS);
                        armor[1] = new ItemStack(Material.LEATHER_LEGGINGS);
                        armor[2] = new ItemStack(Material.LEATHER_CHESTPLATE);
                        armor[3] = new ItemStack(Material.LEATHER_HELMET);

                        ItemStack[] weapon = new ItemStack[6];
                        weapon[0] = new ItemStack(Material.WOODEN_SWORD);
                        weapon[1] = new ItemStack(Material.WOODEN_AXE);
                        weapon[2] = new ItemStack(Material.BOW);
                        weapon[3] = new ItemStack(Material.CROSSBOW);
                        weapon[4] = new ItemStack(Material.ARROW, 32);
                        weapon[5] = new ItemStack(Material.COOKED_BEEF, 64);

                        target.getInventory().setArmorContents(armor);
                        target.getInventory().setStorageContents(weapon);
                        p.sendMessage(PluginName + args[1] + " kit has been equiped!");

                    } else if (args[1].equalsIgnoreCase("chainmail")) {

                        ItemStack[] armor = new ItemStack[4];
                        armor[0] = new ItemStack(Material.CHAINMAIL_BOOTS);
                        armor[1] = new ItemStack(Material.CHAINMAIL_LEGGINGS);
                        armor[2] = new ItemStack(Material.CHAINMAIL_CHESTPLATE);
                        armor[3] = new ItemStack(Material.CHAINMAIL_HELMET);

                        ItemStack[] weapon = new ItemStack[6];
                        weapon[0] = new ItemStack(Material.IRON_SWORD);
                        weapon[1] = new ItemStack(Material.IRON_AXE);
                        weapon[2] = new ItemStack(Material.BOW);
                        weapon[3] = new ItemStack(Material.CROSSBOW);
                        weapon[4] = new ItemStack(Material.ARROW, 32);
                        weapon[5] = new ItemStack(Material.COOKED_BEEF, 64);

                        target.getInventory().setArmorContents(armor);
                        target.getInventory().setStorageContents(weapon);
                        p.sendMessage(PluginName + args[1] + " kit has been equiped!");

                    } else {
                        p.sendMessage(PluginName + "You need to provide the name of the kit\n/getkit <Name> [" + ChatColor.WHITE + " Netherite " + ChatColor.YELLOW + "|" + ChatColor.WHITE + " Diamond " + ChatColor.YELLOW + "|" + ChatColor.WHITE + " Gold " + ChatColor.YELLOW + "|" + ChatColor.WHITE + " Iron " + ChatColor.YELLOW + "|" + ChatColor.WHITE + " Leather " + ChatColor.AQUA + "]");
                    }
                } else {
                    p.sendMessage(PluginName + "You need to provide the name of the kit\n/getkit <Name> [" + ChatColor.WHITE + " Netherite " + ChatColor.YELLOW + "|" + ChatColor.WHITE + " Diamond " + ChatColor.YELLOW + "|" + ChatColor.WHITE + " Gold " + ChatColor.YELLOW + "|" + ChatColor.WHITE + " Iron " + ChatColor.YELLOW + "|" + ChatColor.WHITE + " Leather " + ChatColor.AQUA + "]");
                }
            } else {
                sender.sendMessage("You need to be player to sent the command");
            }
        }
        return true;
    }
}