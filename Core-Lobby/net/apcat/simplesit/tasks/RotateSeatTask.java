package net.apcat.simplesit.tasks;

import java.util.Iterator;
import java.util.logging.Level;
import net.apcat.simplesit.SimpleSit;
import net.apcat.simplesit.utils.Text;
import org.bukkit.entity.ArmorStand;
import org.bukkit.scheduler.BukkitRunnable;

public class RotateSeatTask extends BukkitRunnable {
    private SimpleSit simpleSit;
    private AlignArmorStand alignArmorStand;

    public RotateSeatTask(SimpleSit simpleSit) {
        this.simpleSit = simpleSit;

        try {
            this.alignArmorStand = new RotateSeatTask$1(this);
        } catch (SecurityException | NoSuchMethodException var3) {
            simpleSit.getLogger().log(Level.WARNING, Text.LEGACY_ALIGN_ARMOR_STAND.toString());
            this.alignArmorStand = new RotateSeatTask$2(this);
        }

        this.runTaskTimerAsynchronously(simpleSit, 0L, 1L);
    }

    public void run() {
        Iterator var2 = this.simpleSit.getSeats().values().iterator();

        while(var2.hasNext()) {
            ArmorStand armorstand = (ArmorStand)var2.next();
            if (armorstand.getPassenger() != null) {
                this.alignArmorStand.align(armorstand, armorstand.getPassenger().getLocation().getYaw());
            }
        }

    }
}