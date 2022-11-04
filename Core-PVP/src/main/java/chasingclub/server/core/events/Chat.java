package chasingclub.server.core.events;

import chasingclub.server.core.Core;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static chasingclub.server.core.Utils.Utils.PluginName;

public class Chat implements Listener {
    @EventHandler
    public void onSendMessage(AsyncPlayerChatEvent e){
        String msg = e.getMessage();
        Player p = e.getPlayer();
        Pattern pattern = Pattern.compile("[a-zA-Z0-9ก-ฮ่้๊๋ไโใๆฯ๑-๙ูุึืิ์ัะาีำ.เแํฺ!@#$?:;'\"\\\\^& */\\[\\]()<>_+=,{}%-]*");
        Matcher match = pattern.matcher(msg);
        if(!match.matches() && !p.hasPermission("rank.owner")){
            p.sendMessage(PluginName+ ChatColor.RED+"You can't send message with that character!");
            e.setCancelled(true);
            return;
        }
        String[] split = msg.split(" ");
        for (final Player ap : Bukkit.getOnlinePlayers()) {
            if (ap != e.getPlayer()) {
                if(Arrays.asList(split).contains(ap.getName())) {
                    ap.playSound(ap.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 10000, 2);
                    msg = msg.replaceAll(ap.getName(), ChatColor.YELLOW + ap.getName() + ChatColor.RESET);
                    ap.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.YELLOW+p.getName()+ChatColor.GREEN+" mention you!"));
                }
            }
        }
        msg = msg.replaceAll("%","%%");
        if(p.hasPermission("rank.emoji")){
            msg = msg.replaceAll("<3",ChatColor.RED+"❤"+ChatColor.RESET);
        }
        if(p.hasPermission("rank.emoji")){
            msg = msg.replaceAll("\\\\o/",ChatColor.LIGHT_PURPLE+"\\\\(ﾟ◡ﾟ)/"+ChatColor.RESET);
        }
        if(p.hasPermission("rank.emoji")){
            msg = msg.replaceAll("o/",ChatColor.LIGHT_PURPLE+"( ﾟ◡ﾟ)/"+ChatColor.RESET);
        }
        if(p.hasPermission("rank.emoji")){
            msg = msg.replaceAll("\\\\o",ChatColor.LIGHT_PURPLE+"\\\\(ﾟ◡ﾟ )"+ChatColor.RESET);
        }
        if (Core.chatcooldowns.containsKey(p.getName())){
            e.setCancelled(true);
            p.sendMessage(PluginName+ChatColor.RED+"Please wait "+ Core.chatcooldowns.get(p.getName())+" second before send message.");
            return;
        }
        e.setMessage(msg);
        if(p.hasPermission("pf.default")){
            e.setFormat(ChatColor.RESET+"\uE014 "+ChatColor.GRAY+p.getName()+ChatColor.DARK_GRAY+" » "+ChatColor.RESET+msg);
        }if(p.hasPermission("pf.vip")){
            e.setFormat(ChatColor.RESET+"\uE013 "+ ChatColor.DARK_AQUA +p.getName()+ChatColor.DARK_GRAY+" » "+ChatColor.RESET+msg);
        }if(p.hasPermission("pf.premium")){
            e.setFormat(ChatColor.RESET+"\uE010 "+ChatColor.LIGHT_PURPLE+p.getName()+ChatColor.DARK_GRAY+" » "+ChatColor.RESET+msg);
        }if(p.hasPermission("pf.legend")){
            e.setFormat(ChatColor.RESET+"\uE008 "+ChatColor.GOLD+ChatColor.BOLD+p.getName()+ChatColor.DARK_GRAY+" » "+ChatColor.RESET+msg);
        }if(p.hasPermission("pf.beta")){
            e.setFormat(ChatColor.RESET+"\uE005 "+ChatColor.DARK_PURPLE+p.getName()+ChatColor.DARK_GRAY+" » "+ChatColor.RESET+msg);
        }if(p.hasPermission("pf.social")){
            e.setFormat(ChatColor.RESET+"\uE006 "+ChatColor.RED+p.getName()+ChatColor.DARK_GRAY+" » "+ChatColor.RESET+msg);
        }if(p.hasPermission("pf.contributor")){
            e.setFormat(ChatColor.RESET+"\uE007 "+ChatColor.GOLD+ChatColor.BOLD+p.getName()+ChatColor.DARK_GRAY+" » "+ChatColor.RESET+msg);
        }if(p.hasPermission("pf.staff")){
            e.setFormat(ChatColor.RESET+"\uE004 "+ChatColor.GREEN+ChatColor.BOLD+p.getName()+ChatColor.DARK_GRAY+" » "+ChatColor.RESET+msg);
        }if(p.hasPermission("pf.builder")){
            e.setFormat(ChatColor.RESET+"\uE003 "+ChatColor.DARK_GREEN+ChatColor.BOLD+p.getName()+ChatColor.DARK_GRAY+" » "+ChatColor.RESET+msg);
        }if(p.hasPermission("pf.mod")){
            e.setFormat(ChatColor.RESET+"\uE002 "+ChatColor.BLUE+ChatColor.BOLD+p.getName()+ChatColor.DARK_GRAY+" » "+ChatColor.RESET+msg);
        }if(p.hasPermission("pf.admin")){
            e.setFormat(ChatColor.RESET+"\uE001 "+ChatColor.RED+ChatColor.BOLD+p.getName()+ChatColor.DARK_GRAY+" » "+ChatColor.RESET+msg);
        }if(p.hasPermission("pf.owner")){
            e.setFormat(ChatColor.RESET+"\uE000 "+ChatColor.AQUA+ChatColor.BOLD+p.getName()+ChatColor.DARK_GRAY+" » "+ChatColor.RESET+msg);
        }

        if (!p.hasPermission("rank.premium")) {
            Core.chatcooldowns.put(p.getName(), 3);
        }
    }
    @EventHandler
    public void onSendCmd(PlayerCommandPreprocessEvent e){
        Player p = e.getPlayer();

        if (Core.cmdcooldowns.containsKey(p.getName())){
            e.setCancelled(true);
            p.sendMessage(PluginName+ChatColor.RED+"Please wait "+ Core.cmdcooldowns.get(p.getName())+" second before send command.");
            return;
        }
        if (!p.hasPermission("rank.premium")) {
            Core.cmdcooldowns.put(p.getName(), 3);
        }
    }
}
