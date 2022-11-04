package chasingclub.server.core.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.util.Vector;

import static org.bukkit.Bukkit.getServer;

public class fling implements CommandExecutor, Listener {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (cmd.getName().equalsIgnoreCase("fling")) {
            // Check for arguments
            if (args.length == 0) {
                if (!(sender instanceof Player)) {
                    sender.sendMessage("Can use with only player.");
                    return true;
                }else {
                    Player p = (Player) sender;
                    // Send command overview
                    if (sender.hasPermission("rank.mod")) {
                        // Define player object
                        p.setVelocity(new Vector(0, 200, 0));
                        p.sendMessage(ChatColor.GOLD + p.getName() + " has been FLUNG.");
                        return true;
                    } else {
                        sender.sendMessage(ChatColor.RED+"You don't have the permission");
                        return true;
                    }
                }
            } else if (args.length == 1) {
                if (sender.hasPermission("rank.mod")) {
                    Player argplayer = getServer().getPlayer(args[0]);
                    if (argplayer == null) {
                        sender.sendMessage("Player " + ChatColor.GRAY + args[0] + ChatColor.RESET + " could not be found");
                        return true;
                    }
                    // Send command overview
                    argplayer.setVelocity(new Vector(0, 200, 0));
                    sender.sendMessage(ChatColor.GOLD + argplayer.getName() + " has been FLUNG.");
                    return true;
                } else {
                    sender.sendMessage(ChatColor.RED+"You don't have the permission");
                    return true;
                }
            } else {
                // Send command overview
                sender.sendMessage(ChatColor.YELLOW + " Plugin help:");
                sender.sendMessage("/fling" + ChatColor.GRAY + " - " + ChatColor.GOLD + "Fling yourself.");
                sender.sendMessage("/fling <player>" + ChatColor.GRAY + " - " + ChatColor.GOLD + "Fling other player.");
                return true;
            }

        }
        return true;
    }
}
