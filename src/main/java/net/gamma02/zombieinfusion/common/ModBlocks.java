package net.gamma02.zombieinfusion.common;

import net.gamma02.zombieinfusion.ZombieInfusions;
import net.gamma02.zombieinfusion.common.blocks.CreativeGeneratorBlockEntity;
import net.gamma02.zombieinfusion.common.blocks.InfusionBlock;
import net.gamma02.zombieinfusion.common.blocks.InfusionBlockEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;


@ObjectHolder(ZombieInfusions.modid)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModBlocks
{
    public static TileEntityType<InfusionBlockEntity> INFUSION_BLOCK_ENTITY_TYPE;
    public static TileEntityType<CreativeGeneratorBlockEntity> CREATIVE_BLOCK_ENTITY_TYPE;
    @SubscribeEvent
    public static void registerTE(RegistryEvent.Register<TileEntityType<?>> evt)
    {
        System.out.println("registering?????? maybe?????");
        TileEntityType<InfusionBlockEntity> tempType = TileEntityType.Builder.create(InfusionBlockEntity::new, (ZombieInfusions.INFUSION_BLOCK.get())).build(null);
         CREATIVE_BLOCK_ENTITY_TYPE = TileEntityType.Builder.create(CreativeGeneratorBlockEntity::new, (ZombieInfusions.CREATIVE_GENERATOR.get())).build(null);
        tempType.setRegistryName(ZombieInfusions.modid, "infuser-tile-entity-type");
        CREATIVE_BLOCK_ENTITY_TYPE.setRegistryName(ZombieInfusions.modid, "creative-generator-type");
        INFUSION_BLOCK_ENTITY_TYPE = tempType;


        evt.getRegistry().registerAll(INFUSION_BLOCK_ENTITY_TYPE, CREATIVE_BLOCK_ENTITY_TYPE);

    }

}
//            event.getRegistry().registerAll(HELL_TRIDENT_ENTITY_L1, NEON_SWORD_ENTITY, ECHO_TRIDENT_ENTITY_L1, ECHO_TRIDENT_ENTITY_L2, HELL_TRIDENT_ENTITY_L2, HELL_TRIDENT_ENTITY_L3, ECHO_TRIDENT_ENTITY_L3, SLIMEBALL_PROJECTILE);