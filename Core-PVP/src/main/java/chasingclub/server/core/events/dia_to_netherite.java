package chasingclub.server.core.events;

import chasingclub.server.core.Core;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;

import java.sql.SQLException;
import java.util.Objects;

import static chasingclub.server.core.Utils.Database.UpdateDuel;
import static chasingclub.server.core.Utils.Utils.PluginName;
import static chasingclub.server.core.Utils.Utils.error;

public class dia_to_netherite implements Listener {
    public Core plugin;
    public dia_to_netherite(Core plugin){
        this.plugin = plugin;
    }
    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        Player target = event.getPlayer();
        Player killer = target.getKiller();
        if (killer != null) {
            if (Core.ingame.get(target.getName()) != null && target.getWorld().getName().equals("Netherite_game")) { // Newwwww check world to check HashMap <-----------------------------
                killer.teleport(Objects.requireNonNull(killer.getBedSpawnLocation()));
                try {
                    UpdateDuel(target, "deaths");
                    UpdateDuel(killer, "kills");
                } catch (SQLException e) {
                    e.printStackTrace();
                    target.sendMessage(error+" Please report staff");
                    killer.sendMessage(error+" Please report staff");
                }
                if (target.getInventory().contains(Material.NETHERITE_AXE)) {
                    target.getInventory().clear();
                    killer.getInventory().clear();
                    target.sendMessage(PluginName + "Nice Try! you has been defeated by " + killer.getName());
                    killer.sendMessage(PluginName + "GG! you have defeated " + target.getName());
                    if (Core.playerinmap.get(killer.getName()).equals("Colosseum")){
                        Core.maps.put("Colosseum", true);
                    }else if (Core.playerinmap.get(killer.getName()).equals("Beach")){
                        Core.maps.put("Beach", true);
                    }else if (Core.playerinmap.get(killer.getName()).equals("Abyss")){
                        Core.maps.put("Abyss", true);
                    }
                    try {
                        UpdateDuel(target, "loses");
                        UpdateDuel(killer, "wins");
                    } catch (SQLException e) {
                        e.printStackTrace();
                        target.sendMessage(error+" Please report staff");
                        killer.sendMessage(error+" Please report staff");
                    }
                    target.setGameMode(GameMode.ADVENTURE);
                    killer.setGameMode(GameMode.ADVENTURE);
                    event.setRespawnLocation(plugin.spawnloc);
                    killer.teleport(plugin.spawnloc);
                    Core.ingame.remove(target.getName());
                    Core.ingame.remove(killer.getName());
                    Core.GetKitSelect(target);
                    Core.GetKitSelect(killer);
                    Core.playerinmap.remove(killer.getName());
                    Core.playerinmap.remove(target.getName());
                    Core.combatList.put(killer.getName(), 0);
                    Core.combatList.put(target.getName(), 0);
                    return;
                }
                ItemStack armorHead = new ItemStack(Material.NETHERITE_HELMET);
                ItemStack armorBoots = new ItemStack(Material.NETHERITE_BOOTS);
                ItemStack armorLegs = new ItemStack(Material.NETHERITE_LEGGINGS);
                ItemStack armorChest = new ItemStack(Material.NETHERITE_CHESTPLATE);
                if (target.getInventory().getHelmet() == null) {
                    return;
                }
    //            if ((target.getWorld().getName()).equals("Netherite_game")) { // if player respawn at Void_World
                if (target.getInventory().getHelmet() != null && (target.getInventory().getHelmet().getType()).equals(Material.DIAMOND_HELMET)) {
                    target.getInventory().setHelmet(armorHead); // repalce target helmet after respawn
                } else if (target.getInventory().getHelmet() != null && (Objects.requireNonNull(target.getInventory().getBoots()).getType()).equals(Material.DIAMOND_BOOTS) && target.getInventory().getBoots() != null) {
                    target.getInventory().setBoots(armorBoots);
                } else if (target.getInventory().getHelmet() != null && (Objects.requireNonNull(target.getInventory().getLeggings()).getType()).equals(Material.DIAMOND_LEGGINGS) && target.getInventory().getLeggings() != null) {
                    target.getInventory().setLeggings(armorLegs);
                } else if (target.getInventory().getHelmet() != null && (Objects.requireNonNull(target.getInventory().getChestplate()).getType()).equals(Material.DIAMOND_CHESTPLATE) && target.getInventory().getChestplate() != null) {
                    target.getInventory().setChestplate(armorChest);
                } else if (target.getInventory().contains(Material.DIAMOND_SWORD)) { // if target has diamond sword
                    for (int i = 0; i < target.getInventory().getSize(); i++) { // check the target slot
                        ItemStack item = target.getInventory().getItem(i); // get target slot
                        if (item != null && item.getType().equals(Material.DIAMOND_SWORD)) {
                            item.setType(Material.NETHERITE_SWORD); // replace target slot
                        }
                    }
                } else if (target.getInventory().contains(Material.DIAMOND_AXE)) {
                    for (int i = 0; i < target.getInventory().getSize(); i++) {
                        ItemStack item = target.getInventory().getItem(i);
                        if (item != null && item.getType().equals(Material.DIAMOND_AXE)) {
                            item.setType(Material.NETHERITE_AXE);
                        }
                    }
                }
            }
        }
    }
}
