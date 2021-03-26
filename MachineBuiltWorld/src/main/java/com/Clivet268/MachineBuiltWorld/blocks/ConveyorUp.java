package com.Clivet268.MachineBuiltWorld.blocks;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ToolType;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Stream;

import static net.minecraft.util.Direction.*;
import static net.minecraft.util.Direction.SOUTH;

public class ConveyorUp extends Conveyor{
    public static final DirectionProperty HORIZONTAL_FACING = HorizontalBlock.HORIZONTAL_FACING;
    private static final Map<Direction, VoxelShape> SHAPES = Maps.newEnumMap(
            ImmutableMap.of(Direction.NORTH, Block.makeCuboidShape(5.5D, 3.0D, 11.0D, 10.5D, 13.0D, 16.0D)
                    , Direction.SOUTH, Block.makeCuboidShape(5.5D, 3.0D, 0.0D, 10.5D, 13.0D, 5.0D)
                    , Direction.WEST, Block.makeCuboidShape(11.0D, 3.0D, 5.5D, 16.0D, 13.0D, 10.5D)
                    , Direction.EAST, Block.makeCuboidShape(0.0D, 3.0D, 5.5D, 5.0D, 13.0D, 10.5D)));
    protected static final AxisAlignedBB CONVEYOR_AABB =
            new AxisAlignedBB(0.0625D, 0.0625D, 1.0D, 0.9375D, 0.9375D, 0.8D);
    public static final VoxelShape[] SHAAPES = new VoxelShape[]{
            Stream.of(
                    Block.makeCuboidShape(1, 1, 13, 15, 15, 16),
                    Block.makeCuboidShape(0, 0, 12, 1, 16, 16),
                    Block.makeCuboidShape(15, 0, 12, 16, 16, 16),
                    Block.makeCuboidShape(1, 15, 13.5, 15, 16, 15.5),
                    Block.makeCuboidShape(1, 0, 13.5, 15, 1, 15.5)
            ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get(),
            Stream.of(
                    Block.makeCuboidShape(1, 1, 13, 15, 15, 16),
                    Block.makeCuboidShape(0, 0, 12, 1, 16, 16),
                    Block.makeCuboidShape(15, 0, 12, 16, 16, 16),
                    Block.makeCuboidShape(1, 15, 13.5, 15, 16, 15.5),
                    Block.makeCuboidShape(1, 0, 13.5, 15, 1, 15.5)
            ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get()
    };




    public ConveyorUp(Block.Properties properties) {
            super(properties);
    }

    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAAPES[0];
    }

    public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
        Direction direction = state.get(HORIZONTAL_FACING);
        BlockPos blockpos = pos.offset(direction.getOpposite());
        BlockState blockstate = worldIn.getBlockState(blockpos);
        return blockstate.isSolidSide(worldIn, blockpos, direction);
    }

    @Nullable
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        BlockState blockstate = this.getDefaultState();
        IWorldReader iworldreader = context.getWorld();
        BlockPos blockpos = context.getPos();
        Direction[] adirection = context.getNearestLookingDirections();

        for(Direction direction : adirection) {
            if (direction.getAxis().isHorizontal()) {
                Direction direction1 = direction.getOpposite();
                blockstate = blockstate.with(HORIZONTAL_FACING, direction1);
                if (blockstate.isValidPosition(iworldreader, blockpos)) {
                    return blockstate;
                }
            }
        }

        return null;
    }
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(HORIZONTAL_FACING);
    }

    @Override
    public void moove(World world, BlockPos posi) {
        AxisAlignedBB axisalignedbb = CONVEYOR_AABB.offset(posi);
        List<? extends Entity> list;
        list = world.getEntitiesWithinAABBExcludingEntity((Entity)null, axisalignedbb);
        if (!list.isEmpty()) {
            for(Entity entity : list) {
                System.out.println(entity);
                BlockState state = world.getBlockState(posi);
                if (state.get(FACING).equals(NORTH)) {
                    entity.addVelocity(0.0D, 0.1D,0.03D);
                }
                else if(state.get(FACING).equals(WEST)) {
                    entity.addVelocity(0.03D, 0.1D,0.0D);
                }
                else if (state.get(FACING).equals(EAST)) {
                    entity.addVelocity(-0.03D, 0.1D,0.0D);
                }
                else if (state.get(FACING).equals(SOUTH)) {
                    entity.addVelocity(0.0D, 0.1D,-0.03D);
                }
            }
        }
    }

    @Override
    public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
        moove(worldIn, pos);
    }
}
