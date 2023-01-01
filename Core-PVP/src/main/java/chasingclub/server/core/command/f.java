package chasingclub.server.core.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class f implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, Command cmd, @NotNull String commandLabel, String[] args) {
        if (cmd.getName().equalsIgnoreCase("f")) {
            // Check perms
            if (!sender.hasPermission("rank.owner")){
                sender.sendMessage(ChatColor.RED+"You don't have permission to do that!");
                return true;
            }
            // Check for arguments
            if (args.length == 0) {
                for(int i = 0; i < 3; i++) {
                    for (Player All : Bukkit.getServer().getOnlinePlayers()) {
                        All.chat("f");
                    }
                }
            } else {
                sender.sendMessage(ChatColor.RED+"Something went wrong, please try again.");
                return true;
            }
        }
        return true;
    }
}
