package cc.core.events;

import cc.core.Core;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Objects;

public class playerDeath implements Listener {

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();


            ItemStack head = new ItemStack(Material.PLAYER_HEAD, 1);
            SkullMeta meta = (SkullMeta) head.getItemMeta();
            meta.setOwningPlayer(player);
            // set head name to died player name
            meta.setDisplayName(ChatColor.YELLOW + player.getName() + "'s Head");
            head.setItemMeta(meta);
            // drop head where's player died
            summonHead(player, head);

    }

    private void summonHead(Player player, ItemStack head) {
        // get x, y, z of died player
        Location loc = player.getLocation();
        loc.getWorld().dropItemNaturally(loc, head);
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent e) {

        // if player bed location has bed setup
        if (e.getPlayer().getBedSpawnLocation() != null) {
            Location bedSpawn = e.getPlayer().getBedSpawnLocation();
            e.setRespawnLocation(bedSpawn); // spawn player at bed location
        } else {
            // if player bed location has no bed setup
            Location worldSpawn = Objects.requireNonNull(e.getPlayer().getServer().getWorld("world")).getSpawnLocation();
            worldSpawn.setWorld(Objects.requireNonNull(Core.plugin.getServer().getWorld("world")));
            e.setRespawnLocation(worldSpawn);
        }

    }

}
