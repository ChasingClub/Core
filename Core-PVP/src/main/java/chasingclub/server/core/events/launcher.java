package chasingclub.server.core.events;

import chasingclub.server.core.Core;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

public class launcher implements Listener {
    public Core plugin;
    public launcher(Core plugin){
        this.plugin = plugin;
    }
    public int multiply = 8;
    public Sound sound = Sound.BLOCK_PISTON_EXTEND;
    @EventHandler
    public void onSlime(PlayerMoveEvent e){
        Player p = e.getPlayer();
        if(Core.IsLaunched.contains(p.getName())){return;}
        int playerX = p.getLocation().getBlockX();
        int playerY = p.getLocation().getBlockY() - 1;
        int playerZ = p.getLocation().getBlockZ();
        Location blockbelow = new Location(p.getWorld(), playerX, playerY, playerZ);
        Material type = blockbelow.getBlock().getType();
        if(type == Material.SLIME_BLOCK){
            if(p.getGameMode() == GameMode.CREATIVE || p.getGameMode() == GameMode.SPECTATOR){return;}
            if(plugin.SW.contains(p.getLocation())){
                Vector vector = new Vector(-0.5, 0.2, 0.5);
                Location loc = new Location(plugin.world, 243, 176, 269);
                if (plugin.world != null) {
                    plugin.world.playSound(loc , sound, 1, 0.5f);
                }
                p.setVelocity(vector.multiply(multiply));
                Core.IsLaunched.add(p.getName());
            }else if(plugin.NW.contains(p.getLocation())){
                Vector vector = new Vector(-0.5, 0.2, -0.5);
                Location loc = new Location(plugin.world, 243, 176, 243);
                if (plugin.world != null) {
                    plugin.world.playSound(loc , sound, 1, 0.5f);
                }
                p.setVelocity(vector.multiply(multiply));
                Core.IsLaunched.add(p.getName());
            }else if(plugin.NE.contains(p.getLocation())){
                Vector vector = new Vector(0.5, 0.2, -0.5);
                Location loc = new Location(plugin.world, 269, 176, 243);
                if (plugin.world != null) {
                    plugin.world.playSound(loc , sound, 1, 0.5f);
                }
                p.setVelocity(vector.multiply(multiply));
                Core.IsLaunched.add(p.getName());
            }else if(plugin.SE.contains(p.getLocation())) {
                Vector vector = new Vector(0.5, 0.2, 0.5);
                Location loc = new Location(plugin.world, 269, 176, 269);
                if (plugin.world != null) {
                    plugin.world.playSound(loc, sound, 1, 0.5f);
                }
                p.setVelocity(vector.multiply(multiply));
                Core.IsLaunched.add(p.getName());
            }
        }else if(type == Material.SPONGE){
            if(p.isSneaking()){return;}
            Vector vector = new Vector();
            vector.setY(0.3f);
            vector.setX(p.getVelocity().multiply(4).getX());
            vector.setZ(p.getVelocity().multiply(4).getZ());
            if (plugin.world != null) {
                plugin.world.playSound(p.getLocation(), sound, 1, 0.5f);
            }
            p.setVelocity(vector.multiply(3));
            Core.IsLaunched.add(p.getName());
        }
    }
}
