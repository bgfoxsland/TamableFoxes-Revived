package net.seanomik.tamablefoxes.versions.version_26_3_R1;

import java.lang.reflect.Field;
import java.util.UUID;
import net.minecraft.world.entity.EntityType;
// FOX: 26.x 实体类型常量迁移到 EntityTypes
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.animal.fox.Fox;
import net.seanomik.tamablefoxes.util.NMSInterface;
import net.seanomik.tamablefoxes.util.io.Config;
import net.seanomik.tamablefoxes.util.io.LanguageConfig;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
// FOX: 26.x 的 CraftBukkit 不再带版本包后缀
import org.bukkit.craftbukkit.entity.CraftEntity;
import org.bukkit.entity.Player;

public class NMSInterface_26_3_R1 implements NMSInterface {
    @Override
    public void registerCustomFoxEntity() {

        try { // Replace the fox entity
            Field field = EntityTypes.FOX.getClass().getDeclaredField("factory"); // factory = factory
            field.setAccessible(true);
            field.set(EntityTypes.FOX, (EntityType.EntityFactory<Fox>) EntityTamableFox::new);
            Bukkit.getServer().getConsoleSender().sendMessage(Config.getPrefix() + ChatColor.GREEN + LanguageConfig.getSuccessReplaced());
        } catch (Exception e) {
            Bukkit.getServer().getConsoleSender().sendMessage(Config.getPrefix() + ChatColor.RED + LanguageConfig.getFailureReplace());
            e.printStackTrace();
        }
    }

    @Override
    public void spawnTamableFox(Location loc, FoxType type) {
        EntityTamableFox tamableFox = (EntityTamableFox) ((CraftEntity) loc.getWorld().spawnEntity(loc, org.bukkit.entity.EntityType.FOX)).getHandle();
        tamableFox.setVariant((type == FoxType.RED) ? Fox.Variant.RED : Fox.Variant.SNOW);
    }

    @Override
    public void changeFoxOwner(org.bukkit.entity.Fox fox, Player newOwner) {
        EntityTamableFox tamableFox = (EntityTamableFox) ((CraftEntity) fox).getHandle();
        tamableFox.setOwnerUUID(newOwner.getUniqueId());
    }

    @Override
    public UUID getFoxOwner(org.bukkit.entity.Fox fox) {
        EntityTamableFox tamableFox = (EntityTamableFox) ((CraftEntity) fox).getHandle();
        return tamableFox.getOwnerUUID();
    }

    @Override
    public void renameFox(org.bukkit.entity.Fox fox, Player player) {
        EntityTamableFox tamableFox = (EntityTamableFox) ((CraftEntity) fox).getHandle();
        tamableFox.rename(player);
    }
}
