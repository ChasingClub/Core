package cc;

import cc.Machanic.hopDown;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.projectiles.BlockProjectileSource;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.Vector;

import java.io.File;

public class spaceball extends JavaPlugin implements Listener {

    public static String Plname = (ChatColor.YELLOW + "C" + ChatColor.LIGHT_PURPLE + "C" + ChatColor.DARK_GRAY + " » " + ChatColor.GRAY);

    @Override
    public void onEnable() {

        // Config

        File file = new File(getDataFolder() + File.separator + "config.yml"); //This will get the config file

        if (!file.exists()) { //This will check if the file exist
            getConfig().options().copyDefaults(true); //function to check the important settings
            saveConfig(); //saves the config
            reloadConfig(); //reloads the config
        }

        // Load Plugin

        getServer().getPluginManager().registerEvents(new hopDown(), this);
        getServer().getPluginManager().registerEvents(this, this);
        Bukkit.getLogger().info(Plname + "Plugin Loaded");

    }

    @Override
    public void onDisable() {
        Bukkit.getLogger().info(Plname + "Shutting down Plugin");

    }

    // Code

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onEntityShootBowEvent(EntityShootBowEvent event) {
        LivingEntity entity = event.getEntity();
        Entity projectile = event.getProjectile();

        if (((entity instanceof Player)) && (projectile.getType() == EntityType.ARROW)) {
            Player player = (Player) entity;

            if (event.getBow().displayName().equals("BouncingBow")) {
                if (player.hasPermission("bouncingarrows.use")) {
                    projectile.setMetadata("bouncing", new FixedMetadataValue(this, true));
                }

                if (player.hasPermission("bouncingarrows.aim")) {
                    Projectile projectileP = (Projectile) projectile;
                    LivingEntity target = findTarget(projectileP);
                    if (target != null) {
                        playEffect(projectile);
                        aimAtTarget(projectileP, target, projectileP.getVelocity().length());
                    }
                }
            }
        }
    }

    // thrown snowballs
    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onProjectileLaunch(ProjectileLaunchEvent event) {
        Projectile projectile = event.getEntity();
        EntityType type = projectile.getType();
        if (type == EntityType.SNOWBALL && projectile.getShooter() instanceof Player) {
            Player player = (Player) projectile.getShooter();
            if (player.hasPermission("bouncingarrows.use")) {
                projectile.setMetadata("bouncing", new FixedMetadataValue(this, true));
            }
        }
    }

    private LivingEntity findTarget(Projectile projectile) {
        ProjectileSource source = projectile.getShooter();
        LivingEntity shooterEntity = null;
        Block shooterBlock = null;

        if (source instanceof LivingEntity) {
            shooterEntity = (LivingEntity) source;
        } else if (source instanceof BlockProjectileSource) {
            shooterBlock = ((BlockProjectileSource) source).getBlock();
        }

        double radius = 150.0D;
        Location projectileLocation = projectile.getLocation();
        Vector projectileDirection = projectile.getVelocity().normalize();
        Vector projectileVector = projectileLocation.toVector();

        LivingEntity target = null;
        double minDotProduct = Double.MIN_VALUE;
        for (Entity entity : projectile.getNearbyEntities(radius, radius, radius)) {
            if (entity instanceof LivingEntity && !entity.equals(shooterEntity)) {
                LivingEntity living = (LivingEntity) entity;
                Location newTargetLocation = living.getEyeLocation();

                // check angle to target:
                Vector toTarget = newTargetLocation.toVector().subtract(projectileVector).normalize();
                double dotProduct = toTarget.dot(projectileDirection);
                if (dotProduct > 0.97D && (shooterEntity != null ? shooterEntity.hasLineOfSight(living) : this.canSeeBlock(living, shooterBlock, (int) radius)) && (target == null || dotProduct > minDotProduct)) {
                    target = living;
                    minDotProduct = dotProduct;
                }
            }
        }

        return target;
    }

