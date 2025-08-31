package com.iafenvoy.netherite.entity;

import com.google.common.collect.Lists;
import com.iafenvoy.netherite.config.NetheriteExtensionConfig;
import com.iafenvoy.netherite.registry.NetheriteEntities;
import com.iafenvoy.netherite.registry.NetheriteItems;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ProjectileDeflection;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.s2c.play.GameStateChangeS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.Arrays;

public class NetheriteTridentEntity extends PersistentProjectileEntity {
    private static final TrackedData<Byte> LOYALTY = DataTracker.registerData(NetheriteTridentEntity.class, TrackedDataHandlerRegistry.BYTE);
    private static final TrackedData<Boolean> ENCHANTED = DataTracker.registerData(NetheriteTridentEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final ItemStack DEFAULT = new ItemStack(NetheriteItems.NETHERITE_TRIDENT.get());
    public ItemStack tridentStack;
    public boolean dealtDamage;
    public int returnTimer;

    public NetheriteTridentEntity(EntityType<? extends NetheriteTridentEntity> type, World world) {
        super(type, world);
        this.tridentStack = DEFAULT.copy();
    }

    public NetheriteTridentEntity(World world, LivingEntity owner, ItemStack stack) {
        super(NetheriteEntities.NETHERITE_TRIDENT.get(), owner, world, stack, stack);
        this.tridentStack = stack.copy();
        this.dataTracker.set(LOYALTY, this.getLoyalty(stack));
        this.dataTracker.set(ENCHANTED, stack.hasGlint());
    }

    private byte getLoyalty(ItemStack stack) {
        World var3 = this.getWorld();
        if (var3 instanceof ServerWorld serverWorld) {
            return (byte) MathHelper.clamp(EnchantmentHelper.getTridentReturnAcceleration(serverWorld, stack, this), 0, 127);
        } else {
            return 0;
        }
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(LOYALTY, (byte) 0);
        builder.add(ENCHANTED, false);
    }

    @Override
    public void tick() {
        if (this.inGroundTime > 4) this.dealtDamage = true;
        Entity entity = this.getOwner();
        int i = this.dataTracker.get(LOYALTY);
        if (i > 0 && (this.dealtDamage || this.isNoClip()) && entity != null) {
            if (!this.isOwnerAlive()) {
                if (!this.getWorld().isClient && this.pickupType == PickupPermission.ALLOWED)
                    this.dropStack(this.asItemStack(), 0.1F);
                this.discard();
            } else {
                this.setNoClip(true);
                Vec3d vec3d = entity.getEyePos().subtract(this.getPos());
                this.setPos(this.getX(), this.getY() + vec3d.y * 0.015 * (double) i, this.getZ());
                if (this.getWorld().isClient)
                    this.lastRenderY = this.getY();
                double d = 0.05 * (double) i;
                this.setVelocity(this.getVelocity().multiply(0.95).add(vec3d.normalize().multiply(d)));
                if (this.returnTimer == 0)
                    this.playSound(SoundEvents.ITEM_TRIDENT_RETURN, 10.0F, 1.0F);
                ++this.returnTimer;
            }
        }
        super.tick();
    }

    private boolean isOwnerAlive() {
        Entity entity = this.getOwner();
        if (entity != null && entity.isAlive())
            return !(entity instanceof ServerPlayerEntity) || !entity.isSpectator();
        else
            return false;
    }

    @Override
    protected ItemStack asItemStack() {
        return this.tridentStack.copy();
    }

    @Override
    protected ItemStack getDefaultItemStack() {
        return DEFAULT.copy();
    }

    public boolean isEnchanted() {
        return this.dataTracker.get(ENCHANTED);
    }

    @Override
    protected EntityHitResult getEntityCollision(Vec3d currentPosition, Vec3d nextPosition) {
        return this.dealtDamage ? null : super.getEntityCollision(currentPosition, nextPosition);
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        Entity entity = entityHitResult.getEntity();
        float f = (float) this.getVelocity().length();
        double d = this.getDamage();
        Entity entity2 = this.getOwner();
        DamageSource damageSource = this.getDamageSources().arrow(this, (Entity) (entity2 != null ? entity2 : this));
        if (this.getWeaponStack() != null) {
            World var9 = this.getWorld();
            if (var9 instanceof ServerWorld serverWorld) {
                d = EnchantmentHelper.getDamage(serverWorld, this.getWeaponStack(), entity, damageSource, (float) d);
            }
        }
        d = (float) (d * NetheriteExtensionConfig.INSTANCE.damage.trident_damage_multiplier + NetheriteExtensionConfig.INSTANCE.damage.trident_damage_addition);

        int i = MathHelper.ceil(MathHelper.clamp((double) f * d, (double) 0.0F, (double) Integer.MAX_VALUE));
        if (this.getPierceLevel() > 0) {
            if (this.piercedEntities == null) {
                this.piercedEntities = new IntOpenHashSet(5);
            }

            if (this.piercingKilledEntities == null) {
                this.piercingKilledEntities = Lists.newArrayListWithCapacity(5);
            }

            if (this.piercedEntities.size() >= this.getPierceLevel() + 1) {
                this.discard();
                return;
            }

            this.piercedEntities.add(entity.getId());
        }

        if (this.isCritical()) {
            long l = this.random.nextInt(i / 2 + 2);
            i = (int) Math.min(l + (long) i, 2147483647L);
        }
        if (entity2 instanceof LivingEntity livingEntity) livingEntity.onAttacking(entity);
        boolean bl = entity.getType() == EntityType.ENDERMAN;
        int j = entity.getFireTicks();
        if (this.isOnFire() && !bl) entity.setOnFireFor(5.0F);

        if (entity.damage(damageSource, (float) i)) {
            if (bl) return;
            if (entity instanceof LivingEntity livingEntity2) {
                if (!this.getWorld().isClient && this.getPierceLevel() <= 0)
                    livingEntity2.setStuckArrowCount(livingEntity2.getStuckArrowCount() + 1);
                this.knockback(livingEntity2, damageSource);
                World var13 = this.getWorld();
                if (var13 instanceof ServerWorld serverWorld2)
                    EnchantmentHelper.onTargetDamaged(serverWorld2, livingEntity2, damageSource, this.getWeaponStack());
                this.onHit(livingEntity2);
                if (livingEntity2 != entity2 && livingEntity2 instanceof PlayerEntity && entity2 instanceof ServerPlayerEntity serverPlayer && !this.isSilent())
                    serverPlayer.networkHandler.sendPacket(new GameStateChangeS2CPacket(GameStateChangeS2CPacket.PROJECTILE_HIT_PLAYER, 0.0F));
                if (!entity.isAlive() && this.piercingKilledEntities != null)
                    this.piercingKilledEntities.add(livingEntity2);
                if (!this.getWorld().isClient && entity2 instanceof ServerPlayerEntity serverPlayerEntity)
                    if (this.piercingKilledEntities != null && this.isShotFromCrossbow())
                        Criteria.KILLED_BY_CROSSBOW.trigger(serverPlayerEntity, this.piercingKilledEntities);
                    else if (!entity.isAlive() && this.isShotFromCrossbow())
                        Criteria.KILLED_BY_CROSSBOW.trigger(serverPlayerEntity, Arrays.asList(entity));
            }

            this.playSound(this.getSound(), 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
            if (this.getPierceLevel() <= 0) {
                this.discard();
            }
        } else {
            entity.setFireTicks(j);
            this.deflect(ProjectileDeflection.SIMPLE, entity, this.getOwner(), false);
            this.setVelocity(this.getVelocity().multiply(0.2));
            if (!this.getWorld().isClient && this.getVelocity().lengthSquared() < 1.0E-7) {
                if (this.pickupType == PersistentProjectileEntity.PickupPermission.ALLOWED) {
                    this.dropStack(this.asItemStack(), 0.1F);
                }

                this.discard();
            }
        }
    }

    @Override
    protected boolean tryPickup(PlayerEntity player) {
        return super.tryPickup(player) || this.isNoClip() && this.isOwner(player) && player.getInventory().insertStack(this.asItemStack());
    }

    @Override
    protected SoundEvent getHitSound() {
        return SoundEvents.ITEM_TRIDENT_HIT_GROUND;
    }

    @Override
    public void onPlayerCollision(PlayerEntity player) {
        if (this.isOwner(player) || this.getOwner() == null)
            super.onPlayerCollision(player);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        if (nbt.contains("Trident", 10))
            this.tridentStack = ItemStack.fromNbtOrEmpty(this.getWorld().getRegistryManager(), nbt.getCompound("Trident"));
        this.dealtDamage = nbt.getBoolean("DealtDamage");
        this.dataTracker.set(LOYALTY, this.getLoyalty(this.tridentStack));
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.put("Trident", this.tridentStack.encodeAllowEmpty(this.getWorld().getRegistryManager()));
        nbt.putBoolean("DealtDamage", this.dealtDamage);
    }

    @Override
    public void age() {
        int i = this.dataTracker.get(LOYALTY);
        if (this.pickupType != PickupPermission.ALLOWED || i <= 0)
            super.age();
    }

    @Override
    protected float getDragInWater() {
        return 0.99F;
    }

    @Override
    public boolean shouldRender(double cameraX, double cameraY, double cameraZ) {
        return true;
    }
}
