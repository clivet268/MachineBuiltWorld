package com.Clivet268.MachineBuiltWorld.blocks;

import com.Clivet268.MachineBuiltWorld.inventory.Containers.SprocketeererContainer;
import com.Clivet268.MachineBuiltWorld.state.MoreStateProperties;
import com.Clivet268.MachineBuiltWorld.tileentity.SprocketeererTile;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MathHelper;
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

public class Sprocketeerer extends Block{
    public static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;
    public static final BooleanProperty LIT = BlockStateProperties.LIT;
    public static final BooleanProperty POSTSPAWN = MoreStateProperties.ONOROFF;
    public PlayerEntity playera;
        public Sprocketeerer() {
            super(Properties.create((Material.IRON))
                    .hardnessAndResistance(4.5f, 15.0f)
                    .sound(SoundType.STONE)
                    .harvestLevel(1)
            );

        }
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
            this.playera = context.getPlayer();
        return this.getDefaultState().with(FACING, context.getPlacementHorizontalFacing().getOpposite()).with(LIT, false).with(POSTSPAWN, false);
    }

    @Override
    public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            TileEntity tileentity = worldIn.getTileEntity(pos);
            if (tileentity instanceof SprocketeererTile) {
                InventoryHelper.dropInventoryItems(worldIn, pos, (SprocketeererTile)tileentity);
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
            return new SprocketeererTile(playera);
    }
    @SuppressWarnings("deprecation")
    @Override
    public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult trace) {
        if (!world.isRemote) {
            TileEntity tileEntity = world.getTileEntity(pos);
            if (tileEntity instanceof SprocketeererTile) {
                ((SprocketeererTile)tileEntity).setOwner(player);
                INamedContainerProvider containerProvider = new INamedContainerProvider() {
                    @Override
                    public ITextComponent getDisplayName() {
                        return new TranslationTextComponent("screen.machinebuiltworld.sprocketeerer");
                    }

                    @Override
                    public SprocketeererContainer createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
                        return new SprocketeererContainer(i, world, playerInventory, pos);
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
        builder.add(LIT, FACING, POSTSPAWN);
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
                double d0 = (double)pos.getX();
                double d1 = (double)pos.getY();
                double d2 = (double)pos.getZ();
                if (rand.nextDouble() < 0.1D) {
                    worldIn.playSound(d0, d1, d2, SoundEvents.BLOCK_BLASTFURNACE_FIRE_CRACKLE, SoundCategory.BLOCKS, 2.0F, 1.0F, false);
                }

                if (stateIn.get(POSTSPAWN)) {
                    for (int iw = 0; iw <= 10; iw += 1) {
                        for (int i = 0; i <= 360; i += 15) {
                            double velox = MathHelper.sin(i);
                            double veloz = MathHelper.cos(i);
                            worldIn.addParticle(ParticleTypes.SMOKE, d0 - 0.5D + (velox * 0.2D), d1 + 1.01D, d2 - 0.5D + (veloz * 0.2D), 0.0D, 0.0D, 0.0D);
                        }
                    }
                    worldIn.setBlockState(pos, worldIn.getBlockState(pos).with(Sprocketeerer.POSTSPAWN, false), 3);

                }
            }
        }
    }


