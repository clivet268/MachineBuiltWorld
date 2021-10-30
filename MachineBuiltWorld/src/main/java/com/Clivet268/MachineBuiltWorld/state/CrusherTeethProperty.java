package com.Clivet268.MachineBuiltWorld.state;

import net.minecraft.util.IStringSerializable;

public enum CrusherTeethProperty implements IStringSerializable {
        EMPTY("single", 0),
        LEFT("left", 2),
        RIGHT("right", 1);

        public static final CrusherTeethProperty[] VALUES = values();
        private final String name;
        private final int opposite;

        private CrusherTeethProperty(String name, int oppositeIn) {
            this.name = name;
            this.opposite = oppositeIn;
        }

        public String getName() {
            return this.name;
        }

        public CrusherTeethProperty opposite() {
            return VALUES[this.opposite];
        }

}
