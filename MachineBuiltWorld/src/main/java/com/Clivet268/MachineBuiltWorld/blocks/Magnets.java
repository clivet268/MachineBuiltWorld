package com.Clivet268.MachineBuiltWorld.blocks;


import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.common.ToolType;

import java.util.stream.Stream;

public class Magnets extends Block {

    private static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;

    private static final VoxelShape SHAPE_N = Stream.of(
            Block.makeCuboidShape(1, 0, 1, 15, 1, 15),
            Block.makeCuboidShape(8, 1, 1, 10, 2, 3),
            Block.makeCuboidShape(13, 1, 5.25, 15, 2, 7.25),
            Block.makeCuboidShape(11, 1, 2, 13, 3, 4),
            Block.makeCuboidShape(9, 1, 5, 11, 2, 7),
            Block.makeCuboidShape(4, 1, 2, 6, 2, 4),
            Block.makeCuboidShape(3, 1, 5, 5, 2, 7),
            Block.makeCuboidShape(7, 1, 5, 9, 5, 7),
            Block.makeCuboidShape(5, 1, 7, 7, 6, 9),
            Block.makeCuboidShape(7, 1, 7, 11, 9, 11),
            Block.makeCuboidShape(1, 1, 11, 3, 2, 13),
            Block.makeCuboidShape(2, 1, 8, 4, 3, 10),
            Block.makeCuboidShape(9, 1, 11, 11, 2, 13),
            Block.makeCuboidShape(12, 1, 12, 14, 2, 14),
            Block.makeCuboidShape(4, 1, 12, 6, 2, 14),
            Block.makeCuboidShape(11, 1, 7, 13, 6, 9),
            Block.makeCuboidShape(11, 1, 9, 13, 5, 11)
    ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get();

    private static final VoxelShape SHAPE_E = Stream.of(
            Block.makeCuboidShape(3, 0, 1, 17, 1, 15),
            Block.makeCuboidShape(15, 1, 8, 17, 2, 10),
            Block.makeCuboidShape(10.75, 1, 13, 12.75, 2, 15),
            Block.makeCuboidShape(14, 1, 11, 16, 3, 13),
            Block.makeCuboidShape(11, 1, 9, 13, 2, 11),
            Block.makeCuboidShape(14, 1, 4, 16, 2, 6),
            Block.makeCuboidShape(11, 1, 3, 13, 2, 5),
            Block.makeCuboidShape(11, 1, 7, 13, 5, 9),
            Block.makeCuboidShape(9, 1, 5, 11, 6, 7),
            Block.makeCuboidShape(7, 1, 7, 11, 9, 11),
            Block.makeCuboidShape(5, 1, 1, 7, 2, 3),
            Block.makeCuboidShape(8, 1, 2, 10, 3, 4),
            Block.makeCuboidShape(5, 1, 9, 7, 2, 11),
            Block.makeCuboidShape(4, 1, 12, 6, 2, 14),
            Block.makeCuboidShape(4, 1, 4, 6, 2, 6),
            Block.makeCuboidShape(9, 1, 11, 11, 6, 13),
            Block.makeCuboidShape(7, 1, 11, 9, 5, 13)
    ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get();

    private static final VoxelShape SHAPE_S = Stream.of(
            Block.makeCuboidShape(1, 0, 1, 15, 1, 15),
            Block.makeCuboidShape(6, 1, 13, 8, 2, 15),
            Block.makeCuboidShape(1, 1, 8.75, 3, 2, 10.75),
            Block.makeCuboidShape(3, 1, 12, 5, 3, 14),
            Block.makeCuboidShape(5, 1, 9, 7, 2, 11),
            Block.makeCuboidShape(10, 1, 12, 12, 2, 14),
            Block.makeCuboidShape(11, 1, 9, 13, 2, 11),
            Block.makeCuboidShape(7, 1, 9, 9, 5, 11),
            Block.makeCuboidShape(9, 1, 7, 11, 6, 9),
            Block.makeCuboidShape(5, 1, 5, 9, 9, 9),
            Block.makeCuboidShape(13, 1, 3, 15, 2, 5),
            Block.makeCuboidShape(12, 1, 6, 14, 3, 8),
            Block.makeCuboidShape(5, 1, 3, 7, 2, 5),
            Block.makeCuboidShape(2, 1, 2, 4, 2, 4),
            Block.makeCuboidShape(10, 1, 2, 12, 2, 4),
            Block.makeCuboidShape(3, 1, 7, 5, 6, 9),
            Block.makeCuboidShape(3, 1, 5, 5, 5, 7)
    ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get();

    private static final VoxelShape SHAPE_W = Stream.of(
            Block.makeCuboidShape(1, 0, 1, 15, 1, 15),
            Block.makeCuboidShape(1, 1, 6, 3, 2, 8),
            Block.makeCuboidShape(5.25, 1, 1, 7.25, 2, 3),
            Block.makeCuboidShape(2, 1, 3, 4, 3, 5),
            Block.makeCuboidShape(5, 1, 5, 7, 2, 7),
            Block.makeCuboidShape(2, 1, 10, 4, 2, 12),
            Block.makeCuboidShape(5, 1, 11, 7, 2, 13),
            Block.makeCuboidShape(5, 1, 7, 7, 5, 9),
            Block.makeCuboidShape(7, 1, 9, 9, 6, 11),
            Block.makeCuboidShape(7, 1, 5, 11, 9, 9),
            Block.makeCuboidShape(11, 1, 13, 13, 2, 15),
            Block.makeCuboidShape(8, 1, 12, 10, 3, 14),
            Block.makeCuboidShape(11, 1, 5, 13, 2, 7),
            Block.makeCuboidShape(12, 1, 2, 14, 2, 4),
            Block.makeCuboidShape(12, 1, 10, 14, 2, 12),
            Block.makeCuboidShape(7, 1, 3, 9, 6, 5),
            Block.makeCuboidShape(9, 1, 3, 11, 5, 5)
    ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get();

    public Magnets() {
        super(Block.Properties.create(Material.IRON)
                .hardnessAndResistance(5.0f, 6.0f)
                .sound(SoundType.METAL)
                .harvestLevel(1)
                .harvestTool(ToolType.PICKAXE)
        );
    }
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.getDefaultState().with(FACING, context.getPlacementHorizontalFacing().getOpposite());
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        switch (state.get(FACING)) {
            case NORTH:
                return SHAPE_N;
            case SOUTH:
                return SHAPE_S;
            case EAST:
                return SHAPE_E;
            case WEST:
                return SHAPE_W;
            default:
                return SHAPE_N;
        }
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rot) {
        return state.with(FACING, rot.rotate(state.get(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return state.rotate(mirrorIn.toRotation(state.get(FACING)));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    /**Change the block shadow -- Lower return values = more shadow*/
    @Override
    public float getAmbientOcclusionLightValue(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return 0.6F;
    }
}

