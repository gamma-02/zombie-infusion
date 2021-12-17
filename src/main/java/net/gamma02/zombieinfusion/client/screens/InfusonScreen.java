package net.gamma02.zombieinfusion.client.screens;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.gamma02.zombieinfusion.ZombieInfusions;
import net.gamma02.zombieinfusion.common.blocks.InfusionBlock;
import net.gamma02.zombieinfusion.common.blocks.InfusionBlockEntity;
import net.minecraft.client.gui.IHasContainer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;


public class InfusonScreen extends ContainerScreen<InfusionContainer> implements IHasContainer<InfusionContainer>
{






    public InfusonScreen(InfusionContainer screenContainer, PlayerInventory inv, ITextComponent titleIn)
    {

        super(screenContainer, inv, titleIn);
        System.out.println("J;dajs;dkh;fasj;eklfvjsdfajsdfja;j'w;");
    }


    @Override protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int x, int y)
    {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        minecraft.getTextureManager().bindTexture(new ResourceLocation(ZombieInfusions.modid, "textures/gui/gui_100.png"));
        int x1 = (width - 176) / 2;
        int y1 = (height - (165)) / 2;

        System.out.println("J;dajs;dkh;fasj;eklfvjsdfajsdfja;j'w;");
        blit(matrixStack, x, y, 0, 0, 176, 165);

    }
    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);
        renderHoveredTooltip(matrices, mouseX, mouseY);

    }

    @Override
    protected void init() {
        super.init();
        // Center the title
        //titleX = (backgroundWidth - textRenderer.getWidth(title)) / 2 - 50;
        //titleY = (20);
        playerInventoryTitleY += 50 - 5;

    }
}
