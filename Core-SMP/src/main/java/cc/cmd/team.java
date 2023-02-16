package cc.cmd;


import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class team implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        //args[0] = add, invite, remove, create, join, leave, disband, kick, promote, demote, list, info, chat, ally, enemy, neutral, accept, deny, help

        if (args.length == 0) {
            //help
        } else if (args.length == 1) {
            if (args[0].equalsIgnoreCase("create")) {
                //create team
            } else if (args[0].equalsIgnoreCase("join")) {
                //join team
            } else if (args[0].equalsIgnoreCase("leave")) {
                //leave team
            } else if (args[0].equalsIgnoreCase("disband")) {
                //disband team
            } else if (args[0].equalsIgnoreCase("list")) {
                //list teams
            } else if (args[0].equalsIgnoreCase("info")) {
                //info
            } else if (args[0].equalsIgnoreCase("chat")) {
                //chat
            } else if (args[0].equalsIgnoreCase("ally")) {
                //ally
            } else if (args[0].equalsIgnoreCase("enemy")) {
                //enemy
            } else if (args[0].equalsIgnoreCase("accept")) {
                //accept
            } else if (args[0].equalsIgnoreCase("deny")) {
                //deny
            } else if (args[0].equalsIgnoreCase("invite")) {
                //invite
            } else if (args[0].equalsIgnoreCase("remove")) {
                //remove
            } else if (args[0].equalsIgnoreCase("kick")) {
                //kick
            } else if (args[0].equalsIgnoreCase("promote")) {
                //promote
            } else if (args[0].equalsIgnoreCase("demote")) {
                //demote
            } else if (args[0].equalsIgnoreCase("help")) {
                //help
            } else {
                //help
            }
        } else if (args.length == 2) {

            // if args[0] == create then args[1] == "String" name of team
            if (args[0].equalsIgnoreCase("create")) {

            }

        }

        return true;
    }
}
