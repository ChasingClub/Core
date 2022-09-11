package net.apcat.simplesit.utils;

import org.bukkit.ChatColor;

public enum Text {
    PLAYER_COMMAND(ChatColor.RED + "Only players can use this command!"),
    PREFIX("[" + ChatColor.AQUA + ChatColor.BOLD + "SimpleSit" + ChatColor.RESET + "] "),
    INVALID_PERMISSION_DEFAULT("%s has an invalid permission default: '%s'. Please use: TRUE, FALSE, OP, NOT_OP. Meanwhile using default settings."),
    INVALID_PERMISSION(ChatColor.DARK_RED + "You don't have permission to use this command."),
    LEGACY_ALIGN_ARMOR_STAND("Using legacy armor stand align, for mc versions 1.12 and below.");

    private String text;

    private Text(String text) {
        this.text = text;
    }

    public String format(Object... args) {
        return String.format(this.text, args);
    }

    public String toString() {
        return this.text;
    }
}