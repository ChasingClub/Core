package cc.core.command;

import cc.core.Utilities.randomTeleportUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

import static cc.core.Core.plugin;

public class randomTeleport implements CommandExecutor {

    private final HashMap<UUID, Long> cooldown;
    private int cmdCooldown = plugin.getConfig().getInt("command-cooldown");

    public randomTeleport() {
        this.cooldown = new HashMap<>();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player){
            Player player = (Player) sender;

                if (args.length == 0){

                    //Safe Location that has been generated
                    Location randomLocation = randomTeleportUtils.findSafeLocation(player);

                    //Teleport player
                    player.teleport(randomLocation);

                    player.sendMessage(ChatColor.RED + "You have been randomly teleported!");
                    player.sendMessage(ChatColor.AQUA + "New Coordinates: " + ChatColor.LIGHT_PURPLE + randomLocation.getX() + " " + randomLocation.getY() + " " + randomLocation.getZ());

                }else if(args.length == 1){ //Specify a player to teleport
                    if (player.hasPermission("rtp.others")){
                        //Get the player to teleport
                        Player target = Bukkit.getPlayer(args[0]);

                        //Safe Location that has been generated
                        Location randomLocation = randomTeleportUtils.findSafeLocation(target);

                        //Teleport player
                        target.teleport(randomLocation);

                        target.sendMessage(ChatColor.GREEN + player.getName() + ChatColor.GOLD + " just Random Teleported you!");
                        target.sendMessage(ChatColor.AQUA + "New Coordinates: " + ChatColor.LIGHT_PURPLE + randomLocation.getX() + " " + randomLocation.getY() + " " + randomLocation.getZ());

                        player.sendMessage(ChatColor.RED + "Player successfully teleported to: " + ChatColor.LIGHT_PURPLE + randomLocation.getX() + " " + randomLocation.getY() + " " + randomLocation.getZ());
                    }
                }

        }else {
            System.out.println("You need to be a player to execute this command.");
        }

        return true;
    }

}