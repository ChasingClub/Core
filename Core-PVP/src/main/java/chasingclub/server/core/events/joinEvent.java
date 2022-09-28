package chasingclub.server.core.events;

import chasingclub.server.core.Core;
import chasingclub.server.core.Utils.KitSQLAPI;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.potion.PotionEffect;
import vanish.itdragclick.api.vanish.VanishAPI;

import java.sql.SQLException;

import static chasingclub.server.core.Utils.Database.*;
import static chasingclub.server.core.Utils.Utils.PluginName;
import static chasingclub.server.core.Utils.Utils.error;

public class joinEvent implements Listener {
    public Core plugin;
    public KitSQLAPI GetPlayerKit(String uuid) {
        try {
            return FindKitByPlayerUUID(uuid);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public joinEvent(Core plugin){
        this.plugin = plugin;
    }
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if (Core.combatList.containsKey(e.getPlayer().getName())) {
            Core.combatList.put(p.getName(), 0);
        }
        Core.gotkit.put(p.getName(), false);
        KitSQLAPI data = GetPlayerKit(p.getUniqueId().toString());
        if(data == null){
            try {
                AddKit(p);
            } catch (SQLException ex) {
                p.sendMessage(error);
            }
            Core.kits.put(p,"Default");
            p.sendMessage(PluginName+"You are currently select "+ChatColor.GREEN+"Default");
        }else{
            int kit = data.GetKit();
            String KitName = "Default";
            if (kit == 0) {
                KitName = "Default";
            }else if (kit == 1) {
                KitName = "Trident";
            }else if (kit == 2) {
                KitName = "Viking";
            }else if (kit == 3) {
                KitName = "Archer";
            }else if (kit == 4) {
                KitName = "Admin";
            }
            Core.kits.put(p, KitName);
            p.sendMessage(PluginName+"You are currently select "+ChatColor.GREEN+KitName);
        }
        if (p.getGameMode() != GameMode.CREATIVE && p.getGameMode() != GameMode.SPECTATOR){
            p.setGameMode(GameMode.ADVENTURE);
            p.teleport(plugin.spawnloc);
            p.getInventory().clear();
            Core.GetKitSelect(p);
            for (PotionEffect effect : p.getActivePotionEffects())
                p.removePotionEffect(effect.getType());
            p.setWalkSpeed(0.2F); // default walk speed is 2F
        }
        if(connection != null){
            try {
                if(VanishAPI.isInvisible(p)){
                    AddVanish(p);
                }else{
                    DeleteVanish(p);
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
