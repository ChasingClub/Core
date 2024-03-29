package cc.core.command;

import cc.core.SMPTeam;
import cc.core.SQliteManager.SQLite;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;
import java.util.*;

import static cc.core.Core.plugin;

public class team implements CommandExecutor {
    public static HashMap<Player, Player> tpa = new HashMap();

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, String[] args) {
        SQLite Sqlite = new SQLite(plugin);
        SMPTeam smpTeam = new SMPTeam();
        // Check if commandSender is not console
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage("This command can only be used by players.");
            Bukkit.getLogger().info("smpTeam Mapping:");
            Bukkit.getLogger().info(smpTeam.getPlayerTeams().toString());
            Bukkit.getLogger().info("Sqlite Mapping:");
            Bukkit.getLogger().info(Sqlite.getPlayerTeams().toString());
            return false;
        }
        // Check if player input the command argument or not
        if (args.length == 0) {
            commandSender.sendMessage(ChatColor.RED + "Please provide a command argument.");
            return false;
        }
        Bukkit.getLogger().info("smpTeam Mapping:");
        Bukkit.getLogger().info(smpTeam.getPlayerTeams().toString());
        Bukkit.getLogger().info("Sqlite Mapping:");
        Bukkit.getLogger().info(Sqlite.getPlayerTeams().toString());

        Player player = (Player) commandSender;
        UUID uuid = player.getUniqueId();

        switch (args[0].toLowerCase()) {
            case "create":
                // Check if commandSender input the teamName in args[2] or not if not the code stop here.
                if (args.length < 2) {
                    player.sendMessage(ChatColor.RED + "Please provide a name for your team.");
                    return false;
                }
                String teamName = args[1];
                // Check if the input team is exists or not if not the code will stop here.
                if (smpTeam.teamExist(teamName)) {
                    player.sendMessage(ChatColor.RED + "Someone already use this team name!");
                    return true;
                }
                // Check if player is already in team or not if yes the code stop here.
                if (smpTeam.inTeam(player)) {
                    player.sendMessage(ChatColor.RED + "You are already in a team!");
                    return false;
                }
                // When player is not in any team and input the teamName for team creation this code will create the team.
                try {
                    Sqlite.addMember(uuid, teamName);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                player.sendMessage(ChatColor.GREEN + "You have created and joined the team '" + teamName + "'.");
                break;
            case "join":
                // Check if commandSender input the teamName they want to join or not if not the code stop here.
                if (args.length < 2) {
                    player.sendMessage(ChatColor.RED + "Please provide the name of the team you want to join.");
                    return false;
                }
                // Check if player is already in team or not if yes the code stop here.
                if (smpTeam.inTeam(player)) {
                    player.sendMessage(ChatColor.RED + "You are already in a team!");
                    return false;
                }
                // Check if there's team or not
                if (!smpTeam.teamExist(args[1])) {
                    player.sendMessage(ChatColor.RED + "Team is not exists!");
                    return false;
                }
                // When the player is not in the team and input the teamName they wanted to join this code will work.
                teamName = args[1];
                try {
                    Sqlite.addMember(uuid, teamName);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                player.sendMessage(ChatColor.GREEN + "You have joined the team '" + teamName + "'.");
                break;
            case "add":
                // Check if player input enough argument for this command
                if (args.length < 2) {
                    player.sendMessage(ChatColor.RED + "Usage: /team add <teamName>");
                    return false;
                }
                UUID pending = Objects.requireNonNull(Bukkit.getPlayer(args[2])).getUniqueId();
                if (smpTeam.inTeam(Objects.requireNonNull(Bukkit.getPlayer(args[2])))) {
                    player.sendMessage(ChatColor.RED + "That player is already in a team!");
                    return false;
                }
                teamName = args[1];
                try {
                    Sqlite.addMember(pending, teamName);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case "leave":
                // Check if player is in team or not if not the code will stop here.
                if (!smpTeam.inTeam(player)) {
                    player.sendMessage(ChatColor.RED + "You are not in a team.");
                    return false;
                }
                teamName = smpTeam.getTeam(player);
                try {
                    Sqlite.removeMember(uuid);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                player.sendMessage(ChatColor.GREEN + "You have left the team '" + teamName + "'.");
                break;
            case "message":
                // Check if player is in team or not if not the code will stop here.
                if (!smpTeam.inTeam(player)) {
                    player.sendMessage(ChatColor.RED + "You are not in a team.");
                    return false;
                }
                // Check is player input the message or not if not the code will stop here.
                if (args.length < 2) {
                    player.sendMessage(ChatColor.RED + "Please provide a message.");
                    return false;
                }

                // If the player is not in any team and has provided the message this code will run
                StringBuilder messageBuilder = new StringBuilder();
                for (int i = 1; i < args.length; i++) {
                    messageBuilder.append(args[i]).append(" ");
                }
                // send message to every team member.
                smpTeam.sendTeamMessage(smpTeam.getTeam(player.getUniqueId()), messageBuilder.toString());
                break;
            case "list":
                // Check if player specify the team name or not if not the code will stop here.
                if (args.length < 2) {
                    player.sendMessage(ChatColor.RED + "Please specify a team name.");
                    return true;
                }

                player.sendMessage(smpTeam.getTeamList().toString());
                break;
            case "check":
                if (args.length < 2) {
                    player.sendMessage(ChatColor.RED + "Please specify a team name.");
                    return true;
                }

                teamName = args[1];
                if (!smpTeam.teamExist(teamName)) {
                    player.sendMessage(ChatColor.RED + "Team not found.");
                    return true;
                }

                player.sendMessage(smpTeam.getMember(teamName).toString());
                break;
            case "home":
                // Check if player is in team or not if not the code will stop here.
                if (!smpTeam.inTeam(player)) {
                    player.sendMessage(ChatColor.RED + "You are not in a team.");
                    return false;
                }
                // Check team's home location , if it not exists the code perish!
                if (!Sqlite.hasSetHome(smpTeam.getTeam(player))) {
                    player.sendMessage(ChatColor.RED + "You didn't set team's home yet!");
                    return false;
                }
                Location location = Sqlite.getHome(smpTeam.getTeam(player));
                player.teleport(location);
                break;
            case "sethome":
                // Check if player is in team or not if not the code will stop here.
                if (!smpTeam.inTeam(player)) {
                    player.sendMessage(ChatColor.RED + "You are not in a team.");
                    return false;
                }
                Sqlite.setHome(player.getLocation(), smpTeam.getTeam(player));
                player.sendMessage(ChatColor.GREEN + "You have set team's home.");
                break;
            case "location":
                Location locations = player.getLocation();
                player.sendMessage(String.valueOf(locations));
                break;
            case "tpa":
                // Check if player is in team or not if not the code will stop here.
                if (!smpTeam.inTeam(player)) {
                    player.sendMessage(ChatColor.RED + "You are not in a team.");
                    return false;
                }
                // Check if player specify the member name or not if not the code will stop here.
                if (args.length < 2) {
                    player.sendMessage(ChatColor.RED + "Please specify a member name.");
                    return false;
                }
                // Check if the specified player and command sender is in same team or not if not this code perish!
                Player tp = Bukkit.getPlayer(args[1]);
                if (!smpTeam.sameTeam(tp,player)) {
                    player.sendMessage(ChatColor.RED + "The specified player is not in your team!");
                    return false;
                }
                try {
                    tpa.put(tp, player);
                    assert tp != null;
                    tp.sendMessage("§a" + player.getName() + " send you a Tpa Request!");
                    player.sendMessage("Tpa Request send!");
                } catch (Exception var7) {
                    var7.printStackTrace();
                }
                break;
            case "tpaccept":
                // Check if player is in team or not if not the code will stop here.
                if (!smpTeam.inTeam(player)) {
                    player.sendMessage(ChatColor.RED + "You are not in a team.");
                    return false;
                }
                Player t = (Player) tpa.get(player);
                t.teleport(player.getLocation());
                player.sendMessage("successful!");
                t.sendMessage("successful!");
                break;

            default:
                // If player didn't specify any sub command argument
                player.sendMessage(ChatColor.RED + "Unknown command argument.");
                break;
        }

        return true;
    }
}
