package chasingclub.server.core.hook;

import chasingclub.server.core.Core;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static chasingclub.server.core.Core.ingame;

public class PlaceholderExpansion extends me.clip.placeholderapi.expansion.PlaceholderExpansion {

    @Override
    public String getIdentifier(){
        return "ChasingClub";
    }

    @Override
    public String getAuthor(){
        return "ItDragClick";
    }

    @Override
    public String getVersion(){
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
    public String onPlaceholderRequest(Player p, @NotNull String params){
        if(p == null){
            return "";
        }
        if(params.equals("kit")){
            return Core.kits.get(p);
        }
        if(params.equals("target")){
            return ingame.getOrDefault(p.getName(), "Unknown");
        }
        return null;
    }
}
