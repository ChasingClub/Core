package chasingclub.server.core.command;


import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import static org.bukkit.Bukkit.getServer;

public class gma implements CommandExecutor, Listener {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (cmd.getName().equalsIgnoreCase("gma")) {
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
                        p.setGameMode(GameMode.ADVENTURE);
                        p.sendMessage(ChatColor.GRAY +"Your gamemode has been set to "+ChatColor.GREEN+"ADVENTURE");
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
                    argplayer.setGameMode(GameMode.ADVENTURE);
                    sender.sendMessage(ChatColor.GRAY +argplayer.getName()+" gamemode has been set to "+ChatColor.GREEN+"ADVENTURE");
                    argplayer.sendMessage(ChatColor.GRAY +"Your gamemode has been set to "+ChatColor.GREEN+"ADVENTURE"+ChatColor.GRAY+" By "+ChatColor.YELLOW+sender.getName());
                    return true;
                } else {
                    sender.sendMessage(ChatColor.RED+"You don't have the permission");
                    return true;
                }
            } else {
                // Send command overview
                sender.sendMessage(ChatColor.YELLOW + " Plugin help:");
                sender.sendMessage("/gma" + ChatColor.GRAY + " - " + ChatColor.GOLD + "Set gamemode adventure for yourself.");
                sender.sendMessage("/gma <player>" + ChatColor.GRAY + " - " + ChatColor.GOLD + "Set gamemode adventure for other player.");
                return true;
            }

        }
        return true;
    }
}