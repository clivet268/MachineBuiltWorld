package com.Clivet268.MachineBuiltWorld.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;

public class Wire extends Block {
    private static final VoxelShape STRAIGHT_CENTER = Block.makeCuboidShape(5, 0, 0, 11, 4, 16);
    public Wire() {
        super(Block.Properties.create(Material.IRON)
                .hardnessAndResistance(1.0f, 1.0f)
                .sound(SoundType.BAMBOO)
                .harvestLevel(0));
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return STRAIGHT_CENTER;
    }

}
