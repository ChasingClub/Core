package core.itdragclick.Commands;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static org.bukkit.Bukkit.getServer;
import static core.itdragclick.Core.anti;

public class earape implements CommandExecutor {

    public void particles(Player p) {
        Location loc = p.getLocation();
        for (int i=0; i<247;i++) {
            p.playSound(loc , Sound.UI_TOAST_CHALLENGE_COMPLETE, 1000000000, 0);
        }
    }
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (cmd.getName().equalsIgnoreCase("earape")) {
            // Check for arguments
            if (args.length == 0) {
                if (!(sender instanceof Player)) {
                    sender.sendMessage("Player Not Found");
                    return true;
                }else{
                    if (!(sender.hasPermission("rank.admin"))) {
                        sender.sendMessage(org.bukkit.ChatColor.RED+"You Don't have permission to do that!");
                        return true;
                    }
                    if (sender instanceof Player) {
                        sender.sendMessage("/earape <player>" + org.bukkit.ChatColor.GRAY + " - " + org.bukkit.ChatColor.GOLD + "Earape player you want.");
                        return true;
                    }
                }
            } else if (args.length == 1) {
                if (sender.hasPermission("rank.admin")) {
                    Player argplayer = getServer().getPlayer(args[0]);
                    if (argplayer == null) {
                        sender.sendMessage("Player " + org.bukkit.ChatColor.GRAY + args[0] + org.bukkit.ChatColor.RESET + " could not be found");
                        return true;
                    }else if (anti.get(argplayer.getUniqueId().toString()) != null) {
                        sender.sendMessage(org.bukkit.ChatColor.RED+"You can't earape that player.");
                        return true;
                    }
                    // Send command overview
                    particles(argplayer);
                    sender.sendMessage(org.bukkit.ChatColor.GOLD + argplayer.getName() + " has been earaped.");
                    return true;
                } else {
                    sender.sendMessage(org.bukkit.ChatColor.RED+"You Don't have permission to do that!");
                    return true;
                }
            } else {
                // Send command overview
                sender.sendMessage("/earape <player>" + org.bukkit.ChatColor.GRAY + " - " + org.bukkit.ChatColor.GOLD + "Earape player you want.");
                return true;
            }

        }
        return true;
    }
}
