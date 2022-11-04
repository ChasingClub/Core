package chasingclub.server.core.API;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class Prefix {
    public static void SetTeamPrefix(){
        Scoreboard sb = Bukkit.getScoreboardManager().getMainScoreboard();

        Team Owner = sb.getTeam("owner");
        if(Owner == null) {
            Owner = sb.registerNewTeam("owner");
        }
        Team Admin = sb.getTeam("admin");
        if(Admin == null) {
            Admin = sb.registerNewTeam("admin");
        }
        Team Mod = sb.getTeam("mod");
        if(Mod == null) {
            Mod = sb.registerNewTeam("mod");
        }
        Team Builder = sb.getTeam("builder");
        if(Builder == null) {
            Builder = sb.registerNewTeam("builder");
        }
        Team Staff = sb.getTeam("staff");
        if(Staff == null) {
            Staff = sb.registerNewTeam("staff");
        }
        Team Beta = sb.getTeam("beta");
        if(Beta == null) {
            Beta = sb.registerNewTeam("beta");
        }
        Team Social = sb.getTeam("social");
        if(Social == null) {
            Social = sb.registerNewTeam("social");
        }
        Team Contributor = sb.getTeam("contributor");
        if(Contributor == null) {
            Contributor = sb.registerNewTeam("contributor");
        }
        Team Legend = sb.getTeam("legend");
        if(Legend == null) {
            Legend = sb.registerNewTeam("legend");
        }
        Team Premium = sb.getTeam("premium");
        if(Premium == null) {
            Premium = sb.registerNewTeam("premium");
        }
        Team VIP = sb.getTeam("vip");
        if(VIP == null) {
            VIP = sb.registerNewTeam("vip");
        }
        Team Default = sb.getTeam("default");
        if(Default == null) {
            Default = sb.registerNewTeam("default");
        }
        Owner.setPrefix("\uE100 ");
        Admin.setPrefix("\uE101 ");
        Mod.setPrefix("\uE102 ");
        Builder.setPrefix("\uE103 ");
        Staff.setPrefix("\uE104 ");
        Beta.setPrefix("\uE105 ");
        Social.setPrefix("\uE106 ");
        Contributor.setPrefix("\uE107 ");
        Legend.setPrefix("\uE108 ");
        Premium.setPrefix("\uE110 ");
        VIP.setPrefix("\uE113 ");
        Default.setPrefix("\uE114 ");
        for(Player all : Bukkit.getOnlinePlayers()) {
            if(all.hasPermission("pf.owner")) {
                Owner.addEntry(all.getName());
            }else if(all.hasPermission("pf.admin")) {
                Admin.addEntry(all.getName());
            }else if(all.hasPermission("pf.mod")) {
                Mod.addEntry(all.getName());
            }else if(all.hasPermission("pf.builder")) {
                Builder.addEntry(all.getName());
            }else if(all.hasPermission("pf.staff")) {
                Staff.addEntry(all.getName());
            }else if(all.hasPermission("pf.beta")) {
                Beta.addEntry(all.getName());
            }else if(all.hasPermission("pf.social")) {
                Social.addEntry(all.getName());
            }else if(all.hasPermission("pf.contributor")) {
                Contributor.addEntry(all.getName());
            }else if(all.hasPermission("pf.legend")) {
                Legend.addEntry(all.getName());
            }else if(all.hasPermission("pf.premium")) {
                Premium.addEntry(all.getName());
            }else if(all.hasPermission("pf.vip")) {
                VIP.addEntry(all.getName());
            }else{
                Default.addEntry(all.getName());
            }

        }
    }
}
