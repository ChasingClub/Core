package core.server.core.API;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import static core.server.core.Core.Plname;

public class SetFlySpeed {
    public static void SetSpeedForFly(Player p, String speed){
        if (speed.equalsIgnoreCase("1")) {
            p.setFlySpeed(0.1f);
            p.sendMessage(Plname + p.getName() + " fly speed have been set to " + speed);
        }else if (speed.equalsIgnoreCase("2")) {
            p.setFlySpeed(0.2f);
            p.sendMessage(Plname + p.getName() + " fly speed have been set to " + speed);
        }else if (speed.equalsIgnoreCase("3")) {
            p.setFlySpeed(0.3f);
            p.sendMessage(Plname + p.getName() + " fly speed have been set to " + speed);
        }else if (speed.equalsIgnoreCase("4")) {
            p.setFlySpeed(0.4f);
            p.sendMessage(Plname + p.getName() + " fly speed have been set to " + speed);
        }else if (speed.equalsIgnoreCase("5")) {
            p.setFlySpeed(0.5f);
            p.sendMessage(Plname + p.getName() + " fly speed have been set to " + speed);
        }else if (speed.equalsIgnoreCase("6")) {
            p.setFlySpeed(0.6f);
            p.sendMessage(Plname + p.getName() + " fly speed have been set to " + speed);
        }else if (speed.equalsIgnoreCase("7")) {
            p.setFlySpeed(0.7f);
            p.sendMessage(Plname + p.getName() + " fly speed have been set to " + speed);
        }else if (speed.equalsIgnoreCase("8")) {
            p.setFlySpeed(0.8f);
            p.sendMessage(Plname + p.getName() + " fly speed have been set to " + speed);
        }else if (speed.equalsIgnoreCase("9")) {
            p.setFlySpeed(0.9f);
            p.sendMessage(Plname + p.getName() + " fly speed have been set to " + speed);
        }else if (speed.equalsIgnoreCase("10")) {
            p.setFlySpeed(1f);
            p.sendMessage(Plname + p.getName() + " fly speed have been set to " + speed);
        }else{
            p.sendMessage(Plname+ ChatColor.RED + "Invalid usage pick between 1-10 for fly speed.");
        }
    }
    public static void SetSpeedForFlyTarget(Player sender, Player p, String speed){
        if (speed.equalsIgnoreCase("1")) {
            p.setFlySpeed(0.1f);
            p.sendMessage(Plname + p.getName() + " fly speed have been set to " + speed);
        }else if (speed.equalsIgnoreCase("2")) {
            p.setFlySpeed(0.2f);
            p.sendMessage(Plname + p.getName() + " fly speed have been set to " + speed);
        }else if (speed.equalsIgnoreCase("3")) {
            p.setFlySpeed(0.3f);
            p.sendMessage(Plname + p.getName() + " fly speed have been set to " + speed);
        }else if (speed.equalsIgnoreCase("4")) {
            p.setFlySpeed(0.4f);
            p.sendMessage(Plname + p.getName() + " fly speed have been set to " + speed);
        }else if (speed.equalsIgnoreCase("5")) {
            p.setFlySpeed(0.5f);
            p.sendMessage(Plname + p.getName() + " fly speed have been set to " + speed);
        }else if (speed.equalsIgnoreCase("6")) {
            p.setFlySpeed(0.6f);
            p.sendMessage(Plname + p.getName() + " fly speed have been set to " + speed);
        }else if (speed.equalsIgnoreCase("7")) {
            p.setFlySpeed(0.7f);
            p.sendMessage(Plname + p.getName() + " fly speed have been set to " + speed);
        }else if (speed.equalsIgnoreCase("8")) {
            p.setFlySpeed(0.8f);
            p.sendMessage(Plname + p.getName() + " fly speed have been set to " + speed);
        }else if (speed.equalsIgnoreCase("9")) {
            p.setFlySpeed(0.9f);
            p.sendMessage(Plname + p.getName() + " fly speed have been set to " + speed);
        }else if (speed.equalsIgnoreCase("10")) {
            p.setFlySpeed(1f);
            p.sendMessage(Plname + p.getName() + " fly speed have been set to " + speed);
        }else{
            sender.sendMessage(Plname+ ChatColor.RED + "Invalid usage pick between 1-10 for fly speed.");
        }
    }
}
