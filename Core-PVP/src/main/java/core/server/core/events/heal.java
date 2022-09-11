package core.server.core.events;

import core.server.core.Cuboid;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

public class heal implements Listener {
    @EventHandler
    public void onKillGetHeal(PlayerDeathEvent e) {
        Player p = e.getEntity();
        Player killer = p.getKiller();
        if (killer instanceof Player && p instanceof Player && !killer.getName().equals(p.getName())) {
            double rounded = Math.round(killer.getHealth() * 10) / 10;
            p.sendMessage(killer.getName()+" have "+ChatColor.YELLOW+rounded+ChatColor.RED+"♥"+ChatColor.RESET+" left.");
            Cuboid cuboid = new Cuboid(Bukkit.getServer().getWorld("world"), 160, 50, -66, -24, 2, 121);
            if (cuboid.contains(killer.getLocation())) {
                if (killer.getGameMode() != GameMode.CREATIVE && killer.getGameMode() != GameMode.SPECTATOR) {
                    killer.getInventory().addItem(new ItemStack(Material.GOLDEN_APPLE, 1));
                }
            }
            killer.setHealth(killer.getMaxHealth());
            killer.setFoodLevel(20);
            killer.setSaturation(20f);
        }
    }
}
