package com.Clivet268.MachineBuiltWorld.blocks;

import com.Clivet268.MachineBuiltWorld.inventory.Containers.MixerContainer;
import com.Clivet268.MachineBuiltWorld.state.MoreStateProperties;
import com.Clivet268.MachineBuiltWorld.tileentity.MixerTile;
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
import net.minecraft.item.Items;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
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
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

public class Mixer extends Block {
    public int TTC = 0;
    public static final IntegerProperty PROPERTY_MIXER_WORK = MoreStateProperties.MIXEROAS;
    private static final VoxelShape RESTN = Stream.of(
            Block.makeCuboidShape(0.5, 0, 0.5, 15.5, 2, 2.5),
            Block.makeCuboidShape(0.5, 0, 13.5, 15.5, 2, 15.5),
            Block.makeCuboidShape(1.5, 3.5, 2.5, 3.5, 14.5, 13.5),
            Block.makeCuboidShape(2.5, 13.5, 2.5, 12.5, 14.5, 13.5),
            Block.makeCuboidShape(2.5, 3.5, 2.5, 12.5, 4.5, 13.5),
            Block.makeCuboidShape(2.5, 4.5, 2.5, 12.5, 13.5, 3.5),
            Block.makeCuboidShape(2.5, 4.5, 12.5, 12.5, 13.5, 13.5),
            Block.makeCuboidShape(3.5, 8.25, 7.5, 7.5, 9.75, 9),
            Block.makeCuboidShape(7.5, 5.75, 7.5, 7.5, 8.25, 9),
            Block.makeCuboidShape(7.5, 5.75, 9, 7.5, 7, 10.5),
            Block.makeCuboidShape(7.5, 11, 6, 7.5, 12.25, 7.5),
            Block.makeCuboidShape(7.5, 9.75, 7.5, 7.5, 12.25, 9),
            Block.makeCuboidShape(5.5, 2, 0.5, 9.5, 11, 2.5),
            Block.makeCuboidShape(6.5, 8, -0.5, 8.5, 10, 0.5),
            Block.makeCuboidShape(5.5, 2, 13.5, 9.5, 11, 15.5),
            Block.makeCuboidShape(6.5, 8, 15.5, 8.5, 10, 16.5)
    ).reduce((v1, v2) -> {
        return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);
    }).get();


    public Mixer() {
        super(Block.Properties.create(Material.IRON)
                .hardnessAndResistance(1.0f, 1.0f)
                .sound(SoundType.ANVIL)
                .harvestLevel(0));
        this.setDefaultState(this.stateContainer.getBaseState().with(PROPERTY_MIXER_WORK, Integer.valueOf(0))
        );

    }

    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(PROPERTY_MIXER_WORK);
    }

    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return RESTN;
    }

    @Override
    public void tick(BlockState state, ServerWorld world, BlockPos pos, Random rand) {
        if (state.get(PROPERTY_MIXER_WORK) > 0) {
            if(state.get(PROPERTY_MIXER_WORK) == 1) {
                world.setBlockState(pos, state.with(PROPERTY_MIXER_WORK, Integer.valueOf(2)), 3);
            }
            else if(state.get(PROPERTY_MIXER_WORK) == 2) {
                world.setBlockState(pos, state.with(PROPERTY_MIXER_WORK, Integer.valueOf(1)), 3);
            }
            if(TTC > 0) {
                TTC--;
                world.playSound(pos.getX(),pos.getY(),pos.getZ(), SoundEvents.BLOCK_COMPARATOR_CLICK, SoundCategory.BLOCKS,10000, 1 ,true);
            }
            else if(TTC == 0){
                world.playSound(pos.getX(),pos.getY(),pos.getZ(), SoundEvents.ENTITY_ENDERMAN_DEATH, SoundCategory.BLOCKS,10000, 1 ,true);
                world.setBlockState(pos, state.with(PROPERTY_MIXER_WORK, Integer.valueOf(0)), 3);
                TTC = -1;
            }
        }


    }

    /*@Override
    public int getLightValue(BlockState state) {
        return state.get(BlockStateProperties.POWERED) ? super.getLightValue(state) : 0;
    }

     */
    @Override
    public void addInformation(ItemStack stack, @Nullable IBlockReader reader, List<ITextComponent> list, ITooltipFlag flags) {
        list.add(new TranslationTextComponent("message.battery", Integer.toString(9)));
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }


    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new MixerTile().getTileEntity();
    }

    @SuppressWarnings("deprecation")
    @Override
    public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult trace) {
        if (!world.isRemote) {
            TileEntity tileEntity = world.getTileEntity(pos);
            if (tileEntity instanceof MixerTile) {
                INamedContainerProvider containerProvider = new INamedContainerProvider() {
                    @Override
                    public ITextComponent getDisplayName() {
                        return new TranslationTextComponent("screen.machinebuiltworld.mixer");
                    }

                    @Override
                    public Container createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
                        return new MixerContainer(i, world, pos, playerInventory, playerEntity);
                    }
                };
                NetworkHooks.openGui((ServerPlayerEntity) player, containerProvider, tileEntity.getPos());
            } else {
                throw new IllegalStateException("Our named container provider is missing!");
            }
        }
        TileEntity tileEntity = world.getTileEntity(pos);
        if (tileEntity instanceof MixerTile) {
            if (state.get(PROPERTY_MIXER_WORK) == 0 && player.getHeldItemMainhand().getItem() == Items.AIR && player.isCrouching() == true) {
                world.setBlockState(pos, state.with(PROPERTY_MIXER_WORK, Integer.valueOf(1)), 3);
                TTC=200;
                System.out.println("Mix On");
            } else {
                System.out.println(":(");
            }
        } else if (state.get(PROPERTY_MIXER_WORK) == 1 || state.get(PROPERTY_MIXER_WORK) == 2) {
            if (player.getHeldItemMainhand().getItem() == Items.AIR && player.isCrouching() == true) {
                world.setBlockState(pos, state.with(PROPERTY_MIXER_WORK, Integer.valueOf(0)), 3);
                TTC = -1;
                System.out.println("Mix Off");
            } else {
                System.out.println(":(");
            }
        }
        return ActionResultType.SUCCESS;
    }
}





