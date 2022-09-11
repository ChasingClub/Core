package core.itdragclick.Command.maintenance.SubCommand;

import core.itdragclick.Core;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;

public class helps {
    public static void showhelp2(CommandSender sender){
        if (Boolean.TRUE.equals(Core.maintenance.get("Maintenance"))) {
            sender.sendMessage(ChatColor.GRAY + "=------------[ " + ChatColor.DARK_RED + ChatColor.BOLD + "Maintenance" + ChatColor.RESET + ChatColor.GRAY + " ]------------=");
            sender.sendMessage(ChatColor.RED + "/maintenance on" + ChatColor.GRAY + " - " + ChatColor.GOLD + "Turn maintenance mode on");
            sender.sendMessage(ChatColor.RED + "/maintenance off" + ChatColor.GRAY + " - " + ChatColor.GOLD + "Turn maintenance mode off");
            sender.sendMessage(ChatColor.RED + "/maintenance help" + ChatColor.GRAY + " - " + ChatColor.GOLD + "Show this message");
            sender.sendMessage(ChatColor.GRAY + "=---------------[ " + ChatColor.GREEN + ChatColor.BOLD + "ON" + ChatColor.GRAY + " ]---------------=");
        }else{
            sender.sendMessage(ChatColor.GRAY + "=------------[ " + ChatColor.DARK_RED + ChatColor.BOLD + "Maintenance" + ChatColor.RESET + ChatColor.GRAY + " ]------------=");
            sender.sendMessage(ChatColor.RED + "/maintenance on" + ChatColor.GRAY + " - " + ChatColor.GOLD + "Turn maintenance mode on");
            sender.sendMessage(ChatColor.RED + "/maintenance off" + ChatColor.GRAY + " - " + ChatColor.GOLD + "Turn maintenance mode off");
            sender.sendMessage(ChatColor.RED + "/maintenance help" + ChatColor.GRAY + " - " + ChatColor.GOLD + "Show this message");
            sender.sendMessage(ChatColor.GRAY + "=--------------[ " + ChatColor.RED + ChatColor.BOLD + "OFF" + ChatColor.GRAY + " ]---------------=");
        }
    }
}
