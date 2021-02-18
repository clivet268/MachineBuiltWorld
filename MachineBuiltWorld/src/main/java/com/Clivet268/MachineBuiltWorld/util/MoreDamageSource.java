package com.Clivet268.MachineBuiltWorld.util;

import com.Clivet268.MachineBuiltWorld.entity.AbstractBulletEntity;
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
}
