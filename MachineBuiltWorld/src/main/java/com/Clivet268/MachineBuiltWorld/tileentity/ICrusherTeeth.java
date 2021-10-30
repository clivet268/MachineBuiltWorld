package com.Clivet268.MachineBuiltWorld.tileentity;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public interface ICrusherTeeth {
        @OnlyIn(Dist.CLIENT)
        float getLidAngle(float partialTicks);

}
