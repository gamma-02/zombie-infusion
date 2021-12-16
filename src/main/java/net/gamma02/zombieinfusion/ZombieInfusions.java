package net.gamma02.zombieinfusion;

import com.mojang.datafixers.types.Type;
import net.gamma02.zombieinfusion.common.Items.DNA;
import net.gamma02.zombieinfusion.common.Items.Syringe;
import net.gamma02.zombieinfusion.common.ModBlocks;
import net.gamma02.zombieinfusion.common.blocks.InfusionBlock;
import net.gamma02.zombieinfusion.common.blocks.InfusionBlockEntity;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Util;
import net.minecraft.util.datafix.TypeReferences;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.common.MinecraftForge;
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

import java.util.stream.Collectors;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("zombie-infusion") public class ZombieInfusions
{

    // Directly reference a log4j logger.
    //is this REALLY a good idea??? if $(<command>) gets put into the log........
    private static final Logger LOGGER = LogManager.getLogger();

    public static final String modid = "zombie-infusion";

    public static ItemGroup ModGroup = new ItemGroup(ItemGroup.getGroupCountSafe(), "zombie_infusions")
    {
        @Override public ItemStack createIcon()
        {
            return DNA_ITEM.get().getDefaultInstance();
        }
    };

    public static DeferredRegister<Block> MODBLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, modid);
    public static DeferredRegister<Item> MODITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, modid);


    public static RegistryObject<Block> INFUSION_BLOCK = MODBLOCKS.register("zombie_infusor", () -> {
        return (Block) (new InfusionBlock(AbstractBlock.Properties.from(Blocks.IRON_BLOCK)));
    });

    public static RegistryObject<Item> SYRINGE = MODITEMS.register("syringe", () -> new Syringe(new Item.Properties().group(ModGroup).maxStackSize(1)));
    public static RegistryObject<Item> DNA_ITEM = MODITEMS.register("zombie_dna", () -> new DNA(new Item.Properties().group(ModGroup).maxStackSize(32).containerItem(SYRINGE.get())));


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
        MODBLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        MODITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event)
    {
        // some preinit code
        LOGGER.info("HELLO FROM PREINIT");
        LOGGER.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
    }

    private void doClientStuff(final FMLClientSetupEvent event)
    {
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
    }
}
