package chasingclub.server.core.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class fade implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, @NotNull Command cmd, @NotNull String commandLabel, String[] args) {
        if(sender.hasPermission("rank.owner")){
            if(sender instanceof Player){
                String fade = "\uEF00";
                Player p = ((Player) sender).getPlayer();
                if (p != null) {
                    p.sendTitle(fade, null, 20, 40, 20);
                }
            }
        }
        return true;
    }
}
