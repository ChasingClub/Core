package core.itdragclick.hook;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PlaceholderExpansion extends me.clip.placeholderapi.expansion.PlaceholderExpansion {

    @Override
    public @NotNull String getIdentifier(){
        return "ChasingClub";
    }

    @Override
    public @NotNull String getAuthor(){
        return "ItDragClick";
    }

    @Override
    public @NotNull String getVersion(){
        return "1.5.6";
    }

    @Override
    public boolean canRegister(){
        return true;
    }

    @Override
    public boolean persist(){
        return true;
    }

    @Override
    public String onRequest(OfflinePlayer p, @NotNull String params){
        if(p != null && p.isOnline()){
            return onPlaceholderRequest(p.getPlayer(), params);
        }
        return null;
    }

    public static String server_pvp = "Offline";
    public static String server_bedwars = "Offline";

    @Override
    public String onPlaceholderRequest(Player p, @NotNull String params){
        if(p == null){
            return "";
        }
        if(params.equals("server_pvp")){
            return server_pvp;
        }
        if(params.equals("server_bedwars")){
            return server_bedwars;
        }
        return null;
    }
}
