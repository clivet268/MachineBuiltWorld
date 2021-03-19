package com.Clivet268.MachineBuiltWorld.tileentity;

import com.Clivet268.MachineBuiltWorld.blocks.Wire;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;

public abstract class AbstractWireTile extends TileEntity implements ITickableTileEntity {

    Direction[] direcs = {Direction.EAST, Direction.WEST};
    private boolean powered = false;
    int laglimiter = 0;

    public AbstractWireTile(TileEntityType<?> tileTypeIn) {
        super(tileTypeIn);
    }


    public AbstractWireTile(TileEntityType<?> tileTypeIn, Direction directionIn, Direction direction1In) {
        super(tileTypeIn);
    }
    public void setTheDirections(Direction d1, Direction d2)
    {
        this.direcs[0] = d1;
        this.direcs[1] = d2;
    }


    @Override
    public void tick() {
        if (world.isRemote) {
            return;
        }
        if (laglimiter < 6) {
            laglimiter++;
        } else {
            Wire wire = ((Wire) world.getBlockState(pos).getBlock());
            System.out.println(wire.getFFD(world.getBlockState(pos)));
            setTheDirections(wire.getFFD(world.getBlockState(pos)),wire.getBFD());
            laglimiter=0;
        }
    }




    public boolean checkConnector(TileEntity plug, Direction d){
       return plug instanceof PlugTile;
    }
    public boolean getPow()
    {
        return this.powered;
    }
    public void setPow(boolean i)
    {
        this.powered = i;
    }

    public Direction getFFD()
    {
        return this.direcs[0];
    }
    public Direction getBFD()
    {
        return this.direcs[1];
    }

}