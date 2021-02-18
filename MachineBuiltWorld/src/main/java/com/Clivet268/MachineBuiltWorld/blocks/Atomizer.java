package com.Clivet268.MachineBuiltWorld.blocks;

//import com.Clivet268.MachineBuiltWorld.tileentity.TileEntityAtomizer;
//import com.Clivet268.MachineBuiltWorld.init.ModTileEntityTypes;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

import javax.annotation.Nullable;
import java.util.List;
import java.util.stream.Stream;

public class Atomizer extends Block {

   // private EnumType type;
    //public boolean ONOROFF = false;
    private BlockState defaultState;
    public static final IntegerProperty POWER = BlockStateProperties.POWER_0_15;
    public static final BooleanProperty INVERTED = BlockStateProperties.INVERTED;


    public Atomizer() {

        super(Block.Properties.create(Material.IRON)
                .hardnessAndResistance(5.0f, 6.0f)
                .sound(SoundType.METAL)
                .harvestLevel(1)
                .harvestTool(ToolType.PICKAXE)
        );

    }

    private static final VoxelShape NORTH = Stream.of(
            Block.makeCuboidShape(0, 0, 0, 16, 13, 16),
            Block.makeCuboidShape(1, 14, 1, 15, 15, 15),
            Block.makeCuboidShape(1, 13, 0, 15, 15, 1),
            Block.makeCuboidShape(1, 13, 15, 15, 15, 16),
            Block.makeCuboidShape(15, 13, 0, 16, 15, 16),
            Block.makeCuboidShape(0, 13, 0, 1, 15, 16)
    ).reduce((v1, v2) -> {
        return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);
    }).get();


    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return NORTH;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable IBlockReader reader, List<ITextComponent> list, ITooltipFlag flags) {
        list.add(new TranslationTextComponent("Pretty hot for it's size"));
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    public static void updatePower(BlockState p_196319_0_, World p_196319_1_, BlockPos p_196319_2_) {
        if (p_196319_1_.dimension.hasSkyLight()) {
            int i = p_196319_1_.getLightFor(LightType.SKY, p_196319_2_) - p_196319_1_.getSkylightSubtracted();
            float f = p_196319_1_.getCelestialAngleRadians(1.0F);
            boolean flag = p_196319_0_.get(INVERTED);
            if (flag) {
                i = 15 - i;
            } else if (i > 0) {
                float f1 = f < (float)Math.PI ? 0.0F : ((float)Math.PI * 2F);
                f = f + (f1 - f) * 0.2F;
                i = Math.round((float)i * MathHelper.cos(f));
            }

            i = MathHelper.clamp(i, 0, 15);
            if (p_196319_0_.get(POWER) != i) {
                p_196319_1_.setBlockState(p_196319_2_, p_196319_0_.with(POWER, Integer.valueOf(i)), 3);
            }

        }
    }

  //  @Nonnull
    //@Override
    //public TileEntity createNewTileEntity(@Nonnull IBlockReader world) {
      //  return new AtomizerTile();
  //  }

   /* public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (player.isAllowEdit()) {
            if (worldIn.isRemote) {
                return ActionResultType.SUCCESS;
            } else {
                BlockState blockstate = state.cycle(INVERTED);
                worldIn.setBlockState(pos, blockstate, 4);
                updatePower(blockstate, worldIn, pos);
                return ActionResultType.SUCCESS;
            }
        } else {
            return super.onBlockActivated(state, worldIn, pos, player, handIn, hit);
        }
    }

    */


}

