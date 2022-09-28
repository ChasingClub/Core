package chasingclub.server.core.API;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import static chasingclub.server.core.Utils.Utils.PluginName;


public class SetFlySpeed {
    public static void SetSpeedForFly(Player p, String speed){
        if (speed.equalsIgnoreCase("1")) {
            p.setFlySpeed(0.1f);
            p.sendMessage(PluginName + p.getName() + " fly speed have been set to " + speed);
        }else if (speed.equalsIgnoreCase("2")) {
            p.setFlySpeed(0.2f);
            p.sendMessage(PluginName + p.getName() + " fly speed have been set to " + speed);
        }else if (speed.equalsIgnoreCase("3")) {
            p.setFlySpeed(0.3f);
            p.sendMessage(PluginName + p.getName() + " fly speed have been set to " + speed);
        }else if (speed.equalsIgnoreCase("4")) {
            p.setFlySpeed(0.4f);
            p.sendMessage(PluginName + p.getName() + " fly speed have been set to " + speed);
        }else if (speed.equalsIgnoreCase("5")) {
            p.setFlySpeed(0.5f);
            p.sendMessage(PluginName + p.getName() + " fly speed have been set to " + speed);
        }else if (speed.equalsIgnoreCase("6")) {
            p.setFlySpeed(0.6f);
            p.sendMessage(PluginName + p.getName() + " fly speed have been set to " + speed);
        }else if (speed.equalsIgnoreCase("7")) {
            p.setFlySpeed(0.7f);
            p.sendMessage(PluginName + p.getName() + " fly speed have been set to " + speed);
        }else if (speed.equalsIgnoreCase("8")) {
            p.setFlySpeed(0.8f);
            p.sendMessage(PluginName + p.getName() + " fly speed have been set to " + speed);
        }else if (speed.equalsIgnoreCase("9")) {
            p.setFlySpeed(0.9f);
            p.sendMessage(PluginName + p.getName() + " fly speed have been set to " + speed);
        }else if (speed.equalsIgnoreCase("10")) {
            p.setFlySpeed(1f);
            p.sendMessage(PluginName + p.getName() + " fly speed have been set to " + speed);
        }else{
            p.sendMessage(PluginName+ ChatColor.RED + "Invalid usage pick between 1-10 for fly speed.");
        }
    }
    public static void SetSpeedForFlyTarget(Player sender, Player p, String speed){
        if (speed.equalsIgnoreCase("1")) {
            p.setFlySpeed(0.1f);
            p.sendMessage(PluginName + p.getName() + " fly speed have been set to " + speed);
        }else if (speed.equalsIgnoreCase("2")) {
            p.setFlySpeed(0.2f);
            p.sendMessage(PluginName + p.getName() + " fly speed have been set to " + speed);
        }else if (speed.equalsIgnoreCase("3")) {
            p.setFlySpeed(0.3f);
            p.sendMessage(PluginName + p.getName() + " fly speed have been set to " + speed);
        }else if (speed.equalsIgnoreCase("4")) {
            p.setFlySpeed(0.4f);
            p.sendMessage(PluginName + p.getName() + " fly speed have been set to " + speed);
        }else if (speed.equalsIgnoreCase("5")) {
            p.setFlySpeed(0.5f);
            p.sendMessage(PluginName + p.getName() + " fly speed have been set to " + speed);
        }else if (speed.equalsIgnoreCase("6")) {
            p.setFlySpeed(0.6f);
            p.sendMessage(PluginName + p.getName() + " fly speed have been set to " + speed);
        }else if (speed.equalsIgnoreCase("7")) {
            p.setFlySpeed(0.7f);
            p.sendMessage(PluginName + p.getName() + " fly speed have been set to " + speed);
        }else if (speed.equalsIgnoreCase("8")) {
            p.setFlySpeed(0.8f);
            p.sendMessage(PluginName + p.getName() + " fly speed have been set to " + speed);
        }else if (speed.equalsIgnoreCase("9")) {
            p.setFlySpeed(0.9f);
            p.sendMessage(PluginName + p.getName() + " fly speed have been set to " + speed);
        }else if (speed.equalsIgnoreCase("10")) {
            p.setFlySpeed(1f);
            p.sendMessage(PluginName + p.getName() + " fly speed have been set to " + speed);
        }else{
            sender.sendMessage(PluginName+ ChatColor.RED + "Invalid usage pick between 1-10 for fly speed.");
        }
    }
}
