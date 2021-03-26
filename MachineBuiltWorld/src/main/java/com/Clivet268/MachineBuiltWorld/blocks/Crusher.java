package com.Clivet268.MachineBuiltWorld.blocks;

import com.Clivet268.MachineBuiltWorld.inventory.Containers.CrusherContainer;
import com.Clivet268.MachineBuiltWorld.inventory.Containers.TTTBatteryContainer;
import com.Clivet268.MachineBuiltWorld.state.MoreStateProperties;
import com.Clivet268.MachineBuiltWorld.tileentity.CrusherTile;
import com.Clivet268.MachineBuiltWorld.tileentity.IntensiveHeatingOvenTile;
import com.Clivet268.MachineBuiltWorld.tileentity.TTTBatteryTile;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.particles.BlockParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.stats.Stats;
import net.minecraft.tileentity.FurnaceTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
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
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;
import java.util.Random;
import java.util.stream.Stream;


public class Crusher extends Block{

    public static final IntegerProperty CRUSH = MoreStateProperties.CRUSHING;
    public static final BooleanProperty OC = MoreStateProperties.OPENCLOSE;
    public static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;
    public Crusher() {
        super(Properties.create(Material.IRON)
                .hardnessAndResistance(5.0f, 6.0f)
                .sound(SoundType.METAL)
                .harvestLevel(1)
                .harvestTool(ToolType.PICKAXE));

    }
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(CRUSH,OC,FACING);
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
        return new CrusherTile().getTileEntity();
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.getDefaultState().with(FACING, context.getPlacementHorizontalFacing().getOpposite()).with(CRUSH, 0);
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

    @Override
    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        if (stateIn.get(CRUSH) == 0) {
            double d0 = (double)pos.getX() + 0.5D;
            double d1 = (double)pos.getY();
            double d2 = (double)pos.getZ() + 0.5D;
            if (rand.nextDouble() < 0.1D) {
                worldIn.playSound(d0, d1, d2, SoundEvents.BLOCK_BLASTFURNACE_FIRE_CRACKLE, SoundCategory.BLOCKS, 4.0F, 1.0F, false);
            }

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
    }

}
