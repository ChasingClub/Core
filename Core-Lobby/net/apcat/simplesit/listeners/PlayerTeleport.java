package net.apcat.simplesit.listeners;

import net.apcat.simplesit.SimpleSitPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

public class PlayerTeleport implements Listener {
    public PlayerTeleport() {
    }

    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent e) {
        SimpleSitPlayer player = new SimpleSitPlayer(e.getPlayer());
        if (player.isSitting()) {
            player.setSitting(false);
        }

    }
}