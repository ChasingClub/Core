package core.server.core.command;

import core.server.core.Core;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

import static core.server.core.Core.Plname;
import static core.server.core.Utils.Utils.noperm;

public class setspawn implements CommandExecutor {
    public Core plugin;

    public setspawn(Core plugin){
        this.plugin = plugin;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender instanceof Player){
            Player p = (Player) sender;
            if(!p.hasPermission("rank.admin")){
                p.sendMessage(noperm);
                return true;
            }
            int playerX = p.getLocation().getBlockX();
            int playerY = p.getLocation().getBlockY() - 1;
            int playerZ = p.getLocation().getBlockZ();
            Location blockbelow = new Location(p.getWorld(), playerX, playerY, playerZ);
            if(blockbelow.getBlock().getType() == Material.AIR ||
                    blockbelow.getBlock().getType() == Material.LAVA ||
                    blockbelow.getBlock().getType() == Material.WATER ||
                    blockbelow.getBlock().getType() == Material.TORCH ||
                    blockbelow.getBlock().getType() == Material.LANTERN
            ){
                p.sendMessage(Plname+ChatColor.RED+"You can't set spawn in the air!");
                return true;
            }

            plugin.getConfig().set("spawn.world", p.getWorld().getName());
            plugin.getConfig().set("spawn.x", p.getLocation().getBlockX());
            plugin.getConfig().set("spawn.y", p.getLocation().getBlockY());
            plugin.getConfig().set("spawn.z", p.getLocation().getBlockZ());

            p.sendMessage(Plname+ChatColor.GREEN+ChatColor.BOLD+"Set Spawn Completed!");
            plugin.saveConfig();
        }else{
            sender.sendMessage(ChatColor.RED+"This command can be use with only player!");
        }
        return true;
    }

}
