package core.itdragclick.Commands;

import core.itdragclick.Core;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static org.bukkit.Bukkit.getServer;

public class build implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (cmd.getName().equalsIgnoreCase("b") || cmd.getName().equalsIgnoreCase("build")) {
            // Check for arguments
            if (args.length == 0) {
                if (!(sender instanceof Player)) {
                    sender.sendMessage(ChatColor.RED+"Can't use this command with console.");
                    return true;
                }else{
                    if (!(sender.hasPermission("rank.builder"))) {
                        sender.sendMessage(org.bukkit.ChatColor.RED+"You Don't have permission to do that!");
                        return true;
                    }else {
                        Player p = (Player) sender;
                        if (!(Core.build.contains(p.getName()))) {
                            Core.build.add(p.getName());
                            p.sendMessage(Core.PLname+"Build has been "+ChatColor.GREEN+"enabled"+ChatColor.GRAY+".");
                            return true;
                        } else if (Core.build.contains(p.getName())) {
                            Core.build.remove(p.getName());
                            p.sendMessage(Core.PLname+"Build has been "+ChatColor.RED+"disabled"+ChatColor.GRAY+".");
                            return true;
                        }
                    }
                }
            }else if (args.length == 1) {
                    if (!(sender.hasPermission("rank.owner"))) {
                        sender.sendMessage(org.bukkit.ChatColor.RED+"You Don't have permission to do that!");
                        return true;
                    }else {
                        Player target = getServer().getPlayer(args[0]);
                        if (target == null){
                            sender.sendMessage("Player offline");
                            return true;
                        }
                        if (!(Core.build.contains(target.getName()))) {
                            Core.build.add(target.getName());
                            target.sendMessage(Core.PLname+"Build has been "+ChatColor.GREEN+"enabled"+ChatColor.GRAY+" by "+ChatColor.YELLOW+sender.getName()+ChatColor.GRAY+".");
                            sender.sendMessage(Core.PLname+ChatColor.GREEN+"Enabled build for "+ChatColor.YELLOW+target.getName());
                            return true;
                        } else if (Core.build.contains(target.getName())) {
                            Core.build.remove(target.getName());
                            target.sendMessage(Core.PLname+"Build has been "+ChatColor.RED+"disabled"+ChatColor.GRAY+" by "+ChatColor.YELLOW+sender.getName()+ChatColor.GRAY+".");
                            sender.sendMessage(Core.PLname+ChatColor.RED+"Disabled build for "+ChatColor.YELLOW+target.getName());
                            return true;
                        }
                    }
            } else {
                // Send command overview
                sender.sendMessage("/build" + org.bukkit.ChatColor.GRAY + " - " + ChatColor.GREEN + "Enable/Disable build mode.");
                sender.sendMessage("/build <player>" + org.bukkit.ChatColor.GRAY + " - " + ChatColor.GREEN + "Enable/Disable build mode player you want.");
                return true;
            }

        }
        return true;
    }
}
