package com.Clivet268.MachineBuiltWorld.state;

import net.minecraft.util.IStringSerializable;

public enum MillBit implements IStringSerializable {
        REST("rest"),
        GO1("go1"),
        GO2("go2");

        private final String name;

        private MillBit(String name) {
            this.name = name;
        }

        public String toString() {
            return this.name;
        }

        public String getName() {
            return this.name;
        }

}
