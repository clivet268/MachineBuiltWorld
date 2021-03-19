package com.Clivet268.MachineBuiltWorld.blocks;

import com.Clivet268.MachineBuiltWorld.state.MoreStateProperties;
import com.Clivet268.MachineBuiltWorld.state.WireDirectionStates;
import com.Clivet268.MachineBuiltWorld.tileentity.AbstractWireTile;
import com.Clivet268.MachineBuiltWorld.tileentity.WireTile;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;

public class Wire extends Block {
    private static final VoxelShape[] DASHEPS =
            {
                    Block.makeCuboidShape(5, 0, 0, 11, 4, 16),
                    Block.makeCuboidShape(5, 0, 0, 11, 4, 16),
                    Block.makeCuboidShape(5, 0, 0, 11, 4, 16),
                    Block.makeCuboidShape(5, 0, 0, 11, 4, 16),
                    Block.makeCuboidShape(5, 0, 0, 11, 4, 16),
                    Block.makeCuboidShape(5, 0, 0, 11, 4, 16),
                    Block.makeCuboidShape(5, 0, 0, 11, 4, 16),
                    Block.makeCuboidShape(5, 0, 0, 11, 4, 16),
                    Block.makeCuboidShape(5, 0, 0, 11, 4, 16),
                    Block.makeCuboidShape(5, 0, 0, 11, 4, 16),
                    Block.makeCuboidShape(5, 0, 0, 11, 4, 16),
                    Block.makeCuboidShape(5, 0, 0, 11, 4, 16),
                    Block.makeCuboidShape(5, 0, 0, 11, 4, 16)
            };
    AbstractWireTile te;
    public static final EnumProperty<WireDirectionStates> DIRS = MoreStateProperties.WIREDIR;
    Direction ff;
    Direction bf;
    int laglimiter = 0;
    public Wire() {
        super(Block.Properties.create(Material.IRON)
                .hardnessAndResistance(1.0f, 1.0f)
                .sound(SoundType.BAMBOO)
                .harvestLevel(0));
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        switch(state.get(DIRS).getName()) {
            case ("ns"):
                return DASHEPS[0];
            case ("ne"):
                return DASHEPS[1];
            case ("nw"):
                return DASHEPS[2];
            case ("ws"):
                return DASHEPS[3];
            case ("we"):
                return DASHEPS[4];
            case ("wn"):
                return DASHEPS[5];
            case ("sw"):
                return DASHEPS[6];
            case ("se"):
                return DASHEPS[7];
            case ("sn"):
                return DASHEPS[8];
            case ("ew"):
                return DASHEPS[9];
            case ("es"):
                return DASHEPS[10];
            case ("en"):
                return DASHEPS[11];
            case ("none"):
                return DASHEPS[12];
            default:
                return DASHEPS[13];
        }
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    public Direction getFFD(BlockState statee){
        System.out.println(35);
        System.out.println(statee.get(DIRS).getName());
        switch(statee.get(DIRS).getName().charAt(0)) {
            case ('e'):
                return  Direction.EAST;
            case ('w'):
                return Direction.WEST;
            case ('s'):
                return Direction.SOUTH;
            case ('n'):
                return Direction.NORTH;
            default:
                return Direction.EAST;
        }
    }
    public Direction getBFD(){
        System.out.println(36);
        switch(DIRS.getName().charAt(1)) {
            case ('e'):
                return Direction.EAST;
            case ('w'):
                return Direction.WEST;
            case ('s'):
                return Direction.SOUTH;
            case ('n'):
                return Direction.NORTH;
            default:
                return Direction.EAST;
        }
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(DIRS);
    }

    /*@OnlyIn(Dist.CLIENT)
    public void tick(World worldin)
    {
        System.out.println(this.ff +" " + this.bf);
    }

     */

    public void setAdeDirections()
    {
        System.out.println(34);
        switch(DIRS.getName().charAt(0)) {
            case ('e'):
                this.ff = Direction.EAST;
                break;
            case ('w'):
                this.ff = Direction.WEST;
                break;
            case ('s'):
                this.ff = Direction.SOUTH;
                break;
            case ('n'):
                this.ff = Direction.NORTH;
            default:
        }
        switch(DIRS.getName().charAt(1)) {
            case ('e'):
                this.bf = Direction.EAST;
                break;
            case ('w'):
                this.bf = Direction.WEST;
                break;
            case ('s'):
                this.bf = Direction.SOUTH;
                break;
            case ('n'):
                this.bf = Direction.NORTH;
            default:
        }
    }



    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new WireTile().getTileEntity();
    }


    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        //this.setAdeDirections(context.getNearestLookingDirection().getOpposite(), context.getNearestLookingDirection());
        if(context.getNearestLookingDirection().getOpposite() == Direction.EAST) {
            return getDefaultState().with(DIRS, WireDirectionStates.EW);
        }
        else if(context.getNearestLookingDirection().getOpposite() == Direction.WEST) {
            return getDefaultState().with(DIRS, WireDirectionStates.WE);
        }
        else if(context.getNearestLookingDirection().getOpposite() == Direction.NORTH) {
            return getDefaultState().with(DIRS, WireDirectionStates.NS);
        }
        else if(context.getNearestLookingDirection().getOpposite() == Direction.SOUTH) {
            return getDefaultState().with(DIRS, WireDirectionStates.SN);
        }
        else{
            return getDefaultState().with(DIRS, WireDirectionStates.EW);
        }
    }

}
