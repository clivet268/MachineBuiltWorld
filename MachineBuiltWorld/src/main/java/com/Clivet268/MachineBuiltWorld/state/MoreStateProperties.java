package com.Clivet268.MachineBuiltWorld.state;

import net.minecraft.state.BooleanProperty;
import net.minecraft.state.IntegerProperty;

public class MoreStateProperties {
    public static final BooleanProperty ONOROFF = BooleanProperty.create("onoroff");
    public static final IntegerProperty MILLOAS = IntegerProperty.create("milloas", 0, 2);
    public static final IntegerProperty MIXEROAS = IntegerProperty.create("mixeroas", 0, 2);
    public static final IntegerProperty FILLED = IntegerProperty.create("filled",0,3);
    public static final BooleanProperty CRUSHCONTAIN = BooleanProperty.create("crushcontain");
    public static final BooleanProperty OPENCLOSE = BooleanProperty.create("openclose");
    public static final IntegerProperty CRUSHING = IntegerProperty.create("crushing", 0 ,2);
    //public static final EnumProperty<MillBit> MILL_BIT = EnumProperty.create("mill_bit", MillBit.class);
    //public static final IntegerProperty STAGE_0_1 = IntegerProperty.create("stage", 0, 1);
}
