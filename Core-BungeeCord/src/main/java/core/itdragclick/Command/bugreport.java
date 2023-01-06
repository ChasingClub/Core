package core.itdragclick.Command;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.sql.SQLException;
import java.util.StringJoiner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static core.itdragclick.Core.PLname;
import static core.itdragclick.Core.bugreportcooldowns;
import static core.itdragclick.Utils.Database.addBugs;
import static core.itdragclick.Utils.Utils.noperm;

public class bugreport extends Command {
    public bugreport(){
        super("bugreport","","reportbug","bug","bug-report","report-bug");
    }
    @Override
    public void execute(CommandSender sender, String[] args){
        if(sender instanceof ProxiedPlayer){
            ProxiedPlayer p = (ProxiedPlayer) sender;
            if(p.hasPermission("rank.owner") ||
                    p.hasPermission("rank.admin") ||
                    p.hasPermission("rank.beta") ||
                    p.hasPermission("rank.social") ||
                    p.hasPermission("rank.contributor") ||
                    p.hasPermission("rank.legend") ||
                    p.hasPermission("rank.mod") ||
                    p.hasPermission("rank.builder") ||
                    p.hasPermission("rank.staff")
            ){
                if(args.length == 0){
                    // Helps
                    p.sendMessage(PLname + "/bug-report <message> - Send the bug to admin");
                }else{
                    // Put all after
                    if(bugreportcooldowns.containsKey(p.getName()) && !p.hasPermission("rank.owner")){
                        Integer time = bugreportcooldowns.get(p.getName());
                        sender.sendMessage(ChatColor.RED + "You need to wait for " + time + " seconds to use this command.");
                        return;
                    }
                    StringJoiner msg = new StringJoiner(" ");
                    for (String arg : args) msg.add(arg);
                    String message = msg.toString();
                    Pattern pattern = Pattern.compile("[a-zA-Z0-9ก-ฮ่้๊๋ไโใๆฯ๑-๙ูุึืิ์ัะาีำ.เแํฺ!?:;'\" ()<>_+=,]*");
                    Matcher match = pattern.matcher(message);
                    int length = message.length();
                    if(!match.matches() && !p.hasPermission("rank.owner")){
                        p.sendMessage(PLname+ ChatColor.RED+"You can't use that character!");
                        return;
                    }
                    if(length < 6){
                        sender.sendMessage(PLname + "You use too few character!");
                        return;
                    }
                    if(length > 256){
                        sender.sendMessage(PLname + "You use too much character! The maximum is 256.");
                        return;
                    }
                    try {
                        addBugs(p, message);
                    } catch (SQLException e) {
                        e.printStackTrace();
                        p.sendMessage(ChatColor.RED+"Error: Something went wrong, please try again later.");
                        return;
                    }
                    p.sendMessage(PLname+ChatColor.GREEN+"Thanks for reporting us a bugs.");
                    alertadmin(p.getName());
                    if(!p.hasPermission("rank.owner")) {
                        bugreportcooldowns.put(p.getName(), 60);
                    }
                }
            }else{
                p.sendMessage(noperm+" You need to have rank Legend or higher.");
            }
        }else{
            sender.sendMessage(noperm);
        }
    }
    public void alertadmin(String sender){
        for(ProxiedPlayer p : ProxyServer.getInstance().getPlayers()){
            if(p.hasPermission("rank.mod")){
                p.sendMessage(PLname+sender+" has reported bug!");
                p.sendMessage(PLname+"You can use /bugadmin list "+sender+" for lookup.");
            }
        }
    }
}
