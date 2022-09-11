package net.apcat.simplesit;

import org.bukkit.entity.ArmorStand;
import org.bukkit.plugin.java.JavaPlugin;

public class SimpleSitArmorStand {
    private SimpleSit simpleSit;
    private ArmorStand armorstand;

    public SimpleSitArmorStand(ArmorStand armorStand) {
        this.armorstand = armorStand;
        this.simpleSit = (SimpleSit)JavaPlugin.getPlugin(SimpleSit.class);
    }

    public boolean isSeat() {
        return this.simpleSit.getSeats().containsValue(this.armorstand);
    }

    public ArmorStand getBukkitArmorStand() {
        return this.armorstand;
    }
}