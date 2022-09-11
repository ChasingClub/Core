package net.apcat.simplesit.listeners;

import net.apcat.simplesit.SimpleSitPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuit implements Listener {
    public PlayerQuit() {
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        SimpleSitPlayer player = new SimpleSitPlayer(e.getPlayer());
        if (player.isSitting()) {
            player.setSitting(false);
        }

    }
}