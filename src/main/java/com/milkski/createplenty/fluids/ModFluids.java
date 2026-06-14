package com.milkski.createplenty.fluids;

import com.milkski.createplenty.CreateBopCompatibilityMod;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FlowingFluid;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.minecraft.core.registries.BuiltInRegistries;

public class ModFluids {
    public static final DeferredRegister<Fluid> FLUIDS =
            DeferredRegister.create(BuiltInRegistries.FLUID, CreateBopCompatibilityMod.MODID);

    // The properties object links source, flowing, block, and bucket together.
    // We use suppliers because the things it references don't exist yet at this point.
    public static final BaseFlowingFluid.Properties SULFURIC_ACID_PROPERTIES =
            new BaseFlowingFluid.Properties(
                    ModFluidTypes.SULFURIC_ACID_TYPE,
                    () -> ModFluids.SULFURIC_ACID_SOURCE.get(),
                    () -> ModFluids.SULFURIC_ACID_FLOWING.get()
            )
                    .block(() -> CreateBopCompatibilityMod.SULFURIC_ACID_BLOCK.get())
                    .bucket(() -> CreateBopCompatibilityMod.SULFURIC_ACID_BUCKET.get());

    public static final DeferredHolder<Fluid, FlowingFluid> SULFURIC_ACID_SOURCE =
            FLUIDS.register("sulfuric_acid",
                    () -> new BaseFlowingFluid.Source(SULFURIC_ACID_PROPERTIES));

    public static final DeferredHolder<Fluid, FlowingFluid> SULFURIC_ACID_FLOWING =
            FLUIDS.register("flowing_sulfuric_acid",
                    () -> new BaseFlowingFluid.Flowing(SULFURIC_ACID_PROPERTIES));

    public static void register(IEventBus eventBus) {
        FLUIDS.register(eventBus);
    }
}