package core.itdragclick.Command;

import core.itdragclick.Core;
import core.itdragclick.Utils.VanishAPI;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;

import java.sql.SQLException;
import java.util.Collections;
import java.util.StringJoiner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static core.itdragclick.Core.*;
import static core.itdragclick.Utils.Database.findvanishbyuuid;

public class reply extends Command implements TabExecutor {
    public VanishAPI isVanish(String uuid) {
        try {
            return findvanishbyuuid(uuid);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public reply(){
        super("reply","","r");
    }
    @Override
    public void execute(CommandSender sender, String[] args){
        if (sender instanceof ProxiedPlayer) {
            if (args.length == 0) {
                sender.sendMessage(PLname + "/reply <message> - Send message to latest player you message");
            } else {
                ProxiedPlayer target = ProxyServer.getInstance().getPlayer(lastmsg.get(sender.getName()));
                if (Core.msgcooldowns.get(sender.getName()) != null) {
                    Integer time = Core.msgcooldowns.get(sender.getName());
                    sender.sendMessage(ChatColor.RED + "You need to wait for " + time + " seconds to use this command.");
                    return;
                }
                if (target == null) {
                    sender.sendMessage(PLname + ChatColor.RED + "That player is offline.");
                    return;
                }
                if (isVanish(target.getUniqueId().toString()) != null && !sender.hasPermission("msg.vanish")){
                    sender.sendMessage(PLname+ChatColor.RED+"That player is offline");
                    return;
                }
                if (sender.getName().equals(target.getName())) {
                    sender.sendMessage(PLname + ChatColor.RED + "You can't message yourself.");
                    return;
                }
                StringJoiner msg = new StringJoiner(" ");
                for (String arg : args) msg.add(arg);
                if (Core.msgcooldowns.get(sender.getName()) == null) {
                    sender.sendMessage(ChatColor.AQUA + "To " + ChatColor.GRAY + target.getName() + ChatColor.WHITE + ":" + ChatColor.GRAY + " " + msg);
                    target.sendMessage(ChatColor.AQUA + "From " + ChatColor.GRAY + sender.getName() + ChatColor.WHITE + ":" + ChatColor.GRAY + " " + msg);
                    for (ProxiedPlayer ap : ProxyServer.getInstance().getPlayers()){
                        if (spylist.contains(ap.getName())){
                            ap.sendMessage(ChatColor.AQUA + "Spy "+ChatColor.DARK_GRAY+"» " + ChatColor.GRAY + sender.getName() + ChatColor.YELLOW + "->" + ChatColor.GRAY + target.getName()+ ChatColor.WHITE + ":"+ ChatColor.GRAY + " " + msg);
                        }
                    }
                    lastmsg.put(sender.getName(), target.getName());
                    lastmsg.put(target.getName(), sender.getName());
                    if (!sender.hasPermission("message.bypass")) {
                        Core.msgcooldowns.put(sender.getName(), 3);
                    }
                }
            }
        } else {
            sender.sendMessage(PLname+ChatColor.RED+"You are console and can't have last player message");
        }
    }
    @Override
    public java.util.List<String> onTabComplete(CommandSender sender, String[] args) {
        return Collections.emptyList();
    }
}
