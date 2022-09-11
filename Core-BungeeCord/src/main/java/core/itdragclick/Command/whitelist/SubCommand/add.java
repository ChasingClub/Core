package core.itdragclick.Command.whitelist.SubCommand;

import core.itdragclick.Pack.WhitelistAPI;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;

import java.io.IOException;
import java.sql.SQLException;

import static core.itdragclick.Core.PLname;
import static core.itdragclick.Database.addwhitelist;
import static core.itdragclick.Database.findwhitelistbyuuid;
import static core.itdragclick.Pack.OfflineplayerAPI.getUUID;

public class add {

    public static WhitelistAPI getwhitelist(String uuid) throws SQLException {
        return findwhitelistbyuuid(uuid);
    }
    public static void addplayerwl(String targetname, CommandSender sender){
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
                addwhitelist(uuid, targetname);
                sender.sendMessage(PLname+ChatColor.GREEN+"The Player has been added to the whitelist.");
            }else{
                sender.sendMessage(PLname+ChatColor.RED+"That player is already on the whitelist.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
