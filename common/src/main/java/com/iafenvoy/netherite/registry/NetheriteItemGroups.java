package com.iafenvoy.netherite.registry;

import com.iafenvoy.netherite.NetheriteExtension;
import dev.architectury.registry.CreativeTabRegistry;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public final class NetheriteItemGroups {
    public static final ItemGroup MAIN = CreativeTabRegistry.create(
            new Identifier(NetheriteExtension.MOD_ID, "main"),
            () -> new ItemStack(NetheriteItems.NETHERITE_ELYTRA.get())
    );
}
