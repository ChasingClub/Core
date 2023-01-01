package chasingclub.server.core.hook;

import chasingclub.server.core.Core;
import chasingclub.server.core.Utils.DuelStatsSQLAPI;
import chasingclub.server.core.events.classic_sword;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;
import org.jetbrains.annotations.NotNull;

import java.math.RoundingMode;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Objects;

import static chasingclub.server.core.Core.ingame;
import static chasingclub.server.core.Utils.Database.FindDuelStatsByPlayerUUID;

public class PlaceholderExpansion extends me.clip.placeholderapi.expansion.PlaceholderExpansion {

    @Override
    public @NotNull String getIdentifier(){
        return "ChasingClub";
    }

    @Override
    public @NotNull String getAuthor(){
        return "ItDragClick";
    }

    @Override
    public @NotNull String getVersion(){
        return "1.5.6";
    }

    @Override
    public boolean canRegister(){
        return true;
    }

    @Override
    public boolean persist(){
        return true;
    }

    @Override
    public String onRequest(OfflinePlayer p, @NotNull String params){
        if(p != null && p.isOnline()){
            return onPlaceholderRequest(p.getPlayer(), params);
        }
        return null;
    }
    public DuelStatsSQLAPI GetPlayerDuelStats(String uuid){
        try {
            return FindDuelStatsByPlayerUUID(uuid);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public String onPlaceholderRequest(Player p, @NotNull String params){
        if(p == null){
            return "";
        }
        if(params.equals("kit")){
            return Core.kits.get(p);
        }
        if(params.equals("target")){
            return ingame.getOrDefault(p.getName(), "Unknown");
        }
        if(params.equals("duel_wins")){
            return String.valueOf(GetPlayerDuelStats(p.getUniqueId().toString()).GetWins());
        }
        if(params.equals("duel_loses")){
            return String.valueOf(GetPlayerDuelStats(p.getUniqueId().toString()).GetLoses());
        }
        if(params.equals("duel_winrate")){
            DuelStatsSQLAPI data = GetPlayerDuelStats(p.getUniqueId().toString());
            double wins = data.GetWins();
            double loses = data.GetLoses();
            double winrate = ((wins*100)/(wins+loses));
            DecimalFormat decimalFormat = new DecimalFormat("#.##");
            decimalFormat.setRoundingMode(RoundingMode.CEILING);
            return decimalFormat.format(winrate);
        }
        if(params.equals("hp")){
            if(!ingame.containsKey(p.getName())){
                return "N/A";
            }
            String msg = "N/A";
            if(p.getWorld().getName().equals("Netherite_game")) {
                PlayerInventory inv = p.getInventory();
                if (Objects.requireNonNull(inv.getHelmet()).getType() == Material.DIAMOND_HELMET) {
                    msg = ("§a●●●●●●●");
                }
                if (Objects.requireNonNull(inv.getHelmet()).getType() == Material.NETHERITE_HELMET) {
                    msg = ("§a●●●●●●§c●");
                }
                if (Objects.requireNonNull(inv.getBoots()).getType() == Material.NETHERITE_BOOTS) {
                    msg = ("§a●●●●●§c●●");
                }
                if (Objects.requireNonNull(inv.getLeggings()).getType() == Material.NETHERITE_LEGGINGS) {
                    msg = ("§a●●●●§c●●●");
                }
                if (Objects.requireNonNull(inv.getChestplate()).getType() == Material.NETHERITE_CHESTPLATE) {
                    msg = ("§a●●●§c●●●●");
                }
                if (inv.contains(Material.NETHERITE_SWORD)) {
                    msg = ("§a●●§c●●●●●");
                }
                if (inv.contains(Material.NETHERITE_AXE)) {
                    msg = ("§a●§c●●●●●●");
                }
            }else if(p.getWorld().getName().equals("classic_sword")){
                if(classic_sword.Lives.get(p.getName()) == 3){
                    msg = ("§a●●●");
                }
                if(classic_sword.Lives.get(p.getName()) == 2){
                    msg = ("§a●●§c●");
                }
                if(classic_sword.Lives.get(p.getName()) == 1){
                    msg = ("§a●§c●●");
                }
            }

            return msg;
        }
        if(params.equals("target_hp")){
            if(!ingame.containsKey(p.getName())){
                return "N/A";
            }
            Player target = Bukkit.getServer().getPlayer(ingame.get(p.getName()));
            if(target == null){
                return "N/A";
            }
            String msg = "N/A";
            if(target.getWorld().getName().equals("Netherite_game")) {
                PlayerInventory inv = target.getInventory();
                if (Objects.requireNonNull(inv.getHelmet()).getType() == Material.DIAMOND_HELMET) {
                    msg = ("§a●●●●●●●");
                }
                if (Objects.requireNonNull(inv.getHelmet()).getType() == Material.NETHERITE_HELMET) {
                    msg = ("§a●●●●●●§c●");
                }
                if (Objects.requireNonNull(inv.getBoots()).getType() == Material.NETHERITE_BOOTS) {
                    msg = ("§a●●●●●§c●●");
                }
                if (Objects.requireNonNull(inv.getLeggings()).getType() == Material.NETHERITE_LEGGINGS) {
                    msg = ("§a●●●●§c●●●");
                }
                if (Objects.requireNonNull(inv.getChestplate()).getType() == Material.NETHERITE_CHESTPLATE) {
                    msg = ("§a●●●§c●●●●");
                }
                if (inv.contains(Material.NETHERITE_SWORD)) {
                    msg = ("§a●●§c●●●●●");
                }
                if (inv.contains(Material.NETHERITE_AXE)) {
                    msg = ("§a●§c●●●●●●");
                }
            }else if(target.getWorld().getName().equals("classic_sword")){
                if(classic_sword.Lives.get(target.getName()) == 3){
                    msg = ("§a●●●");
                }
                if(classic_sword.Lives.get(target.getName()) == 2){
                    msg = ("§a●●§c●");
                }
                if(classic_sword.Lives.get(target.getName()) == 1){
                    msg = ("§a●§c●●");
                }
            }

            return msg;
        }
        return null;
    }
}
