package com.Clivet268.MachineBuiltWorld.state;

import net.minecraft.state.BooleanProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.properties.BlockStateProperties;

public class MoreStateProperties extends BlockStateProperties {
    public static final BooleanProperty ONOROFF = BooleanProperty.create("onoroff");
    public static final BooleanProperty UPORNOT = BooleanProperty.create("upornot");
    public static final IntegerProperty MILLOAS = IntegerProperty.create("milloas", 0, 2);
    public static final IntegerProperty MIXEROAS = IntegerProperty.create("mixeroas", 0, 2);
    public static final IntegerProperty FILLED = IntegerProperty.create("filled",0,3);
    public static final BooleanProperty FULLNOTFULL = BooleanProperty.create("fullnotfull");
    public static final BooleanProperty CRUSHCONTAIN = BooleanProperty.create("crushcontain");
    public static final EnumProperty<CrusherTeethProperty> TYPE = EnumProperty.create("type", CrusherTeethProperty.class);
    public static final IntegerProperty CRUSHING = IntegerProperty.create("crushing", 0 ,3);
    public static final IntegerProperty TOOTHSTATE = IntegerProperty.create("toothstate", 0 ,6);
    public static final IntegerProperty CRYSTILIZATIONSTATES = IntegerProperty.create("crystallization", 0,6);
    public static final EnumProperty<WireDirectionStates> WIREDIR = EnumProperty.create("wiredir",WireDirectionStates.class);

    /*public static final EnumProperty<CrystallizationChamberStates> CRYSTILIZATIONSTATES =
            EnumProperty.create("crystallization", CrystallizationChamberStates.class);

     */
    //public static final EnumProperty<MillBit> MILL_BIT = EnumProperty.create("mill_bit", MillBit.class);
    //public static final IntegerProperty STAGE_0_1 = IntegerProperty.create("stage", 0, 1);
}
