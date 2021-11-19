package com.Clivet268.MachineBuiltWorld.blocks;

//import com.Clivet268.MachineBuiltWorld.tileentity.TileEntityAtomizer;
//import com.Clivet268.MachineBuiltWorld.init.ModTileEntityTypes;

import com.Clivet268.MachineBuiltWorld.inventory.Containers.AtomizerContainer;
import com.Clivet268.MachineBuiltWorld.tileentity.AtomizerTile;
import com.Clivet268.MachineBuiltWorld.tileentity.CrusherTile;
import com.Clivet268.MachineBuiltWorld.tileentity.ResonatingFrameTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.EndPortalTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
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
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.stream.Stream;

//TODO make texture
//TODO make states
public class ResonatingFrame extends Block {

    public static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;


    public ResonatingFrame() {

        super(Block.Properties.create(Material.PORTAL, MaterialColor.BLACK)
                .doesNotBlockMovement()
                .lightValue(15)
                .hardnessAndResistance(150.0F, 28.0F)
                .noDrops());

    }

    protected static final VoxelShape NORTH_SOUTH_MT =
            Stream.of(
                    Block.makeCuboidShape(-8.5, 44, 7, 24.5, 46, 9.5),
                    Block.makeCuboidShape(-8.5, 2, 7, -6.5, 44, 9.5),
                    Block.makeCuboidShape(22.5, 2, 7, 24.5, 44, 9.5),
                    Block.makeCuboidShape(-8.5, 0, 7, 24.5, 2, 9.5)
            ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR)).get();
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return NORTH_SOUTH_MT;
    }

    public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
        if (!worldIn.isRemote && !entityIn.isPassenger() && !entityIn.isBeingRidden() && entityIn.isNonBoss() && VoxelShapes.compare(VoxelShapes.create(entityIn.getBoundingBox().offset((double)(-pos.getX()), (double)(-pos.getY()), (double)(-pos.getZ()))), state.getShape(worldIn, pos), IBooleanFunction.AND)) {
            entityIn.changeDimension(worldIn.dimension.getType() == DimensionType.THE_END ? DimensionType.OVERWORLD : DimensionType.THE_END);
        }

    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

   @Nonnull
    @Override
   public TileEntity createTileEntity(BlockState state, IBlockReader world) {
     return new ResonatingFrameTileEntity().getTileEntity();
  }


}

