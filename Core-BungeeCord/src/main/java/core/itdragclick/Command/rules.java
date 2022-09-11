package core.itdragclick.Command;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.*;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;

import java.util.ArrayList;
import java.util.List;

import static core.itdragclick.Core.PLname;

public class rules extends Command implements TabExecutor {
    public rules(){
        super("rules","","rule");
    }
    @Override
    public void execute(CommandSender Sender, String[] args){
        if (args.length == 0) {
            if (!(Sender instanceof ProxiedPlayer)) {
                Sender.sendMessage(ChatColor.RED + "Console can't use this command");
            } else {
                ProxiedPlayer p = (ProxiedPlayer) Sender;
                sendrules(p);
            }
        } else {
            if (!(Sender instanceof ProxiedPlayer)) {
                Sender.sendMessage(ChatColor.RED + "Console can't use this command");
            } else {
                ProxiedPlayer p = (ProxiedPlayer) Sender;
                sendrules(p);
            }
        }
    }
    public static void sendrules(ProxiedPlayer p){
        TextComponent message = new TextComponent(PLname);
        message.setColor(net.md_5.bungee.api.ChatColor.GRAY);
        ComponentBuilder cb = new ComponentBuilder("Discord Link: ").bold(false).color(net.md_5.bungee.api.ChatColor.GRAY).append("dsc.gg/chasingclub").color(net.md_5.bungee.api.ChatColor.BLUE).underlined(true).bold(false);
        TextComponent report = new TextComponent("Click Here!");
        report.setColor(ChatColor.AQUA);
        report.setBold(false);
        report.setItalic(false);
        report.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, cb.create()));
        report.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/report [Player] [Reason]"));

        message.addExtra(report);
        p.sendMessage(ChatColor.GRAY + "=-----------[ " + ChatColor.RED+ChatColor.BOLD+ "Rules" +ChatColor.RESET+ChatColor.GRAY+" ]-----------=");
        p.sendMessage(ChatColor.WHITE+"• "+ChatColor.YELLOW+"No Unfair Advantages");
        p.sendMessage(ChatColor.GRAY+"Disallowed modifications, macros, bug abuse, exploiting, boosting, scripting, or escape from punishment.");
        p.sendMessage(ChatColor.WHITE+"• "+ChatColor.YELLOW+"No Chat Abuse");
        p.sendMessage(ChatColor.GRAY+"Abusive language, misleading information, or any kind of spam.");
        p.sendMessage(ChatColor.WHITE+"• "+ChatColor.YELLOW+"No Advertisements");
        p.sendMessage(ChatColor.GRAY+"Advertising third-party websites, servers, social media.");
        p.sendMessage(ChatColor.WHITE+"• "+ChatColor.YELLOW+"Inappropriate Content");
        p.sendMessage(ChatColor.GRAY+"Capes, skins, usernames, Guild content");
        p.sendMessage(ChatColor.WHITE+"• "+ChatColor.YELLOW+"Threats");
        p.sendMessage(ChatColor.GRAY+"Threatening to or actively doxing, DDOSing, harassing or blackmailing others, sharing personal information, or sending malicious links.");
        p.sendMessage(ChatColor.LIGHT_PURPLE+"You can use "+ChatColor.GOLD+"/report"+ChatColor.LIGHT_PURPLE+" command to report Rule Breakers.");
        p.sendMessage(ChatColor.GRAY+"=------------------------------=");
    }
    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return new ArrayList<>();
    }
}
