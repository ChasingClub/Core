package chasingclub.server.core.events;

import chasingclub.server.core.Core;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffect;

import java.sql.SQLException;

import static chasingclub.server.core.Utils.Database.UpdateKit;
import static chasingclub.server.core.Utils.Utils.PluginName;
import static chasingclub.server.core.Utils.Utils.error;
import static org.bukkit.Bukkit.getServer;
import static chasingclub.server.core.Core.*;

public class LeaveEvent implements Listener {
    public Core plugin;
    public LeaveEvent(Core plugin){
        this.plugin = plugin;
    }
    @EventHandler
    public void onleavesv(PlayerQuitEvent e){
        Player p = e.getPlayer();
        if (build.contains(p.getName())) {
            build.remove(p.getName());
            msgconsole(p.getName()+"'s build mode has been disabled, Because They left the server.");
        }
        String kit = kits.get(p);
        try {
            switch (kit) {
                case "Default":
                    UpdateKit(p, 0);
                    break;
                case "Trident":
                    UpdateKit(p, 1);
                    break;
                case "Viking":
                    UpdateKit(p, 2);
                    break;
                case "Archer":
                    UpdateKit(p, 3);
                    break;
                case "Admin":
                    UpdateKit(p, 4);
                    break;
            }
        } catch (SQLException ex) {
            msgconsole(error);
        }
        if (ingame.get(p.getName()) != null){
            p.teleport(plugin.spawnloc);
            p.getInventory().clear();
            Player target = getServer().getPlayer(ingame.get(p.getName()));
            target.getInventory().clear();
            combatList.put(target.getName(), 0);
            target.teleport(plugin.spawnloc);
            target.sendMessage(PluginName + "GG! you have defeated " + p.getName());
            GetKitSelect(target);
            if (playerinmap.get(p.getName()).equals("Colosseum")){
                maps.put("Colosseum", true);
            }if (playerinmap.get(p.getName()).equals("Beach")){
                maps.put("Beach", true);
            }if (playerinmap.get(p.getName()).equals("Abyss")){
                maps.put("Abyss", true);
            }
            playerinmap.remove(p.getName());
            playerinmap.remove(target.getName());
            ingame.remove(target.getName());
            ingame.remove(p.getName());
        }
        if (p.getLocation().getWorld().getName().endsWith("world") && p.getGameMode() != GameMode.CREATIVE) {
            p.getInventory().clear();
            for (PotionEffect effect : p.getActivePotionEffects())
                p.removePotionEffect(effect.getType());
        }
    }
}
