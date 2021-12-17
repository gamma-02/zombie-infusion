package net.gamma02.zombieinfusion;

import com.mojang.datafixers.types.Type;
import net.gamma02.zombieinfusion.client.screens.InfusionContainer;
import net.gamma02.zombieinfusion.client.screens.InfusonScreen;
import net.gamma02.zombieinfusion.common.Items.DNA;
import net.gamma02.zombieinfusion.common.Items.Syringe;
import net.gamma02.zombieinfusion.common.ModBlocks;
import net.gamma02.zombieinfusion.common.blocks.InfusionBlock;
import net.gamma02.zombieinfusion.common.blocks.InfusionBlockEntity;
import net.gamma02.zombieinfusion.common.recipes.InfusionRecipe;
import net.gamma02.zombieinfusion.common.recipes.InfusionRecipeType;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.datafix.TypeReferences;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.system.CallbackI;

import java.net.Proxy;
import java.util.stream.Collectors;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("zombie-infusion")
public class ZombieInfusions
{

    // Directly reference a log4j logger.
    //is this REALLY a good idea??? if $(<command>) gets put into the log........
    private static final Logger LOGGER = LogManager.getLogger();




    public static final String modid = "zombie-infusion";
    public static final IRecipeType<InfusionRecipe> INFUSION_RECIPE_TYPE = new InfusionRecipeType();

    public static ItemGroup ModGroup = new ItemGroup(ItemGroup.getGroupCountSafe(), "zombie_infusions")
    {
        @Override public ItemStack createIcon()
        {
            return DNA_ITEM.get().getDefaultInstance();
        }
    };

    public static DeferredRegister<Block> MODBLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, modid);
    public static DeferredRegister<Item> MODITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, modid);
    public static DeferredRegister<ContainerType<?>> MODCONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, modid);


    public static RegistryObject<Block> INFUSION_BLOCK = MODBLOCKS.register("zombie_infusor", () -> {
        return (Block) (new InfusionBlock(AbstractBlock.Properties.from(Blocks.IRON_BLOCK)));
    });

    public static RegistryObject<Item> SYRINGE = MODITEMS.register("syringe", () -> new Syringe(new Item.Properties().group(ModGroup).maxStackSize(1)));
    public static RegistryObject<Item> DNA_ITEM = MODITEMS.register("zombie_dna", () -> new DNA(new Item.Properties().group(ModGroup).maxStackSize(32).containerItem(SYRINGE.get())));
    public static RegistryObject<ContainerType<InfusionContainer>> INFUSION_CONTAINER = MODCONTAINERS.register("zombie_infusors", () -> new ContainerType<InfusionContainer>(InfusionContainer::new));


    public ZombieInfusions()
    {
        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        // Register the enqueueIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        // Register the processIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
        // Register the doClientStuff method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);


        // Register ourselves for server and other game events we are interested in
        MODCONTAINERS.register(FMLJavaModLoadingContext.get().getModEventBus());
        MODBLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        MODITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());

        registerClient();

        MinecraftForge.EVENT_BUS.register(this);
    }


    @OnlyIn(Dist.CLIENT)
    private void registerClient(){

    }
    private void setup(final FMLCommonSetupEvent event)
    {
        // some preinit code
        LOGGER.info("HELLO FROM PREINIT");
        LOGGER.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
        ScreenManager.registerFactory(INFUSION_CONTAINER.get(), InfusonScreen::new);
    }


    @OnlyIn(Dist.CLIENT)
    private void doClientStuff(final FMLClientSetupEvent event)
    {
        ScreenManager.registerFactory(INFUSION_CONTAINER.get(), InfusonScreen::new);
        // do something that can only be done on the client

        LOGGER.info("Got game settings {}", event.getMinecraftSupplier().get().gameSettings);
    }

    private void enqueueIMC(final InterModEnqueueEvent event)
    {
        // some example code to dispatch IMC to another mod
        InterModComms.sendTo("zombie-infusion", "helloworld", () -> {
            LOGGER.info("Hello world from the MDK");
            return "Hello world";
        });
    }

    private void processIMC(final InterModProcessEvent event)
    {
        // some example code to receive and process InterModComms from other mods
        LOGGER.info("Got IMC {}",
                event.getIMCStream().map(m -> m.getMessageSupplier().get()).collect(Collectors.toList()));
    }


    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent public void onServerStarting(FMLServerStartingEvent event)
    {
        // do something when the server starts
        LOGGER.info("HELLO from server starting");
    }

    // You can use EventBusSubscriber to automatically subscribe events on the contained class (this is subscribing to the MOD
    // Event bus for receiving Registry Events)
    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD) public static class RegistryEvents
    {
        @SubscribeEvent public static void onBlocksRegistry(final RegistryEvent.Register<Block> blockRegistryEvent)
        {
            // register a new block here
            LOGGER.info("HELLO from Register Block");
        }
        @SubscribeEvent
        public void registerRecipeSerializers (RegistryEvent.Register<IRecipeSerializer<?>> event) {

            // Vanilla has a registry for recipe types, but it does not actively use this registry.
            // While this makes registering your recipe type an optional step, I recommend
            // registering it anyway to allow other mods to discover your custom recipe types.
            Registry.register(Registry.RECIPE_TYPE, new ResourceLocation(INFUSION_RECIPE_TYPE.toString()), INFUSION_RECIPE_TYPE);

            // Register the recipe serializer. This handles from json, from packet, and to packet.
            event.getRegistry().register(InfusionRecipe.SERIALIZER);
        }
    }
}
