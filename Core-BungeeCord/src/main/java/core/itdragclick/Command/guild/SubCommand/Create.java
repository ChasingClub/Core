package core.itdragclick.Command.guild.SubCommand;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static core.itdragclick.Core.PLname;
import static core.itdragclick.Database.*;

public class Create {
    public static void createguild(ProxiedPlayer p, String name){
        Pattern pattern = Pattern.compile("[a-zA-Z0-9]*");
        Matcher match = pattern.matcher(name);

        if (!match.matches()) {
            p.sendMessage(PLname+ChatColor.RED+"You can't create a guild with a symbol!");
            return;
        }
        try {
            adduserowner(name, p);
            addguild(name, p);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        p.sendMessage(PLname+ ChatColor.GREEN+"Guild has been created!");
    }
}
