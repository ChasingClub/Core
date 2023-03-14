package cc.core.command;

import cc.core.Core;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

import static cc.core.Core.fixer;

public class secretTake implements CommandExecutor {
    @Override
    public boolean onCommand( CommandSender commandSender,  Command command,  String s,  String[] args) {

        if (commandSender instanceof Player) {
            Player p = (Player) commandSender;
            if (args.length == 0) {
                p.sendMessage("You must specify an Item!");
            } else if (p.isOp()) {
                if (args[0].equalsIgnoreCase("bouncingbow")) {
                    bouncingBow(p);
                } else if (args[0].equalsIgnoreCase("maxedbow")) {
                    MaxedBow(p);
                } else if (args[0].equalsIgnoreCase("phantomarrow")) {
                    PhantomArrow(p);
                } else if (args[0].equalsIgnoreCase("dash")) {
                    Dasher(p);
                } else {
                    p.sendMessage("That item does not exist!");
                }
            } else {
                p.sendMessage(ChatColor.RED + "You don't have permissions to use this command!");
            }
        } else {
            commandSender.sendMessage("You must be a player to use this command!");
        }

        return true;
    }

    private void bouncingBow(Player p) {
        ItemStack bouncingBow = new ItemStack(Material.BOW, 1);

        bouncingBow.addEnchantment(Core.bouncing, 1);

        ItemMeta bouncingBowMeta = bouncingBow.getItemMeta();

        bouncingBowMeta.setLore(Arrays.asList(fixer + Core.bouncing.getName()));

        bouncingBow.setItemMeta(bouncingBowMeta);

        p.getInventory().addItem(bouncingBow);

    }

    private void MaxedBow(Player p) {

        ItemStack MaxedBow = new ItemStack(Material.BOW, 1);

        MaxedBow.addUnsafeEnchantment(Core.phantomArrow, 1);
        MaxedBow.addUnsafeEnchantment(Core.bouncing, 1);
        MaxedBow.addUnsafeEnchantment(Enchantment.ARROW_FIRE, 1);
        MaxedBow.addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, 1);
        MaxedBow.addUnsafeEnchantment(Enchantment.ARROW_INFINITE, 1);
        MaxedBow.addUnsafeEnchantment(Enchantment.ARROW_KNOCKBACK, 1);
        MaxedBow.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
        MaxedBow.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 1);
        MaxedBow.addUnsafeEnchantment(Enchantment.DAMAGE_ARTHROPODS, 1);
        MaxedBow.addUnsafeEnchantment(Enchantment.DAMAGE_UNDEAD, 1);
        MaxedBow.addUnsafeEnchantment(Enchantment.FIRE_ASPECT, 1);
        MaxedBow.addUnsafeEnchantment(Enchantment.KNOCKBACK, 1);
        MaxedBow.addUnsafeEnchantment(Enchantment.LOOT_BONUS_BLOCKS, 1);
        MaxedBow.addUnsafeEnchantment(Enchantment.LOOT_BONUS_MOBS, 1);
        MaxedBow.addUnsafeEnchantment(Enchantment.LUCK, 1);
        MaxedBow.addUnsafeEnchantment(Enchantment.LURE, 1);
        MaxedBow.addUnsafeEnchantment(Enchantment.MENDING, 1);
        MaxedBow.addUnsafeEnchantment(Enchantment.OXYGEN, 1);
        MaxedBow.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        MaxedBow.addUnsafeEnchantment(Enchantment.PROTECTION_EXPLOSIONS, 1);
        MaxedBow.addUnsafeEnchantment(Enchantment.PROTECTION_FALL, 1);
        MaxedBow.addUnsafeEnchantment(Enchantment.PROTECTION_FIRE, 1);
        MaxedBow.addUnsafeEnchantment(Enchantment.PROTECTION_PROJECTILE, 1);
        MaxedBow.addUnsafeEnchantment(Enchantment.SILK_TOUCH, 1);
        MaxedBow.addUnsafeEnchantment(Enchantment.THORNS, 1);
        MaxedBow.addUnsafeEnchantment(Enchantment.WATER_WORKER, 1);

        ItemMeta MaxedBowMeta = MaxedBow.getItemMeta();

        MaxedBowMeta.setDisplayName("§r§l§6Maxed Bow");
        MaxedBowMeta.setLore(Arrays.asList(fixer + Core.phantomArrow.getName(), fixer + Core.bouncing.getName()));

        MaxedBow.setItemMeta(MaxedBowMeta);

        p.getInventory().addItem(MaxedBow);

    }

    private void Dasher(Player p) {

        ItemStack Dasher = new ItemStack(Material.DIAMOND_BOOTS, 1);

        Dasher.addEnchantment(Core.dash, 1);

        ItemMeta DasherMeta = Dasher.getItemMeta();

        DasherMeta.setLore(Arrays.asList(fixer + Core.dash.getName()));

        Dasher.setItemMeta(DasherMeta);

        p.getInventory().addItem(Dasher);

    }

    private void PhantomArrow(Player p) {

        ItemStack PhantomArrow = new ItemStack(Material.ARROW, 1);

        PhantomArrow.addEnchantment(Core.phantomArrow, 1);

        ItemMeta PhantomArrowMeta = PhantomArrow.getItemMeta();

        PhantomArrowMeta.setLore(Arrays.asList(fixer + Core.phantomArrow.getName()));

        PhantomArrow.setItemMeta(PhantomArrowMeta);

        p.getInventory().addItem(PhantomArrow);

    }

}
