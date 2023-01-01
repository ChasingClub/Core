package cc.pinont;

import com.sk89q.jnbt.CompoundTag;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.extension.platform.Capability;
import com.sk89q.worldedit.extension.platform.Platform;
import com.sk89q.worldedit.extent.clipboard.BlockArrayClipboard;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.session.ClipboardHolder;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Core extends JavaPlugin implements Listener {
    // Map to store player selections
    private Map<UUID, Location[]> playerSelections = new HashMap<>();

    @Override
    public void onEnable() {
        // Register the plugin as a listener
        getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("savearea")) {
            // Check if the sender is a player
            if (!(sender instanceof Player)) {
                sender.sendMessage("This command can only be used by a player.");
                return true;
            }

            Player player = (Player) sender;
            UUID playerId = player.getUniqueId();

            // Check if the player has a selection
            if (!playerSelections.containsKey(playerId)) {
                player.sendMessage("You have not made a selection.");
                return true;
            }

            // Save the selection to an NBT file
            Location[] selection = playerSelections.get(playerId);
            saveSelectionToNBT(selection[0], selection[1], player.getWorld());

            player.sendMessage("Selection saved to NBT file.");
            return true;
        }
        return false;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();

        // Check if the player is using a stick
        if (item == null || item.getType() != Material.STICK) {
            return;
        }

        UUID playerId = player.getUniqueId();
        Location location = event.getClickedBlock().getLocation();

        // Check if the player left clicked or right clicked
        if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
            // Set position 1 for the player's selection
            playerSelections.put(playerId, new Location[]{location, null});
            player.sendMessage("Position 1 set at " + location.getBlockX() + ", " + location.getBlockY() + ", " + location.getBlockZ());
        } else if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            // Check if the player has a position 1 set
            if (!playerSelections.containsKey(playerId) || playerSelections.get(playerId)[0] == null) {
                player.sendMessage("You must set position 1 before setting position 2.");
                return;
            }

            // Set position 2 for the player's selection
            Location[] selection = playerSelections.get(playerId);
            selection[1] = location;
            player.sendMessage("Position 2 set at " + location.getBlockX() + ", " + location.getBlockY() + ", " + location.getBlockZ());
        }

        private void saveSelectionToNBT(Location pos1, Location pos2, World world) {
            // Create an NBT compound tag to store the selection data
            CompoundTag selectionTag = new CompoundTag()
            selectionTag.putString("world", world.getName());
            selectionTag.putInt("pos1X", pos1.getBlockX());
            selectionTag.putInt("pos1Y", pos1.getBlockY());
            selectionTag.putInt("pos1Z", pos1.getBlockZ());
            selectionTag.putInt("pos2X", pos2.getBlockX());
            selectionTag.putInt("pos2Y", pos2.getBlockY());
            selectionTag.putInt("pos2Z", pos2.getBlockZ());

            // Write the NBT compound tag to a file
            File file = new File(getDataFolder(), "selection.nbt");
            try {
                NBTIO.writeTag(selectionTag, file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        }
}
