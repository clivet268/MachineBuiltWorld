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
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;

//TODO model the wire in adaptive states
//TODO make texture
//TODO make it worky
public class Wire extends Block {
    AbstractWireTile te;
    public static final EnumProperty<WireDirectionStates> DIRS = MoreStateProperties.WIREDIR;
    Direction ff;
    Direction bf;

    public Wire() {
        super(Block.Properties.create(Material.IRON)
                .hardnessAndResistance(1.0f, 1.0f)
                .sound(SoundType.STONE)
                .harvestLevel(0));
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context)
    {
        return VoxelShapes.empty();
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
