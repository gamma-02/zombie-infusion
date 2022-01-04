package net.gamma02.zombieinfusion.client.renderingthingies;

import net.gamma02.zombieinfusion.common.Entities.DNAZombie;
import net.minecraft.client.renderer.entity.model.AbstractZombieModel;
import net.minecraft.client.renderer.entity.model.ZombieModel;

public class DNAZombieModel extends ZombieModel<DNAZombie>
{
    protected DNAZombieModel(float modelSize, float yOffsetIn, int textureWidthIn, int textureHeightIn)
    {
        super(modelSize, yOffsetIn, textureWidthIn, textureHeightIn);
    }

    @Override public boolean isAggressive(DNAZombie entityIn)
    {
        return true;
    }
}
