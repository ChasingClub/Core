package cc.core.command;

import cc.core.Core;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class spawn implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player){
            Player player = (Player) sender;

            Location spawn = new Location(player.getWorld(), 4016, 72, -3136);

            player.teleport(spawn);

        } else {
            sender.sendMessage(ChatColor.RED + "This Command is only for a player");
        }

        return true;
    }
}