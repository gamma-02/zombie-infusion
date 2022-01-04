package net.gamma02.zombieinfusion.common.blocks;

import net.minecraft.block.ContainerBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nullable;

public class CreativeGeneratorBlock extends ContainerBlock
{
    public CreativeGeneratorBlock(Properties builder)
    {
        super(builder);
    }

    @Nullable @Override public TileEntity createNewTileEntity(IBlockReader worldIn)
    {
        return new CreativeGeneratorBlockEntity();
    }
}
