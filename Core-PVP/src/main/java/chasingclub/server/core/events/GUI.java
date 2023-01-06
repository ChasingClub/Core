package chasingclub.server.core.events;

import chasingclub.server.core.Utils.HotbarGui;
import chasingclub.server.core.Utils.SlotSQL;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

import java.sql.SQLException;

import static chasingclub.server.core.Core.GetKitSelect;
import static chasingclub.server.core.Utils.Database.*;
import static chasingclub.server.core.Utils.HotbarGui.*;
import static chasingclub.server.core.Utils.Utils.PluginName;
import static chasingclub.server.core.Utils.Utils.error;

public class GUI implements Listener {
    @EventHandler
    public void onCloseInventory(InventoryCloseEvent e){
        Player p = (Player) e.getPlayer();
        InventoryView view = e.getView();
        if (view.getTitle().equals(NS) || view.getTitle().equals(CS) || view.getTitle().equals(D) || view.getTitle().equals(T) || view.getTitle().equals(V) || view.getTitle().equals(A)) {
            p.getOpenInventory().setCursor(null);
            p.getInventory().clear();
            GetKitSelect(p);
        }
    }
    public SlotSQL GetPlayerSlot(String uuid, String table){
        try {
            return FindSlotByUUID(uuid,table);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @EventHandler
    public void onClick(InventoryClickEvent e){
        Player p = (Player) e.getWhoClicked();
        InventoryView view = e.getView();
        ItemStack item = e.getCurrentItem();
        if(item == null){return;}
        if(item.getType() == Material.WHITE_STAINED_GLASS_PANE && item.getItemMeta().hasCustomModelData() && item.getItemMeta().getCustomModelData() == 1) {
            if (view.getTitle().equals(NS) || view.getTitle().equals(CS) || view.getTitle().equals(D) || view.getTitle().equals(T) || view.getTitle().equals(V) || view.getTitle().equals(A)) {
                e.setCancelled(true);
            }
        }
        if(item.getType() == Material.STRUCTURE_VOID && item.getItemMeta().hasCustomModelData() && item.getItemMeta().getCustomModelData() == 1) {
            if (view.getTitle().equals(NS) || view.getTitle().equals(CS) || view.getTitle().equals(D) || view.getTitle().equals(T) || view.getTitle().equals(V) || view.getTitle().equals(A)) {
                e.setCancelled(true);
            }
        }
        /////////////////////////////////////////////////////////////////////////////////////
        //////////////////////////////        THINGS        /////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////////////
        if(item.getType() == Material.LIME_CONCRETE && item.getItemMeta().hasCustomModelData() && item.getItemMeta().getCustomModelData() == 1) {
            if (view.getTitle().equals(NS)) {
                e.setCancelled(true);
                SlotSQL data = GetPlayerSlot(p.getUniqueId().toString(),"netherite_stack");
                InventoryView inv = p.getOpenInventory();

                String t1 = "AIR";
                String t2 = "AIR";
                String t3 = "AIR";
                String t4 = "AIR";
                String t5 = "AIR";
                String t6 = "AIR";
                String t7 = "AIR";
                String t8 = "AIR";
                String t9 = "AIR";
                String t10 = "AIR";
                if(inv.getTopInventory().getItem(0) != null) {
                    t1 = inv.getTopInventory().getItem(0).getType().name();
                }
                if(inv.getTopInventory().getItem(1) != null) {
                    t2 = inv.getTopInventory().getItem(1).getType().name();
                }
                if(inv.getTopInventory().getItem(2) != null) {
                    t3 = inv.getTopInventory().getItem(2).getType().name();
                }
                if(inv.getTopInventory().getItem(3) != null) {
                    t4 = inv.getTopInventory().getItem(3).getType().name();
                }
                if(inv.getTopInventory().getItem(4) != null) {
                    t5 = inv.getTopInventory().getItem(4).getType().name();
                }
                if(inv.getTopInventory().getItem(5) != null) {
                    t6 = inv.getTopInventory().getItem(5).getType().name();
                }
                if(inv.getTopInventory().getItem(6) != null) {
                    t7 = inv.getTopInventory().getItem(6).getType().name();
                }
                if(inv.getTopInventory().getItem(7) != null) {
                    t8 = inv.getTopInventory().getItem(7).getType().name();
                }
                if(inv.getTopInventory().getItem(8) != null) {
                    t9 = inv.getTopInventory().getItem(8).getType().name();
                }
                if(inv.getTopInventory().getItem(26) != null) {
                    t10 = inv.getTopInventory().getItem(26).getType().name();
                }
                if(data == null){
                    try {
                        AddSlot("netherite_stack",p.getUniqueId().toString(),t1,t2,t3,t4,t5,t6,t7,t8,t9,t10);
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        p.closeInventory();
                        p.sendMessage(error);
                        return;
                    }
                }else{
                    try {
                        UpdateSlot("netherite_stack",p.getUniqueId().toString(),t1,t2,t3,t4,t5,t6,t7,t8,t9,t10);
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        p.closeInventory();
                        p.sendMessage(error);
                        return;
                    }
                }
                p.getOpenInventory().getTopInventory().clear();
                p.getOpenInventory().getBottomInventory().clear();
                p.getOpenInventory().setCursor(null);
                p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1F, 2F);
                p.closeInventory();
                p.sendMessage(PluginName+ ChatColor.GREEN+"Your Kit have been saved!");
                GetKitSelect(p);
            }else if (view.getTitle().equals(CS)) {
                e.setCancelled(true);
                SlotSQL data = GetPlayerSlot(p.getUniqueId().toString(),"classic_sword");
                InventoryView inv = p.getOpenInventory();

                String t1 = "AIR";
                String t2 = "AIR";
                String t3 = "AIR";
                String t4 = "AIR";
                String t5 = "AIR";
                String t6 = "AIR";
                String t7 = "AIR";
                String t8 = "AIR";
                String t9 = "AIR";
                String t10 = "AIR";
                if(inv.getTopInventory().getItem(0) != null) {
                    t1 = inv.getTopInventory().getItem(0).getType().name();
                }
                if(inv.getTopInventory().getItem(1) != null) {
                    t2 = inv.getTopInventory().getItem(1).getType().name();
                }
                if(inv.getTopInventory().getItem(2) != null) {
                    t3 = inv.getTopInventory().getItem(2).getType().name();
                }
                if(inv.getTopInventory().getItem(3) != null) {
                    t4 = inv.getTopInventory().getItem(3).getType().name();
                }
                if(inv.getTopInventory().getItem(4) != null) {
                    t5 = inv.getTopInventory().getItem(4).getType().name();
                }
                if(inv.getTopInventory().getItem(5) != null) {
                    t6 = inv.getTopInventory().getItem(5).getType().name();
                }
                if(inv.getTopInventory().getItem(6) != null) {
                    t7 = inv.getTopInventory().getItem(6).getType().name();
                }
                if(inv.getTopInventory().getItem(7) != null) {
                    t8 = inv.getTopInventory().getItem(7).getType().name();
                }
                if(inv.getTopInventory().getItem(8) != null) {
                    t9 = inv.getTopInventory().getItem(8).getType().name();
                }
                if(inv.getTopInventory().getItem(26) != null) {
                    t10 = inv.getTopInventory().getItem(26).getType().name();
                }
                if(data == null){
                    try {
                        AddSlot("classic_sword",p.getUniqueId().toString(),t1,t2,t3,t4,t5,t6,t7,t8,t9,t10);
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        p.closeInventory();
                        p.sendMessage(error);
                        return;
                    }
                }else{
                    try {
                        UpdateSlot("classic_sword",p.getUniqueId().toString(),t1,t2,t3,t4,t5,t6,t7,t8,t9,t10);
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        p.closeInventory();
                        p.sendMessage(error);
                        return;
                    }
                }
                p.getOpenInventory().getTopInventory().clear();
                p.getOpenInventory().getBottomInventory().clear();
                p.getOpenInventory().setCursor(null);
                p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1F, 2F);
                p.closeInventory();
                p.sendMessage(PluginName+ ChatColor.GREEN+"Your Kit have been saved!");
                GetKitSelect(p);
            }else if (view.getTitle().equals(D)) {
                e.setCancelled(true);
                SlotSQL data = GetPlayerSlot(p.getUniqueId().toString(),"default_kit");
                InventoryView inv = p.getOpenInventory();

                String t1 = "AIR";
                String t2 = "AIR";
                String t3 = "AIR";
                String t4 = "AIR";
                String t5 = "AIR";
                String t6 = "AIR";
                String t7 = "AIR";
                String t8 = "AIR";
                String t9 = "AIR";
                String t10 = "AIR";
                if(inv.getTopInventory().getItem(0) != null) {
                    t1 = inv.getTopInventory().getItem(0).getType().name();
                }
                if(inv.getTopInventory().getItem(1) != null) {
                    t2 = inv.getTopInventory().getItem(1).getType().name();
                }
                if(inv.getTopInventory().getItem(2) != null) {
                    t3 = inv.getTopInventory().getItem(2).getType().name();
                }
                if(inv.getTopInventory().getItem(3) != null) {
                    t4 = inv.getTopInventory().getItem(3).getType().name();
                }
                if(inv.getTopInventory().getItem(4) != null) {
                    t5 = inv.getTopInventory().getItem(4).getType().name();
                }
                if(inv.getTopInventory().getItem(5) != null) {
                    t6 = inv.getTopInventory().getItem(5).getType().name();
                }
                if(inv.getTopInventory().getItem(6) != null) {
                    t7 = inv.getTopInventory().getItem(6).getType().name();
                }
                if(inv.getTopInventory().getItem(7) != null) {
                    t8 = inv.getTopInventory().getItem(7).getType().name();
                }
                if(inv.getTopInventory().getItem(8) != null) {
                    t9 = inv.getTopInventory().getItem(8).getType().name();
                }
                if(inv.getTopInventory().getItem(26) != null) {
                    t10 = inv.getTopInventory().getItem(26).getType().name();
                }
                if(data == null){
                    try {
                        AddSlot("default_kit",p.getUniqueId().toString(),t1,t2,t3,t4,t5,t6,t7,t8,t9,t10);
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        p.closeInventory();
                        p.sendMessage(error);
                        return;
                    }
                }else{
                    try {
                        UpdateSlot("default_kit",p.getUniqueId().toString(),t1,t2,t3,t4,t5,t6,t7,t8,t9,t10);
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        p.closeInventory();
                        p.sendMessage(error);
                        return;
                    }
                }
                p.getOpenInventory().getTopInventory().clear();
                p.getOpenInventory().getBottomInventory().clear();
                p.getOpenInventory().setCursor(null);
                p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1F, 2F);
                p.closeInventory();
                p.sendMessage(PluginName+ ChatColor.GREEN+"Your Kit have been saved!");
                GetKitSelect(p);
            }else if (view.getTitle().equals(T)) {
                e.setCancelled(true);
                SlotSQL data = GetPlayerSlot(p.getUniqueId().toString(),"trident_kit");
                InventoryView inv = p.getOpenInventory();

                String t1 = "AIR";
                String t2 = "AIR";
                String t3 = "AIR";
                String t4 = "AIR";
                String t5 = "AIR";
                String t6 = "AIR";
                String t7 = "AIR";
                String t8 = "AIR";
                String t9 = "AIR";
                String t10 = "AIR";
                if(inv.getTopInventory().getItem(0) != null) {
                    t1 = inv.getTopInventory().getItem(0).getType().name();
                }
                if(inv.getTopInventory().getItem(1) != null) {
                    t2 = inv.getTopInventory().getItem(1).getType().name();
                }
                if(inv.getTopInventory().getItem(2) != null) {
                    t3 = inv.getTopInventory().getItem(2).getType().name();
                }
                if(inv.getTopInventory().getItem(3) != null) {
                    t4 = inv.getTopInventory().getItem(3).getType().name();
                }
                if(inv.getTopInventory().getItem(4) != null) {
                    t5 = inv.getTopInventory().getItem(4).getType().name();
                }
                if(inv.getTopInventory().getItem(5) != null) {
                    t6 = inv.getTopInventory().getItem(5).getType().name();
                }
                if(inv.getTopInventory().getItem(6) != null) {
                    t7 = inv.getTopInventory().getItem(6).getType().name();
                }
                if(inv.getTopInventory().getItem(7) != null) {
                    t8 = inv.getTopInventory().getItem(7).getType().name();
                }
                if(inv.getTopInventory().getItem(8) != null) {
                    t9 = inv.getTopInventory().getItem(8).getType().name();
                }
                if(inv.getTopInventory().getItem(26) != null) {
                    t10 = inv.getTopInventory().getItem(26).getType().name();
                }
                if(data == null){
                    try {
                        AddSlot("trident_kit",p.getUniqueId().toString(),t1,t2,t3,t4,t5,t6,t7,t8,t9,t10);
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        p.closeInventory();
                        p.sendMessage(error);
                        return;
                    }
                }else{
                    try {
                        UpdateSlot("trident_kit",p.getUniqueId().toString(),t1,t2,t3,t4,t5,t6,t7,t8,t9,t10);
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        p.closeInventory();
                        p.sendMessage(error);
                        return;
                    }
                }
                p.getOpenInventory().getTopInventory().clear();
                p.getOpenInventory().getBottomInventory().clear();
                p.getOpenInventory().setCursor(null);
                p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1F, 2F);
                p.closeInventory();
                p.sendMessage(PluginName+ ChatColor.GREEN+"Your Kit have been saved!");
                GetKitSelect(p);
            }else if (view.getTitle().equals(V)) {
                e.setCancelled(true);
                SlotSQL data = GetPlayerSlot(p.getUniqueId().toString(),"viking_kit");
                InventoryView inv = p.getOpenInventory();

                String t1 = "AIR";
                String t2 = "AIR";
                String t3 = "AIR";
                String t4 = "AIR";
                String t5 = "AIR";
                String t6 = "AIR";
                String t7 = "AIR";
                String t8 = "AIR";
                String t9 = "AIR";
                String t10 = "AIR";
                if(inv.getTopInventory().getItem(0) != null) {
                    t1 = inv.getTopInventory().getItem(0).getType().name();
                }
                if(inv.getTopInventory().getItem(1) != null) {
                    t2 = inv.getTopInventory().getItem(1).getType().name();
                }
                if(inv.getTopInventory().getItem(2) != null) {
                    t3 = inv.getTopInventory().getItem(2).getType().name();
                }
                if(inv.getTopInventory().getItem(3) != null) {
                    t4 = inv.getTopInventory().getItem(3).getType().name();
                }
                if(inv.getTopInventory().getItem(4) != null) {
                    t5 = inv.getTopInventory().getItem(4).getType().name();
                }
                if(inv.getTopInventory().getItem(5) != null) {
                    t6 = inv.getTopInventory().getItem(5).getType().name();
                }
                if(inv.getTopInventory().getItem(6) != null) {
                    t7 = inv.getTopInventory().getItem(6).getType().name();
                }
                if(inv.getTopInventory().getItem(7) != null) {
                    t8 = inv.getTopInventory().getItem(7).getType().name();
                }
                if(inv.getTopInventory().getItem(8) != null) {
                    t9 = inv.getTopInventory().getItem(8).getType().name();
                }
                if(inv.getTopInventory().getItem(26) != null) {
                    t10 = inv.getTopInventory().getItem(26).getType().name();
                }
                if(data == null){
                    try {
                        AddSlot("viking_kit",p.getUniqueId().toString(),t1,t2,t3,t4,t5,t6,t7,t8,t9,t10);
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        p.closeInventory();
                        p.sendMessage(error);
                        return;
                    }
                }else{
                    try {
                        UpdateSlot("viking_kit",p.getUniqueId().toString(),t1,t2,t3,t4,t5,t6,t7,t8,t9,t10);
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        p.closeInventory();
                        p.sendMessage(error);
                        return;
                    }
                }
                p.getOpenInventory().getTopInventory().clear();
                p.getOpenInventory().getBottomInventory().clear();
                p.getOpenInventory().setCursor(null);
                p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1F, 2F);
                p.closeInventory();
                p.sendMessage(PluginName+ ChatColor.GREEN+"Your Kit have been saved!");
                GetKitSelect(p);
            }else if (view.getTitle().equals(A)) {
                e.setCancelled(true);
                SlotSQL data = GetPlayerSlot(p.getUniqueId().toString(),"archer_kit");
                InventoryView inv = p.getOpenInventory();

                String t1 = "AIR";
                String t2 = "AIR";
                String t3 = "AIR";
                String t4 = "AIR";
                String t5 = "AIR";
                String t6 = "AIR";
                String t7 = "AIR";
                String t8 = "AIR";
                String t9 = "AIR";
                String t10 = "AIR";
                if(inv.getTopInventory().getItem(0) != null) {
                    t1 = inv.getTopInventory().getItem(0).getType().name();
                }
                if(inv.getTopInventory().getItem(1) != null) {
                    t2 = inv.getTopInventory().getItem(1).getType().name();
                }
                if(inv.getTopInventory().getItem(2) != null) {
                    t3 = inv.getTopInventory().getItem(2).getType().name();
                }
                if(inv.getTopInventory().getItem(3) != null) {
                    t4 = inv.getTopInventory().getItem(3).getType().name();
                }
                if(inv.getTopInventory().getItem(4) != null) {
                    t5 = inv.getTopInventory().getItem(4).getType().name();
                }
                if(inv.getTopInventory().getItem(5) != null) {
                    t6 = inv.getTopInventory().getItem(5).getType().name();
                }
                if(inv.getTopInventory().getItem(6) != null) {
                    t7 = inv.getTopInventory().getItem(6).getType().name();
                }
                if(inv.getTopInventory().getItem(7) != null) {
                    t8 = inv.getTopInventory().getItem(7).getType().name();
                }
                if(inv.getTopInventory().getItem(8) != null) {
                    t9 = inv.getTopInventory().getItem(8).getType().name();
                }
                if(inv.getTopInventory().getItem(26) != null) {
                    t10 = inv.getTopInventory().getItem(26).getType().name();
                }
                if(data == null){
                    try {
                        AddSlot("archer_kit",p.getUniqueId().toString(),t1,t2,t3,t4,t5,t6,t7,t8,t9,t10);
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        p.closeInventory();
                        p.sendMessage(error);
                        return;
                    }
                }else{
                    try {
                        UpdateSlot("archer_kit",p.getUniqueId().toString(),t1,t2,t3,t4,t5,t6,t7,t8,t9,t10);
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        p.closeInventory();
                        p.sendMessage(error);
                        return;
                    }
                }
                p.getOpenInventory().getTopInventory().clear();
                p.getOpenInventory().getBottomInventory().clear();
                p.getOpenInventory().setCursor(null);
                p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1F, 2F);
                p.closeInventory();
                p.sendMessage(PluginName+ ChatColor.GREEN+"Your Kit have been saved!");
                GetKitSelect(p);
            }
        }
        /////////////////////////////////////////////////////////////////////////////////////
        if(item.getType() == Material.RED_CONCRETE && item.getItemMeta().hasCustomModelData() && item.getItemMeta().getCustomModelData() == 1) {
            if (view.getTitle().equals(NS) || view.getTitle().equals(CS) || view.getTitle().equals(D) || view.getTitle().equals(T) || view.getTitle().equals(V) || view.getTitle().equals(A)) {
                e.setCancelled(true);
                p.getOpenInventory().getTopInventory().clear();
                p.getOpenInventory().getBottomInventory().clear();
                p.getOpenInventory().setCursor(null);
                p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO, 1F, 1.2F);
                p.closeInventory();
                p.sendMessage(PluginName+ ChatColor.RED+"You have cancel editing.");
                GetKitSelect(p);
            }
        }
        if(item.getType() == Material.GRAY_CONCRETE && item.getItemMeta().hasCustomModelData() && item.getItemMeta().getCustomModelData() == 1) {
            if (view.getTitle().equals(NS)) {
                e.setCancelled(true);
                p.getInventory().remove(p.getItemOnCursor());
                p.getOpenInventory().getTopInventory().clear();
                p.getOpenInventory().getBottomInventory().clear();
                p.getOpenInventory().setCursor(null);
                p.closeInventory();
                p.getInventory().clear();
                p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_LAND, 0.75F, 2F);
                p.openInventory(HotbarGui.GUINetherite(p));
            }else if (view.getTitle().equals(CS)) {
                e.setCancelled(true);
                p.getInventory().remove(p.getItemOnCursor());
                p.getOpenInventory().getTopInventory().clear();
                p.getOpenInventory().getBottomInventory().clear();
                p.getOpenInventory().setCursor(null);
                p.closeInventory();
                p.getInventory().clear();
                p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_LAND, 0.75F, 2F);
                p.openInventory(HotbarGui.GUIClassicSword(p));
            }else if (view.getTitle().equals(D)) {
                e.setCancelled(true);
                p.getInventory().remove(p.getItemOnCursor());
                p.getOpenInventory().getTopInventory().clear();
                p.getOpenInventory().getBottomInventory().clear();
                p.getOpenInventory().setCursor(null);
                p.closeInventory();
                p.getInventory().clear();
                p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_LAND, 0.75F, 2F);
                p.openInventory(HotbarGui.GUIDefault(p));
            }else if (view.getTitle().equals(T)) {
                e.setCancelled(true);
                p.getInventory().remove(p.getItemOnCursor());
                p.getOpenInventory().getTopInventory().clear();
                p.getOpenInventory().getBottomInventory().clear();
                p.getOpenInventory().setCursor(null);
                p.closeInventory();
                p.getInventory().clear();
                p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_LAND, 0.75F, 2F);
                p.openInventory(HotbarGui.GUITrident(p));
            }else if (view.getTitle().equals(V)) {
                e.setCancelled(true);
                p.getInventory().remove(p.getItemOnCursor());
                p.getOpenInventory().getTopInventory().clear();
                p.getOpenInventory().getBottomInventory().clear();
                p.getOpenInventory().setCursor(null);
                p.closeInventory();
                p.getInventory().clear();
                p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_LAND, 0.75F, 2F);
                p.openInventory(HotbarGui.GUIViking(p));
            }else if (view.getTitle().equals(A)) {
                e.setCancelled(true);
                p.getInventory().remove(p.getItemOnCursor());
                p.getOpenInventory().getTopInventory().clear();
                p.getOpenInventory().getBottomInventory().clear();
                p.getOpenInventory().setCursor(null);
                p.closeInventory();
                p.getInventory().clear();
                p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_LAND, 0.75F, 2F);
                p.openInventory(HotbarGui.GUIArcher(p));
            }
        }
    }
}
