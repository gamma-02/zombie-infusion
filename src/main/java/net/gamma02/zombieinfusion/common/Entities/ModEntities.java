package net.gamma02.zombieinfusion.common.Entities;

import net.gamma02.zombieinfusion.ZombieInfusions;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(ZombieInfusions.modid)
public class ModEntities
{
    public static EntityType<DNAZombie> DNA_ZOMBIE_ENTITY_TYPE = null;

    @SubscribeEvent
    public void registerEntities(RegistryEvent.Register<EntityType<?>> evt){
        DNA_ZOMBIE_ENTITY_TYPE = EntityType.Builder.<DNAZombie>create(DNAZombie::new, EntityClassification.MONSTER).size(EntityType.ZOMBIE.getWidth(), EntityType.ZOMBIE.getHeight()).disableSummoning().build(null);
        evt.getRegistry().registerAll(DNA_ZOMBIE_ENTITY_TYPE);

    }
    @SubscribeEvent public void registerAttributes(EntityAttributeCreationEvent event){
        event.put(DNA_ZOMBIE_ENTITY_TYPE, DNAZombie.func_234342_eQ_().create());
    }




}
