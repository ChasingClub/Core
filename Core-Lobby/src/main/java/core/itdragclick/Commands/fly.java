package core.itdragclick.Commands;

import core.itdragclick.API.SetFlySpeed;
import core.itdragclick.Core;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static core.itdragclick.API.SetFlySpeed.SetSpeedForFly;
import static core.itdragclick.API.SetFlySpeed.SetSpeedForFlyTarget;
import static core.itdragclick.Core.PLname;
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
                    if (!(sender.hasPermission("rank.vip"))) {
                        sender.sendMessage(org.bukkit.ChatColor.RED+"You don't have permission to do that!");
                        return true;
                    }else {
                        Player p = (Player) sender;
                        if (Boolean.TRUE.equals(p.getAllowFlight())) {
                            p.setAllowFlight(false);
                            p.sendMessage(Core.PLname+ChatColor.RED+"Turn off flight");
                            return true;
                        }
                        if (Boolean.FALSE.equals(p.getAllowFlight())) {
                            p.setAllowFlight(true);
                            p.sendMessage(Core.PLname+ChatColor.GREEN+"Turn on flight");
                            return true;
                        }
                    }
                }
            }else if (args.length == 1) {
                Pattern pattern = Pattern.compile("[0-9]*");
                Matcher match = pattern.matcher(args[0]);
                if (sender.hasPermission("rank.staff")) {
                    if (match.matches()){
                        Player target = getServer().getPlayer(args[0]);
                        if (target == null){
                            Player p = (Player) sender;
                            SetSpeedForFly(p, args[0]);
                            return true;
                        }
                        if (Boolean.TRUE.equals(target.getAllowFlight())) {
                            target.setAllowFlight(false);
                            target.sendMessage(Core.PLname+"Flight has been "+ChatColor.RED+"disabled"+ChatColor.GRAY+" by "+ChatColor.YELLOW+sender.getName()+ChatColor.GRAY+".");
                            sender.sendMessage(Core.PLname+ChatColor.RED+"Disabled flight for "+ChatColor.YELLOW+target.getName());
                            return true;
                        }
                        if (Boolean.FALSE.equals(target.getAllowFlight())) {
                            target.setAllowFlight(true);
                            target.sendMessage(Core.PLname+"Flight has been "+ChatColor.GREEN+"enabled"+ChatColor.GRAY+" by "+ChatColor.YELLOW+sender.getName()+ChatColor.GRAY+".");
                            sender.sendMessage(Core.PLname+ChatColor.GREEN+"Enabled flight for "+ChatColor.YELLOW+target.getName());
                            return true;
                        }
                    } else {
                        Player target = getServer().getPlayer(args[0]);
                        if (target == null){
                            sender.sendMessage(PLname+ChatColor.RED+"Player offline");
                            return true;
                        }
                        if (Boolean.TRUE.equals(target.getAllowFlight())) {
                            target.setAllowFlight(false);
                            target.sendMessage(Core.PLname+"Flight has been "+ChatColor.RED+"disabled"+ChatColor.GRAY+" by "+ChatColor.YELLOW+sender.getName()+ChatColor.GRAY+".");
                            sender.sendMessage(Core.PLname+ChatColor.RED+"Disabled flight for "+ChatColor.YELLOW+target.getName());
                            return true;
                        }
                        if (Boolean.FALSE.equals(target.getAllowFlight())) {
                            target.setAllowFlight(true);
                            target.sendMessage(Core.PLname+"Flight has been "+ChatColor.GREEN+"enabled"+ChatColor.GRAY+" by "+ChatColor.YELLOW+sender.getName()+ChatColor.GRAY+".");
                            sender.sendMessage(Core.PLname+ChatColor.GREEN+"Enabled flight for "+ChatColor.YELLOW+target.getName());
                            return true;
                        }
                    }
                }else if (sender.hasPermission("rank.vip")) {
                    Player p = (Player) sender;
                    if (Boolean.TRUE.equals(p.getAllowFlight())) {
                        p.setAllowFlight(false);
                        p.sendMessage(Core.PLname+ChatColor.RED+"Turn off flight");
                        return true;
                    }
                    if (Boolean.FALSE.equals(p.getAllowFlight())) {
                        p.setAllowFlight(true);
                        p.sendMessage(Core.PLname+ChatColor.GREEN+"Turn on flight");
                        return true;
                    }
                }else{
                    sender.sendMessage(org.bukkit.ChatColor.RED+"You don't have permission to do that!");
                    return true;
                }
            }else if (args.length == 2) {
                Pattern pattern = Pattern.compile("[0-9]*");
                Matcher match = pattern.matcher(args[1]);
                if (sender.hasPermission("rank.staff")) {
                    if (match.matches()) {
                        Player target = getServer().getPlayer(args[0]);
                        Player p = (Player) sender;
                        if (target == null) {
                            sender.sendMessage(PLname + ChatColor.RED + "Player offline");
                            return true;
                        }
                        SetSpeedForFlyTarget(p, target, args[1]);
                        return true;
                    } else {
                        sender.sendMessage(PLname + ChatColor.RED + "Invalid usage pick between 1-10 for fly speed.");
                    }
                }else if (sender.hasPermission("rank.vip")) {
                    Player p = (Player) sender;
                    if (Boolean.TRUE.equals(p.getAllowFlight())) {
                        p.setAllowFlight(false);
                        p.sendMessage(Core.PLname+ChatColor.RED+"Turn off flight");
                        return true;
                    }
                    if (Boolean.FALSE.equals(p.getAllowFlight())) {
                        p.setAllowFlight(true);
                        p.sendMessage(Core.PLname+ChatColor.GREEN+"Turn on flight");
                        return true;
                    }
                }else{
                    sender.sendMessage(org.bukkit.ChatColor.RED+"You don't have permission to do that!");
                    return true;
                }
            } else {
                if (sender.hasPermission("rank.staff")) {
                    // Send command overview
                    sender.sendMessage("/fly" + org.bukkit.ChatColor.GRAY + " - " + ChatColor.GREEN + "Enable/Disable flight.");
                    sender.sendMessage("/fly <player>" + org.bukkit.ChatColor.GRAY + " - " + ChatColor.GREEN + "Enable/Disable flight player you want.");
                    return true;
                }
                if (!sender.hasPermission("rank.vip")){
                    sender.sendMessage(org.bukkit.ChatColor.RED+"You don't have permission to do that!");
                    return true;
                }else{
                    Player p = (Player) sender;
                    if (Boolean.TRUE.equals(p.getAllowFlight())) {
                        p.setAllowFlight(false);
                        p.sendMessage(Core.PLname+ChatColor.RED+"Turn off flight");
                        return true;
                    }
                    if (Boolean.FALSE.equals(p.getAllowFlight())) {
                        p.setAllowFlight(true);
                        p.sendMessage(Core.PLname+ChatColor.GREEN+"Turn on flight");
                        return true;
                    }
                }
            }

        }
        return true;
    }
}
