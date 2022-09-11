package core.itdragclick.Command.whitelist.SubCommand;

import core.itdragclick.Core;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;

public class helps {
    public static void showhelp2(CommandSender sender){
        if (Boolean.TRUE.equals(Core.whitelist.get("Whitelist"))) {
            sender.sendMessage(ChatColor.GRAY + "=------------[ " + ChatColor.AQUA + ChatColor.BOLD + "Whitelist" + ChatColor.RESET + ChatColor.GRAY + " ]------------=");
            sender.sendMessage(ChatColor.BLUE + "/whitelist on" + ChatColor.GRAY + " - " + ChatColor.GOLD + "Turn whitelist on");
            sender.sendMessage(ChatColor.BLUE + "/whitelist off" + ChatColor.GRAY + " - " + ChatColor.GOLD + "Turn whitelist off");
            sender.sendMessage(ChatColor.BLUE + "/whitelist add <name>" + ChatColor.GRAY + " - " + ChatColor.GOLD + "Add a player whitelist");
            sender.sendMessage(ChatColor.BLUE + "/whitelist remove <name>" + ChatColor.GRAY + " - " + ChatColor.GOLD + "Remove a player whitelist");
            sender.sendMessage(ChatColor.BLUE + "/whitelist help" + ChatColor.GRAY + " - " + ChatColor.GOLD + "Show this message");
            sender.sendMessage(ChatColor.GRAY + "=---------------[ " + ChatColor.GREEN + ChatColor.BOLD + "ON" + ChatColor.GRAY + " ]---------------=");
        }else{
            sender.sendMessage(ChatColor.GRAY + "=------------[ " + ChatColor.AQUA + ChatColor.BOLD + "Whitelist" + ChatColor.RESET + ChatColor.GRAY + " ]------------=");
            sender.sendMessage(ChatColor.BLUE + "/whitelist on" + ChatColor.GRAY + " - " + ChatColor.GOLD + "Turn whitelist on");
            sender.sendMessage(ChatColor.BLUE + "/whitelist off" + ChatColor.GRAY + " - " + ChatColor.GOLD + "Turn whitelist off");
            sender.sendMessage(ChatColor.BLUE + "/whitelist add <name>" + ChatColor.GRAY + " - " + ChatColor.GOLD + "Add a player whitelist");
            sender.sendMessage(ChatColor.BLUE + "/whitelist remove <name>" + ChatColor.GRAY + " - " + ChatColor.GOLD + "Remove a player whitelist");
            sender.sendMessage(ChatColor.BLUE + "/whitelist help" + ChatColor.GRAY + " - " + ChatColor.GOLD + "Show this message");
            sender.sendMessage(ChatColor.GRAY + "=--------------[ " + ChatColor.RED + ChatColor.BOLD + "OFF" + ChatColor.GRAY + " ]---------------=");
        }
    }
}
