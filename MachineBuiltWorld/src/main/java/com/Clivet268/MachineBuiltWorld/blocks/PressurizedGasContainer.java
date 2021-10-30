package com.Clivet268.MachineBuiltWorld.blocks;

import com.Clivet268.MachineBuiltWorld.inventory.Containers.GeneratorContainer;
import com.Clivet268.MachineBuiltWorld.tileentity.GeneratorTile;
import com.Clivet268.MachineBuiltWorld.tileentity.PressurizedGasContainerTile;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
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
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;
import java.util.List;
import java.util.stream.Stream;

import static net.minecraft.util.Direction.*;

public class PressurizedGasContainer extends Block{
    public static final DirectionProperty FFACING = BlockStateProperties.FACING;
    public static final VoxelShape[] SHAPES = {
            // west
            Stream.of(
                    Block.makeCuboidShape(11.5, 5.25, 5.75, 12.5, 11.75, 12.25),
                    Block.makeCuboidShape(-0.5, 7, 7.5, 0.5, 10, 10.5),
                    Block.makeCuboidShape(0.5, 5, 5.5, 3.5, 12, 12.5),
                    Block.makeCuboidShape(4.5, 5, 5.5, 7.5, 12, 12.5),
                    Block.makeCuboidShape(8.5, 5, 5.5, 11.5, 12, 12.5),
                    Block.makeCuboidShape(3.5, 5.25, 5.75, 4.5, 11.75, 12.25),
                    Block.makeCuboidShape(12.5, 5, 5.5, 15.5, 12, 12.5),
                    Block.makeCuboidShape(7.5, 5.25, 5.75, 8.5, 11.75, 12.25)
            ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get(),
            //east
            Stream.of(
                    Block.makeCuboidShape(2.5, 5.25, 5.75, 3.5, 11.75, 12.25),
                    Block.makeCuboidShape(14.5, 7, 7.5, 15.5, 10, 10.5),
                    Block.makeCuboidShape(11.5, 5, 5.5, 14.5, 12, 12.5),
                    Block.makeCuboidShape(7.5, 5, 5.5, 10.5, 12, 12.5),
                    Block.makeCuboidShape(3.5, 5, 5.5, 6.5, 12, 12.5),
                    Block.makeCuboidShape(10.5, 5.25, 5.75, 11.5, 11.75, 12.25),
                    Block.makeCuboidShape(0, 5, 5.5, 3, 12, 12.5),
                    Block.makeCuboidShape(6.5, 5.25, 5.75, 7.5, 11.75, 12.25)
            ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get(),
            //south
            Stream.of(
                    Block.makeCuboidShape(4.75, 5.25, 3.5, 11.25, 11.75, 4.5),
                    Block.makeCuboidShape(6.5, 7, 15.5, 9.5, 10, 16.5),
                    Block.makeCuboidShape(4.5, 5, 12.5, 11.5, 12, 15.5),
                    Block.makeCuboidShape(4.5, 5, 8.5, 11.5, 12, 11.5),
                    Block.makeCuboidShape(4.5, 5, 4.5, 11.5, 12, 7.5),
                    Block.makeCuboidShape(4.75, 5.25, 11.5, 11.25, 11.75, 12.5),
                    Block.makeCuboidShape(4.5, 5, 0.5, 11.5, 12, 3.5),
                    Block.makeCuboidShape(4.75, 5.25, 7.5, 11.25, 11.75, 8.5)
            ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get(),
            //north
            Stream.of(
                    Block.makeCuboidShape(4.75, 5.25, 11.5, 11.25, 11.75, 12.5),
                    Block.makeCuboidShape(6.5, 7, -0.5, 9.5, 10, 0.5),
                    Block.makeCuboidShape(4.5, 5, 0.5, 11.5, 12, 3.5),
                    Block.makeCuboidShape(4.5, 5, 4.5, 11.5, 12, 7.5),
                    Block.makeCuboidShape(4.5, 5, 8.5, 11.5, 12, 11.5),
                    Block.makeCuboidShape(4.75, 5.25, 3.5, 11.25, 11.75, 4.5),
                    Block.makeCuboidShape(4.5, 5, 12.5, 11.5, 12, 15.5),
                    Block.makeCuboidShape(4.75, 5.25, 7.5, 11.25, 11.75, 8.5)
            ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get(),
            //up
            Stream.of(
                    Block.makeCuboidShape(4.75, 3, 4.75, 11.25, 4, 11.25),
                    Block.makeCuboidShape(6.5, 15, 6.5, 9.5, 16, 9.5),
                    Block.makeCuboidShape(4.5, 12, 4.5, 11.5, 15, 11.5),
                    Block.makeCuboidShape(4.5, 8, 4.5, 11.5, 11, 11.5),
                    Block.makeCuboidShape(4.5, 4, 4.5, 11.5, 7, 11.5),
                    Block.makeCuboidShape(4.75, 11, 4.75, 11.25, 12, 11.25),
                    Block.makeCuboidShape(4.5, 0, 4.5, 11.5, 3, 11.5),
                    Block.makeCuboidShape(4.75, 7, 4.75, 11.25, 8, 11.25)
            ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get(),
            //down
            Stream.of(
                    Block.makeCuboidShape(4.75, 12, 4.75, 11.25, 13, 11.25),
                    Block.makeCuboidShape(6.5, 0, 6.5, 9.5, 1, 9.5),
                    Block.makeCuboidShape(4.5, 1, 4.5, 11.5, 4, 11.5),
                    Block.makeCuboidShape(4.5, 5, 4.5, 11.5, 8, 11.5),
                    Block.makeCuboidShape(4.5, 9, 4.5, 11.5, 12, 11.5),
                    Block.makeCuboidShape(4.75, 4, 4.75, 11.25, 5, 11.25),
                    Block.makeCuboidShape(4.5, 13, 4.5, 11.5, 16, 11.5),
                    Block.makeCuboidShape(4.75, 8, 4.75, 11.25, 9, 11.25)
            ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get()

    };

   public PressurizedGasContainer(){
        super(Properties.create(Material.IRON)
                .hardnessAndResistance(1.0f, 1.0f)
                .sound(SoundType.BAMBOO)
                .harvestLevel(0));
    }


    @Override
    public void addInformation(ItemStack stack, @Nullable IBlockReader reader, List<ITextComponent> list, ITooltipFlag flags) {
        list.add(new TranslationTextComponent("message.generator"));
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }


    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new GeneratorTile().getTileEntity();
    }


    @SuppressWarnings("deprecation")
    @Override
    public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult trace) {
        if (!world.isRemote) {
            TileEntity tileEntity = world.getTileEntity(pos);
            if (tileEntity instanceof PressurizedGasContainerTile) {
                INamedContainerProvider containerProvider = new INamedContainerProvider() {
                    @Override
                    public ITextComponent getDisplayName() {
                        return new TranslationTextComponent("screen.machinebuiltworld.pressurized_gas_container");
                    }

                    @Override
                    public Container createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
                        return new GeneratorContainer(i, world, pos, playerInventory);
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
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FFACING);
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.getDefaultState().with(FFACING, context.getPlacementHorizontalFacing().getOpposite());
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        if (state.get(FFACING).equals(NORTH)){
            return SHAPES[3];
        }
        else if(state.get(FFACING).equals(SOUTH))
        {
            return SHAPES[2];
        }
        else if(state.get(FFACING).equals(WEST))
        {
            return SHAPES[0];
        }
        else if(state.get(FFACING).equals(EAST))
        {
            return SHAPES[1];
        }
        else if(state.get(FFACING).equals(UP))
        {
            return SHAPES[4];
        }
        else if(state.get(FFACING).equals(DOWN))
        {
            return SHAPES[5];
        }
        else {
            return SHAPES[0];
        }
    }
}
