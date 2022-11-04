package core.itdragclick.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Locale;
import java.util.StringJoiner;

import static core.itdragclick.Core.PLname;

public class sudo implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (cmd.getName().equalsIgnoreCase("sudo")) {
            // Check perms
            if (!sender.hasPermission("rank.owner")){
                sender.sendMessage(ChatColor.RED+"You don't have permission to do that!");
                return true;
            }
            // Check for arguments
            if (args.length == 0) {
                sender.sendMessage(PLname+ ChatColor.RED+"/sudo <player> <message>");
                return true;
            } else if (args.length == 1) {
                Player target = Bukkit.getServer().getPlayer(args[0]);
                if (target == null){
                    sender.sendMessage(ChatColor.RED+"That player is offline");
                    return true;
                }
                sender.sendMessage(PLname+ ChatColor.RED+"/sudo "+target.getName()+" <message>");
                return true;
            } else {
                Player target = Bukkit.getServer().getPlayer(args[0]);
                String message = args[1];
                if (target == null){
                    sender.sendMessage(ChatColor.RED+"That player is offline");
                    return true;
                }
                if (args[1].toLowerCase(Locale.ENGLISH).startsWith("c:")){
                    message = message.replaceFirst("c:","");
                    message = message.replaceFirst("/","");
                    message = message.replaceFirst("C:","");
                    StringJoiner msg = new StringJoiner(" ");
                    for(int i = 2; i < args.length; i++)
                        msg.add(args[i]);
                    target.chat(message+" "+msg);
                    return true;
                }else if (args[1].toLowerCase(Locale.ENGLISH).startsWith("cmd:")){
                    message = message.replaceFirst("cmd:","/");
                    message = message.replaceFirst("CMD:","/");
                    target.chat(message);
                    return true;
                }else{
                    sender.sendMessage(ChatColor.RED+"Something went wrong, please try again.");
                    return true;
                }
            }
        }
        return true;
    }
}
