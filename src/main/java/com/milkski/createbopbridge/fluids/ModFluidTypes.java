package com.milkski.createbopbridge.fluids;

import com.milkski.createbopbridge.CreateBopCompatibilityMod;
import net.minecraft.sounds.SoundEvents;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.SoundActions;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class ModFluidTypes {
    public static final DeferredRegister<FluidType> FLUID_TYPES =
            DeferredRegister.create(NeoForgeRegistries.FLUID_TYPES,
                    CreateBopCompatibilityMod.MODID);

    public static final DeferredHolder<FluidType, FluidType> SULFURIC_ACID_TYPE =
            FLUID_TYPES.register("sulfuric_acid_type",
                    () -> new FluidType(FluidType.Properties.create()
                            .density(1000)
                            .viscosity(1000)
                            .canSwim(false)
                            .canDrown(true)
                            .supportsBoating(false)
                            .sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL)
                            .sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY)
                    ));

    public static void register(IEventBus eventBus) {
        FLUID_TYPES.register(eventBus);
    }
}