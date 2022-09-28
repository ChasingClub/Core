package chasingclub.server.core.events;

import org.bukkit.ChatColor;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

import static chasingclub.server.core.Utils.Utils.PluginName;

public class tellwhenkillwitharrow implements Listener {
    @EventHandler
    public void onHit(PlayerDeathEvent e){
        Player target = e.getPlayer();
        Player killer = target.getKiller();
        if (target.getLastDamageCause().getCause().equals(EntityDamageEvent.DamageCause.PROJECTILE)){
            if (killer == null){
                return;
            }
            double distance = target.getLocation().distance(killer.getLocation());
            double rounded = Math.round(distance * 10) / 10;
            String dis = Double.toString(rounded);
            killer.sendMessage(PluginName+"You shot that player with "+ ChatColor.GREEN +dis+ChatColor.GRAY+" meters.");
        }
    }
}
