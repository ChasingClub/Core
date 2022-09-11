package core.itdragclick.Command;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class fakeplugin extends Command {
    public fakeplugin(){
        super("plugins","","pl","bukkit:pl","bukkit:plugins");
    }
    @Override
    public void execute(CommandSender sender, String[] args){
        sender.sendMessage(ChatColor.WHITE+"Plugins (1): "+ChatColor.GREEN+"ChasingClubCore");
    }
}
