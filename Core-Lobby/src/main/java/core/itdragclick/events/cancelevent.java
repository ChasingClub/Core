package core.itdragclick.events;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerTakeLecternBookEvent;

import static core.itdragclick.Core.build;

public class cancelevent implements Listener {
    @EventHandler
    public void onDamage(EntityDamageEvent e){
        if (e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();
            if(p.getWorld() == Bukkit.getWorld("Lobby")){
                e.setCancelled(true);
            }
        }
    }
    @EventHandler
    public void onThrownPotion(PotionSplashEvent e){
        SplashPotion potion = (SplashPotion) e.getEntity();
        Player p = (Player) potion.getShooter();
        if (p == null){
            e.setCancelled(true);
            return;
        }
        if(p.getWorld() == Bukkit.getWorld("Lobby")){
            e.setCancelled(true);
        }
    }
    @EventHandler
    public void onPotion(EntityPotionEffectEvent e){
        if (e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();
            if(p.getWorld() == Bukkit.getWorld("Lobby")){
                e.setCancelled(true);
            }
        }
    }
    @EventHandler
    public void onBreak(BlockBreakEvent e){
        Player p = e.getPlayer();
        if (!(build.contains(p.getName()))){
            e.setCancelled(true);
        }
    }
    @EventHandler
    public void onPlace(BlockPlaceEvent e){
        Player p = e.getPlayer();
        if (!(build.contains(p.getName()))) {
            e.setCancelled(true);
        }
    }
    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Material b = e.getClickedBlock().getType();
            Player p = e.getPlayer();
            if (b == Material.SPRUCE_TRAPDOOR) {
                if (!(build.contains(p.getName()))) {
                    e.setCancelled(true);
                }
            } else if (b == Material.DARK_OAK_TRAPDOOR) {
                if (!(build.contains(p.getName()))) {
                    e.setCancelled(true);
                }
            }
        }
    }
    @EventHandler
    public void onTakenBookFromLectern(PlayerTakeLecternBookEvent e){
        Player p = e.getPlayer();
        if (!(build.contains(p.getName()))) {
            e.setCancelled(true);
        }
    }
    @EventHandler
    public void onEntityExplode(EntityExplodeEvent e){
        e.setCancelled(true);
    }
    @EventHandler
    public void FrameEntity(EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof ItemFrame || e.getEntity() instanceof GlowItemFrame) {
            if (e.getDamager() instanceof Player) {
                Player pd = (Player) e.getDamager();
                if (!(build.contains(pd.getName()))) {
                    e.setCancelled(true);
                }
            }
            if (e.getDamager() instanceof Projectile) {
                if (((Projectile) e.getDamager()).getShooter() instanceof Player) {
                    Player pp = (Player) ((Projectile) e.getDamager()).getShooter();
                    if (!(build.contains(pp.getName()))) {
                        e.getDamager().remove();
                        e.setCancelled(true);
                    }
                }
            }
        }
    }
    @EventHandler
    public void FrameEntity(HangingBreakByEntityEvent e) {
        Player p = (Player) e.getRemover();
        if (p == null){return;}
        if (!(e.getEntity() instanceof ItemFrame || e.getEntity() instanceof Painting)) {
            return;
        }
        if (!(e.getRemover() instanceof Player || e.getRemover() instanceof Projectile)) {
            return;
        }
        if (e.getRemover() instanceof Projectile) {
            if (!(((Projectile) e.getRemover()).getShooter() instanceof Player)) {
                return;
            }
        }
        if (!(build.contains(p.getName()))) {
            e.setCancelled(true);
        }
    }
    @EventHandler
    public void FrameRotate(PlayerInteractEntityEvent e) {
        Entity check = e.getRightClicked();
        if (check.getType() == EntityType.ITEM_FRAME || check.getType() == EntityType.GLOW_ITEM_FRAME) {
            if (!e.isCancelled()
                    && e.getRightClicked() instanceof ItemFrame
                    || e.getRightClicked() instanceof GlowItemFrame
                    && !((ItemFrame) e.getRightClicked()).getItem().getType().equals(Material.AIR)
                    || !((GlowItemFrame) e.getRightClicked()).getItem().getType().equals(Material.AIR)) {
                if (!(build.contains(e.getPlayer().getName()))) {
                    e.setCancelled(true);
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
            if (!(build.contains(p.getName()))) {
                e.setCancelled(true);
            }
        }
    }
    @EventHandler
    public void onIgniteTNT(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        Block blk = e.getClickedBlock();
        if(blk == null) {
            return;
        }else if (blk.getType().name().startsWith("TNT") || blk.getType() == Material.TNT) {
            if (!(build.contains(p.getName()))) {
                e.setCancelled(true);
            }
        }
    }
}
