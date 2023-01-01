package core.itdragclick.events;

import core.itdragclick.Core;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import vanish.itdragclick.api.vanish.VanishAPI;

import java.sql.SQLException;

import static core.itdragclick.Core.msgconsole;
import static core.itdragclick.Utils.Database.*;
import static core.itdragclick.Utils.Utils.*;


public class onjoin implements Listener {
    public Core plugin;

    public onjoin(Core plugin){
        this.plugin = plugin;
    }
    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        Player p = e.getPlayer();
        if (p.getGameMode() != GameMode.CREATIVE && p.getGameMode() != GameMode.SPECTATOR){
            p.setGameMode(GameMode.ADVENTURE);
            if(p.hasPermission("rank.vip")) {
                p.setAllowFlight(true);
            }
            p.teleport(plugin.spawnloc);
            p.getInventory().clear();
        }
        getserverselect(p);
        getprofileitem(p);
        getcosmeticitem(p);
        getsettingsitem(p);
        if(connection != null){
            try {
                if(VanishAPI.isInvisible(p)){
                    AddVanish(p);
                }else{
                    DeleteVanish(p);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                msgconsole(ChatColor.RED+"Could not initialize database.");
            }
        }
    }
}
