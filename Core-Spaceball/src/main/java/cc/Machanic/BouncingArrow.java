package cc.Machanic;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.util.Vector;

import java.lang.reflect.Field;
import java.rmi.server.ExportException;
import java.util.Objects;

public class BouncingArrow implements Listener {

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onProjectileEvent(ProjectileHitEvent e) {

        Projectile projectile = e.getEntity();
        ProjectileSource projectileSource = projectile.getShooter();
        EntityType projectileType = projectile.getType();

        // Check if the projectile type is Arrow
        if (projectileType == EntityType.ARROW) {

            // implement variable
            Vector arrowVelocity = projectile.getVelocity();
            double speed = arrowVelocity.length();
            Location arrowLocation = projectile.getLocation();
            BlockFace blockFace = e.getHitBlockFace();

            Bukkit.getLogger().info(e.getHitBlockFace() + " and " + projectileSource);



            // if arrow not detect air
            if(blockFace != null && speed >= 0.3D) {

                Bukkit.getLogger().info("BlockFace != null");

                if (blockFace == BlockFace.UP) {

                    Vector mirrorDirection = new Vector(blockFace.getModX(), blockFace.getModY(), blockFace.getModZ());
                    double dotProduct = arrowVelocity.dot(mirrorDirection);
                    mirrorDirection = mirrorDirection.multiply(dotProduct).multiply(0.2D);

                    // reduce projectile speed
                    speed *= 0.5D;

                    Projectile newProjectile;

                    newProjectile = projectile.getWorld().spawnArrow(arrowLocation, arrowVelocity.subtract(mirrorDirection), (float) speed, 4.0F);

                    // make arrow pickup-able
                    if (projectileSource instanceof Player) {

                        Field field;

                        try {

                            // create arrow
                            Object entityArrow = newProjectile.getClass().getMethod("getHandle").invoke(newProjectile);
                            field = entityArrow.getClass().getDeclaredField("fromPlayer");
                            // field.setAccessible(true);
                            field.set(entityArrow, 1); // create 1 arrow

                        } catch (Exception err) {

                            System.out.println("[CscbAr] Failed to set the arrow pick-able! StackTrace: ");
                            err.printStackTrace();

                        }

                    } else {

                        newProjectile = (Projectile) projectile.getWorld().spawnEntity(arrowLocation, projectile.getType());
                        newProjectile.setVelocity(arrowVelocity.subtract(mirrorDirection).normalize().multiply(speed));

                    }

                    newProjectile.setShooter(projectileSource); // set arrow owner's to player shooter
                    newProjectile.setFireTicks(projectile.getFireTicks()); // if bow has flame enchant

                    // remove old arrow
                    projectile.remove();
                    Bukkit.getLogger().info("condition 1: UP");

                } else if (blockFace == BlockFace.DOWN) {

                    Vector mirrorDirection = new Vector(blockFace.getModX(), blockFace.getModY(), blockFace.getModZ());
                    double dotProduct = arrowVelocity.dot(mirrorDirection);
                    mirrorDirection = mirrorDirection.multiply(dotProduct).multiply(2.0D);

                    // reduce projectile speed
                    speed *= 0.6D;

                    Projectile newProjectile;

                    newProjectile = projectile.getWorld().spawnArrow(arrowLocation, arrowVelocity.subtract(mirrorDirection), (float) speed, 4.0F);

                    // make arrow pickup-able
                    if (projectileSource instanceof Player) {

                        Field field;

                        try {

                            // create arrow
                            Object entityArrow = newProjectile.getClass().getMethod("getHandle").invoke(newProjectile);
                            field = entityArrow.getClass().getDeclaredField("fromPlayer");
                            // field.setAccessible(true);
                            field.set(entityArrow, 1); // create 1 arrow

                        } catch (Exception err) {

                            System.out.println("[CscbAr] Failed to set the arrow pick-able! StackTrace: ");
                            err.printStackTrace();

                        }

                    } else {

                        newProjectile = (Projectile) projectile.getWorld().spawnEntity(arrowLocation, projectile.getType());
                        newProjectile.setVelocity(arrowVelocity.subtract(mirrorDirection).normalize().multiply(speed));

                    }

                    newProjectile.setShooter(projectileSource); // set arrow owner's to player shooter
                    newProjectile.setFireTicks(projectile.getFireTicks()); // if bow has flame enchant

                    // remove old arrow
                    projectile.remove();
                    Bukkit.getLogger().info("condition 2: DOWN");

                } else if (blockFace == BlockFace.NORTH) {

                    Vector mirrorDirection = new Vector(blockFace.getModX(), blockFace.getModY(), blockFace.getModZ());
                    double dotProduct = arrowVelocity.dot(mirrorDirection);
                    mirrorDirection = mirrorDirection.multiply(dotProduct).multiply(2.0D);

                    // reduce projectile speed
                    speed *= 0.6D;

                    Projectile newProjectile;

                    newProjectile = projectile.getWorld().spawnArrow(arrowLocation, arrowVelocity.subtract(mirrorDirection), (float) speed, 4.0F);

                    // make arrow pickup-able
                    if (projectileSource instanceof Player) {

                        Field field;

                        try {

                            // create arrow
                            Object entityArrow = newProjectile.getClass().getMethod("getHandle").invoke(newProjectile);
                            field = entityArrow.getClass().getDeclaredField("fromPlayer");
                            // field.setAccessible(true);
                            field.set(entityArrow, 1); // create 1 arrow

                        } catch (Exception err) {

                            System.out.println("[CscbAr] Failed to set the arrow pick-able! StackTrace: ");
                            err.printStackTrace();

                        }

                    } else {

                        newProjectile = (Projectile) projectile.getWorld().spawnEntity(arrowLocation, projectile.getType());
                        newProjectile.setVelocity(arrowVelocity.subtract(mirrorDirection).normalize().multiply(speed));

                    }

                    newProjectile.setShooter(projectileSource); // set arrow owner's to player shooter
                    newProjectile.setFireTicks(projectile.getFireTicks()); // if bow has flame enchant

                    // remove old arrow
                    projectile.remove();
                    Bukkit.getLogger().info("condition 4: NORTH");

                } else if (blockFace == BlockFace.EAST) {

                    Vector mirrorDirection = new Vector(blockFace.getModX(), blockFace.getModY(), blockFace.getModZ());
                    double dotProduct = arrowVelocity.dot(mirrorDirection);
                    mirrorDirection = mirrorDirection.multiply(dotProduct).multiply(2.0D);

                    // reduce projectile speed
                    speed *= 0.6D;

                    Projectile newProjectile;

                    newProjectile = projectile.getWorld().spawnArrow(arrowLocation, arrowVelocity.subtract(mirrorDirection), (float) speed, 4.0F);

                    // make arrow pickup-able
                    if (projectileSource instanceof Player) {

                        Field field;

                        try {

                            // create arrow
                            Object entityArrow = newProjectile.getClass().getMethod("getHandle").invoke(newProjectile);
                            field = entityArrow.getClass().getDeclaredField("fromPlayer");
                            // field.setAccessible(true);
                            field.set(entityArrow, 1); // create 1 arrow

                        } catch (Exception err) {

                            System.out.println("[CscbAr] Failed to set the arrow pick-able! StackTrace: ");
                            err.printStackTrace();

                        }

                    } else {

                        newProjectile = (Projectile) projectile.getWorld().spawnEntity(arrowLocation, projectile.getType());
                        newProjectile.setVelocity(arrowVelocity.subtract(mirrorDirection).normalize().multiply(speed));

                    }

                    newProjectile.setShooter(projectileSource); // set arrow owner's to player shooter
                    newProjectile.setFireTicks(projectile.getFireTicks()); // if bow has flame enchant

                    // remove old arrow
                    projectile.remove();
                    Bukkit.getLogger().info("condition 5: EAST");

                } else if (blockFace == BlockFace.WEST) {

                    Vector mirrorDirection = new Vector(blockFace.getModX(), blockFace.getModY(), blockFace.getModZ());
                    double dotProduct = arrowVelocity.dot(mirrorDirection);
                    mirrorDirection = mirrorDirection.multiply(dotProduct).multiply(2.0D);

                    // reduce projectile speed
                    speed *= 0.6D;

                    Projectile newProjectile;

                    newProjectile = projectile.getWorld().spawnArrow(arrowLocation, arrowVelocity.subtract(mirrorDirection), (float) speed, 4.0F);

                    // make arrow pickup-able
                    if (projectileSource instanceof Player) {

                        Field field;

                        try {

                            // create arrow
                            Object entityArrow = newProjectile.getClass().getMethod("getHandle").invoke(newProjectile);
                            field = entityArrow.getClass().getDeclaredField("fromPlayer");
                            // field.setAccessible(true);
                            field.set(entityArrow, 1); // create 1 arrow

                        } catch (Exception err) {

                            System.out.println("[CscbAr] Failed to set the arrow pick-able! StackTrace: ");
                            err.printStackTrace();

                        }

                    } else {

                        newProjectile = (Projectile) projectile.getWorld().spawnEntity(arrowLocation, projectile.getType());
                        newProjectile.setVelocity(arrowVelocity.subtract(mirrorDirection).normalize().multiply(speed));

                    }

                    newProjectile.setShooter(projectileSource); // set arrow owner's to player shooter
                    newProjectile.setFireTicks(projectile.getFireTicks()); // if bow has flame enchant

                    // remove old arrow
                    projectile.remove();
                    Bukkit.getLogger().info("condition 6: WEST");

                }
            }

        }

    }


