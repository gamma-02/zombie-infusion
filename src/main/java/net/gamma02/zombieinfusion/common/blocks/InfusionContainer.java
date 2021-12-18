package net.gamma02.zombieinfusion.common.blocks;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.gamma02.zombieinfusion.ZombieInfusions;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;

public class InfusionContainer extends Container
{
    public InfusionBlockEntity energy;
    public InfusionContainer(int id, PlayerInventory inventory, IInventory inventory1, @Nullable InfusionBlockEntity energy){

        super(ZombieInfusions.INFUSION_CONTAINER.get(), id);
        this.addSlot(new Slot(inventory1, 0, 45, 16));
        this.addSlot(new Slot(inventory1, 1, 44, 56));
        this.addSlot(new Slot(inventory1, 2, 106, 36));
        this.energy = energy;


            for (int i = 0; i < 3; ++i)
            {
                for (int j = 0; j < 9; ++j)
                {
                    this.addSlot(new Slot(inventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
                }
            }
            for (int i = 0; i < 9; ++i)
            {
                this.addSlot(new Slot(inventory, i, 8 + i * 18, 142));
            }

    }

    public InfusionContainer(int id, PlayerInventory inventory){
        this( id, inventory, new Inventory(3), null);
    }


    @Override public boolean canInteractWith(PlayerEntity playerIn)
    {
        return true;
    }
    @Override
    @Nonnull
    public ItemStack transferStackInSlot(@Nonnull PlayerEntity playerIn, int index)
    {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);
        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();
            if (index < 2) {
                if (!this.mergeItemStack(itemstack1, 2, this.inventorySlots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.mergeItemStack(itemstack1, 0, 2, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }
        }
        return itemstack;
    }




}
