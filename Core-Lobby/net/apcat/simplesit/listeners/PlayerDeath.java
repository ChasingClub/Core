package net.apcat.simplesit.listeners;

import net.apcat.simplesit.SimpleSitPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeath implements Listener {
    public PlayerDeath() {
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        SimpleSitPlayer player = new SimpleSitPlayer(e.getEntity());
        if (player.isSitting()) {
            player.setSitting(false);
        }

    }
}