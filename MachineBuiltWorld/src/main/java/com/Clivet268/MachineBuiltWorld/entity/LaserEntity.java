package com.Clivet268.MachineBuiltWorld.entity;

import com.Clivet268.MachineBuiltWorld.util.RegistryHandler;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class LaserEntity extends AbstractLaserEntity {
    //private static final DataParameter<Integer> COLOR = EntityDataManager.createKey(BulletEntity.class, DataSerializers.VARINT);
    //private Potion potion = Potions.EMPTY;
    //private final Set<EffectInstance> customPotionEffects = Sets.newHashSet();
    private boolean fixedColor;

    public LaserEntity(EntityType<LaserEntity> type, World worldIn) {
        super(type, worldIn);
    }

    public LaserEntity(World worldIn, double x, double y, double z) {
        super(RegistryHandler.LASER_ENTITY.get(), x, y, z, worldIn);
    }

    public LaserEntity(World worldIn, LivingEntity shooter) {
        super(RegistryHandler.LASER_ENTITY.get(), shooter, worldIn);
    }

    protected void registerData() {
        super.registerData();
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
            return new ItemStack(Items.AIR);
    }

    /**
     * Handler for {@link World#setEntityState}
     */
    @OnlyIn(Dist.CLIENT)
    public void handleStatusUpdate(byte id) {
        super.handleStatusUpdate(id);
    }
}

