package com.iafenvoy.netherite;

import com.iafenvoy.netherite.network.NetworkHelper;
import com.iafenvoy.netherite.registry.NetheriteItems;
import com.iafenvoy.netherite.registry.NetheriteRenderers;
import com.iafenvoy.netherite.registry.NetheriteScreenHandlers;
import dev.architectury.networking.NetworkManager;
import it.unimi.dsi.fastutil.ints.IntArraySet;
import it.unimi.dsi.fastutil.ints.IntSet;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.item.ItemStack;

@Environment(EnvType.CLIENT)
public class NetheriteExtensionClient {
    public static final IntSet NETHERITE_TRIDENT_LIST = new IntArraySet();

    public static void init() {
        NetheriteRenderers.registerEntityRenderers();
    }

    public static void process() {
        NetheriteRenderers.registerModelPredicates();
        NetheriteRenderers.registerBlockEntityRenderers();
        NetheriteScreenHandlers.initializeClient();
        NetworkManager.registerReceiver(NetworkManager.Side.S2C, NetworkHelper.NETHERITE_TRIDENT, (buf, context) -> {
            int id = buf.readInt();
            context.queue(() -> {
                ClientWorld world = MinecraftClient.getInstance().world;
                assert world != null;
                Entity entity = world.getEntityById(id);
                if (entity instanceof TridentEntity trident)
                    trident.tridentStack = new ItemStack(NetheriteItems.NETHERITE_TRIDENT.get());
                else NETHERITE_TRIDENT_LIST.add(id);
            });
        });
    }
}
