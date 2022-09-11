package net.apcat.simplesit;

import net.apcat.simplesit.events.PlayerSitEvent;
import net.apcat.simplesit.events.PlayerStopSittingEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class SimpleSitPlayer {
    private Player player;
    private SimpleSit simpleSit;

    public SimpleSitPlayer(Player player) {
        this.player = player;
    }

    public Player getBukkitPlayer() {
        return this.player;
    }

    public void setSitting(boolean arg) {
        if (arg && !this.isSitting()) {
            Location location = this.player.getLocation();
            ArmorStand seat = (ArmorStand)location.getWorld().spawn(location.clone().subtract(0.0, 1.7, 0.0), ArmorStand.class);
            seat.setGravity(false);
            seat.setVisible(false);
            PlayerSitEvent playerSitEvent = new PlayerSitEvent(this.player, seat, this.simpleSit.getSitDownMessage());
            Bukkit.getPluginManager().callEvent(playerSitEvent);
            if (playerSitEvent.isCancelled()) {
                seat.remove();
                return;
            }

            this.player.sendMessage(playerSitEvent.getMessage());
            seat.setPassenger(this.player);
            this.simpleSit.getSeats().put(this.player.getUniqueId(), seat);
        } else if (!arg && this.isSitting()) {
            ArmorStand seat = (ArmorStand)this.simpleSit.getSeats().get(this.player.getUniqueId());
            PlayerStopSittingEvent playerStopSittingEvent = new PlayerStopSittingEvent(this.player, seat, this.simpleSit.getSitUpMessage());
            Bukkit.getPluginManager().callEvent(playerStopSittingEvent);
            this.player.sendMessage(playerStopSittingEvent.getMessage());
            this.simpleSit.getSeats().remove(this.player.getUniqueId());
            this.player.eject();
            this.player.teleport(seat.getLocation().clone().add(0.0, 1.7, 0.0));
            seat.remove();
        }

    }

    public boolean isSitting() {
        return this.simpleSit.getSeats().containsKey(this.player.getUniqueId());
    }
}