    // ## This is Main Code
    /**
//     *     @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
     *     public void onProjectileEvent(ProjectileHitEvent e) {
     *         Projectile projectile = e.getEntity();
     *         ProjectileSource source = projectile.getShooter();
     *         EntityType projectileType = projectile.getType();
     *
     *         if (projectile.hasMetadata("bouncing")) {
     *             Vector arrowVelocity = projectile.getVelocity();
     *             double speed = arrowVelocity.length();
     *
     *             if (speed < 0.3D || (projectileType == EntityType.ARROW && speed < 0.5D)) {
     *                 return;
     *             }
     *
     *             Location arrowLocation = projectile.getLocation();
     *             Block hitBlock = arrowLocation.getBlock();
     *
     *             BlockFace blockFace = BlockFace.UP;
     *
     *             if (blockFace != null) {
     *                 if (blockFace == BlockFace.SELF) {
     *                     blockFace = BlockFace.UP;
     *                 }
     *
     *                 Vector mirrorDirection = new Vector(blockFace.getModX(), blockFace.getModY(), blockFace.getModZ());
     *                 double dotProduct = arrowVelocity.dot(mirrorDirection);
     *                 mirrorDirection = mirrorDirection.multiply(dotProduct).multiply(2.0D);
     *
     *                 // reduce projectile speed
     *                 speed *= 0.6D;
     *
     *                 Projectile newProjectile;
     *                 if (projectileType == EntityType.ARROW) {
     *                     // spawn with slight spray:
     *                     newProjectile = projectile.getWorld().spawnArrow(arrowLocation, arrowVelocity.subtract(mirrorDirection), (float) speed, 4.0F);
     *
     *                     // make the arrow pickup-able:
     *                     if (source instanceof Player) {
     *                         Field field;
     *                         try {
     *                             Object entityArrow = newProjectile.getClass().getMethod("getHandle").invoke(newProjectile);
     *                             field = entityArrow.getClass().getDeclaredField("fromPlayer");
     *                             // field.setAccessible(true);
     *                             field.set(entityArrow, 1);
     *                         } catch (Exception err) {
     *                             System.out.println("[BouncingArrows] Failed to set the arrow pick-able! StackTrace: ");
     *                             err.printStackTrace();
     *                         }
     *                     }
     *                 } else {
     *                     // without spray:
     *                     newProjectile = (Projectile) projectile.getWorld().spawnEntity(arrowLocation, projectile.getType());
     *                     newProjectile.setVelocity(arrowVelocity.subtract(mirrorDirection).normalize().multiply(speed));
     *                 }
     *
     *                 newProjectile.setShooter(source);
     *                 newProjectile.setFireTicks(projectile.getFireTicks());
     *
     *                 // remove old arrow
     *                 projectile.remove();
     *
     *     }**/




}
