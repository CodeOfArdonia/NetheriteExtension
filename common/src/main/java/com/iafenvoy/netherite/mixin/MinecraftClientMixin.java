package com.iafenvoy.netherite.mixin;

import com.iafenvoy.netherite.registry.NetheriteRenderers;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.RunArgs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {
    @Inject(method = "<init>", at = @At("TAIL"))
    private void onClientInitComplete(RunArgs args, CallbackInfo ci) {
//        NetheriteRenderers.registerBuiltinItemRenderers((MinecraftClient) (Object) this);
    }
}
