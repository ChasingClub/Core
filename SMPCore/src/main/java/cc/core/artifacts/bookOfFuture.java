package cc.core.artifacts;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

import static cc.core.Core.plugin;

public class bookOfFuture implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        String playerName = player.getName();

        if (!plugin.getConfig().getBoolean("beta-player." + playerName + ".received")) {
            // give the player a Book of Future
            ItemStack book = new ItemStack(Material.BOOK);

            // give a book of future to the player with custom name and glint effect
            ItemStack bookOfFuture = new ItemStack(Material.BOOK, 1);
            ItemMeta bookMeta = bookOfFuture.getItemMeta();

            bookMeta.setDisplayName(ChatColor.AQUA + "Book of Future");
            bookMeta.addEnchant(Enchantment.LUCK, 1, true);
            bookMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

            // give the book a lore
            bookMeta.setLore(Arrays.asList("",ChatColor.WHITE + "Given to: " + ChatColor.YELLOW + playerName, "", ChatColor.GRAY + "Within the pages of the Book of the Future lies the secrets of what is yet to come.", ChatColor.GRAY + "Those who possess it hold the power to shape their destiny,", ChatColor.GRAY + "but with great knowledge comes great responsibility.", "", ChatColor.RED + "" + ChatColor.BOLD + "Special Artifact"));
            bookOfFuture.setItemMeta(bookMeta);
            player.getInventory().addItem(bookOfFuture);

            // send a welcome message to the player
            player.sendMessage(ChatColor.YELLOW + "Welcome " + playerName + "! Since you have registered for the beta, " +
                    "you have received the " + ChatColor.AQUA + ChatColor.BOLD + "Book of Future" + ChatColor.YELLOW + "!");

            // update the config
            plugin.getConfig().set("beta-player." + playerName + ".received", true);
            plugin.saveConfig();
        }
    }
}
