package net.apcat.simplesit.events;

import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerSitEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    private Player player;
    private ArmorStand seat;
    private String message;
    private boolean canceled = false;

    public PlayerSitEvent(Player player, ArmorStand seat, String message) {
        this.player = player;
        this.seat = seat;
        this.message = message;
    }

    public Player getPlayer() {
        return this.player;
    }

    public ArmorStand getSeat() {
        return this.seat;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public boolean isCancelled() {
        return this.canceled;
    }

    public void setCancelled(boolean canceled) {
        this.canceled = canceled;
    }
}