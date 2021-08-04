package com.Clivet268.MachineBuiltWorld.entity.ai;

import com.Clivet268.MachineBuiltWorld.entity.MachineEntity;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.TargetGoal;

import java.util.EnumSet;

public class AttackAgressorToAlignedLeaderGoal extends TargetGoal {
    private final MachineEntity tameable;
    private LivingEntity attacker;
    private int timestamp;
    private boolean agressiveornah;

    public AttackAgressorToAlignedLeaderGoal(MachineEntity theDefendingTameableIn, boolean agressive) {
        super(theDefendingTameableIn, false);
        this.tameable = theDefendingTameableIn;
        this.setMutexFlags(EnumSet.of(Goal.Flag.TARGET));
        this.agressiveornah = agressive;
    }

    /**
     * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
     * method as well.
     */
    public boolean shouldExecute() {
        if(this.agressiveornah) {
            if (this.tameable.isAligned() && !this.tameable.isSitting()) {
                LivingEntity livingentity = this.tameable.getOwner();
                if (livingentity == null) {
                    return false;
                } else {
                    this.attacker = livingentity.getRevengeTarget();
                    int i = livingentity.getRevengeTimer();
                    return i != this.timestamp && this.isSuitableTarget(this.attacker, EntityPredicate.DEFAULT) && this.tameable.shouldAttackEntity(this.attacker, livingentity);
                }
            } else {
                return false;
            }
        }
        else {
            return false;
        }
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting() {
        this.goalOwner.setAttackTarget(this.attacker);
        LivingEntity livingentity = this.tameable.getOwner();
        if (livingentity != null) {
            this.timestamp = livingentity.getRevengeTimer();
        }

        super.startExecuting();
    }
}

