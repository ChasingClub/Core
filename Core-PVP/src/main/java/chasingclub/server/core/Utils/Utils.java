package chasingclub.server.core.Utils;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Utils {
    public static String PluginName = (ChatColor.YELLOW + "C" + ChatColor.LIGHT_PURPLE + "C" + ChatColor.DARK_GRAY + " » "+ChatColor.GRAY);
    public static String error = PluginName+ChatColor.RED+"Error: Something went wrong, please try again later.";
    public static String noperm = PluginName+ ChatColor.RED+"You don't have permission to do that!";
    public static String GetGroupPlayer(Player p){
        String group = null;
        if (p.hasPermission("pf.default")){
            group = "Default";
        }if (p.hasPermission("pf.vip")) {
            group = "VIP";
        }if (p.hasPermission("pf.premium")) {
            group = "Premium";
        }if (p.hasPermission("pf.legend")) {
            group = "Legend";
        }if (p.hasPermission("pf.contributor")) {
            group = "Contributor";
        }if (p.hasPermission("pf.social")) {
            group = "Social";
        }if (p.hasPermission("pf.beta")) {
            group = "Beta Tester";
        }if (p.hasPermission("pf.staff")) {
            group = "Staff";
        }if (p.hasPermission("pf.builder")) {
            group = "Builder";
        }if (p.hasPermission("pf.mod")) {
            group = "Moderator";
        }if (p.hasPermission("pf.admin")) {
            group = "Admin";
        }if (p.hasPermission("pf.owner")) {
            group = "Owner";
        }
        return group;
    }
    public static String GetPrefixPlayer(Player p){
        String group = null;
        if (p.hasPermission("pf.default")){
            group = "§f\uE114 ";
        }if (p.hasPermission("pf.vip")) {
            group = "§f\uE113 ";
        }if (p.hasPermission("pf.premium")) {
            group = "§f\uE110 ";
        }if (p.hasPermission("pf.legend")) {
            group = "§f\uE108 ";
        }if (p.hasPermission("pf.contributor")) {
            group = "§f\uE107 ";
        }if (p.hasPermission("pf.social")) {
            group = "§f\uE106 ";
        }if (p.hasPermission("pf.beta")) {
            group = "§f\uE105 ";
        }if (p.hasPermission("pf.staff")) {
            group = "§f\uE104 ";
        }if (p.hasPermission("pf.builder")) {
            group = "§f\uE103 ";
        }if (p.hasPermission("pf.mod")) {
            group = "§f\uE102 ";
        }if (p.hasPermission("pf.admin")) {
            group = "§f\uE101 ";
        }if (p.hasPermission("pf.owner")) {
            group = "§f\uE100 ";
        }
        return group;
    }
    public static String GetGroupColorPlayer(Player p){
        String group = null;
        if (p.hasPermission("pf.default")){
            group = "§7";
        }if (p.hasPermission("pf.vip")) {
            group = "§3";
        }if (p.hasPermission("pf.premium")) {
            group = "§d";
        }if (p.hasPermission("pf.legend")) {
            group = "§6§l";
        }if (p.hasPermission("pf.contributor")) {
            group = "§6§l";
        }if (p.hasPermission("pf.social")) {
            group = "§c§l";
        }if (p.hasPermission("pf.beta")) {
            group = "§5";
        }if (p.hasPermission("pf.staff")) {
            group = "§a§l";
        }if (p.hasPermission("pf.builder")) {
            group = "§2§l";
        }if (p.hasPermission("pf.mod")) {
            group = "§9§l";
        }if (p.hasPermission("pf.admin")) {
            group = "§c§l";
        }if (p.hasPermission("pf.owner")) {
            group = "§b§l";
        }
        return group;
    }
}
