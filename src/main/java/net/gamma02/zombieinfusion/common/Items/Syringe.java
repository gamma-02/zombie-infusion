package net.gamma02.zombieinfusion.common.Items;

import net.gamma02.zombieinfusion.ZombieInfusions;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import javax.annotation.Nonnull;

public class Syringe extends Item
{
    public Syringe(Properties properties)
    {
        super(properties);
    }

    @Override
    public boolean hitEntity(@Nonnull ItemStack stack, @Nonnull LivingEntity target, @Nonnull LivingEntity attacker) {
        if(target instanceof ZombieEntity && attacker instanceof ServerPlayerEntity){
            ServerPlayerEntity user = (ServerPlayerEntity) attacker;
            user.inventory.addItemStackToInventory(ZombieInfusions.DNA_ITEM.get().getDefaultInstance());

        }
        return super.hitEntity(stack, target, attacker);
    }
}
