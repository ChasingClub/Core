package core.itdragclick.Commands.Core.Subcommand;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class helps {
    public static void helpcore(CommandSender sender){
        sender.sendMessage(ChatColor.GRAY + "=------------[ " + ChatColor.GREEN + "Core" + ChatColor.RESET + ChatColor.GRAY + " ]------------=");
        sender.sendMessage(ChatColor.RED + "/core reload" + ChatColor.GRAY + " - " + ChatColor.GOLD + "Reload Core Plugin");
        sender.sendMessage(ChatColor.RED + "/core reloadconfig" + ChatColor.GRAY + " - " + ChatColor.GOLD + "Reload Core Configs");
        sender.sendMessage(ChatColor.RED + "/core help" + ChatColor.GRAY + " - " + ChatColor.GOLD + "Show this meessage");
        sender.sendMessage(ChatColor.GRAY + "=-------------------------------=");
    }
}
