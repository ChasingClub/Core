package core.itdragclick.event;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.Favicon;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

import static core.itdragclick.Core.maintenance;

public class onPing implements Listener {
    public static Favicon icon;
    public static Favicon icon2;

    public static void onIcon(){
        try {
            icon = Favicon.create(ImageIO.read(new File("icon.png")));
            icon2 = Favicon.create(ImageIO.read(new File("icon2.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @EventHandler
    public void onPinging(ProxyPingEvent e){
        ServerPing serverPing = e.getResponse();
        if (Boolean.TRUE.equals(maintenance.get("Maintenance"))) {
            serverPing.setFavicon(icon);
            serverPing.setDescription(ChatColor.RED+"Server is in maintenance mode.\n"+ChatColor.GRAY+"Discord"+ChatColor.DARK_GRAY+" » "+ChatColor.BLUE+ChatColor.UNDERLINE+"dsc.gg/ChasingClub");
            serverPing.setVersion(new ServerPing.Protocol("maintenance", 999));
            serverPing.setPlayers(new ServerPing.Players(0,0,null));
            e.setResponse(serverPing);
        }else{
            serverPing.setFavicon(icon2);
        }
    }
}
