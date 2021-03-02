package com.Clivet268.MachineBuiltWorld.client.gui;

import com.Clivet268.MachineBuiltWorld.MachineBuiltWorld;
import com.Clivet268.MachineBuiltWorld.inventory.Containers.CokeOvenContainer;
import net.minecraft.client.gui.recipebook.FurnaceRecipeGui;
import net.minecraft.client.gui.screen.inventory.AbstractFurnaceScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.FurnaceContainer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CokeOvenScreen extends AbstractCokeOvenScreen<CokeOvenContainer> {
   private static final ResourceLocation FURNACE_GUI_TEXTURES = new ResourceLocation(MachineBuiltWorld.MOD_ID,"textures/gui/coke_oven.png");

   public CokeOvenScreen(CokeOvenContainer p_i51089_1_, PlayerInventory p_i51089_2_, ITextComponent p_i51089_3_) {
      super(p_i51089_1_, new CokeOvenGui(), p_i51089_2_, p_i51089_3_, FURNACE_GUI_TEXTURES);
   }
}
