package core.itdragclick;

import com.google.common.base.Joiner;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;
import java.util.Optional;

import static org.bukkit.Bukkit.getServer;


public class testcmd1 implements CommandExecutor {
    private Plugin plugin;
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("test")) {
            if (args.length <= 1) {
                sendErrorMessage(sender, "Wrong command, Missing arguments");
                return false;
            }

            String channelPlayer = args[0];

            Optional<? extends Player> optPlayer = Bukkit.getOnlinePlayers().stream().findAny();
            if (!optPlayer.isPresent()) {
                sendErrorMessage(sender, "No player is online to forward this command");
                return true;
            }

            Player messageSender = optPlayer.get();

            ByteArrayDataOutput dataOutput = ByteStreams.newDataOutput();
            dataOutput.writeBoolean(false);
            if (sender instanceof Player && !sender.getName().equalsIgnoreCase(channelPlayer)) {
                sendErrorMessage(sender, "Permissions.ERROR_MESSAGE");
                return true;
            }

            if (getServer().getPlayer(channelPlayer) == null) {
                sendErrorMessage(sender, "Player '" + channelPlayer + "' not found");
                return true;
            }

            dataOutput.writeBoolean(true);
            messageSender = getServer().getPlayer(channelPlayer);

            dataOutput.writeUTF(args[1]);
            dataOutput.writeUTF(Joiner.on(' ').join(Arrays.copyOfRange(args, 2, args.length)));
            dataOutput.writeBoolean(sender.isOp());
            dataOutput.writeBoolean(sender.isOp());
            assert messageSender != null;
//            messageSender.sendPluginMessage(this, "BungeeCord", dataOutput.toByteArray());

        }
        return true;
    }
    private void sendErrorMessage(CommandSender sender, String message) {
        sender.sendMessage(Core.PLname + ChatColor.RED + message);
    }
}
