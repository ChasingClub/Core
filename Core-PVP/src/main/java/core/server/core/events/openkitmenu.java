package core.server.core.events;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

public class openkitmenu implements Listener {
    @EventHandler
    public void onPlayerUse(PlayerInteractEvent event){
        Player p = event.getPlayer();
        Action act = event.getAction();

        EquipmentSlot eq = event.getHand();
        if (!(eq == EquipmentSlot.HAND)) {return;}


        if(act == Action.RIGHT_CLICK_BLOCK){
            if(p.getItemInHand().getType() == Material.BOOK){
                Bukkit.dispatchCommand(p,"dm open kits");
                return;
            }
        }
        if(act == Action.RIGHT_CLICK_AIR){
            if(p.getItemInHand().getType() == Material.BOOK){
                Bukkit.dispatchCommand(p,"dm open kits");
                return;
            }
        }
    }
}
