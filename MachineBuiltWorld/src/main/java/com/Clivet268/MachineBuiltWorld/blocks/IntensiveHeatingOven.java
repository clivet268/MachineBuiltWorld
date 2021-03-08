package com.Clivet268.MachineBuiltWorld.blocks;

import com.Clivet268.MachineBuiltWorld.inventory.Containers.IntensiveHeatingOvenContainer;
import com.Clivet268.MachineBuiltWorld.tileentity.IntensiveHeatingOvenTile;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;
import java.util.Random;

import static com.Clivet268.MachineBuiltWorld.util.RegistryHandler.MoreStats.INTERACT_WITH_COKE_OVEN;

public class IntensiveHeatingOven extends Block{
    public static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;
    public static final BooleanProperty LIT = BlockStateProperties.LIT;
        public IntensiveHeatingOven() {
            super(Properties.create((Material.IRON))
                    .hardnessAndResistance(4.5f, 15.0f)
                    .sound(SoundType.STONE)
                    .harvestLevel(1)
                    .lightValue(6)
            );
        }
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.getDefaultState().with(FACING, context.getPlacementHorizontalFacing().getOpposite()).with(LIT, false);
    }

    @Override
    public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            TileEntity tileentity = worldIn.getTileEntity(pos);
            if (tileentity instanceof IntensiveHeatingOvenTile) {
                InventoryHelper.dropInventoryItems(worldIn, pos, (IntensiveHeatingOvenTile)tileentity);
                worldIn.updateComparatorOutputLevel(pos, this);
            }

            super.onReplaced(state, worldIn, pos, newState, isMoving);
        }
    }


    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }


    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new IntensiveHeatingOvenTile().getTileEntity();
    }
    @SuppressWarnings("deprecation")
    @Override
    public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult trace) {
        if (!world.isRemote) {
            TileEntity tileEntity = world.getTileEntity(pos);
            if (tileEntity instanceof IntensiveHeatingOvenTile) {
                INamedContainerProvider containerProvider = new INamedContainerProvider() {
                    @Override
                    public ITextComponent getDisplayName() {
                        return new TranslationTextComponent("screen.machinebuiltworld.intensive_heating_oven");
                    }

                    @Override
                    public Container createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
                        return new IntensiveHeatingOvenContainer(i, world, playerInventory, pos);
                    }
                };
                NetworkHooks.openGui((ServerPlayerEntity) player, containerProvider, tileEntity.getPos());
            } else {
                throw new IllegalStateException("Our named container provider is missing!");
            }
        }
        player.addStat(INTERACT_WITH_COKE_OVEN);
        return ActionResultType.SUCCESS;
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(LIT, FACING);
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
    public boolean canEntitySpawn(BlockState state, IBlockReader worldIn, BlockPos pos, EntityType<?> type) { return false; }


    @Override
    @OnlyIn(Dist.CLIENT)
        public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
            if (stateIn.get(LIT)) {
                double d0 = (double)pos.getX() + 0.5D;
                double d1 = (double)pos.getY();
                double d2 = (double)pos.getZ() + 0.5D;
                if (rand.nextDouble() < 0.1D) {
                    worldIn.playSound(d0, d1, d2, SoundEvents.BLOCK_BLASTFURNACE_FIRE_CRACKLE, SoundCategory.BLOCKS, 2.0F, 1.0F, false);
                }

                Direction direction = stateIn.get(FACING);
                Direction.Axis direction$axis = direction.getAxis();
                double d3 = 0.52D;
                double d4 = rand.nextDouble() * 0.6D - 0.3D;
                double d5 = direction$axis == Direction.Axis.X ? (double)direction.getXOffset() * 0.52D : d4;
                double d6 = rand.nextDouble() * 9.0D / 16.0D;
                double d7 = direction$axis == Direction.Axis.Z ? (double)direction.getZOffset() * 0.52D : d4;
                worldIn.addParticle(ParticleTypes.SMOKE, d0 + d5, d1 + d6, d2 + d7, 0.0D, 0.0D, 0.0D);
            }
        }
    }


