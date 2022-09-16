package cc.Machanic;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.util.Vector;

import java.lang.reflect.Field;

public class BouncingArrow implements Listener {

    @EventHandler
    public void onProjectileEvent(ProjectileHitEvent e) {

        Projectile projectile = e.getEntity();
        ProjectileSource projectileSource = projectile.getShooter();
        EntityType projectileType = projectile.getType();

        // Check if the projectile type is arrow
        if (projectileType == EntityType.ARROW) {

            // implement variable
            Vector arrowVelocity = projectile.getVelocity();
            double speed = arrowVelocity.length();
            Location arrowLocation = projectile.getLocation();
            BlockFace blockFace = e.getHitBlockFace();

            System.out.println(e.getHitBlockFace().toString() + " and " + projectileSource.toString());
            // if arrow not detect air
            if(blockFace != null) {

                System.out.println(e.getHitBlockFace().toString());

//                if (blockFace == BlockFace.SELF) {
//                    blockFace = BlockFace.NORTH;
//                    Vector mirrorDetection = new Vector(blockFace.getModX(), blockFace.getModY(), blockFace.getModZ());
//                    double dotProduct = arrowVelocity.dot(mirrorDetection);
//                    mirrorDetection = mirrorDetection.multiply(dotProduct).multiply(2.0D);
//
//                    // reduce projectile speed
//                    speed *= 0.6D;
//
//                }

            }

        }

    }

}
