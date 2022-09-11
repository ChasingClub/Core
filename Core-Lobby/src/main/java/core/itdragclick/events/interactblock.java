package core.itdragclick.events;

import core.itdragclick.Core;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

import static core.itdragclick.Core.PLname;

public class interactblock implements Listener {
    @EventHandler
    public void onClick(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        World w = p.getWorld();
        TextComponent message = new TextComponent(PLname);
        message.setColor(net.md_5.bungee.api.ChatColor.GRAY);
        ComponentBuilder cb = new ComponentBuilder("Discord Link: ").bold(false).color(net.md_5.bungee.api.ChatColor.GRAY).append("dsc.gg/chasingclub").color(net.md_5.bungee.api.ChatColor.BLUE).underlined(true).bold(false);
        TextComponent discord = new TextComponent("Click Here!");
        discord.setColor(ChatColor.AQUA);
        discord.setBold(true);
        discord.setItalic(true);
        discord.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, cb.create()));
        discord.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://dsc.gg/chasingclub"));

        message.addExtra(discord);
        Action act = e.getAction();
            if(!(act == Action.RIGHT_CLICK_BLOCK)) {return;}
            if (e.getClickedBlock() == null){
                return;
            }
            Material bl = e.getClickedBlock().getType();
            if(!(bl == Material.PLAYER_HEAD)) {return;}
            int bx = 96;
            int by = 102;
            int bz = 95;

            int x = e.getClickedBlock().getLocation().getBlockX();
            int y = e.getClickedBlock().getLocation().getBlockY();
            int z = e.getClickedBlock().getLocation().getBlockZ();
            if (x != bx || y != by || z != bz){
                return;
            }
            EquipmentSlot eq = e.getHand();
            if (!(eq == EquipmentSlot.HAND)) {return;}
            if(w.getBlockAt(96, 102, 95).getType() == Material.PLAYER_HEAD) {
                p.sendMessage(message);
//                p.sendMessage(PLname+ ChatColor.BLUE+ChatColor.UNDERLINE+"https://dsc.gg/chasingclub");
            }
    }
}
