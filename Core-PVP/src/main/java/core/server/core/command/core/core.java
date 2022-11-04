package core.server.core.command.core;

import core.server.core.Core;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

import static core.server.core.Core.Plname;
import static core.server.core.Utils.Utils.noperm;

public class core implements CommandExecutor, TabCompleter {
    public Core plugin;

    public core(Core plugin){
        this.plugin = plugin;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender.hasPermission("rank.admin")){
            if(args.length == 0){
                helps.helpcore(sender);
                return true;
            }else if(args.length == 1){
                if(args[0].equalsIgnoreCase("reload")){
                    plugin.reload();
                    sender.sendMessage(Plname+ ChatColor.GREEN+"Config have been reloaded!");
                    return true;
                }else if(args[0].equalsIgnoreCase("help")){
                    helps.helpcore(sender);
                    return true;
                }else{
                    sender.sendMessage(ChatColor.RED+"Invalid usage, you can use "+ ChatColor.AQUA+"/core help"+ ChatColor.RED+" for list of commands.");
                }
            }else{
                sender.sendMessage(ChatColor.RED+"Invalid usage, you can use "+ ChatColor.AQUA+"/core help"+ ChatColor.RED+" for list of commands.");
            }
        }else{
            sender.sendMessage(noperm);
        }
        return true;
    }
    @Override
    public java.util.List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args) {
        if (args.length == 1) {
            List<String> argruments = new ArrayList<>();
            argruments.add("reload");
            argruments.add("help");

            return argruments;
        }
        return null;
    }
}
