package chasingclub.server.core.command;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import vanish.itdragclick.api.vanish.VanishAPI;

import static chasingclub.server.core.Utils.Utils.PluginName;
import static chasingclub.server.core.Utils.Utils.error;

public class ping implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
            if(args.length == 0) {
                if(sender instanceof Player){
                    if (PlaceholderAPI.isRegistered("Player")) {
                        sender.sendMessage(PluginName + "Your Ping: " + ChatColor.GREEN + PlaceholderAPI.setPlaceholders((Player) sender, "%player_ping%"));
                    } else {
                        sender.sendMessage(error);
                    }
                }else{
                    sender.sendMessage(PluginName+"Your Ping: "+ChatColor.GREEN+"0");
                }
            }else if(args.length == 1){
                Player target = Bukkit.getServer().getPlayer(args[0]);
                if (target == null) {
                    sender.sendMessage(PluginName + ChatColor.RED + "That player is offline!");
                    return true;
                }
                if (VanishAPI.isInvisible(target) && !target.getName().equals(sender.getName()) && !sender.hasPermission("rank.staff")) {
                    sender.sendMessage(PluginName + ChatColor.RED + "That player is offline!");
                    return true;
                }
                if (PlaceholderAPI.isRegistered("Player")) {
                    sender.sendMessage(PluginName + ChatColor.YELLOW+target.getName()+ChatColor.GRAY+"'s Ping: " + ChatColor.GREEN + PlaceholderAPI.setPlaceholders(target, "%player_ping_"+target.getName()+"%"));
                } else {
                    sender.sendMessage(error);
                }
            }else{
                sender.sendMessage(PluginName+"/ping - Show your own Ping");
                sender.sendMessage(PluginName+"/ping <player> - Show others Ping");
            }
        return true;
    }
}
