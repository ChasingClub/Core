package core.server.core.events;

import core.server.core.Core;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;

public class consumeCooldown implements Listener {
    @EventHandler
    public void onPlayerEat(PlayerItemConsumeEvent e){
        Player p = e.getPlayer();
        if (e.getItem().getType() == Material.COOKED_BEEF && (p.getWorld() != Bukkit.getServer().getWorld("Netherite_game"))){
            Core.bhopcooldown.put(p.getName(), 5);
        }
    }
}
