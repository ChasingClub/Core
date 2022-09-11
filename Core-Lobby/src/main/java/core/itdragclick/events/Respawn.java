package core.itdragclick.events;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class Respawn implements Listener {
    @EventHandler
    public void onRespawn(PlayerRespawnEvent e) {
        Player p = e.getPlayer();
        if (p.getLocation().getWorld().getName().endsWith("Lobby")) {
            World SessionWorld = Bukkit.getServer().getWorld("Lobby");
            Location SessionWorldSpawn = new Location(SessionWorld, 100.5, 101, 100.5, 180f, 0f);
            e.setRespawnLocation(SessionWorldSpawn);
            p.sendMessage(ChatColor.RED + "You Died.");
        }
    }
    // WORK!
//    @EventHandler
//    public void onChat(AsyncPlayerChatEvent e){
//        char Marks = '\u0022';
//        Player p = e.getPlayer();
//        if (e.getMessage().contains(String.valueOf(Marks))) {
//            p.sendMessage(ChatColor.RED + "You Said Bad Sympol");
//            return;
//        }
//    }
}