package com.iafenvoy.netherite.item;

import com.iafenvoy.netherite.config.NetheriteExtensionConfig;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.BowItem;
import org.jetbrains.annotations.Nullable;

public class NetheriteBowItem extends BowItem {
    public NetheriteBowItem(Settings settings) {
        super(settings);
    }

    @Override
    protected void shoot(LivingEntity shooter, ProjectileEntity projectile, int index, float speed, float divergence, float yaw, @Nullable LivingEntity target) {
        if (projectile instanceof PersistentProjectileEntity persist)
            persist.setDamage(persist.getDamage() * NetheriteExtensionConfig.INSTANCE.damage.bow_damage_multiplier + NetheriteExtensionConfig.INSTANCE.damage.bow_damage_addition);
        projectile.setVelocity(shooter, shooter.getPitch(), shooter.getYaw() + yaw, 0.0F, speed, divergence);
    }
}
