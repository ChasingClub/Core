package cc.commands.core;

import cc.Core;

import org.bukkit.ChatColor;
import org.bukkit.command.*;

import java.util.ArrayList;
import java.util.List;

import static cc.Core.Plname;
import static cc.commands.core.Utils.*;

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
                    sender.sendMessage(Plname+ ChatColor.GREEN+"Config have been reloaded!");
                    return true;
                }else if(args[0].equalsIgnoreCase("reload")){
                    plugin.onDisable();
                    plugin.onEnable();
                    sender.sendMessage(Plname+ ChatColor.GREEN+"Plugin have been reloaded!");
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
            argruments.add("reloadconfig");
            argruments.add("help");

            return argruments;
        }
        return null;
    }
}
