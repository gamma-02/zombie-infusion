package net.gamma02.zombieinfusion.common.blocks;

import net.gamma02.zombieinfusion.ZombieInfusions;
import net.gamma02.zombieinfusion.common.ModBlocks;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.ContainerBlock;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;

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

    public ActionResultType onBlockActivated( @Nonnull BlockState state, @Nonnull World worldIn, @Nonnull BlockPos pos,  @Nonnull PlayerEntity player, @Nonnull Hand handIn, @Nonnull BlockRayTraceResult hit){
        TileEntity tileEntity = worldIn.getTileEntity(pos);
        if(tileEntity instanceof InfusionBlockEntity && player instanceof ServerPlayerEntity){
            NetworkHooks.openGui((ServerPlayerEntity) player, (InfusionBlockEntity) tileEntity);
        }
        return ActionResultType.SUCCESS;
    }






}
