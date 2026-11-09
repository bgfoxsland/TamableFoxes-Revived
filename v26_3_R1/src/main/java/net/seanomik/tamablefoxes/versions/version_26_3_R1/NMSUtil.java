package net.seanomik.tamablefoxes.versions.version_26_3_R1;

import net.minecraft.core.UUIDUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntArrayTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import org.bukkit.event.entity.EntityTargetEvent;

import java.lang.reflect.Method;
import java.util.UUID;

public class NMSUtil {

    public static Method setTargetMethod = getSetTargetMethod();

    public static void putUUID(CompoundTag compoundTag, String key, UUID uuid) {
        compoundTag.put(key, createUUIDTag(uuid));
    }

    public static UUID getUUID(CompoundTag compoundTag, String key) {
        int[] result = compoundTag.getIntArray(key).orElse(null);
        if (result == null) return null;
        return getUUITFromTag(result);
    }

    public static UUID getUUITFromTag(int[] intArray) {
        return UUIDUtil.uuidFromIntArray(intArray);
    }

    public static IntArrayTag createUUIDTag(UUID var0) {
        return new IntArrayTag(UUIDUtil.uuidToIntArray(var0));
    }

    public static boolean setTarget(Mob mob, LivingEntity entityliving, EntityTargetEvent.TargetReason reason) {
        if (setTargetMethod == null) {
            throw new RuntimeException(new NoSuchMethodException("net.minecraft.world.entity.Mob#setTarget(LivingEntity, EntityTargetEvent.TargetReason) method not found"));
        }

        try {
            return (Boolean) setTargetMethod.invoke(mob, entityliving, reason);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static Method getSetTargetMethod() {
        try {
            Method method = Mob.class.getDeclaredMethod("setTarget", LivingEntity.class, EntityTargetEvent.TargetReason.class);
            method.setAccessible(true);
            return method;

        } catch (NoSuchMethodException ignored) {
        }

        return null;
    }

}
