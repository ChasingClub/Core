package cc.core.events;

import cc.core.SQliteManager.YamlStorage;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class playerDealDamage implements Listener {
    @EventHandler
    public void onPlayerAttack(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player && event.getDamager() instanceof Player) {
            YamlStorage victim = new YamlStorage((Player) event.getEntity());
            YamlStorage attacker = new YamlStorage((Player) event.getDamager());
            if (!victim.pvpEnabled() || !attacker.pvpEnabled()) {
                event.setCancelled(true);
            }
        }
    }
}
