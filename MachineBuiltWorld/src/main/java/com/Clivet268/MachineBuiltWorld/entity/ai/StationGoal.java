package com.Clivet268.MachineBuiltWorld.entity.ai;

import com.Clivet268.MachineBuiltWorld.entity.MachineEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;

import java.util.EnumSet;

public class StationGoal extends Goal {
    private final MachineEntity entity;
    private boolean isSitting;

    public StationGoal(MachineEntity entityIn) {
        this.entity = entityIn;
        this.setMutexFlags(EnumSet.of(Goal.Flag.JUMP, Goal.Flag.MOVE));
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean shouldContinueExecuting() {
        return this.isSitting;
    }

    /**
     * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
     * method as well.
     */
    public boolean shouldExecute() {
        if (!(this.entity.isAligned())) {
            return false;
        } else if (this.entity.isInWaterOrBubbleColumn()) {
            return false;
        } else if (!this.entity.onGround) {
            return false;
        } else {
            LivingEntity livingentity = this.entity.getOwner();
            if (livingentity == null) {
                return true;
            } else {
                return this.entity.getDistanceSq(livingentity) < 144.0D && livingentity.getRevengeTarget() != null ? false : this.isSitting;
            }
        }
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting() {
        this.entity.getNavigator().clearPath();
        this.entity.setStationed(true);
    }

    /**
     * Reset the task's internal state. Called when this task is interrupted by another one
     */
    public void resetTask() {
        this.entity.setStationed(false);
    }

    /**
     * Sets the sitting flag.
     */
    public void setSitting(boolean sitting) {
        this.isSitting = sitting;
    }
}