    private boolean canSeeBlock(LivingEntity entity, Block block, int maxDistance) {
        Location blockLocation = block.getLocation();
        Vector blockVector = blockLocation.toVector();
        Location eyeLocation = entity.getEyeLocation();
        Vector dir = (eyeLocation.toVector().subtract(blockVector)).normalize();
        BlockIterator iterator = new BlockIterator(blockLocation.getWorld(), blockVector, dir, 0, maxDistance);

        while (iterator.hasNext()) {
            Block b = iterator.next();
            if (b.getType() != Material.AIR && !b.equals(block)) return false;
        }
        return true;
    }

    private void aimAtTarget(final Projectile projectile, final LivingEntity target, final double speed) {
        Location projectileLocation = projectile.getLocation();
        Location targetLocation = target.getEyeLocation();
        // validate target:
        if (target.isDead() || !target.isValid() || !targetLocation.getWorld().getName().equals(projectileLocation.getWorld().getName()) || targetLocation.distanceSquared(projectileLocation) > 25000) {
            return;
        }

        // move towards target
        Vector oldVelocity = projectile.getVelocity();

        Vector direction = targetLocation.toVector().subtract(projectileLocation.toVector()).normalize().multiply(targetLocation.getY() > projectileLocation.getY() ? speed : speed / 3);
        projectile.setVelocity(oldVelocity.add(direction).normalize().multiply(speed));

        // repeat:
        getServer().getScheduler().runTaskLater(this, new Runnable() {

            @Override
            public void run() {
                if (!projectile.isDead() && projectile.isValid() && !projectile.isOnGround() && projectile.getTicksLived() < 600)
                    aimAtTarget(projectile, target, speed);
            }
        }, 1L);
    }

    private void playEffect(final Entity entity) {
        getServer().getScheduler().runTaskLater(this, new Runnable() {

            @Override
            public void run() {
                if (entity.isDead() || !entity.isValid() || entity.isOnGround()) return;
                playEffect(entity);
            }
        }, 2L);
    }


    // reflection with hit block upward and downward
    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onProjectileHitEvent(ProjectileHitEvent event) {
        Projectile projectile = event.getEntity();
        ProjectileSource source = projectile.getShooter();
        EntityType projectileType = projectile.getType();

        if (projectile.hasMetadata("bouncing")) {
            Vector arrowVelocity = projectile.getVelocity();
            double speed = arrowVelocity.length();

            if (speed < 0.3D || (projectileType == EntityType.ARROW && speed < 0.5D)) {
                return;
            }

            Location arrowLocation = projectile.getLocation();
            Block hitBlock = arrowLocation.getBlock();

            BlockFace blockFaceU = BlockFace.UP;
//            BlockFace blockFaceN = BlockFace.NORTH;

            if (blockFaceU != null) {
                if (blockFaceU == BlockFace.SELF) {
                    blockFaceU = BlockFace.UP;
                }

                Vector mirrorDirection = new Vector(blockFaceU.getModX(), blockFaceU.getModY(), blockFaceU.getModZ());
                double dotProduct = arrowVelocity.dot(mirrorDirection);
                mirrorDirection = mirrorDirection.multiply(dotProduct).multiply(2.0D);

                // reduce projectile speed
                speed *= 0.6D;

                Projectile newProjectile;
                if (projectileType == EntityType.ARROW) {
                    // spawn with slight spray:
                    newProjectile = projectile.getWorld().spawnArrow(arrowLocation, arrowVelocity.subtract(mirrorDirection), (float) speed, 4.0F);

                    newProjectile.setShooter(source);
                    newProjectile.setFireTicks(projectile.getFireTicks());
                    newProjectile.setMetadata("bouncing", new FixedMetadataValue(this, true));

                    // remove old arrow
                    projectile.remove();
                }
//            } else if (blockFaceN != null) {
//                if (blockFaceN == BlockFace.SELF) {
//                    blockFaceN = BlockFace.NORTH;
//                }
//
//                Vector mirriorDetection = new Vector(blockFaceN.getModX(), blockFaceN.getModY(), blockFaceN.getModZ());
//                double dotProduct = arrowVelocity.dot(mirriorDetection);
//                mirriorDetection = mirriorDetection.multiply(dotProduct).multiply(2.0D);
//            }
            }
        }
    }
}
