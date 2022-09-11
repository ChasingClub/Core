package net.apcat.simplesit.listeners;

import net.apcat.simplesit.SimpleSitArmorStand;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;

public class PlayerArmorStandManipulate implements Listener {
    public PlayerArmorStandManipulate() {
    }

    @EventHandler
    public void onPlayerArmorStandManipulate(PlayerArmorStandManipulateEvent e) {
        SimpleSitArmorStand simpleSitArmorStand = new SimpleSitArmorStand(e.getRightClicked());
        if (simpleSitArmorStand.isSeat()) {
            e.setCancelled(true);
        }

    }
}