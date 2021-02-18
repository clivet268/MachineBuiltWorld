package com.Clivet268.MachineBuiltWorld.tileentity;

import net.minecraft.block.BlockState;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;

import static com.Clivet268.MachineBuiltWorld.util.RegistryHandler.FIBERGLASS_MOULD_TILE;
public class FiberglassMouldTile extends TileEntity implements ITickableTileEntity {

    public FiberglassMouldTile() {
        super(FIBERGLASS_MOULD_TILE.get());


    }

    @Override
    public void tick() {
        if (world.isRemote) {
            return;
        }
        BlockState blockState = world.getBlockState(pos);
    }

}
