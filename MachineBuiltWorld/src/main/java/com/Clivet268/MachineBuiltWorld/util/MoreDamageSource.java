package com.Clivet268.MachineBuiltWorld.util;

import com.Clivet268.MachineBuiltWorld.entity.AbstractBulletEntity;
import com.Clivet268.MachineBuiltWorld.entity.AbstractGearEntity;
import com.Clivet268.MachineBuiltWorld.entity.AbstractLaserEntity;
import com.Clivet268.MachineBuiltWorld.entity.AbstractSickShotEntity;
import net.minecraft.entity.Entity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IndirectEntityDamageSource;

import javax.annotation.Nullable;

public class MoreDamageSource extends DamageSource {
    //public static final MoreDamageSource BULLET = (new MoreDamageSource("inFire")).setFireDamage();
    public MoreDamageSource(String damageTypeIn) {
        super(damageTypeIn);
    }
    public static DamageSource causeBulletDamage(AbstractBulletEntity bullet, @Nullable Entity indirectEntityIn) {
        return (new IndirectEntityDamageSource("bullet", bullet, indirectEntityIn)).setProjectile();
    }
    public static DamageSource causeLaserDamage(AbstractLaserEntity laser, @Nullable Entity indirectEntityIn) {
        return (new IndirectEntityDamageSource("laser", laser, indirectEntityIn)).setProjectile().setFireDamage();
    }
    public static DamageSource causeCannonBallDamage(AbstractGearEntity laser, @Nullable Entity indirectEntityIn) {
        return (new IndirectEntityDamageSource("gear", laser, indirectEntityIn)).setProjectile();
    }
    public static DamageSource causeCannonBallDamage(AbstractSickShotEntity laser, @Nullable Entity indirectEntityIn) {
        return (new IndirectEntityDamageSource("cannon_ball", laser, indirectEntityIn)).setProjectile();
    }
}
