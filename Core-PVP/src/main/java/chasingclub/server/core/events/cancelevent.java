package chasingclub.server.core.events;

import chasingclub.server.core.Core;
import chasingclub.server.core.Utils.Utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.PlayerInventory;
import vanish.itdragclick.api.vanish.VanishAPI;

import static chasingclub.server.core.Utils.HotbarGui.*;
import static chasingclub.server.core.Utils.HotbarGui.A;
import static chasingclub.server.core.Utils.Utils.PluginName;

public class cancelevent implements Listener {
    @EventHandler
    public void onBreak(BlockBreakEvent e){
        Player p = e.getPlayer();
        if (!(Core.build.contains(p.getName())))
            {e.setCancelled(true);
        }
    }
    @EventHandler
    public void onSplash(PotionSplashEvent e){
        Entity entity = e.getEntity();
        if(Core.spawn.contains(entity.getLocation())){
            e.setCancelled(true);
        }else{
            if (entity.getName().equals("kongekzaza")) e.setCancelled(true);
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
            Block b = e.getClickedBlock();
            Player p = e.getPlayer();
            if(b == null){return;}
            if (b.getType() == Material.SPRUCE_TRAPDOOR) {
                if (!(Core.build.contains(p.getName()))) {
                    e.setCancelled(true);
                }
            } else if (b.getType() == Material.DARK_OAK_TRAPDOOR) {
                if (!(Core.build.contains(p.getName()))) {
                    e.setCancelled(true);
                }
            }
        }
    }
    @EventHandler
    public void onSlot(InventoryClickEvent e){
        Player p = (Player) e.getWhoClicked();
        InventoryView view = e.getView();

        if(Core.spawn.contains(p.getLocation()) && p.getGameMode() != GameMode.CREATIVE && p.getGameMode() != GameMode.SPECTATOR){
            if(view.getTitle().equals(NS) || view.getTitle().equals(CS) || view.getTitle().equals(D) || view.getTitle().equals(T) || view.getTitle().equals(V) || view.getTitle().equals(A)){
                return;
            }
            e.setCancelled(true);
        }
    }
    @EventHandler
    public void onSwap(PlayerSwapHandItemsEvent e){
        Player p = e.getPlayer();
        if(Core.spawn.contains(p.getLocation()) && p.getGameMode() != GameMode.CREATIVE && p.getGameMode() != GameMode.SPECTATOR){
            e.setCancelled(true);
        }
    }
    @EventHandler
    public void onDamage(EntityDamageEvent e){
        if (!(e.getEntity() instanceof Player)){
            return;
        }
        Player p = (Player) e.getEntity();
        if(Core.spawn.contains(p.getLocation())){
            e.setCancelled(true);
        }
    }
    @EventHandler
    public void onDamageVanish(EntityDamageByEntityEvent e){
        if (!(e.getEntity() instanceof Player)){
            return;
        }
        if(!(e.getDamager() instanceof Player)){
            return;
        }
        Player p = (Player) e.getEntity();
        Player t = (Player) e.getDamager();
        if(p.getGameMode() == GameMode.CREATIVE || p.getGameMode() == GameMode.SPECTATOR){return;}
        if(VanishAPI.isInvisible(t)){
            t.sendMessage(PluginName+"You are vanished, you can't damage others.");
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
    public void onChangeBlock(EntityChangeBlockEvent e){
        if(e.getEntity() instanceof Player){
            Player p = (Player) e.getEntity();
            if (!(Core.build.contains(p.getName())) && e.getBlock().getType() != Material.BIG_DRIPLEAF) {
                e.setCancelled(true);
            }
        }else{
            e.setCancelled(true);
        }
    }
    @EventHandler
    public void onCMDWE(PlayerCommandPreprocessEvent e){
        Player p = e.getPlayer();
        String cmd = e.getMessage();
        if(cmd.contains("//") || cmd.toLowerCase().contains("/br") || cmd.toLowerCase().contains("/wea")) {
            if (!(Core.build.contains(p.getName()))) {
                p.sendMessage(Utils.noperm);
                e.setCancelled(true);
            }
        }
    }
    @EventHandler
    public void onInteractPot(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        Block blk = e.getClickedBlock();
        if(blk == null) {
            return;
        }
        if (blk.getType().name().startsWith("POTTED_") || blk.getType() == Material.FLOWER_POT) {
            if (!(Core.build.contains(p.getName()))) {
                e.setCancelled(true);
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
            if (p.getWorld() == Bukkit.getServer().getWorld("world")){
                if (Core.spawn.contains(p.getLocation())) {
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
            if (p.getWorld() == Bukkit.getServer().getWorld("world")){
                if (Core.spawn.contains(p.getLocation())) {
                    t.remove();
                }
            }
        }
    }
}
