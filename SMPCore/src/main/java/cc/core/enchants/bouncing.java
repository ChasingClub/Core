package cc.core.enchants;

import cc.core.Core;
import io.papermc.paper.enchantments.EnchantmentRarity;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;
import java.util.Set;

import static cc.core.Core.plugin;

public class bouncing extends Enchantment implements Listener {
    public bouncing(String namespace) {
        super(new NamespacedKey("chasingclub", namespace));
    }

    public static final double MIN_MAGNITUDE_THRESHOLD = 0.6;
    public static final int MAX_BOUNCE_COUNT = 10;

    @EventHandler
    public void onEntityShootBowEvent(EntityShootBowEvent event) {

        LivingEntity entity = event.getEntity();
        Entity projectile = event.getProjectile();

        if (entity instanceof Player && projectile.getType() == EntityType.ARROW) {

            ItemStack theBow = event.getBow();

            Player player = (Player) event.getEntity();

            if (Objects.requireNonNull(player.getInventory().getItemInMainHand()).getEnchantments().containsKey(Enchantment.getByKey(Core.bouncing.getKey()))) {
                    final int enchantmentLevel = theBow.getItemMeta().getEnchantLevel(Enchantment.getByKey(Core.bouncing.getKey()));
                    final int bouncingCount = Math.max(1, Math.min(enchantmentLevel, MAX_BOUNCE_COUNT));
                    projectile.setMetadata("bouncing", new FixedMetadataValue(plugin, 10));
                    if (player.getInventory().getItemInMainHand().getEnchantments().containsKey(Enchantment.ARROW_INFINITE)) {
                        projectile.setMetadata("bouncingInf", new FixedMetadataValue(plugin, 10));
                    }
            }

        }

    }

    @EventHandler(priority= EventPriority.HIGH, ignoreCancelled=true)
    public void onProjectileHitEvent(ProjectileHitEvent event) {

        Projectile entity = event.getEntity();

        ProjectileSource shooter = event.getEntity().getShooter();

        if (shooter instanceof Player
                && entity.getType() == EntityType.ARROW
                && entity.hasMetadata("bouncing")) {

            Vector arrowVector = entity.getVelocity();

            final double magnitude = Math.sqrt(
                    Math.pow(arrowVector.getX(), 2) +
                            Math.pow(arrowVector.getY(), 2) +
                            Math.pow(arrowVector.getZ(), 2));

            if (magnitude < MIN_MAGNITUDE_THRESHOLD) {
                return;
            }

            Location hitLoc = entity.getLocation();

            BlockIterator b = new BlockIterator(hitLoc.getWorld(),
                    hitLoc.toVector(), arrowVector, 0, 3);

            Block hitBlock = event.getEntity().getLocation().getBlock();
            Entity hitEntity = event.getHitEntity();

            // if !hit arrow entity then remove entity
            if (hitEntity != null && hitEntity.getType() != EntityType.ARROW) {
                entity.remove();
                return;
            }

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

                float speed = (float) magnitude;
                speed *= 0.6F;

                Arrow newArrow = entity.getWorld().spawnArrow(entity.getLocation(), arrowVector.subtract(u), speed, 12.0F);

                List<MetadataValue> metaDataValues = entity.getMetadata("bouncing");
                List<MetadataValue> metaDataValuesInf = entity.getMetadata("bouncingInf");
                if (metaDataValues.size() > 0 || metaDataValuesInf.size() > 0) {
                    int prevBouncingRate = metaDataValues.get(0).asInt();
                    if (prevBouncingRate > 1) {
                        newArrow.setMetadata("bouncing", new FixedMetadataValue(plugin, prevBouncingRate - 1));
                    }
                    if (metaDataValuesInf.size() > 0) {
                        newArrow.setMetadata("bouncingInf", new FixedMetadataValue(plugin, 10));
                    }
                }

                if (!entity.hasMetadata("bouncingInf")) {
                    newArrow.setShooter(shooter);
                }
                newArrow.setFireTicks(entity.getFireTicks());

                entity.remove();

            }

        }
    }

    @Override
    public @NotNull String getName() {
        return "Boucing";
    }

    @Override
    public int getMaxLevel() {
        return 5;
    }

    @Override
    public int getStartLevel() {
        return 1;
    }

    @Override
    public @NotNull EnchantmentTarget getItemTarget() {
        return EnchantmentTarget.BOW;
    }

    @Override
    public boolean isTreasure() {
        return false;
    }

    @Override
    public boolean isCursed() {
        return false;
    }

    @Override
    public boolean conflictsWith(@NotNull Enchantment enchantment) {
        return false;
    }

    @Override
    public boolean canEnchantItem(@NotNull ItemStack item) {
        return true;
    }

    @Override
    public @NotNull Component displayName(int i) {
        return null;
    }

    @Override
    public boolean isTradeable() {
        return false;
    }

    @Override
    public boolean isDiscoverable() {
        return true;
    }

    @Override
    public @NotNull EnchantmentRarity getRarity() {
        return null;
    }

    @Override
    public float getDamageIncrease(int i, @NotNull EntityCategory entityCategory) {
        return 0;
    }


    @Override
    public @NotNull Set<EquipmentSlot> getActiveSlots() {
        return null;
    }

    @Override
    public @NotNull String translationKey() {
        return "Bouncing";
    }
}
