package com.Clivet268.MachineBuiltWorld.blocks;

//import com.Clivet268.MachineBuiltWorld.init.ModTileEntityTypes;

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

public class MeltingPot extends Block{
        private static final VoxelShape REG_SHAPE = Stream.of(
                Block.makeCuboidShape(12, 0, 12, 14, 2, 14),
                Block.makeCuboidShape(2, 0, 2, 4, 2, 4),
                Block.makeCuboidShape(2, 3, 4, 4, 13, 12),
                Block.makeCuboidShape(12, 3, 4, 14, 13, 12),
                Block.makeCuboidShape(2, 3, 2, 14, 13, 4),
                Block.makeCuboidShape(2, 3, 12, 14, 13, 14),
                Block.makeCuboidShape(2, 2, 2, 14, 3, 14),
                Block.makeCuboidShape(12, 0, 2, 14, 2, 4),
                Block.makeCuboidShape(2, 0, 12, 4, 2, 14)
        ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get();
        public MeltingPot() {
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
        return REG_SHAPE;
    }
}
