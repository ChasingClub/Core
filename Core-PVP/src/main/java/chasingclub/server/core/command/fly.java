package chasingclub.server.core.command;

import chasingclub.server.core.API.SetFlySpeed;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static chasingclub.server.core.Utils.Utils.PluginName;
import static org.bukkit.Bukkit.getServer;

public class fly implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (cmd.getName().equalsIgnoreCase("fly")) {
            // Check for arguments
            if (args.length == 0) {
                if (!(sender instanceof Player)) {
                    sender.sendMessage(ChatColor.RED+"Can't use this command with console.");
                    return true;
                }else{
                    if (!(sender.hasPermission("rank.staff"))) {
                        sender.sendMessage(org.bukkit.ChatColor.RED+"You Don't have permission to do that!");
                        return true;
                    }else {
                        Player p = (Player) sender;
                        if (Boolean.TRUE.equals(p.getAllowFlight())) {
                            p.setAllowFlight(false);
                            p.sendMessage(PluginName+"Flight has been "+ChatColor.RED+"disabled"+ChatColor.GRAY+".");
                            return true;
                        }
                        if (Boolean.FALSE.equals(p.getAllowFlight())) {
                            p.setAllowFlight(true);
                            p.sendMessage(PluginName+"Flight has been "+ChatColor.GREEN+"enabled"+ChatColor.GRAY+".");
                            return true;
                        }
                    }
                }
            }else if (args.length == 1) {
                Pattern pattern = Pattern.compile("[0-9]*");
                Matcher match = pattern.matcher(args[0]);
                if (!(sender.hasPermission("rank.admin"))) {
                    sender.sendMessage(org.bukkit.ChatColor.RED+"You don't have permission to do that!");
                    return true;
                }if (match.matches()){
                    Player target = getServer().getPlayer(args[0]);
                    if (target == null){
                        Player p = (Player) sender;
                        SetFlySpeed.SetSpeedForFly(p, args[0]);
                        return true;
                    }
                    if (Boolean.TRUE.equals(target.getAllowFlight())) {
                        target.setAllowFlight(false);
                        target.sendMessage(PluginName+"Flight has been "+ChatColor.RED+"disabled"+ChatColor.GRAY+" by "+ChatColor.YELLOW+sender.getName()+ChatColor.GRAY+".");
                        sender.sendMessage(PluginName+ChatColor.RED+"Disabled flight for "+ChatColor.YELLOW+target.getName());
                        return true;
                    }
                    if (Boolean.FALSE.equals(target.getAllowFlight())) {
                        target.setAllowFlight(true);
                        target.sendMessage(PluginName+"Flight has been "+ChatColor.GREEN+"enabled"+ChatColor.GRAY+" by "+ChatColor.YELLOW+sender.getName()+ChatColor.GRAY+".");
                        sender.sendMessage(PluginName+ChatColor.GREEN+"Enabled flight for "+ChatColor.YELLOW+target.getName());
                        return true;
                    }
                } else {
                    Player target = getServer().getPlayer(args[0]);
                    if (target == null){
                        sender.sendMessage(PluginName+ChatColor.RED+"Player offline");
                        return true;
                    }
                    if (Boolean.TRUE.equals(target.getAllowFlight())) {
                        target.setAllowFlight(false);
                        target.sendMessage(PluginName+"Flight has been "+ChatColor.RED+"disabled"+ChatColor.GRAY+" by "+ChatColor.YELLOW+sender.getName()+ChatColor.GRAY+".");
                        sender.sendMessage(PluginName+ChatColor.RED+"Disabled flight for "+ChatColor.YELLOW+target.getName());
                        return true;
                    }
                    if (Boolean.FALSE.equals(target.getAllowFlight())) {
                        target.setAllowFlight(true);
                        target.sendMessage(PluginName+"Flight has been "+ChatColor.GREEN+"enabled"+ChatColor.GRAY+" by "+ChatColor.YELLOW+sender.getName()+ChatColor.GRAY+".");
                        sender.sendMessage(PluginName+ChatColor.GREEN+"Enabled flight for "+ChatColor.YELLOW+target.getName());
                        return true;
                    }
                }
            }else if (args.length == 2) {
                Pattern pattern = Pattern.compile("[0-9]*");
                Matcher match = pattern.matcher(args[1]);
                if (!(sender.hasPermission("rank.admin"))) {
                    sender.sendMessage(org.bukkit.ChatColor.RED+"You don't have permission to do that!");
                    return true;
                }if (match.matches()){
                    Player target = getServer().getPlayer(args[0]);
                    Player p = (Player) sender;
                    if (target == null){
                        sender.sendMessage(PluginName+ChatColor.RED+"Player offline");
                        return true;
                    }
                    SetFlySpeed.SetSpeedForFlyTarget(p , target, args[1]);
                    return true;
                } else {
                    sender.sendMessage(PluginName+ ChatColor.RED + "Invalid usage pick between 1-10 for fly speed.");
                }
            } else {
                // Send command overview
                sender.sendMessage("/fly" + org.bukkit.ChatColor.GRAY + " - " + ChatColor.GREEN + "Enable/Disable flight.");
                sender.sendMessage("/fly <player>" + org.bukkit.ChatColor.GRAY + " - " + ChatColor.GREEN + "Enable/Disable flight player you want.");
                return true;
            }

        }
        return true;
    }
}
