package core.itdragclick.Command.maintenance;

import core.itdragclick.Command.whitelist.SubCommand.helps;
import core.itdragclick.Core;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static core.itdragclick.Command.maintenance.SubCommand.helps.showhelp2;
import static core.itdragclick.Command.maintenance.SubCommand.kickallnoperm.kick;
import static core.itdragclick.Core.PLname;

public class maintenance extends Command implements TabExecutor {
    public maintenance(){
        super("maintenance","maintenance.admin","m");
    }
    @Override
    public void execute(CommandSender sender, String[] args){
        if (args.length == 0){
            showhelp2(sender);
        }else if (args.length == 1){
            if (args[0].equalsIgnoreCase("help")){
                showhelp2(sender);
            }else if (args[0].equalsIgnoreCase("on")) {
                if(Boolean.FALSE.equals(Core.maintenance.get("Maintenance"))){
                    Core.maintenance.put("Maintenance", true);
                    kick(sender);
                    sender.sendMessage(PLname+"Maintenance mode has been "+ ChatColor.GREEN+"ENABLED");
                }else{
                    sender.sendMessage(PLname+ChatColor.RED+"Maintenance mode is already "+ChatColor.GREEN+"ENABLED");
                }
            }else if (args[0].equalsIgnoreCase("off")) {
                if(Boolean.TRUE.equals(Core.maintenance.get("Maintenance"))){
                    Core.maintenance.put("Maintenance", false);
                    sender.sendMessage(PLname+"Maintenance mode has been "+ChatColor.RED+"DISABLED");
                }else{
                    sender.sendMessage(PLname+ChatColor.RED+"Maintenance mode is already "+ChatColor.RED+"DISABLED");
                }
            }else{
                sender.sendMessage(ChatColor.RED+"Invalid usage, you can use "+ChatColor.AQUA+"/maintenance help"+ChatColor.RED+" for list of commands.");
            }
        }else{
            sender.sendMessage(ChatColor.RED+"Invalid usage, you can use "+ChatColor.AQUA+"/maintenance help"+ChatColor.RED+" for list of commands.");
        }
    }
    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        if (args.length == 1) {
            List<String> argruments = new ArrayList<>();
            argruments.add("on");
            argruments.add("off");
            argruments.add("help");

            return argruments;
        }
        return Collections.emptyList();
    }
}
