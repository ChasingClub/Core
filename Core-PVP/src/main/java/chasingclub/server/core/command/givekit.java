package chasingclub.server.core.command;

import chasingclub.server.core.Core;
import chasingclub.server.core.Utils.SlotSQL;
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
import org.bukkit.inventory.meta.CrossbowMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static chasingclub.server.core.Utils.Database.FindSlotByUUID;
import static org.bukkit.Bukkit.getServer;

public class givekit implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, Command cmd, @NotNull String commandLabel, String[] args) {
        if (cmd.getName().equalsIgnoreCase("givekit")) {
            // Check for arguments
            if (args.length == 0) {
                if (!(sender instanceof Player)) {
                    sender.sendMessage("Player Not Found");
                }else{
                    if (!(sender.hasPermission("rank.admin"))) {
                        sender.sendMessage(ChatColor.RED+"You Don't have permission to do that!");
                        return true;
                    }
                    sender.sendMessage("/givekit <player>" + ChatColor.GRAY + " - " + ChatColor.GOLD + "Give kit player you want.");
                    return true;
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
                } else {
                    sender.sendMessage(ChatColor.RED+"You don't have permission to do that!");
                    return true;
                }
            } else {
                sender.sendMessage("/givekit <player>" + ChatColor.GRAY + " - " + ChatColor.GOLD + "Give kit player you want.");
                return true;
            }
        }
        return true;
    }
    public static SlotSQL GetPlayerSlot(String uuid, String table){
        try {
            return FindSlotByUUID(uuid,table);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    // GIVE KITSSSS
    public static void givekite(Player p){
        p.closeInventory();
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
            if (Core.kits.get(p).equals("Archer")) {
                /////////////////////////////////////////////////////////////////////////////
                SlotSQL data = GetPlayerSlot(p.getUniqueId().toString(), "archer_kit");
                if(data != null){
                    ItemStack sword = new ItemStack(Material.DIAMOND_SWORD, 1);
                    ItemMeta swordm = sword.getItemMeta();
                    swordm.setUnbreakable(true);
                    sword.setItemMeta(swordm);
                    sword.addEnchantment(Enchantment.DAMAGE_ALL, 3);
                    sword.addEnchantment(Enchantment.KNOCKBACK, 1);
                    ItemStack axe = new ItemStack(Material.DIAMOND_AXE, 1);
                    ItemMeta axem = axe.getItemMeta();
                    axem.setUnbreakable(true);
                    axe.setItemMeta(axem);
                    axe.addEnchantment(Enchantment.DAMAGE_ALL, 1);
                    ItemStack bow = new ItemStack(Material.BOW, 1);
                    ItemMeta bowm = bow.getItemMeta();
                    bowm.setUnbreakable(true);
                    bow.setItemMeta(bowm);
                    bow.addEnchantment(Enchantment.ARROW_DAMAGE, 3);
                    bow.addEnchantment(Enchantment.ARROW_INFINITE, 1);
                    bow.addEnchantment(Enchantment.ARROW_KNOCKBACK, 1);
                    ItemStack food = new ItemStack(Material.COOKED_BEEF, 48);
                    ItemStack apple = new ItemStack(Material.GOLDEN_APPLE, 4);

                    if(data.Get1().equalsIgnoreCase("DIAMOND_SWORD")){
                        inv.setItem(0, sword);
                    }else if(data.Get1().equalsIgnoreCase("DIAMOND_AXE")){
                        inv.setItem(0, axe);
                    }else if(data.Get1().equalsIgnoreCase("BOW")){
                        inv.setItem(0, bow);
                    }else if(data.Get1().equalsIgnoreCase("COOKED_BEEF")){
                        inv.setItem(0, food);
                    }else if(data.Get1().equalsIgnoreCase("GOLDEN_APPLE")){
                        inv.setItem(0, apple);
                    }else if(data.Get1().equalsIgnoreCase("AIR")){
                        inv.setItem(0, new ItemStack(Material.AIR, 1));
                    }

                    if(data.Get2().equalsIgnoreCase("DIAMOND_SWORD")){
                        inv.setItem(1, sword);
                    }else if(data.Get2().equalsIgnoreCase("DIAMOND_AXE")){
                        inv.setItem(1, axe);
                    }else if(data.Get2().equalsIgnoreCase("BOW")){
                        inv.setItem(1, bow);
                    }else if(data.Get2().equalsIgnoreCase("COOKED_BEEF")){
                        inv.setItem(1, food);
                    }else if(data.Get2().equalsIgnoreCase("GOLDEN_APPLE")){
                        inv.setItem(1, apple);
                    }else if(data.Get2().equalsIgnoreCase("AIR")){
                        inv.setItem(1, new ItemStack(Material.AIR, 1));
                    }

                    if(data.Get3().equalsIgnoreCase("DIAMOND_SWORD")){
                        inv.setItem(2, sword);
                    }else if(data.Get3().equalsIgnoreCase("DIAMOND_AXE")){
                        inv.setItem(2, axe);
                    }else if(data.Get3().equalsIgnoreCase("BOW")){
                        inv.setItem(2, bow);
                    }else if(data.Get3().equalsIgnoreCase("COOKED_BEEF")){
                        inv.setItem(2, food);
                    }else if(data.Get3().equalsIgnoreCase("GOLDEN_APPLE")){
                        inv.setItem(2, apple);
                    }else if(data.Get3().equalsIgnoreCase("AIR")){
                        inv.setItem(2, new ItemStack(Material.AIR, 1));
                    }

                    if(data.Get4().equalsIgnoreCase("DIAMOND_SWORD")){
                        inv.setItem(3, sword);
                    }else if(data.Get4().equalsIgnoreCase("DIAMOND_AXE")){
                        inv.setItem(3, axe);
                    }else if(data.Get4().equalsIgnoreCase("BOW")){
                        inv.setItem(3, bow);
                    }else if(data.Get4().equalsIgnoreCase("COOKED_BEEF")){
                        inv.setItem(3, food);
                    }else if(data.Get4().equalsIgnoreCase("GOLDEN_APPLE")){
                        inv.setItem(3, apple);
                    }else if(data.Get4().equalsIgnoreCase("AIR")){
                        inv.setItem(3, new ItemStack(Material.AIR, 1));
                    }

                    if(data.Get5().equalsIgnoreCase("DIAMOND_SWORD")){
                        inv.setItem(4, sword);
                    }else if(data.Get5().equalsIgnoreCase("DIAMOND_AXE")){
                        inv.setItem(4, axe);
                    }else if(data.Get5().equalsIgnoreCase("BOW")){
                        inv.setItem(4, bow);
                    }else if(data.Get5().equalsIgnoreCase("COOKED_BEEF")){
                        inv.setItem(4, food);
                    }else if(data.Get5().equalsIgnoreCase("GOLDEN_APPLE")){
                        inv.setItem(4, apple);
                    }else if(data.Get5().equalsIgnoreCase("AIR")){
                        inv.setItem(4, new ItemStack(Material.AIR, 1));
                    }

                    if(data.Get6().equalsIgnoreCase("DIAMOND_SWORD")){
                        inv.setItem(5, sword);
                    }else if(data.Get6().equalsIgnoreCase("DIAMOND_AXE")){
                        inv.setItem(5, axe);
                    }else if(data.Get6().equalsIgnoreCase("BOW")){
                        inv.setItem(5, bow);
                    }else if(data.Get6().equalsIgnoreCase("COOKED_BEEF")){
                        inv.setItem(5, food);
                    }else if(data.Get6().equalsIgnoreCase("GOLDEN_APPLE")){
                        inv.setItem(5, apple);
                    }else if(data.Get6().equalsIgnoreCase("AIR")){
                        inv.setItem(5, new ItemStack(Material.AIR, 1));
                    }

                    if(data.Get7().equalsIgnoreCase("DIAMOND_SWORD")){
                        inv.setItem(6, sword);
                    }else if(data.Get7().equalsIgnoreCase("DIAMOND_AXE")){
                        inv.setItem(6, axe);
                    }else if(data.Get7().equalsIgnoreCase("BOW")){
                        inv.setItem(6, bow);
                    }else if(data.Get7().equalsIgnoreCase("COOKED_BEEF")){
                        inv.setItem(6, food);
                    }else if(data.Get7().equalsIgnoreCase("GOLDEN_APPLE")){
                        inv.setItem(6, apple);
                    }else if(data.Get7().equalsIgnoreCase("AIR")){
                        inv.setItem(6, new ItemStack(Material.AIR, 1));
                    }

                    if(data.Get8().equalsIgnoreCase("DIAMOND_SWORD")){
                        inv.setItem(7, sword);
                    }else if(data.Get8().equalsIgnoreCase("DIAMOND_AXE")){
                        inv.setItem(7, axe);
                    }else if(data.Get8().equalsIgnoreCase("BOW")){
                        inv.setItem(7, bow);
                    }else if(data.Get8().equalsIgnoreCase("COOKED_BEEF")){
                        inv.setItem(7, food);
                    }else if(data.Get8().equalsIgnoreCase("GOLDEN_APPLE")){
                        inv.setItem(7, apple);
                    }else if(data.Get8().equalsIgnoreCase("AIR")){
                        inv.setItem(7, new ItemStack(Material.AIR, 1));
                    }

                    if(data.Get9().equalsIgnoreCase("DIAMOND_SWORD")){
                        inv.setItem(8, sword);
                    }else if(data.Get9().equalsIgnoreCase("DIAMOND_AXE")){
                        inv.setItem(8, axe);
                    }else if(data.Get9().equalsIgnoreCase("BOW")){
                        inv.setItem(8, bow);
                    }else if(data.Get9().equalsIgnoreCase("COOKED_BEEF")){
                        inv.setItem(8, food);
                    }else if(data.Get9().equalsIgnoreCase("GOLDEN_APPLE")){
                        inv.setItem(8, apple);
                    }else if(data.Get9().equalsIgnoreCase("AIR")){
                        inv.setItem(8, new ItemStack(Material.AIR, 1));
                    }

                    if(data.GetOffhand().equalsIgnoreCase("DIAMOND_SWORD")){
                        p.getInventory().setItemInOffHand(sword);
                    }else if(data.GetOffhand().equalsIgnoreCase("DIAMOND_AXE")){
                        p.getInventory().setItemInOffHand(axe);
                    }else if(data.GetOffhand().equalsIgnoreCase("BOW")){
                        p.getInventory().setItemInOffHand(bow);
                    }else if(data.GetOffhand().equalsIgnoreCase("COOKED_BEEF")){
                        p.getInventory().setItemInOffHand(food);
                    }else if(data.GetOffhand().equalsIgnoreCase("GOLDEN_APPLE")){
                        p.getInventory().setItemInOffHand(apple);
                    }else if(data.GetOffhand().equalsIgnoreCase("AIR")){
                        p.getInventory().setItemInOffHand(new ItemStack(Material.AIR, 1));
                    }
                    inv.setItem(9,new ItemStack(Material.ARROW, 1));
                    p.getInventory().setArmorContents(armor);
                    return;
                }
                /////////////////////////////////////////////////////////////////////////////
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
            }if (Core.kits.get(p).equals("Default")) {
                /////////////////////////////////////////////////////////////////////////////
                SlotSQL data = GetPlayerSlot(p.getUniqueId().toString(), "default_kit");
                if(data != null){
                    ItemStack sword = new ItemStack(Material.DIAMOND_SWORD, 1);
                    ItemMeta swordm = sword.getItemMeta();
                    swordm.setUnbreakable(true);
                    sword.setItemMeta(swordm);
                    sword.addEnchantment(Enchantment.DAMAGE_ALL, 2);
                    ItemStack axe = new ItemStack(Material.DIAMOND_AXE, 1);
                    ItemMeta axem = axe.getItemMeta();
                    axem.setUnbreakable(true);
                    axe.setItemMeta(axem);
                    ItemStack bow = new ItemStack(Material.BOW, 1);
                    ItemMeta bowm = bow.getItemMeta();
                    bowm.setUnbreakable(true);
                    bow.setItemMeta(bowm);
                    bow.addEnchantment(Enchantment.ARROW_DAMAGE, 1);
                    ItemStack cbow = new ItemStack(Material.CROSSBOW, 1);
                    CrossbowMeta cbowm = (CrossbowMeta) cbow.getItemMeta();
                    List<ItemStack> pjt = new ArrayList<>();
                    pjt.add(new ItemStack(Material.ARROW, 1));
                    cbowm.setChargedProjectiles(pjt);
                    cbowm.setUnbreakable(true);
                    cbow.setItemMeta(cbowm);
                    cbow.addEnchantment(Enchantment.PIERCING, 1);
                    cbow.addEnchantment(Enchantment.QUICK_CHARGE, 1);
                    ItemStack food = new ItemStack(Material.COOKED_BEEF, 48);
                    ItemStack apple = new ItemStack(Material.GOLDEN_APPLE, 4);

                    if(data.Get1().equalsIgnoreCase("DIAMOND_SWORD")){
                        inv.setItem(0, sword);
                    }else if(data.Get1().equalsIgnoreCase("DIAMOND_AXE")){
                        inv.setItem(0, axe);
                    }else if(data.Get1().equalsIgnoreCase("BOW")){
                        inv.setItem(0, bow);
                    }else if(data.Get1().equalsIgnoreCase("COOKED_BEEF")){
                        inv.setItem(0, food);
                    }else if(data.Get1().equalsIgnoreCase("GOLDEN_APPLE")){
                        inv.setItem(0, apple);
                    }else if(data.Get1().equalsIgnoreCase("CROSSBOW")){
                        inv.setItem(0, cbow);
                    }else if(data.Get1().equalsIgnoreCase("SHIELD")){
                        inv.setItem(0, shield);
                    }else if(data.Get1().equalsIgnoreCase("AIR")){
                        inv.setItem(0, new ItemStack(Material.AIR, 1));
                    }

                    if(data.Get2().equalsIgnoreCase("DIAMOND_SWORD")){
                        inv.setItem(1, sword);
                    }else if(data.Get2().equalsIgnoreCase("DIAMOND_AXE")){
                        inv.setItem(1, axe);
                    }else if(data.Get2().equalsIgnoreCase("BOW")){
                        inv.setItem(1, bow);
                    }else if(data.Get2().equalsIgnoreCase("COOKED_BEEF")){
                        inv.setItem(1, food);
                    }else if(data.Get2().equalsIgnoreCase("GOLDEN_APPLE")){
                        inv.setItem(1, apple);
                    }else if(data.Get2().equalsIgnoreCase("CROSSBOW")){
                        inv.setItem(1, cbow);
                    }else if(data.Get2().equalsIgnoreCase("SHIELD")){
                        inv.setItem(1, shield);
                    }else if(data.Get2().equalsIgnoreCase("AIR")){
                        inv.setItem(1, new ItemStack(Material.AIR, 1));
                    }

                    if(data.Get3().equalsIgnoreCase("DIAMOND_SWORD")){
                        inv.setItem(2, sword);
                    }else if(data.Get3().equalsIgnoreCase("DIAMOND_AXE")){
                        inv.setItem(2, axe);
                    }else if(data.Get3().equalsIgnoreCase("BOW")){
                        inv.setItem(2, bow);
                    }else if(data.Get3().equalsIgnoreCase("COOKED_BEEF")){
                        inv.setItem(2, food);
                    }else if(data.Get3().equalsIgnoreCase("GOLDEN_APPLE")){
                        inv.setItem(2, apple);
                    }else if(data.Get3().equalsIgnoreCase("CROSSBOW")){
                        inv.setItem(2, cbow);
                    }else if(data.Get3().equalsIgnoreCase("SHIELD")){
                        inv.setItem(2, shield);
                    }else if(data.Get3().equalsIgnoreCase("AIR")){
                        inv.setItem(2, new ItemStack(Material.AIR, 1));
                    }

                    if(data.Get4().equalsIgnoreCase("DIAMOND_SWORD")){
                        inv.setItem(3, sword);
                    }else if(data.Get4().equalsIgnoreCase("DIAMOND_AXE")){
                        inv.setItem(3, axe);
                    }else if(data.Get4().equalsIgnoreCase("BOW")){
                        inv.setItem(3, bow);
                    }else if(data.Get4().equalsIgnoreCase("COOKED_BEEF")){
                        inv.setItem(3, food);
                    }else if(data.Get4().equalsIgnoreCase("GOLDEN_APPLE")){
                        inv.setItem(3, apple);
                    }else if(data.Get4().equalsIgnoreCase("CROSSBOW")){
                        inv.setItem(3, cbow);
                    }else if(data.Get4().equalsIgnoreCase("SHIELD")){
                        inv.setItem(3, shield);
                    }else if(data.Get4().equalsIgnoreCase("AIR")){
                        inv.setItem(3, new ItemStack(Material.AIR, 1));
                    }

                    if(data.Get5().equalsIgnoreCase("DIAMOND_SWORD")){
                        inv.setItem(4, sword);
                    }else if(data.Get5().equalsIgnoreCase("DIAMOND_AXE")){
                        inv.setItem(4, axe);
                    }else if(data.Get5().equalsIgnoreCase("BOW")){
                        inv.setItem(4, bow);
                    }else if(data.Get5().equalsIgnoreCase("COOKED_BEEF")){
                        inv.setItem(4, food);
                    }else if(data.Get5().equalsIgnoreCase("GOLDEN_APPLE")){
                        inv.setItem(4, apple);
                    }else if(data.Get5().equalsIgnoreCase("CROSSBOW")){
                        inv.setItem(4, cbow);
                    }else if(data.Get5().equalsIgnoreCase("SHIELD")){
                        inv.setItem(4, shield);
                    }else if(data.Get5().equalsIgnoreCase("AIR")){
                        inv.setItem(4, new ItemStack(Material.AIR, 1));
                    }

                    if(data.Get6().equalsIgnoreCase("DIAMOND_SWORD")){
                        inv.setItem(5, sword);
                    }else if(data.Get6().equalsIgnoreCase("DIAMOND_AXE")){
                        inv.setItem(5, axe);
                    }else if(data.Get6().equalsIgnoreCase("BOW")){
                        inv.setItem(5, bow);
                    }else if(data.Get6().equalsIgnoreCase("COOKED_BEEF")){
                        inv.setItem(5, food);
                    }else if(data.Get6().equalsIgnoreCase("GOLDEN_APPLE")){
                        inv.setItem(5, apple);
                    }else if(data.Get6().equalsIgnoreCase("CROSSBOW")){
                        inv.setItem(5, cbow);
                    }else if(data.Get6().equalsIgnoreCase("SHIELD")){
                        inv.setItem(5, shield);
                    }else if(data.Get6().equalsIgnoreCase("AIR")){
                        inv.setItem(5, new ItemStack(Material.AIR, 1));
                    }

                    if(data.Get7().equalsIgnoreCase("DIAMOND_SWORD")){
                        inv.setItem(6, sword);
                    }else if(data.Get7().equalsIgnoreCase("DIAMOND_AXE")){
                        inv.setItem(6, axe);
                    }else if(data.Get7().equalsIgnoreCase("BOW")){
                        inv.setItem(6, bow);
                    }else if(data.Get7().equalsIgnoreCase("COOKED_BEEF")){
                        inv.setItem(6, food);
                    }else if(data.Get7().equalsIgnoreCase("GOLDEN_APPLE")){
                        inv.setItem(6, apple);
                    }else if(data.Get7().equalsIgnoreCase("CROSSBOW")){
                        inv.setItem(6, cbow);
                    }else if(data.Get7().equalsIgnoreCase("SHIELD")){
                        inv.setItem(6, shield);
                    }else if(data.Get7().equalsIgnoreCase("AIR")){
                        inv.setItem(6, new ItemStack(Material.AIR, 1));
                    }

                    if(data.Get8().equalsIgnoreCase("DIAMOND_SWORD")){
                        inv.setItem(7, sword);
                    }else if(data.Get8().equalsIgnoreCase("DIAMOND_AXE")){
                        inv.setItem(7, axe);
                    }else if(data.Get8().equalsIgnoreCase("BOW")){
                        inv.setItem(7, bow);
                    }else if(data.Get8().equalsIgnoreCase("COOKED_BEEF")){
                        inv.setItem(7, food);
                    }else if(data.Get8().equalsIgnoreCase("GOLDEN_APPLE")){
                        inv.setItem(7, apple);
                    }else if(data.Get8().equalsIgnoreCase("CROSSBOW")){
                        inv.setItem(7, cbow);
                    }else if(data.Get8().equalsIgnoreCase("SHIELD")){
                        inv.setItem(7, shield);
                    }else if(data.Get8().equalsIgnoreCase("AIR")){
                        inv.setItem(7, new ItemStack(Material.AIR, 1));
                    }

                    if(data.Get9().equalsIgnoreCase("DIAMOND_SWORD")){
                        inv.setItem(8, sword);
                    }else if(data.Get9().equalsIgnoreCase("DIAMOND_AXE")){
                        inv.setItem(8, axe);
                    }else if(data.Get9().equalsIgnoreCase("BOW")){
                        inv.setItem(8, bow);
                    }else if(data.Get9().equalsIgnoreCase("COOKED_BEEF")){
                        inv.setItem(8, food);
                    }else if(data.Get9().equalsIgnoreCase("GOLDEN_APPLE")){
                        inv.setItem(8, apple);
                    }else if(data.Get9().equalsIgnoreCase("CROSSBOW")){
                        inv.setItem(8, cbow);
                    }else if(data.Get9().equalsIgnoreCase("SHIELD")){
                        inv.setItem(8, shield);
                    }else if(data.Get9().equalsIgnoreCase("AIR")){
                        inv.setItem(8, new ItemStack(Material.AIR, 1));
                    }

                    if(data.GetOffhand().equalsIgnoreCase("DIAMOND_SWORD")){
                        p.getInventory().setItemInOffHand(sword);
                    }else if(data.GetOffhand().equalsIgnoreCase("DIAMOND_AXE")){
                        p.getInventory().setItemInOffHand(axe);
                    }else if(data.GetOffhand().equalsIgnoreCase("BOW")){
                        p.getInventory().setItemInOffHand(bow);
                    }else if(data.GetOffhand().equalsIgnoreCase("COOKED_BEEF")){
                        p.getInventory().setItemInOffHand(food);
                    }else if(data.GetOffhand().equalsIgnoreCase("GOLDEN_APPLE")){
                        p.getInventory().setItemInOffHand(apple);
                    }else if(data.GetOffhand().equalsIgnoreCase("CROSSBOW")){
                        p.getInventory().setItemInOffHand(cbow);
                    }else if(data.GetOffhand().equalsIgnoreCase("SHIELD")){
                        p.getInventory().setItemInOffHand(shield);
                    }else if(data.GetOffhand().equalsIgnoreCase("AIR")){
                        p.getInventory().setItemInOffHand(new ItemStack(Material.AIR, 1));
                    }
                    inv.setItem(9,arrow);
                    p.getInventory().setArmorContents(armor);
                    return;
                }
                /////////////////////////////////////////////////////////////////////////////
                ItemStack[] itd = new ItemStack[4];
                itd[0] = new ItemStack(Material.DIAMOND_SWORD, 1);
                itd[1] = new ItemStack(Material.DIAMOND_AXE, 1);
                itd[2] = new ItemStack(Material.BOW, 1);
                itd[3] = new ItemStack(Material.CROSSBOW, 1);
                List<ItemStack> pjt = new ArrayList<>();
                pjt.add(new ItemStack(Material.ARROW, 1));
                ItemMeta imd1 = itd[0].getItemMeta();
                ItemMeta imd2 = itd[1].getItemMeta();
                ItemMeta imd3 = itd[2].getItemMeta();
                CrossbowMeta imd4 = (CrossbowMeta) itd[3].getItemMeta();
                imd1.setUnbreakable(true);
                imd2.setUnbreakable(true);
                imd3.setUnbreakable(true);
                imd4.setUnbreakable(true);
                imd4.setChargedProjectiles(pjt);
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
            }if (Core.kits.get(p).equals("Trident")) {
                /////////////////////////////////////////////////////////////////////////////
                SlotSQL data = GetPlayerSlot(p.getUniqueId().toString(), "trident_kit");
                if(data != null){
                    ItemStack sword = new ItemStack(Material.DIAMOND_SWORD, 1);
                    ItemMeta swordm = sword.getItemMeta();
                    swordm.setUnbreakable(true);
                    sword.setItemMeta(swordm);
                    sword.addEnchantment(Enchantment.DAMAGE_ALL, 3);
                    sword.addEnchantment(Enchantment.KNOCKBACK, 1);
                    ItemStack axe = new ItemStack(Material.DIAMOND_AXE, 1);
                    ItemMeta axem = axe.getItemMeta();
                    axem.setUnbreakable(true);
                    axe.setItemMeta(axem);
                    axe.addEnchantment(Enchantment.DAMAGE_ALL, 1);
                    ItemStack trident = new ItemStack(Material.TRIDENT, 1);
                    ItemMeta tridentm = trident.getItemMeta();
                    tridentm.setUnbreakable(true);
                    trident.setItemMeta(tridentm);
                    trident.addEnchantment(Enchantment.IMPALING, 3);
                    trident.addEnchantment(Enchantment.LOYALTY, 3);
                    trident.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 5);
                    ItemStack food = new ItemStack(Material.COOKED_BEEF, 48);
                    ItemStack apple = new ItemStack(Material.GOLDEN_APPLE, 4);

                    if(data.Get1().equalsIgnoreCase("DIAMOND_SWORD")){
                        inv.setItem(0, sword);
                    }else if(data.Get1().equalsIgnoreCase("DIAMOND_AXE")){
                        inv.setItem(0, axe);
                    }else if(data.Get1().equalsIgnoreCase("TRIDENT")){
                        inv.setItem(0, trident);
                    }else if(data.Get1().equalsIgnoreCase("COOKED_BEEF")){
                        inv.setItem(0, food);
                    }else if(data.Get1().equalsIgnoreCase("GOLDEN_APPLE")){
                        inv.setItem(0, apple);
                    }else if(data.Get1().equalsIgnoreCase("AIR")){
                        inv.setItem(0, new ItemStack(Material.AIR, 1));
                    }

                    if(data.Get2().equalsIgnoreCase("DIAMOND_SWORD")){
                        inv.setItem(1, sword);
                    }else if(data.Get2().equalsIgnoreCase("DIAMOND_AXE")){
                        inv.setItem(1, axe);
                    }else if(data.Get2().equalsIgnoreCase("TRIDENT")){
                        inv.setItem(1, trident);
                    }else if(data.Get2().equalsIgnoreCase("COOKED_BEEF")){
                        inv.setItem(1, food);
                    }else if(data.Get2().equalsIgnoreCase("GOLDEN_APPLE")){
                        inv.setItem(1, apple);
                    }else if(data.Get2().equalsIgnoreCase("AIR")){
                        inv.setItem(1, new ItemStack(Material.AIR, 1));
                    }

                    if(data.Get3().equalsIgnoreCase("DIAMOND_SWORD")){
                        inv.setItem(2, sword);
                    }else if(data.Get3().equalsIgnoreCase("DIAMOND_AXE")){
                        inv.setItem(2, axe);
                    }else if(data.Get3().equalsIgnoreCase("TRIDENT")){
                        inv.setItem(2, trident);
                    }else if(data.Get3().equalsIgnoreCase("COOKED_BEEF")){
                        inv.setItem(2, food);
                    }else if(data.Get3().equalsIgnoreCase("GOLDEN_APPLE")){
                        inv.setItem(2, apple);
                    }else if(data.Get3().equalsIgnoreCase("AIR")){
                        inv.setItem(2, new ItemStack(Material.AIR, 1));
                    }

                    if(data.Get4().equalsIgnoreCase("DIAMOND_SWORD")){
                        inv.setItem(3, sword);
                    }else if(data.Get4().equalsIgnoreCase("DIAMOND_AXE")){
                        inv.setItem(3, axe);
                    }else if(data.Get4().equalsIgnoreCase("TRIDENT")){
                        inv.setItem(3, trident);
                    }else if(data.Get4().equalsIgnoreCase("COOKED_BEEF")){
                        inv.setItem(3, food);
                    }else if(data.Get4().equalsIgnoreCase("GOLDEN_APPLE")){
                        inv.setItem(3, apple);
                    }else if(data.Get4().equalsIgnoreCase("AIR")){
                        inv.setItem(3, new ItemStack(Material.AIR, 1));
                    }

                    if(data.Get5().equalsIgnoreCase("DIAMOND_SWORD")){
                        inv.setItem(4, sword);
                    }else if(data.Get5().equalsIgnoreCase("DIAMOND_AXE")){
                        inv.setItem(4, axe);
                    }else if(data.Get5().equalsIgnoreCase("TRIDENT")){
                        inv.setItem(4, trident);
                    }else if(data.Get5().equalsIgnoreCase("COOKED_BEEF")){
                        inv.setItem(4, food);
                    }else if(data.Get5().equalsIgnoreCase("GOLDEN_APPLE")){
                        inv.setItem(4, apple);
                    }else if(data.Get5().equalsIgnoreCase("AIR")){
                        inv.setItem(4, new ItemStack(Material.AIR, 1));
                    }

                    if(data.Get6().equalsIgnoreCase("DIAMOND_SWORD")){
                        inv.setItem(5, sword);
                    }else if(data.Get6().equalsIgnoreCase("DIAMOND_AXE")){
                        inv.setItem(5, axe);
                    }else if(data.Get6().equalsIgnoreCase("TRIDENT")){
                        inv.setItem(5, trident);
                    }else if(data.Get6().equalsIgnoreCase("COOKED_BEEF")){
                        inv.setItem(5, food);
                    }else if(data.Get6().equalsIgnoreCase("GOLDEN_APPLE")){
                        inv.setItem(5, apple);
                    }else if(data.Get6().equalsIgnoreCase("AIR")){
                        inv.setItem(5, new ItemStack(Material.AIR, 1));
                    }

                    if(data.Get7().equalsIgnoreCase("DIAMOND_SWORD")){
                        inv.setItem(6, sword);
                    }else if(data.Get7().equalsIgnoreCase("DIAMOND_AXE")){
                        inv.setItem(6, axe);
                    }else if(data.Get7().equalsIgnoreCase("TRIDENT")){
                        inv.setItem(6, trident);
                    }else if(data.Get7().equalsIgnoreCase("COOKED_BEEF")){
                        inv.setItem(6, food);
                    }else if(data.Get7().equalsIgnoreCase("GOLDEN_APPLE")){
                        inv.setItem(6, apple);
                    }else if(data.Get7().equalsIgnoreCase("AIR")){
                        inv.setItem(6, new ItemStack(Material.AIR, 1));
                    }

                    if(data.Get8().equalsIgnoreCase("DIAMOND_SWORD")){
                        inv.setItem(7, sword);
                    }else if(data.Get8().equalsIgnoreCase("DIAMOND_AXE")){
                        inv.setItem(7, axe);
                    }else if(data.Get8().equalsIgnoreCase("TRIDENT")){
                        inv.setItem(7, trident);
                    }else if(data.Get8().equalsIgnoreCase("COOKED_BEEF")){
                        inv.setItem(7, food);
                    }else if(data.Get8().equalsIgnoreCase("GOLDEN_APPLE")){
                        inv.setItem(7, apple);
                    }else if(data.Get8().equalsIgnoreCase("AIR")){
                        inv.setItem(7, new ItemStack(Material.AIR, 1));
                    }

                    if(data.Get9().equalsIgnoreCase("DIAMOND_SWORD")){
                        inv.setItem(8, sword);
                    }else if(data.Get9().equalsIgnoreCase("DIAMOND_AXE")){
                        inv.setItem(8, axe);
                    }else if(data.Get9().equalsIgnoreCase("TRIDENT")){
                        inv.setItem(8, trident);
                    }else if(data.Get9().equalsIgnoreCase("COOKED_BEEF")){
                        inv.setItem(8, food);
                    }else if(data.Get9().equalsIgnoreCase("GOLDEN_APPLE")){
                        inv.setItem(8, apple);
                    }else if(data.Get9().equalsIgnoreCase("AIR")){
                        inv.setItem(8, new ItemStack(Material.AIR, 1));
                    }

                    if(data.GetOffhand().equalsIgnoreCase("DIAMOND_SWORD")){
                        p.getInventory().setItemInOffHand(sword);
                    }else if(data.GetOffhand().equalsIgnoreCase("DIAMOND_AXE")){
                        p.getInventory().setItemInOffHand(axe);
                    }else if(data.GetOffhand().equalsIgnoreCase("TRIDENT")){
                        p.getInventory().setItemInOffHand(trident);
                    }else if(data.GetOffhand().equalsIgnoreCase("COOKED_BEEF")){
                        p.getInventory().setItemInOffHand(food);
                    }else if(data.GetOffhand().equalsIgnoreCase("GOLDEN_APPLE")){
                        p.getInventory().setItemInOffHand(apple);
                    }else if(data.GetOffhand().equalsIgnoreCase("AIR")){
                        p.getInventory().setItemInOffHand(new ItemStack(Material.AIR, 1));
                    }
                    p.getInventory().setArmorContents(armor);
                    return;
                }
                /////////////////////////////////////////////////////////////////////////////
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
            }if (Core.kits.get(p).equals("Viking")) {
                /////////////////////////////////////////////////////////////////////////////
                SlotSQL data = GetPlayerSlot(p.getUniqueId().toString(), "viking_kit");
                if(data != null){
                    ItemStack axe = new ItemStack(Material.DIAMOND_AXE, 1);
                    ItemMeta axem = axe.getItemMeta();
                    axem.setUnbreakable(true);
                    axe.setItemMeta(axem);
                    axe.addEnchantment(Enchantment.DAMAGE_ALL, 4);
                    ItemStack cbow = new ItemStack(Material.CROSSBOW, 1);
                    CrossbowMeta cbowm = (CrossbowMeta) cbow.getItemMeta();
                    List<ItemStack> pjt = new ArrayList<>();
                    pjt.add(new ItemStack(Material.ARROW, 1));
                    cbowm.setChargedProjectiles(pjt);
                    cbowm.setUnbreakable(true);
                    cbow.setItemMeta(cbowm);
                    cbow.addEnchantment(Enchantment.PIERCING, 3);
                    cbow.addEnchantment(Enchantment.QUICK_CHARGE, 1);
                    ItemStack food = new ItemStack(Material.COOKED_BEEF, 48);
                    ItemStack apple = new ItemStack(Material.GOLDEN_APPLE, 4);

                    if(data.Get1().equalsIgnoreCase("DIAMOND_AXE")){
                        inv.setItem(0, axe);
                    }else if(data.Get1().equalsIgnoreCase("CROSSBOW")){
                        inv.setItem(0, cbow);
                    }else if(data.Get1().equalsIgnoreCase("COOKED_BEEF")){
                        inv.setItem(0, food);
                    }else if(data.Get1().equalsIgnoreCase("GOLDEN_APPLE")){
                        inv.setItem(0, apple);
                    }else if(data.Get1().equalsIgnoreCase("AIR")){
                        inv.setItem(0, new ItemStack(Material.AIR, 1));
                    }

                    if(data.Get2().equalsIgnoreCase("DIAMOND_AXE")){
                        inv.setItem(1, axe);
                    }else if(data.Get2().equalsIgnoreCase("CROSSBOW")){
                        inv.setItem(1, cbow);
                    }else if(data.Get2().equalsIgnoreCase("COOKED_BEEF")){
                        inv.setItem(1, food);
                    }else if(data.Get2().equalsIgnoreCase("GOLDEN_APPLE")){
                        inv.setItem(1, apple);
                    }else if(data.Get2().equalsIgnoreCase("AIR")){
                        inv.setItem(1, new ItemStack(Material.AIR, 1));
                    }

                    if(data.Get3().equalsIgnoreCase("DIAMOND_AXE")){
                        inv.setItem(2, axe);
                    }else if(data.Get3().equalsIgnoreCase("CROSSBOW")){
                        inv.setItem(2, cbow);
                    }else if(data.Get3().equalsIgnoreCase("COOKED_BEEF")){
                        inv.setItem(2, food);
                    }else if(data.Get3().equalsIgnoreCase("GOLDEN_APPLE")){
                        inv.setItem(2, apple);
                    }else if(data.Get3().equalsIgnoreCase("AIR")){
                        inv.setItem(2, new ItemStack(Material.AIR, 1));
                    }

                    if(data.Get4().equalsIgnoreCase("DIAMOND_AXE")){
                        inv.setItem(3, axe);
                    }else if(data.Get4().equalsIgnoreCase("CROSSBOW")){
                        inv.setItem(3, cbow);
                    }else if(data.Get4().equalsIgnoreCase("COOKED_BEEF")){
                        inv.setItem(3, food);
                    }else if(data.Get4().equalsIgnoreCase("GOLDEN_APPLE")){
                        inv.setItem(3, apple);
                    }else if(data.Get4().equalsIgnoreCase("AIR")){
                        inv.setItem(3, new ItemStack(Material.AIR, 1));
                    }

                    if(data.Get5().equalsIgnoreCase("DIAMOND_AXE")){
                        inv.setItem(4, axe);
                    }else if(data.Get5().equalsIgnoreCase("CROSSBOW")){
                        inv.setItem(4, cbow);
                    }else if(data.Get5().equalsIgnoreCase("COOKED_BEEF")){
                        inv.setItem(4, food);
                    }else if(data.Get5().equalsIgnoreCase("GOLDEN_APPLE")){
                        inv.setItem(4, apple);
                    }else if(data.Get5().equalsIgnoreCase("AIR")){
                        inv.setItem(4, new ItemStack(Material.AIR, 1));
                    }

                    if(data.Get6().equalsIgnoreCase("DIAMOND_AXE")){
                        inv.setItem(5, axe);
                    }else if(data.Get6().equalsIgnoreCase("CROSSBOW")){
                        inv.setItem(5, cbow);
                    }else if(data.Get6().equalsIgnoreCase("COOKED_BEEF")){
                        inv.setItem(5, food);
                    }else if(data.Get6().equalsIgnoreCase("GOLDEN_APPLE")){
                        inv.setItem(5, apple);
                    }else if(data.Get6().equalsIgnoreCase("AIR")){
                        inv.setItem(5, new ItemStack(Material.AIR, 1));
                    }

                    if(data.Get7().equalsIgnoreCase("DIAMOND_AXE")){
                        inv.setItem(6, axe);
                    }else if(data.Get7().equalsIgnoreCase("CROSSBOW")){
                        inv.setItem(6, cbow);
                    }else if(data.Get7().equalsIgnoreCase("COOKED_BEEF")){
                        inv.setItem(6, food);
                    }else if(data.Get7().equalsIgnoreCase("GOLDEN_APPLE")){
                        inv.setItem(6, apple);
                    }else if(data.Get7().equalsIgnoreCase("AIR")){
                        inv.setItem(6, new ItemStack(Material.AIR, 1));
                    }

                    if(data.Get8().equalsIgnoreCase("DIAMOND_AXE")){
                        inv.setItem(7, axe);
                    }else if(data.Get8().equalsIgnoreCase("CROSSBOW")){
                        inv.setItem(7, cbow);
                    }else if(data.Get8().equalsIgnoreCase("COOKED_BEEF")){
                        inv.setItem(7, food);
                    }else if(data.Get8().equalsIgnoreCase("GOLDEN_APPLE")){
                        inv.setItem(7, apple);
                    }else if(data.Get8().equalsIgnoreCase("AIR")){
                        inv.setItem(7, new ItemStack(Material.AIR, 1));
                    }

                    if(data.Get9().equalsIgnoreCase("DIAMOND_AXE")){
                        inv.setItem(8, axe);
                    }else if(data.Get9().equalsIgnoreCase("CROSSBOW")){
                        inv.setItem(8, cbow);
                    }else if(data.Get9().equalsIgnoreCase("COOKED_BEEF")){
                        inv.setItem(8, food);
                    }else if(data.Get9().equalsIgnoreCase("GOLDEN_APPLE")){
                        inv.setItem(8, apple);
                    }else if(data.Get9().equalsIgnoreCase("AIR")){
                        inv.setItem(8, new ItemStack(Material.AIR, 1));
                    }

                    if(data.GetOffhand().equalsIgnoreCase("DIAMOND_AXE")){
                        p.getInventory().setItemInOffHand(axe);
                    }else if(data.GetOffhand().equalsIgnoreCase("CROSSBOW")){
                        p.getInventory().setItemInOffHand(cbow);
                    }else if(data.GetOffhand().equalsIgnoreCase("COOKED_BEEF")){
                        p.getInventory().setItemInOffHand(food);
                    }else if(data.GetOffhand().equalsIgnoreCase("GOLDEN_APPLE")){
                        p.getInventory().setItemInOffHand(apple);
                    }else if(data.GetOffhand().equalsIgnoreCase("AIR")){
                        p.getInventory().setItemInOffHand(new ItemStack(Material.AIR, 1));
                    }
                    inv.setItem(9,arrow);
                    p.getInventory().setArmorContents(armor);
                    return;
                }
                /////////////////////////////////////////////////////////////////////////////
                ItemStack[] itb = new ItemStack[1];
                itb[0] = new ItemStack(Material.DIAMOND_AXE, 1);
                ItemStack CB1 = new ItemStack(Material.CROSSBOW, 1);
                List<ItemStack> pjt = new ArrayList<>();
                pjt.add(new ItemStack(Material.ARROW, 1));
                ItemMeta imb1 = itb[0].getItemMeta();
                CrossbowMeta CB11 = (CrossbowMeta) CB1.getItemMeta();
                imb1.setUnbreakable(true);
                CB11.setUnbreakable(true);
                CB11.setChargedProjectiles(pjt);
                itb[0].setItemMeta(imb1);
                CB1.setItemMeta(CB11);
                itb[0].addEnchantment(Enchantment.DAMAGE_ALL, 4);
                CB1.addEnchantment(Enchantment.PIERCING, 3);
                CB1.addEnchantment(Enchantment.QUICK_CHARGE, 1);

                inv.clear();
                p.getInventory().setArmorContents(armor);
                p.getInventory().setStorageContents(itb);
                p.getInventory().setItemInOffHand(CB1);
                inv.addItem(new ItemStack(Material.COOKED_BEEF, 64));
                inv.addItem(new ItemStack(Material.GOLDEN_APPLE, 4));
                inv.setItem(9, arrow);
            }if (Core.kits.get(p).equals("Admin")) {
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
