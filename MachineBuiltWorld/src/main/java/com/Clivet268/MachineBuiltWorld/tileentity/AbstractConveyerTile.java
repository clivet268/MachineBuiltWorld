package com.Clivet268.MachineBuiltWorld.tileentity;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

import static com.Clivet268.MachineBuiltWorld.util.RegistryHandler.SMOKE_DETECTOR_TILE;

public abstract class AbstractConveyerTile extends TileEntity implements ITickableTileEntity {


    public AbstractConveyerTile(TileEntityType<?> tileTypeIn) {
            super(tileTypeIn);


        }

        @Override
        public void tick() {

            if (world.isRemote) {
                return;
            }


        }



}
