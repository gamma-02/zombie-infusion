package net.gamma02.zombieinfusion.common.blocks;

import com.mojang.datafixers.types.Type;
import mcp.MethodsReturnNonnullByDefault;
import net.gamma02.zombieinfusion.ZombieInfusions;
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
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.energy.IEnergyStorage;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;

public class InfusionBlockEntity extends TileEntity implements ITickableTileEntity, IEnergyStorage, IInventory, INamedContainerProvider, INameable//if you are reading this, im a fabric dev and I WILL use Yarn names lmfao
{
    private NonNullList<ItemStack> inventory = NonNullList.withSize(2, ItemStack.EMPTY);
    private int capacity;
    private int maxExtract;
    private int maxRecive;
    private int energy;

    public InfusionBlockEntity(TileEntityType<? extends InfusionBlockEntity> tileEntityTypeIn, int capacity, int maxReceive, int maxExtract)
    {
        super(ModBlocks.INFUSION_BLOCK_ENTITY_TYPE);
        this.capacity = capacity;
        this.maxRecive = maxReceive;
        this.maxExtract = maxExtract;
        this.energy = Math.max(0 , Math.min(capacity, energy));
    }

    @Override

    @Nonnull public TileEntityType<?> getType(){
        return ModBlocks.INFUSION_BLOCK_ENTITY_TYPE;
    }


    public InfusionBlockEntity(){
        super(ModBlocks.INFUSION_BLOCK_ENTITY_TYPE);
    }




    @Override public void tick()
    {
        if(!Objects.requireNonNull(this.getWorld()).isRemote){
            //TODO : do recipie stuff here, will figure out later
        }
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate)
    {
        if (!canReceive())
            return 0;

        int energyReceived = Math.min(capacity - energy, Math.min(this.maxRecive, maxReceive));
        if (!simulate)
            energy += energyReceived;
        return energyReceived;
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate)
    {
        if (!canExtract())
            return 0;

        int energyExtracted = Math.min(energy, Math.min(this.maxExtract, maxExtract));
        if (!simulate)
            energy -= energyExtracted;
        return energyExtracted;
    }

    @Override
    public int getEnergyStored()
    {
        return energy;
    }

    @Override
    public int getMaxEnergyStored()
    {
        return capacity;
    }

    @Override
    public boolean canExtract()
    {
        return this.maxExtract > 0;
    }

    @Override
    public boolean canReceive()
    {
        return this.maxRecive > 0;
    }

    @Override public int getSizeInventory()
    {
        return 2;
    }

    @Override public boolean isEmpty()
    {
        return this.inventory.stream().allMatch(ItemStack::isEmpty);
    }

    @Override public ItemStack getStackInSlot(int index)
    {
        return this.inventory.get(index);
    }

    @Override @Nonnull public ItemStack decrStackSize(int index, int count)
    {

        return ItemStackHelper.getAndSplit(this.inventory, index, count);
    }

    @Override @Nonnull public ItemStack removeStackFromSlot(int index)
    {
        return ItemStackHelper.getAndRemove(this.inventory, index);
    }

    @Override public void setInventorySlotContents(int index, @Nonnull ItemStack stack)
    {
        this.inventory.set(index, stack);
    }

    @Override public boolean isUsableByPlayer( @Nonnull PlayerEntity player)
    {
        return true;
    }

    @Override public void clear()
    {
        this.inventory.removeIf((itemStack) -> !itemStack.equals(ItemStack.EMPTY));
    }

    @Override @Nonnull public ITextComponent getName()
    {
        return ITextComponent.getTextComponentOrEmpty("DNA Infuser");
    }

    @Override @Nonnull public ITextComponent getDisplayName()
    {
        return ITextComponent.getTextComponentOrEmpty("DNA Infuser");
    }

    @Nullable @Override public Container createMenu(int p_createMenu_1_, @Nonnull PlayerInventory p_createMenu_2_,
           @Nonnull PlayerEntity p_createMenu_3_)
    {
        return null;
    }
}
