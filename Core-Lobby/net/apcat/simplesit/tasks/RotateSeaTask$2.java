package net.apcat.simplesit.tasks;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import org.bukkit.entity.ArmorStand;

class RotateSeatTask$2 implements AlignArmorStand {
    private final RotateSeatTask this$0;

    RotateSeatTask$2(RotateSeatTask var1) {
        this.this$0 = var1;
    }

    public void align(ArmorStand armorStand, float yaw) {
        try {
            Object entityArmorstand = armorStand.getClass().getMethod("getHandle").invoke(armorStand);
            Field yawField = entityArmorstand.getClass().getField("yaw");
            yawField.set(entityArmorstand, yaw);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | SecurityException | NoSuchFieldException | NoSuchMethodException var5) {
            var5.printStackTrace();
        }

    }
}