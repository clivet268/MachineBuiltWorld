package com.Clivet268.MachineBuiltWorld.material;


import net.minecraft.block.material.MaterialColor;
import net.minecraft.block.material.PushReaction;

public class MoreMaterials{
    public static final MoreMaterials FIBERGLASSMOULD = (new MoreMaterials.MoreBuilder(MaterialColor.IRON)).notOpaque().pushDestroys().build();
    /** The color index used to draw the blocks of this material on maps. */
    private final MaterialColor color;
    /**
     * Mobility information flag. 0 indicates that this block is normal, 1 indicates that it can't push other blocks, 2
     * indicates that it can't be pushed.
     */
    private final PushReaction pushReaction;
    private final boolean blocksMovement;
    private final boolean flammable;
    private final boolean requiresNoTool;
    private final boolean isLiquid;
    private final boolean isOpaque;
    private final boolean replaceable;
    private final boolean isSolid;

    public MoreMaterials(MaterialColor materialMapColorIn, boolean liquid, boolean solid, boolean doesBlockMovement, boolean opaque, boolean requiresNoToolIn, boolean canBurnIn, boolean replaceableIn, PushReaction mobilityFlag) {
        this.color = materialMapColorIn;
        this.isLiquid = liquid;
        this.isSolid = solid;
        this.blocksMovement = doesBlockMovement;
        this.isOpaque = opaque;
        this.requiresNoTool = requiresNoToolIn;
        this.flammable = canBurnIn;
        this.replaceable = replaceableIn;
        this.pushReaction = mobilityFlag;
    }

    /**
     * Returns if blocks of these materials are liquids.
     */
    public boolean isLiquid() {
        return this.isLiquid;
    }

    /**
     * Returns true if the block is a considered solid. This is true by default.
     */
    public boolean isSolid() {
        return this.isSolid;
    }

    /**
     * Returns if this material is considered solid or not
     */
    public boolean blocksMovement() {
        return this.blocksMovement;
    }

    /**
     * Returns if the block can burn or not.
     */
    public boolean isFlammable() {
        return this.flammable;
    }

    /**
     * Returns whether the material can be replaced by other blocks when placed - eg snow, vines and tall grass.
     */
    public boolean isReplaceable() {
        return this.replaceable;
    }

    /**
     * Indicate if the material is opaque
     */
    public boolean isOpaque() {
        return this.isOpaque;
    }

    /**
     * Returns true if the material can be harvested without a tool (or with the wrong tool)
     */
    public boolean isToolNotRequired() {
        return this.requiresNoTool;
    }

    public PushReaction getPushReaction() {
        return this.pushReaction;
    }

    /**
     * Retrieves the color index of the block. This is is the same color used by vanilla maps to represent this block.
     */
    public MaterialColor getColor() {
        return this.color;
    }

    public static class MoreBuilder {
        private PushReaction pushReaction = PushReaction.NORMAL;
        private boolean blocksMovement = true;
        private boolean canBurn;
        private boolean requiresNoTool = true;
        private boolean isLiquid;
        private boolean isReplaceable;
        private boolean isSolid = true;
        private final MaterialColor color;
        private boolean isOpaque = true;

        public MoreBuilder(MaterialColor color) {
            this.color = color;
        }

        public MoreMaterials.MoreBuilder liquid() {
            this.isLiquid = true;
            return this;
        }

        public MoreMaterials.MoreBuilder notSolid() {
            this.isSolid = false;
            return this;
        }

        public MoreMaterials.MoreBuilder doesNotBlockMovement() {
            this.blocksMovement = false;
            return this;
        }

        private MoreMaterials.MoreBuilder notOpaque() {
            this.isOpaque = false;
            return this;
        }

        protected MoreMaterials.MoreBuilder requiresTool() {
            this.requiresNoTool = false;
            return this;
        }

        protected MoreMaterials.MoreBuilder flammable() {
            this.canBurn = true;
            return this;
        }

        public MoreMaterials.MoreBuilder replaceable() {
            this.isReplaceable = true;
            return this;
        }

        protected MoreMaterials.MoreBuilder pushDestroys() {
            this.pushReaction = PushReaction.DESTROY;
            return this;
        }

        protected MoreMaterials.MoreBuilder pushBlocks() {
            this.pushReaction = PushReaction.BLOCK;
            return this;
        }

        public MoreMaterials build() {
            return new MoreMaterials(this.color, this.isLiquid, this.isSolid, this.blocksMovement, this.isOpaque, this.requiresNoTool, this.canBurn, this.isReplaceable, this.pushReaction);
        }
    }
}
