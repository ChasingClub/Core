package chasingclub.server.core.command;

import chasingclub.server.core.Utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static chasingclub.server.core.Utils.Utils.PluginName;

public class enderchest implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if(sender instanceof Player){
            Player p = (Player) sender;
            if(p.hasPermission("rank.staff")){
                if(args.length == 0){
                    p.openInventory(p.getEnderChest());
                }else if(args.length == 1){
                    Player target = Bukkit.getServer().getPlayer(args[0]);
                    if(target == null){
                        sender.sendMessage(PluginName+ChatColor.RED+"That player is offline!");
                        return true;
                    }
                    p.openInventory(target.getEnderChest());
                }else{
                    sender.sendMessage(ChatColor.RED+"Invalid usage, you can use :");
                    sender.sendMessage(ChatColor.AQUA+"/enderchest"+ ChatColor.GRAY+" - "+ ChatColor.DARK_AQUA+"Open your ender chest");
                    sender.sendMessage(ChatColor.AQUA+"/enderchest <player>"+ ChatColor.GRAY+" - "+ ChatColor.DARK_AQUA+"Open player ender chest");
                }
            }else{
                sender.sendMessage(Utils.noperm);
            }
        }else{
            sender.sendMessage(ChatColor.RED+"Console can't use this command!");
        }
        return true;
    }
}
