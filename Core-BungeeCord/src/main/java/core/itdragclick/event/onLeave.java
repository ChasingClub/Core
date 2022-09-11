package core.itdragclick.event;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import core.itdragclick.Database;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ServerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import org.checkerframework.checker.units.qual.C;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.SQLException;

import static core.itdragclick.Database.playerdataonleave;

public class onLeave implements Listener {
    @EventHandler
    public void onPlayerLeave(ServerDisconnectEvent e) {
        ProxiedPlayer p = e.getPlayer();
        String IP = p.getAddress().getAddress().getHostAddress();
        String Country;
        try {
            Country = getCountry(IP);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        try {
            playerdataonleave(p, Country);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
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
