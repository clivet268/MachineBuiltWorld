package com.Clivet268.MachineBuiltWorld.blocks;

import com.Clivet268.MachineBuiltWorld.state.MoreStateProperties;
import com.Clivet268.MachineBuiltWorld.tileentity.CrystallizationChamberPartTile;
import com.Clivet268.MachineBuiltWorld.util.RegistryHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
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
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import java.util.Random;
import java.util.stream.Stream;

//TODO make textures
//TODO make worky better, GUI?
public class CrystallizationChamberPart extends Block{
    public static final DirectionProperty PROPERTY_FACING = BlockStateProperties.FACING;
    public static final IntegerProperty STATE = MoreStateProperties.CRYSTILIZATIONSTATES;
    protected static final VoxelShape[] CHAMBERSTATE = new VoxelShape[]{
            Stream.of(
                    Block.makeCuboidShape(1, 0, 1, 15, 1, 15),
                    Block.makeCuboidShape(0, 1, 0, 16, 16, 1),
                    Block.makeCuboidShape(0, 1, 15, 16, 16, 16),
                    Block.makeCuboidShape(0, 1, 1, 1, 16, 15),
                    Block.makeCuboidShape(15, 1, 1, 16, 16, 15)
            ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get(),
    //down
            Stream.of(
                    Block.makeCuboidShape(1, 15, 1, 15, 16, 15),
                    Block.makeCuboidShape(0, 0, 15, 16, 15, 16),
                    Block.makeCuboidShape(0, 0, 0, 16, 15, 1),
                    Block.makeCuboidShape(0, 0, 1, 1, 15, 15),
                    Block.makeCuboidShape(15, 0, 1, 16, 15, 15)
            ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get(),
    //combined
            Stream.of(
                    Block.makeCuboidShape(1, 0, 1, 15, 1, 15),
                    Block.makeCuboidShape(0, 1, 0, 16, 16, 1),
                    Block.makeCuboidShape(0, 1, 15, 16, 16, 16),
                    Block.makeCuboidShape(0, 1, 1, 1, 16, 15),
                    Block.makeCuboidShape(15, 1, 1, 16, 16, 15),
                    Block.makeCuboidShape(1, 31, 1, 15, 32, 15),
                    Block.makeCuboidShape(0, 16, 15, 16, 31, 16),
                    Block.makeCuboidShape(0, 16, 0, 16, 31, 1),
                    Block.makeCuboidShape(0, 16, 1, 1, 31, 15),
                    Block.makeCuboidShape(15, 16, 1, 16, 31, 15)
            ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get(),


    //combined with stick
            Stream.of(
                    Block.makeCuboidShape(1, 0, 1, 15, 1, 15),
                    Block.makeCuboidShape(0, 1, 0, 16, 16, 1),
                    Block.makeCuboidShape(0, 1, 15, 16, 16, 16),
                    Block.makeCuboidShape(0, 1, 1, 1, 16, 15),
                    Block.makeCuboidShape(15, 1, 1, 16, 16, 15),
                    Block.makeCuboidShape(1, 31, 1, 15, 32, 15),
                    Block.makeCuboidShape(0, 16, 15, 16, 31, 16),
                    Block.makeCuboidShape(0, 16, 0, 16, 31, 1),
                    Block.makeCuboidShape(0, 16, 1, 1, 31, 15),
                    Block.makeCuboidShape(15, 16, 1, 16, 31, 15),
                    Block.makeCuboidShape(7.5, 29, 1, 8.5, 30, 14)
            ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get(),

    //combined with stick stage one
            Stream.of(
                    Block.makeCuboidShape(0, 15, 0, 16, 16, 16),
                    Block.makeCuboidShape(0, 0, 0, 16, 15.0, 1),
                    Block.makeCuboidShape(0, 0, 15, 16, 15.0, 16),
                    Block.makeCuboidShape(15, 0, 1, 16, 15.0, 15),
                    Block.makeCuboidShape(0, 0, 1, 1, 15.0, 15)
            ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get(),

    //combined with stick stage two
            Stream.of(
                    Block.makeCuboidShape(0, 15, 0, 16, 16, 16),
                    Block.makeCuboidShape(0, 0, 0, 16, 15.0, 1),
                    Block.makeCuboidShape(0, 0, 15, 16, 15.0, 16),
                    Block.makeCuboidShape(15, 0, 1, 16, 15.0, 15),
                    Block.makeCuboidShape(0, 0, 1, 1, 15.0, 15)
            ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get(),

    //combined with stick stage three
            Stream.of(
                    Block.makeCuboidShape(0, 15, 0, 16, 16, 16),
                    Block.makeCuboidShape(0, 0, 0, 16, 15.0, 1),
                    Block.makeCuboidShape(0, 0, 15, 16, 15.0, 16),
                    Block.makeCuboidShape(15, 0, 1, 16, 15.0, 15),
                    Block.makeCuboidShape(0, 0, 1, 1, 15.0, 15)
            ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get()

    };
    public CrystallizationChamberPart(){
        super(Properties.create((Material.GLASS))
                .hardnessAndResistance(1.0f, 1.0f)
                .sound(SoundType.GLASS)
                .harvestLevel(0)
                .tickRandomly()
                .lightValue(2)
        );
    }

    @Override
    public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {
        int i = state.get(STATE);
        //int ii = state.get(AGE);
        if (i == 3 || i == 5|| i==4 ){
                if (worldIn.rand.nextInt(2) == 0)
                {
                    if (i == 3){
                    worldIn.setBlockState(pos, state.with(STATE, 4), 3);
                }
                    else if (i == 4) {
                        worldIn.setBlockState(pos, state.with(STATE, 5), 3);

                    }
                    else if (i == 5){
                            worldIn.setBlockState(pos, state.with(STATE, 6), 3);
                        }
                    }
            if (new Random().nextInt(7) == 0||new Random().nextInt(7) == 1)
            {
                worldIn.addParticle(ParticleTypes.DRIPPING_WATER, pos.getX(), pos.getY() + 0.75, pos.getZ(), 0, -0.1, 0);
            }
        }

    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        switch(state.get(STATE)) {
            case (0):
                return CHAMBERSTATE[0];
            case (1):
                return CHAMBERSTATE[1];
            case (2):
                return CHAMBERSTATE[2];
            case (3):
                return CHAMBERSTATE[3];
            case (4):
                return CHAMBERSTATE[4];
            case (5):
                return CHAMBERSTATE[5];
            case (6):
                return CHAMBERSTATE[6];
            default:
                return CHAMBERSTATE[1];
        }
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }


    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new CrystallizationChamberPartTile().getTileEntity();
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        if(context.getNearestLookingDirection().getOpposite() == Direction.DOWN){
            return getDefaultState().with(STATE, 1);
        }
        return getDefaultState().with(STATE, 0);
    }

    @SuppressWarnings("deprecation")
    @Override
    public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult trace) {
        if (!world.isRemote) {
            TileEntity tileEntity = world.getTileEntity(pos);
            if (tileEntity instanceof CrystallizationChamberPartTile) {
                if (state.get(STATE) == 0) {
                    if (world.getBlockState(pos.add(0, 1, 0)).getBlock()
                            == RegistryHandler.CRYSTALLIZATION_CHAMBER_PART.get()) {
                        if (world.getBlockState(pos.add(0, 1, 0)).getBlockState().get(STATE)
                                == 1) {
                            world.setBlockState(pos, state.with(STATE, 2)
                                    , 3);
                            world.getBlockState(pos.add(0, 1, 0)).getBlock().removedByPlayer(world.getBlockState(pos.add(0, 1, 0)), world, pos.add(0, 1, 0), player, false, world.getBlockState(pos.add(0, 1, 0)).getFluidState());
                        }
                    }
                }
                if (state.get(STATE) == 2) {
                    if (player.getHeldItemMainhand().getItem() == Items.STICK) {
                        int mainh = player.inventory.currentItem;
                        player.inventory.decrStackSize(mainh, 1);
                        world.setBlockState(pos, state.with(STATE, 3), 3);
                    }
                }
                if (state.get(STATE) == 6) {
                    if (player.getHeldItemMainhand().getItem() == Items.AIR) {
                        world.addEntity(new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(),
                                new ItemStack(RegistryHandler.RESONATING_CRYSTAL.get())));
                        world.setBlockState(pos, state.with(STATE, 2), 3);
                    }
                }
            }
        }
            return ActionResultType.SUCCESS;

    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
       builder.add(STATE)
              // .add(PROPERTY_FACING)
       ;
    }
    @Override
    public float getAmbientOcclusionLightValue(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return 1.0F;
    }
    @Override
    public boolean propagatesSkylightDown(BlockState state, IBlockReader reader, BlockPos pos) {
        return true;
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

}
