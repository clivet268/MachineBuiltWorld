package com.Clivet268.MachineBuiltWorld.blocks;

import com.Clivet268.MachineBuiltWorld.inventory.Containers.BatteryPotContainer;
import com.Clivet268.MachineBuiltWorld.tileentity.BatteryPotTile;
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
import net.minecraft.item.ItemStack;
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
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;
import java.util.List;
import java.util.stream.Stream;

public class BatteryPot extends Block{

    private static final VoxelShape NORTH = Stream.of(
            Block.makeCuboidShape(1, 1, 2, 2, 14, 14),
            Block.makeCuboidShape(3, 0, 3, 13, 1, 13),
            Block.makeCuboidShape(3, 0, 13, 13, 1, 14),
            Block.makeCuboidShape(3, 0, 2, 13, 1, 3),
            Block.makeCuboidShape(13, 0, 3, 14, 1, 13),
            Block.makeCuboidShape(2, 0, 3, 3, 1, 13),
            Block.makeCuboidShape(14, 1, 2, 15, 14, 14),
            Block.makeCuboidShape(2, 1, 1, 14, 14, 2),
            Block.makeCuboidShape(2, 1, 14, 14, 14, 15),
            Block.makeCuboidShape(1, 15, 1, 15, 16, 15),
            Block.makeCuboidShape(7, 16, 7, 9, 18, 9),
            Block.makeCuboidShape(1, 14, 11, 2, 15, 12),
            Block.makeCuboidShape(0, 12, 11, 1, 14, 12),
            Block.makeCuboidShape(0, 11, 10, 1, 12, 11),
            Block.makeCuboidShape(0, 9, 9, 1, 11, 10),
            Block.makeCuboidShape(0, 7, 10, 1, 9, 11),
            Block.makeCuboidShape(15, 8, 4, 16, 9, 5),
            Block.makeCuboidShape(15, 9, 5, 16, 11, 6),
            Block.makeCuboidShape(15, 12, 6, 16, 14, 7),
            Block.makeCuboidShape(14, 14, 6, 15, 15, 7),
            Block.makeCuboidShape(15, 11, 5, 16, 12, 6),
            Block.makeCuboidShape(15, 8, 3, 16, 9, 4),
            Block.makeCuboidShape(14, 14, 5, 15, 15, 6),
            Block.makeCuboidShape(2, 1, 2, 14, 2, 14),
            Block.makeCuboidShape(2, 1, 2, 14, 2, 14),
            Block.makeCuboidShape(2, 2, 2, 14, 12, 14),
            Block.makeCuboidShape(13, 12, 5, 14, 15, 6),
            Block.makeCuboidShape(2, 12, 11, 3, 15, 12),
            Block.makeCuboidShape(0, 6, 11, 1, 7, 12),
            Block.makeCuboidShape(15, 7, 2, 16, 8, 3)
    ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get();
    public BatteryPot(){
        super(Properties.create(Material.IRON)
                .hardnessAndResistance(1.0f, 1.0f)
                .sound(SoundType.SLIME)
                .harvestLevel(0));
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return NORTH;
    }
    /*
    @Override
    public int getLightValue(BlockState state) {
        return state.get(BlockStateProperties.POWERED) ? super.getLightValue(state) : 0;
    }

     */
    @Override
    public void addInformation(ItemStack stack, @Nullable IBlockReader reader, List<ITextComponent> list, ITooltipFlag flags) {
        list.add(new TranslationTextComponent("Cheeseburger"));
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }


    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new BatteryPotTile().getTileEntity();
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
            if (tileEntity instanceof BatteryPotTile) {
                INamedContainerProvider containerProvider = new INamedContainerProvider() {
                    @Override
                    public ITextComponent getDisplayName() {
                        return new TranslationTextComponent("screen.machinebuiltworld.battery_pot");
                    }

                    @Override
                    public Container createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
                        return new BatteryPotContainer(i, world, pos, playerInventory, playerEntity);
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
       // builder.add(BlockStateProperties.POWERED);
    }
}
