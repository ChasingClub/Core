package core.server.core.command;

import core.server.core.Core;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import static org.bukkit.Bukkit.getServer;

public class givekit implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (cmd.getName().equalsIgnoreCase("givekit")) {
            // Check for arguments
            if (args.length == 0) {
                if (!(sender instanceof Player)) {
                    sender.sendMessage("Player Not Found");
                    return true;
                }else{
                    if (!(sender.hasPermission("rank.admin"))) {
                        sender.sendMessage(ChatColor.RED+"You Don't have permission to do that!");
                        return true;
                    }
                    if (sender instanceof Player) {
                        sender.sendMessage("/givekit <player>" + ChatColor.GRAY + " - " + ChatColor.GOLD + "Give kit player you want.");
                        return true;
                    }
                }
            } else if (args.length == 1) {
                if (sender.hasPermission("rank.admin")) {
                    Player argplayer = getServer().getPlayer(args[0]);
                    if (argplayer == null) {
                        sender.sendMessage("Player " + ChatColor.GRAY + args[0] + ChatColor.RESET + " could not be found");
                        return true;
                    }
                    // Send command
                    givekite(argplayer);
                    return true;
                } else {
                    sender.sendMessage(ChatColor.RED+"You Don't have permission to do that!");
                    return true;
                }
            } else {
                sender.sendMessage("/givekit <player>" + ChatColor.GRAY + " - " + ChatColor.GOLD + "Give kit player you want.");
                return true;
            }

        }
        return true;
    }

    // GIVE KITSSSS
    public void givekite(Player p){
        Inventory inv = p.getInventory();
        ItemStack[] armor = new ItemStack[4];
        ItemStack arrow = new ItemStack(Material.ARROW, 48);
        armor[0] = new ItemStack(Material.IRON_BOOTS, 1);
        armor[1] = new ItemStack(Material.IRON_LEGGINGS, 1);
        armor[2] = new ItemStack(Material.IRON_CHESTPLATE, 1);
        armor[3] = new ItemStack(Material.IRON_HELMET, 1);
        ItemMeta imh = armor[0].getItemMeta();
        ItemMeta imc = armor[1].getItemMeta();
        ItemMeta iml = armor[2].getItemMeta();
        ItemMeta imb = armor[3].getItemMeta();
        ItemStack shield = new ItemStack(Material.SHIELD, 1);
        ItemMeta ims = shield.getItemMeta();
        imh.setUnbreakable(true);
        imc.setUnbreakable(true);
        iml.setUnbreakable(true);
        imb.setUnbreakable(true);
        ims.setUnbreakable(true);
        armor[0].setItemMeta(imh);
        armor[1].setItemMeta(imc);
        armor[2].setItemMeta(iml);
        armor[3].setItemMeta(imb);
        shield.setItemMeta(ims);
        armor[0].addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
        armor[1].addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
        armor[2].addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
        armor[3].addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);

        if (p.getGameMode() != GameMode.CREATIVE && p.getGameMode() != GameMode.SPECTATOR) {
            if (Core.kits.get(p) == "bow") {
                ItemStack[] itbb = new ItemStack[2];
                itbb[0] = new ItemStack(Material.DIAMOND_SWORD, 1);
                itbb[1] = new ItemStack(Material.DIAMOND_AXE, 1);
                ItemStack B1 = new ItemStack(Material.BOW, 1);
                ItemMeta imbb1 = itbb[0].getItemMeta();
                ItemMeta imbb2 = itbb[1].getItemMeta();
                ItemMeta B11 = B1.getItemMeta();
                imbb1.setUnbreakable(true);
                imbb2.setUnbreakable(true);
                B11.setUnbreakable(true);
                itbb[0].setItemMeta(imbb1);
                itbb[1].setItemMeta(imbb1);
                B1.setItemMeta(B11);
                itbb[0].addEnchantment(Enchantment.DAMAGE_ALL, 3);
                itbb[0].addEnchantment(Enchantment.KNOCKBACK, 1);
                itbb[1].addEnchantment(Enchantment.DAMAGE_ALL, 1);
                B1.addEnchantment(Enchantment.ARROW_DAMAGE, 3);
                B1.addEnchantment(Enchantment.ARROW_INFINITE, 1);
                B1.addEnchantment(Enchantment.ARROW_KNOCKBACK, 1);

                inv.clear();
                p.getInventory().setArmorContents(armor);
                p.getInventory().setStorageContents(itbb);
                p.getInventory().setItemInOffHand(B1);
                inv.addItem(new ItemStack(Material.COOKED_BEEF, 64));
                inv.addItem(new ItemStack(Material.GOLDEN_APPLE, 4));
                inv.setItem(9, new ItemStack(Material.ARROW, 1));
            }if (Core.kits.get(p) == "Default") {
                ItemStack[] itd = new ItemStack[4];
                itd[0] = new ItemStack(Material.DIAMOND_SWORD, 1);
                itd[1] = new ItemStack(Material.DIAMOND_AXE, 1);
                itd[2] = new ItemStack(Material.BOW, 1);
                itd[3] = new ItemStack(Material.CROSSBOW, 1);
                ItemMeta imd1 = itd[0].getItemMeta();
                ItemMeta imd2 = itd[1].getItemMeta();
                ItemMeta imd3 = itd[2].getItemMeta();
                ItemMeta imd4 = itd[3].getItemMeta();
                imd1.setUnbreakable(true);
                imd2.setUnbreakable(true);
                imd3.setUnbreakable(true);
                imd4.setUnbreakable(true);
                itd[0].setItemMeta(imd1);
                itd[1].setItemMeta(imd2);
                itd[2].setItemMeta(imd3);
                itd[3].setItemMeta(imd4);
                itd[0].addEnchantment(Enchantment.DAMAGE_ALL, 2);
                itd[2].addEnchantment(Enchantment.ARROW_DAMAGE, 1);
                itd[3].addEnchantment(Enchantment.PIERCING, 1);
                itd[3].addEnchantment(Enchantment.QUICK_CHARGE, 1);

                inv.clear();
                p.getInventory().setItemInOffHand(shield);
                p.getInventory().setArmorContents(armor);
                p.getInventory().setStorageContents(itd);
                inv.addItem(new ItemStack(Material.COOKED_BEEF, 64));
                inv.addItem(new ItemStack(Material.GOLDEN_APPLE, 4));
                inv.setItem(9, arrow);
            }if (Core.kits.get(p) == "trident") {
                ItemStack[] itt = new ItemStack[2];
                itt[0] = new ItemStack(Material.DIAMOND_SWORD, 1);
                itt[1] = new ItemStack(Material.DIAMOND_AXE, 1);
                ItemStack trident = new ItemStack(Material.TRIDENT, 1);
                ItemMeta imt1 = itt[0].getItemMeta();
                ItemMeta imt2 = itt[1].getItemMeta();
                ItemMeta imt3 = trident.getItemMeta();
                imt1.setUnbreakable(true);
                imt2.setUnbreakable(true);
                imt3.setUnbreakable(true);
                itt[0].setItemMeta(imt1);
                itt[1].setItemMeta(imt2);
                trident.setItemMeta(imt3);
                itt[0].addEnchantment(Enchantment.DAMAGE_ALL, 3);
                itt[0].addEnchantment(Enchantment.KNOCKBACK, 1);
                itt[1].addEnchantment(Enchantment.DAMAGE_ALL, 1);
                trident.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 5);
                trident.addEnchantment(Enchantment.IMPALING, 3);
                trident.addEnchantment(Enchantment.LOYALTY, 3);

                inv.clear();
                p.getInventory().setArmorContents(armor);
                p.getInventory().setStorageContents(itt);
                p.getInventory().setItemInOffHand(trident);
                inv.addItem(new ItemStack(Material.COOKED_BEEF, 64));
                inv.addItem(new ItemStack(Material.GOLDEN_APPLE, 4));
                inv.setItem(9, arrow);
            }if (Core.kits.get(p) == "viking") {
                ItemStack[] itb = new ItemStack[1];
                itb[0] = new ItemStack(Material.DIAMOND_AXE, 1);
                ItemStack CB1 = new ItemStack(Material.CROSSBOW, 1);
                ItemMeta imb1 = itb[0].getItemMeta();
                ItemMeta CB11 = CB1.getItemMeta();
                imb1.setUnbreakable(true);
                CB11.setUnbreakable(true);
                itb[0].setItemMeta(imb1);
                CB1.setItemMeta(CB11);
                itb[0].addEnchantment(Enchantment.DAMAGE_ALL, 3);
                CB1.addEnchantment(Enchantment.PIERCING, 3);
                CB1.addEnchantment(Enchantment.QUICK_CHARGE, 1);

                inv.clear();
                p.getInventory().setArmorContents(armor);
                p.getInventory().setStorageContents(itb);
                p.getInventory().setItemInOffHand(CB1);
                inv.addItem(new ItemStack(Material.COOKED_BEEF, 64));
                inv.addItem(new ItemStack(Material.GOLDEN_APPLE, 4));
                inv.setItem(9, arrow);
            }if (Core.kits.get(p) == "admin") {
                ItemStack[] armora = new ItemStack[4];
                armora[0] = new ItemStack(Material.IRON_BOOTS, 1);
                ItemMeta imah = armora[0].getItemMeta();
                armora[1] = new ItemStack(Material.IRON_LEGGINGS, 1);
                ItemMeta imac = armora[1].getItemMeta();
                armora[2] = new ItemStack(Material.IRON_CHESTPLATE, 1);
                ItemMeta imal = armora[2].getItemMeta();
                armora[3] = new ItemStack(Material.IRON_HELMET, 1);
                ItemMeta imab = armora[3].getItemMeta();
                imah.setUnbreakable(true);
                imac.setUnbreakable(true);
                imal.setUnbreakable(true);
                imab.setUnbreakable(true);
                armora[0].setItemMeta(imah);
                armora[1].setItemMeta(imac);
                armora[2].setItemMeta(imal);
                armora[3].setItemMeta(imab);
                armora[0].addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 255);
                armora[0].addUnsafeEnchantment(Enchantment.DEPTH_STRIDER, 5);
                armora[0].addUnsafeEnchantment(Enchantment.THORNS, 255);
                armora[1].addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 255);
                armora[1].addUnsafeEnchantment(Enchantment.THORNS, 255);
                armora[1].addUnsafeEnchantment(Enchantment.SWIFT_SNEAK, 255);
                armora[2].addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 255);
                armora[2].addUnsafeEnchantment(Enchantment.THORNS, 255);
                armora[3].addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 255);
                armora[3].addUnsafeEnchantment(Enchantment.THORNS, 255);
                armora[3].addUnsafeEnchantment(Enchantment.OXYGEN, 255);
                ItemStack[] ita = new ItemStack[4];
                ita[0] = new ItemStack(Material.DIAMOND_SWORD, 1);
                ita[1] = new ItemStack(Material.DIAMOND_AXE, 1);
                ita[2] = new ItemStack(Material.BOW, 1);
                ita[3] = new ItemStack(Material.CROSSBOW, 1);
                ItemMeta ima1 = ita[0].getItemMeta();
                ItemMeta ima2 = ita[1].getItemMeta();
                ItemMeta ima3 = ita[2].getItemMeta();
                ItemMeta ima4 = ita[3].getItemMeta();
                ima1.setUnbreakable(true);
                ima2.setUnbreakable(true);
                ima3.setUnbreakable(true);
                ima4.setUnbreakable(true);
                ita[0].setItemMeta(ima1);
                ita[1].setItemMeta(ima2);
                ita[2].setItemMeta(ima3);
                ita[3].setItemMeta(ima4);
                ita[0].addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 255);
                ita[0].addUnsafeEnchantment(Enchantment.FIRE_ASPECT, 255);
                ita[0].addUnsafeEnchantment(Enchantment.KNOCKBACK, 255);
                ita[1].addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 255);
                ita[2].addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, 255);
                ita[2].addUnsafeEnchantment(Enchantment.ARROW_FIRE, 255);
                ita[2].addUnsafeEnchantment(Enchantment.ARROW_KNOCKBACK, 255);
                ita[2].addUnsafeEnchantment(Enchantment.ARROW_INFINITE, 1);
                ita[3].addUnsafeEnchantment(Enchantment.PIERCING, 255);
                ita[3].addUnsafeEnchantment(Enchantment.MULTISHOT, 255);
                ita[3].addUnsafeEnchantment(Enchantment.QUICK_CHARGE, 5);

                inv.clear();
                p.getInventory().setItemInOffHand(shield);
                p.getInventory().setArmorContents(armora);
                p.getInventory().setStorageContents(ita);
                inv.addItem(new ItemStack(Material.COOKED_BEEF, 64));
                inv.addItem(new ItemStack(Material.GOLDEN_APPLE, 64));
                inv.addItem(new ItemStack(Material.ENCHANTED_GOLDEN_APPLE, 64));
                inv.setItem(9, arrow);
            }
        }
    }
}
