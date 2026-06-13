package com.milkski.createbopbridge;

import com.milkski.createbopbridge.fluids.ModFluidTypes;
import com.milkski.createbopbridge.fluids.ModFluids;
import com.milkski.createbopbridge.fluids.SulfuricAcidBlock;
import com.milkski.createbopbridge.items.SulfuricBoneMealItem;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.LiquidBlock;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(CreateBopCompatibilityMod.MODID)
public class CreateBopCompatibilityMod {
    // Define mod id in a common place for everything to reference
    public static final String MODID = "create_bop_bridge";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();
    // Create a Deferred Register to hold Blocks which will all be registered under the "examplemod" namespace
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MODID);
    // Create a Deferred Register to hold Items which will all be registered under the "examplemod" namespace
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MODID);
    // Create a Deferred Register to hold CreativeModeTabs which will all be registered under the "examplemod" namespace
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

    // Creates a new Block with the id "examplemod:example_block", combining the namespace and path
    public static final DeferredBlock<Block> EXAMPLE_BLOCK = BLOCKS.registerSimpleBlock("example_block", BlockBehaviour.Properties.of().mapColor(MapColor.STONE));
    // Creates a new BlockItem with the id "examplemod:example_block", combining the namespace and path
    public static final DeferredItem<BlockItem> EXAMPLE_BLOCK_ITEM = ITEMS.registerSimpleBlockItem("example_block", EXAMPLE_BLOCK);

    // ------- ITEMS ------
    public static final DeferredItem<Item> ROSE_QUARTZ_NUGGET = ITEMS.registerSimpleItem("rose_quartz_nugget", new Item.Properties());
    public static final DeferredItem<Item> SULFUR = ITEMS.registerSimpleItem("sulfur", new Item.Properties());
    public static final DeferredItem<Item> SULFURIC_BONE_MEAL = ITEMS.register("sulfuric_bone_meal", () -> new SulfuricBoneMealItem(new Item.Properties()));
    public static final DeferredItem<Item> WHITE_SANDPAPER = ITEMS.registerSimpleItem("white_sandpaper", new Item.Properties());
    public static final DeferredItem<Item> ORANGE_SANDPAPER = ITEMS.registerSimpleItem("orange_sandpaper", new Item.Properties());
    public static final DeferredItem<Item> BLACK_SANDPAPER = ITEMS.registerSimpleItem("black_sandpaper", new Item.Properties());



    // -------- Fluid ------
    // LiquidBlock - the in-world block that holds the fluid
    public static final DeferredBlock<LiquidBlock> SULFURIC_ACID_BLOCK =
            BLOCKS.register("sulfuric_acid",
                    () -> new SulfuricAcidBlock(ModFluids.SULFURIC_ACID_SOURCE.get(),
                            BlockBehaviour.Properties.of()
                                    .replaceable()
                                    .noCollission()
                                    .strength(100f)
                                    .pushReaction(net.minecraft.world.level.material.PushReaction.DESTROY)
                                    .noLootTable()
                                    .liquid()
                                    .sound(net.minecraft.world.level.block.SoundType.EMPTY)));

    // BucketItem - what you carry the fluid around in
    public static final DeferredItem<BucketItem> SULFURIC_ACID_BUCKET =
            ITEMS.register("sulfuric_acid_bucket",
                    () -> new BucketItem(ModFluids.SULFURIC_ACID_SOURCE.get(),
                            new Item.Properties()
                                    .craftRemainder(net.minecraft.world.item.Items.BUCKET)
                                    .stacksTo(1)));


    // Creates a creative tab with the id "examplemod:example_tab" for the example item, that is placed after the combat tab
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> CREATE_BOP_BRIDGE_TAB = CREATIVE_MODE_TABS.register("example_tab", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.create_bop_bridge")) //The language key for the title of your CreativeModeTab
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> ROSE_QUARTZ_NUGGET.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                output.accept(ROSE_QUARTZ_NUGGET.get()); // Add the example item to the tab. For your own tabs, this method is preferred over the event
                output.accept(SULFUR.get());
                output.accept(SULFURIC_BONE_MEAL.get());
                output.accept(SULFURIC_ACID_BUCKET);
                output.accept(WHITE_SANDPAPER);
                output.accept(ORANGE_SANDPAPER);
                output.accept(BLACK_SANDPAPER);
            }).build());

    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public CreateBopCompatibilityMod(IEventBus modEventBus, ModContainer modContainer) {
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Register the Deferred Register to the mod event bus so blocks get registered
        BLOCKS.register(modEventBus);
        // Register the Deferred Register to the mod event bus so items get registered
        ITEMS.register(modEventBus);
        // Register the Deferred Register to the mod event bus so tabs get registered
        CREATIVE_MODE_TABS.register(modEventBus);

        //Registers Fluids
        ModFluidTypes.register(modEventBus);
        ModFluids.register(modEventBus);

        // Register ourselves for server and other game events we are interested in.
        // Note that this is necessary if and only if we want *this* class (ExampleMod) to respond directly to events.
        // Do not add this line if there are no @SubscribeEvent-annotated functions in this class, like onServerStarting() below.
        NeoForge.EVENT_BUS.register(this);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);

        // Register our mod's ModConfigSpec so that FML can create and load the config file for us
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        // Some common setup code
        LOGGER.info("HELLO FROM COMMON SETUP");

        if (Config.LOG_DIRT_BLOCK.getAsBoolean()) {
            LOGGER.info("DIRT BLOCK >> {}", BuiltInRegistries.BLOCK.getKey(Blocks.DIRT));
        }

        LOGGER.info("{}{}", Config.MAGIC_NUMBER_INTRODUCTION.get(), Config.MAGIC_NUMBER.getAsInt());

        Config.ITEM_STRINGS.get().forEach((item) -> LOGGER.info("ITEM >> {}", item));
    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) {
            event.accept(EXAMPLE_BLOCK_ITEM);
        }
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }
}
