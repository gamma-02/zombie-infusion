package net.gamma02.zombieinfusion.common.recipes;

import com.google.gson.JsonObject;
import net.gamma02.zombieinfusion.ZombieInfusions;

import net.gamma02.zombieinfusion.common.blocks.InfusionBlockEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.*;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.awt.*;
import java.util.Arrays;

public class InfusionRecipe implements IRecipe<InfusionBlockEntity>
{
    public static final Sieralizer SERIALIZER = new Sieralizer();
    public Item infuser;
    public ResourceLocation id;
    public Color zombieColor;
    public int energy;
    public int ticks;

    public InfusionRecipe(ResourceLocation id, ResourceLocation item, Color color, int energy, int ticks){
        this.id = id;
        try
        {
            this.infuser = ForgeRegistries.ITEMS.getValue(item);
        }
        catch ( NullPointerException exception){
            System.out.println("RECIPE ITEM IS INVALID " + exception.getLocalizedMessage());
            System.out.println(Arrays.toString(exception.getStackTrace()));
        }
        this.zombieColor = color;
        this.energy = energy;
        this.ticks = ticks;
    }

    @Override public boolean matches(InfusionBlockEntity inv, @Nonnull World worldIn)
    {
        return inv.hasDNA() && inv.getInputItem().getItem() == this.infuser;
    }

    @Override @Nonnull public ItemStack getCraftingResult(@Nonnull InfusionBlockEntity inv)
    {


        return ZombieInfusions.DNA_ITEM.get().getDefaultInstance();
    }

    @Override public boolean canFit(int width, int height)
    {
        return width < 3 && width >=1 && height == 1;
    }

    @Override @Nonnull public ItemStack getRecipeOutput()
    {
        return ZombieInfusions.DNA_ITEM.get().getDefaultInstance();//TODO : impliment something with tooltips and DNA infusion % later
    }

    @Override @Nonnull public ResourceLocation getId()
    {
        return this.id;
    }

    @Override @Nonnull public IRecipeSerializer<?> getSerializer()
    {
        return SERIALIZER;
    }

    @Override @Nonnull public IRecipeType<?> getType()
    {
        return ZombieInfusions.INFUSION_RECIPE_TYPE;
    }

    private static class Sieralizer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<InfusionRecipe>{

        Sieralizer(){
            this.setRegistryName(new ResourceLocation(ZombieInfusions.modid, "infusion_recipe_type"));
        }

        @Override @Nonnull public InfusionRecipe read(@Nonnull ResourceLocation recipeId, @Nonnull JsonObject json) throws NullPointerException
        {
            final ResourceLocation item = ShapedRecipe.deserializeItem(json).getItem().getRegistryName();
            final Color ZombieTint = new Color(JSONUtils.getFloat(json, "red"), JSONUtils.getFloat(json, "green"), JSONUtils.getFloat(json, "blue"));
            final int energy = JSONUtils.getInt(json, "energy");
            final int ticks = JSONUtils.getInt(json, "time");

            if(ForgeRegistries.ITEMS.getValue(item) == null || ForgeRegistries.ITEMS.getValue(item) == Items.AIR){
                throw new NullPointerException("Recipe item is null or invalid!");
            }

            return new InfusionRecipe(recipeId, item, ZombieTint, energy, ticks);
        }

        @Nullable @Override public InfusionRecipe read( @Nonnull ResourceLocation recipeId, PacketBuffer buffer)
        {
            final Item item = buffer.readItemStack().getItem();
            final Color ZombieTint = new Color(buffer.readFloat(), buffer.readFloat(), buffer.readFloat());
            final int energy = buffer.readInt();
            final int ticks = buffer.readInt();
            return new InfusionRecipe(recipeId, item.getRegistryName(), ZombieTint, energy, ticks);
        }

        @Override public void write(PacketBuffer buffer, InfusionRecipe recipe)
        {
            buffer.writeItemStack(recipe.infuser.getDefaultInstance());
            buffer.writeFloat(recipe.zombieColor.getRed());
            buffer.writeFloat(recipe.zombieColor.getGreen());
            buffer.writeFloat(recipe.zombieColor.getBlue());
            buffer.writeInt(recipe.energy);
            buffer.writeInt(recipe.ticks);
        }
    }
}
