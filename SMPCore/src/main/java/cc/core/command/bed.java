package cc.core.command;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

import static cc.core.Core.plugin;

public class bed implements CommandExecutor {

    private final HashMap<UUID, Long> cooldown;
    private int cmdCooldown = plugin.getConfig().getInt("command-cooldown");

    public bed() {
        this.cooldown = new HashMap<>();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        // Check if sender is a player
        if (sender instanceof Player){
            Player player = (Player) sender;

                // check if player has a bed spawn location
                if (player.getPlayer().getBedSpawnLocation() != null) {
                    Location bedSpawn = player.getPlayer().getBedSpawnLocation();
                    player.teleport(bedSpawn);
                } else {
                    // if not
                    player.sendMessage(ChatColor.RED + "You don't have a bed spawn location!");
                }

        } else {
            sender.sendMessage(ChatColor.RED + "This Command is only for a player");
        }

        return true;
    }
}
