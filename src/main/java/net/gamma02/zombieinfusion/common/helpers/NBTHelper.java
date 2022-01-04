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

    public static float getInfusedPercent(ItemStack itemIn) {
        if(itemIn.serializeNBT().contains(InfusedPercentKey)){
            return itemIn.serializeNBT().getFloat(InfusedPercentKey);
        }else{
            CompoundNBT d = itemIn.getTag();

            if (d != null)
            {
                d.putFloat(InfusedPercentKey, 0.0f);
            }
            itemIn.setTag(d);
            return itemIn.serializeNBT().getFloat(InfusedPercentKey);
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
            CompoundNBT itemInNBT = itemIn.getTag();
            itemIn.setTag(InfuseDNA(itemInNBT != null ? itemInNBT : new CompoundNBT(), infuser, percent));

        }
    }
}
