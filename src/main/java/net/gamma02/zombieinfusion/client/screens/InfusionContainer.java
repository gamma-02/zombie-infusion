package net.gamma02.zombieinfusion.client.screens;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;

import javax.annotation.Nullable;

public class InfusionContainer extends Container
{
    protected InfusionContainer(@Nullable ContainerType<?> type, int id)
    {
        super(type, id);
    }

    @Override public boolean canInteractWith(PlayerEntity playerIn)
    {
        return true;
    }
}
