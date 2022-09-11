package core.itdragclick.Command.maintenance.SubCommand;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import static core.itdragclick.Core.PL;

public class kickallnoperm {
    public static void kick(CommandSender sender){
        String maintenancekickmsg2 = PL+"\n\n"+ ChatColor.GRAY+"You have been kicked\n"+ChatColor.RED+"The server is in maintenance mode.\n\n"+ChatColor.GRAY+"Contact Staff in discord\n"+ChatColor.BLUE+ChatColor.BOLD+"Discord "+ChatColor.DARK_GRAY+"» "+ChatColor.AQUA+ChatColor.UNDERLINE+"https://dsc.gg/praf-war";
        for (ProxiedPlayer p : ProxyServer.getInstance().getPlayers()){
            if (!p.hasPermission("maintenance.bypass")) {
                p.disconnect(maintenancekickmsg2);
                sender.sendMessage(ChatColor.YELLOW+p.getName()+" "+ChatColor.RED+"has been kicked.");
            }
        }
    }
}
