package core.itdragclick.Command.guild;

import core.itdragclick.Pack.GuildAPI;
import core.itdragclick.Pack.UserAPI;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static core.itdragclick.Command.guild.SubCommand.Create.createguild;
import static core.itdragclick.Command.guild.SubCommand.Invite.guildinvite;
import static core.itdragclick.Command.guild.SubCommand.ShowHelp.showhelp;
import static core.itdragclick.Core.PLname;
import static core.itdragclick.Database.*;

public class guild extends Command implements TabExecutor {
    public GuildAPI getguild(String name) throws SQLException {
        return findguildbyname(name);
    }
    public UserAPI getuser(String uuid){
        try {
            return finduserbyuuid(uuid);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public guild(){
        super("guild", "", "g");
    }
    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof ProxiedPlayer){
            // PLAYER
            ProxiedPlayer p = (ProxiedPlayer) sender;
            UserAPI user = getuser(p.getUUID());
            if (args.length == 0){
                showhelp(p);
            }else if (args.length == 1){
                if (args[0].equalsIgnoreCase("help")){
                    showhelp(p);
                }else if (args[0].equalsIgnoreCase("create")) {
                    p.sendMessage(ChatColor.RED+"/guild create <name>");
                }else if (args[0].equalsIgnoreCase("disband")) {
                    p.sendMessage(ChatColor.RED+"/guild disband <your guild name>" + ChatColor.GRAY + " - "+ChatColor.AQUA+"To confirm you want to disband it");
                }else{
                    p.sendMessage(ChatColor.RED+"Invalid usage, you can use "+ChatColor.AQUA+"/guild help"+ChatColor.RED+" for list of commands.");
                }
            }else if (args.length == 2){
                 if (args[0].equalsIgnoreCase("create")) {
                     String name;
                     if (args[1].getBytes().length <= 12){
                         if (args[1].getBytes().length >= 3){
                             name = args[1];
                             String checkname = args[1].toLowerCase();
                             if (checkname.contains("fuck") || checkname.contains("damn") || checkname.contains("nigga") || checkname.contains("gay") || checkname.contains("bitch") || checkname.contains("shit") || checkname.contains("null") || checkname.contains("dick") || checkname.contains("pussy") || checkname.contains("ass") || checkname.contains("admin")){
                                 p.sendMessage(PLname+ChatColor.RED+"You can't create the guild with that name!");
                                 return;
                             }
                             try {
                                 if (user.getguild().equals("null")) {
                                     if (getguild(name) == null) {
                                         if (!p.hasPermission("rank.premium")){
                                             p.sendMessage(PLname+ChatColor.RED+"You need to be Premium Rank or higher to create the guild!");
                                         }else {
                                             createguild(p, name);
                                         }
                                     } else {
                                         p.sendMessage(PLname + ChatColor.RED + "This name is already in use!");
                                     }
                                 }else{
                                     p.sendMessage(PLname + ChatColor.RED + "You already in the guild!");
                                 }
                             } catch (SQLException e) {
                                 throw new RuntimeException(e);
                             }
                         }else{
                             p.sendMessage(PLname+ChatColor.RED+"Too few characters, limit is 3");
                         }
                     }else{
                         p.sendMessage(PLname+ChatColor.RED+"Too much characters, limit is 12");
                     }
                }else if (args[0].equalsIgnoreCase("disband")) {
                     String name = args[1];
                         try {
                             if (getguild(name) != null) {
                                 if (user.getguild().equals("null")){
                                     p.sendMessage(PLname+ChatColor.RED+"You are not in the guild!");
                                     return;
                                 }
                                 if (!(user.getguild().equals(name))){
                                     p.sendMessage(PLname+ChatColor.RED+"You are not in that guild!");
                                     return;
                                 }
                                 if (!user.getrank().equals("owner")){
                                     p.sendMessage(PLname+ChatColor.RED+"You are not the owner of the guild!");
                                     return;
                                 }
                                 for (ProxiedPlayer ap : ProxyServer.getInstance().getPlayers()){
                                     UserAPI up = getuser(ap.getUUID());
                                     if (up.getguild().equals(name)) {
                                         if (!up.getrank().equals("owner")) {
                                             ap.sendMessage(PLname + ChatColor.YELLOW + "Your guild has been disbanded by owner!");
                                         }
                                         removeuser(ap);
                                     }
                                 }
                                 removeguild(name);
                                 p.sendMessage(PLname+ ChatColor.GREEN+"Guild has been disbanded!");
                             }else{
                                 p.sendMessage(PLname+ ChatColor.RED+"Guild not found!");
                             }
                         } catch (SQLException e) {
                             throw new RuntimeException(e);
                         }
                }else if (args[0].equalsIgnoreCase("invite")) {
                     ProxiedPlayer target = ProxyServer.getInstance().getPlayer(args[1]);
                     if (target == null){
                         p.sendMessage(PLname+ChatColor.RED+"That player is offline");
                         return;
                     }
                        guildinvite(p, target);
                }else{
                    p.sendMessage(ChatColor.RED+"Invalid usage, you can use "+ChatColor.AQUA+"/guild help"+ChatColor.RED+" for list of commands.");
                }
            }else{
                p.sendMessage(ChatColor.RED+"Invalid usage, you can use "+ChatColor.AQUA+"/guild help"+ChatColor.RED+" for list of commands.");
            }
        }else{
            // CONSOLE
            sender.sendMessage("Console can't use this command.");
        }
    }
    @Override
    public java.util.List<String> onTabComplete(CommandSender sender, String[] args) {
        if (args.length == 1) {
            List<String> argruments = new ArrayList<>();
            argruments.add("accept");
            argruments.add("create");
            argruments.add("disband");
            argruments.add("help");
            argruments.add("invite");
            argruments.add("list");

            return argruments;
        }
        return new ArrayList<>();
    }
}
