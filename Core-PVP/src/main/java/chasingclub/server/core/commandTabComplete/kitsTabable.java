package chasingclub.server.core.commandTabComplete;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class kitsTabable implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args) {
        if (args.length == 1) {
            ArrayList<String> playerNames = new ArrayList<>();
            Player[] players = new Player[Bukkit.getServer().getOnlinePlayers().size()];
            Bukkit.getServer().getOnlinePlayers().toArray(players);
            for (int i = 0; i < players.length; i++) {
                playerNames.add(players[i].getName());
            }

            return playerNames;
        } else if (args.length == 2) {
            List<String> argruments = new ArrayList<>();
            argruments.add("Netherite");
            argruments.add("Diamond");
            argruments.add("Gold");
            argruments.add("Iron");
            argruments.add("Leather");
            argruments.add("Chainmail");

            return argruments;
        }
        return null;
    }

}
