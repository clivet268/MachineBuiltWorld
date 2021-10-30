package com.Clivet268.MachineBuiltWorld.client.gui;


import com.Clivet268.MachineBuiltWorld.MachineBuiltWorld;
import com.Clivet268.MachineBuiltWorld.inventory.Containers.GeneratorContainer;
import com.Clivet268.MachineBuiltWorld.tileentity.GeneratorTile;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
//TODO all screens that have energy are not displaying energy properly/ at all
public class GeneratorScreen extends ContainerScreen<GeneratorContainer> {

        private ResourceLocation GUI = new ResourceLocation(MachineBuiltWorld.MOD_ID, "textures/gui/generator.png");

        public GeneratorScreen(GeneratorContainer container, PlayerInventory inv, ITextComponent name) {
            super(container, inv, name);
        }
        GeneratorTile ee = container.tileEntity;

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        drawString(this.minecraft.fontRenderer, "Energy: " + container.getEnergy(), 10, 10, 0xffffff);
    }
        @Override
        public void render(int mouseX, int mouseY, float partialTicks) {
            this.renderBackground();
            super.render(mouseX, mouseY, partialTicks);
            this.renderHoveredToolTip(mouseX, mouseY);
        }

    private void drawEnergyBar(int energy) {
            super.blit(guiLeft + 10, guiTop + 5, guiLeft + 112, guiTop + 15, 0xff555555, 1);
        int percentage = energy * 100 / 10000;
        for (int i = 0 ; i < percentage ; i++) {
            vLine(guiLeft + 10 + 1 + i, guiTop + 5, guiTop + 14, i % 2 == 0 ? 0xffff0000 : 0xff000000);
        }
    }

    @Override
        protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.minecraft.getTextureManager().bindTexture(GUI);
            int relX = (this.width - this.xSize) / 2;
            int relY = (this.height - this.ySize) / 2;
            this.blit(relX, relY, 0, 0, this.xSize, this.ySize);

        int energy = container.getEnergy();
        //drawEnergyBar(energy);

    }
}
