package core.itdragclick.Commands;


import core.itdragclick.Core;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static org.bukkit.Bukkit.getServer;


public class spawn implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (args.length == 0) {
                World SessionWorld = Bukkit.getServer().getWorld("Lobby");
                Location SessionWorldSpawn = new Location(SessionWorld, 100.5, 101, 100.5, 180f, 0f);
                p.teleport(SessionWorldSpawn);
                p.sendMessage(Core.PLname + "You have been teleported to spawn.");
                return true;
            }else if (args.length == 1) {
                if (!(p.hasPermission("rank.admin"))){
                    World SessionWorld = Bukkit.getServer().getWorld("Lobby");
                    Location SessionWorldSpawn = new Location(SessionWorld, 100.5, 101, 100.5, 180f, 0f);
                    p.teleport(SessionWorldSpawn);
                    p.sendMessage(Core.PLname + "You have been teleported to spawn.");
                }else{
                    // HAVE RANK
                    Player target = getServer().getPlayer(args[0]);
                    if (target == null){
                        p.sendMessage(Core.PLname+ChatColor.RED+"That player is offline.");
                        return true;
                    }
                    World SessionWorld = Bukkit.getServer().getWorld("Lobby");
                    Location SessionWorldSpawn = new Location(SessionWorld, 100.5, 101, 100.5, 180f, 0f);
                    target.teleport(SessionWorldSpawn);
                    target.sendMessage(Core.PLname + "You have been teleported to spawn.");
                    p.sendMessage(Core.PLname+"Sent "+target.getName()+" to spawn.");
                    return true;
                }
            }else{
                if (!(p.hasPermission("rank.admin"))){
                    World SessionWorld = Bukkit.getServer().getWorld("Lobby");
                    Location SessionWorldSpawn = new Location(SessionWorld, 100.5, 101, 100.5, 180f, 0f);
                    p.teleport(SessionWorldSpawn);
                    p.sendMessage(Core.PLname + "You have been teleported to spawn.");
                }else{
                    sender.sendMessage("/spawn <player>" + ChatColor.GRAY + " - " + ChatColor.YELLOW + "Spawn player you want.");
                    return true;
                }
            }
        } else {
            Core.msgconsole(Core.PLname+"Console Can't use that command");
        }
        return true;
    }
}
