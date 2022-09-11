package core.server.core.events;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.entity.SmallFireball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.meta.ItemMeta;

public class adminfireball implements Listener {
    @EventHandler
    public void Interact(PlayerInteractEvent e){
        Player p = e.getPlayer();
        Action act = e.getAction();

        EquipmentSlot eq = e.getHand();
        if (!(eq == EquipmentSlot.HAND)) {return;}

        if(act == Action.RIGHT_CLICK_BLOCK || act == Action.RIGHT_CLICK_AIR){
            if(p.getItemInHand().getType() == Material.BLAZE_ROD){
                ItemMeta im = p.getItemInHand().getItemMeta();
                if (im.getDisplayName().contains("§cAdmin§6Rod")) {
                    p.launchProjectile(Fireball.class).setVelocity(p.getLocation().getDirection().multiply(0.5));
                    return;
                }
            }
        }
    }
}
