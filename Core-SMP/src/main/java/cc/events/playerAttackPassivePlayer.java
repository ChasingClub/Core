package cc.events;

import io.papermc.paper.event.player.PrePlayerAttackEntityEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerEvent;

public class playerAttackPassivePlayer implements Listener {

    @EventHandler
    public void playerAttackPassivePlayer(PrePlayerAttackEntityEvent e) {
        Player player = e.getPlayer();

    }

}
