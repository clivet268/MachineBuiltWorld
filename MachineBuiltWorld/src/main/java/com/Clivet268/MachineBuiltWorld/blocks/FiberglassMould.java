package com.Clivet268.MachineBuiltWorld.blocks;

import com.Clivet268.MachineBuiltWorld.state.MoreStateProperties;
import com.Clivet268.MachineBuiltWorld.tileentity.FiberglassMouldTile;
import com.Clivet268.MachineBuiltWorld.util.RegistryHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.IntegerProperty;
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

public class FiberglassMould extends Block{
    //public static final IntegerProperty AGE = BlockStateProperties.AGE_0_1;
    public static final IntegerProperty FULL = MoreStateProperties.FILLED;
    protected static final VoxelShape[] FILLNOFILL = new VoxelShape[]{Stream.of(
            Block.makeCuboidShape(3.5, 1, 14, 12.5, 3.5, 15),
            Block.makeCuboidShape(2.5, 1, 1, 3.5, 3.5, 15),
            Block.makeCuboidShape(3.5, 1, 1, 12.5, 3.5, 2),
            Block.makeCuboidShape(12.5, 1, 1, 13.5, 3.5, 15),
            Block.makeCuboidShape(2.5, 0, 1, 13.5, 1, 15)
    ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get(),Stream.of(
            Block.makeCuboidShape(3.5, 1, 14, 12.5, 3.5, 15),
            Block.makeCuboidShape(2.5, 1, 1, 3.5, 3.5, 15),
            Block.makeCuboidShape(3.5, 1, 1, 12.5, 3.5, 2),
            Block.makeCuboidShape(12.5, 1, 1, 13.5, 3.5, 15),
            Block.makeCuboidShape(2.5, 0, 1, 13.5, 1, 15),
            Block.makeCuboidShape(4, 0.75, 2.75, 12, 1.75, 13.25)
    ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get(), Stream.of(
            Block.makeCuboidShape(3.5, 1, 14, 12.5, 3.5, 15),
            Block.makeCuboidShape(2.5, 1, 1, 3.5, 3.5, 15),
            Block.makeCuboidShape(3.5, 1, 1, 12.5, 3.5, 2),
            Block.makeCuboidShape(12.5, 1, 1, 13.5, 3.5, 15),
            Block.makeCuboidShape(2.5, 0, 1, 13.5, 1, 15),
            Block.makeCuboidShape(3.5, 0.75, 2, 12.5, 3.25, 14)
    ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get(), Stream.of(
            Block.makeCuboidShape(3.5, 1, 14, 12.5, 3.5, 15),
            Block.makeCuboidShape(2.5, 1, 1, 3.5, 3.5, 15),
            Block.makeCuboidShape(3.5, 1, 1, 12.5, 3.5, 2),
            Block.makeCuboidShape(12.5, 1, 1, 13.5, 3.5, 15),
            Block.makeCuboidShape(2.5, 0, 1, 13.5, 1, 15),
            Block.makeCuboidShape(3.5, 0.75, 2, 12.5, 3.25, 14)
    ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get()};
    public FiberglassMould(){
        super(Properties.create((Material.GLASS))
                .hardnessAndResistance(1.0f, 1.0f)
                .sound(SoundType.METAL)
                .harvestLevel(0)
                .tickRandomly()
                .lightValue(2)
        );
    }

    @Override
    public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {
        int i = state.get(FULL);
        //int ii = state.get(AGE);
        if (i == 2) {
                if (worldIn.rand.nextInt(8) == 0)
                {
                    worldIn.setBlockState(pos, state.with(FULL, Integer.valueOf(3)), 3);
                }
        }

    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        int i = state.get(FULL);
        return FILLNOFILL[i];
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }


    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new FiberglassMouldTile().getTileEntity();
    }

   // @Nullable
    //@Override
   // public BlockState getStateForPlacement(BlockItemUseContext context) {
    //    return getDefaultState().with(BlockStateProperties.FACING, context.getNearestLookingDirection().getOpposite());
   // }

    @SuppressWarnings("deprecation")
    @Override
    public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult trace) {
        if (!world.isRemote) {
            TileEntity tileEntity = world.getTileEntity(pos);
            if (tileEntity instanceof FiberglassMouldTile) {
                if(state.get(FULL) == 0) {
                    if (player.getHeldItemMainhand().getItem() == RegistryHandler.GLASH_SHARDS.get()) {
                        int mainh = player.inventory.currentItem;
                        player.inventory.decrStackSize(mainh, 1);
                        world.setBlockState(pos, state.with(FULL, Integer.valueOf(1)), 3);
                        //System.out.println("Step 1 success");
                    }
                    else {
                        //System.out.println("You were not holding a glass pane");
                    }
                }

                else if(state.get(FULL) == 1) {
                    if (player.getHeldItemMainhand().getItem() == RegistryHandler.RESIN_BUCKET.get()) {
                        int mainh = player.inventory.currentItem;
                        player.inventory.setInventorySlotContents(mainh, new ItemStack(Items.BUCKET));
                        world.setBlockState(pos, state.with(FULL, Integer.valueOf(2)), 3);
                        //System.out.println("Step 2 success");
                    }
                    else {//System.out.println("You were not holding a resin bucket");
                         }
                    if (player.getHeldItemMainhand().getItem() == Items.AIR && player.isCrouching() == true) {
                        int mainh = player.inventory.currentItem;
                        player.inventory.setInventorySlotContents(mainh, new ItemStack(RegistryHandler.GLASH_SHARDS.get()));
                        world.setBlockState(pos, state.with(FULL, Integer.valueOf(0)), 3);
                        //System.out.println("Picked that back up");
                    }
                    else {//System.out.println("Ya had something else in ur hand");
                    }
                }

                else if(state.get(FULL) == 2) {
                    if (player.getHeldItemMainhand().getItem() == Items.BUCKET && player.isCrouching() == true) {
                        int mainh = player.inventory.currentItem;
                        player.inventory.setInventorySlotContents(mainh, new ItemStack(RegistryHandler.RESIN_BUCKET.get()));
                        world.setBlockState(pos, state.with(FULL, Integer.valueOf(1)), 3);
                        //System.out.println("Picked that thing back up");
                    }
                    else {//System.out.println("Gotta Wait");
                    }
                }
                else if(state.get(FULL) == 3) {
                    if (player.getHeldItemMainhand().getItem() == Items.WOODEN_SWORD) {
                        player.addItemStackToInventory(new ItemStack(RegistryHandler.FIBER_GLASS.get()));
                        world.setBlockState(pos, state.with(FULL, Integer.valueOf(0)), 3);
                       // System.out.println("You did it?");

                    } else {
                        //System.out.println("You didnt do it?");
                    }
                }
            }
        }
        return ActionResultType.SUCCESS;
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
       builder.add(FULL);
    }
    @Override
    public float getAmbientOcclusionLightValue(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return 1.0F;
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
}
