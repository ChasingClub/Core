package core.itdragclick.events;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffect;

import static core.itdragclick.Core.build;
import static core.itdragclick.Core.msgconsole;

public class LeaveClear implements Listener {
    @EventHandler
    public void onleavesv(PlayerQuitEvent e){
        Player p = e.getPlayer();
        if (build.contains(p.getName())) {
            build.remove(p.getName());
            msgconsole(p.getName()+"'s build mode has been disabled, Because They left the server.");
        }
        if (p.getGameMode() != GameMode.CREATIVE && p.getGameMode() != GameMode.SPECTATOR) {
            p.getInventory().clear();
            for (PotionEffect effect : p.getActivePotionEffects())
                p.removePotionEffect(effect.getType());
        }
    }
}
