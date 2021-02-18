package com.Clivet268.MachineBuiltWorld.blocks;

import com.Clivet268.MachineBuiltWorld.inventory.Containers.TTTBatteryContainer;
import com.Clivet268.MachineBuiltWorld.state.MoreStateProperties;
import com.Clivet268.MachineBuiltWorld.tileentity.TTTBatteryTile;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.stats.Stats;
import net.minecraft.tileentity.FurnaceTileEntity;
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


public class Crusher extends Block{

    public static final IntegerProperty CRUSH = MoreStateProperties.CRUSHING;
    public static final BooleanProperty OC = MoreStateProperties.OPENCLOSE;
    public static final BooleanProperty FULL = MoreStateProperties.CRUSHCONTAIN;
    public Crusher() {
        super(Properties.create(Material.IRON)
                .hardnessAndResistance(5.0f, 6.0f)
                .sound(SoundType.METAL)
                .harvestLevel(1)
                .harvestTool(ToolType.PICKAXE));

        this.setDefaultState(this.stateContainer.getBaseState().with(CRUSH, Integer.valueOf(0))
                .with(OC, Boolean.valueOf(false)).with(FULL, Boolean.valueOf(false)));

    }
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(CRUSH,OC,FULL);
    }
    protected static final VoxelShape[] ALLTHESHAPES = new VoxelShape[]{Stream.of(
            Block.makeCuboidShape(0, 2, 0, 1, 17, 16),
            Block.makeCuboidShape(1, 2, 0, 15, 17, 1),
            Block.makeCuboidShape(15, 2, 0, 16, 17, 16),
            Block.makeCuboidShape(1, 2, 15, 15, 17, 16),
            Block.makeCuboidShape(14, 0, 0, 16, 2, 16),
            Block.makeCuboidShape(2.5, 8.5, 1, 6.5, 12.5, 15),
            Block.makeCuboidShape(0, 0, 0, 2, 2, 16),
            Block.makeCuboidShape(2, 0, 13, 14, 2, 16),
            Block.makeCuboidShape(2, 0, 0, 14, 2, 3),
            Block.makeCuboidShape(6, 3, 1, 10, 7, 15),
            Block.makeCuboidShape(4.5, 12.5, 12.75, 6.25, 13.5, 14.5),
            Block.makeCuboidShape(4, 12.5, 9.25, 5.75, 13.5, 11),
            Block.makeCuboidShape(3.5, 12.5, 5.25, 5.25, 13.5, 7),
            Block.makeCuboidShape(2.75, 12.5, 1.25, 4.5, 13.5, 3),
            Block.makeCuboidShape(1.5, 10.25, 12.75, 2.5, 12.25, 14.75),
            Block.makeCuboidShape(6.5, 10.5, 1.25, 7.5, 12.25, 3),
            Block.makeCuboidShape(6.5, 10, 5.25, 7.5, 11.75, 7),
            Block.makeCuboidShape(6.5, 9.5, 9.25, 7.5, 11.25, 11),
            Block.makeCuboidShape(6.5, 9, 12.75, 7.5, 10.75, 14.5),
            Block.makeCuboidShape(1.5, 10.25, 12.75, 2.5, 12.25, 14.75),
            Block.makeCuboidShape(1.5, 9.75, 9, 2.5, 11.75, 11),
            Block.makeCuboidShape(1.5, 9.25, 5, 2.5, 11.25, 7),
            Block.makeCuboidShape(1.5, 8.75, 1.25, 2.5, 10.75, 3.25),
            Block.makeCuboidShape(4.5, 7.5, 1.25, 6.25, 8.5, 3),
            Block.makeCuboidShape(4, 7.5, 5.25, 5.75, 8.5, 7),
            Block.makeCuboidShape(3.25, 7.5, 9.25, 5, 8.5, 11),
            Block.makeCuboidShape(2.75, 7.5, 12.75, 4.5, 8.5, 14.5),
            Block.makeCuboidShape(8, 7, 12.75, 9.75, 8, 14.5),
            Block.makeCuboidShape(7.5, 7, 9.25, 9.25, 8, 11),
            Block.makeCuboidShape(7, 7, 5.25, 8.75, 8, 7),
            Block.makeCuboidShape(6.25, 7, 1.25, 8, 8, 3),
            Block.makeCuboidShape(5, 4.75, 12.75, 6, 6.75, 14.75),
            Block.makeCuboidShape(10, 5, 1.25, 11, 6.75, 3),
            Block.makeCuboidShape(10, 4.5, 5.25, 11, 6.25, 7),
            Block.makeCuboidShape(10, 4, 9.25, 11, 5.75, 11),
            Block.makeCuboidShape(10, 3.5, 12.75, 11, 5.25, 14.5),
            Block.makeCuboidShape(5, 4.25, 9, 6, 6.25, 11),
            Block.makeCuboidShape(5, 3.75, 5, 6, 5.75, 7),
            Block.makeCuboidShape(5, 3.25, 1.25, 6, 5.25, 3.25),
            Block.makeCuboidShape(8, 2, 1.25, 9.75, 3, 3),
            Block.makeCuboidShape(7.5, 2, 5.25, 9.25, 3, 7),
            Block.makeCuboidShape(6.75, 2, 9.25, 8.5, 3, 11),
            Block.makeCuboidShape(6.25, 2, 12.75, 8, 3, 14.5),
            Block.makeCuboidShape(11, 7.5, 5.25, 12.75, 8.5, 7),
            Block.makeCuboidShape(11.5, 7.5, 1.25, 13.25, 8.5, 3),
            Block.makeCuboidShape(10.25, 7.5, 9.25, 12, 8.5, 11),
            Block.makeCuboidShape(9.75, 7.5, 12.75, 11.5, 8.5, 14.5),
            Block.makeCuboidShape(9.5, 8.5, 1, 13.5, 12.5, 15),
            Block.makeCuboidShape(8.5, 10.25, 12.75, 9.5, 12.25, 14.75),
            Block.makeCuboidShape(8.5, 9.75, 9, 9.5, 11.75, 11),
            Block.makeCuboidShape(8.5, 9.25, 5, 9.5, 11.25, 7),
            Block.makeCuboidShape(8.5, 8.75, 1.25, 9.5, 10.75, 3.25),
            Block.makeCuboidShape(13.5, 10.5, 1.25, 14.5, 12.25, 3),
            Block.makeCuboidShape(13.5, 10, 5.25, 14.5, 11.75, 7),
            Block.makeCuboidShape(13.5, 9.5, 9.25, 14.5, 11.25, 11),
            Block.makeCuboidShape(13.5, 9, 12.75, 14.5, 10.75, 14.5),
            Block.makeCuboidShape(11.5, 12.5, 12.75, 13.25, 13.5, 14.5),
            Block.makeCuboidShape(10.5, 12.5, 5.25, 12.25, 13.5, 7),
            Block.makeCuboidShape(11, 12.5, 9.25, 12.75, 13.5, 11),
            Block.makeCuboidShape(9.75, 12.5, 1.25, 11.5, 13.5, 3),
            Block.makeCuboidShape(2, 0, 3, 14, 0, 8),
            Block.makeCuboidShape(2, 0, 8, 14, 0, 13)
    ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get()};


    public TileEntity createNewTileEntity(IBlockReader p_196283_1_) {
        return new FurnaceTileEntity();
    }

    protected void interactWith(World p_220089_1_, BlockPos p_220089_2_, PlayerEntity p_220089_3_) {
        TileEntity lvt_4_1_ = p_220089_1_.getTileEntity(p_220089_2_);
        if (lvt_4_1_ instanceof FurnaceTileEntity) {
            p_220089_3_.openContainer((INamedContainerProvider)lvt_4_1_);
            p_220089_3_.addStat(Stats.INTERACT_WITH_FURNACE);
        }

    }

    /*
    @Override
    public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult trace) {
        if (!world.isRemote) {
            TileEntity tileEntity = world.getTileEntity(pos);
            if (tileEntity instanceof CrusherTile) {
                //if(player.getHeldItemMainhand().getItem() == RegistryHandler.MULTIMETER.get())
                //{
                    INamedContainerProvider containerProvider = new INamedContainerProvider() {
                        @Override
                        public ITextComponent getDisplayName() {
                            return new TranslationTextComponent("screen.machinebuiltworld.crusher");
                        }

                        @Override
                        public Container createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
                            return new AbstractCrusherContainer(i, world, pos, playerInventory, playerEntity);
                        }
                    };
                    NetworkHooks.openGui((ServerPlayerEntity) player, containerProvider, tileEntity.getPos());
                //}
                //
                }else {
                throw new IllegalStateException("Our named container provider is missing!");
            }
                if(state.get(CRUSH) == 0) {
                    if (player.getHeldItemMainhand().getItem() == Items.AIR) {
                        world.setBlockState(pos, state.with(CRUSH, Integer.valueOf(1)), 3);
                        System.out.println("Step 1 success");
                    }
                    else {
                        System.out.println("idk whats wrong");
                    }
                }

                else if(state.get(CRUSH) == 1 || state.get(CRUSH) == 2) {
                    if (player.getHeldItemMainhand().getItem() == Items.AIR) {
                        world.setBlockState(pos, state.with(CRUSH, Integer.valueOf(0)), 3);
                        System.out.println("Step 2 success");
                    }
                    else {System.out.println("idk still");
                    }
                }


                if (player.getHeldItemMainhand().getItem() == Items.AIR && player.isCrouching() == true) {
                    if(state.get(OC) == false) {
                        world.setBlockState(pos, state.with(OC, Boolean.valueOf(true)), 3);
                        System.out.println("yay");
                    }
                    else if(state.get(OC) == true) {
                        world.setBlockState(pos, state.with(OC, Boolean.valueOf(false)), 3);
                        System.out.println("yay");
                    }
                    }
                    else {System.out.println("ono");}

        }
        return ActionResultType.SUCCESS;
    }

     */
    @Override
    public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {
    }
    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {

            return ALLTHESHAPES[0];
    }


    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new TTTBatteryTile().getTileEntity();
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
            if (tileEntity instanceof TTTBatteryTile) {
                INamedContainerProvider containerProvider = new INamedContainerProvider() {
                    @Override
                    public ITextComponent getDisplayName() {
                        return new TranslationTextComponent("screen.machinebuiltworld.tttbattery");
                    }

                    @Override
                    public Container createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
                        return new TTTBatteryContainer(i, world, pos, playerInventory, playerEntity);
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
