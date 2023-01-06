package core.itdragclick.Utils;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;


public class Utils {
    public static String PluginName = (ChatColor.YELLOW + "C" + ChatColor.LIGHT_PURPLE + "C" + ChatColor.DARK_GRAY + " » "+ChatColor.GRAY);
    public static String noperm = PluginName+ ChatColor.RED+"You don't have permission to do that!";
    public static String error = PluginName+ChatColor.RED+"Error: Something went wrong, please try again later.";

    public static void getserverselect(Player p){

        PlayerInventory inv = p.getInventory();

        ItemStack compass = new ItemStack(Material.STICK, 1);

        ItemMeta im = compass.getItemMeta();

        ArrayList<String> lore = new ArrayList<>();
        lore.add("§7");
        lore.add("§7Right Click to open the Server Selector!");
        im.setDisplayName("§a§lServer Selector");
        im.setCustomModelData(1);
        im.setLore(lore);

        compass.setItemMeta(im);
        if (p.getGameMode() != GameMode.CREATIVE && p.getGameMode() != GameMode.SPECTATOR){
            inv.setItem(3, compass);
        }
    }
    public static void getprofileitem(Player p){

        PlayerInventory inv = p.getInventory();

        ItemStack head = new ItemStack(Material.PLAYER_HEAD, 1);

        SkullMeta im = (SkullMeta) head.getItemMeta();

        ArrayList<String> lore = new ArrayList<>();
        lore.add("§7");
        lore.add("§7Right Click to open the Profile!");
        im.setDisplayName("§9§lProfile §c(ComingSoon..)");
        im.setCustomModelData(1);
        im.setOwner(p.getName());
        im.setLore(lore);

        head.setItemMeta(im);
        if (p.getGameMode() != GameMode.CREATIVE && p.getGameMode() != GameMode.SPECTATOR){
            inv.setItem(5, head);
        }
    }
    public static void getcosmeticitem(Player p){

        PlayerInventory inv = p.getInventory();

        ItemStack head = new ItemStack(Material.BARRIER, 1);

        ItemMeta im = head.getItemMeta();

        ArrayList<String> lore = new ArrayList<>();
        lore.add("§7");
        lore.add("§7Right Click to open the Cosmetic!");
        im.setDisplayName("§d§lCosmetic §c(ComingSoon..)");
        im.setCustomModelData(1);
        im.setLore(lore);

        head.setItemMeta(im);
        if (p.getGameMode() != GameMode.CREATIVE && p.getGameMode() != GameMode.SPECTATOR){
            inv.setItem(0, head);
        }
    }
    public static void getsettingsitem(Player p){

        PlayerInventory inv = p.getInventory();

        ItemStack head = new ItemStack(Material.STICK, 1);

        ItemMeta im = head.getItemMeta();

        ArrayList<String> lore = new ArrayList<>();
        lore.add("§7");
        lore.add("§7Right Click to open the Settings!");
        im.setDisplayName("§7§lSettings");
        im.setCustomModelData(2);
        im.setLore(lore);

        head.setItemMeta(im);
        if (p.getGameMode() != GameMode.CREATIVE && p.getGameMode() != GameMode.SPECTATOR){
            inv.setItem(8, head);
        }
    }
}
