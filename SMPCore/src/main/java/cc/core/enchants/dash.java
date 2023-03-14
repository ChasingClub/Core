package cc.core.enchants;

import cc.core.Core;
import com.destroystokyo.paper.event.player.PlayerJumpEvent;
import io.papermc.paper.enchantments.EnchantmentRarity;
import net.kyori.adventure.text.Component;
import org.bukkit.GameMode;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.EntityCategory;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.Set;

public class dash extends Enchantment implements Listener {

    public dash(String namespace) {
        super(new NamespacedKey("chasingclub", namespace));
    }

    @EventHandler
    public void onPlayerDashing(PlayerJumpEvent event) {
        Player player = event.getPlayer();
        if (player.isSneaking() && player.isSprinting() && player.isOnGround() && !player.isSwimming() && !player.isGliding() && !player.isInLava() && !player.isInWater() && !player.isInsideVehicle()) {

            if (Objects.requireNonNull(player.getInventory().getBoots()).getEnchantments().containsKey(Enchantment.getByKey(Core.dash.getKey()))) {
                // if player food level > 15 then player can dash 2.0F
                if (player.getFoodLevel() > 15) {

                    player.setVelocity(player.getLocation().getDirection().multiply(2.0F));
                    if(player.getGameMode().equals(GameMode.SURVIVAL) || player.getGameMode().equals(GameMode.ADVENTURE))
                        player.setFoodLevel(player.getFoodLevel() - 3);

                } else if (player.getFoodLevel() > 9) { // if player food level > 9 then player can dash 1.5F

                    player.setVelocity(player.getLocation().getDirection().multiply(1.5F));
                    if(player.getGameMode().equals(GameMode.SURVIVAL) || player.getGameMode().equals(GameMode.ADVENTURE))
                        player.setFoodLevel(player.getFoodLevel() - 3);

                } else if (player.getFoodLevel() > 3) { // if player food level > 3 then player can dash 0.5F

                    player.setVelocity(player.getLocation().getDirection().multiply(0.5F));
                    if(player.getGameMode().equals(GameMode.SURVIVAL) || player.getGameMode().equals(GameMode.ADVENTURE))
                        player.setFoodLevel(player.getFoodLevel() - 3);

                }

                // make that player cannot hold sneak and Dashing
                player.setSneaking(false);
            }
        }
    }

    @Override
    public @NotNull String getName() {
        return "Dash";
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
        return EnchantmentTarget.ARMOR_FEET;
    }

    @Override
    public boolean isTreasure() {
        return true;
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
    public Component displayName(int level) {
        return Component.text("Dash");
    }

    @Override
    public boolean isTradeable() {
        return true;
    }

    @Override
    public boolean isDiscoverable() {
        return false;
    }

    @Override
    public EnchantmentRarity getRarity() {
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
        return "Dash";
    }
}
