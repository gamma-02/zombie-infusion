package net.gamma02.zombieinfusion.common.blocks;

import net.gamma02.zombieinfusion.ZombieInfusions;
import net.gamma02.zombieinfusion.common.Items.DNA;
import net.gamma02.zombieinfusion.common.ModBlocks;
import net.gamma02.zombieinfusion.common.helpers.NBTHelper;
import net.gamma02.zombieinfusion.common.recipes.InfusionRecipe;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.INameable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.energy.IEnergyStorage;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;

public class InfusionBlockEntity extends TileEntity implements ITickableTileEntity, IEnergyStorage, IInventory, INamedContainerProvider, INameable
        //if you are reading this, im a fabric dev, and I WILL use Yarn names lmfao
{
    public final NonNullList<ItemStack> inventory = NonNullList.withSize(3, ItemStack.EMPTY);
    private int capacity;
    private int maxExtract;
    private int maxRecive;
    private int energy;
    private int craftingTicks = 0;

    public InfusionBlockEntity(TileEntityType<? extends InfusionBlockEntity> tileEntityTypeIn, int capacity, int maxReceive, int maxExtract)
    {
        super(ModBlocks.INFUSION_BLOCK_ENTITY_TYPE);
        System.out.println("intended tile entity type: "+tileEntityTypeIn);
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
        this.energy = 50000;
        if(!Objects.requireNonNull(this.getWorld()).isRemote && this.hasDNA()){
            List<InfusionRecipe> recipes = this.getWorld().getRecipeManager().getRecipes(ZombieInfusions.INFUSION_RECIPE_TYPE, this, this.getWorld());


            for (InfusionRecipe element:
                 recipes)
            {


                if(element.matches(this, Objects.requireNonNull(world))){
                    this.energy -= element.energy/element.ticks;
                    System.out.println("working");
                    int shrinkAmount = 0;
                    float infusedpercent = NBTHelper.getInfusedPercent(this.getStackInSlot(0));
                    if(infusedpercent <= 0.1){
                        shrinkAmount = 10;
                    }else if(infusedpercent <=0.2){
                        shrinkAmount = 15;
                    }else if(infusedpercent <=0.3){
                        shrinkAmount = 23;
                    }else if(infusedpercent <=0.4){
                        shrinkAmount = 35;
                    }else if(infusedpercent <=0.5){
                        shrinkAmount = 52;
                    }else if(infusedpercent <= 0.6){
                        shrinkAmount = 78;
                    }else if(infusedpercent <= 0.7){
                        shrinkAmount = 117;
                    }else if(infusedpercent <= 0.8){
                        shrinkAmount = 175;
                    }else if(infusedpercent <= 0.9){
                        shrinkAmount = 262;
                    }
                    if(this.getInputItem().getCount() > 1)
                    {
                        this.inventory.get(1).setCount(this.inventory.get(1).getCount() - shrinkAmount / element.ticks);
                    }


                    this.craftingTicks++;
                    if(craftingTicks>= element.ticks){
                        this.craftingTicks = 0;
                        NBTHelper.Infuse(element.infuser, infusedpercent+0.1f, this.getStackInSlot(0));
                        this.inventory.set(2, this.getStackInSlot(0));
                        this.inventory.set(0, ItemStack.EMPTY);



                    }
                }
            }
        }
        this.energy = 50000;
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
        return this.inventory.size();
    }

    @Override public boolean isEmpty()
    {
        return this.inventory.stream().allMatch(ItemStack::isEmpty);
    }

    @Override @Nonnull public ItemStack getStackInSlot(int index)
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
        if(this.getCustomName() != null)
        {
            return this.getCustomName();
        }else{
            return this.getName();
        }
    }

    @Nullable @Override public Container createMenu(int p_createMenu_1_, @Nonnull PlayerInventory p_createMenu_2_,
           @Nonnull PlayerEntity p_createMenu_3_)
    {
        return new InfusionContainer(p_createMenu_1_, p_createMenu_2_, this, this);
    }

    @Nonnull public ItemStack getInputItem(){
        return this.inventory.get(1);
    }
    public boolean hasDNA(){
        return this.inventory.get(0).getItem() == ZombieInfusions.DNA_ITEM.get();
    }
    public void read(BlockState state, CompoundNBT nbt) {
        super.read(state, nbt);

        ItemStackHelper.loadAllItems(nbt, this.inventory);


    }

    public CompoundNBT write(CompoundNBT compound) {
        super.write(compound);

        ItemStackHelper.saveAllItems(compound, this.inventory);


        return compound;
    }

    public ItemStack getDNA(){
        return this.inventory.get(0);
    }
}
