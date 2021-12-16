package net.gamma02.zombieinfusion.common.helpers;

import net.gamma02.zombieinfusion.common.Items.DNA;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;

public class NBTHelper
{
    public static String InfusedNamespaceKey = "InfusedNamespace";
    public static String InfusedEntryKey = "InfusedEntry";

    public static String InfusedPercentKey = "InfusedPercent";


    @Nullable public static Item getItemFromNBT(CompoundNBT nbt) throws NullPointerException{

        if(nbt.contains(InfusedNamespaceKey) && nbt.contains(InfusedEntryKey)){
            return ForgeRegistries.ITEMS.getValue(new ResourceLocation(nbt.getString(InfusedNamespaceKey), nbt.getString(InfusedEntryKey)));
        }else{
            throw new NullPointerException("DNA Infused was Null!");
        }

    }

    public static float getInfusedPercent(CompoundNBT nbt) throws NullPointerException{
        if(nbt.contains(InfusedPercentKey)){
            return nbt.getFloat(InfusedPercentKey);
        }else{
            throw new NullPointerException("DNA was infused without a percent!");
        }
    }

    public static CompoundNBT InfuseDNA(CompoundNBT nbt, Item itemIn, float percent){
        nbt.putString(InfusedNamespaceKey, itemIn.getRegistryName().getNamespace());
        nbt.putString(InfusedEntryKey, itemIn.getRegistryName().getPath());
        nbt.putFloat(InfusedPercentKey, percent);
        return nbt;
    }
    public static void Infuse(Item infuser, float percent, ItemStack itemIn) {
        if(itemIn.getItem() instanceof DNA){
            CompoundNBT itemInNBT = itemIn.serializeNBT();
            itemIn.deserializeNBT(InfuseDNA(itemInNBT, infuser, percent));

        }
    }
}
