package core.itdragclick.events;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

import static core.itdragclick.Core.PLname;


public class canceldrops implements Listener {

    @EventHandler
    public void onPlayerDrops(PlayerDropItemEvent event) {
        Player p = event.getPlayer();
        if (p.getGameMode() != GameMode.CREATIVE) {
            event.setCancelled(true);
            p.sendMessage(PLname + ChatColor.RED + "You can't drop item.");
        }
    }
}