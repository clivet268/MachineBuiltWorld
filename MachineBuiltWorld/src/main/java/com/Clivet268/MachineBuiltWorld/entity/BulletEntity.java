package com.Clivet268.MachineBuiltWorld.entity;

import com.Clivet268.MachineBuiltWorld.util.RegistryHandler;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.World;

public class BulletEntity extends AbstractBulletEntity {
    //private static final DataParameter<Integer> COLOR = EntityDataManager.createKey(BulletEntity.class, DataSerializers.VARINT);
    //private Potion potion = Potions.EMPTY;
    //private final Set<EffectInstance> customPotionEffects = Sets.newHashSet();
    private boolean fixedColor;

    public BulletEntity(EntityType<BulletEntity> type, World worldIn) {
        super(type, worldIn);
    }

    public BulletEntity(World worldIn, double x, double y, double z) {
        super(RegistryHandler.BULLET_ENTITY.get(), x, y, z, worldIn);
    }

    public BulletEntity(World worldIn, LivingEntity shooter) {
        super(RegistryHandler.BULLET_ENTITY.get(), shooter, worldIn);
    }

    protected void registerData() {
        super.registerData();
        //this.dataManager.register(COLOR, -1);
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void tick() {
        super.tick();
        if (!this.world.isRemote &&this.inGround && this.timeInGround != 0 && this.timeInGround >= 600) {
            this.world.setEntityState(this, (byte)0);
        }

    }
    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */

    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);

    }


    protected void bulletHit(LivingEntity living) {
        super.bulletHit(living);

        }



    protected ItemStack getBulletStack() {
            return new ItemStack(RegistryHandler.LASER_SHELL.get());
    }
}

