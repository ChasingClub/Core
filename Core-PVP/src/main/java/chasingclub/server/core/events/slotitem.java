package chasingclub.server.core.events;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemStack;

import static org.bukkit.Bukkit.getServer;

public class slotitem implements Listener {
    @EventHandler
    public void onSlotItem(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        ItemStack i = e.getCurrentItem();
        World SessionWorld = getServer().getWorld("Netherite_game");
        if (p.getGameMode() != GameMode.CREATIVE && p.getWorld() == SessionWorld){
            if (i == null) {return;}
            if (i.getType() == Material.DIAMOND_HELMET || i.getType() == Material.DIAMOND_CHESTPLATE || i.getType() == Material.DIAMOND_LEGGINGS || i.getType() == Material.DIAMOND_BOOTS || i.getType() == Material.NETHERITE_HELMET || i.getType() == Material.NETHERITE_CHESTPLATE || i.getType() == Material.NETHERITE_LEGGINGS || i.getType() == Material.NETHERITE_BOOTS) {
                e.setCancelled(true);
            }
        }
    }
    @EventHandler
    public void onOffhand(PlayerSwapHandItemsEvent e){
        Player p = e.getPlayer();
        ItemStack i = e.getOffHandItem();
        World SessionWorld = getServer().getWorld("Netherite_game");
        if (p.getGameMode() != GameMode.CREATIVE && p.getWorld() == SessionWorld){
            if (i == null) {return;}
            if (i.getType() == Material.DIAMOND_HELMET || i.getType() == Material.DIAMOND_CHESTPLATE || i.getType() == Material.DIAMOND_LEGGINGS || i.getType() == Material.DIAMOND_BOOTS || i.getType() == Material.NETHERITE_HELMET || i.getType() == Material.NETHERITE_CHESTPLATE || i.getType() == Material.NETHERITE_LEGGINGS || i.getType() == Material.NETHERITE_BOOTS) {
                e.setCancelled(true);
            }
        }
    }
}
