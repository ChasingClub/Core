package chasingclub.server.core.command;

import chasingclub.server.core.Core;
import chasingclub.server.core.Utils.HotbarGui;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static chasingclub.server.core.Utils.Utils.PluginName;

public class invslot implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String commandLabel, String[] args) {
        if(sender instanceof Player){
            if (!Core.spawn.contains(((Player) sender).getLocation())) {
                sender.sendMessage(PluginName + ChatColor.RED + "You need to be in the spawn region to use this command!");
                return true;
            }
            if(args.length == 0){
                sender.sendMessage(PluginName + "/invslot [Kit] - Set your inventory slot");
                return true;
            }else if(args.length == 1) {
                if(args[0].equalsIgnoreCase("default")) {
                    ((Player) sender).openInventory(HotbarGui.GUIDefault((Player) sender));
                }
                if(args[0].equalsIgnoreCase("trident")) {
                    ((Player) sender).openInventory(HotbarGui.GUITrident((Player) sender));
                }
                if(args[0].equalsIgnoreCase("viking")) {
                    ((Player) sender).openInventory(HotbarGui.GUIViking((Player) sender));
                }
                if(args[0].equalsIgnoreCase("archer")) {
                    ((Player) sender).openInventory(HotbarGui.GUIArcher((Player) sender));
                }
                if(args[0].equalsIgnoreCase("classicsword")) {
                    ((Player) sender).openInventory(HotbarGui.GUIClassicSword((Player) sender));
                }
                if(args[0].equalsIgnoreCase("netheritestack")) {
                    ((Player) sender).openInventory(HotbarGui.GUINetherite((Player) sender));
                }
            }else{
                sender.sendMessage(PluginName + ChatColor.RED + "Wrong arguments, please try again!");
                return true;
            }
        }
        return true;
    }
}
