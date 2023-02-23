package cc.core.Utilities;

import cc.core.Core;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Objects;
import java.util.Random;

import static cc.core.Core.plugin;

public class randomTeleportUtils {
    //List of all the unsafe blocks
    public static HashSet<Material> bad_blocks = new HashSet<>();

    static{
        bad_blocks.add(Material.LAVA);
        bad_blocks.add(Material.FIRE);
        bad_blocks.add(Material.CACTUS);
        bad_blocks.add(Material.WATER);
        bad_blocks.add(Material.COBWEB);
        bad_blocks.add(Material.SWEET_BERRY_BUSH);
        bad_blocks.add(Material.NETHER_PORTAL);
        bad_blocks.add(Material.END_PORTAL);
        bad_blocks.add(Material.END_PORTAL_FRAME);
        bad_blocks.add(Material.MAGMA_BLOCK);
        bad_blocks.add(Material.NETHER_WART);
        bad_blocks.add(Material.KELP);
        bad_blocks.add(Material.KELP_PLANT);
    }

    public static Location generateLocation(Player player){
        //Generate Random Location
        Random random = new Random();

        double border = player.getWorld().getWorldBorder().getSize();

        // Get a random location within the default world border
        int x = random.nextInt((int) border/2);
        int z = random.nextInt((int) border/2);
        int y = 0;

        if(plugin.getConfig().getBoolean("rtp-radius-limit")){ //If they want to limit the distance
            x = random.nextInt(plugin.getConfig().getInt("rtp-radius"));
            z = random.nextInt(plugin.getConfig().getInt("rtp-radius"));
            y = 150;
        }else if(!plugin.getConfig().getBoolean("rtp-radius-limit")){ //If they don't
            x = random.nextInt(25000); //25000 is default
            z = random.nextInt(25000);
            y = 150;
        }

        Location randomLocation = new Location(player.getWorld(), x, y, z);
        y = Objects.requireNonNull(randomLocation.getWorld()).getHighestBlockYAt(randomLocation);
        randomLocation.setY(y+1);

        return randomLocation;
    }

    public static Location findSafeLocation(Player player){

        Location randomLocation = generateLocation(player);

        while (!isLocationSafe(randomLocation)){
            //Keep looking for a safe location
            randomLocation = generateLocation(player);
        }
        return randomLocation;
    }

    public static boolean isLocationSafe(Location location){

        int x = location.getBlockX();
        int y = location.getBlockY();
        int z = location.getBlockZ();
        //Get instances of the blocks around where the player would spawn
        Block block = Objects.requireNonNull(location.getWorld()).getBlockAt(x, y, z);
        Block below = location.getWorld().getBlockAt(x, y - 2, z);
        Block above = location.getWorld().getBlockAt(x, y + 2, z);

        //Check to see if the surroundings are safe or not
        return !(bad_blocks.contains(below.getType())) || (block.getType().isSolid()) || (above.getType().isSolid());
    }
}
