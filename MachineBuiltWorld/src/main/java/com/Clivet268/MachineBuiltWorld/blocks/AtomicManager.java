package com.Clivet268.MachineBuiltWorld.blocks;

//import com.Clivet268.MachineBuiltWorld.tileentity.TileEntityAtomizer;
//import com.Clivet268.MachineBuiltWorld.init.ModTileEntityTypes;

import com.Clivet268.MachineBuiltWorld.inventory.Containers.AtomizerContainer;
import com.Clivet268.MachineBuiltWorld.tileentity.AtomizerTile;
import com.Clivet268.MachineBuiltWorld.tileentity.CrusherTile;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.properties.BlockStateProperties;
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
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.stream.Stream;

//TODO make texture
//TODO make states
public class AtomicManager extends Block {

    private BlockState defaultState;
    public static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;
    public static final BooleanProperty INVERTED = BlockStateProperties.INVERTED;


    public AtomicManager() {

        super(Properties.create(Material.IRON)
                .hardnessAndResistance(40.0f, 35.0f)
                .sound(SoundType.METAL)
                .harvestLevel(4)
                .harvestTool(ToolType.PICKAXE)
        );

    }

    private static final VoxelShape NORTH = Stream.of(
                    Block.makeCuboidShape(1, 0, 2, 15, 9, 14),
                    Block.makeCuboidShape(1, 7, 14, 15, 9, 15),
                    Block.makeCuboidShape(1, 7, 1, 15, 9, 2),
                    Block.makeCuboidShape(1, 0, 14, 15, 5, 15),
                    Block.makeCuboidShape(1, 0, 1, 15, 5, 2),
                    Block.makeCuboidShape(10, 5, 14, 15, 7, 15),
                    Block.makeCuboidShape(10, 5, 1, 15, 7, 2),
                    Block.makeCuboidShape(1, 5, 14, 6, 7, 15),
                    Block.makeCuboidShape(1, 5, 1, 6, 7, 2),
                    Block.makeCuboidShape(1, 14, 1, 15, 15, 15),
                    Block.makeCuboidShape(1, 9, 0, 15, 16, 1),
                    Block.makeCuboidShape(1, 9, 15, 15, 16, 16),
                    Block.makeCuboidShape(1, 0, 0, 15, 3, 1),
                    Block.makeCuboidShape(1, 0, 15, 15, 3, 16),
                    Block.makeCuboidShape(1, 3, 0, 5, 9, 1),
                    Block.makeCuboidShape(1, 3, 15, 5, 9, 16),
                    Block.makeCuboidShape(11, 3, 0, 15, 9, 1),
                    Block.makeCuboidShape(11, 3, 15, 15, 9, 16),
                    Block.makeCuboidShape(15, 0, 0, 16, 15, 16),
                    Block.makeCuboidShape(0, 0, 0, 1, 15, 16)
            ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get();


    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return NORTH;
    }



    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    public Direction getLeft(BlockState bs){
        switch(bs.get(FACING).getHorizontalIndex()){
            case 0:
                return Direction.EAST;
            case 1:
                return Direction.SOUTH;
            case 2:
                return Direction.WEST;
            case 3:
                return Direction.NORTH;
            default:
                return null;
        }
    }

    @Nullable
    public Direction getRight(BlockState bs){
        switch(bs.get(FACING).getHorizontalIndex()){
            case 0:
                return Direction.WEST;
            case 1:
                return Direction.NORTH;
            case 2:
                return Direction.EAST;
            case 3:
                return Direction.SOUTH;
            default:
                return null;
        }
    }


   @Nonnull
    @Override
   public TileEntity createTileEntity(BlockState state, IBlockReader world) {
     return new AtomizerTile().getTileEntity();
  }

   public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
       if (!worldIn.isRemote) {
           TileEntity tileEntity = worldIn.getTileEntity(pos);
           if (tileEntity instanceof CrusherTile) {
               INamedContainerProvider containerProvider = new INamedContainerProvider() {
                   @Override
                   public ITextComponent getDisplayName() {
                       return new TranslationTextComponent("screen.machinebuiltworld.atomizer");
                   }

                   @Override
                   public Container createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
                       return new AtomizerContainer(i, worldIn, pos ,playerInventory, playerEntity);
                   }
               };
               NetworkHooks.openGui((ServerPlayerEntity) player, containerProvider, tileEntity.getPos());
           } else {
               throw new IllegalStateException("Our named container provider is missing!");
           }
       }
       return ActionResultType.SUCCESS;
    }



}

