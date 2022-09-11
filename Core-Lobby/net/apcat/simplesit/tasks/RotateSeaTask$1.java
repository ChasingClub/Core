package net.apcat.simplesit.tasks;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;

class RotateSeatTask$1 implements AlignArmorStand {
    private final RotateSeatTask this$0;
    Method method;

    RotateSeatTask$1(RotateSeatTask var1) throws NoSuchMethodException {
        this.this$0 = var1;
        this.method = Entity.class.getMethod("setRotation", Float.TYPE, Float.TYPE);
    }

    public void align(ArmorStand armorStand, float yaw) {
        try {
            this.method.invoke(armorStand, yaw, 0);
        } catch (IllegalArgumentException | InvocationTargetException | IllegalAccessException var4) {
            var4.printStackTrace();
        }

    }
}