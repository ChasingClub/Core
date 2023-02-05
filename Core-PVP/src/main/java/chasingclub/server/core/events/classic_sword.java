package chasingclub.server.core.events;

import chasingclub.server.core.Core;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Objects;

import static chasingclub.server.core.Utils.Database.UpdateDuel;
import static chasingclub.server.core.Utils.Utils.PluginName;
import static chasingclub.server.core.Utils.Utils.error;

public class classic_sword implements Listener {
    public Core plugin;
    public classic_sword(Core plugin){
        this.plugin = plugin;
    }

    public static HashMap<String, Integer> Lives = new HashMap<>();

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        Player target = event.getPlayer();
        Player killer = target.getKiller();
        if (killer != null) {
            if (Core.ingame.get(target.getName()) != null && target.getWorld().getName().equals("classic_sword")) { // Newwwww check world to check HashMap <-----------------------------
                killer.teleport(Objects.requireNonNull(killer.getBedSpawnLocation()));
                try {
                    UpdateDuel(target, "deaths");
                    UpdateDuel(killer, "kills");
                } catch (SQLException e) {
                    e.printStackTrace();
                    target.sendMessage(error+" Please report staff");
                    killer.sendMessage(error+" Please report staff");
                }
                if (Lives.get(target.getName()) == 1) {
                    target.getInventory().clear();
                    killer.getInventory().clear();
                    target.sendMessage(PluginName + "Nice Try! you has been defeated by " + killer.getName());
                    killer.sendMessage(PluginName + "GG! you have defeated " + target.getName());
                    if (Core.playerinmap.get(killer.getName()).equals("Colosseum2")){
                        Core.maps.put("Colosseum2", true);
                    }
                    try {
                        UpdateDuel(target, "loses");
                        UpdateDuel(killer, "wins");
                    } catch (SQLException e) {
                        e.printStackTrace();
                        target.sendMessage(error+" Please report staff");
                        killer.sendMessage(error+" Please report staff");
                    }
                    target.setGameMode(GameMode.ADVENTURE);
                    killer.setGameMode(GameMode.ADVENTURE);
                    event.setRespawnLocation(plugin.spawnloc);
                    killer.teleport(plugin.spawnloc);
                    Lives.remove(target.getName());
                    Lives.remove(killer.getName());
                    Core.ingame.remove(target.getName());
                    Core.ingame.remove(killer.getName());
                    Core.GetKitSelect(target);
                    Core.GetKitSelect(killer);
                    Core.playerinmap.remove(killer.getName());
                    Core.playerinmap.remove(target.getName());
                    Core.combatList.put(killer.getName(), 0);
                    Core.combatList.put(target.getName(), 0);
                }else{
                    Lives.put(target.getName(), Lives.get(target.getName()) - 1);
                }
            }
        }
    }
}
