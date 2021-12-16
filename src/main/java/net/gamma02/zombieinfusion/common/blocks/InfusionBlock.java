package net.gamma02.zombieinfusion.common.blocks;

import net.gamma02.zombieinfusion.ZombieInfusions;
import net.gamma02.zombieinfusion.common.ModBlocks;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.ContainerBlock;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nullable;

public class InfusionBlock extends ContainerBlock implements ITileEntityProvider
{
    public InfusionBlock(AbstractBlock.Properties builder)
    {
        super(builder);
    }

    @Nullable @Override public TileEntity createNewTileEntity(IBlockReader worldIn)
    {
        return new InfusionBlockEntity(ModBlocks.INFUSION_BLOCK_ENTITY_TYPE, 50000, 200, 0);
    }

    public boolean hasTileEntity(){
        return true;
    }


}
