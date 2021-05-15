package com.Clivet268.MachineBuiltWorld.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;

public class SteelBlock extends Block {
    public SteelBlock() {

        super(Properties.create(Material.IRON)
        .hardnessAndResistance(5.0f, 8.0f)
        .sound(SoundType.METAL)
        .harvestLevel(1)
        .harvestTool(ToolType.PICKAXE)
        );
    }
}