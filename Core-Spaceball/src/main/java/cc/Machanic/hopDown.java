package cc.Machanic;

import com.destroystokyo.paper.event.player.PlayerJumpEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class hopDown implements Listener {

    @EventHandler
    public void onSneak(PlayerMoveEvent e) {

        Player player = e.getPlayer();

        if(!(player.isOnGround()) && player.isSneaking() && !(player.isSwimming()) && !(player.isFlying()) && !(player.isFrozen()) && !(player.isVisualFire())) {
            player.setVelocity(player.getLocation().getDirection().setY(1));
            player.setFoodLevel(player.getFoodLevel() - 3);
            player.setSneaking(false);
        }
    }

    @EventHandler
    public void onJump(PlayerJumpEvent e) {

        Player player = e.getPlayer();

        if(!(player.isOnGround() && player.isSneaking() && player.isSwimming() && player.isFlying() && player.isFrozen() && player.isVisualFire()) && player.isJumping()) {
            player.setVelocity(player.getVelocity().setY(7));
            player.setJumping(false);
        }

    }

}
