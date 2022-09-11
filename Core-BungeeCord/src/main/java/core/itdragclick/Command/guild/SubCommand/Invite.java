package core.itdragclick.Command.guild.SubCommand;

import core.itdragclick.Pack.GuildAPI;
import core.itdragclick.Pack.UserAPI;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.sql.SQLException;

import static core.itdragclick.Core.*;
import static core.itdragclick.Database.findguildbyname;
import static core.itdragclick.Database.finduserbyuuid;

public class Invite {
    public static UserAPI getuser(String uuid){
        try {
            return finduserbyuuid(uuid);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void guildinvite(ProxiedPlayer p, ProxiedPlayer target){
        UserAPI user = getuser(p.getUUID());
        UserAPI usertarget = getuser(target.getUUID());
        String guildname = user.getguild();
        if (user.getguild().equals("null")){
            p.sendMessage(PLname+ ChatColor.RED+"You are not in the guild!");
            return;
        }
        if (!usertarget.getguild().equals("null")){
            p.sendMessage(PLname+ ChatColor.RED+"That player is already in the guild!");
            return;
        }
        if (!user.getrank().equals("owner")){
            p.sendMessage(PLname+ChatColor.RED+"You are not the owner of the guild!");
            return;
        }
        if (invitebyguild.get(target.getName()) != null){
            p.sendMessage(PLname+ChatColor.RED+"You already invited this player.");
            return;
        }
        invitebyguild.put(target.getName(), p.getName());
    }
}
