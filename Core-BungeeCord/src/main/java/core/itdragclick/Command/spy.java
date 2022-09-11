package core.itdragclick.Command;

import core.itdragclick.Core;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static core.itdragclick.Core.PLname;
import static core.itdragclick.Core.spylist;

public class spy extends Command implements TabExecutor {
    public spy(){
        super("spy","rank.admin");
    }
    @Override
    public void execute(CommandSender sender, String[] args){
        if (sender instanceof ProxiedPlayer){
            if (args.length == 0){
                if (spylist.contains(sender.getName())){
                    spylist.remove(sender.getName());
                    sender.sendMessage(PLname+"Your spy mode has been "+ChatColor.RED+"DISABLED");
                }else{
                    spylist.add(sender.getName());
                    sender.sendMessage(PLname+"Your spy mode has been "+ChatColor.GREEN+"ENABLED");
                }
            }else if (args.length == 1){
                if (args[0].equalsIgnoreCase("help")){
                    if (spylist.contains(sender.getName())) {
                        sender.sendMessage(ChatColor.GRAY + "=------------[ " + ChatColor.DARK_AQUA + ChatColor.BOLD + "Spy" + ChatColor.RESET + ChatColor.GRAY + " ]------------=");
                        sender.sendMessage(ChatColor.YELLOW + "/spy enable" + ChatColor.GRAY + " - " + ChatColor.GOLD + "Turn spy mode on");
                        sender.sendMessage(ChatColor.YELLOW + "/spy disable" + ChatColor.GRAY + " - " + ChatColor.GOLD + "Turn spy mode off");
                        sender.sendMessage(ChatColor.YELLOW + "/spy help" + ChatColor.GRAY + " - " + ChatColor.GOLD + "Show this message");
                        sender.sendMessage(ChatColor.GRAY + "=-------------[ " + ChatColor.GREEN + ChatColor.BOLD + "ON" + ChatColor.GRAY + " ]------------=");
                    }else{
                        sender.sendMessage(ChatColor.GRAY + "=------------[ " + ChatColor.DARK_AQUA + ChatColor.BOLD + "Spy" + ChatColor.RESET + ChatColor.GRAY + " ]------------=");
                        sender.sendMessage(ChatColor.YELLOW + "/spy enable" + ChatColor.GRAY + " - " + ChatColor.GOLD + "Turn spy mode on");
                        sender.sendMessage(ChatColor.YELLOW + "/spy disable" + ChatColor.GRAY + " - " + ChatColor.GOLD + "Turn spy mode off");
                        sender.sendMessage(ChatColor.YELLOW + "/spy help" + ChatColor.GRAY + " - " + ChatColor.GOLD + "Show this message");
                        sender.sendMessage(ChatColor.GRAY + "=------------[ " + ChatColor.RED + ChatColor.BOLD + "OFF" + ChatColor.GRAY + " ]------------=");
                    }
                }else if (args[0].equalsIgnoreCase("disable")){
                    if (!spylist.contains(sender.getName())){
                        sender.sendMessage(PLname+ChatColor.RED+"Your spy mode is already "+ChatColor.RED+"DISABLED");
                        return;
                    }
                    if (spylist.contains(sender.getName())){
                        spylist.remove(sender.getName());
                        sender.sendMessage(PLname+"Your spy mode has been "+ChatColor.RED+"DISABLED");
                    }
                }else if (args[0].equalsIgnoreCase("enable")){
                    if (spylist.contains(sender.getName())){
                        sender.sendMessage(PLname+ChatColor.RED+"Your spy mode is already "+ChatColor.GREEN+"ENABLED");
                        return;
                    }
                    if (!spylist.contains(sender.getName())){
                        spylist.add(sender.getName());
                        sender.sendMessage(PLname+"Your spy mode has been "+ChatColor.GREEN+"ENABLED");
                    }
                }else{
                    sender.sendMessage(ChatColor.RED+"Invalid usage, you can use "+ChatColor.AQUA+"/spy help"+ChatColor.RED+" for list of commands.");
                }
            }else{
                sender.sendMessage(ChatColor.RED+"Invalid usage, you can use "+ChatColor.AQUA+"/spy help"+ChatColor.RED+" for list of commands.");
            }
        }else{
            sender.sendMessage(ChatColor.RED+"You are not a Player!");
        }
    }
    @Override
    public java.util.List<String> onTabComplete(CommandSender sender, String[] args) {
        if (args.length == 1) {
            List<String> argruments = new ArrayList<>();
            argruments.add("enable");
            argruments.add("disable");
            argruments.add("help");

            return argruments;
        }
        return Collections.emptyList();
    }
}
