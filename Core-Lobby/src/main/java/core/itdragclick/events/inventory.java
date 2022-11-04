package core.itdragclick.events;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;

public class inventory {
    public static void CraftingSlot(){
        ItemStack skull = new ItemStack(Material.PLAYER_HEAD, 1, (byte)3);

        SkullMeta im = (SkullMeta) skull.getItemMeta();

        ArrayList<String> lore = new ArrayList<>();
        lore.add("§7");
        lore.add("§7test");
        im.setDisplayName("§9§lDISCORD");
        im.setLore(lore);

        for (Player p : Bukkit.getServer().getOnlinePlayers()) {
            im.setOwner(p.getName());
            skull.setItemMeta(im);
            if (p.getInventory().contains(skull)) {
                p.getInventory().removeItem(skull);
            }else if(p.getOpenInventory().getType() == InventoryType.CRAFTING){
                p.getOpenInventory().getTopInventory().setItem(1, skull);
            }
        }
    }
}
