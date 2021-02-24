package com.Clivet268.MachineBuiltWorld.client.gui;

import com.Clivet268.MachineBuiltWorld.inventory.Containers.CokeOvenContainerBase;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.recipebook.AbstractRecipeBookGui;
import net.minecraft.client.gui.recipebook.IRecipeShownListener;
import net.minecraft.client.gui.recipebook.RecipeBookGui;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.button.ImageButton;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class AbstractCokeOvenScreen<T extends CokeOvenContainerBase> extends ContainerScreen<T> implements IRecipeShownListener {
   private static final ResourceLocation RECIPE_BOOK = new ResourceLocation("textures/gui/recipe_button.png");
   public final AbstractRecipeBookGui recipeGui;
   private boolean widthTooNarrowIn;
   private final ResourceLocation guiTexture;

   public AbstractCokeOvenScreen(T screenContainer, AbstractRecipeBookGui recipeGuiIn, PlayerInventory inv, ITextComponent titleIn, ResourceLocation guiTextureIn) {
      super(screenContainer, inv, titleIn);
      this.recipeGui = recipeGuiIn;
      this.guiTexture = guiTextureIn;
   }
   @Override
   public void init() {
      super.init();
      this.widthTooNarrowIn = this.width < 379;
      this.recipeGui.init(this.width, this.height, this.minecraft, this.widthTooNarrowIn, this.container);
      this.guiLeft = this.recipeGui.updateScreenPosition(this.widthTooNarrowIn, this.width, this.xSize);
      this.addButton((new ImageButton(this.guiLeft + 20, this.height / 2 - 49, 20, 18, 0, 0, 19, RECIPE_BOOK, (p_214087_1_) -> {
         this.recipeGui.initSearchBar(this.widthTooNarrowIn);
         this.recipeGui.toggleVisibility();
         this.guiLeft = this.recipeGui.updateScreenPosition(this.widthTooNarrowIn, this.width, this.xSize);
         ((ImageButton)p_214087_1_).setPosition(this.guiLeft + 20, this.height / 2 - 49);
      })));
   }

   @Override
   public void tick() {
      super.tick();
      this.recipeGui.tick();
   }
   @Override
   public void render(int p_render_1_, int p_render_2_, float p_render_3_) {
      this.renderBackground();
      if (this.recipeGui.isVisible() && this.widthTooNarrowIn) {
         this.drawGuiContainerBackgroundLayer(p_render_3_, p_render_1_, p_render_2_);
         this.recipeGui.render(p_render_1_, p_render_2_, p_render_3_);
      } else {
         this.recipeGui.render(p_render_1_, p_render_2_, p_render_3_);
         super.render(p_render_1_, p_render_2_, p_render_3_);
         this.recipeGui.renderGhostRecipe(this.guiLeft, this.guiTop, true, p_render_3_);
      }

      this.renderHoveredToolTip(p_render_1_, p_render_2_);
      this.recipeGui.renderTooltip(this.guiLeft, this.guiTop, p_render_1_, p_render_2_);
   }

   /**
    * Draw the foreground layer for the GuiContainer (everything in front of the items)
    */
   @Override
   protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
      String s = this.title.getFormattedText();
      this.font.drawString(s, (float)(this.xSize / 2 - this.font.getStringWidth(s) / 2), 6.0F, 4210752);
      this.font.drawString(this.playerInventory.getDisplayName().getFormattedText(), 8.0F, (float)(this.ySize - 96 + 2), 4210752);
   }

   /**
    * Draws the background layer of this container (behind the items).
    */
   @Override
   protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      this.minecraft.getTextureManager().bindTexture(this.guiTexture);
      int i = this.guiLeft;
      int j = this.guiTop;
      this.blit(i, j, 0, 0, this.xSize, this.ySize);
      if (((CokeOvenContainerBase)this.container).isBurning()) {
         int k = ((CokeOvenContainerBase)this.container).getBurnLeftScaled();
         this.blit(i + 56, j + 36 + 12 - k, 176, 12 - k, 14, k + 1);
      }

      int l = ((CokeOvenContainerBase)this.container).getCookProgressionScaled();
      this.blit(i + 79, j + 34, 176, 14, l + 1, 16);
   }
   @Override
   public boolean mouseClicked(double p_mouseClicked_1_, double p_mouseClicked_3_, int p_mouseClicked_5_) {
      if (this.recipeGui.mouseClicked(p_mouseClicked_1_, p_mouseClicked_3_, p_mouseClicked_5_)) {
         return true;
      } else {
         return this.widthTooNarrowIn && this.recipeGui.isVisible() ? true : super.mouseClicked(p_mouseClicked_1_, p_mouseClicked_3_, p_mouseClicked_5_);
      }
   }

   /**
    * Called when the mouse is clicked over a slot or outside the gui.
    */
   @Override
   protected void handleMouseClick(Slot slotIn, int slotId, int mouseButton, ClickType type) {
      super.handleMouseClick(slotIn, slotId, mouseButton, type);
      this.recipeGui.slotClicked(slotIn);
   }
   @Override
   public boolean keyPressed(int p_keyPressed_1_, int p_keyPressed_2_, int p_keyPressed_3_) {
      return this.recipeGui.keyPressed(p_keyPressed_1_, p_keyPressed_2_, p_keyPressed_3_) ? false : super.keyPressed(p_keyPressed_1_, p_keyPressed_2_, p_keyPressed_3_);
   }
   @Override
   protected boolean hasClickedOutside(double mouseX, double mouseY, int guiLeftIn, int guiTopIn, int mouseButton) {
      boolean flag = mouseX < (double)guiLeftIn || mouseY < (double)guiTopIn || mouseX >= (double)(guiLeftIn + this.xSize) || mouseY >= (double)(guiTopIn + this.ySize);
      return this.recipeGui.func_195604_a(mouseX, mouseY, this.guiLeft, this.guiTop, this.xSize, this.ySize, mouseButton) && flag;
   }
   @Override
   public boolean charTyped(char p_charTyped_1_, int p_charTyped_2_) {
      return this.recipeGui.charTyped(p_charTyped_1_, p_charTyped_2_) ? true : super.charTyped(p_charTyped_1_, p_charTyped_2_);
   }
   @Override
   public void recipesUpdated() {
      this.recipeGui.recipesUpdated();
   }
   @Override
   public RecipeBookGui getRecipeGui() {
      return this.recipeGui;
   }
  @Override
   public void removed() {
      this.recipeGui.removed();
      super.removed();
   }
}
