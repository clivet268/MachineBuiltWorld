package com.Clivet268.MachineBuiltWorld.blocks;

import com.Clivet268.MachineBuiltWorld.inventory.Containers.CrusherContainer;
import com.Clivet268.MachineBuiltWorld.state.CrusherTeethProperty;
import com.Clivet268.MachineBuiltWorld.state.MoreStateProperties;
import com.Clivet268.MachineBuiltWorld.tileentity.CrusherTile;
import com.Clivet268.MachineBuiltWorld.tileentity.ICrusherTeeth;
import com.Clivet268.MachineBuiltWorld.util.RegistryHandler;
import it.unimi.dsi.fastutil.floats.Float2FloatFunction;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.particles.BlockParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.*;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMerger;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;
import java.util.Random;
import java.util.function.BiPredicate;
import java.util.stream.Stream;

//TODO model tile entity so the model files dont take up more space than the mod itself
public class Crusher extends Block{

    public static final EnumProperty<CrusherTeethProperty> TYPE = MoreStateProperties.TYPE;
    public static final IntegerProperty CRUSH = MoreStateProperties.CRUSHING;
    public static final BooleanProperty OC = MoreStateProperties.CRUSHCONTAIN;
    public static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;
    int i;
    public Crusher() {
        super(Properties.create(Material.IRON)
                .hardnessAndResistance(5.0f, 6.0f)
                .sound(SoundType.METAL)
                .harvestLevel(1)
                .harvestTool(ToolType.PICKAXE));
        i=0;
    }
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(CRUSH,TYPE,FACING);
    }
    protected static final VoxelShape[] ALLTHESHAPES = new VoxelShape[]{
            //north uncrushing
            Stream.of(
                    Block.makeCuboidShape(0, 2, 15, 16, 17, 16),
                    Block.makeCuboidShape(0, 2, 0, 16, 17, 1),
                    Block.makeCuboidShape(0, 2, 1, 1, 17, 15),
                    Block.makeCuboidShape(15, 2, 1, 16, 17, 15),
                    Block.makeCuboidShape(0, 0, 0, 16, 2, 2),
                    Block.makeCuboidShape(0, 0, 14, 16, 2, 16),
                    Block.makeCuboidShape(13, 0, 2, 16, 2, 14),
                    Block.makeCuboidShape(0, 0, 2, 3, 2, 14)
            ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get()
            //empty north south
            /*Stream.of(
                    Block.makeCuboidShape(0, 2, 0, 1, 17, 16),
                    Block.makeCuboidShape(15, 2, 0, 16, 17, 16),
                    Block.makeCuboidShape(1, 2, 0, 15, 17, 1),
                    Block.makeCuboidShape(1, 2, 15, 15, 17, 16),
                    Block.makeCuboidShape(14, 0, 0, 16, 2, 16),
                    Block.makeCuboidShape(0, 0, 0, 2, 2, 16),
                    Block.makeCuboidShape(2, 0, 13, 14, 2, 16),
                    Block.makeCuboidShape(2, 0, 0, 14, 2, 3)
            ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get(),

             */
    };



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
        return new CrusherTile().getTileEntity();
    }

    public TileEntityMerger.ICallbackWrapper<CrusherTile> merger(BlockState p_225536_1_, World p_225536_2_, BlockPos p_225536_3_, boolean p_225536_4_) {
        BiPredicate<IWorld, BlockPos> bipredicate;
        if (p_225536_4_) {
            bipredicate = (p_226918_0_, p_226918_1_) -> {
                return false;
            };
        } else {
            bipredicate = ChestBlock::isBlocked;
        }

        return TileEntityMerger.func_226924_a_(RegistryHandler.CRUSHER_TILE.get(), ChestBlock::func_226919_h_, ChestBlock::getDirectionToAttached, FACING, p_225536_1_, p_225536_2_, p_225536_3_, bipredicate);
    }

    @OnlyIn(Dist.CLIENT)
    public static TileEntityMerger.ICallback<CrusherTile, Float2FloatFunction> rotate(final ICrusherTeeth p_226917_0_) {
        return new TileEntityMerger.ICallback<CrusherTile, Float2FloatFunction>() {
            public Float2FloatFunction func_225539_a_(CrusherTile p_225539_1_, CrusherTile p_225539_2_) {
                return (p_226921_2_) -> {
                    return Math.max(p_225539_1_.getLidAngle(p_226921_2_), p_225539_2_.getLidAngle(p_226921_2_));
                };
            }

            public Float2FloatFunction func_225538_a_(CrusherTile p_225538_1_) {
                return p_225538_1_::getLidAngle;
            }

            public Float2FloatFunction func_225537_b_() {
                return p_226917_0_::getLidAngle;
            }
        };
    }
    
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.getDefaultState().with(FACING, context.getPlacementHorizontalFacing().getOpposite())
                .with(CRUSH, 0).with(TYPE, CrusherTeethProperty.EMPTY);
    }

    @SuppressWarnings("deprecation")
    @Override
    public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult trace) {
        if (!world.isRemote) {
            TileEntity tileEntity = world.getTileEntity(pos);
            if (tileEntity instanceof CrusherTile) {
                INamedContainerProvider containerProvider = new INamedContainerProvider() {
                    @Override
                    public ITextComponent getDisplayName() {
                        return new TranslationTextComponent("screen.machinebuiltworld.crusher");
                    }

                    @Override
                    public Container createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
                        return new CrusherContainer(i, world, playerInventory, pos);
                    }
                };
                NetworkHooks.openGui((ServerPlayerEntity) player, containerProvider, tileEntity.getPos());
            } else {
                throw new IllegalStateException("Our named container provider is missing!");
            }
        }
        return ActionResultType.SUCCESS;
    }

    public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            TileEntity tileentity = worldIn.getTileEntity(pos);
            if (tileentity instanceof CrusherTile) {
                InventoryHelper.dropInventoryItems(worldIn, pos, (CrusherTile)tileentity);
                worldIn.updateComparatorOutputLevel(pos, this);
            }

            super.onReplaced(state, worldIn, pos, newState, isMoving);
        }
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        if (stateIn.get(CRUSH) == 0) {

            Direction direction = stateIn.get(FACING);
            Direction.Axis direction$axis = direction.getAxis();
            double d3 = 0.52D;
            double d4 = rand.nextDouble() * 0.6D - 0.3D;
            double d5 = direction$axis == Direction.Axis.X ? (double)direction.getXOffset() * 0.52D : d4;
            double d6 = rand.nextDouble() * 9.0D / 16.0D;
            double d7 = direction$axis == Direction.Axis.Z ? (double)direction.getZOffset() * 0.52D : d4;
            //worldIn.addParticle(ParticleTypes.BLOCK, d0 + d5, d1 + d6, d2 + d7, 0.0D, 0.0D, 0.0D);
            worldIn.addParticle(new BlockParticleData(ParticleTypes.BLOCK, Blocks.STONE.getDefaultState()),
                    pos.getX()+0.5D, pos.getY() - 0.1D, pos.getZ()+0.5D, (Math.random()*2) - (Math.random()*2), (Math.random()*4) - (Math.random()*4), (Math.random()*2) - (Math.random()*2));
            worldIn.addParticle(new BlockParticleData(ParticleTypes.BLOCK, Blocks.STONE.getDefaultState()),
                    pos.getX()+0.5D, pos.getY() - 0.1D, pos.getZ()+0.5D, (Math.random()*2) - (Math.random()*2), (Math.random()*4) - (Math.random()*4), (Math.random()*2) - (Math.random()*2));

        }
        if(i == 20)
        {
            double d0 = (double)pos.getX() + 0.5D;
            double d1 = (double)pos.getY();
            double d2 = (double)pos.getZ() + 0.5D;
                worldIn.playSound(d0, d1, d2, RegistryHandler.CRUSHER_CRUSHING.get(), SoundCategory.BLOCKS, 8.0F, 1.0F, false);
            i = 0;
        }
        else{
            i++;
        }
    }

}
