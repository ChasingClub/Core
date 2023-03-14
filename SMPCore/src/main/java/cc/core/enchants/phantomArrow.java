package cc.core.enchants;

import cc.core.Core;
import io.papermc.paper.enchantments.EnchantmentRarity;
import net.kyori.adventure.text.Component;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class phantomArrow extends Enchantment implements Listener {
    public phantomArrow(String namespace) {
        super(new NamespacedKey("chasingclub", namespace));
    }


    List<Arrow> arrows = new ArrayList<>();

    @EventHandler
    public void onShoot(EntityShootBowEvent e) {

        if (e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();
            ItemStack bow = e.getBow();
            if (bow != null && bow.getEnchantments().containsKey(Enchantment.getByKey(Core.phantomArrow.getKey()))) {
                Arrow arrow = (Arrow) e.getProjectile();
                updateArrow(arrow);
                arrows.add(arrow);
            }
        }

    }

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent e) {
        Entity entity = e.getEntity();

        if (entity instanceof Arrow) {
            Arrow arrow = (Arrow) entity;
            arrows.remove(arrow); // Remove Arrow
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();

        for (Arrow arrow : arrows) {
            List<UUID> uuids = arrow.getNearbyEntities(5,5,5).stream().map((entity) -> entity.getUniqueId()).collect(Collectors.toList());
            if (uuids.contains(p.getUniqueId())) { // Check if player is next to an arrow
                updateArrow(arrow);
            }
        }
    }

    private void updateArrow(Arrow arrow) {
        Player target = null;
        double targetDistance = 10;
        // Search for nearest Player
        for (Entity nearest : arrow.getNearbyEntities(5, 100, 5)) {
            if (!nearest.getUniqueId().equals(((LivingEntity)arrow.getShooter()).getUniqueId()) && nearest.getType() != EntityType.ARROW) { // Not the shooter
                double distance = arrow.getLocation().distance(nearest.getLocation());
                if (target == null ||
                        distance < targetDistance) {
                    targetDistance = distance;
                    target = (Player) nearest;
                }
            }
        }
        if (target != null) {
            arrow.setVelocity(target.getLocation().toVector().subtract(arrow.getLocation().toVector())); // Set direction of arrow
        }
    }

    @Override
    public @NotNull String getName() {
        return "Phantom";
    }

    @Override
    public int getMaxLevel() {
        return 1;
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
        return item.getType().name().contains("BOW");
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
        return false;
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
        return "Homing";
    }
}
