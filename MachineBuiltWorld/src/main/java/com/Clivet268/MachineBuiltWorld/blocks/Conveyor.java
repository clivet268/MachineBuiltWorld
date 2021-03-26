package com.Clivet268.MachineBuiltWorld.blocks;

import com.Clivet268.MachineBuiltWorld.state.MoreStateProperties;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ToolType;

import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

import static net.minecraft.state.properties.BlockStateProperties.HORIZONTAL_FACING;
import static net.minecraft.state.properties.BlockStateProperties.UP;
import static net.minecraft.util.Direction.*;

public class Conveyor extends Block {

    public static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;
    //public static final BooleanProperty ONO = MoreStateProperties.UPORNOT;
    protected static final AxisAlignedBB CONVEYOR_AABB =
            new AxisAlignedBB(0.0625D, 0.0D, 0.0625D, 0.9375D, 0.2D, 0.9375D);
    private static final VoxelShape[] LONE =
            {
                    //North South
                    Stream.of(
                            Block.makeCuboidShape(1, 0.5, 15, 15, 2.5, 16),
                            Block.makeCuboidShape(1, 0, 1, 15, 3, 15),
                            Block.makeCuboidShape(0, 0, 0, 1, 4, 16),
                            Block.makeCuboidShape(15, 0, 0, 16, 4, 16),
                            Block.makeCuboidShape(1, 0.5, 0, 15, 2.5, 1)
                    ).reduce((v1, v2) -> {
                        return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);
                    }).get(),
                    //East West
                    Stream.of(
                            Block.makeCuboidShape(0, 0.5, 1, 1, 2.5, 15),
                            Block.makeCuboidShape(1, 0, 1, 15, 3, 15),
                            Block.makeCuboidShape(0, 0, 0, 16, 4, 1),
                            Block.makeCuboidShape(0, 0, 15, 16, 4, 16),
                            Block.makeCuboidShape(15, 0.5, 1, 16, 2.5, 15)
                    ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get()
            };

    public Conveyor(Block.Properties properties) {
            super(properties);
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
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.getDefaultState().with(FACING, context.getPlacementHorizontalFacing().getOpposite());
    }
    public void moove(World world, BlockPos posi) {
        AxisAlignedBB axisalignedbb = CONVEYOR_AABB.offset(posi);
        List<? extends Entity> list;
        list = world.getEntitiesWithinAABBExcludingEntity((Entity)null, axisalignedbb);
        if (!list.isEmpty()) {
            for(Entity entity : list) {
                System.out.println(entity);
                BlockState state = world.getBlockState(posi);
                if (state.get(FACING).equals(NORTH)) {
                    entity.addVelocity(0.0D, 0.0D,-0.1D);
                }
                else if(state.get(FACING).equals(WEST)) {
                    entity.addVelocity(-0.1D, 0.0D,0.0D);
                }
                else if (state.get(FACING).equals(EAST)) {
                    entity.addVelocity(0.1D, 0.0D,0.0D);
                }
                else if (state.get(FACING).equals(SOUTH)) {
                    entity.addVelocity(0.0D, 0.0D,0.1D);
                }
            }
        }
    }

    @Override
    public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
        moove(worldIn, pos);
    }
    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        if (state.get(FACING).equals(NORTH) || state.get(FACING).equals(SOUTH)) {
            return LONE[0];
        }
        else if (state.get(FACING).equals(WEST) || state.get(FACING).equals(EAST)) {
            return LONE[1];
        }
        else
        {
            return LONE[0];
        }
    }
}
