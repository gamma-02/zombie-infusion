package net.gamma02.zombieinfusion.common.Items;

import net.gamma02.zombieinfusion.common.helpers.NBTHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.MobSpawnerTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;

import javax.annotation.Nonnull;

public class DNA extends Item
{


    public DNA(Properties properties)
    {
        super(properties);
    }

    @Override @Nonnull public ActionResultType onItemUse(ItemUseContext context)
    {
        TileEntity tileentity = context.getWorld().getTileEntity(context.getPos());
        if (tileentity instanceof MobSpawnerTileEntity && context.getItem().serializeNBT().contains(NBTHelper.InfusedEntryKey) && context.getItem().serializeNBT().contains(NBTHelper.InfusedPercentKey) && context.getItem().serializeNBT().contains(NBTHelper.InfusedNamespaceKey)) {
            CompoundNBT nbt = tileentity.serializeNBT();
            CompoundNBT data = new CompoundNBT();
            data.putString("id", "zombie-infusion");
            data.putFloat(NBTHelper.InfusedPercentKey, NBTHelper.getInfusedPercent(context.getItem().serializeNBT()));
            data.putString(NBTHelper.InfusedNamespaceKey, context.getItem().serializeNBT().getString(NBTHelper.InfusedNamespaceKey));
            data.putString(NBTHelper.InfusedEntryKey, context.getItem().serializeNBT().getString(NBTHelper.InfusedEntryKey));
            nbt.put("SpawnerData", data);
            return ActionResultType.CONSUME;
        }
        return ActionResultType.FAIL;


    }
}
