package com.Clivet268.MachineBuiltWorld.tileentity;

import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;

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
