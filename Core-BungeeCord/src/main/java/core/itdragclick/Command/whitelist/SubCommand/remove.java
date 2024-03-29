package core.itdragclick.Command.whitelist.SubCommand;

import core.itdragclick.Utils.WhitelistAPI;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;

import java.io.IOException;
import java.sql.SQLException;

import static core.itdragclick.Core.PLname;
import static core.itdragclick.Utils.Database.*;
import static core.itdragclick.Utils.OfflineplayerAPI.getUUID;

public class remove {
    public static WhitelistAPI getwhitelist(String uuid) throws SQLException {
        return findwhitelistbyuuid(uuid);
    }
    public static void removeplayerwl(String targetname, CommandSender sender){
        String uuid;
        try {
            uuid = getUUID(targetname);
        } catch (IOException e) {
            e.printStackTrace();
            sender.sendMessage(PLname+ ChatColor.RED +"Could not initialize player data.");
            return;
        }
        if (uuid == null){
            sender.sendMessage(PLname+ChatColor.RED+"Could not found that player on mojang API");
            return;
        }
        try {
            if (getwhitelist(uuid) == null){
                sender.sendMessage(PLname+ChatColor.RED+"That player is not on the whitelist.");
            }else{
                removewhitelist(uuid);
                sender.sendMessage(PLname+ChatColor.GREEN+"The Player has been removed from the whitelist.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
