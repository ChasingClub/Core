package cc.core.events;

import org.bukkit.ChatColor;
import org.bukkit.advancement.Advancement;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class HeartUnlocker implements Listener {

    @EventHandler
    public void onPlayerJoinFirstTime(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        // if player has join the server for the first time set player heart to 10
        if (player.hasPlayedBefore() == false || player.getMaxHealth() == 20) {
            player.setMaxHealth(10);
            player.setHealth(player.getMaxHealth());
            player.sendMessage(ChatColor.YELLOW + "Welcome to the server " + ChatColor.GREEN + player.getName() + "!" + ChatColor.YELLOW + " You need to complete the task to get more hearts!");
        }
    }

    @EventHandler
    public void onUnlockAdvancement(PlayerAdvancementDoneEvent e) {
        Player player = e.getPlayer();
        Advancement advancement = e.getAdvancement();
        String advancementName = advancement.getKey().getKey();
        if (advancementName.equals("story/iron_tools")) {
            player.setMaxHealth(player.getMaxHealth() + 2);
            player.setHealth(player.getMaxHealth());
            player.sendMessage(ChatColor.YELLOW + "Task completed! " + ChatColor.LIGHT_PURPLE + "+1 Heart");
        }
    }


}
