package core.itdragclick.Command.guild.SubCommand;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class ShowHelp {
    public static void showhelp(ProxiedPlayer p){
        p.sendMessage(ChatColor.GRAY + "=-----------[ " + ChatColor.YELLOW+ChatColor.BOLD+ "GUILD" +ChatColor.RESET+ChatColor.GRAY+" ]-----------=");
        p.sendMessage(ChatColor.YELLOW+"/guild accept"+ChatColor.GRAY+" - "+ChatColor.GREEN+"Accepts a guild invitation");
        p.sendMessage(ChatColor.YELLOW+"/guild create <name>"+ChatColor.GRAY+" - "+ChatColor.GREEN+"Creates a guild with the specified name");
        p.sendMessage(ChatColor.YELLOW+"/guild disband"+ChatColor.GRAY+" - "+ChatColor.GREEN+"Disbands the guild");
        p.sendMessage(ChatColor.YELLOW+"/guild help"+ChatColor.GRAY+" - "+ChatColor.GREEN+"Show this message");
        p.sendMessage(ChatColor.YELLOW+"/guild invite <player>"+ChatColor.GRAY+" - "+ChatColor.GREEN+"Invites the player to your guild");
        p.sendMessage(ChatColor.YELLOW+"/guild leave"+ChatColor.GRAY+" - "+ChatColor.GREEN+"Leaves your currently guild");
        p.sendMessage(ChatColor.YELLOW+"/guild list"+ChatColor.GRAY+" - "+ChatColor.GREEN+"Lists players in your guild");
        p.sendMessage(ChatColor.GRAY+"=------------------------------=");
    }
}
