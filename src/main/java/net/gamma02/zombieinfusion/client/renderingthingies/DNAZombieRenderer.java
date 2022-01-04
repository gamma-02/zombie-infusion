package net.gamma02.zombieinfusion.client.renderingthingies;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.gamma02.zombieinfusion.common.Entities.DNAZombie;
import net.gamma02.zombieinfusion.common.Entities.ModEntities;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.AbstractZombieRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.ZombieRenderer;
import net.minecraft.client.renderer.entity.model.AbstractZombieModel;
import net.minecraft.client.renderer.entity.model.ZombieModel;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.util.ResourceLocation;

public class DNAZombieRenderer<T extends DNAZombie> extends AbstractZombieRenderer<DNAZombie, DNAZombieModel>
{

    protected DNAZombieRenderer(EntityRendererManager p_i50974_1_, DNAZombieModel p_i50974_2_,
            DNAZombieModel p_i50974_3_, DNAZombieModel p_i50974_4_)
    {
        super(p_i50974_1_, p_i50974_2_, p_i50974_3_, p_i50974_4_);
    }

    @Override public void render(DNAZombie entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn,
            IRenderTypeBuffer bufferIn, int packedLightIn)
    {
        bufferIn.getBuffer(RenderType.getEntitySolid(new ResourceLocation("textures/entity/zombie/zombie.png"))).color(entityIn.getColor().getRed(), entityIn.getColor().getGreen(), entityIn.getColor().getBlue(), entityIn.getColor().getAlpha());
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }
}
