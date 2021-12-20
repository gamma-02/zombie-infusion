package net.gamma02.zombieinfusion.common.blocks;

import net.gamma02.zombieinfusion.common.ModBlocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.INameable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.energy.IEnergyStorage;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class CreativeGeneratorBlockEntity extends TileEntity
        implements ITickableTileEntity, IEnergyStorage, IInventory, INamedContainerProvider, INameable
{

    public final NonNullList<ItemStack> inventory = NonNullList.withSize(1, ItemStack.EMPTY);
    private int capacity;
    private int maxExtract;
    private int maxRecive;
    private int energy;

    public CreativeGeneratorBlockEntity(TileEntityType<? extends CreativeGeneratorBlockEntity> tileEntityTypeIn/*, int capacity, int maxReceive, int maxExtract*/)
    {
        super(ModBlocks.CREATIVE_BLOCK_ENTITY_TYPE);

//        this.capacity = capacity;
//        this.maxRecive = maxReceive;
//        this.maxExtract = maxExtract;
//        this.energy = Math.max(0 , Math.min(capacity, energy));
    }
    public CreativeGeneratorBlockEntity(){
        super(ModBlocks.CREATIVE_BLOCK_ENTITY_TYPE);
    }


    @Override public int getSizeInventory()
    {
        return 1;
    }

    @Override public boolean isEmpty()
    {
        return false;
    }

    @Override public ItemStack getStackInSlot(int index)
    {
        return this.inventory.get(index);
    }

    @Override public ItemStack decrStackSize(int index, int count)
    {
        return ItemStackHelper.getAndSplit(this.inventory, index, count);
    }

    @Override public ItemStack removeStackFromSlot(int index)
    {
        return this.inventory.remove(index);
    }

    @Override public void setInventorySlotContents(int index, ItemStack stack)
    {
        this.inventory.set(index, stack);
    }

    @Override public boolean isUsableByPlayer(PlayerEntity player)
    {
        return true;
    }

    @Override public void clear()
    {
        this.inventory.set(0, ItemStack.EMPTY);
    }

    @Override @Nonnull public ITextComponent getName()
    {
        return ITextComponent.getTextComponentOrEmpty("Creative Generator");
    }

    @Override @Nonnull public ITextComponent getDisplayName()
    {
        if(this.getCustomName() != null)
        {
            return this.getCustomName();
        }else{
            return this.getName();
        }
    }

    @Nullable @Override public Container createMenu(int p_createMenu_1_, PlayerInventory p_createMenu_2_,
            PlayerEntity p_createMenu_3_)
    {
        return null;
    }

    @Override public void tick()
    {

    }

    @Override public int receiveEnergy(int maxReceive, boolean simulate)
    {
        return 0;
    }

    @Override public int extractEnergy(int maxExtract, boolean simulate)
    {
        return maxExtract;
    }

    @Override public int getEnergyStored()
    {
        return 2147483647;
    }

    @Override public int getMaxEnergyStored()
    {
        return 2147483647;
    }

    @Override public boolean canExtract()
    {
        return true;
    }

    @Override public boolean canReceive()
    {
        return false;
    }
}
