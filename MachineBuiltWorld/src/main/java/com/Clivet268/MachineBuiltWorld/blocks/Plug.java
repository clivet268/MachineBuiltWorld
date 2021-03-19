package com.Clivet268.MachineBuiltWorld.blocks;

import com.Clivet268.MachineBuiltWorld.tileentity.PlugTile;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import java.util.Random;
import java.util.stream.Stream;

public class Plug extends Block{
    //public static final IntegerProperty AGE = BlockStateProperties.AGE_0_1;
    public static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;
    private Direction dir = Direction.EAST;
    protected static final VoxelShape[] FILLNOFILL = new VoxelShape[]{Stream.of(
            Block.makeCuboidShape(3.5, 1, 14, 12.5, 3.5, 15),
            Block.makeCuboidShape(2.5, 1, 1, 3.5, 3.5, 15),
            Block.makeCuboidShape(3.5, 1, 1, 12.5, 3.5, 2),
            Block.makeCuboidShape(12.5, 1, 1, 13.5, 3.5, 15),
            Block.makeCuboidShape(2.5, 0, 1, 13.5, 1, 15)
    ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get()};
    public Plug(){
        super(Properties.create((Material.IRON))
                .hardnessAndResistance(1.0f, 1.0f)
                .sound(SoundType.METAL)
                .harvestLevel(0)
                .tickRandomly()
                .lightValue(2)
        );
    }

    @Override
    public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {

    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return FILLNOFILL[0];
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }


    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new PlugTile().getTileEntity();
    }

    @SuppressWarnings("deprecation")
    @Override
    public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult trace) {

        return ActionResultType.SUCCESS;
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
       builder.add(FACING);
    }
    @Override
    public boolean propagatesSkylightDown(BlockState state, IBlockReader reader, BlockPos pos) {
        return false;
    }
    @Override
    public boolean causesSuffocation(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return false;
    }
    @Override
    public boolean isNormalCube(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return false;
    }
    @Override
    public boolean canEntitySpawn(BlockState state, IBlockReader worldIn, BlockPos pos, EntityType<?> type) { return false; }
    @Override
    public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
        BlockPos blockpos = pos.down();
        return hasSolidSideOnTop(worldIn, blockpos) || hasEnoughSolidSide(worldIn, blockpos, Direction.UP);
    }
    public Direction disDiraction(){
        return this.dir;
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        this.dir = context.getNearestLookingDirection().getOpposite();
            return getDefaultState().with(FACING,context.getPlacementHorizontalFacing().getOpposite());
    }
}
