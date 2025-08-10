package com.iafenvoy.netherite.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Shearable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShearsItem;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;

public class NetheriteShearsItem extends ShearsItem {
    public NetheriteShearsItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        if (entity instanceof Shearable shearable && shearable.isShearable()) {
            shearable.sheared(SoundCategory.PLAYERS);
            stack.damage(1, user, p -> p.sendToolBreakStatus(hand));
            return ActionResult.SUCCESS;
        }
        return super.useOnEntity(stack, user, entity, hand);
    }
}
