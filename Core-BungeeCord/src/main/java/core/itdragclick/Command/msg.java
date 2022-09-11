package core.itdragclick.Command;

import core.itdragclick.Core;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.StringJoiner;

import static core.itdragclick.Core.*;

public class msg extends Command implements TabExecutor {
    public msg(){
        super("msg","","w","message","minecraft:tell","minecraft:w","minecraft:msg");
    }
    @Override
    public void execute(CommandSender sender, String[] args){
        if (args.length == 0) {
            sender.sendMessage(PLname+"/message <player> <message> - Send message to player you want");
            return;
        }if (args.length == 1) {
            ProxiedPlayer target = ProxyServer.getInstance().getPlayer(args[0]);
            if (target == null){
                sender.sendMessage(PLname+ChatColor.RED+"That player is offline");
                return;
            }
            if (sender.getName().equals(target.getName())){
                sender.sendMessage(PLname+ChatColor.RED+"You can't message yourself.");
                return;
            }
            sender.sendMessage(PLname+"/message "+target.getName()+" <message>");
        } else {
            ProxiedPlayer target = ProxyServer.getInstance().getPlayer(args[0]);
            if (Core.msgcooldowns.get(sender.getName()) != null) {
                Integer time = Core.msgcooldowns.get(sender.getName());
                sender.sendMessage(ChatColor.RED + "You need to wait for " + time + " seconds to use this command.");
                return;
            }
            if (target == null){
                sender.sendMessage(PLname+ChatColor.RED+"That player is offline.");
                return;
            }
            if (sender.getName().equals(target.getName())){
                sender.sendMessage(PLname+ChatColor.RED+"You can't message yourself.");
                return;
            }
            StringJoiner msg = new StringJoiner(" ");
            for(int i = 1; i < args.length; i++)
                msg.add(args[i]);
            if (Core.msgcooldowns.get(sender.getName()) == null) {
                sender.sendMessage(ChatColor.AQUA+"To "+ChatColor.GRAY+target.getName()+ChatColor.WHITE+":"+ChatColor.GRAY+" "+msg);
                if (sender instanceof ProxiedPlayer){
                    target.sendMessage(ChatColor.AQUA+"From "+ChatColor.GRAY+sender.getName()+ChatColor.WHITE+":"+ChatColor.GRAY+" "+msg);
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
                } else {
                    target.sendMessage(ChatColor.AQUA+"From "+ChatColor.YELLOW+"CONSOLE"+ChatColor.WHITE+":"+ChatColor.GRAY+" "+msg);
                }
            }
        }
    }
    @Override
    public java.util.List<String> onTabComplete(CommandSender sender, String[] args) {
        ProxiedPlayer p = (ProxiedPlayer) sender;
        if (args.length == 1) {
            ArrayList<String> playerNames = new ArrayList<>();
            ProxiedPlayer[] players = new ProxiedPlayer[ProxyServer.getInstance().getPlayers().size()];
            ProxyServer.getInstance().getPlayers().toArray(players);
            for (ProxiedPlayer player : players) {
                if (!(player.getServer().getInfo().getName().equals(p.getServer().getInfo().getName()))) {
                    playerNames.add(player.getName());
                }
            }

            return playerNames;
        }
        return Collections.emptyList();
    }
}
