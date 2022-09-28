package core.itdragclick.events;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.Stairs;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.spigotmc.event.entity.EntityDismountEvent;

import static core.itdragclick.Core.sitting;

public class SitEvent implements Listener {
    @EventHandler
    public void OnClickSit(PlayerInteractEvent e){
        Block b = e.getClickedBlock();
        Player player = e.getPlayer();
        if (b == null){
            return;
        }
        Location loc = b.getLocation();
        if (b.getBlockData().getAsString().contains("half=top")){
            return;
        }
        if ((e.getAction() == Action.RIGHT_CLICK_BLOCK) &&
                (b.getType().toString().contains("STAIRS")) &&
                (loc.add(0, 1, 0).getBlock().getType() == Material.AIR) &&
                (e.getHand() == EquipmentSlot.HAND))
        {
            if (player.getLocation().distance(b.getLocation()) > 3){
                return;
            }
            if (player.isSneaking() || sitting.contains(player.getUniqueId())){
                return;
            }
        if (sitting.contains(player.getUniqueId()))
        {
            sitting.remove(player.getUniqueId());

            if (player.getVehicle() != null && player.getVehicle() instanceof ArmorStand)
            {
                Entity vehicle = player.getVehicle();
                vehicle.eject();
                vehicle.remove();
            }

            return;
        }
        if (!player.isOnGround())
        {
            player.sendMessage("§cYou need to be on the ground to be able to sit!");
            return;
        }
        Location location = b.getLocation();
        World world = location.getWorld();
        float yaw = 0f;
        if (b.getBlockData().getAsString().contains("facing=east")){
            yaw = 90f;
        }else if (b.getBlockData().getAsString().contains("facing=west")){
            yaw = -90f;
        }else if (b.getBlockData().getAsString().contains("facing=south")){
            yaw = 180f;
        }else if (b.getBlockData().getAsString().contains("facing=north")){
            yaw = 0f;
        }
        location.setYaw(yaw);

        ArmorStand chair = (ArmorStand) world.spawnEntity(location.subtract(-0.5, 1.1, -0.5), EntityType.ARMOR_STAND);
        chair.setGravity(false);
        chair.setVisible(false);
        chair.setInvulnerable(true);
        chair.addPassenger(player);

        sitting.add(player.getUniqueId());
        }
    }
    @EventHandler
    public void onDismount(EntityDismountEvent event)
    {
        Entity entity = event.getEntity();

        if (!(entity instanceof Player))
            return;

        Player player = (Player) entity;

        if (!(event.getDismounted() instanceof ArmorStand))
            return;

        if (!sitting.contains(player.getUniqueId()))
            return;

        Entity vehicle = event.getDismounted();
        vehicle.eject();
        player.teleport(player.getLocation().add(0, 1,0));
        vehicle.remove();
        sitting.remove(player.getUniqueId());
    }
}
