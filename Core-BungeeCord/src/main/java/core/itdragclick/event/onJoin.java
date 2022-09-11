package core.itdragclick.event;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import core.itdragclick.Pack.WhitelistAPI;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URL;
import java.sql.SQLException;

import static core.itdragclick.Core.*;
import static core.itdragclick.Database.*;

public class onJoin implements Listener {
    public WhitelistAPI getwhitelist(String uuid) throws SQLException {
        return findwhitelistbyuuid(uuid);
    }
    @EventHandler
    public void onPlayerJoin(ServerConnectEvent e) throws SQLException {
        ProxiedPlayer p = e.getPlayer();
        // CHECK MAINTENANCE
        //////////////////////////////////////////////////////
        if (Boolean.TRUE.equals(maintenance.get("Maintenance"))){
            if (!p.hasPermission("maintenance.bypass")){
                p.disconnect(maintenancekickmsg);
                return;
            }
        }
        //////////////////////////////////////////////////////
        // CHECK WHITELIST
        //////////////////////////////////////////////////////
        if (Boolean.TRUE.equals(whitelist.get("Whitelist"))){
            if (getwhitelist(p.getUUID()) == null){
                p.disconnect(whitelistkickmsg);
                return;
            }
        }
        //////////////////////////////////////////////////////
        String ip = p.getAddress().getAddress().getHostAddress();
        String loc;
        try {
            loc = getCountry(ip);
        } catch (IOException ex) {
            System.out.println(PLname+ ChatColor.RED+"Error IP not found!");
            return;
        }
        try {
            playerdataonfirstjoin(p, loc);
            playerdataonjoin(p, loc);
            checkuser(p);
        } catch (SQLException ee) {
            System.out.println(PLname+ ChatColor.RED+"Error Player not found!");
            return;
        }
        if(!e.getReason().equals(ServerConnectEvent.Reason.JOIN_PROXY)){
            return;
        }
        if(!(p.hasPermission("staff.nolobby"))){
            boolean online;
            Socket s = new Socket();
            try {
                s.connect(new InetSocketAddress("proxy.the-asmc.com", 25566), 10); //good timeout is 10-20
                s.close();
                online = true;
            } catch (IOException ex) {
                online = false;
            }
            if(!online){
                p.disconnect(ChatColor.RED+"The verification Server is null, please try again soon.");
                return;
            }
            e.setTarget(ProxyServer.getInstance().getServerInfo("checkpack"));
        }else{
            e.setTarget(ProxyServer.getInstance().getServerInfo("lobby"));
        }
    }
    @EventHandler
    public void onLogin(PostLoginEvent e){
        ProxiedPlayer p = e.getPlayer();
        String hostname = p.getPendingConnection().getVirtualHost().getHostName();
        System.out.println(p.getName()+" join with hostname "+hostname);
        if (!p.hasPermission("domain.bypass") && !hostname.contains(domain)){
            p.disconnect(wrongdomainkick);
        }
    }
    @EventHandler
    public void onJoinHost(net.md_5.bungee.api.event.LoginEvent e){
        String uuid = e.getConnection().getUUID();
        ProxiedPlayer p = ProxyServer.getInstance().getPlayer(uuid);
        String PlayerIP = e.getConnection().getAddress().getAddress().getHostAddress();
        if(p != null) {
            for (ProxiedPlayer ap : ProxyServer.getInstance().getPlayers()) {
                if (ap.getAddress().getAddress().getHostAddress().equals(PlayerIP)){
                    p.disconnect(ChatColor.RED+"You have the same IPAddress on the server.\n"+ChatColor.GRAY+"You can contact staff in discord\n"+ChatColor.BLUE+"dsc.gg/ChasingClub");
                }
                if (ap.getName().equals(p.getName())) {
                    ap.disconnect(ChatColor.RED + "You logged in from another location!");
                }
            }
        }
    }
    public String getCountry(String IP) throws IOException {
        String apiURL = String.format("http://ip-api.com/json/%s", IP);
        URL url = new URL(apiURL);
        BufferedReader stream = new BufferedReader(new InputStreamReader(url.openStream()));
        String websiteResponse = stream.readLine();
        stream.close();

        JsonObject resp = new Gson().fromJson(websiteResponse, JsonObject.class);

        if (resp != null && resp.has("country")) return resp.get("country").getAsString();

        return null;
    }
}
