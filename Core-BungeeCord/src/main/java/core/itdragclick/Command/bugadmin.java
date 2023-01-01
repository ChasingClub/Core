package core.itdragclick.Command;

import core.itdragclick.Utils.OfflineplayerAPI;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

import java.io.IOException;
import java.sql.SQLException;

import static core.itdragclick.Core.PLname;
import static core.itdragclick.Utils.Database.GetBugsList;
import static core.itdragclick.Utils.Utils.noperm;

public class bugadmin extends Command {
    public bugadmin(){
        super("bugadmin","","adminbug");
    }
    @Override
    public void execute(CommandSender sender, String[] args){
        if(sender.hasPermission("rank.owner") ||
                sender.hasPermission("rank.admin") ||
                sender.hasPermission("rank.mod")
        ){
            if(args.length == 0){
                // Helps
                sender.sendMessage(PLname + "/bugadmin help - Show this message");
                sender.sendMessage(PLname + "/bugadmin list - Show list report of bug");
                sender.sendMessage(PLname + "/bugadmin list <player> - Show list report of bug from the player");
            }else if(args.length == 1){
                if(args[0].equalsIgnoreCase("list")) {
                    try {
                        GetBugsList(null, sender);
                    } catch (SQLException e) {
                        sender.sendMessage(PLname+ChatColor.RED+"Error: Something went wrong, please try again later.");
                    }
                }else if(args[0].equalsIgnoreCase("help")) {
                    sender.sendMessage(PLname + "/bugadmin help - Show this message");
                    sender.sendMessage(PLname + "/bugadmin list - Show list report of bug");
                    sender.sendMessage(PLname + "/bugadmin list <player> - Show list report of bug from the player");
                }else{
                    sender.sendMessage(ChatColor.RED+"Invalid usage, you can use "+ChatColor.AQUA+"/bugadmin help"+ChatColor.RED+" for list of commands.");
                }
            }else if(args.length == 2){
                if(args[0].equalsIgnoreCase("list")) {
                    String uuid;
                    try {
                        uuid = OfflineplayerAPI.getUUID(args[1]);
                    } catch (IOException e) {
                        e.printStackTrace();
                        sender.sendMessage(PLname+ ChatColor.RED +"Could not initialize player data.");
                        return;
                    }
                    if (uuid == null){
                        sender.sendMessage(PLname+ChatColor.RED+"Could not found that player on mojang API");
                        return;
                    }
                    try {
                        GetBugsList(uuid, sender);
                    } catch (SQLException e) {
                        sender.sendMessage(PLname+ChatColor.RED+"Error: Something went wrong, please try again later.");
                    }
                }else{
                    sender.sendMessage(ChatColor.RED+"Invalid usage, you can use "+ChatColor.AQUA+"/bugadmin help"+ChatColor.RED+" for list of commands.");
                }
            }else{
                sender.sendMessage(ChatColor.RED+"Invalid usage, you can use "+ChatColor.AQUA+"/bugadmin help"+ChatColor.RED+" for list of commands.");
            }
        }else{
            sender.sendMessage(noperm);
        }
    }
}
