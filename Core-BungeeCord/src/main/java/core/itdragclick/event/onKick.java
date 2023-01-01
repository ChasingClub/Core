package core.itdragclick.event;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ServerKickEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import static core.itdragclick.Core.PLname;

public class onKick implements Listener {
    @EventHandler
    public void onPlayerKick(ServerKickEvent e){
        ProxiedPlayer p = e.getPlayer();
        ServerInfo from = e.getKickedFrom();
        if(from.getName().equals("checkpack") || from.getName().equals("lobby")){return;}
        e.setCancelServer(ProxyServer.getInstance().getServerInfo("lobby"));
        String reason = e.getKickReason();
        p.sendMessage(PLname+"You got kicked from "+ ChatColor.YELLOW +from.getName()+ChatColor.GRAY+".");
        p.sendMessage(PLname+ChatColor.AQUA+"Reason"+ChatColor.GRAY+" » "+ ChatColor.RED +reason);
        e.setCancelled(true);
    }
}
