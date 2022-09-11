package core.server.core.events;

import core.server.core.Core;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.potion.PotionEffect;

public class Respawn implements Listener {
    @EventHandler
    public void onRespawn(PlayerRespawnEvent e) {
        Player p = e.getPlayer();
        if (p.getLocation().getWorld().getName().endsWith("world")) {
                Core.combatList.put(p.getName(), 0);
                World SessionWorld = Bukkit.getServer().getWorld("world");
                Location SessionWorldSpawn = new Location(SessionWorld, 64.5, 180.5, 26.5);
                e.setRespawnLocation(SessionWorldSpawn);
                p.teleport(SessionWorldSpawn);
            if (p.getGameMode() != GameMode.CREATIVE && p.getGameMode() != GameMode.SPECTATOR) {
                p.getInventory().clear();
                Core.GetKitSelect(p);
                for (PotionEffect effect : p.getActivePotionEffects())
                p.removePotionEffect(effect.getType());
            }
            p.sendMessage(ChatColor.RED + "You Died.");
        }
    }
}