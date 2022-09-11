package core.itdragclick.Commands.Core;

import core.itdragclick.Core;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static core.itdragclick.Core.PLname;

public class core implements CommandExecutor {
    private Core Plugin;
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (cmd.getName().equalsIgnoreCase("core")) {
            // Check for arguments
            if (args.length == 0) {
                    if (!(sender.hasPermission("rank.admin"))) {
                        sender.sendMessage(ChatColor.RED+"You don't have permission to do that!");
                        return true;
                    }
                    sender.sendMessage(PLname+"/core help - for helps");
                    return true;
            } else if (args.length == 1) {
                if (sender.hasPermission("rank.admin")) {
                    if(args[0].equalsIgnoreCase("reloadconfig")){
//                        Plugin.reloadbyplayer();
                        sender.sendMessage(PLname+ ChatColor.GREEN + "Config has been reloaded!");
                        return true;
                    }else if(args[0].equalsIgnoreCase("npc")){
                        Player p = (Player) sender;
                        return true;
                    } else {
                        sender.sendMessage(PLname+"/core help - for helps");
                        return true;
                    }
                } else {
                    sender.sendMessage(ChatColor.RED+"You don't have permission to do that!");
                    return true;
                }
            } else {
                sender.sendMessage(PLname+"/core help - for helps");
                return true;
            }

        }
        return true;
    }
}
