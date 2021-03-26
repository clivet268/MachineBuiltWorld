package com.Clivet268.MachineBuiltWorld.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;

public class BauxiteOre extends Block{
    public BauxiteOre() {

        super(Properties.create(Material.ROCK)
                .hardnessAndResistance(3.0f, 3.0f)
                .sound(SoundType.STONE)
                .harvestLevel(1)
                .harvestTool(ToolType.PICKAXE)
        );
    }
}
