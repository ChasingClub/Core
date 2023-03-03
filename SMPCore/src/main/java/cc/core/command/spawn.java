package cc.core.command;

import cc.core.Core;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

import static cc.core.Core.plugin;

public class spawn implements CommandExecutor {

    private final HashMap<UUID, Long> cooldown;
    private int cmdCooldown = plugin.getConfig().getInt("command-cooldown");

    public spawn() {
        this.cooldown = new HashMap<>();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        // Check if sender is a player
        if (sender instanceof Player){
            Player player;
            player = (Player) sender;

                // Get the world spawn location
                if (player.getWorld().equals(Objects.requireNonNull(Core.plugin.getServer().getWorld("world")))) {
                    Location worldSpawn = player.getWorld().getSpawnLocation();

                    // Teleport player to world spawn
                    player.teleport(worldSpawn);
                } else {
                    Location worldSpawn = Objects.requireNonNull(player.getServer().getWorld("world")).getSpawnLocation();
                    worldSpawn.setWorld(Objects.requireNonNull(Core.plugin.getServer().getWorld("world")));

                    // Teleport player to nether spawn
                    player.teleport(worldSpawn);
                }


        } else {
            sender.sendMessage(ChatColor.RED + "This Command is only for a player");
        }

        return true;
    }
}