package com.milkski.createplenty.data.recipes;

import com.milkski.createplenty.CreateBopCompatibilityMod;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import java.util.concurrent.CompletableFuture;

public class ModVanillaRecipes extends RecipeProvider {
    public ModVanillaRecipes(PackOutput output,
                             CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput output) {
        // Look up Create's items from registry by ID
        Item createRoseQuartz = BuiltInRegistries.ITEM.get(
                ResourceLocation.fromNamespaceAndPath("create", "rose_quartz"));
        Item createSandpaper = BuiltInRegistries.ITEM.get(
                ResourceLocation.fromNamespaceAndPath("create", "sand_paper"));

        // Existing nugget recipe
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

        // Sandpaper from every sand variant we want to support
        addSandpaperRecipe(output, createSandpaper, Items.SAND, "sandpaper_from_sand");
        addSandpaperRecipe(output, createSandpaper, lookupItem("biomesoplenty", "black_sand"),
                "sandpaper_from_black_sand");
        addSandpaperRecipe(output, createSandpaper, lookupItem("biomesoplenty", "white_sand"),
                "sandpaper_from_white_sand");
        addSandpaperRecipe(output, createSandpaper, lookupItem("biomesoplenty", "orange_sand"),
                "sandpaper_from_orange_sand");
    }

    private void addSandpaperRecipe(RecipeOutput output, Item sandpaper,
                                    Item sand, String recipeName) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, sandpaper)
                .requires(Items.PAPER)
                .requires(sand)
                .unlockedBy("has_sand", has(sand))
                .save(output,
                        ResourceLocation.fromNamespaceAndPath(
                                CreateBopCompatibilityMod.MODID, recipeName));
    }

    private static Item lookupItem(String namespace, String path) {
        return BuiltInRegistries.ITEM.get(
                ResourceLocation.fromNamespaceAndPath(namespace, path));
    }


}