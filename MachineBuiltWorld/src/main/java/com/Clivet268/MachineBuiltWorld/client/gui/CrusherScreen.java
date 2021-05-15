package com.Clivet268.MachineBuiltWorld.client.gui;


import com.Clivet268.MachineBuiltWorld.MachineBuiltWorld;
import com.Clivet268.MachineBuiltWorld.blocks.Crusher;
import com.Clivet268.MachineBuiltWorld.inventory.Containers.AbstractCrusherContainer;
import com.Clivet268.MachineBuiltWorld.inventory.Containers.CrusherContainer;
import com.Clivet268.MachineBuiltWorld.tileentity.AbstractCrusherTile;
import com.Clivet268.MachineBuiltWorld.tileentity.CrusherTile;
import com.Clivet268.MachineBuiltWorld.tileentity.GeneratorTile;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.tileentity.CommandBlockLogic;
import net.minecraft.tileentity.CommandBlockTileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class CrusherScreen extends ContainerScreen<CrusherContainer> {
    private AbstractCrusherTile.Mode commandBlockMode = AbstractCrusherTile.Mode.CLOSE;
    private Button modeBtn;
    private CrusherTile crusherTile;
        private final ResourceLocation GUI = new ResourceLocation(MachineBuiltWorld.MOD_ID, "textures/gui/crusher.png");

        public CrusherScreen(CrusherContainer container, PlayerInventory inv, ITextComponent name) {
            super(container, inv, name);
            this.crusherTile = container.tileEntity;
        }

        @Override
        public void tick()
        {
            this.modeBtn.active = true;
            if(this.commandBlockMode == AbstractCrusherTile.Mode.OPEN) {
                this.crusherTile.getWorld().setBlockState(this.crusherTile.getPos(), this.crusherTile.getBlockState().with(Crusher.OC, true), 3);
            }
            else if(this.commandBlockMode == AbstractCrusherTile.Mode.CLOSE) {
                this.crusherTile.getWorld().setBlockState(this.crusherTile.getPos(), this.crusherTile.getBlockState().with(Crusher.OC, false), 3);
            }
        }
        @Override
        public void init()
        {
            super.init();
            this.modeBtn = this.addButton(new Button(this.width / 2 - 50 - 100 - 4, 165, 19, 19, I18n.format("crushbutt.mode.close"), (p_214191_1_) -> {
                this.nextMode();
                this.updateMode();
            }));
        this.modeBtn.active = false;
        }

        @Override
        public void render(int mouseX, int mouseY, float partialTicks) {
            this.renderBackground();
            super.render(mouseX, mouseY, partialTicks);
            this.renderHoveredToolTip(mouseX, mouseY);
        }
    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        String s = this.title.getFormattedText();
        drawString(Minecraft.getInstance().fontRenderer, "Energy: " + ((CrusherTile)container.tileEntity).energyStorage.getEnergyStored(), 10, 10, 0xffffff);
        this.font.drawString(s, (float)(this.xSize / 2 - this.font.getStringWidth(s) / 2), 6.0F, 4210752);
        this.font.drawString(this.playerInventory.getDisplayName().getFormattedText(), 8.0F, (float)(this.ySize - 96 + 2), 4210752);
    }

    /**
     * Draws the background layer of this container (behind the items).
     */
        @Override
        protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            assert this.minecraft != null;
            this.minecraft.getTextureManager().bindTexture(this.GUI);
            int i = this.guiLeft;
            int j = this.guiTop;
            this.blit(i, j, 0, 0, this.xSize, this.ySize);
            if (((AbstractCrusherContainer)this.container).isBurning()) {
                int k = ((AbstractCrusherContainer)this.container).getBurnLeftScaled();
                this.blit(i + 56, j + 36 + 12 - k, 176, 12 - k, 14, k + 1);
            }

            int l = ((AbstractCrusherContainer)this.container).getCookProgressionScaled();
            this.blit(i + 79, j + 34, 176, 14, l + 1, 16);
        }


    private void updateMode() {
        switch(this.commandBlockMode) {
            case OPEN:
                this.modeBtn.setMessage(I18n.format("crushbutt.mode.open"));
                break;
            case CLOSE:
                this.modeBtn.setMessage(I18n.format("crushbutt.mode.close"));
        }
    }

    private void nextMode() {
        switch(this.commandBlockMode) {
            case OPEN:
                this.commandBlockMode = AbstractCrusherTile.Mode.CLOSE;
                break;
            case CLOSE:
                this.commandBlockMode = AbstractCrusherTile.Mode.OPEN;
        }

    }

    public void updateGui() {
        this.updateMode();
        this.modeBtn.active = true;
    }

    @Override
    public void resize(Minecraft p_resize_1_, int p_resize_2_, int p_resize_3_) {
        super.resize(p_resize_1_, p_resize_2_, p_resize_3_);
        this.updateMode();
        this.modeBtn.active = true;
    }



}
