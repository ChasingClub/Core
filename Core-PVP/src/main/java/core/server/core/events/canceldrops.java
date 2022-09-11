package core.server.core.events;

import core.server.core.Core;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

public class canceldrops implements Listener {

    @EventHandler
    public void onPlayerDrops(PlayerDropItemEvent event) {
        Player p = event.getPlayer();
        if (p.getGameMode() != GameMode.CREATIVE) {
            event.setCancelled(true);
            p.sendMessage(Core.Plname + ChatColor.RED + "You can't drop item.");
        }
    }
}