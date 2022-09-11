package core.itdragclick.Command;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;

import java.util.ArrayList;
import java.util.List;

import static core.itdragclick.Core.PLname;

public class LobbyCommand extends Command implements TabExecutor {
    public LobbyCommand(){
        super("lobby","","l","hub");
    }
    @Override
    public void execute(CommandSender Sender, String[] args){
        if (args.length == 0) {
            if (!(Sender instanceof ProxiedPlayer)) {
                Sender.sendMessage(ChatColor.RED + "Console can't use this command");
            } else {
                ProxiedPlayer p = (ProxiedPlayer) Sender;
                if (p.getServer().getInfo().equals(ProxyServer.getInstance().getServerInfo("checkpack"))){
                    return;
                }
                if (p.getServer().getInfo().getName().equalsIgnoreCase("lobby")){
                    p.sendMessage(PLname+ChatColor.RED+"You already in Lobby.");
                    return;
                }
                p.connect(ProxyServer.getInstance().getServerInfo("lobby"));
                p.sendMessage(PLname+"You have been connected to Lobby.");
            }
        } else {
            if (!(Sender instanceof ProxiedPlayer)) {
                Sender.sendMessage(ChatColor.RED + "Console can't use this command");
            } else {
                ProxiedPlayer p = (ProxiedPlayer) Sender;
                if (p.getServer().getInfo().equals(ProxyServer.getInstance().getServerInfo("checkpack"))){
                    return;
                }
                if (p.getServer().getInfo().getName().equalsIgnoreCase("lobby")){
                    p.sendMessage(PLname+ChatColor.RED+"You already in Lobby.");
                    return;
                }
                p.connect(ProxyServer.getInstance().getServerInfo("lobby"));
                p.sendMessage(PLname+"You have been connected to Lobby.");
            }
        }
    }
    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return new ArrayList<>();
    }
}
