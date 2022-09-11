package core.itdragclick.event;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ServerKickEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import static core.itdragclick.Core.FlagKicks;

public class onKick implements Listener {
    @EventHandler
    public void onPlayerKick(ServerKickEvent e){
        ProxiedPlayer p = e.getPlayer();
        String reason = e.getKickReason();
        String ip = p.getAddress().getAddress().getHostAddress();
        FlagKicks.merge(ip, 1, Integer::sum);
//        if (reason.toLowerCase().contains("lol")){
//        }
    }
}
