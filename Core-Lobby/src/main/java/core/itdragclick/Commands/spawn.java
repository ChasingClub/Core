package core.itdragclick.Commands;


import core.itdragclick.Core;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static org.bukkit.Bukkit.getServer;


public class spawn implements CommandExecutor {
    public Core plugin;

    public spawn(Core plugin){
        this.plugin = plugin;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (args.length == 0) {
                p.teleport(plugin.spawnloc);
                p.sendMessage(Core.PLname + "You have been teleported to spawn.");
                return true;
            }else if (args.length == 1) {
                if (!(p.hasPermission("rank.admin"))){
                    p.teleport(plugin.spawnloc);
                    p.sendMessage(Core.PLname + "You have been teleported to spawn.");
                }else{
                    // HAVE RANK
                    Player target = getServer().getPlayer(args[0]);
                    if (target == null){
                        p.sendMessage(Core.PLname+ChatColor.RED+"That player is offline.");
                        return true;
                    }
                    target.teleport(plugin.spawnloc);
                    target.sendMessage(Core.PLname + "You have been teleported to spawn.");
                    p.sendMessage(Core.PLname+"Sent "+target.getName()+" to spawn.");
                    return true;
                }
            }else{
                if (!(p.hasPermission("rank.admin"))){
                    p.teleport(plugin.spawnloc);
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
