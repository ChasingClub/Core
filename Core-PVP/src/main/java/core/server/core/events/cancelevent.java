package core.server.core.events;

import core.server.core.Core;
import static core.server.core.Core.spawn;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import core.server.core.Cuboid;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;

public class cancelevent implements Listener {
    @EventHandler
    public void onBreak(BlockBreakEvent e){
        Player p = e.getPlayer();
        if (!(Core.build.contains(p.getName())))
            {e.setCancelled(true);
        }
    }
    @EventHandler
    public void onPlace(BlockPlaceEvent e){
        Player p = e.getPlayer();
        if (!(Core.build.contains(p.getName()))) {
            e.setCancelled(true);
        }
    }
    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Material b = e.getClickedBlock().getType();
            Player p = e.getPlayer();
            if (b == Material.SPRUCE_TRAPDOOR) {
                if (!(Core.build.contains(p.getName()))) {
                    e.setCancelled(true);
                    return;
                }
            } else if (b == Material.DARK_OAK_TRAPDOOR) {
                if (!(Core.build.contains(p.getName()))) {
                    e.setCancelled(true);
                    return;
                }
            }
        }
    }
    @EventHandler
    public void onSlot(InventoryClickEvent e){
        Player p = (Player) e.getWhoClicked();
        if(spawn.contains(p.getLocation()) && p.getGameMode() != GameMode.CREATIVE && p.getGameMode() != GameMode.SPECTATOR){
            e.setCancelled(true);
        }
    }
    @EventHandler
    public void onSwap(PlayerSwapHandItemsEvent e){
        Player p = e.getPlayer();
        if(spawn.contains(p.getLocation()) && p.getGameMode() != GameMode.CREATIVE && p.getGameMode() != GameMode.SPECTATOR){
            e.setCancelled(true);
        }
    }
    @EventHandler
    public void onDamage(EntityDamageEvent e){
        Player p = (Player) e.getEntity();
        if(spawn.contains(p.getLocation())){
            e.setCancelled(true);
        }
    }
    @EventHandler
    public void onEntityExplode(EntityExplodeEvent e){
        e.setCancelled(true);
    }
    @EventHandler
    public void FrameEntity(EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof ItemFrame) {
            if (e.getDamager() instanceof Player) {
                Player pd = (Player) e.getDamager();
                if (!(Core.build.contains(pd.getName()))) {
                    e.setCancelled(true);
                }
            }
            if (e.getDamager() instanceof Projectile) {
                if (((Projectile) e.getDamager()).getShooter() instanceof Player) {
                    Player pp = (Player) ((Projectile) e.getDamager()).getShooter();
                    if (!(Core.build.contains(pp.getName()))) {
                        e.getDamager().remove();
                        e.setCancelled(true);
                    }
                }
            }
        }
    }
    @EventHandler
    public void onInteractPot(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        Block blk = e.getClickedBlock();
        if(blk == null) {
            return;
        }else if (blk.getType().name().startsWith("POTTED_") || blk.getType() == Material.FLOWER_POT) {
            if (!(Core.build.contains(p.getName()))) {
                e.setCancelled(true);
                return;
            }
        }
    }
    @EventHandler
    public void Arrow(ProjectileHitEvent e) {
        if (e.getEntity() instanceof Arrow) {
            Arrow a = (Arrow) e.getEntity();
            a.remove();
        }
    }
    @EventHandler
    public void Trident(ProjectileLaunchEvent e){
        if (e.getEntity() instanceof Trident) {
            Player p = (Player) e.getEntity().getShooter();
            if (p == null){return;}
            Cuboid cuboid = new Cuboid(Bukkit.getServer().getWorld("world"), 160, 50, -66, -24, 2, 121);
          if (!(cuboid.contains(p.getLocation()))) {
              if (p.getWorld() == Bukkit.getServer().getWorld("world")){
                  e.setCancelled(true);
              }
          }
        }
    }
    @EventHandler
    public void TridentHit(ProjectileHitEvent e){
        if (e.getEntity() instanceof Trident) {
            Trident t = (Trident) e.getEntity();
            Player p = (Player) e.getEntity().getShooter();
            if (p == null){t.remove();return;}
            Cuboid cuboid = new Cuboid(Bukkit.getServer().getWorld("world"), 160, 50, -66, -24, 2, 121);
            if (!(cuboid.contains(p.getLocation()))) {
                if (p.getWorld() == Bukkit.getServer().getWorld("world")){
                    t.remove();
                }
            }
        }
    }
}
