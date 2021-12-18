package net.gamma02.zombieinfusion.common.Entities;


import net.gamma02.zombieinfusion.common.Items.DNA;
import net.gamma02.zombieinfusion.common.helpers.NBTHelper;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.Random;

public class DNAZombie extends ZombieEntity
{
    private static final DataParameter<ItemStack> InfuesdItem = EntityDataManager.createKey(DNAZombie.class, DataSerializers.ITEMSTACK);
    private static final DataParameter<Float> InfuesdPrecent = EntityDataManager.createKey(DNAZombie.class, DataSerializers.FLOAT);
    public DNAZombie(EntityType<? extends DNAZombie> type, World worldIn)
    {
        super(type, worldIn);
    }
    public DNAZombie(EntityType<? extends DNAZombie> type, World worldIn, Item itemIn, float floatIn){
        super(type, worldIn);
        this.dataManager.set(InfuesdPrecent, floatIn);
        this.dataManager.set(InfuesdItem, itemIn.getDefaultInstance());

    }
    public void registerData(){
        super.registerData();
        this.dataManager.register(InfuesdPrecent, 0.0f);
        this.dataManager.register(InfuesdItem, ItemStack.EMPTY);
    }

    @Override public void onDeath(DamageSource cause)
    {
        double randNum =  MathHelper.nextInt(new Random(), 0, 100);
        float f = this.dataManager.get(InfuesdPrecent);
        ItemStack toDrop = this.dataManager.get(InfuesdItem);
        if(f <= 0.1 && randNum <= 10){
            toDrop.setCount(1);
            this.entityDropItem(toDrop);
        }else if( f <= 0.2 && randNum <= 20){
            toDrop.setCount(MathHelper.nextInt(new Random(), 1, 5));
            this.entityDropItem(toDrop);
        }else if( f <= 0.3 && randNum <= 30){
            toDrop.setCount(MathHelper.nextInt(new Random(), 2, 8));
            this.entityDropItem(toDrop);
        }else if( f <= 0.4 && randNum <= 40){
            toDrop.setCount(MathHelper.nextInt(new Random(), 5, 14));
            this.entityDropItem(toDrop);
        }else if( f <= 0.5 && randNum <= 50){
            toDrop.setCount(15);
            this.entityDropItem(toDrop);
        }else if( f <= 0.6 ){
            toDrop.setCount(4);
            this.entityDropItem(toDrop);
        }else if( f <= 0.7){
            toDrop.setCount(8);
            this.entityDropItem(toDrop);
        }else if( f <= 0.8){
            toDrop.setCount(16);
            this.entityDropItem(toDrop);
        }else if( f <= 0.9){
            toDrop.setCount(32);
            this.entityDropItem(toDrop);
        }else{
            toDrop.setCount(64);
        }
    }

    @Override public void read(CompoundNBT compound)
    {
        this.dataManager.set(InfuesdItem, ForgeRegistries.ITEMS.getValue(new ResourceLocation(compound.getString(NBTHelper.InfusedNamespaceKey), compound.getString(NBTHelper.InfusedEntryKey))).getDefaultInstance());
        this.dataManager.set(InfuesdPrecent, compound.getFloat(NBTHelper.InfusedPercentKey));

        super.read(compound);
    }
}


