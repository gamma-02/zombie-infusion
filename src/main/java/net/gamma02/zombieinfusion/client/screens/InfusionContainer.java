package net.gamma02.zombieinfusion.client.screens;

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
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;

import javax.annotation.Nullable;

public class InfusionContainer extends Container
{
    public InfusionContainer(int id,  PlayerInventory inventory, IInventory inventory1){

        super(ZombieInfusions.INFUSION_CONTAINER.get(), id);
        this.addSlot(new Slot(inventory1, 0, 100, 100));
        this.addSlot(new Slot(inventory1, 1, 200, 100));

        int l;
        int m;
        //player inventory
        for (m = 0; m < 3; ++m) {
            for (l = 0; l < 9; ++l) {
                this.addSlot(new Slot(inventory, l + m * 9 + 9, 8 + l * 18, 84 + m * 18 + 45));
            }
        }
        //The player Hotbar
        for (m = 0; m < 9; ++m) {
            this.addSlot(new Slot(inventory, m, 8 + m * 18, 142 + 45));
        }
    }

    public InfusionContainer(int id, PlayerInventory inventory){
        super(ZombieInfusions.INFUSION_CONTAINER.get(), id);
    }


    @Override public boolean canInteractWith(PlayerEntity playerIn)
    {
        return true;
    }

}
