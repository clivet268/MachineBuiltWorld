package com.Clivet268.MachineBuiltWorld.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FallingBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ToolType;

//TODO darker texture, more rusty
public class IronOxide extends FallingBlock {
   private final int dustColor;

   public IronOxide(int dustColorIn) {
      super(Block.Properties.create(Material.SAND)
              .hardnessAndResistance(5.0f, 1.0f)
              .sound(SoundType.SAND)
              .harvestLevel(0)
              .harvestTool(ToolType.SHOVEL)
      );
      this.dustColor = dustColorIn;
   }

   @OnlyIn(Dist.CLIENT)
   public int getDustColor(BlockState state) {
      return this.dustColor;
   }
}
