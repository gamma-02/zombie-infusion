package net.gamma02.zombieinfusion.client.screens;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.gamma02.zombieinfusion.ZombieInfusions;
import net.gamma02.zombieinfusion.common.blocks.InfusionBlock;
import net.gamma02.zombieinfusion.common.blocks.InfusionBlockEntity;
import net.gamma02.zombieinfusion.common.blocks.InfusionContainer;
import net.minecraft.client.gui.IHasContainer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.energy.IEnergyStorage;

import javax.annotation.Nonnull;
import java.util.Objects;

public class InfusonScreen extends ContainerScreen<InfusionContainer> implements IHasContainer<InfusionContainer>
{


    public InfusionContainer myContainer;
    public TileEntity owner;



    public InfusonScreen(InfusionContainer screenContainer, PlayerInventory inv, ITextComponent titleIn)
    {

        super(screenContainer, inv, titleIn);
        this.myContainer = screenContainer;
        TileEntity owner = inv.player.getEntityWorld().getTileEntity( playerInventory.player.getEntityWorld().rayTraceBlocks(new RayTraceContext( inv.player.getLookVec(), inv.player.getLookVec().add(5, 5, 5), RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, inv.player)).getPos());
        this.owner = owner;



    }


    @Override protected void drawGuiContainerBackgroundLayer(@Nonnull MatrixStack matrixStack, float partialTicks, int x, int y)
    {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        Objects.requireNonNull(minecraft).getTextureManager().bindTexture(new ResourceLocation(ZombieInfusions.modid, "textures/gui/gui_100.png"));
        int x1 = (width - 176) / 2;
        int y1 = (height - (165)) / 2;
        blit(matrixStack, x1, y1, 0, 0, 176, 165);
    }
    @Override
    public void render(@Nonnull MatrixStack matrices, int mouseX, int mouseY, float delta) {
        renderBackground(matrices);

        if(this.owner instanceof IEnergyStorage)
        {
            this.font.drawText(matrices, ITextComponent.getTextComponentOrEmpty(("FE: " + ((InfusionBlockEntity)this.owner).getEnergyStored())), 8, 75,
                    5);
        }

        super.render(matrices, mouseX, mouseY, delta);

        renderHoveredTooltip(matrices, mouseX, mouseY);

    }

    @Override
    protected void init() {
        super.init();
        // Center the title
        titleX = (width - font.getStringPropertyWidth(title)) / 2 - 50;
        titleY = (20);


    }
    @Override
    @Nonnull
    public InfusionContainer getContainer(){

        return this.myContainer;
    }

    @Override public void tick()
    {
        super.tick();

    }
}
