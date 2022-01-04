package net.gamma02.zombieinfusion.common.Entities;

import net.gamma02.zombieinfusion.ZombieInfusions;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.DefaultedRegistry;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(ZombieInfusions.modid)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEntities
{
    public static EntityType<DNAZombie> DNA_ZOMBIE_ENTITY_TYPE = null;

    @SubscribeEvent
    public static void registerEntities(final RegistryEvent.Register<EntityType<?>> evt){
        System.out.println("registering?????? maybe?????");
        DNA_ZOMBIE_ENTITY_TYPE = build("dnazombie", EntityType.Builder.<DNAZombie>create(DNAZombie::new, EntityClassification.MONSTER).size(EntityType.ZOMBIE.getWidth(), EntityType.ZOMBIE.getHeight()));
        evt.getRegistry().registerAll(DNA_ZOMBIE_ENTITY_TYPE);

    }
    @SubscribeEvent public static void registerAttributes(final EntityAttributeCreationEvent event){
        event.put(DNA_ZOMBIE_ENTITY_TYPE, DNAZombie.func_234342_eQ_().create());
    }

    private static <T extends Entity> EntityType<T> build(final String name, final EntityType.Builder<T> builder) {
        final ResourceLocation registryName = new ResourceLocation(ZombieInfusions.modid, name);

        final EntityType<T> entityType = builder
                .build(registryName.toString());

        entityType.setRegistryName(registryName);

        return entityType;
    }




}
