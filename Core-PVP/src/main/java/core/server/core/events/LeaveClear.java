package core.server.core.events;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffect;

import static org.bukkit.Bukkit.getServer;
import static core.server.core.Core.*;

public class LeaveClear implements Listener {
    @EventHandler
    public void onleavesv(PlayerQuitEvent e){
        Player p = e.getPlayer();
        if (build.contains(p.getName())) {
            build.remove(p.getName());
            msgconsole(p.getName()+"'s build mode has been disabled, Because They left the server.");
        }
        if (ingame.get(p.getName()) != null){
            World SessionWorld = Bukkit.getServer().getWorld("world");
            Location Spawn = new Location(SessionWorld, 64.5, 180, 26.5);
            p.teleport(Spawn);
            p.getInventory().clear();
            Player target = getServer().getPlayer(ingame.get(p.getName()));
            target.getInventory().clear();
            combatList.put(target.getName(), 0);
            target.teleport(Spawn);
            target.sendMessage(Plname + "GG! you have defeated " + p.getName());
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
