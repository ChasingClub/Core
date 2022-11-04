package core.server.core.events;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.potion.PotionEffect;
import core.server.core.Core;

import static core.server.core.Core.*;

public class joinEvent implements Listener {
    public Core plugin;

    public joinEvent(Core plugin){
        this.plugin = plugin;
    }
    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if (Core.combatList.containsKey(e.getPlayer().getName())) {
            Core.combatList.put(p.getName(), 0);
        }
        gotkit.put(p.getName(), false);
        kits.put(p,"Default");
        if (p.getGameMode() != GameMode.CREATIVE && p.getGameMode() != GameMode.SPECTATOR){
            p.setGameMode(GameMode.ADVENTURE);
            p.teleport(plugin.spawnloc);
            p.getInventory().clear();
            GetKitSelect(p);
            for (PotionEffect effect : p.getActivePotionEffects())
                p.removePotionEffect(effect.getType());
            p.setWalkSpeed(0.2F); // default walk speed is 2F
        }
    }

}
