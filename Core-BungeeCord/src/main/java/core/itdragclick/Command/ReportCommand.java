package core.itdragclick.Command;

import core.itdragclick.Core;
import core.itdragclick.DiscordWebhook;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;

import java.awt.*;
import java.io.IOException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.*;
import java.util.List;
import java.util.logging.Logger;

import static core.itdragclick.Core.blockletters;

public class ReportCommand extends Command implements TabExecutor {
    public Logger logger;
    public ReportCommand(){
        super("report");
    }
    @Override
    public void execute(CommandSender Sender, String[] args) {

            if (args.length == 0) {
                if (!(Sender instanceof ProxiedPlayer)) {
                    Sender.sendMessage(ChatColor.RED + "Console Can't report player");
                } else {
                    ProxiedPlayer p = (ProxiedPlayer) Sender;
                    p.sendMessage(ChatColor.YELLOW + " Plugin help:");
                    p.sendMessage("/report <player> <reason>" + ChatColor.GRAY + " - " + ChatColor.GOLD + "Report player who break the rules.");
                }
            } else if (args.length == 1) {
                if (!(Sender instanceof ProxiedPlayer)) {
                    Sender.sendMessage(ChatColor.RED + "Console Can't report player");
                } else {
                    ProxiedPlayer argplayer = ProxyServer.getInstance().getPlayer(args[0]);
                    if (argplayer == null) {
                        Sender.sendMessage("Player " + ChatColor.GRAY + args[0] + ChatColor.RESET + " could not be found");
                        return;
                    } else if (argplayer.getName().equals(Sender.getName())) {
                        Sender.sendMessage(ChatColor.RED + "You can't report yourself!");
                        return;
                    }
                    Sender.sendMessage(ChatColor.RED + "/report " + args[0] + " <reason>");
                }
            } else if (args.length == 2) {
                if (!(Sender instanceof ProxiedPlayer)) {
                    Sender.sendMessage(ChatColor.RED + "Console Can't report player");
                } else {
                    ProxiedPlayer argplayer = ProxyServer.getInstance().getPlayer(args[0]);
                    if (argplayer == null) {
                        Sender.sendMessage("Player " + ChatColor.GRAY + args[0] + ChatColor.RESET + " could not be found");
                        return;
                    }
                    if (argplayer.getName().equals(Sender.getName())) {
                        Sender.sendMessage(ChatColor.RED + "You can't report yourself!");
                        return;
                    }
                    char Marks = '\u0022';
                    if (args[1].contains(String.valueOf(Marks))) {
                        Sender.sendMessage(ChatColor.RED + "/report " + args[0] + " <reason>");
                        Sender.sendMessage(ChatColor.RED + "Reason" + ChatColor.GRAY + ": " + ChatColor.YELLOW + "Cheating, Bug-Abusing, Combat-Logout, Chat-Abuse, Advertising, Others");
                        return;
                    }
                    if (Core.reasons.get(args[1]) == null) {
                        Sender.sendMessage(ChatColor.RED + "/report " + args[0] + " <reason>");
                        Sender.sendMessage(ChatColor.RED + "Reason" + ChatColor.GRAY + ": " + ChatColor.YELLOW + "Cheating, Bug-Abusing, Combat-Logout, Chat-Abuse, Advertising, Others");
                        return;
                    }
                    String group = null;
                    // CHECK GROUP
                    //////////////////////////////////////////////////////
                    if (Sender.hasPermission("rank.default")){
                        group = "Default";
                    }if (Sender.hasPermission("rank.vip")) {
                        group = "VIP";
                    }if (Sender.hasPermission("rank.premium")) {
                        group = "Premium";
                    }if (Sender.hasPermission("rank.legend")) {
                        group = "Legend";
                    }if (Sender.hasPermission("rank.contributor")) {
                        group = "Contributor";
                    }if (Sender.hasPermission("rank.social")) {
                        group = "Social";
                    }if (Sender.hasPermission("rank.beta")) {
                        group = "Beta Tester";
                    }if (Sender.hasPermission("rank.staff")) {
                        group = "Staff";
                    }if (Sender.hasPermission("rank.builder")) {
                        group = "Builder";
                    }if (Sender.hasPermission("rank.mod")) {
                        group = "Admins";
                    }if (Sender.hasPermission("rank.admin")) {
                        group = "Admins";
                    }if (Sender.hasPermission("rank.owner")) {
                        group = "Owner";
                    }
                    //////////////////////////////////////////////////////
                    if (argplayer.hasPermission("report.admin")){
                        ProxiedPlayer player = (ProxiedPlayer) Sender;
                        Sender.sendMessage(ChatColor.RED + "You can't report this player.");
                        if (Core.WebhookEnable.equals("true")) {
                            DiscordWebhook webhook = new DiscordWebhook(Core.webhookReport);
                            webhook.addEmbed(new DiscordWebhook.EmbedObject()
                                    .setTitle("Player Reported Admin!")
                                    .setAuthor("Click Here Player For Info", "https://namemc.com/profile/" + Sender.getName(), "https://minotar.net/helm/" + Sender.getName() + "/4096.png")
                                    .addField("Time", getDate(), false)
                                    .addField("Reporter", Sender.getName() + " / " + player.getAddress().getAddress().getHostAddress(), false)
                                    .addField("In Server", ProxyServer.getInstance().getPlayer(Sender.getName()).getServer().getInfo().getName(), false)
                                    .addField("Rank", group, false)
                                    .setFooter(String.valueOf(Time.from(Instant.now())), "")
                                    .setThumbnail("https://minotar.net/armor/body/" + Sender.getName() + "/4096.png")
                                    .setColor(Color.MAGENTA)
                            );
                            try {
                                webhook.execute();
                            } catch (IOException e) {
                                getLogger().severe(Arrays.toString(e.getStackTrace()));
                            }
                        }
                        return;
                    }
                    if (Core.reportcooldowns.get(Sender.getName()) != null) {
                        Integer time = Core.reportcooldowns.get(Sender.getName());
                        Sender.sendMessage(ChatColor.RED + "You need to wait for " + time + " seconds to use this command.");
                        return;
                    }
                    if (!(Sender.hasPermission("report.bypass"))) {
                        Core.reportcooldowns.put(Sender.getName(), 60);
                    }
                    Sender.sendMessage(Core.PLname + ChatColor.YELLOW + "You have reported " + ChatColor.BLUE + argplayer.getName() + ChatColor.YELLOW + " for " + ChatColor.GOLD + args[1] + ChatColor.YELLOW + ".");
                    ProxiedPlayer player = (ProxiedPlayer) Sender;
                    String ipreported = argplayer.getAddress().getAddress().getHostAddress();
                    String ipreportby = player.getAddress().getAddress().getHostAddress();
                    String svreporter = ProxyServer.getInstance().getPlayer(Sender.getName()).getServer().getInfo().getName();
                    String svreported = ProxyServer.getInstance().getPlayer(argplayer.getName()).getServer().getInfo().getName();
                    if (Core.WebhookEnable.equals("true")) {
                        DiscordWebhook webhook = new DiscordWebhook(Core.webhookReport);
                        webhook.addEmbed(new DiscordWebhook.EmbedObject()
                                .setTitle("Player Reported!")
                                .setAuthor("Click Here Player For Info", "https://namemc.com/profile/" + argplayer.getName(), "https://minotar.net/helm/" + argplayer.getName() + "/4096.png")
                                .addField("Time", getDate(), false)
                                .addField("Report By", Sender.getName() + " / " + ipreportby, false)
                                .addField("Reported", argplayer.getName() + " / " + ipreported, false)
                                .addField("UUID Player Get Reported", argplayer.getUUID(), false)
                                .addField("Server Reporter / Server Get Reported", svreporter + " / " + svreported, false)
                                .addField("Rank", group, false)
                                .addField("Reason", args[1], false)
                                .setFooter(String.valueOf(Time.from(Instant.now())), "")
                                .setThumbnail("https://minotar.net/armor/body/" + argplayer.getName() + "/4096.png")
                                .setColor(Color.MAGENTA)
                        );
                        try {
                            webhook.execute();
                        } catch (IOException e) {
                            getLogger().severe(Arrays.toString(e.getStackTrace()));
                        }
                    }
                    for (ProxiedPlayer all : ProxyServer.getInstance().getPlayers()) {
                        if (all.hasPermission("report.staff")) {
                            all.sendMessage("§7■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
                            all.sendMessage("§8» §a§l§nReport");
                            all.sendMessage("");
                            all.sendMessage("§8» §c§lServer:§r §e" + ProxyServer.getInstance().getPlayer(Sender.getName()).getServer().getInfo().getName());
                            all.sendMessage("§8» §c§lReport By:§r §e" + Sender.getName());
                            all.sendMessage("§8» §c§lReported:§r §e" + argplayer.getName());
                            all.sendMessage("§8» §c§lReason:§r §e" + args[1]);
                            all.sendMessage("");
                            all.sendMessage("§7■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
                        }
                    }
                }
            }else{
            Sender.sendMessage("/report <player> <reason>" + ChatColor.GRAY + " - " + ChatColor.GOLD + "Report player who break the rules.");
        }
    }
    public String getDate(){
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Bangkok"));
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        return sdf.format(cal.getTime());
    }
    public Logger getLogger() {
        return logger;
    }
    @Override
    public java.util.List<String> onTabComplete(CommandSender sender, String[] args) {
        ProxiedPlayer p = (ProxiedPlayer) sender;
        if (args.length == 1) {
            ArrayList<String> playerNames = new ArrayList<>();
            ProxiedPlayer[] players = new ProxiedPlayer[ProxyServer.getInstance().getPlayers().size()];
            ProxyServer.getInstance().getPlayers().toArray(players);
            for (ProxiedPlayer player : players) {
                if (!(player.getServer().getInfo().getName().equals(p.getServer().getInfo().getName()))) {
                    playerNames.add(player.getName());
                }
            }

            return playerNames;
        } else if (args.length == 2) {
            List<String> argruments = new ArrayList<>();
            argruments.add("Cheating");
            argruments.add("Bug-Abusing");
            argruments.add("Combat-Logout");
            argruments.add("Chat-Abuse");
            argruments.add("Advertising");
            argruments.add("Threats");
            argruments.add("Others");

            return argruments;
        }
        return Collections.emptyList();
    }
}