package core.itdragclick.Utils;

import net.md_5.bungee.api.ChatColor;

import java.util.logging.Logger;

public class logo {
    private static Logger logger;
    public static void msgconsole(String msg){
        getLogger().info(msg);
    }

    public static Logger getLogger() {
        return logger;
    }
}
