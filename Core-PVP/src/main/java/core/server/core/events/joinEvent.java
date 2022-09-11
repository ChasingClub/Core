package core.server.core.events;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.potion.PotionEffect;
import core.server.core.Core;

import static core.server.core.Core.GetKitSelect;
import static core.server.core.Core.kits;

public class joinEvent implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if (Core.combatList.containsKey(e.getPlayer().getName())) {
            Core.combatList.put(p.getName(), 0);
        }
        kits.put(p,"Default");
        if (p.getGameMode() != GameMode.CREATIVE && p.getGameMode() != GameMode.SPECTATOR){
            p.setGameMode(GameMode.ADVENTURE);
            World SessionWorld = Bukkit.getServer().getWorld("world");
            Location SessionWorldSpawn = new Location(SessionWorld, 64.5, 180, 26.5);
            p.teleport(SessionWorldSpawn);
            p.getInventory().clear();
            GetKitSelect(p);
            for (PotionEffect effect : p.getActivePotionEffects())
                p.removePotionEffect(effect.getType());
            p.setWalkSpeed(0.2F); // default walk speed is 2F
        }
    }

}
