package cc.commands.core;

import org.bukkit.ChatColor;

public class Utils {
    public static String PluginName = (ChatColor.YELLOW + "C" + ChatColor.LIGHT_PURPLE + "C" + ChatColor.DARK_GRAY + " » "+ChatColor.GRAY);
    public static String error = PluginName+ChatColor.RED+"Error: Something went wrong, please try again later.";
    public static String noperm = PluginName+ ChatColor.RED+"You don't have permission to do that!";
}