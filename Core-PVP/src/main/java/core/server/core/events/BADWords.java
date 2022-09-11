package core.server.core.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class BADWords implements Listener {
    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        char Marks = '\u0022';
        Player p = e.getPlayer();
        if (!(p.hasPermission("chat.bypass"))) {
            if (e.getMessage().toLowerCase().contains("fuck")) {
                e.setMessage("I'm so stupid.");
                return;
            } else if (e.getMessage().toLowerCase().contains("pussy")) {
                e.setMessage("I'm so stupid.");
                return;
            } else if (e.getMessage().toLowerCase().contains("bitch")) {
                e.setMessage("I'm so stupid.");
                return;
            } else if (e.getMessage().toLowerCase().contains("dick")) {
                e.setMessage("I'm so stupid.");
                return;
            } else if (e.getMessage().toLowerCase().contains("shit")) {
                e.setMessage("I'm eating poop.");
                return;
            } else if (e.getMessage().toLowerCase().contains("nigga")) {
                e.setMessage("I'm nigga.");
                return;
            } else if (e.getMessage().toLowerCase().contains("gay")) {
                e.setMessage("I'm gay. :)");
                return;
            } else if (e.getMessage().toLowerCase().contains("noob")) {
                e.setMessage("I'm Noob");
                return;
            } else if (e.getMessage().toLowerCase().contains("ez")) {
                e.setMessage("WoW, You so good at this game.");
                return;
            }
        }
    }
}
