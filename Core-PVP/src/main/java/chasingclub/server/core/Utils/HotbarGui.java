package chasingclub.server.core.Utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HotbarGui {
    public static Inventory inv;
    public static int inv_boxes = 27;
    public static String NS = ChatColor.DARK_AQUA+"NetheriteStack";
    public static String CS = ChatColor.DARK_AQUA+"ClassicSword";
    public static String D = ChatColor.DARK_AQUA+"Default "+ChatColor.GRAY+"Kit";
    public static String T = ChatColor.DARK_AQUA+"Trident "+ChatColor.GRAY+"Kit";
    public static String V = ChatColor.DARK_AQUA+"Viking "+ChatColor.GRAY+"Kit";
    public static String A = ChatColor.DARK_AQUA+"Archer "+ChatColor.GRAY+"Kit";

    public static void UI_initialize(){
        inv = Bukkit.createInventory(null, inv_boxes);
    }
    public static ItemStack CreateItem(Inventory inv, Material material, int amount, int invSlot, int CustomModelData, String displayname, String... loreString){
        if (material != null){
            ItemStack item = new ItemStack(material, amount);

            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(displayname);
            List<String> lore = new ArrayList<>(Arrays.asList(loreString));
            if(CustomModelData != 0){
                meta.setCustomModelData(CustomModelData);
            }
            meta.setLore(lore);
            meta.addItemFlags(ItemFlag.values());
            item.setItemMeta(meta);

            inv.setItem(invSlot, item);
            return item;
        }
        return null;
    }
    public static String Color(String text){
        return text == null ? null : ChatColor.translateAlternateColorCodes('&', text);
    }
    public static Inventory GUINetherite(Player p){
        inv.clear();
        PlayerInventory playerInventory = p.getInventory();
        playerInventory.clear();
        ItemStack sv = new ItemStack(Material.STRUCTURE_VOID, 1);
        ItemMeta meta = sv.getItemMeta();
        meta.setCustomModelData(1);
        meta.setDisplayName(ChatColor.MAGIC+"");
        sv.setItemMeta(meta);
        for (int i = 0; i < 36; i++) {
            playerInventory.setItem(i, sv);
        }
        Inventory inventory = Bukkit.createInventory(null, inv_boxes, NS);
        CreateItem(inv, Material.DIAMOND_SWORD, 1, 0, 0, "");
        CreateItem(inv, Material.DIAMOND_AXE, 1, 1, 0, "");
        CreateItem(inv, Material.BOW, 1, 2, 0, "");
        CreateItem(inv, Material.CROSSBOW, 1, 3, 0, "");
        CreateItem(inv, Material.COOKED_BEEF, 1, 4, 0, "");
        CreateItem(inv, Material.SHIELD, 1, 26, 0, "");
        for (int i = 9; i < 18; i++) {
            CreateItem(inv, Material.WHITE_STAINED_GLASS_PANE, 1, i, 1, ChatColor.RESET+"");
        }
        CreateItem(inv, Material.LIME_CONCRETE, 1, 18, 1, Color("&a&lSubmit"), Color("&7Click to &aSubmit &7editing"));
        CreateItem(inv, Material.RED_CONCRETE, 1, 19, 1, Color("&c&lCancel"), Color("&7Click to &cCancel &7editing"));
        CreateItem(inv, Material.GRAY_CONCRETE, 1, 20, 1, Color("&8&lReset"), Color("&7Click to &8Reset &7to default"));
        CreateItem(inv, Material.WHITE_STAINED_GLASS_PANE, 1, 21, 1, ChatColor.RESET+"");
        CreateItem(inv, Material.WHITE_STAINED_GLASS_PANE, 1, 22, 1, ChatColor.RESET+"");
        CreateItem(inv, Material.WHITE_STAINED_GLASS_PANE, 1, 23, 1, ChatColor.RESET+"");
        CreateItem(inv, Material.WHITE_STAINED_GLASS_PANE, 1, 24, 1, ChatColor.RESET+"");
        CreateItem(inv, Material.WHITE_STAINED_GLASS_PANE, 1, 25, 1, ChatColor.RESET+"");
        inventory.setContents(inv.getContents());
        return inventory;
    }
    public static Inventory GUIClassicSword(Player p){
        inv.clear();
        PlayerInventory playerInventory = p.getInventory();
        playerInventory.clear();
        ItemStack sv = new ItemStack(Material.STRUCTURE_VOID, 1);
        ItemMeta meta = sv.getItemMeta();
        meta.setCustomModelData(1);
        meta.setDisplayName(ChatColor.MAGIC+"");
        sv.setItemMeta(meta);
        for (int i = 0; i < 36; i++) {
            playerInventory.setItem(i, sv);
        }
        Inventory inventory = Bukkit.createInventory(null, inv_boxes, CS);
        CreateItem(inv, Material.DIAMOND_SWORD, 1, 0, 0, "");
        CreateItem(inv, Material.COOKED_BEEF, 1, 26, 0, "");
        for (int i = 9; i < 18; i++) {
            CreateItem(inv, Material.WHITE_STAINED_GLASS_PANE, 1, i, 1, ChatColor.RESET+"");
        }
        CreateItem(inv, Material.LIME_CONCRETE, 1, 18, 1, Color("&a&lSubmit"), Color("&7Click to &aSubmit &7editing"));
        CreateItem(inv, Material.RED_CONCRETE, 1, 19, 1, Color("&c&lCancel"), Color("&7Click to &cCancel &7editing"));
        CreateItem(inv, Material.GRAY_CONCRETE, 1, 20, 1, Color("&8&lReset"), Color("&7Click to &8Reset &7to default"));
        CreateItem(inv, Material.WHITE_STAINED_GLASS_PANE, 1, 21, 1, ChatColor.RESET+"");
        CreateItem(inv, Material.WHITE_STAINED_GLASS_PANE, 1, 22, 1, ChatColor.RESET+"");
        CreateItem(inv, Material.WHITE_STAINED_GLASS_PANE, 1, 23, 1, ChatColor.RESET+"");
        CreateItem(inv, Material.WHITE_STAINED_GLASS_PANE, 1, 24, 1, ChatColor.RESET+"");
        CreateItem(inv, Material.WHITE_STAINED_GLASS_PANE, 1, 25, 1, ChatColor.RESET+"");
        inventory.setContents(inv.getContents());
        return inventory;
    }
    public static Inventory GUIDefault(Player p){
        inv.clear();
        PlayerInventory playerInventory = p.getInventory();
        playerInventory.clear();
        ItemStack sv = new ItemStack(Material.STRUCTURE_VOID, 1);
        ItemMeta meta = sv.getItemMeta();
        meta.setCustomModelData(1);
        meta.setDisplayName(ChatColor.MAGIC+"");
        sv.setItemMeta(meta);
        for (int i = 0; i < 36; i++) {
            playerInventory.setItem(i, sv);
        }
        Inventory inventory = Bukkit.createInventory(null, inv_boxes, D);
        CreateItem(inv, Material.DIAMOND_SWORD, 1, 0, 0, "");
        CreateItem(inv, Material.DIAMOND_AXE, 1, 1, 0, "");
        CreateItem(inv, Material.BOW, 1, 2, 0, "");
        CreateItem(inv, Material.CROSSBOW, 1, 3, 0, "");
        CreateItem(inv, Material.COOKED_BEEF, 1, 4, 0, "");
        CreateItem(inv, Material.GOLDEN_APPLE, 1, 5, 0, "");
        CreateItem(inv, Material.SHIELD, 1, 26, 0, "");
        for (int i = 9; i < 18; i++) {
            CreateItem(inv, Material.WHITE_STAINED_GLASS_PANE, 1, i, 1, ChatColor.RESET+"");
        }
        CreateItem(inv, Material.LIME_CONCRETE, 1, 18, 1, Color("&a&lSubmit"), Color("&7Click to &aSubmit &7editing"));
        CreateItem(inv, Material.RED_CONCRETE, 1, 19, 1, Color("&c&lCancel"), Color("&7Click to &cCancel &7editing"));
        CreateItem(inv, Material.GRAY_CONCRETE, 1, 20, 1, Color("&8&lReset"), Color("&7Click to &8Reset &7to default"));
        CreateItem(inv, Material.WHITE_STAINED_GLASS_PANE, 1, 21, 1, ChatColor.RESET+"");
        CreateItem(inv, Material.WHITE_STAINED_GLASS_PANE, 1, 22, 1, ChatColor.RESET+"");
        CreateItem(inv, Material.WHITE_STAINED_GLASS_PANE, 1, 23, 1, ChatColor.RESET+"");
        CreateItem(inv, Material.WHITE_STAINED_GLASS_PANE, 1, 24, 1, ChatColor.RESET+"");
        CreateItem(inv, Material.WHITE_STAINED_GLASS_PANE, 1, 25, 1, ChatColor.RESET+"");
        inventory.setContents(inv.getContents());
        return inventory;
    }
    public static Inventory GUITrident(Player p){
        inv.clear();
        PlayerInventory playerInventory = p.getInventory();
        playerInventory.clear();
        ItemStack sv = new ItemStack(Material.STRUCTURE_VOID, 1);
        ItemMeta meta = sv.getItemMeta();
        meta.setCustomModelData(1);
        meta.setDisplayName(ChatColor.MAGIC+"");
        sv.setItemMeta(meta);
        for (int i = 0; i < 36; i++) {
            playerInventory.setItem(i, sv);
        }
        Inventory inventory = Bukkit.createInventory(null, inv_boxes, T);
        CreateItem(inv, Material.DIAMOND_SWORD, 1, 0, 0, "");
        CreateItem(inv, Material.DIAMOND_AXE, 1, 1, 0, "");
        CreateItem(inv, Material.COOKED_BEEF, 1, 2, 0, "");
        CreateItem(inv, Material.GOLDEN_APPLE, 1, 3, 0, "");
        CreateItem(inv, Material.TRIDENT, 1, 26, 0, "");
        for (int i = 9; i < 18; i++) {
            CreateItem(inv, Material.WHITE_STAINED_GLASS_PANE, 1, i, 1, ChatColor.RESET+"");
        }
        CreateItem(inv, Material.LIME_CONCRETE, 1, 18, 1, Color("&a&lSubmit"), Color("&7Click to &aSubmit &7editing"));
        CreateItem(inv, Material.RED_CONCRETE, 1, 19, 1, Color("&c&lCancel"), Color("&7Click to &cCancel &7editing"));
        CreateItem(inv, Material.GRAY_CONCRETE, 1, 20, 1, Color("&8&lReset"), Color("&7Click to &8Reset &7to default"));
        CreateItem(inv, Material.WHITE_STAINED_GLASS_PANE, 1, 21, 1, ChatColor.RESET+"");
        CreateItem(inv, Material.WHITE_STAINED_GLASS_PANE, 1, 22, 1, ChatColor.RESET+"");
        CreateItem(inv, Material.WHITE_STAINED_GLASS_PANE, 1, 23, 1, ChatColor.RESET+"");
        CreateItem(inv, Material.WHITE_STAINED_GLASS_PANE, 1, 24, 1, ChatColor.RESET+"");
        CreateItem(inv, Material.WHITE_STAINED_GLASS_PANE, 1, 25, 1, ChatColor.RESET+"");
        inventory.setContents(inv.getContents());
        return inventory;
    }
    public static Inventory GUIViking(Player p){
        inv.clear();
        PlayerInventory playerInventory = p.getInventory();
        playerInventory.clear();
        ItemStack sv = new ItemStack(Material.STRUCTURE_VOID, 1);
        ItemMeta meta = sv.getItemMeta();
        meta.setCustomModelData(1);
        meta.setDisplayName(ChatColor.MAGIC+"");
        sv.setItemMeta(meta);
        for (int i = 0; i < 36; i++) {
            playerInventory.setItem(i, sv);
        }
        Inventory inventory = Bukkit.createInventory(null, inv_boxes, V);
        CreateItem(inv, Material.DIAMOND_AXE, 1, 0, 0, "");
        CreateItem(inv, Material.COOKED_BEEF, 1, 1, 0, "");
        CreateItem(inv, Material.GOLDEN_APPLE, 1, 2, 0, "");
        CreateItem(inv, Material.CROSSBOW, 1, 26, 0, "");
        for (int i = 9; i < 18; i++) {
            CreateItem(inv, Material.WHITE_STAINED_GLASS_PANE, 1, i, 1, ChatColor.RESET+"");
        }
        CreateItem(inv, Material.LIME_CONCRETE, 1, 18, 1, Color("&a&lSubmit"), Color("&7Click to &aSubmit &7editing"));
        CreateItem(inv, Material.RED_CONCRETE, 1, 19, 1, Color("&c&lCancel"), Color("&7Click to &cCancel &7editing"));
        CreateItem(inv, Material.GRAY_CONCRETE, 1, 20, 1, Color("&8&lReset"), Color("&7Click to &8Reset &7to default"));
        CreateItem(inv, Material.WHITE_STAINED_GLASS_PANE, 1, 21, 1, ChatColor.RESET+"");
        CreateItem(inv, Material.WHITE_STAINED_GLASS_PANE, 1, 22, 1, ChatColor.RESET+"");
        CreateItem(inv, Material.WHITE_STAINED_GLASS_PANE, 1, 23, 1, ChatColor.RESET+"");
        CreateItem(inv, Material.WHITE_STAINED_GLASS_PANE, 1, 24, 1, ChatColor.RESET+"");
        CreateItem(inv, Material.WHITE_STAINED_GLASS_PANE, 1, 25, 1, ChatColor.RESET+"");
        inventory.setContents(inv.getContents());
        return inventory;
    }
    public static Inventory GUIArcher(Player p){
        inv.clear();
        PlayerInventory playerInventory = p.getInventory();
        playerInventory.clear();
        ItemStack sv = new ItemStack(Material.STRUCTURE_VOID, 1);
        ItemMeta meta = sv.getItemMeta();
        meta.setCustomModelData(1);
        meta.setDisplayName(ChatColor.MAGIC+"");
        sv.setItemMeta(meta);
        for (int i = 0; i < 36; i++) {
            playerInventory.setItem(i, sv);
        }
        Inventory inventory = Bukkit.createInventory(null, inv_boxes, A);
        CreateItem(inv, Material.DIAMOND_SWORD, 1, 0, 0, "");
        CreateItem(inv, Material.DIAMOND_AXE, 1, 1, 0, "");
        CreateItem(inv, Material.COOKED_BEEF, 1, 2, 0, "");
        CreateItem(inv, Material.GOLDEN_APPLE, 1, 3, 0, "");
        CreateItem(inv, Material.BOW, 1, 26, 0, "");
        for (int i = 9; i < 18; i++) {
            CreateItem(inv, Material.WHITE_STAINED_GLASS_PANE, 1, i, 1, ChatColor.RESET+"");
        }
        CreateItem(inv, Material.LIME_CONCRETE, 1, 18, 1, Color("&a&lSubmit"), Color("&7Click to &aSubmit &7editing"));
        CreateItem(inv, Material.RED_CONCRETE, 1, 19, 1, Color("&c&lCancel"), Color("&7Click to &cCancel &7editing"));
        CreateItem(inv, Material.GRAY_CONCRETE, 1, 20, 1, Color("&8&lReset"), Color("&7Click to &8Reset &7to default"));
        CreateItem(inv, Material.WHITE_STAINED_GLASS_PANE, 1, 21, 1, ChatColor.RESET+"");
        CreateItem(inv, Material.WHITE_STAINED_GLASS_PANE, 1, 22, 1, ChatColor.RESET+"");
        CreateItem(inv, Material.WHITE_STAINED_GLASS_PANE, 1, 23, 1, ChatColor.RESET+"");
        CreateItem(inv, Material.WHITE_STAINED_GLASS_PANE, 1, 24, 1, ChatColor.RESET+"");
        CreateItem(inv, Material.WHITE_STAINED_GLASS_PANE, 1, 25, 1, ChatColor.RESET+"");
        inventory.setContents(inv.getContents());
        return inventory;
    }
}
