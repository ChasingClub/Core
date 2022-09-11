package core.itdragclick.events;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;

public class slotitem implements Listener {
    @EventHandler
    public void onSlotItem(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        if (p.getGameMode() != GameMode.CREATIVE){
            e.setCancelled(true);
        }
    }
    @EventHandler
    public void onOffhand(PlayerSwapHandItemsEvent e){
        Player p = e.getPlayer();
        if (p.getGameMode() != GameMode.CREATIVE){
            e.setCancelled(true);
        }
    }
}
