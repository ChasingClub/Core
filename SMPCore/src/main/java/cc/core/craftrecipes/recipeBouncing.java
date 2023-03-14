package cc.core.craftrecipes;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

import static cc.core.Core.fixer;

public class recipeBouncing {
    public void EnchantBouncing(EnchantmentTarget enchantmentTarget, NamespacedKey namespacedKey) {
        ItemStack item = new ItemStack(Material.BOW);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(fixer + "Bouncing Bow");
        meta.setLore(Arrays.asList(fixer + "Bouncing Bow"));
        item.setItemMeta(meta);
        ShapedRecipe recipe = new ShapedRecipe(namespacedKey, item);
        recipe.shape("ABA", "ABA", "ABA");
        recipe.setIngredient('A', Material.ARROW);
        recipe.setIngredient('B', Material.BOW);
        Bukkit.addRecipe(recipe);
    }
}
