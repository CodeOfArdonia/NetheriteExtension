package com.iafenvoy.netherite.item.impl.neoforge;

import com.iafenvoy.netherite.item.impl.NetheriteElytraItem;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ElytraItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.event.GameEvent;

public class NetheriteElytraItemImpl extends NetheriteElytraItem {
    protected NetheriteElytraItemImpl(Settings settings) {
        super(settings);
    }

    public static NetheriteElytraItem create(Settings settings) {
        return new NetheriteElytraItemImpl(settings);
    }

    @Override
    public boolean canElytraFly(ItemStack stack, LivingEntity entity) {
        return ElytraItem.isUsable(stack);
    }

    @Override
    public boolean elytraFlightTick(ItemStack stack, LivingEntity entity, int flightTicks) {
        if (!entity.getWorld().isClient) {
            int nextFlightTick = flightTicks + 1;
            if (nextFlightTick % 10 == 0) {
                if (nextFlightTick % 20 == 0)
                    stack.damage(1, entity, (e) -> e.sendEquipmentBreakStatus(EquipmentSlot.CHEST));
                entity.emitGameEvent(GameEvent.ELYTRA_GLIDE);
            }
        }
        return true;
    }
}
