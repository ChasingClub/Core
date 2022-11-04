package core.server.core.command.core;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class helps {
    public static void helpcore(CommandSender sender){
        sender.sendMessage(ChatColor.GRAY + "=------------[ " + ChatColor.GREEN + "Core" + ChatColor.RESET + ChatColor.GRAY + " ]------------=");
        sender.sendMessage(ChatColor.RED + "/core reload" + ChatColor.GRAY + " - " + ChatColor.GOLD + "Reload Core Configs");
        sender.sendMessage(ChatColor.GRAY + "=-------------------------------=");
    }
}
