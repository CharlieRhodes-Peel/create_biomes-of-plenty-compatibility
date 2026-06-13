package com.milkski.createbopbridge.events;

import com.milkski.createbopbridge.CreateBopCompatibilityMod;
import com.milkski.createbopbridge.fluids.ModFluids;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

@EventBusSubscriber(modid = CreateBopCompatibilityMod.MODID)
public class ModEvents {

    @SubscribeEvent
    public static void onEntityTick(EntityTickEvent.Post event) {
        if (!(event.getEntity() instanceof LivingEntity entity)) return;
        if (entity.level().isClientSide()) return;  // server-side only

        // Check if the entity is touching our fluid
        boolean inAcid = entity.isEyeInFluidType(ModFluids.SULFURIC_ACID_SOURCE.get().getFluidType())
                || entity.isInFluidType(ModFluids.SULFURIC_ACID_SOURCE.get().getFluidType());

        if (inAcid && entity.tickCount % 10 == 0) {  // damage every 0.5s
            entity.hurt(entity.damageSources().magic(), 2.0f);  // 1 heart per tick
        }
    }
}