package com.milkski.createbopbridge.data.recipes;

import com.milkski.createbopbridge.CreateBopCompatibilityMod;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

import java.util.concurrent.CompletableFuture;

public class ModVanillaRecipes extends RecipeProvider {
    public ModVanillaRecipes(PackOutput output,
                             CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput output) {
        // Look up Create's rose quartz item from the registry by ID
        Item createRoseQuartz = BuiltInRegistries.ITEM.get(
                ResourceLocation.fromNamespaceAndPath("create", "rose_quartz"));

        // 9 nuggets in 3x3 -> 1 Create rose quartz
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, createRoseQuartz)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#', CreateBopCompatibilityMod.ROSE_QUARTZ_NUGGET.get())
                .unlockedBy("has_nugget",
                        has(CreateBopCompatibilityMod.ROSE_QUARTZ_NUGGET.get()))
                .save(output,
                        ResourceLocation.fromNamespaceAndPath(
                                CreateBopCompatibilityMod.MODID, "rose_quartz_from_nuggets"));
    }


}