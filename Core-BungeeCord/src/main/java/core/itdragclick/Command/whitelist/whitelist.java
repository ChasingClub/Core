package core.itdragclick.Command.whitelist;

import core.itdragclick.Core;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static core.itdragclick.Command.guild.SubCommand.ShowHelp.showhelp;
import static core.itdragclick.Command.whitelist.SubCommand.add.addplayerwl;
import static core.itdragclick.Command.whitelist.SubCommand.helps.showhelp2;
import static core.itdragclick.Command.whitelist.SubCommand.remove.removeplayerwl;
import static core.itdragclick.Core.PLname;

public class whitelist extends Command implements TabExecutor {
    public whitelist(){
        super("whitelist", "whitelist.admin", "wl");
    }
    @Override
    public void execute(CommandSender sender, String[] args) {
            // PLAYER
            if (args.length == 0){
                showhelp2(sender);
            }else if (args.length == 1){
                if (args[0].equalsIgnoreCase("help")){
                    showhelp2(sender);
                }else if (args[0].equalsIgnoreCase("on")) {
                    if(Boolean.FALSE.equals(Core.whitelist.get("Whitelist"))){
                        Core.whitelist.put("Whitelist", true);
                        sender.sendMessage(PLname+"Whitelist has been "+ChatColor.GREEN+"ENABLED");
                    }else{
                        sender.sendMessage(PLname+ChatColor.RED+"Whitelist is already "+ChatColor.GREEN+"ENABLED");
                    }
                }else if (args[0].equalsIgnoreCase("off")) {
                    if(Boolean.TRUE.equals(Core.whitelist.get("Whitelist"))){
                        Core.whitelist.put("Whitelist", false);
                        sender.sendMessage(PLname+"Whitelist has been "+ChatColor.RED+"DISABLED");
                    }else{
                        sender.sendMessage(PLname+ChatColor.RED+"Whitelist is already "+ChatColor.RED+"DISABLED");
                    }
                }else if (args[0].equalsIgnoreCase("add")) {
                    sender.sendMessage(PLname+ChatColor.RED+"/whitelist add <name>");
                }else if (args[0].equalsIgnoreCase("remove")) {
                    sender.sendMessage(PLname+ChatColor.RED+"/whitelist remove <name>");
                }else{
                    sender.sendMessage(ChatColor.RED+"Invalid usage, you can use "+ChatColor.AQUA+"/whitelist help"+ChatColor.RED+" for list of commands.");
                }
            }else if (args.length == 2){
                 if (args[0].equalsIgnoreCase("add")) {
                    addplayerwl(args[1], sender);
                }else if (args[0].equalsIgnoreCase("remove")) {
                     removeplayerwl(args[1], sender);
                }else{
                    sender.sendMessage(ChatColor.RED+"Invalid usage, you can use "+ChatColor.AQUA+"/whitelist help"+ChatColor.RED+" for list of commands.");
                }
            }else{
                sender.sendMessage(ChatColor.RED+"Invalid usage, you can use "+ChatColor.AQUA+"/whitelist help"+ChatColor.RED+" for list of commands.");
            }
    }
    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        if (args.length == 1) {
            List<String> argruments = new ArrayList<>();
            argruments.add("on");
            argruments.add("off");
            argruments.add("add");
            argruments.add("remove");
            argruments.add("list");
            argruments.add("help");

            return argruments;
        }
        return Collections.emptyList();
    }
}
