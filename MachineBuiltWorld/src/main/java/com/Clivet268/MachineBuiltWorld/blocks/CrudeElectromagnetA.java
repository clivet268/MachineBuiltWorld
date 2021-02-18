package com.Clivet268.MachineBuiltWorld.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.common.ToolType;

import java.util.stream.Stream;

public class CrudeElectromagnetA extends  Block {


    private static final VoxelShape SPLIT = Stream.of(
            Block.makeCuboidShape(2, 0, 0, 3, 1, 9),
            Block.makeCuboidShape(4, 6, 14, 5, 10, 15),
            Block.makeCuboidShape(4, 4, 13, 5, 6, 14),
            Block.makeCuboidShape(4, 10, 13, 5, 12, 14),
            Block.makeCuboidShape(4, 6, 1, 5, 10, 2),
            Block.makeCuboidShape(4, 4, 2, 5, 6, 3),
            Block.makeCuboidShape(4, 10, 2, 5, 12, 3),
            Block.makeCuboidShape(4, 14, 6, 5, 15, 10),
            Block.makeCuboidShape(4, 13, 4, 5, 14, 6),
            Block.makeCuboidShape(4, 13, 10, 5, 14, 12),
            Block.makeCuboidShape(4, 2, 11, 5, 3, 12),
            Block.makeCuboidShape(4, 3, 3, 5, 4, 4),
            Block.makeCuboidShape(4, 3, 12, 5, 4, 13),
            Block.makeCuboidShape(4, 12, 12, 5, 13, 13),
            Block.makeCuboidShape(4, 12, 3, 5, 13, 4),
            Block.makeCuboidShape(6, 6, 14, 7, 10, 15),
            Block.makeCuboidShape(6, 4, 13, 7, 6, 14),
            Block.makeCuboidShape(6, 10, 13, 7, 12, 14),
            Block.makeCuboidShape(6, 6, 1, 7, 10, 2),
            Block.makeCuboidShape(6, 4, 2, 7, 6, 3),
            Block.makeCuboidShape(6, 10, 2, 7, 12, 3),
            Block.makeCuboidShape(6, 14, 6, 7, 15, 10),
            Block.makeCuboidShape(6, 13, 4, 7, 14, 6),
            Block.makeCuboidShape(6, 13, 10, 7, 14, 12),
            Block.makeCuboidShape(6, 2, 4, 7, 3, 5),
            Block.makeCuboidShape(6, 2, 10, 7, 3, 12),
            Block.makeCuboidShape(6, 3, 3, 7, 4, 4),
            Block.makeCuboidShape(6, 3, 12, 7, 4, 13),
            Block.makeCuboidShape(6, 12, 12, 7, 13, 13),
            Block.makeCuboidShape(6, 12, 3, 7, 13, 4),
            Block.makeCuboidShape(8, 6, 14, 9, 10, 15),
            Block.makeCuboidShape(8, 4, 13, 9, 6, 14),
            Block.makeCuboidShape(8, 10, 13, 9, 12, 14),
            Block.makeCuboidShape(8, 6, 1, 9, 10, 2),
            Block.makeCuboidShape(8, 4, 2, 9, 6, 3),
            Block.makeCuboidShape(8, 10, 2, 9, 12, 3),
            Block.makeCuboidShape(8, 14, 6, 9, 15, 10),
            Block.makeCuboidShape(8, 13, 4, 9, 14, 6),
            Block.makeCuboidShape(8, 13, 10, 9, 14, 12),
            Block.makeCuboidShape(8, 2, 10, 9, 3, 12),
            Block.makeCuboidShape(8, 3, 3, 9, 4, 4),
            Block.makeCuboidShape(8, 3, 12, 9, 4, 13),
            Block.makeCuboidShape(8, 12, 12, 9, 13, 13),
            Block.makeCuboidShape(8, 12, 3, 9, 13, 4),
            Block.makeCuboidShape(10, 6, 14, 11, 10, 15),
            Block.makeCuboidShape(10, 4, 13, 11, 6, 14),
            Block.makeCuboidShape(10, 10, 13, 11, 12, 14),
            Block.makeCuboidShape(10, 6, 1, 11, 10, 2),
            Block.makeCuboidShape(10, 4, 2, 11, 6, 3),
            Block.makeCuboidShape(10, 10, 2, 11, 12, 3),
            Block.makeCuboidShape(10, 14, 6, 11, 15, 10),
            Block.makeCuboidShape(10, 13, 4, 11, 14, 6),
            Block.makeCuboidShape(10, 13, 10, 11, 14, 12),
            Block.makeCuboidShape(10, 2, 10, 11, 3, 12),
            Block.makeCuboidShape(10, 3, 3, 11, 4, 4),
            Block.makeCuboidShape(10, 3, 12, 11, 4, 13),
            Block.makeCuboidShape(10, 12, 12, 11, 13, 13),
            Block.makeCuboidShape(10, 12, 3, 11, 13, 4),
            Block.makeCuboidShape(6, 1, 5, 7, 2, 7),
            Block.makeCuboidShape(7, 1, 7, 8, 2, 10),
            Block.makeCuboidShape(4, 2, 4, 5, 3, 5),
            Block.makeCuboidShape(4, 1, 5, 5, 2, 7),
            Block.makeCuboidShape(5, 1, 7, 6, 2, 10),
            Block.makeCuboidShape(3, 1, 9, 4, 2, 11),
            Block.makeCuboidShape(8, 2, 4, 9, 3, 5),
            Block.makeCuboidShape(8, 1, 5, 9, 2, 7),
            Block.makeCuboidShape(9, 1, 7, 10, 2, 10),
            Block.makeCuboidShape(10, 1, 5, 11, 2, 7),
            Block.makeCuboidShape(11, 0, 7, 12, 1, 16),
            Block.makeCuboidShape(10, 2, 4, 11, 3, 5)
    ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get();

    public CrudeElectromagnetA() {
        super(Block.Properties.create(Material.IRON)
                .hardnessAndResistance(5.0f, 6.0f)
                .sound(SoundType.METAL)
                .harvestLevel(1)
                .harvestTool(ToolType.PICKAXE));
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SPLIT;
    }

}
