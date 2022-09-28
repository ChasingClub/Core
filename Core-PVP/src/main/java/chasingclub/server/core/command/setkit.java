package chasingclub.server.core.command;

import chasingclub.server.core.Core;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static org.bukkit.Bukkit.getServer;

public class setkit implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, Command cmd, @NotNull String commandLabel, String[] args) {
        if (cmd.getName().equalsIgnoreCase("setkit")) {
            // Check for arguments
            if (args.length == 0) {
                    if (!(sender.hasPermission("rank.admin"))) {
                        sender.sendMessage(ChatColor.RED+"You Don't have permission to do that!");
                    }else {
                        sender.sendMessage("/setkit <player> <kit>" + ChatColor.GRAY + " - " + ChatColor.GOLD + "Set kit player.");
                    }
                return true;
            } else if (args.length == 1) {
                if (sender.hasPermission("rank.admin")) {
                    Player argplayer = getServer().getPlayer(args[0]);
                    if (argplayer == null) {
                        sender.sendMessage("Player " + ChatColor.GRAY + args[0] + ChatColor.RESET + " could not be found");
                        return true;
                    }
                    sender.sendMessage("/setkit "+argplayer.getName()+" <kit>" + ChatColor.GRAY + " - " + ChatColor.GOLD + "Set kit player.");
                } else {
                    sender.sendMessage(ChatColor.RED+"You Don't have permission to do that!");
                }
                return true;
            } else if (args.length == 2) {
                if (sender.hasPermission("rank.admin")) {
                    Player argplayer = getServer().getPlayer(args[0]);
                    if (argplayer == null) {
                        sender.sendMessage("Player " + ChatColor.GRAY + args[0] + ChatColor.RESET + " could not be found");
                        return true;
                    }
                    if (Core.playerkits.get(args[1]) == null) {
                        sender.sendMessage(ChatColor.RED + "Kits: Default, Trident, Viking, Archer, Admin");
                        return true;
                    }
                    Integer kt = Core.playerkits.get(args[1]);
                    // Send command
                    if (kt == 1) {
                        Core.kits.put(argplayer, "Default");
                    }else if (kt == 2) {
                        Core.kits.put(argplayer, "Trident");
                    }else if (kt == 3) {
                        Core.kits.put(argplayer, "Viking");
                    }else if (kt == 4) {
                        Core.kits.put(argplayer, "Archer");
                    }else if (kt == 5) {
                        Core.kits.put(argplayer, "Admin");
                    }
                    sender.sendMessage(ChatColor.GREEN + "Set Kit "+ChatColor.RED+args[1]+ChatColor.GREEN+" for "+ChatColor.YELLOW+argplayer.getName()+ChatColor.GREEN+"!");
                } else {
                    sender.sendMessage(ChatColor.RED+"You don't have permission to do that!");
                }
                return true;
            } else {
                sender.sendMessage("/setkit <player> <kit>" + ChatColor.GRAY + " - " + ChatColor.GOLD + "Set kit player.");
                return true;
            }

        }
        return true;
    }
}
