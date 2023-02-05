package core.itdragclick;

import core.itdragclick.Command.*;
import core.itdragclick.Command.maintenance.maintenance;
import core.itdragclick.Command.whitelist.whitelist;
import core.itdragclick.Utils.Database;
import core.itdragclick.Utils.DiscordWebhook;
import core.itdragclick.event.*;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;

import java.sql.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Core extends Plugin {
    // DATABASE
    ////////////////////////////////////////
    public static String ip = "panel.chasingclub.net:3306";
    ////////////////////////////////////////
    public static String database2 = "s2_Core1";
    public static String username2 = "u2_JFwIGUAzID";
    public static String password2 = "BDU+42R!@VtqI+h3DrPiUIXt";
    ////////////////////////////////////////
    public static String database3 = "s2_Core2";
    public static String username3 = "u2_P2XfIFQhJm";
    public static String password3 = "8K.AYhQ=P^oCy3Jsjb^vhKYM";
    ////////////////////////////////////////
    public static String domain = "chasingclub.net";
    public static HashMap<String, String> reasons = new HashMap<>();
    public static HashMap<String ,Boolean> whitelist = new HashMap<>();
    public static ArrayList<String> spylist = new ArrayList<>();
    public static HashMap<String ,Boolean> maintenance = new HashMap<>();
    public static HashMap<String, String> blockletters = new HashMap<>();
    public static HashMap<String, String> lastmsg = new HashMap<>();
    public static HashMap<String, String> invitebyguild = new HashMap<>();
    public static String WebhookEnable = "true";
    public static String PLname = (ChatColor.YELLOW + "C" + ChatColor.LIGHT_PURPLE + "C" + ChatColor.DARK_GRAY + " » "+ChatColor.GRAY);
    public static String PL = (ChatColor.YELLOW + "Chasing" + ChatColor.LIGHT_PURPLE + "Club" + " " +ChatColor.GRAY);
    public static String whitelistkickmsg = PL+"\n\n"+ChatColor.RED+"You are not whitelist on the server.\n\n"+ChatColor.GRAY+"Contact Staff in discord\n"+ChatColor.BLUE+ChatColor.BOLD+"Discord "+ChatColor.DARK_GRAY+"» "+ChatColor.AQUA+ChatColor.UNDERLINE+"https://dsc.gg/chasingclub";
    public static String maintenancekickmsg = PL+"\n\n"+ChatColor.RED+"The server is in maintenance mode.\n\n"+ChatColor.GRAY+"Contact Staff in discord\n"+ChatColor.BLUE+ChatColor.BOLD+"Discord "+ChatColor.DARK_GRAY+"» "+ChatColor.AQUA+ChatColor.UNDERLINE+"https://dsc.gg/chasingclub";
    public static String wrongdomainkick = PL+"\n\n"+ChatColor.RED+"You join with an unavailable domain.\n"+ChatColor.RED+"Please join with "+ChatColor.YELLOW+domain+ChatColor.RED+".\n\n"+ChatColor.GRAY+"Contact Staff in discord\n"+ChatColor.BLUE+ChatColor.BOLD+"Discord "+ChatColor.DARK_GRAY+"» "+ChatColor.AQUA+ChatColor.UNDERLINE+"https://dsc.gg/chasingclub";
    public static HashMap<String, Integer> reportcooldowns;
    public static HashMap<String, Integer> bugreportcooldowns;
    public static HashMap<String, Integer> msgcooldowns;
    public static String webhookURL = "https://discord.com/api/webhooks/1071599523002069003/kC45Zo8GbO_jVqOpYBn4zD4iq8hpwCVXEzcQfWXAEgbwcau16OUB1X_bOmLbkvI2io2g";
    public static String webhookReport = "https://discord.com/api/webhooks/1071599523002069003/kC45Zo8GbO_jVqOpYBn4zD4iq8hpwCVXEzcQfWXAEgbwcau16OUB1X_bOmLbkvI2io2g";
    public static void reload(){
        onPing.onIcon();
    }
    public void msgconsole(String msg){
        getLogger().info(msg);
    }
    //// Enable Plugin
    @Override
    public void onEnable() {
        // Set TimeZone
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Bangkok"));
        // RELOAD
        reload();
        // Register Commands
        getProxy().getPluginManager().registerCommand(this, new ReportCommand());
        getProxy().getPluginManager().registerCommand(this, new LobbyCommand());
//        getProxy().getPluginManager().registerCommand(this, new guild());
        getProxy().getPluginManager().registerCommand(this, new msg());
        getProxy().getPluginManager().registerCommand(this, new reply());
        getProxy().getPluginManager().registerCommand(this, new rules());
        getProxy().getPluginManager().registerCommand(this, new whitelist());
        getProxy().getPluginManager().registerCommand(this, new maintenance());
        getProxy().getPluginManager().registerCommand(this, new hash());
        getProxy().getPluginManager().registerCommand(this, new bugadmin());
        getProxy().getPluginManager().registerCommand(this, new spy());
        getProxy().getPluginManager().registerCommand(this, new bugreport());
        getProxy().getPluginManager().registerCommand(this, new discord());
//        getProxy().getPluginManager().registerCommand(this, new fakeplugin());
        // Register Events
//        getProxy().getPluginManager().registerListener(this, new onTab());
        getProxy().getPluginManager().registerListener(this, new onJoin());
        getProxy().getPluginManager().registerListener(this, new onLeave());
        getProxy().getPluginManager().registerListener(this, new onKick());
        getProxy().getPluginManager().registerListener(this, new onPing());
        // Put Custom Reasons
        blockletters.put("'", "1");
        char c1 = '\u0022';
        blockletters.put(String.valueOf(c1), "2");
        whitelist.put("Whitelist", true);
        maintenance.put("Maintenance", false);
        reasons.put("Cheating", "1");reasons.put("Bug-Abusing", "2");reasons.put("Combat-Logout", "3");reasons.put("Chat-Abuse", "4");reasons.put("Advertising", "5");reasons.put("Threats", "6");reasons.put("Others", "7");
        DiscordWebhook webhook = new DiscordWebhook(webhookURL);
        webhook.addEmbed(new DiscordWebhook.EmbedObject()
                .setDescription("Server Started.")
                .addField("Time", getDate(), false)
                .addField("Server", "ProxyServer", false)
                .setColor(Color.GREEN)
        );
        try {
            webhook.execute();
        }catch (java.io.IOException e){
            getLogger().severe(Arrays.toString(e.getStackTrace()));
        }
        sendlogo();
        msgconsole(PLname+ChatColor.GREEN+"has been loaded!");
        Database database = new Database();
        try {
            database.initializeDatabase2();
            database.playerdatatable();
            database.initializeDatabase3();
            msgconsole(PLname+"database is loading....\n\n");
            msgconsole(ChatColor.GREEN+"███╗░░░███╗██╗░░░██╗░██████╗░██████╗░██╗░░░░░");
            msgconsole(ChatColor.GREEN+"████╗░████║╚██╗░██╔╝██╔════╝██╔═══██╗██║░░░░░");
            msgconsole(ChatColor.GREEN+"██╔████╔██║░╚████╔╝░╚█████╗░██║██╗██║██║░░░░░");
            msgconsole(ChatColor.GREEN+"██║╚██╔╝██║░░╚██╔╝░░░╚═══██╗╚██████╔╝██║░░░░░");
            msgconsole(ChatColor.GREEN+"██║░╚═╝░██║░░░██║░░░██████╔╝░╚═██╔═╝░███████╗");
            msgconsole(ChatColor.GREEN+"╚═╝░░░░░╚═╝░░░╚═╝░░░╚═════╝░░░░╚═╝░░░╚══════╝");
            msgconsole(ChatColor.YELLOW+"=----------------------------------=");
            msgconsole(PLname+"Connecting to database.");
            msgconsole(ChatColor.YELLOW+"=----------------------------------=");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(PLname+ChatColor.RED+"Could not initialize database.");
        }
        msgconsole(PLname+"Connected to database.");
        reportcooldowns = new HashMap<>();
        msgcooldowns = new HashMap<>();
        bugreportcooldowns = new HashMap<>();
        getProxy().getScheduler().schedule(this, () -> {
            onDelay();
            onDelay2();
            onDelay3();
            for (ProxiedPlayer a : ProxyServer.getInstance().getPlayers()) {
                if ((a.hasPermission("report.bypass"))) {
                    reportcooldowns.remove(a.getName());
                }
            }
        }, 0, 1, TimeUnit.SECONDS);
        // CHECK THE MYSQL
        getProxy().getScheduler().schedule(this, () -> {
            try {
                database.getConnection3();
                database.getConnection2();
                database.getConnectiondata();
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println(PLname+ChatColor.RED+"Could not initialize database.");
            }
        }, 0, 60, TimeUnit.MINUTES);
    }
    //// Disable Plugin
    @Override
    public void onDisable() {
        DiscordWebhook webhook = new DiscordWebhook(webhookURL);
        webhook.addEmbed(new DiscordWebhook.EmbedObject()
                .setDescription("Server Stopped.")
                .addField("Time", getDate(), false)
                .addField("Server", "ProxyServer", false)
                .setColor(Color.GRAY)
        );
        try {
            webhook.execute();
        }catch (java.io.IOException e){
            getLogger().severe(Arrays.toString(e.getStackTrace()));
        }
        sendlogo();
        msgconsole(PLname+"has been disabled!");
    }
    public String getDate(){
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Bangkok"));
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        return sdf.format(cal.getTime());
    }
    // onDelay
    public void onDelay(){
        HashMap<String, Integer> temp = new HashMap<>();
        for (String plrname : reportcooldowns.keySet()){
            int timer = reportcooldowns.get(plrname) - 1;
            if (timer >= 0){
                temp.put(plrname, timer);
            }
        }
        reportcooldowns = temp;
    }
    public void onDelay2(){
        HashMap<String, Integer> temp = new HashMap<>();
        for (String plrname : msgcooldowns.keySet()){
            int timer = msgcooldowns.get(plrname) - 1;
            if (timer >= 0){
                temp.put(plrname, timer);
            }
        }
        msgcooldowns = temp;
    }
    public void onDelay3(){
        HashMap<String, Integer> temp = new HashMap<>();
        for (String plrname : bugreportcooldowns.keySet()){
            int timer = bugreportcooldowns.get(plrname) - 1;
            if (timer >= 0){
                temp.put(plrname, timer);
            }
        }
        bugreportcooldowns = temp;
    }
    public void sendlogo(){
        msgconsole("\n");
        msgconsole(ChatColor.YELLOW+"░█████╗░██╗░░██╗░█████╗░░██████╗██╗███╗░░██╗░██████╗░" );
        msgconsole(ChatColor.YELLOW+"██╔══██╗██║░░██║██╔══██╗██╔════╝██║████╗░██║██╔════╝░" );
        msgconsole(ChatColor.YELLOW+"██║░░╚═╝███████║███████║╚█████╗░██║██╔██╗██║██║░░██╗░" );
        msgconsole(ChatColor.YELLOW+"██║░░██╗██╔══██║██╔══██║░╚═══██╗██║██║╚████║██║░░╚██╗" );
        msgconsole(ChatColor.YELLOW+"╚█████╔╝██║░░██║██║░░██║██████╔╝██║██║░╚███║╚██████╔╝" );
        msgconsole(ChatColor.YELLOW+"░╚════╝░╚═╝░░╚═╝╚═╝░░╚═╝╚═════╝░╚═╝╚═╝░░╚══╝░╚═════╝░");
        msgconsole(ChatColor.LIGHT_PURPLE+"          ░█████╗░██╗░░░░░██╗░░░██╗██████╗░" );
        msgconsole(ChatColor.LIGHT_PURPLE+"          ██╔══██╗██║░░░░░██║░░░██║██╔══██╗");
        msgconsole(ChatColor.LIGHT_PURPLE+"          ██║░░╚═╝██║░░░░░██║░░░██║██████╦╝");
        msgconsole(ChatColor.LIGHT_PURPLE+"          ██║░░██╗██║░░░░░██║░░░██║██╔══██╗");
        msgconsole(ChatColor.LIGHT_PURPLE+"          ╚█████╔╝███████╗╚██████╔╝██████╦╝");
        msgconsole(ChatColor.LIGHT_PURPLE+"          ░╚════╝░╚══════╝░╚═════╝░╚═════╝░");
        msgconsole("\n");
    }
}
