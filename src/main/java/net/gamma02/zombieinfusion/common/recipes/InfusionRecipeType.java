package net.gamma02.zombieinfusion.common.recipes;


import net.gamma02.zombieinfusion.ZombieInfusions;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;

public class InfusionRecipeType implements IRecipeType<InfusionRecipe>
{

    @Override
    public String toString(){
        return new ResourceLocation(ZombieInfusions.modid, "infusion_recipe_type").toString();
    }
}
