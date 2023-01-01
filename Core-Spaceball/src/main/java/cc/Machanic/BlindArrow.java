package cc.Machanic;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.Vector;

import java.util.List;

public class BlindArrow implements Listener {

    @EventHandler(priority= EventPriority.HIGH, ignoreCancelled=true)
    public void ArrowHit(ProjectileHitEvent event) {

        // implements
        Projectile entity = event.getEntity();

        ProjectileSource shooter = event.getEntity().getShooter();

        if (shooter instanceof Player
                && entity.getType() == EntityType.ARROW
                && entity.hasMetadata("blind")) {

            Vector arrowVector = entity.getVelocity();

            Location hitLoc = entity.getLocation();

            BlockIterator b = new BlockIterator(hitLoc.getWorld(),
                    hitLoc.toVector(), arrowVector, 0, 3);

            Block hitBlock = event.getEntity().getLocation().getBlock();

            Block blockBefore = hitBlock;
            Block nextBlock = b.next();

            while (b.hasNext() && nextBlock.getType() == Material.AIR)
            {
                blockBefore = nextBlock;
                nextBlock = b.next();
            }

            BlockFace blockFace = nextBlock.getFace(blockBefore);

            if (blockFace != null) {

                // Convert blockFace SELF to UP:
                if (blockFace == BlockFace.SELF) {
                    blockFace = BlockFace.UP;
                }

                Vector hitPlain = new Vector(blockFace.getModX(), blockFace.getModY(), blockFace.getModZ());

                double dotProduct = arrowVector.dot(hitPlain);
                Vector u = hitPlain.multiply(dotProduct).multiply(2.0);




                List<MetadataValue> metaDataValues = entity.getMetadata("bouncing");
                if (metaDataValues.size() > 0) {
                    int prevBouncingRate = metaDataValues.get(0).asInt();
                    if (prevBouncingRate > 1) {
                    }
                }


                entity.remove();

            }

        }
    }
}
