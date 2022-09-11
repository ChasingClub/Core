package core.itdragclick.events;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

public class JoinMessage implements Listener {
    @EventHandler
    public void Joinmsg(PlayerJoinEvent event)
    {
        Player player = event.getPlayer();
        event.setJoinMessage(ChatColor.GREEN + "[+] " + ChatColor.GRAY + player.getName());
        if (!player.hasPlayedBefore()){
            event.setJoinMessage(null);
            ByteArrayOutputStream b = new ByteArrayOutputStream();
            DataOutputStream out = new DataOutputStream(b);
            try {
                out.writeUTF("MessageRaw");
                out.writeUTF("ALL");
                out.writeUTF("{\"text\":\"[+] \",\"bold\":true,\"color\":\"green\"},{\"text\":\""+player.getName()+" \",\"bold\":true,\"color\":\"gray\"},{\"text\":\"joined for the first time!\",\"bold\":true,\"color\":\"aqua\"}");
            } catch(Exception e) {
                e.printStackTrace();
            }
            player.sendPluginMessage((Plugin) this, "BungeeCord", b.toByteArray());
        }
    }

    @EventHandler
    public void Leavemsg(PlayerQuitEvent event)
    {
        Player player = event.getPlayer();
        event.setQuitMessage(ChatColor.RED + "[-] "+ChatColor.GRAY + player.getName());
    }
}
