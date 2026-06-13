package com.milkski.createbopbridge.client;

import com.milkski.createbopbridge.CreateBopCompatibilityMod;
import com.milkski.createbopbridge.fluids.ModFluidTypes;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

@EventBusSubscriber(modid = CreateBopCompatibilityMod.MODID,
        value = Dist.CLIENT)
public class ClientSetup {

    @SubscribeEvent
    public static void onRegisterClientExtensions(RegisterClientExtensionsEvent event) {
        event.registerFluidType(new IClientFluidTypeExtensions() {
            @Override
            public @NotNull ResourceLocation getStillTexture() {
                return ResourceLocation.parse("minecraft:block/water_still");
            }

            @Override
            public @NotNull ResourceLocation getFlowingTexture() {
                return ResourceLocation.parse("minecraft:block/water_flow");
            }

            @Override
            public int getTintColor() {
                return 0xFFF94F;
            }

            @Override
            public @NotNull Vector3f modifyFogColor(@NotNull Camera camera,
                                                    float partialTick, @NotNull ClientLevel level,
                                                    int renderDistance, float darkenWorldAmount,
                                                    @NotNull Vector3f fluidFogColor) {
                return new Vector3f(0.8f, 0.8f, 0.0f);
            }
        }, ModFluidTypes.SULFURIC_ACID_TYPE.get());
    }
}