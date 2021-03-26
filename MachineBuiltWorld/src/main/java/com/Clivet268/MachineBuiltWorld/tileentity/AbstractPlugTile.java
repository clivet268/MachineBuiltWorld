package com.Clivet268.MachineBuiltWorld.tileentity;

import com.Clivet268.MachineBuiltWorld.blocks.Plug;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;

import static com.Clivet268.MachineBuiltWorld.Config.TTBATTERY_SEND;

public abstract class AbstractPlugTile extends TileEntity implements ITickableTileEntity {
    private Direction e = Direction.EAST;
    private int chainLength = 0;
    private Direction ee = e;
    private TileEntity teii;
    private TileEntity teiir;
    private boolean pLuGeD = false;
    public AbstractPlugTile(TileEntityType<?> tileTypeIn) {
        super(tileTypeIn);
    }
    @Override
    public void tick() {
        this.e = ((Plug)world.getBlockState(pos).getBlock()).disDiraction();
        TileEntity te = world.getTileEntity(pos.offset(e));
        boolean i = isWire(te);
        boolean flag = false;
        if(i) {
            while (i) {
                System.out.println("tis a wire " + te.getPos());
                Direction ffd = ((AbstractWireTile)te).getFFD();
                System.out.println(ffd);
                BlockPos posi = te.getPos();
                te = this.world.getTileEntity(posi.offset(ffd));
                i = isWire(te);
                if(te instanceof AbstractPlugTile){
                    flag = true;
                    setTileTorTransferTo((AbstractPlugTile)te);
                }
            }
        }
        this.pLuGeD = flag;
        if(this.pLuGeD)
        {
            transferOverLink();
        }

    }

    public void transferOverLink(){
        System.out.println(teii != null && teii instanceof EnergyHoldableTile && teiir instanceof EnergyHoldableTile);
        if (teii != null && teii instanceof EnergyHoldableTile && teiir instanceof EnergyHoldableTile) {
            int received = TTBATTERY_SEND.get();
            ((EnergyHoldableTile) teii).getEnergyS().consumeEnergy(received);
            ((EnergyHoldableTile) teiir).getEnergyS().addEnergy(received);
        }
    }

    public void setTileTorTransferTo(AbstractPlugTile plugy){
        if (!pLuGeD)
        {
            return;
        }
        this.teii = world.getTileEntity(pos.offset(e.getOpposite()));
        this.teiir = world.getTileEntity(plugy.getPos().offset(plugy.getDir().getOpposite()));
    }

    public boolean isWire(TileEntity wire, Direction d){
        return this.world.getTileEntity(wire.getPos().offset(d)) instanceof AbstractWireTile;
    }
    public boolean isWire(TileEntity wire){
        return wire instanceof AbstractWireTile;
    }
    public void setDir(Direction d){
        this.e = d;
    }

    public Direction getDir(){
        return e;
    }
    @Override
    public void remove() {
        super.remove();
    }
}
