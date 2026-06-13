package com.milkski.createbopbridge.data;

import com.milkski.createbopbridge.CreateBopCompatibilityMod;
import com.milkski.createbopbridge.data.recipes.ModVanillaRecipes;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = CreateBopCompatibilityMod.MODID)
public class DataGenerators {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        // Vanilla recipes (your existing 3x3 nugget recipe)
        generator.addProvider(event.includeServer(),
                new ModVanillaRecipes(packOutput, lookupProvider));
    }
}