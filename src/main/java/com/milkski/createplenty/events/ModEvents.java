package com.milkski.createplenty.events;

import com.milkski.createplenty.CreateBopCompatibilityMod;
import com.milkski.createplenty.fluids.ModFluids;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;

@EventBusSubscriber(modid = CreateBopCompatibilityMod.MODID)
public class ModEvents {

    public static final ResourceKey<DamageType> SULFURIC_ACID_DAMAGE =
            ResourceKey.create(Registries.DAMAGE_TYPE,
                    ResourceLocation.fromNamespaceAndPath(
                            CreateBopCompatibilityMod.MODID, "sulfuric_acid"));

    @SubscribeEvent
    public static void onEntityTick(EntityTickEvent.Post event) {
        if (!(event.getEntity() instanceof LivingEntity entity)) return;
        if (entity.level().isClientSide()) return;  // server-side only

        // Check if the entity is touching our fluid
        boolean inAcid = entity.isEyeInFluidType(ModFluids.SULFURIC_ACID_SOURCE.get().getFluidType())
                || entity.isInFluidType(ModFluids.SULFURIC_ACID_SOURCE.get().getFluidType());

        if (inAcid && entity.tickCount % 10 == 0) {  // damage every 0.5s
            DamageSource acidDamage = new DamageSource(
                    entity.level().registryAccess()
                            .registryOrThrow(Registries.DAMAGE_TYPE)
                            .getHolderOrThrow(SULFURIC_ACID_DAMAGE));
            entity.hurt(acidDamage, 2.0f);
        }
    }
}