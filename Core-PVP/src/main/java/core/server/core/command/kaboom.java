package core.server.core.command;

import core.server.core.Core;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import static org.bukkit.Bukkit.getServer;


public class kaboom implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (cmd.getName().equalsIgnoreCase("kaboom")) {
            // Check for arguments
            if (args.length == 0) {
                if (!(sender instanceof Player)) {
                    sender.sendMessage("Can't use with console");
                    return true;
                }else{
                    if (!(sender.hasPermission("rank.mod"))) {
                        sender.sendMessage(ChatColor.RED+"You Don't have permission to do that!");
                        return true;
                    }
                    if (sender instanceof Player) {
                        // Run All KaBoom!!!
                        for (Player ap : getServer().getOnlinePlayers()) {
                            ap.getWorld().strikeLightningEffect(ap.getLocation());
                            ap.setVelocity(new Vector(0, 200, 0));
                            ap.sendMessage(Core.Plname+ChatColor.WHITE+ChatColor.BOLD+"KABOOM!!!");
                            ap.sendTitle("§0§kii§r §f§lKABOOM§r §0§kii§r", "§7You got §f§lKABOOM §7by §c"+sender.getName(), 1, 80, 1);
                        }
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
                    argplayer.getWorld().strikeLightningEffect(argplayer.getLocation());
                    argplayer.setVelocity(new Vector(0, 200, 0));
                    argplayer.sendMessage(Core.Plname+ChatColor.WHITE+ChatColor.BOLD+"KABOOM!!!");
                    sender.sendMessage(ChatColor.YELLOW + argplayer.getName() +ChatColor.GRAY+ " has been "+ChatColor.WHITE+ChatColor.BOLD+"KABOOM!!");
                    return true;
                } else {
                    sender.sendMessage(ChatColor.RED+"You Don't have permission to do that!");
                    return true;
                }
            } else {
                // Send command overview
                sender.sendMessage("/kaboom" + ChatColor.GRAY + " - " + ChatColor.YELLOW + "KABOOM ALL PLAYER!");
                sender.sendMessage("/kaboom <player>" + ChatColor.GRAY + " - " + ChatColor.YELLOW + "KABOOM PLAYER YOU WANT!");
                return true;
            }

        }
        return true;
    }
}
