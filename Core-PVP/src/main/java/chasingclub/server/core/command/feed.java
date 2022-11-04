package chasingclub.server.core.command;


import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import static org.bukkit.Bukkit.getServer;

public class feed implements CommandExecutor, Listener {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (cmd.getName().equalsIgnoreCase("feed")) {
            // Check for arguments
            if (args.length == 0) {
                if (!(sender instanceof Player)) {
                    sender.sendMessage("Can use with only player.");
                    return true;
                }else {
                    Player p = (Player) sender;
                    // Send command overview
                    if (sender.hasPermission("rank.staff")) {
                        // Define player object
                        p.setFoodLevel(20);
                        p.setSaturation(20f);
                        p.sendMessage(ChatColor.GOLD + p.getName() + " has been feed.");
                        return true;
                    } else {
                        sender.sendMessage(ChatColor.RED+"You don't have the permission");
                        return true;
                    }
                }
            } else if (args.length == 1) {
                if (sender.hasPermission("rank.staff")) {
                    Player argplayer = getServer().getPlayer(args[0]);
                    if (argplayer == null) {
                        sender.sendMessage("Player " + ChatColor.GRAY + args[0] + ChatColor.RESET + " could not be found");
                        return true;
                    }
                    // Send command overview
                    argplayer.setFoodLevel(20);
                    argplayer.setSaturation(20f);
                    sender.sendMessage(ChatColor.GOLD + argplayer.getName() + " has been feed.");
                    return true;
                } else {
                    sender.sendMessage(ChatColor.RED+"You don't have the permission");
                    return true;
                }
            } else {
                // Send command overview
                sender.sendMessage(ChatColor.YELLOW + " Plugin help:");
                sender.sendMessage("/feed" + ChatColor.GRAY + " - " + ChatColor.GOLD + "Feed yourself.");
                sender.sendMessage("/feed <player>" + ChatColor.GRAY + " - " + ChatColor.GOLD + "Feed other player.");
                return true;
            }

        }
        return true;
    }
}