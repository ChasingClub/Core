package chasingclub.server.core.events;


import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import vanish.itdragclick.api.vanish.PostPlayerHideEvent;
import vanish.itdragclick.api.vanish.PostPlayerShowEvent;

import java.sql.SQLException;

import static chasingclub.server.core.Core.msgconsole;
import static chasingclub.server.core.Utils.Database.*;

public class Vanish implements Listener {
    @EventHandler
    public void onVanish(PostPlayerShowEvent e){
        Player p = e.getPlayer();
        if(connection != null) {
            try {
                DeleteVanish(p);
            } catch (SQLException ex) {
                msgconsole(ChatColor.RED+"Error While DeleteVanish!");
            }
        }
    }
    @EventHandler
    public void onUnVanish(PostPlayerHideEvent e){
        Player p = e.getPlayer();
        if(connection != null) {
            try {
                AddVanish(p);
            } catch (SQLException ex) {
                msgconsole(ChatColor.RED+"Error While AddVanish!");
            }
        }
    }
}
