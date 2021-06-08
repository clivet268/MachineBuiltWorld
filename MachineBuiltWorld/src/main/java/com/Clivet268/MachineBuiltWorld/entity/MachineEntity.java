package com.Clivet268.MachineBuiltWorld.entity;

import com.Clivet268.MachineBuiltWorld.entity.ai.StationGoal;
import net.minecraft.entity.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.server.management.PreYggdrasilConverter;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.UUID;

public abstract class MachineEntity extends CreatureEntity {

    protected static final DataParameter<Byte> ALLEGIANCE = EntityDataManager.createKey(MachineEntity.class, DataSerializers.BYTE);
    protected static final DataParameter<Optional<UUID>> OWNER_UUID = EntityDataManager.createKey(MachineEntity.class, DataSerializers.OPTIONAL_UNIQUE_ID);
    private boolean isWet;
    protected StationGoal sitGoal;
    byte e;

    public MachineEntity(EntityType<? extends MachineEntity> type, World worldIn) {
        super(type, worldIn);
        this.setupTamedAI();
    }

    protected void registerData() {
        super.registerData();
        this.dataManager.register(ALLEGIANCE, (byte) e);
        this.dataManager.register(OWNER_UUID, Optional.empty());
    }

    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        if (this.getAlignedId() == null) {
            compound.putString("AlignedLeaderUUID", "");
        } else {
            compound.putString("AlignedLeaderUUID", this.getAlignedId().toString());
        }

        compound.putBoolean("Sitting", this.isSitting());
    }

    public boolean isSitting() {
        return (this.dataManager.get(ALLEGIANCE)) >=3;
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        String s;
        if (compound.contains("AlignedLeaderUUID", 8)) {
            s = compound.getString("AlignedLeaderUUID");
        } else {
            String s1 = compound.getString("Owner");
            s = PreYggdrasilConverter.convertMobOwnerIfNeeded(this.getServer(), s1);
        }

        if (!s.isEmpty()) {
            try {
                this.setAlignedId(UUID.fromString(s));
                this.setTamed(true);
            } catch (Throwable var4) {
                this.setTamed(false);
            }
        }

        if (this.sitGoal != null) {
            this.sitGoal.setSitting(compound.getBoolean("Sitting"));
        }

        this.setStationed(compound.getBoolean("Sitting"));
    }

    public void setStationed(boolean sitting) {
        byte b0 = (Byte)this.dataManager.get(ALLEGIANCE);
        if (sitting) {
            this.dataManager.set(ALLEGIANCE, (byte)(this.dataManager.get(ALLEGIANCE) - 3));
        } else {
            this.dataManager.set(ALLEGIANCE, (byte)(b0 & -2));
        }

    }

    public boolean shouldAttackEntity(LivingEntity target, LivingEntity owner) {
        return true;
    }

    public int getAllegiance() {
        return ((Byte)this.dataManager.get(ALLEGIANCE));
    }

    public boolean isAligned() {
        return ((Byte)this.dataManager.get(ALLEGIANCE)) == 1 || this.dataManager.get(ALLEGIANCE) == 4;

    }
    public boolean isMaligned() {
        return ((Byte)this.dataManager.get(ALLEGIANCE)) == 3 || this.dataManager.get(ALLEGIANCE) == 6;

    }
    public boolean isAlignedLeader(LivingEntity entityIn) {
        return entityIn == this.getOwner();
    }

    /**
     * Called when the mob's health reaches 0.
     */
    public void onDeath(DamageSource cause) {
        this.isWet = false;
        super.onDeath(cause);
    }



    protected float getStandingEyeHeight(Pose poseIn, EntitySize sizeIn) {
        return sizeIn.height * 0.8F;
    }

    /**
     * Handler for {@link World#setEntityState}
     */
    @OnlyIn(Dist.CLIENT)
    public void handleStatusUpdate(byte id) {
        if (id == 8) {
        } else {
            super.handleStatusUpdate(id);
        }

    }
    public void setAlignedLeader(Entity player) {
        this.setTamed(true);
        this.setAlignedId(player.getUniqueID());

    }

    public void setAllegiance(byte allegiance){
    this.dataManager.set(ALLEGIANCE, allegiance);
    }

    /**
     * Will return how many at most can spawn in a chunk at once.
     */
    public int getMaxSpawnedInChunk() {
        return 100;
    }


    protected void setupTamedAI() {
    }

    @Nullable
    public UUID getAlignedId() {
        return this.dataManager.get(OWNER_UUID).orElse((UUID) null);
    }

    public void setAlignedId(@Nullable UUID p_184754_1_) {
        this.dataManager.set(OWNER_UUID, Optional.ofNullable(p_184754_1_));
    }

    public void setTamed(boolean tamed) {
        byte b0 = this.dataManager.get(ALLEGIANCE);
        if (tamed) {
            this.dataManager.set(ALLEGIANCE, (byte)(b0 | 1));
            this.setupTamedAI();
        } else {
        }
    }

    @Nullable
    public LivingEntity getOwner() {
        try {
            UUID uuid = this.getAlignedId();
            return uuid == null ? null : this.world.getPlayerByUuid(uuid);
        } catch (IllegalArgumentException var2) {
            return null;
        }
    }
}
