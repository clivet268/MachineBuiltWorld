package com.Clivet268.MachineBuiltWorld.blocks;

import com.Clivet268.MachineBuiltWorld.inventory.Containers.CrusherContainer;
import com.Clivet268.MachineBuiltWorld.inventory.Containers.TTTBatteryContainer;
import com.Clivet268.MachineBuiltWorld.state.MoreStateProperties;
import com.Clivet268.MachineBuiltWorld.tileentity.CrusherTile;
import com.Clivet268.MachineBuiltWorld.tileentity.IntensiveHeatingOvenTile;
import com.Clivet268.MachineBuiltWorld.tileentity.TTTBatteryTile;
import com.Clivet268.MachineBuiltWorld.util.RegistryHandler;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.dispenser.*;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.BlockParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.stats.Stats;
import net.minecraft.tileentity.DispenserTileEntity;
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

    public static final IntegerProperty TOOTH = MoreStateProperties.TOOTHSTATE;
    public static final IntegerProperty CRUSH = MoreStateProperties.CRUSHING;
    public static final BooleanProperty OC = MoreStateProperties.OPENCLOSE;
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
        builder.add(CRUSH,OC,FACING,TOOTH);
    }
    protected static final VoxelShape[] ALLTHESHAPES = new VoxelShape[]{
            //north uncrushing
            Stream.of(
                    Block.makeCuboidShape(0, 2, 0, 1, 17, 16),
                    Block.makeCuboidShape(15, 2, 0, 16, 17, 16),
                    Block.makeCuboidShape(1, 2, 0, 15, 17, 1),
                    Block.makeCuboidShape(1, 2, 15, 15, 17, 16),
                    Block.makeCuboidShape(14, 0, 0, 16, 2, 16),
                    Block.makeCuboidShape(0, 0, 0, 2, 2, 16),
                    Block.makeCuboidShape(2, 0, 13, 14, 2, 16),
                    Block.makeCuboidShape(2, 0, 0, 14, 2, 3),
                    Block.makeCuboidShape(2.5, 8.5, 1, 6.5, 12.5, 15),
                    Block.makeCuboidShape(9.5, 8.5, 1, 13.5, 12.5, 15),
                    Block.makeCuboidShape(4.5, 12.5, 12.75, 6.25, 13.5, 14.5),
                    Block.makeCuboidShape(11.5, 12.5, 12.75, 13.25, 13.5, 14.5),
                    Block.makeCuboidShape(8, 7, 12.75, 9.75, 8, 14.5),
                    Block.makeCuboidShape(2.75, 7.5, 12.75, 4.5, 8.5, 14.5),
                    Block.makeCuboidShape(9.75, 7.5, 12.75, 11.5, 8.5, 14.5),
                    Block.makeCuboidShape(6.25, 2, 12.75, 8, 3, 14.5),
                    Block.makeCuboidShape(6.5, 9, 12.75, 7.5, 10.75, 14.5),
                    Block.makeCuboidShape(13.5, 9, 12.75, 14.5, 10.75, 14.5),
                    Block.makeCuboidShape(10, 3.5, 12.75, 11, 5.25, 14.5),
                    Block.makeCuboidShape(4, 12.5, 9.25, 5.75, 13.5, 11),
                    Block.makeCuboidShape(11, 12.5, 9.25, 12.75, 13.5, 11),
                    Block.makeCuboidShape(7.5, 7, 9.25, 9.25, 8, 11),
                    Block.makeCuboidShape(3.25, 7.5, 9.25, 5, 8.5, 11),
                    Block.makeCuboidShape(10.25, 7.5, 9.25, 12, 8.5, 11),
                    Block.makeCuboidShape(6.75, 2, 9.25, 8.5, 3, 11),
                    Block.makeCuboidShape(6.5, 9.5, 9.25, 7.5, 11.25, 11),
                    Block.makeCuboidShape(13.5, 9.5, 9.25, 14.5, 11.25, 11),
                    Block.makeCuboidShape(10, 4, 9.25, 11, 5.75, 11),
                    Block.makeCuboidShape(3.5, 12.5, 5.25, 5.25, 13.5, 7),
                    Block.makeCuboidShape(10.5, 12.5, 5.25, 12.25, 13.5, 7),
                    Block.makeCuboidShape(7, 7, 5.25, 8.75, 8, 7),
                    Block.makeCuboidShape(4, 7.5, 5.25, 5.75, 8.5, 7),
                    Block.makeCuboidShape(11, 7.5, 5.25, 12.75, 8.5, 7),
                    Block.makeCuboidShape(7.5, 2, 5.25, 9.25, 3, 7),
                    Block.makeCuboidShape(6.5, 10, 5.25, 7.5, 11.75, 7),
                    Block.makeCuboidShape(13.5, 10, 5.25, 14.5, 11.75, 7),
                    Block.makeCuboidShape(10, 4.5, 5.25, 11, 6.25, 7),
                    Block.makeCuboidShape(2.75, 12.5, 1.25, 4.5, 13.5, 3),
                    Block.makeCuboidShape(9.75, 12.5, 1.25, 11.5, 13.5, 3),
                    Block.makeCuboidShape(6.25, 7, 1.25, 8, 8, 3),
                    Block.makeCuboidShape(4.5, 7.5, 1.25, 6.25, 8.5, 3),
                    Block.makeCuboidShape(11.5, 7.5, 1.25, 13.25, 8.5, 3),
                    Block.makeCuboidShape(8, 2, 1.25, 9.75, 3, 3),
                    Block.makeCuboidShape(6.5, 10.5, 1.25, 7.5, 12.25, 3),
                    Block.makeCuboidShape(13.5, 10.5, 1.25, 14.5, 12.25, 3),
                    Block.makeCuboidShape(10, 5, 1.25, 11, 6.75, 3),
                    Block.makeCuboidShape(1.5, 10.25, 12.75, 2.5, 12.25, 14.75),
                    Block.makeCuboidShape(8.5, 10.25, 12.75, 9.5, 12.25, 14.75),
                    Block.makeCuboidShape(5, 4.75, 12.75, 6, 6.75, 14.75),
                    Block.makeCuboidShape(1.5, 9.75, 9, 2.5, 11.75, 11),
                    Block.makeCuboidShape(8.5, 9.75, 9, 9.5, 11.75, 11),
                    Block.makeCuboidShape(5, 4.25, 9, 6, 6.25, 11),
                    Block.makeCuboidShape(1.5, 9.25, 5, 2.5, 11.25, 7),
                    Block.makeCuboidShape(8.5, 9.25, 5, 9.5, 11.25, 7),
                    Block.makeCuboidShape(5, 3.75, 5, 6, 5.75, 7),
                    Block.makeCuboidShape(1.5, 8.75, 1.25, 2.5, 10.75, 3.25),
                    Block.makeCuboidShape(8.5, 8.75, 1.25, 9.5, 10.75, 3.25),
                    Block.makeCuboidShape(5, 3.25, 1.25, 6, 5.25, 3.25),
                    Block.makeCuboidShape(1.5, 10.25, 12.75, 2.5, 12.25, 14.75),
                    Block.makeCuboidShape(6, 3, 1, 10, 7, 15),
                    Block.makeCuboidShape(2, 0, 3, 14, 0, 8),
                    Block.makeCuboidShape(2, 0, 8, 14, 0, 13)
            ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get(),
            // south uncrushing
            Stream.of(
                    Block.makeCuboidShape(15, 2, 0, 16, 17, 16),
                    Block.makeCuboidShape(0, 2, 0, 1, 17, 16),
                    Block.makeCuboidShape(1, 2, 15, 15, 17, 16),
                    Block.makeCuboidShape(1, 2, 0, 15, 17, 1),
                    Block.makeCuboidShape(0, 0, 0, 2, 2, 16),
                    Block.makeCuboidShape(14, 0, 0, 16, 2, 16),
                    Block.makeCuboidShape(2, 0, 0, 14, 2, 3),
                    Block.makeCuboidShape(2, 0, 13, 14, 2, 16),
                    Block.makeCuboidShape(9.5, 8.5, 1, 13.5, 12.5, 15),
                    Block.makeCuboidShape(2.5, 8.5, 1, 6.5, 12.5, 15),
                    Block.makeCuboidShape(9.75, 12.5, 1.5, 11.5, 13.5, 3.25),
                    Block.makeCuboidShape(2.75, 12.5, 1.5, 4.5, 13.5, 3.25),
                    Block.makeCuboidShape(6.25, 7, 1.5, 8, 8, 3.25),
                    Block.makeCuboidShape(11.5, 7.5, 1.5, 13.25, 8.5, 3.25),
                    Block.makeCuboidShape(4.5, 7.5, 1.5, 6.25, 8.5, 3.25),
                    Block.makeCuboidShape(8, 2, 1.5, 9.75, 3, 3.25),
                    Block.makeCuboidShape(8.5, 9, 1.5, 9.5, 10.75, 3.25),
                    Block.makeCuboidShape(1.5, 9, 1.5, 2.5, 10.75, 3.25),
                    Block.makeCuboidShape(5, 3.5, 1.5, 6, 5.25, 3.25),
                    Block.makeCuboidShape(10.25, 12.5, 5, 12, 13.5, 6.75),
                    Block.makeCuboidShape(3.25, 12.5, 5, 5, 13.5, 6.75),
                    Block.makeCuboidShape(6.75, 7, 5, 8.5, 8, 6.75),
                    Block.makeCuboidShape(11, 7.5, 5, 12.75, 8.5, 6.75),
                    Block.makeCuboidShape(4, 7.5, 5, 5.75, 8.5, 6.75),
                    Block.makeCuboidShape(7.5, 2, 5, 9.25, 3, 6.75),
                    Block.makeCuboidShape(8.5, 9.5, 5, 9.5, 11.25, 6.75),
                    Block.makeCuboidShape(1.5, 9.5, 5, 2.5, 11.25, 6.75),
                    Block.makeCuboidShape(5, 4, 5, 6, 5.75, 6.75),
                    Block.makeCuboidShape(10.75, 12.5, 9, 12.5, 13.5, 10.75),
                    Block.makeCuboidShape(3.75, 12.5, 9, 5.5, 13.5, 10.75),
                    Block.makeCuboidShape(7.25, 7, 9, 9, 8, 10.75),
                    Block.makeCuboidShape(10.25, 7.5, 9, 12, 8.5, 10.75),
                    Block.makeCuboidShape(3.25, 7.5, 9, 5, 8.5, 10.75),
                    Block.makeCuboidShape(6.75, 2, 9, 8.5, 3, 10.75),
                    Block.makeCuboidShape(8.5, 10, 9, 9.5, 11.75, 10.75),
                    Block.makeCuboidShape(1.5, 10, 9, 2.5, 11.75, 10.75),
                    Block.makeCuboidShape(5, 4.5, 9, 6, 6.25, 10.75),
                    Block.makeCuboidShape(11.5, 12.5, 13, 13.25, 13.5, 14.75),
                    Block.makeCuboidShape(4.5, 12.5, 13, 6.25, 13.5, 14.75),
                    Block.makeCuboidShape(8, 7, 13, 9.75, 8, 14.75),
                    Block.makeCuboidShape(9.75, 7.5, 13, 11.5, 8.5, 14.75),
                    Block.makeCuboidShape(2.75, 7.5, 13, 4.5, 8.5, 14.75),
                    Block.makeCuboidShape(6.25, 2, 13, 8, 3, 14.75),
                    Block.makeCuboidShape(8.5, 10.5, 13, 9.5, 12.25, 14.75),
                    Block.makeCuboidShape(1.5, 10.5, 13, 2.5, 12.25, 14.75),
                    Block.makeCuboidShape(5, 5, 13, 6, 6.75, 14.75),
                    Block.makeCuboidShape(13.5, 10.25, 1.25, 14.5, 12.25, 3.25),
                    Block.makeCuboidShape(6.5, 10.25, 1.25, 7.5, 12.25, 3.25),
                    Block.makeCuboidShape(10, 4.75, 1.25, 11, 6.75, 3.25),
                    Block.makeCuboidShape(13.5, 9.75, 5, 14.5, 11.75, 7),
                    Block.makeCuboidShape(6.5, 9.75, 5, 7.5, 11.75, 7),
                    Block.makeCuboidShape(10, 4.25, 5, 11, 6.25, 7),
                    Block.makeCuboidShape(13.5, 9.25, 9, 14.5, 11.25, 11),
                    Block.makeCuboidShape(6.5, 9.25, 9, 7.5, 11.25, 11),
                    Block.makeCuboidShape(10, 3.75, 9, 11, 5.75, 11),
                    Block.makeCuboidShape(13.5, 8.75, 12.75, 14.5, 10.75, 14.75),
                    Block.makeCuboidShape(6.5, 8.75, 12.75, 7.5, 10.75, 14.75),
                    Block.makeCuboidShape(10, 3.25, 12.75, 11, 5.25, 14.75),
                    Block.makeCuboidShape(13.5, 10.25, 1.25, 14.5, 12.25, 3.25),
                    Block.makeCuboidShape(6, 3, 1, 10, 7, 15),
                    Block.makeCuboidShape(2, 0, 8, 14, 0, 13),
                    Block.makeCuboidShape(2, 0, 3, 14, 0, 8)
            ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get(),
            //east uncrushing
            Stream.of(
                    Block.makeCuboidShape(0, 2, 0, 16, 17, 1),
                    Block.makeCuboidShape(0, 2, 15, 16, 17, 16),
                    Block.makeCuboidShape(15, 2, 1, 16, 17, 15),
                    Block.makeCuboidShape(0, 2, 1, 1, 17, 15),
                    Block.makeCuboidShape(0, 0, 14, 16, 2, 16),
                    Block.makeCuboidShape(0, 0, 0, 16, 2, 2),
                    Block.makeCuboidShape(0, 0, 2, 3, 2, 14),
                    Block.makeCuboidShape(13, 0, 2, 16, 2, 14),
                    Block.makeCuboidShape(1, 8.5, 2.5, 15, 12.5, 6.5),
                    Block.makeCuboidShape(1, 8.5, 9.5, 15, 12.5, 13.5),
                    Block.makeCuboidShape(1.5, 12.5, 4.5, 3.25, 13.5, 6.25),
                    Block.makeCuboidShape(1.5, 12.5, 11.5, 3.25, 13.5, 13.25),
                    Block.makeCuboidShape(1.5, 7, 8, 3.25, 8, 9.75),
                    Block.makeCuboidShape(1.5, 7.5, 2.75, 3.25, 8.5, 4.5),
                    Block.makeCuboidShape(1.5, 7.5, 9.75, 3.25, 8.5, 11.5),
                    Block.makeCuboidShape(1.5, 2, 6.25, 3.25, 3, 8),
                    Block.makeCuboidShape(1.5, 9, 6.5, 3.25, 10.75, 7.5),
                    Block.makeCuboidShape(1.5, 9, 13.5, 3.25, 10.75, 14.5),
                    Block.makeCuboidShape(1.5, 3.5, 10, 3.25, 5.25, 11),
                    Block.makeCuboidShape(5, 12.5, 4, 6.75, 13.5, 5.75),
                    Block.makeCuboidShape(5, 12.5, 11, 6.75, 13.5, 12.75),
                    Block.makeCuboidShape(5, 7, 7.5, 6.75, 8, 9.25),
                    Block.makeCuboidShape(5, 7.5, 3.25, 6.75, 8.5, 5),
                    Block.makeCuboidShape(5, 7.5, 10.25, 6.75, 8.5, 12),
                    Block.makeCuboidShape(5, 2, 6.75, 6.75, 3, 8.5),
                    Block.makeCuboidShape(5, 9.5, 6.5, 6.75, 11.25, 7.5),
                    Block.makeCuboidShape(5, 9.5, 13.5, 6.75, 11.25, 14.5),
                    Block.makeCuboidShape(5, 4, 10, 6.75, 5.75, 11),
                    Block.makeCuboidShape(9, 12.5, 3.5, 10.75, 13.5, 5.25),
                    Block.makeCuboidShape(9, 12.5, 10.5, 10.75, 13.5, 12.25),
                    Block.makeCuboidShape(9, 7, 7, 10.75, 8, 8.75),
                    Block.makeCuboidShape(9, 7.5, 4, 10.75, 8.5, 5.75),
                    Block.makeCuboidShape(9, 7.5, 11, 10.75, 8.5, 12.75),
                    Block.makeCuboidShape(9, 2, 7.5, 10.75, 3, 9.25),
                    Block.makeCuboidShape(9, 10, 6.5, 10.75, 11.75, 7.5),
                    Block.makeCuboidShape(9, 10, 13.5, 10.75, 11.75, 14.5),
                    Block.makeCuboidShape(9, 4.5, 10, 10.75, 6.25, 11),
                    Block.makeCuboidShape(13, 12.5, 2.75, 14.75, 13.5, 4.5),
                    Block.makeCuboidShape(13, 12.5, 9.75, 14.75, 13.5, 11.5),
                    Block.makeCuboidShape(13, 7, 6.25, 14.75, 8, 8),
                    Block.makeCuboidShape(13, 7.5, 4.5, 14.75, 8.5, 6.25),
                    Block.makeCuboidShape(13, 7.5, 11.5, 14.75, 8.5, 13.25),
                    Block.makeCuboidShape(13, 2, 8, 14.75, 3, 9.75),
                    Block.makeCuboidShape(13, 10.5, 6.5, 14.75, 12.25, 7.5),
                    Block.makeCuboidShape(13, 10.5, 13.5, 14.75, 12.25, 14.5),
                    Block.makeCuboidShape(13, 5, 10, 14.75, 6.75, 11),
                    Block.makeCuboidShape(1.25, 10.25, 1.5, 3.25, 12.25, 2.5),
                    Block.makeCuboidShape(1.25, 10.25, 8.5, 3.25, 12.25, 9.5),
                    Block.makeCuboidShape(1.25, 4.75, 5, 3.25, 6.75, 6),
                    Block.makeCuboidShape(5, 9.75, 1.5, 7, 11.75, 2.5),
                    Block.makeCuboidShape(5, 9.75, 8.5, 7, 11.75, 9.5),
                    Block.makeCuboidShape(5, 4.25, 5, 7, 6.25, 6),
                    Block.makeCuboidShape(9, 9.25, 1.5, 11, 11.25, 2.5),
                    Block.makeCuboidShape(9, 9.25, 8.5, 11, 11.25, 9.5),
                    Block.makeCuboidShape(9, 3.75, 5, 11, 5.75, 6),
                    Block.makeCuboidShape(12.75, 8.75, 1.5, 14.75, 10.75, 2.5),
                    Block.makeCuboidShape(12.75, 8.75, 8.5, 14.75, 10.75, 9.5),
                    Block.makeCuboidShape(12.75, 3.25, 5, 14.75, 5.25, 6),
                    Block.makeCuboidShape(1.25, 10.25, 1.5, 3.25, 12.25, 2.5),
                    Block.makeCuboidShape(1, 3, 6, 15, 7, 10),
                    Block.makeCuboidShape(8, 0, 2, 13, 0, 14),
                    Block.makeCuboidShape(3, 0, 2, 8, 0, 14)
            ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get(),
            //west uncrushing
            Stream.of(
                    Block.makeCuboidShape(0, 2, 15, 16, 17, 16),
                    Block.makeCuboidShape(0, 2, 0, 16, 17, 1),
                    Block.makeCuboidShape(0, 2, 1, 1, 17, 15),
                    Block.makeCuboidShape(15, 2, 1, 16, 17, 15),
                    Block.makeCuboidShape(0, 0, 0, 16, 2, 2),
                    Block.makeCuboidShape(0, 0, 14, 16, 2, 16),
                    Block.makeCuboidShape(13, 0, 2, 16, 2, 14),
                    Block.makeCuboidShape(0, 0, 2, 3, 2, 14),
                    Block.makeCuboidShape(1, 8.5, 9.5, 15, 12.5, 13.5),
                    Block.makeCuboidShape(1, 8.5, 2.5, 15, 12.5, 6.5),
                    Block.makeCuboidShape(12.75, 12.5, 9.75, 14.5, 13.5, 11.5),
                    Block.makeCuboidShape(12.75, 12.5, 2.75, 14.5, 13.5, 4.5),
                    Block.makeCuboidShape(12.75, 7, 6.25, 14.5, 8, 8),
                    Block.makeCuboidShape(12.75, 7.5, 11.5, 14.5, 8.5, 13.25),
                    Block.makeCuboidShape(12.75, 7.5, 4.5, 14.5, 8.5, 6.25),
                    Block.makeCuboidShape(12.75, 2, 8, 14.5, 3, 9.75),
                    Block.makeCuboidShape(12.75, 9, 8.5, 14.5, 10.75, 9.5),
                    Block.makeCuboidShape(12.75, 9, 1.5, 14.5, 10.75, 2.5),
                    Block.makeCuboidShape(12.75, 3.5, 5, 14.5, 5.25, 6),
                    Block.makeCuboidShape(9.25, 12.5, 10.25, 11, 13.5, 12),
                    Block.makeCuboidShape(9.25, 12.5, 3.25, 11, 13.5, 5),
                    Block.makeCuboidShape(9.25, 7, 6.75, 11, 8, 8.5),
                    Block.makeCuboidShape(9.25, 7.5, 11, 11, 8.5, 12.75),
                    Block.makeCuboidShape(9.25, 7.5, 4, 11, 8.5, 5.75),
                    Block.makeCuboidShape(9.25, 2, 7.5, 11, 3, 9.25),
                    Block.makeCuboidShape(9.25, 9.5, 8.5, 11, 11.25, 9.5),
                    Block.makeCuboidShape(9.25, 9.5, 1.5, 11, 11.25, 2.5),
                    Block.makeCuboidShape(9.25, 4, 5, 11, 5.75, 6),
                    Block.makeCuboidShape(5.25, 12.5, 10.75, 7, 13.5, 12.5),
                    Block.makeCuboidShape(5.25, 12.5, 3.75, 7, 13.5, 5.5),
                    Block.makeCuboidShape(5.25, 7, 7.25, 7, 8, 9),
                    Block.makeCuboidShape(5.25, 7.5, 10.25, 7, 8.5, 12),
                    Block.makeCuboidShape(5.25, 7.5, 3.25, 7, 8.5, 5),
                    Block.makeCuboidShape(5.25, 2, 6.75, 7, 3, 8.5),
                    Block.makeCuboidShape(5.25, 10, 8.5, 7, 11.75, 9.5),
                    Block.makeCuboidShape(5.25, 10, 1.5, 7, 11.75, 2.5),
                    Block.makeCuboidShape(5.25, 4.5, 5, 7, 6.25, 6),
                    Block.makeCuboidShape(1.25, 12.5, 11.5, 3, 13.5, 13.25),
                    Block.makeCuboidShape(1.25, 12.5, 4.5, 3, 13.5, 6.25),
                    Block.makeCuboidShape(1.25, 7, 8, 3, 8, 9.75),
                    Block.makeCuboidShape(1.25, 7.5, 9.75, 3, 8.5, 11.5),
                    Block.makeCuboidShape(1.25, 7.5, 2.75, 3, 8.5, 4.5),
                    Block.makeCuboidShape(1.25, 2, 6.25, 3, 3, 8),
                    Block.makeCuboidShape(1.25, 10.5, 8.5, 3, 12.25, 9.5),
                    Block.makeCuboidShape(1.25, 10.5, 1.5, 3, 12.25, 2.5),
                    Block.makeCuboidShape(1.25, 5, 5, 3, 6.75, 6),
                    Block.makeCuboidShape(12.75, 10.25, 13.5, 14.75, 12.25, 14.5),
                    Block.makeCuboidShape(12.75, 10.25, 6.5, 14.75, 12.25, 7.5),
                    Block.makeCuboidShape(12.75, 4.75, 10, 14.75, 6.75, 11),
                    Block.makeCuboidShape(9, 9.75, 13.5, 11, 11.75, 14.5),
                    Block.makeCuboidShape(9, 9.75, 6.5, 11, 11.75, 7.5),
                    Block.makeCuboidShape(9, 4.25, 10, 11, 6.25, 11),
                    Block.makeCuboidShape(5, 9.25, 13.5, 7, 11.25, 14.5),
                    Block.makeCuboidShape(5, 9.25, 6.5, 7, 11.25, 7.5),
                    Block.makeCuboidShape(5, 3.75, 10, 7, 5.75, 11),
                    Block.makeCuboidShape(1.25, 8.75, 13.5, 3.25, 10.75, 14.5),
                    Block.makeCuboidShape(1.25, 8.75, 6.5, 3.25, 10.75, 7.5),
                    Block.makeCuboidShape(1.25, 3.25, 10, 3.25, 5.25, 11),
                    Block.makeCuboidShape(12.75, 10.25, 13.5, 14.75, 12.25, 14.5),
                    Block.makeCuboidShape(1, 3, 6, 15, 7, 10),
                    Block.makeCuboidShape(3, 0, 2, 8, 0, 14),
                    Block.makeCuboidShape(8, 0, 2, 13, 0, 14)
            ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get(),
            //empty north south
            Stream.of(
                    Block.makeCuboidShape(0, 2, 0, 1, 17, 16),
                    Block.makeCuboidShape(15, 2, 0, 16, 17, 16),
                    Block.makeCuboidShape(1, 2, 0, 15, 17, 1),
                    Block.makeCuboidShape(1, 2, 15, 15, 17, 16),
                    Block.makeCuboidShape(14, 0, 0, 16, 2, 16),
                    Block.makeCuboidShape(0, 0, 0, 2, 2, 16),
                    Block.makeCuboidShape(2, 0, 13, 14, 2, 16),
                    Block.makeCuboidShape(2, 0, 0, 14, 2, 3)
            ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get(),
            //empty east west
            Stream.of(
                    Block.makeCuboidShape(0, 2, 15, 16, 17, 16),
                    Block.makeCuboidShape(0, 2, 0, 16, 17, 1),
                    Block.makeCuboidShape(0, 2, 1, 1, 17, 15),
                    Block.makeCuboidShape(15, 2, 1, 16, 17, 15),
                    Block.makeCuboidShape(0, 0, 0, 16, 2, 2),
                    Block.makeCuboidShape(0, 0, 14, 16, 2, 16),
                    Block.makeCuboidShape(13, 0, 2, 16, 2, 14),
                    Block.makeCuboidShape(0, 0, 2, 3, 2, 14)
            ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get(),
            //east crush 1
            Stream.of(
                    Block.makeCuboidShape(0, 2, 0, 16, 17, 1),
                    Block.makeCuboidShape(0, 2, 15, 16, 17, 16),
                    Block.makeCuboidShape(15, 2, 1, 16, 17, 15),
                    Block.makeCuboidShape(0, 2, 1, 1, 17, 15),
                    Block.makeCuboidShape(0, 0, 14, 16, 2, 16),
                    Block.makeCuboidShape(0, 0, 0, 16, 2, 2),
                    Block.makeCuboidShape(0, 0, 2, 3, 2, 14),
                    Block.makeCuboidShape(13, 0, 2, 16, 2, 14),
                    Block.makeCuboidShape(1, 8.5, 2.5, 15, 12.5, 6.5),
                    Block.makeCuboidShape(1, 8.5, 9.5, 15, 12.5, 13.5),
                    Block.makeCuboidShape(1.5, 12.5, 4.5, 3.25, 13.5, 6.25),
                    Block.makeCuboidShape(1.5, 12.5, 11.5, 3.25, 13.5, 13.25),
                    Block.makeCuboidShape(1.5, 7, 8, 3.25, 8, 9.75),
                    Block.makeCuboidShape(1.5, 7.5, 2.75, 3.25, 8.5, 4.5),
                    Block.makeCuboidShape(1.5, 7.5, 9.75, 3.25, 8.5, 11.5),
                    Block.makeCuboidShape(1.5, 2, 6.25, 3.25, 3, 8),
                    Block.makeCuboidShape(1.5, 9, 6.5, 3.25, 10.75, 7.5),
                    Block.makeCuboidShape(1.5, 9, 13.5, 3.25, 10.75, 14.5),
                    Block.makeCuboidShape(1.5, 3.5, 10, 3.25, 5.25, 11),
                    Block.makeCuboidShape(5, 12.5, 4, 6.75, 13.5, 5.75),
                    Block.makeCuboidShape(5, 12.5, 11, 6.75, 13.5, 12.75),
                    Block.makeCuboidShape(5, 7, 7.5, 6.75, 8, 9.25),
                    Block.makeCuboidShape(5, 7.5, 3.25, 6.75, 8.5, 5),
                    Block.makeCuboidShape(5, 7.5, 10.25, 6.75, 8.5, 12),
                    Block.makeCuboidShape(5, 2, 6.75, 6.75, 3, 8.5),
                    Block.makeCuboidShape(5, 9.5, 6.5, 6.75, 11.25, 7.5),
                    Block.makeCuboidShape(5, 9.5, 13.5, 6.75, 11.25, 14.5),
                    Block.makeCuboidShape(5, 4, 10, 6.75, 5.75, 11),
                    Block.makeCuboidShape(9, 12.5, 3.5, 10.75, 13.5, 5.25),
                    Block.makeCuboidShape(9, 12.5, 10.5, 10.75, 13.5, 12.25),
                    Block.makeCuboidShape(9, 7, 7, 10.75, 8, 8.75),
                    Block.makeCuboidShape(9, 7.5, 4, 10.75, 8.5, 5.75),
                    Block.makeCuboidShape(9, 7.5, 11, 10.75, 8.5, 12.75),
                    Block.makeCuboidShape(9, 2, 7.5, 10.75, 3, 9.25),
                    Block.makeCuboidShape(9, 10, 6.5, 10.75, 11.75, 7.5),
                    Block.makeCuboidShape(9, 10, 13.5, 10.75, 11.75, 14.5),
                    Block.makeCuboidShape(9, 4.5, 10, 10.75, 6.25, 11),
                    Block.makeCuboidShape(13, 12.5, 2.75, 14.75, 13.5, 4.5),
                    Block.makeCuboidShape(13, 12.5, 9.75, 14.75, 13.5, 11.5),
                    Block.makeCuboidShape(13, 7, 6.25, 14.75, 8, 8),
                    Block.makeCuboidShape(13, 7.5, 4.5, 14.75, 8.5, 6.25),
                    Block.makeCuboidShape(13, 7.5, 11.5, 14.75, 8.5, 13.25),
                    Block.makeCuboidShape(13, 2, 8, 14.75, 3, 9.75),
                    Block.makeCuboidShape(13, 10.5, 6.5, 14.75, 12.25, 7.5),
                    Block.makeCuboidShape(13, 10.5, 13.5, 14.75, 12.25, 14.5),
                    Block.makeCuboidShape(13, 5, 10, 14.75, 6.75, 11),
                    Block.makeCuboidShape(1.25, 10.25, 1.5, 3.25, 12.25, 2.5),
                    Block.makeCuboidShape(1.25, 10.25, 8.5, 3.25, 12.25, 9.5),
                    Block.makeCuboidShape(1.25, 4.75, 5, 3.25, 6.75, 6),
                    Block.makeCuboidShape(5, 9.75, 1.5, 7, 11.75, 2.5),
                    Block.makeCuboidShape(5, 9.75, 8.5, 7, 11.75, 9.5),
                    Block.makeCuboidShape(5, 4.25, 5, 7, 6.25, 6),
                    Block.makeCuboidShape(9, 9.25, 1.5, 11, 11.25, 2.5),
                    Block.makeCuboidShape(9, 9.25, 8.5, 11, 11.25, 9.5),
                    Block.makeCuboidShape(9, 3.75, 5, 11, 5.75, 6),
                    Block.makeCuboidShape(12.75, 8.75, 1.5, 14.75, 10.75, 2.5),
                    Block.makeCuboidShape(12.75, 8.75, 8.5, 14.75, 10.75, 9.5),
                    Block.makeCuboidShape(12.75, 3.25, 5, 14.75, 5.25, 6),
                    Block.makeCuboidShape(1, 3, 6, 15, 7, 10),
                    Block.makeCuboidShape(8, 0, 2, 13, 0, 14),
                    Block.makeCuboidShape(3, 0, 2, 8, 0, 14)
            ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get(),
            //west crush 1
            Stream.of(
                    Block.makeCuboidShape(0, 2, 15, 16, 17, 16),
                    Block.makeCuboidShape(0, 2, 0, 16, 17, 1),
                    Block.makeCuboidShape(0, 2, 1, 1, 17, 15),
                    Block.makeCuboidShape(15, 2, 1, 16, 17, 15),
                    Block.makeCuboidShape(0, 0, 0, 16, 2, 2),
                    Block.makeCuboidShape(0, 0, 14, 16, 2, 16),
                    Block.makeCuboidShape(13, 0, 2, 16, 2, 14),
                    Block.makeCuboidShape(0, 0, 2, 3, 2, 14),
                    Block.makeCuboidShape(1, 8.5, 9.5, 15, 12.5, 13.5),
                    Block.makeCuboidShape(1, 8.5, 2.5, 15, 12.5, 6.5),
                    Block.makeCuboidShape(12.75, 12.5, 9.75, 14.5, 13.5, 11.5),
                    Block.makeCuboidShape(12.75, 12.5, 2.75, 14.5, 13.5, 4.5),
                    Block.makeCuboidShape(12.75, 7, 6.25, 14.5, 8, 8),
                    Block.makeCuboidShape(12.75, 7.5, 11.5, 14.5, 8.5, 13.25),
                    Block.makeCuboidShape(12.75, 7.5, 4.5, 14.5, 8.5, 6.25),
                    Block.makeCuboidShape(12.75, 2, 8, 14.5, 3, 9.75),
                    Block.makeCuboidShape(12.75, 9, 8.5, 14.5, 10.75, 9.5),
                    Block.makeCuboidShape(12.75, 9, 1.5, 14.5, 10.75, 2.5),
                    Block.makeCuboidShape(12.75, 3.5, 5, 14.5, 5.25, 6),
                    Block.makeCuboidShape(9.25, 12.5, 10.25, 11, 13.5, 12),
                    Block.makeCuboidShape(9.25, 12.5, 3.25, 11, 13.5, 5),
                    Block.makeCuboidShape(9.25, 7, 6.75, 11, 8, 8.5),
                    Block.makeCuboidShape(9.25, 7.5, 11, 11, 8.5, 12.75),
                    Block.makeCuboidShape(9.25, 7.5, 4, 11, 8.5, 5.75),
                    Block.makeCuboidShape(9.25, 2, 7.5, 11, 3, 9.25),
                    Block.makeCuboidShape(9.25, 9.5, 8.5, 11, 11.25, 9.5),
                    Block.makeCuboidShape(9.25, 9.5, 1.5, 11, 11.25, 2.5),
                    Block.makeCuboidShape(9.25, 4, 5, 11, 5.75, 6),
                    Block.makeCuboidShape(5.25, 12.5, 10.75, 7, 13.5, 12.5),
                    Block.makeCuboidShape(5.25, 12.5, 3.75, 7, 13.5, 5.5),
                    Block.makeCuboidShape(5.25, 7, 7.25, 7, 8, 9),
                    Block.makeCuboidShape(5.25, 7.5, 10.25, 7, 8.5, 12),
                    Block.makeCuboidShape(5.25, 7.5, 3.25, 7, 8.5, 5),
                    Block.makeCuboidShape(5.25, 2, 6.75, 7, 3, 8.5),
                    Block.makeCuboidShape(5.25, 10, 8.5, 7, 11.75, 9.5),
                    Block.makeCuboidShape(5.25, 10, 1.5, 7, 11.75, 2.5),
                    Block.makeCuboidShape(5.25, 4.5, 5, 7, 6.25, 6),
                    Block.makeCuboidShape(1.25, 12.5, 11.5, 3, 13.5, 13.25),
                    Block.makeCuboidShape(1.25, 12.5, 4.5, 3, 13.5, 6.25),
                    Block.makeCuboidShape(1.25, 7, 8, 3, 8, 9.75),
                    Block.makeCuboidShape(1.25, 7.5, 9.75, 3, 8.5, 11.5),
                    Block.makeCuboidShape(1.25, 7.5, 2.75, 3, 8.5, 4.5),
                    Block.makeCuboidShape(1.25, 2, 6.25, 3, 3, 8),
                    Block.makeCuboidShape(1.25, 10.5, 8.5, 3, 12.25, 9.5),
                    Block.makeCuboidShape(1.25, 10.5, 1.5, 3, 12.25, 2.5),
                    Block.makeCuboidShape(1.25, 5, 5, 3, 6.75, 6),
                    Block.makeCuboidShape(12.75, 10.25, 13.5, 14.75, 12.25, 14.5),
                    Block.makeCuboidShape(12.75, 10.25, 6.5, 14.75, 12.25, 7.5),
                    Block.makeCuboidShape(12.75, 4.75, 10, 14.75, 6.75, 11),
                    Block.makeCuboidShape(9, 9.75, 13.5, 11, 11.75, 14.5),
                    Block.makeCuboidShape(9, 9.75, 6.5, 11, 11.75, 7.5),
                    Block.makeCuboidShape(9, 4.25, 10, 11, 6.25, 11),
                    Block.makeCuboidShape(5, 9.25, 13.5, 7, 11.25, 14.5),
                    Block.makeCuboidShape(5, 9.25, 6.5, 7, 11.25, 7.5),
                    Block.makeCuboidShape(5, 3.75, 10, 7, 5.75, 11),
                    Block.makeCuboidShape(1.25, 8.75, 13.5, 3.25, 10.75, 14.5),
                    Block.makeCuboidShape(1.25, 8.75, 6.5, 3.25, 10.75, 7.5),
                    Block.makeCuboidShape(1.25, 3.25, 10, 3.25, 5.25, 11),
                    Block.makeCuboidShape(1, 3, 6, 15, 7, 10),
                    Block.makeCuboidShape(3, 0, 2, 8, 0, 14),
                    Block.makeCuboidShape(8, 0, 2, 13, 0, 14)
            ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get(),
            //north crush 1
            Stream.of(
                    Block.makeCuboidShape(0, 2, 0, 1, 17, 16),
                    Block.makeCuboidShape(15, 2, 0, 16, 17, 16),
                    Block.makeCuboidShape(1, 2, 0, 15, 17, 1),
                    Block.makeCuboidShape(1, 2, 15, 15, 17, 16),
                    Block.makeCuboidShape(14, 0, 0, 16, 2, 16),
                    Block.makeCuboidShape(0, 0, 0, 2, 2, 16),
                    Block.makeCuboidShape(2, 0, 13, 14, 2, 16),
                    Block.makeCuboidShape(2, 0, 0, 14, 2, 3),
                    Block.makeCuboidShape(2.5, 8.5, 1, 6.5, 12.5, 15),
                    Block.makeCuboidShape(9.5, 8.5, 1, 13.5, 12.5, 15),
                    Block.makeCuboidShape(4.5, 12.5, 12.75, 6.25, 13.5, 14.5),
                    Block.makeCuboidShape(11.5, 12.5, 12.75, 13.25, 13.5, 14.5),
                    Block.makeCuboidShape(8, 7, 12.75, 9.75, 8, 14.5),
                    Block.makeCuboidShape(2.75, 7.5, 12.75, 4.5, 8.5, 14.5),
                    Block.makeCuboidShape(9.75, 7.5, 12.75, 11.5, 8.5, 14.5),
                    Block.makeCuboidShape(6.25, 2, 12.75, 8, 3, 14.5),
                    Block.makeCuboidShape(6.5, 9, 12.75, 7.5, 10.75, 14.5),
                    Block.makeCuboidShape(13.5, 9, 12.75, 14.5, 10.75, 14.5),
                    Block.makeCuboidShape(10, 3.5, 12.75, 11, 5.25, 14.5),
                    Block.makeCuboidShape(4, 12.5, 9.25, 5.75, 13.5, 11),
                    Block.makeCuboidShape(11, 12.5, 9.25, 12.75, 13.5, 11),
                    Block.makeCuboidShape(7.5, 7, 9.25, 9.25, 8, 11),
                    Block.makeCuboidShape(3.25, 7.5, 9.25, 5, 8.5, 11),
                    Block.makeCuboidShape(10.25, 7.5, 9.25, 12, 8.5, 11),
                    Block.makeCuboidShape(6.75, 2, 9.25, 8.5, 3, 11),
                    Block.makeCuboidShape(6.5, 9.5, 9.25, 7.5, 11.25, 11),
                    Block.makeCuboidShape(13.5, 9.5, 9.25, 14.5, 11.25, 11),
                    Block.makeCuboidShape(10, 4, 9.25, 11, 5.75, 11),
                    Block.makeCuboidShape(3.5, 12.5, 5.25, 5.25, 13.5, 7),
                    Block.makeCuboidShape(10.5, 12.5, 5.25, 12.25, 13.5, 7),
                    Block.makeCuboidShape(7, 7, 5.25, 8.75, 8, 7),
                    Block.makeCuboidShape(4, 7.5, 5.25, 5.75, 8.5, 7),
                    Block.makeCuboidShape(11, 7.5, 5.25, 12.75, 8.5, 7),
                    Block.makeCuboidShape(7.5, 2, 5.25, 9.25, 3, 7),
                    Block.makeCuboidShape(6.5, 10, 5.25, 7.5, 11.75, 7),
                    Block.makeCuboidShape(13.5, 10, 5.25, 14.5, 11.75, 7),
                    Block.makeCuboidShape(10, 4.5, 5.25, 11, 6.25, 7),
                    Block.makeCuboidShape(2.75, 12.5, 1.25, 4.5, 13.5, 3),
                    Block.makeCuboidShape(9.75, 12.5, 1.25, 11.5, 13.5, 3),
                    Block.makeCuboidShape(6.25, 7, 1.25, 8, 8, 3),
                    Block.makeCuboidShape(4.5, 7.5, 1.25, 6.25, 8.5, 3),
                    Block.makeCuboidShape(11.5, 7.5, 1.25, 13.25, 8.5, 3),
                    Block.makeCuboidShape(8, 2, 1.25, 9.75, 3, 3),
                    Block.makeCuboidShape(6.5, 10.5, 1.25, 7.5, 12.25, 3),
                    Block.makeCuboidShape(13.5, 10.5, 1.25, 14.5, 12.25, 3),
                    Block.makeCuboidShape(10, 5, 1.25, 11, 6.75, 3),
                    Block.makeCuboidShape(1.5, 10.25, 12.75, 2.5, 12.25, 14.75),
                    Block.makeCuboidShape(8.5, 10.25, 12.75, 9.5, 12.25, 14.75),
                    Block.makeCuboidShape(5, 4.75, 12.75, 6, 6.75, 14.75),
                    Block.makeCuboidShape(1.5, 9.75, 9, 2.5, 11.75, 11),
                    Block.makeCuboidShape(8.5, 9.75, 9, 9.5, 11.75, 11),
                    Block.makeCuboidShape(5, 4.25, 9, 6, 6.25, 11),
                    Block.makeCuboidShape(1.5, 9.25, 5, 2.5, 11.25, 7),
                    Block.makeCuboidShape(8.5, 9.25, 5, 9.5, 11.25, 7),
                    Block.makeCuboidShape(5, 3.75, 5, 6, 5.75, 7),
                    Block.makeCuboidShape(1.5, 8.75, 1.25, 2.5, 10.75, 3.25),
                    Block.makeCuboidShape(8.5, 8.75, 1.25, 9.5, 10.75, 3.25),
                    Block.makeCuboidShape(5, 3.25, 1.25, 6, 5.25, 3.25),
                    Block.makeCuboidShape(6, 3, 1, 10, 7, 15),
                    Block.makeCuboidShape(2, 0, 3, 14, 0, 8),
                    Block.makeCuboidShape(2, 0, 8, 14, 0, 13)
            ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get(),
            //south crush 1
            Stream.of(
                    Block.makeCuboidShape(0, 2, 0, 1, 17, 16),
                    Block.makeCuboidShape(15, 2, 0, 16, 17, 16),
                    Block.makeCuboidShape(1, 2, 15, 15, 17, 16),
                    Block.makeCuboidShape(1, 2, 0, 15, 17, 1),
                    Block.makeCuboidShape(0, 0, 0, 2, 2, 16),
                    Block.makeCuboidShape(14, 0, 0, 16, 2, 16),
                    Block.makeCuboidShape(2, 0, 0, 14, 2, 3),
                    Block.makeCuboidShape(2, 0, 13, 14, 2, 16),
                    Block.makeCuboidShape(9.5, 8.5, 1, 13.5, 12.5, 15),
                    Block.makeCuboidShape(2.5, 8.5, 1, 6.5, 12.5, 15),
                    Block.makeCuboidShape(9.75, 12.5, 1.5, 11.5, 13.5, 3.25),
                    Block.makeCuboidShape(2.75, 12.5, 1.5, 4.5, 13.5, 3.25),
                    Block.makeCuboidShape(6.25, 7, 1.5, 8, 8, 3.25),
                    Block.makeCuboidShape(11.5, 7.5, 1.5, 13.25, 8.5, 3.25),
                    Block.makeCuboidShape(4.5, 7.5, 1.5, 6.25, 8.5, 3.25),
                    Block.makeCuboidShape(8, 2, 1.5, 9.75, 3, 3.25),
                    Block.makeCuboidShape(8.5, 9, 1.5, 9.5, 10.75, 3.25),
                    Block.makeCuboidShape(1.5, 9, 1.5, 2.5, 10.75, 3.25),
                    Block.makeCuboidShape(5, 3.5, 1.5, 6, 5.25, 3.25),
                    Block.makeCuboidShape(10.25, 12.5, 5, 12, 13.5, 6.75),
                    Block.makeCuboidShape(3.25, 12.5, 5, 5, 13.5, 6.75),
                    Block.makeCuboidShape(6.75, 7, 5, 8.5, 8, 6.75),
                    Block.makeCuboidShape(11, 7.5, 5, 12.75, 8.5, 6.75),
                    Block.makeCuboidShape(4, 7.5, 5, 5.75, 8.5, 6.75),
                    Block.makeCuboidShape(7.5, 2, 5, 9.25, 3, 6.75),
                    Block.makeCuboidShape(8.5, 9.5, 5, 9.5, 11.25, 6.75),
                    Block.makeCuboidShape(1.5, 9.5, 5, 2.5, 11.25, 6.75),
                    Block.makeCuboidShape(5, 4, 5, 6, 5.75, 6.75),
                    Block.makeCuboidShape(10.75, 12.5, 9, 12.5, 13.5, 10.75),
                    Block.makeCuboidShape(3.75, 12.5, 9, 5.5, 13.5, 10.75),
                    Block.makeCuboidShape(7.25, 7, 9, 9, 8, 10.75),
                    Block.makeCuboidShape(10.25, 7.5, 9, 12, 8.5, 10.75),
                    Block.makeCuboidShape(3.25, 7.5, 9, 5, 8.5, 10.75),
                    Block.makeCuboidShape(6.75, 2, 9, 8.5, 3, 10.75),
                    Block.makeCuboidShape(8.5, 10, 9, 9.5, 11.75, 10.75),
                    Block.makeCuboidShape(1.5, 10, 9, 2.5, 11.75, 10.75),
                    Block.makeCuboidShape(5, 4.5, 9, 6, 6.25, 10.75),
                    Block.makeCuboidShape(11.5, 12.5, 13, 13.25, 13.5, 14.75),
                    Block.makeCuboidShape(4.5, 12.5, 13, 6.25, 13.5, 14.75),
                    Block.makeCuboidShape(8, 7, 13, 9.75, 8, 14.75),
                    Block.makeCuboidShape(9.75, 7.5, 13, 11.5, 8.5, 14.75),
                    Block.makeCuboidShape(2.75, 7.5, 13, 4.5, 8.5, 14.75),
                    Block.makeCuboidShape(6.25, 2, 13, 8, 3, 14.75),
                    Block.makeCuboidShape(8.5, 10.5, 13, 9.5, 12.25, 14.75),
                    Block.makeCuboidShape(1.5, 10.5, 13, 2.5, 12.25, 14.75),
                    Block.makeCuboidShape(5, 5, 13, 6, 6.75, 14.75),
                    Block.makeCuboidShape(13.5, 10.25, 1.25, 14.5, 12.25, 3.25),
                    Block.makeCuboidShape(6.5, 10.25, 1.25, 7.5, 12.25, 3.25),
                    Block.makeCuboidShape(10, 4.75, 1.25, 11, 6.75, 3.25),
                    Block.makeCuboidShape(13.5, 9.75, 5, 14.5, 11.75, 7),
                    Block.makeCuboidShape(6.5, 9.75, 5, 7.5, 11.75, 7),
                    Block.makeCuboidShape(10, 4.25, 5, 11, 6.25, 7),
                    Block.makeCuboidShape(13.5, 9.25, 9, 14.5, 11.25, 11),
                    Block.makeCuboidShape(6.5, 9.25, 9, 7.5, 11.25, 11),
                    Block.makeCuboidShape(10, 3.75, 9, 11, 5.75, 11),
                    Block.makeCuboidShape(13.5, 8.75, 12.75, 14.5, 10.75, 14.75),
                    Block.makeCuboidShape(6.5, 8.75, 12.75, 7.5, 10.75, 14.75),
                    Block.makeCuboidShape(10, 3.25, 12.75, 11, 5.25, 14.75),
                    Block.makeCuboidShape(6, 3, 1, 10, 7, 15),
                    Block.makeCuboidShape(2, 0, 8, 14, 0, 13),
                    Block.makeCuboidShape(2, 0, 3, 14, 0, 8)
            ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get(),
    //east crsuh 2
            Stream.of(
                    Block.makeCuboidShape(0, 2, 0, 16, 17, 1),
                    Block.makeCuboidShape(0, 2, 15, 16, 17, 16),
                    Block.makeCuboidShape(15, 2, 1, 16, 17, 15),
                    Block.makeCuboidShape(0, 2, 1, 1, 17, 15),
                    Block.makeCuboidShape(0, 0, 14, 16, 2, 16),
                    Block.makeCuboidShape(0, 0, 0, 16, 2, 2),
                    Block.makeCuboidShape(0, 0, 2, 3, 2, 14),
                    Block.makeCuboidShape(13, 0, 2, 16, 2, 14),
                    Block.makeCuboidShape(1, 8.5, 2.5, 15, 12.5, 6.5),
                    Block.makeCuboidShape(1, 8.5, 9.5, 15, 12.5, 13.5),
                    Block.makeCuboidShape(1.5, 12.5, 4.5, 3.25, 13.5, 6.25),
                    Block.makeCuboidShape(1.5, 12.5, 11.5, 3.25, 13.5, 13.25),
                    Block.makeCuboidShape(1.5, 7, 8, 3.25, 8, 9.75),
                    Block.makeCuboidShape(1.5, 7.5, 2.75, 3.25, 8.5, 4.5),
                    Block.makeCuboidShape(1.5, 7.5, 9.75, 3.25, 8.5, 11.5),
                    Block.makeCuboidShape(1.5, 2, 6.25, 3.25, 3, 8),
                    Block.makeCuboidShape(1.5, 9, 6.5, 3.25, 10.75, 7.5),
                    Block.makeCuboidShape(1.5, 9, 13.5, 3.25, 10.75, 14.5),
                    Block.makeCuboidShape(1.5, 3.5, 10, 3.25, 5.25, 11),
                    Block.makeCuboidShape(5, 12.5, 4, 6.75, 13.5, 5.75),
                    Block.makeCuboidShape(5, 12.5, 11, 6.75, 13.5, 12.75),
                    Block.makeCuboidShape(5, 7, 7.5, 6.75, 8, 9.25),
                    Block.makeCuboidShape(5, 7.5, 3.25, 6.75, 8.5, 5),
                    Block.makeCuboidShape(5, 7.5, 10.25, 6.75, 8.5, 12),
                    Block.makeCuboidShape(5, 2, 6.75, 6.75, 3, 8.5),
                    Block.makeCuboidShape(5, 9.5, 6.5, 6.75, 11.25, 7.5),
                    Block.makeCuboidShape(5, 9.5, 13.5, 6.75, 11.25, 14.5),
                    Block.makeCuboidShape(5, 4, 10, 6.75, 5.75, 11),
                    Block.makeCuboidShape(9, 12.5, 3.5, 10.75, 13.5, 5.25),
                    Block.makeCuboidShape(9, 12.5, 10.5, 10.75, 13.5, 12.25),
                    Block.makeCuboidShape(9, 7, 7, 10.75, 8, 8.75),
                    Block.makeCuboidShape(9, 7.5, 4, 10.75, 8.5, 5.75),
                    Block.makeCuboidShape(9, 7.5, 11, 10.75, 8.5, 12.75),
                    Block.makeCuboidShape(9, 2, 7.5, 10.75, 3, 9.25),
                    Block.makeCuboidShape(9, 10, 6.5, 10.75, 11.75, 7.5),
                    Block.makeCuboidShape(9, 10, 13.5, 10.75, 11.75, 14.5),
                    Block.makeCuboidShape(9, 4.5, 10, 10.75, 6.25, 11),
                    Block.makeCuboidShape(13, 12.5, 2.75, 14.75, 13.5, 4.5),
                    Block.makeCuboidShape(13, 12.5, 9.75, 14.75, 13.5, 11.5),
                    Block.makeCuboidShape(13, 7, 6.25, 14.75, 8, 8),
                    Block.makeCuboidShape(13, 7.5, 4.5, 14.75, 8.5, 6.25),
                    Block.makeCuboidShape(13, 7.5, 11.5, 14.75, 8.5, 13.25),
                    Block.makeCuboidShape(13, 2, 8, 14.75, 3, 9.75),
                    Block.makeCuboidShape(13, 10.5, 6.5, 14.75, 12.25, 7.5),
                    Block.makeCuboidShape(13, 10.5, 13.5, 14.75, 12.25, 14.5),
                    Block.makeCuboidShape(13, 5, 10, 14.75, 6.75, 11),
                    Block.makeCuboidShape(1.25, 10.25, 1.5, 3.25, 12.25, 2.5),
                    Block.makeCuboidShape(1.25, 10.25, 8.5, 3.25, 12.25, 9.5),
                    Block.makeCuboidShape(1.25, 4.75, 5, 3.25, 6.75, 6),
                    Block.makeCuboidShape(5, 9.75, 1.5, 7, 11.75, 2.5),
                    Block.makeCuboidShape(5, 9.75, 8.5, 7, 11.75, 9.5),
                    Block.makeCuboidShape(5, 4.25, 5, 7, 6.25, 6),
                    Block.makeCuboidShape(9, 9.25, 1.5, 11, 11.25, 2.5),
                    Block.makeCuboidShape(9, 9.25, 8.5, 11, 11.25, 9.5),
                    Block.makeCuboidShape(9, 3.75, 5, 11, 5.75, 6),
                    Block.makeCuboidShape(12.75, 8.75, 1.5, 14.75, 10.75, 2.5),
                    Block.makeCuboidShape(12.75, 8.75, 8.5, 14.75, 10.75, 9.5),
                    Block.makeCuboidShape(12.75, 3.25, 5, 14.75, 5.25, 6),
                    Block.makeCuboidShape(1, 3, 6, 15, 7, 10),
                    Block.makeCuboidShape(8, 0, 2, 13, 0, 14),
                    Block.makeCuboidShape(3, 0, 2, 8, 0, 14)
            ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get(),
      //      west crush 2
            Stream.of(
                    Block.makeCuboidShape(0, 2, 15, 16, 17, 16),
                    Block.makeCuboidShape(0, 2, 0, 16, 17, 1),
                    Block.makeCuboidShape(0, 2, 1, 1, 17, 15),
                    Block.makeCuboidShape(15, 2, 1, 16, 17, 15),
                    Block.makeCuboidShape(0, 0, 0, 16, 2, 2),
                    Block.makeCuboidShape(0, 0, 14, 16, 2, 16),
                    Block.makeCuboidShape(13, 0, 2, 16, 2, 14),
                    Block.makeCuboidShape(0, 0, 2, 3, 2, 14),
                    Block.makeCuboidShape(1, 8.5, 9.5, 15, 12.5, 13.5),
                    Block.makeCuboidShape(1, 8.5, 2.5, 15, 12.5, 6.5),
                    Block.makeCuboidShape(12.75, 12.5, 9.75, 14.5, 13.5, 11.5),
                    Block.makeCuboidShape(12.75, 12.5, 2.75, 14.5, 13.5, 4.5),
                    Block.makeCuboidShape(12.75, 7, 6.25, 14.5, 8, 8),
                    Block.makeCuboidShape(12.75, 7.5, 11.5, 14.5, 8.5, 13.25),
                    Block.makeCuboidShape(12.75, 7.5, 4.5, 14.5, 8.5, 6.25),
                    Block.makeCuboidShape(12.75, 2, 8, 14.5, 3, 9.75),
                    Block.makeCuboidShape(12.75, 9, 8.5, 14.5, 10.75, 9.5),
                    Block.makeCuboidShape(12.75, 9, 1.5, 14.5, 10.75, 2.5),
                    Block.makeCuboidShape(12.75, 3.5, 5, 14.5, 5.25, 6),
                    Block.makeCuboidShape(9.25, 12.5, 10.25, 11, 13.5, 12),
                    Block.makeCuboidShape(9.25, 12.5, 3.25, 11, 13.5, 5),
                    Block.makeCuboidShape(9.25, 7, 6.75, 11, 8, 8.5),
                    Block.makeCuboidShape(9.25, 7.5, 11, 11, 8.5, 12.75),
                    Block.makeCuboidShape(9.25, 7.5, 4, 11, 8.5, 5.75),
                    Block.makeCuboidShape(9.25, 2, 7.5, 11, 3, 9.25),
                    Block.makeCuboidShape(9.25, 9.5, 8.5, 11, 11.25, 9.5),
                    Block.makeCuboidShape(9.25, 9.5, 1.5, 11, 11.25, 2.5),
                    Block.makeCuboidShape(9.25, 4, 5, 11, 5.75, 6),
                    Block.makeCuboidShape(5.25, 12.5, 10.75, 7, 13.5, 12.5),
                    Block.makeCuboidShape(5.25, 12.5, 3.75, 7, 13.5, 5.5),
                    Block.makeCuboidShape(5.25, 7, 7.25, 7, 8, 9),
                    Block.makeCuboidShape(5.25, 7.5, 10.25, 7, 8.5, 12),
                    Block.makeCuboidShape(5.25, 7.5, 3.25, 7, 8.5, 5),
                    Block.makeCuboidShape(5.25, 2, 6.75, 7, 3, 8.5),
                    Block.makeCuboidShape(5.25, 10, 8.5, 7, 11.75, 9.5),
                    Block.makeCuboidShape(5.25, 10, 1.5, 7, 11.75, 2.5),
                    Block.makeCuboidShape(5.25, 4.5, 5, 7, 6.25, 6),
                    Block.makeCuboidShape(1.25, 12.5, 11.5, 3, 13.5, 13.25),
                    Block.makeCuboidShape(1.25, 12.5, 4.5, 3, 13.5, 6.25),
                    Block.makeCuboidShape(1.25, 7, 8, 3, 8, 9.75),
                    Block.makeCuboidShape(1.25, 7.5, 9.75, 3, 8.5, 11.5),
                    Block.makeCuboidShape(1.25, 7.5, 2.75, 3, 8.5, 4.5),
                    Block.makeCuboidShape(1.25, 2, 6.25, 3, 3, 8),
                    Block.makeCuboidShape(1.25, 10.5, 8.5, 3, 12.25, 9.5),
                    Block.makeCuboidShape(1.25, 10.5, 1.5, 3, 12.25, 2.5),
                    Block.makeCuboidShape(1.25, 5, 5, 3, 6.75, 6),
                    Block.makeCuboidShape(12.75, 10.25, 13.5, 14.75, 12.25, 14.5),
                    Block.makeCuboidShape(12.75, 10.25, 6.5, 14.75, 12.25, 7.5),
                    Block.makeCuboidShape(12.75, 4.75, 10, 14.75, 6.75, 11),
                    Block.makeCuboidShape(9, 9.75, 13.5, 11, 11.75, 14.5),
                    Block.makeCuboidShape(9, 9.75, 6.5, 11, 11.75, 7.5),
                    Block.makeCuboidShape(9, 4.25, 10, 11, 6.25, 11),
                    Block.makeCuboidShape(5, 9.25, 13.5, 7, 11.25, 14.5),
                    Block.makeCuboidShape(5, 9.25, 6.5, 7, 11.25, 7.5),
                    Block.makeCuboidShape(5, 3.75, 10, 7, 5.75, 11),
                    Block.makeCuboidShape(1.25, 8.75, 13.5, 3.25, 10.75, 14.5),
                    Block.makeCuboidShape(1.25, 8.75, 6.5, 3.25, 10.75, 7.5),
                    Block.makeCuboidShape(1.25, 3.25, 10, 3.25, 5.25, 11),
                    Block.makeCuboidShape(1, 3, 6, 15, 7, 10),
                    Block.makeCuboidShape(3, 0, 2, 8, 0, 14),
                    Block.makeCuboidShape(8, 0, 2, 13, 0, 14)
            ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get(),
         //   north crush 2
            Stream.of(
                    Block.makeCuboidShape(0, 2, 0, 1, 17, 16),
                    Block.makeCuboidShape(15, 2, 0, 16, 17, 16),
                    Block.makeCuboidShape(1, 2, 0, 15, 17, 1),
                    Block.makeCuboidShape(1, 2, 15, 15, 17, 16),
                    Block.makeCuboidShape(14, 0, 0, 16, 2, 16),
                    Block.makeCuboidShape(0, 0, 0, 2, 2, 16),
                    Block.makeCuboidShape(2, 0, 13, 14, 2, 16),
                    Block.makeCuboidShape(2, 0, 0, 14, 2, 3),
                    Block.makeCuboidShape(2.5, 8.5, 1, 6.5, 12.5, 15),
                    Block.makeCuboidShape(9.5, 8.5, 1, 13.5, 12.5, 15),
                    Block.makeCuboidShape(4.5, 12.5, 12.75, 6.25, 13.5, 14.5),
                    Block.makeCuboidShape(11.5, 12.5, 12.75, 13.25, 13.5, 14.5),
                    Block.makeCuboidShape(8, 7, 12.75, 9.75, 8, 14.5),
                    Block.makeCuboidShape(2.75, 7.5, 12.75, 4.5, 8.5, 14.5),
                    Block.makeCuboidShape(9.75, 7.5, 12.75, 11.5, 8.5, 14.5),
                    Block.makeCuboidShape(6.25, 2, 12.75, 8, 3, 14.5),
                    Block.makeCuboidShape(6.5, 9, 12.75, 7.5, 10.75, 14.5),
                    Block.makeCuboidShape(13.5, 9, 12.75, 14.5, 10.75, 14.5),
                    Block.makeCuboidShape(10, 3.5, 12.75, 11, 5.25, 14.5),
                    Block.makeCuboidShape(4, 12.5, 9.25, 5.75, 13.5, 11),
                    Block.makeCuboidShape(11, 12.5, 9.25, 12.75, 13.5, 11),
                    Block.makeCuboidShape(7.5, 7, 9.25, 9.25, 8, 11),
                    Block.makeCuboidShape(3.25, 7.5, 9.25, 5, 8.5, 11),
                    Block.makeCuboidShape(10.25, 7.5, 9.25, 12, 8.5, 11),
                    Block.makeCuboidShape(6.75, 2, 9.25, 8.5, 3, 11),
                    Block.makeCuboidShape(6.5, 9.5, 9.25, 7.5, 11.25, 11),
                    Block.makeCuboidShape(13.5, 9.5, 9.25, 14.5, 11.25, 11),
                    Block.makeCuboidShape(10, 4, 9.25, 11, 5.75, 11),
                    Block.makeCuboidShape(3.5, 12.5, 5.25, 5.25, 13.5, 7),
                    Block.makeCuboidShape(10.5, 12.5, 5.25, 12.25, 13.5, 7),
                    Block.makeCuboidShape(7, 7, 5.25, 8.75, 8, 7),
                    Block.makeCuboidShape(4, 7.5, 5.25, 5.75, 8.5, 7),
                    Block.makeCuboidShape(11, 7.5, 5.25, 12.75, 8.5, 7),
                    Block.makeCuboidShape(7.5, 2, 5.25, 9.25, 3, 7),
                    Block.makeCuboidShape(6.5, 10, 5.25, 7.5, 11.75, 7),
                    Block.makeCuboidShape(13.5, 10, 5.25, 14.5, 11.75, 7),
                    Block.makeCuboidShape(10, 4.5, 5.25, 11, 6.25, 7),
                    Block.makeCuboidShape(2.75, 12.5, 1.25, 4.5, 13.5, 3),
                    Block.makeCuboidShape(9.75, 12.5, 1.25, 11.5, 13.5, 3),
                    Block.makeCuboidShape(6.25, 7, 1.25, 8, 8, 3),
                    Block.makeCuboidShape(4.5, 7.5, 1.25, 6.25, 8.5, 3),
                    Block.makeCuboidShape(11.5, 7.5, 1.25, 13.25, 8.5, 3),
                    Block.makeCuboidShape(8, 2, 1.25, 9.75, 3, 3),
                    Block.makeCuboidShape(6.5, 10.5, 1.25, 7.5, 12.25, 3),
                    Block.makeCuboidShape(13.5, 10.5, 1.25, 14.5, 12.25, 3),
                    Block.makeCuboidShape(10, 5, 1.25, 11, 6.75, 3),
                    Block.makeCuboidShape(1.5, 10.25, 12.75, 2.5, 12.25, 14.75),
                    Block.makeCuboidShape(8.5, 10.25, 12.75, 9.5, 12.25, 14.75),
                    Block.makeCuboidShape(5, 4.75, 12.75, 6, 6.75, 14.75),
                    Block.makeCuboidShape(1.5, 9.75, 9, 2.5, 11.75, 11),
                    Block.makeCuboidShape(8.5, 9.75, 9, 9.5, 11.75, 11),
                    Block.makeCuboidShape(5, 4.25, 9, 6, 6.25, 11),
                    Block.makeCuboidShape(1.5, 9.25, 5, 2.5, 11.25, 7),
                    Block.makeCuboidShape(8.5, 9.25, 5, 9.5, 11.25, 7),
                    Block.makeCuboidShape(5, 3.75, 5, 6, 5.75, 7),
                    Block.makeCuboidShape(1.5, 8.75, 1.25, 2.5, 10.75, 3.25),
                    Block.makeCuboidShape(8.5, 8.75, 1.25, 9.5, 10.75, 3.25),
                    Block.makeCuboidShape(5, 3.25, 1.25, 6, 5.25, 3.25),
                    Block.makeCuboidShape(6, 3, 1, 10, 7, 15),
                    Block.makeCuboidShape(2, 0, 3, 14, 0, 8),
                    Block.makeCuboidShape(2, 0, 8, 14, 0, 13)
            ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get(),
           // south crush 2
            Stream.of(
                    Block.makeCuboidShape(1, 2, 15, 15, 17, 16),
                    Block.makeCuboidShape(15, 2, 0, 16, 17, 16),
                    Block.makeCuboidShape(0, 2, 0, 1, 17, 16),
                    Block.makeCuboidShape(1, 2, 0, 15, 17, 1),
                    Block.makeCuboidShape(0, 0, 0, 2, 2, 16),
                    Block.makeCuboidShape(14, 0, 0, 16, 2, 16),
                    Block.makeCuboidShape(2, 0, 0, 14, 2, 3),
                    Block.makeCuboidShape(2, 0, 13, 14, 2, 16),
                    Block.makeCuboidShape(9.5, 8.5, 1, 13.5, 12.5, 15),
                    Block.makeCuboidShape(2.5, 8.5, 1, 6.5, 12.5, 15),
                    Block.makeCuboidShape(9.75, 12.5, 1.5, 11.5, 13.5, 3.25),
                    Block.makeCuboidShape(2.75, 12.5, 1.5, 4.5, 13.5, 3.25),
                    Block.makeCuboidShape(5, 3.25, 1.5, 6, 5.25, 3.25),
                    Block.makeCuboidShape(11.5, 7.5, 1.5, 13.25, 8.5, 3.25),
                    Block.makeCuboidShape(4.5, 7.5, 1.5, 6.25, 8.5, 3.25),
                    Block.makeCuboidShape(10, 5.25, 1.5, 11, 6.79412, 3.25),
                    Block.makeCuboidShape(8.5, 9, 1.5, 9.5, 10.75, 3.25),
                    Block.makeCuboidShape(1.5, 9, 1.5, 2.5, 10.75, 3.25),
                    Block.makeCuboidShape(7.7647, 2.25, 1.5, 9.5147, 3.25, 3.25),
                    Block.makeCuboidShape(10.25, 12.5, 5, 12, 13.5, 6.75),
                    Block.makeCuboidShape(3.25, 12.5, 5, 5, 13.5, 6.75),
                    Block.makeCuboidShape(5, 3.79412, 5, 6, 5.54412, 6.75),
                    Block.makeCuboidShape(11, 7.5, 5, 12.75, 8.5, 6.75),
                    Block.makeCuboidShape(4, 7.5, 5, 5.75, 8.5, 6.75),
                    Block.makeCuboidShape(10, 4.54412, 5, 11, 6.25, 6.75),
                    Block.makeCuboidShape(8.5, 9.5, 5, 9.5, 11.25, 6.75),
                    Block.makeCuboidShape(1.5, 9.5, 5, 2.5, 11.25, 6.75),
                    Block.makeCuboidShape(7.2647, 2.25, 5, 9, 3.25, 6.75),
                    Block.makeCuboidShape(10.75, 12.5, 9, 12.5, 13.5, 10.75),
                    Block.makeCuboidShape(3.75, 12.5, 9, 5.5, 13.5, 10.75),
                    Block.makeCuboidShape(5, 4.25, 9, 6, 6.25, 10.75),
                    Block.makeCuboidShape(10.25, 7.5, 9, 12, 8.5, 10.75),
                    Block.makeCuboidShape(3.25, 7.5, 9, 5, 8.5, 10.75),
                    Block.makeCuboidShape(10, 3.79412, 9, 11, 5.54412, 10.75),
                    Block.makeCuboidShape(8.5, 10, 9, 9.5, 11.75, 10.75),
                    Block.makeCuboidShape(1.5, 10, 9, 2.5, 11.75, 10.75),
                    Block.makeCuboidShape(6.7647, 2.25, 9, 8.5147, 3.25, 10.75),
                    Block.makeCuboidShape(11.5, 12.5, 13, 13.25, 13.5, 14.75),
                    Block.makeCuboidShape(4.5, 12.5, 13, 6.25, 13.5, 14.75),
                    Block.makeCuboidShape(5, 5.25, 13, 6, 6.79412, 14.75),
                    Block.makeCuboidShape(9.75, 7.5, 13, 11.5, 8.5, 14.75),
                    Block.makeCuboidShape(2.75, 7.5, 13, 4.5, 8.5, 14.75),
                    Block.makeCuboidShape(10, 3.25, 13, 11, 5.25, 14.75),
                    Block.makeCuboidShape(8.5, 10.5, 13, 9.5, 12.25, 14.75),
                    Block.makeCuboidShape(1.5, 10.5, 13, 2.5, 12.25, 14.75),
                    Block.makeCuboidShape(6.2647, 2.25, 13, 8, 3.25, 14.75),
                    Block.makeCuboidShape(13.5, 10.25, 1.25, 14.5, 12.25, 3.25),
                    Block.makeCuboidShape(6.5, 10.25, 1.25, 7.5, 12.25, 3.25),
                    Block.makeCuboidShape(6.2647, 7.25, 1.25, 8.2647, 8.25, 3.25),
                    Block.makeCuboidShape(13.5, 9.75, 5, 14.5, 11.75, 7),
                    Block.makeCuboidShape(6.5, 9.75, 5, 7.5, 11.75, 7),
                    Block.makeCuboidShape(6.7647, 7.25, 5, 8.7647, 8.25, 7),
                    Block.makeCuboidShape(13.5, 9.25, 9, 14.5, 11.25, 11),
                    Block.makeCuboidShape(6.5, 9.25, 9, 7.5, 11.25, 11),
                    Block.makeCuboidShape(7.2647, 7.25, 9, 9.2647, 8.25, 11),
                    Block.makeCuboidShape(13.5, 8.75, 12.75, 14.5, 10.75, 14.75),
                    Block.makeCuboidShape(6.5, 8.75, 12.75, 7.5, 10.75, 14.75),
                    Block.makeCuboidShape(7.7647, 7.25, 12.75, 9.7647, 8.25, 14.75),
                    Block.makeCuboidShape(6, 3.25, 1, 10, 7.25, 15),
                    Block.makeCuboidShape(2, 0, 8, 14, 0, 13),
                    Block.makeCuboidShape(2, 0, 3, 14, 0, 8)
            ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get()
    };



    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {

        if(state.get(TOOTH) == 0)
        {
            if(state.get(FACING) == Direction.NORTH || state.get(FACING) == Direction.SOUTH)
            {
                return ALLTHESHAPES[4];
            }
            else if(state.get(FACING) == Direction.WEST || state.get(FACING) == Direction.EAST)
            {
                return ALLTHESHAPES[5];
            }
        }
        else if(state.get(FACING)==Direction.EAST && state.get(CRUSH)==1)
        {
            return ALLTHESHAPES[6];
        }
        else if(state.get(FACING)==Direction.WEST&&state.get(CRUSH)==1)
        {
            return ALLTHESHAPES[7];
        }
        else if(state.get(FACING)==Direction.NORTH&&state.get(CRUSH)==1)
        {
            return ALLTHESHAPES[8];
        }
        else if(state.get(FACING)==Direction.SOUTH&&state.get(CRUSH)==1)
        {
            return ALLTHESHAPES[9];
        }
        else if(state.get(FACING)==Direction.EAST && state.get(CRUSH)==2)
        {
            return ALLTHESHAPES[10];
        }
        else if(state.get(FACING)==Direction.WEST&&state.get(CRUSH)==2)
        {
            return ALLTHESHAPES[11];
        }
        else if(state.get(FACING)==Direction.NORTH&&state.get(CRUSH)==2)
        {
            return ALLTHESHAPES[12];
        }
        else if(state.get(FACING)==Direction.SOUTH&&state.get(CRUSH)==2)
        {
            return ALLTHESHAPES[13];
        }
        else if(state.get(FACING)==Direction.EAST)
        {
            return ALLTHESHAPES[2];
        }
        else if(state.get(FACING)==Direction.WEST)
        {
            return ALLTHESHAPES[3];
        }
        else if(state.get(FACING)==Direction.NORTH)
        {
            return ALLTHESHAPES[0];
        }
        else if(state.get(FACING)==Direction.SOUTH)
        {
            return ALLTHESHAPES[1];
        }
        return ALLTHESHAPES[5];
    }


    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new CrusherTile().getTileEntity();
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.getDefaultState().with(FACING, context.getPlacementHorizontalFacing().getOpposite())
                .with(CRUSH, 0).with(OC, false).with(TOOTH,0);
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
        if (stateIn.get(OC) && stateIn.get(CRUSH) == 0) {





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
