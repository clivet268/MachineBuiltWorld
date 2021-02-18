package com.Clivet268.MachineBuiltWorld.tileentity;

import net.minecraft.block.BlockState;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import static com.Clivet268.MachineBuiltWorld.util.RegistryHandler.SMOKE_DETECTOR_TILE;

public class SmokeDetectorTile extends TileEntity implements ITickableTileEntity {

    public SmokeDetectorTile() {
        super(SMOKE_DETECTOR_TILE.get());


    }

    @Override
    public void tick() {
        if (world.isRemote) {
            return;
        }
        BlockState blockState = world.getBlockState(pos);
    }


    public void detect(World world, BlockPos pos) {
        //System.out.print("??");
        TileEntity tileEntity = world.getTileEntity(pos);
        if (tileEntity instanceof SmokeDetectorTile) {

        }

    }
}
