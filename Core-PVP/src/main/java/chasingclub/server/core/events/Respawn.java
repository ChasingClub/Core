package chasingclub.server.core.events;

import chasingclub.server.core.Core;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.potion.PotionEffect;

public class Respawn implements Listener {
    public Core plugin;

    public Respawn(Core plugin){
        this.plugin = plugin;
    }
    @EventHandler
    public void onRespawn(PlayerRespawnEvent e) {
        Player p = e.getPlayer();
        if (p.getLocation().getWorld().getName().endsWith("world")) {
                Core.combatList.put(p.getName(), 0);
                e.setRespawnLocation(plugin.spawnloc);
                p.teleport(plugin.spawnloc);
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