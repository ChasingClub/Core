package chasingclub.server.core.events;

import com.destroystokyo.paper.event.player.PlayerJumpEvent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import static chasingclub.server.core.Core.bhopcooldown;
import static chasingclub.server.core.Utils.Utils.PluginName;

public class Bhopping implements Listener {

    @EventHandler
    public void onPlayerJump(PlayerJumpEvent e) {
        Player player = e.getPlayer();
        if (player.getLocation().getWorld().getName().endsWith("world")) {
            if (player.isSneaking() && player.isSprinting() && !(player.isFlying()) && !(player.isInLava()) && !(player.isClimbing()) && !(player.isSwimming())) {
                if (bhopcooldown.get(player.getName()) != null){
                    player.sendMessage(PluginName+ ChatColor.RED+"Your B-hop is in cooldown.");
                    return;
                }
                player.setFoodLevel(player.getFoodLevel() - 3);
                player.setSneaking(false);
                player.setVelocity(player.getLocation().getDirection());
            }
        }
//        if (player.getLocation().getWorld().getName().endsWith("world_nether")) {
//            if (player.isSneaking() && player.isSprinting() && !(player.isFlying()) && !(player.isInLava()) && !(player.isClimbing()) && !(player.isSwimming())) {
//                if (player.getLocation().getWorld().getName().endsWith("world")) {
//                    player.setFoodLevel(player.getFoodLevel() - 3);
//                }
//                player.setVelocity(player.getLocation().getDirection());
//            }
//        }
    }
}