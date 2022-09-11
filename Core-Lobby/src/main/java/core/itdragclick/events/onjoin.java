package core.itdragclick.events;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import static core.itdragclick.Core.getserverselect;


public class onjoin implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        Player p = e.getPlayer();
        if (p.getGameMode() != GameMode.CREATIVE && p.getGameMode() != GameMode.SPECTATOR){
            p.setGameMode(GameMode.ADVENTURE);
            World SessionWorld = Bukkit.getServer().getWorld("Lobby");
            Location SessionWorldSpawn = new Location(SessionWorld, 100.5, 101, 100.5, 180f, 0f);
            p.teleport(SessionWorldSpawn);
            p.getInventory().clear();
        }
        getserverselect(p);
    }
}
