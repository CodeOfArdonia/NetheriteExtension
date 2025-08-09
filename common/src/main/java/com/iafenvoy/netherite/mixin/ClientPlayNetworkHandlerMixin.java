package com.iafenvoy.netherite.mixin;

import com.iafenvoy.netherite.NetheriteExtensionClient;
import com.iafenvoy.netherite.registry.NetheriteItems;
import com.llamalad7.mixinextras.sugar.Local;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(ClientPlayNetworkHandler.class)
public abstract class ClientPlayNetworkHandlerMixin {
    @Inject(method = "onEntitySpawn", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayNetworkHandler;playSpawnSound(Lnet/minecraft/entity/Entity;)V"))
    public void onEntitySpawnMixin(EntitySpawnS2CPacket packet, CallbackInfo ci, @Local Entity entity) {
        if (entity instanceof TridentEntity trident && NetheriteExtensionClient.NETHERITE_TRIDENT_LIST.contains(trident.getId())) {
            NetheriteExtensionClient.NETHERITE_TRIDENT_LIST.remove(trident.getId());
            trident.tridentStack = new ItemStack(NetheriteItems.NETHERITE_TRIDENT.get());
        }
    }

    @Inject(method = "onPlayerRespawn", at = @At("RETURN"))
    private void clearList(CallbackInfo ci) {
        NetheriteExtensionClient.NETHERITE_TRIDENT_LIST.clear();
    }
}
