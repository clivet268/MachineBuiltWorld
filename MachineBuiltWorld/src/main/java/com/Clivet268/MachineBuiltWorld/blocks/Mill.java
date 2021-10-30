package com.Clivet268.MachineBuiltWorld.blocks;

import com.Clivet268.MachineBuiltWorld.inventory.Containers.MillContainer;
import com.Clivet268.MachineBuiltWorld.state.MoreStateProperties;
import com.Clivet268.MachineBuiltWorld.tileentity.MillTile;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;
import java.util.Random;
import java.util.stream.Stream;

public class Mill extends Block implements IWorkable{

    public static final IntegerProperty PROPERTY_WORK = MoreStateProperties.MILLOAS;
    //public static final EnumProperty<MillBit> PROPERTY_MILL_BIT = MoreStateProperties.MILL_BIT;

    //public static final BooleanProperty PROPERTY_ONOROFF = MoreStateProperties.ONOROFF;
    public Mill() {
        super(Block.Properties.create(Material.IRON)
                .hardnessAndResistance(5.0f, 6.0f)
                .sound(SoundType.METAL)
                .harvestLevel(1)
                .harvestTool(ToolType.PICKAXE));

        this.setDefaultState(this.stateContainer.getBaseState().with(PROPERTY_WORK, Integer.valueOf(0))
                //.with(
                        //PROPERTY_MILL_BIT, MillBit.REST)
                //.with(PROPERTY_ONOROFF, Boolean.valueOf(false))
                );

    }
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(
                PROPERTY_WORK
                //PROPERTY_MILL_BIT
                //PROPERTY_ONOROFF
        );
    }

    private static final VoxelShape RESTN = Stream.of(
            Block.makeCuboidShape(4, 7, 7, 5, 10, 8),
            Block.makeCuboidShape(0, 0, 0, 16, 1, 16),
            Block.makeCuboidShape(0, 1, 0, 1, 14, 1),
            Block.makeCuboidShape(15, 1, 0, 16, 14, 1),
            Block.makeCuboidShape(0, 1, 15, 1, 14, 16),
            Block.makeCuboidShape(15, 1, 15, 16, 14, 16),
            Block.makeCuboidShape(1, 13, 0, 15, 14, 1),
            Block.makeCuboidShape(1, 13, 15, 15, 14, 16),
            Block.makeCuboidShape(0, 13, 1, 1, 14, 15),
            Block.makeCuboidShape(15, 13, 1, 16, 14, 15),
            Block.makeCuboidShape(5, 13, 7, 15, 14, 8),
            Block.makeCuboidShape(4, 13, 1, 5, 14, 15),
            Block.makeCuboidShape(1, 13, 7, 4, 14, 8),
            Block.makeCuboidShape(3, 10, 6, 6, 13, 9)
    ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get();

    private static final VoxelShape ACTIVE1N = Stream.of(
                    Block.makeCuboidShape(6, 7, 8, 7, 10, 9),
                    Block.makeCuboidShape(0, 0, 0, 16, 1, 16),
                    Block.makeCuboidShape(0, 1, 0, 1, 14, 1),
                    Block.makeCuboidShape(15, 1, 0, 16, 14, 1),
                    Block.makeCuboidShape(0, 1, 15, 1, 14, 16),
                    Block.makeCuboidShape(15, 1, 15, 16, 14, 16),
                    Block.makeCuboidShape(1, 13, 0, 15, 14, 1),
                    Block.makeCuboidShape(1, 13, 15, 15, 14, 16),
                    Block.makeCuboidShape(0, 13, 1, 1, 14, 15),
                    Block.makeCuboidShape(15, 13, 1, 16, 14, 15),
                    Block.makeCuboidShape(7, 13, 8, 15, 14, 9),
                    Block.makeCuboidShape(6, 13, 1, 7, 14, 15),
                    Block.makeCuboidShape(1, 13, 8, 6, 14, 9),
                    Block.makeCuboidShape(5, 10, 7, 8, 13, 10)
            ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get();

    private static final VoxelShape ACTIVE2N = Stream.of(
            Block.makeCuboidShape(4, 7, 7, 5, 10, 8),
            Block.makeCuboidShape(0, 0, 0, 16, 1, 16),
            Block.makeCuboidShape(0, 1, 0, 1, 14, 1),
            Block.makeCuboidShape(15, 1, 0, 16, 14, 1),
            Block.makeCuboidShape(0, 1, 15, 1, 14, 16),
            Block.makeCuboidShape(15, 1, 15, 16, 14, 16),
            Block.makeCuboidShape(1, 13, 0, 15, 14, 1),
            Block.makeCuboidShape(1, 13, 15, 15, 14, 16),
            Block.makeCuboidShape(0, 13, 1, 1, 14, 15),
            Block.makeCuboidShape(15, 13, 1, 16, 14, 15),
            Block.makeCuboidShape(5, 13, 7, 15, 14, 8),
            Block.makeCuboidShape(4, 13, 1, 5, 14, 15),
            Block.makeCuboidShape(1, 13, 7, 4, 14, 8),
            Block.makeCuboidShape(3, 10, 6, 6, 13, 9)
    ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get();

    @Override
    public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {
    this.runTheMill(state, worldIn, pos);
        //state.
        /*if (state.get(PROPERTY_STAGE) == 0) {
            if (true) {
                int i = this.getNumBambooBlocksBelow(worldIn, pos) + 1;
                if (i < 16 && net.minecraftforge.common.ForgeHooks.onCropsGrowPre(worldIn, pos, state, rand.nextInt(3) == 0 && worldIn.isAirBlock(pos.up()) && worldIn.getLightSubtracted(pos.up(), 0) >= 9)) {
                    this.grow(state, worldIn, pos, rand, i);
                    net.minecraftforge.common.ForgeHooks.onCropsGrowPost(worldIn, pos, state);
                }
            }

        }

         */
    }

    //canWork(IBlockReader worldIn, BlockPos pos, BlockState state, boolean isClient);

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    boolean ONOROFF = setMillOnOrOff(false);

    public boolean getMillOnOrOff(){
        return ONOROFF;
    }
    public boolean setMillOnOrOff(boolean onoroff){
        return onoroff;
    }

    protected void runTheMill(BlockState blockStateIn, World worldIn, BlockPos posIn) {

        BlockState blockstate = worldIn.getBlockState(posIn);
        //BlockPos blockpos = posIn.down(2);
        //BlockState blockstate1 = worldIn.getBlockState(blockpos);
        //MillBit millBit = MillBit.REST;
    /*
        if (worldIn.isDaytime()) {
            worldIn.setBlockState(posIn, this.getDefaultState() );
            setMillOnOrOff(true);
            //Boolean.valueOf(true);
        }
        else {
            setMillOnOrOff(false);
        }

     */
        //int counter = 0;
        //if (blockstate.getBlock() == RegistryHandler.MILL.get()
                //&& worldIn.isDaytime()
                //getMillOnOrOff() == true
       // ) {
            if ((int)blockStateIn.get(PROPERTY_WORK) == 0) {
                worldIn.setBlockState(posIn, blockstate.with(PROPERTY_WORK, Integer.valueOf((int)blockStateIn.get(PROPERTY_WORK) + 1)), 3);
                //PROPERTY_WORK++;
            } else if ((int)blockStateIn.get(PROPERTY_WORK) == 1) {
                worldIn.setBlockState(posIn, blockstate.with(PROPERTY_WORK, Integer.valueOf((int)blockStateIn.get(PROPERTY_WORK) + 1)), 3);
                //PROPERTY_WORK++;
            } else if ((int)blockStateIn.get(PROPERTY_WORK) == 2) {
                worldIn.setBlockState(posIn, blockstate.with(PROPERTY_WORK, Integer.valueOf(1)), 3);
                //PROPERTY_WORK = 1;
            } else {
                throw new IllegalStateException("You Can't Count");
            }
        }
    //}

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {

        VoxelShape voxelshape;
        if (state.get(PROPERTY_WORK) == 0) {
            voxelshape = RESTN;
        }
        else if((int) state.get(PROPERTY_WORK) == 1)
        {
             voxelshape = ACTIVE1N;
        }
        else if((int) state.get(PROPERTY_WORK) == 2)
        {
             voxelshape = ACTIVE2N;
        }
        else{
            throw new IllegalStateException("You Can't Count");
        }
        System.out.println(state.get(PROPERTY_WORK));
        return voxelshape;
    }

    @Override
    public boolean canWork(IBlockReader worldIn, BlockPos pos, BlockState state, boolean isClient) {
        return true;
    }

    @Override
    public void work(ServerWorld worldIn, Random rand, BlockPos pos, BlockState state) {

    }
    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new MillTile().getTileEntity();
    }

    @SuppressWarnings("deprecation")
    @Override
    public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult trace) {
        if (!world.isRemote) {
            TileEntity tileEntity = world.getTileEntity(pos);
            if (tileEntity instanceof MillTile) {
                INamedContainerProvider containerProvider = new INamedContainerProvider() {
                    @Override
                    public ITextComponent getDisplayName() {
                        return new TranslationTextComponent("screen.machinebuiltworld.mill");
                    }

                    @Override
                    public Container createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
                        return new MillContainer(i, world, playerInventory, pos);
                    }
                };
                NetworkHooks.openGui((ServerPlayerEntity) player, containerProvider, tileEntity.getPos());
            } else {
                throw new IllegalStateException("Our named container provider is missing!");
            }
        }
        return ActionResultType.SUCCESS;
    }
}
