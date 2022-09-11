package core.server.core.command;

import core.server.core.Core;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static org.bukkit.Bukkit.getServer;

public class setkit implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (cmd.getName().equalsIgnoreCase("setkit")) {
            // Check for arguments
            if (args.length == 0) {
                    if (!(sender.hasPermission("rank.admin"))) {
                        sender.sendMessage(ChatColor.RED+"You Don't have permission to do that!");
                        return true;
                    }else {
                        sender.sendMessage("/setkit <player> <kit>" + ChatColor.GRAY + " - " + ChatColor.GOLD + "Set kit player.");
                        return true;
                    }
            } else if (args.length == 1) {
                if (sender.hasPermission("rank.admin")) {
                    Player argplayer = getServer().getPlayer(args[0]);
                    if (argplayer == null) {
                        sender.sendMessage("Player " + ChatColor.GRAY + args[0] + ChatColor.RESET + " could not be found");
                        return true;
                    }
                    sender.sendMessage("/setkit "+argplayer.getName()+" <kit>" + ChatColor.GRAY + " - " + ChatColor.GOLD + "Set kit player.");
                    return true;
                } else {
                    sender.sendMessage(ChatColor.RED+"You Don't have permission to do that!");
                    return true;
                }
            } else if (args.length == 2) {
                if (sender.hasPermission("rank.admin")) {
                    Player argplayer = getServer().getPlayer(args[0]);
                    if (argplayer == null) {
                        sender.sendMessage("Player " + ChatColor.GRAY + args[0] + ChatColor.RESET + " could not be found");
                        return true;
                    }
                    if (Core.playerkits.get(args[1]) == null) {
                        sender.sendMessage(ChatColor.RED + "Kits: Default, trident, viking, bow, admin");
                        return true;
                    }
                    Integer kt = Core.playerkits.get(args[1]);
                    // Send command
                    if (kt == 1) {
                        Core.kits.put(argplayer, "Default");
                    }if (kt == 2) {
                        Core.kits.put(argplayer, "trident");
                    }if (kt == 3) {
                        Core.kits.put(argplayer, "viking");
                    }if (kt == 4) {
                        Core.kits.put(argplayer, "bow");
                    }if (kt == 5) {
                        Core.kits.put(argplayer, "admin");
                    }
                    sender.sendMessage(ChatColor.GREEN + "Set Kit "+ChatColor.RED+args[1]+ChatColor.GREEN+" for "+ChatColor.YELLOW+argplayer.getName()+ChatColor.GREEN+"!");
                    return true;
                } else {
                    sender.sendMessage(ChatColor.RED+"You Don't have permission to do that!");
                    return true;
                }
            } else {
                sender.sendMessage("/setkit <player> <kit>" + ChatColor.GRAY + " - " + ChatColor.GOLD + "Set kit player.");
                return true;
            }

        }
        return true;
    }
}
