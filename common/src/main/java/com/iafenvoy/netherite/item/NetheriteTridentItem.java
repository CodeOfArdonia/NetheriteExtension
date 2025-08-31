package com.iafenvoy.netherite.item;

import com.iafenvoy.netherite.config.NetheriteExtensionConfig;
import com.iafenvoy.netherite.entity.NetheriteTridentEntity;
import net.minecraft.component.EnchantmentEffectComponentTypes;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.TridentItem;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class NetheriteTridentItem extends TridentItem {
    public NetheriteTridentItem(Item.Settings settings) {
        super(settings.attributeModifiers(createAttributeModifiers()));
    }

    public static AttributeModifiersComponent createAttributeModifiers() {
        return AttributeModifiersComponent.builder().add(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier(BASE_ATTACK_DAMAGE_MODIFIER_ID, 8.0F * NetheriteExtensionConfig.INSTANCE.damage.trident_damage_multiplier + NetheriteExtensionConfig.INSTANCE.damage.trident_damage_addition, EntityAttributeModifier.Operation.ADD_VALUE), AttributeModifierSlot.MAINHAND).add(EntityAttributes.GENERIC_ATTACK_SPEED, new EntityAttributeModifier(BASE_ATTACK_SPEED_MODIFIER_ID, -2.9F, EntityAttributeModifier.Operation.ADD_VALUE), AttributeModifierSlot.MAINHAND).build();
    }

    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        if (user instanceof PlayerEntity playerEntity) {
            int i = this.getMaxUseTime(stack, user) - remainingUseTicks;
            if (i >= 10) {
                float f = EnchantmentHelper.getTridentSpinAttackStrength(stack, playerEntity);
                if (!(f > 0.0F) || playerEntity.isTouchingWaterOrRain()) {
                    if (!isAboutToBreak(stack)) {
                        RegistryEntry<SoundEvent> registryEntry = EnchantmentHelper.getEffect(stack, EnchantmentEffectComponentTypes.TRIDENT_SOUND).orElse(SoundEvents.ITEM_TRIDENT_THROW);
                        if (!world.isClient) {
                            stack.damage(1, playerEntity, LivingEntity.getSlotForHand(user.getActiveHand()));
                            if (f == 0.0F) {
                                NetheriteTridentEntity tridentEntity = new NetheriteTridentEntity(world, playerEntity, stack);
                                tridentEntity.setVelocity(playerEntity, playerEntity.getPitch(), playerEntity.getYaw(), 0.0F, 2.5F, 1.0F);
                                if (playerEntity.isInCreativeMode())
                                    tridentEntity.pickupType = PersistentProjectileEntity.PickupPermission.CREATIVE_ONLY;

                                world.spawnEntity(tridentEntity);
                                world.playSoundFromEntity(null, tridentEntity, registryEntry.value(), SoundCategory.PLAYERS, 1.0F, 1.0F);
                                if (!playerEntity.isInCreativeMode()) {
                                    playerEntity.getInventory().removeOne(stack);
                                }
                            }
                        }
                        playerEntity.incrementStat(Stats.USED.getOrCreateStat(this));
                        if (f > 0.0F) {
                            float g = playerEntity.getYaw();
                            float h = playerEntity.getPitch();
                            float j = -MathHelper.sin(g * ((float) Math.PI / 180F)) * MathHelper.cos(h * ((float) Math.PI / 180F));
                            float k = -MathHelper.sin(h * ((float) Math.PI / 180F));
                            float l = MathHelper.cos(g * ((float) Math.PI / 180F)) * MathHelper.cos(h * ((float) Math.PI / 180F));
                            float m = MathHelper.sqrt(j * j + k * k + l * l);
                            j *= f / m;
                            k *= f / m;
                            l *= f / m;
                            playerEntity.addVelocity(j, k, l);
                            playerEntity.useRiptide(20, 8.0F, stack);
                            if (playerEntity.isOnGround())
                                playerEntity.move(MovementType.SELF, new Vec3d(0, 2, 0));
                            world.playSoundFromEntity(null, playerEntity, registryEntry.value(), SoundCategory.PLAYERS, 1.0F, 1.0F);
                        }
                    }
                }
            }
        }
    }

    private static boolean isAboutToBreak(ItemStack stack) {
        return stack.getDamage() >= stack.getMaxDamage() - 1;
    }
}
