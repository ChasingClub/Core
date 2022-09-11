package core.itdragclick.Commands;


import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import static org.bukkit.Bukkit.getServer;

public class gmc implements CommandExecutor, Listener {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (cmd.getName().equalsIgnoreCase("gmc")) {
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
                        p.setGameMode(GameMode.CREATIVE);
                        p.sendMessage(ChatColor.GRAY +"Your gamemode has been set to "+ChatColor.GREEN+"CREATIVE");
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
                    argplayer.setGameMode(GameMode.CREATIVE);
                    sender.sendMessage(ChatColor.GRAY +argplayer.getName()+" gamemode has been set to "+ChatColor.GREEN+"CREATIVE");
                    argplayer.sendMessage(ChatColor.GRAY +"Your gamemode has been set to "+ChatColor.GREEN+"CREATIVE"+ChatColor.GRAY+" By "+ChatColor.YELLOW+sender.getName());
                    return true;
                } else {
                    sender.sendMessage(ChatColor.RED+"You don't have the permission");
                    return true;
                }
            } else {
                // Send command overview
                sender.sendMessage(ChatColor.YELLOW + " Plugin help:");
                sender.sendMessage("/gmc" + ChatColor.GRAY + " - " + ChatColor.GOLD + "Set gamemode creative for yourself.");
                sender.sendMessage("/gmc <player>" + ChatColor.GRAY + " - " + ChatColor.GOLD + "Set gamemode creative for other player.");
                return true;
            }

        }
        return true;
    }
}