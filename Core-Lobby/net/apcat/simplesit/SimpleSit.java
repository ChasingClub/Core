package net.apcat.simplesit;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;
import net.apcat.simplesit.listeners.PlayerArmorStandManipulate;
import net.apcat.simplesit.listeners.PlayerDeath;
import net.apcat.simplesit.listeners.PlayerQuit;
import net.apcat.simplesit.listeners.PlayerTeleport;
import net.apcat.simplesit.tasks.RotateSeatTask;
import net.apcat.simplesit.utils.Text;
import net.apcat.simplesit.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.ArmorStand;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class SimpleSit extends JavaPlugin {
    private final Map<UUID, ArmorStand> seats = new HashMap<>();
    private Permission sitPermission;
    private String sitDownMessage;
    private String sitUpMessage;
    private String sitFailMessage;

    public SimpleSit() {
    }

    public void onEnable() {
        this.registerConfig();
        this.registerPermissions();
        this.registerListeners();
        new RotateSeatTask(this);
    }

    public void onDisable() {
        Object[] var4;
        int var3 = (var4 = this.seats.keySet().toArray()).length;

        for(int var2 = 0; var2 < var3; ++var2) {
            Object uuid = var4[var2];
            SimpleSitPlayer player = new SimpleSitPlayer(Bukkit.getPlayer((UUID)uuid));
            player.setSitting(false);
        }

    }

    public void convertConfig() {
        boolean convert = false;
        String oldPermissionDefault = null;
        String oldSitDownMessage = null;
        String oldSitUpMessage = null;
        String oldSitFailMessage = null;
        if (this.getConfig().contains("checkForUpdates")) {
            convert = true;
            if (this.getConfig().contains("sitPermissionDefault")) {
                oldPermissionDefault = this.getConfig().getString("sitPermissionDefault");
            }

            if (this.getConfig().contains("sitDown")) {
                oldSitDownMessage = this.getConfig().getString("sitDown");
            }

            if (this.getConfig().contains("sitFail")) {
                oldSitFailMessage = this.getConfig().getString("sitFail");
            }

            if (this.getConfig().contains("sitUp")) {
                oldSitUpMessage = this.getConfig().getString("sitUp");
            }

            File configFile = new File(this.getDataFolder(), "config.yml");
            configFile.delete();
        }

        this.saveDefaultConfig();
        this.reloadConfig();
        if (convert) {
            if (oldPermissionDefault != null) {
                this.getConfig().set("sit-permission-default", oldPermissionDefault);
            }

            if (oldSitDownMessage != null) {
                this.getConfig().set("sitdown-message", oldSitDownMessage);
            }

            if (oldSitFailMessage != null) {
                this.getConfig().set("sitfail-message", oldSitFailMessage);
            }

            if (oldSitUpMessage != null) {
                this.getConfig().set("situp-message", oldSitUpMessage);
            }

            this.saveConfig();
            this.reloadConfig();
        }
    }

    private void registerConfig() {
        this.convertConfig();
        this.sitDownMessage = Utils.color(this.getConfig().getString("sitdown-message"));
        this.sitUpMessage = Utils.color(this.getConfig().getString("situp-message"));
        this.sitFailMessage = Utils.color(this.getConfig().getString("sitfail-message"));
    }

    private void registerPermissions() {
        this.sitPermission = this.getPermission("simplesit.sit", "sit-permission-default");
    }

    private Permission getPermission(String permissionString, String location) {
        Permission permission = new Permission(permissionString);
        String permissionDefault = this.getConfig().getString(location);
        if (!Utils.isPermissionDefault(permissionDefault)) {
            this.sendConfigError(Text.INVALID_PERMISSION_DEFAULT.format(new Object[]{location, permissionDefault}), Level.WARNING);
            permission.setDefault(PermissionDefault.TRUE);
        } else {
            permission.setDefault(PermissionDefault.valueOf(permissionDefault.toUpperCase()));
        }

        Bukkit.getPluginManager().addPermission(permission);
        return permission;
    }

    private void registerListeners() {
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new PlayerTeleport(), this);
        pm.registerEvents(new PlayerQuit(), this);
        pm.registerEvents(new PlayerArmorStandManipulate(), this);
        pm.registerEvents(new PlayerDeath(), this);
    }

    public Map<UUID, ArmorStand> getSeats() {
        return this.seats;
    }

    public String getSitFailMessage() {
        return this.sitFailMessage;
    }

    public String getSitDownMessage() {
        return this.sitDownMessage;
    }

    public String getSitUpMessage() {
        return this.sitUpMessage;
    }

    public Permission getSitPermission() {
        return this.sitPermission;
    }

    private void sendConfigError(String message, Level level) {
        this.getLogger().log(level, message);
    }
}