package chasingclub.server.core.command.core;

import chasingclub.server.core.Core;
import chasingclub.server.core.Utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

import static chasingclub.server.core.Utils.Utils.PluginName;

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
                if(args[0].equalsIgnoreCase("reloadconfig")){
                    plugin.reload();
                    sender.sendMessage(PluginName+ ChatColor.GREEN+"Config have been reloaded!");
                    return true;
                }else if(args[0].equalsIgnoreCase("reload")){
                    plugin.onDisable();
                    plugin.onEnable();
                    sender.sendMessage(PluginName+ ChatColor.GREEN+"Plugin have been reloaded!");
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
            sender.sendMessage(Utils.noperm);
        }
        return true;
    }
    @Override
    public java.util.List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args) {
        if (args.length == 1) {
            List<String> argruments = new ArrayList<>();
            argruments.add("reload");
            argruments.add("reloadconfig");
            argruments.add("help");

            return argruments;
        }
        return null;
    }
}
