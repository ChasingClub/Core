package core.itdragclick.Command;

import core.itdragclick.Core;
import core.itdragclick.Utils.DiscordWebhook;
import core.itdragclick.Utils.OfflineplayerAPI;
import core.itdragclick.Utils.VanishAPI;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;

import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.*;
import java.util.List;
import java.util.logging.Logger;

import static core.itdragclick.Core.PLname;
import static core.itdragclick.Utils.Database.findvanishbyuuid;
import static core.itdragclick.Utils.Utils.CheckGroupSender;

public class ReportCommand extends Command implements TabExecutor {
    public VanishAPI isVanish(String uuid) {
        try {
            return findvanishbyuuid(uuid);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public Logger logger;
    public ReportCommand(){
        super("report", "", "hacker", "hack", "hacking", "cheater", "cheating", "cheat");
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
                    String argplayer;
                    try {
                        argplayer = OfflineplayerAPI.getUUID(args[0]);
                    } catch (IOException e) {
                        Sender.sendMessage(ChatColor.RED+"Can't find that player in MojangAPI");
                        return;
                    }
                    if(argplayer == null){
                        Sender.sendMessage(ChatColor.RED+"Can't find that player in MojangAPI");
                        return;
                    }
                    if (argplayer.equals(((ProxiedPlayer) Sender).getUUID())) {
                        Sender.sendMessage(ChatColor.RED + "You can't report yourself!");
                        return;
                    }
                    Sender.sendMessage(ChatColor.RED + "/report " + args[0] + " <reason>");
                }
            } else if (args.length == 2) {
                if (!(Sender instanceof ProxiedPlayer)) {
                    Sender.sendMessage(ChatColor.RED + "Console Can't report player");
                } else {
                    String UUID;
                    String Name;
                    try {
                        UUID = OfflineplayerAPI.getUUID(args[0]);
                        Name = OfflineplayerAPI.getName(UUID);
                    } catch (IOException e) {
                        Sender.sendMessage(ChatColor.RED+"Can't find that player in MojangAPI");
                        return;
                    }
                    if(UUID == null){
                        Sender.sendMessage(ChatColor.RED+"Can't find that player in MojangAPI");
                        return;
                    }
                    if(Name == null){
                        Sender.sendMessage(ChatColor.RED+"Can't find that player in MojangAPI");
                        return;
                    }
                    if (Name.equals(Sender.getName())) {
                        Sender.sendMessage(ChatColor.RED + "You can't report yourself!");
                        return;
                    }
                    if (Core.reasons.get(args[1]) == null) {
                        Sender.sendMessage(ChatColor.RED + "/report " + args[0] + " <reason>");
                        Sender.sendMessage(ChatColor.RED + "Reason" + ChatColor.GRAY + ": " + ChatColor.YELLOW + "Cheating, Bug-Abusing, Combat-Logout, Chat-Abuse, Advertising, Others");
                        return;
                    }
                    // CHECK GROUP
                    //////////////////////////////////////////////////////
                    String group = CheckGroupSender(Sender);
                    //////////////////////////////////////////////////////
                    List<String> AdminList = new ArrayList<>();
                    AdminList.add("ItDragClick");
                    AdminList.add("RichterYT");
                    AdminList.add("Pinont_");

                    if (AdminList.contains(Name)){
                        Sender.sendMessage(ChatColor.RED + "You can't report this player.");
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
                    Sender.sendMessage(PLname + ChatColor.YELLOW + "You have reported " + ChatColor.BLUE + Name + ChatColor.YELLOW + " for " + ChatColor.GOLD + args[1] + ChatColor.YELLOW + ".");
                    String svreporter = ProxyServer.getInstance().getPlayer(Sender.getName()).getServer().getInfo().getName();
                    if (Core.WebhookEnable.equals("true")) {
                        DiscordWebhook webhook = new DiscordWebhook(Core.webhookReport);
                        webhook.addEmbed(new DiscordWebhook.EmbedObject()
                                .setTitle("Player Reported!")
                                .setAuthor("Click Here Player For Info", "https://namemc.com/profile/" + Name, "https://minotar.net/helm/" + Name + "/4096.png")
                                .addField("Time", getDate(), false)
                                .addField("Report By", Sender.getName(), false)
                                .addField("Reported", Name, false)
                                .addField("UUID Player Get Reported", UUID, false)
                                .addField("Server Reporter", svreporter, false)
                                .addField("Rank", group, false)
                                .addField("Reason", args[1], false)
                                .setFooter(String.valueOf(Time.from(Instant.now())), "")
                                .setThumbnail("https://minotar.net/armor/body/" + Name + "/4096.png")
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
                            all.sendMessage("§7■■■■■■■■■■■■■■■■■■■■■■■■■■■");
                            all.sendMessage("§8» §a§l§nReport");
                            all.sendMessage("");
                            all.sendMessage("§8» §c§lServer:§r §e" + svreporter);
                            all.sendMessage("§8» §c§lReport By:§r §e" + Sender.getName());
                            all.sendMessage("§8» §c§lReported:§r §e" + Name);
                            all.sendMessage("§8» §c§lRank:§r §e" + group);
                            all.sendMessage("§8» §c§lReason:§r §e" + args[1]);
                            all.sendMessage("");
                            all.sendMessage("§7■■■■■■■■■■■■■■■■■■■■■■■■■■■");
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
        if (args.length == 1) {
            ArrayList<String> playerNames = new ArrayList<>();
            ProxiedPlayer[] players = new ProxiedPlayer[ProxyServer.getInstance().getPlayers().size()];
            ProxyServer.getInstance().getPlayers().toArray(players);
            for (ProxiedPlayer player : players) {
                if (isVanish(player.getUniqueId().toString()) == null){
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