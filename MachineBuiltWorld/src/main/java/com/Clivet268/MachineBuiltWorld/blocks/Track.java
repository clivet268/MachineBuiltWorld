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

public class Track extends Block {


    private static final VoxelShape LONE = Stream.of(
            Block.makeCuboidShape(15, 0, 0, 16, 4, 16),
            Block.makeCuboidShape(1, 0, 0, 15, 3, 16),
            Block.makeCuboidShape(0, 0, 0, 1, 4, 16)
    ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get();

    public Track() {
        super(Block.Properties.create(Material.IRON)
                .hardnessAndResistance(5.0f, 6.0f)
                .sound(SoundType.METAL)
                .harvestLevel(1)
                .harvestTool(ToolType.PICKAXE));
    }
    @Override
    public boolean hasTileEntity(BlockState states)
    {
        return true;
    }
    /*@Override
        public TileEntity createTileEntity(BlockState states, IBlockReader world)
        {
           return ModTileEntityTypes.MELTING_POT.get().create();
        }

     */
    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return LONE;
    }
}
