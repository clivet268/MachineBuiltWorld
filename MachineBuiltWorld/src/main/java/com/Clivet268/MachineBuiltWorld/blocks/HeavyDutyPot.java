package com.Clivet268.MachineBuiltWorld.blocks;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.*;
import net.minecraft.potion.PotionUtils;
import net.minecraft.potion.Potions;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.stats.Stats;
import net.minecraft.tileentity.BannerTileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

import java.util.stream.Stream;

public class HeavyDutyPot extends Block{
    public static final IntegerProperty LEVEL = BlockStateProperties.LEVEL_0_3;

    private static final VoxelShape SHAPE = Stream.of(
            Block.makeCuboidShape(3, 0, 3, 13, 1, 13),
            Block.makeCuboidShape(3, 0, 13, 13, 1, 14),
            Block.makeCuboidShape(3, 0, 2, 13, 1, 3),
            Block.makeCuboidShape(13, 0, 3, 14, 1, 13),
            Block.makeCuboidShape(2, 0, 3, 3, 1, 13),
            Block.makeCuboidShape(14, 1, 2, 15, 14, 14),
            Block.makeCuboidShape(2, 1, 1, 14, 14, 2),
            Block.makeCuboidShape(1, 1, 2, 2, 14, 14),
            Block.makeCuboidShape(2, 1, 14, 14, 14, 15),
            Block.makeCuboidShape(2, 1, 2, 3, 2, 3),
            Block.makeCuboidShape(13, 1, 2, 14, 2, 3),
            Block.makeCuboidShape(13, 1, 13, 14, 2, 14),
            Block.makeCuboidShape(2, 1, 13, 3, 2, 14)
    ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get();

    public HeavyDutyPot() {
        super(Block.Properties.create(Material.CLAY)
                .hardnessAndResistance(5.0f, 6.0f)
                .sound(SoundType.STEM)
                .harvestLevel(1)
                .harvestTool(ToolType.PICKAXE));
        this.setDefaultState(this.stateContainer.getBaseState().with(LEVEL, Integer.valueOf(0)));
    }
    public void setLiquidLevel(World worldIn, BlockPos pos, BlockState state, int level) {
        worldIn.setBlockState(pos, state.with(LEVEL, Integer.valueOf(MathHelper.clamp(level, 0, 3))), 2);
        worldIn.updateComparatorOutputLevel(pos, this);
    }

    public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
        int i = state.get(LEVEL);
        float f = (float)pos.getY() + (6.0F + (float)(3 * i)) / 16.0F;
        if (!worldIn.isRemote && entityIn.isBurning() && i > 0 && entityIn.getPosY() <= (double)f) {
            entityIn.extinguish();
            this.setLiquidLevel(worldIn, pos, state, i - 1);
        }

    }

    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        ItemStack itemstack = player.getHeldItem(handIn);
        if (itemstack.isEmpty()) {
            return ActionResultType.PASS;
        } else {
            int i = state.get(LEVEL);
            Item item = itemstack.getItem();
            if (item == Items.WATER_BUCKET) {
                if (i < 3 && !worldIn.isRemote) {
                    if (!player.abilities.isCreativeMode) {
                        player.setHeldItem(handIn, new ItemStack(Items.BUCKET));
                    }

                    //player.addStat(Stats.FILL_CAULDRON);
                    this.setLiquidLevel(worldIn, pos, state, 3);
                    worldIn.playSound((PlayerEntity)null, pos, SoundEvents.ITEM_BUCKET_EMPTY, SoundCategory.BLOCKS, 1.0F, 1.0F);
                }

                return ActionResultType.SUCCESS;
            } else if (item == Items.BUCKET) {
                if (i == 3 && !worldIn.isRemote) {
                    if (!player.abilities.isCreativeMode) {
                        itemstack.shrink(1);
                        if (itemstack.isEmpty()) {
                            player.setHeldItem(handIn, new ItemStack(Items.WATER_BUCKET));
                        } else if (!player.inventory.addItemStackToInventory(new ItemStack(Items.WATER_BUCKET))) {
                            player.dropItem(new ItemStack(Items.WATER_BUCKET), false);
                        }
                    }

                    //player.addStat(Stats.USE_CAULDRON);
                    this.setLiquidLevel(worldIn, pos, state, 0);
                    worldIn.playSound((PlayerEntity)null, pos, SoundEvents.ITEM_BUCKET_FILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
                }

                return ActionResultType.SUCCESS;
            } else if (item == Items.GLASS_BOTTLE) {
                if (i > 0 && !worldIn.isRemote) {
                    if (!player.abilities.isCreativeMode) {
                        ItemStack itemstack4 = PotionUtils.addPotionToItemStack(new ItemStack(Items.POTION), Potions.WATER);
                        player.addStat(Stats.USE_CAULDRON);
                        itemstack.shrink(1);
                        if (itemstack.isEmpty()) {
                            player.setHeldItem(handIn, itemstack4);
                        } else if (!player.inventory.addItemStackToInventory(itemstack4)) {
                            player.dropItem(itemstack4, false);
                        } else if (player instanceof ServerPlayerEntity) {
                            ((ServerPlayerEntity)player).sendContainerToPlayer(player.container);
                        }
                    }

                    worldIn.playSound((PlayerEntity)null, pos, SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
                    this.setLiquidLevel(worldIn, pos, state, i - 1);
                }

                return ActionResultType.SUCCESS;
            } else if (item == Items.POTION && PotionUtils.getPotionFromItem(itemstack) == Potions.WATER) {
                if (i < 3 && !worldIn.isRemote) {
                    if (!player.abilities.isCreativeMode) {
                        ItemStack itemstack3 = new ItemStack(Items.GLASS_BOTTLE);
                        player.addStat(Stats.USE_CAULDRON);
                        player.setHeldItem(handIn, itemstack3);
                        if (player instanceof ServerPlayerEntity) {
                            ((ServerPlayerEntity)player).sendContainerToPlayer(player.container);
                        }
                    }

                    worldIn.playSound((PlayerEntity)null, pos, SoundEvents.ITEM_BOTTLE_EMPTY, SoundCategory.BLOCKS, 1.0F, 1.0F);
                    this.setLiquidLevel(worldIn, pos, state, i + 1);
                }

                return ActionResultType.SUCCESS;
            } else {
                if (i > 0 && item instanceof IDyeableArmorItem) {
                    IDyeableArmorItem idyeablearmoritem = (IDyeableArmorItem)item;
                    if (idyeablearmoritem.hasColor(itemstack) && !worldIn.isRemote) {
                        idyeablearmoritem.removeColor(itemstack);
                        this.setLiquidLevel(worldIn, pos, state, i - 1);
                        player.addStat(Stats.CLEAN_ARMOR);
                        return ActionResultType.SUCCESS;
                    }
                }

                if (i > 0 && item instanceof BannerItem) {
                    if (BannerTileEntity.getPatterns(itemstack) > 0 && !worldIn.isRemote) {
                        ItemStack itemstack2 = itemstack.copy();
                        itemstack2.setCount(1);
                        BannerTileEntity.removeBannerData(itemstack2);
                        player.addStat(Stats.CLEAN_BANNER);
                        if (!player.abilities.isCreativeMode) {
                            itemstack.shrink(1);
                            this.setLiquidLevel(worldIn, pos, state, i - 1);
                        }

                        if (itemstack.isEmpty()) {
                            player.setHeldItem(handIn, itemstack2);
                        } else if (!player.inventory.addItemStackToInventory(itemstack2)) {
                            player.dropItem(itemstack2, false);
                        } else if (player instanceof ServerPlayerEntity) {
                            ((ServerPlayerEntity)player).sendContainerToPlayer(player.container);
                        }
                    }

                    return ActionResultType.SUCCESS;
                } else if (i > 0 && item instanceof BlockItem) {
                    Block block = ((BlockItem)item).getBlock();
                    if (block instanceof ShulkerBoxBlock && !worldIn.isRemote()) {
                        ItemStack itemstack1 = new ItemStack(Blocks.SHULKER_BOX, 1);
                        if (itemstack.hasTag()) {
                            itemstack1.setTag(itemstack.getTag().copy());
                        }

                        player.setHeldItem(handIn, itemstack1);
                        this.setLiquidLevel(worldIn, pos, state, i - 1);
                        player.addStat(Stats.CLEAN_SHULKER_BOX);
                        return ActionResultType.SUCCESS;
                    } else {
                        return ActionResultType.CONSUME;
                    }
                } else {
                    return ActionResultType.PASS;
                }
            }
        }
    }
    @Override
    public boolean hasTileEntity(BlockState states)
    {
        return true;
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPE;
    }
    public int getComparatorInputOverride(BlockState blockState, World worldIn, BlockPos pos) {
        return blockState.get(LEVEL);
    }

    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(LEVEL);
    }
}
