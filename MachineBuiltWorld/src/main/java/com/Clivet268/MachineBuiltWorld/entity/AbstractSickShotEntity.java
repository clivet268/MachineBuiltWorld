package com.Clivet268.MachineBuiltWorld.entity;

import com.Clivet268.MachineBuiltWorld.util.MoreDamageSource;
import com.Clivet268.MachineBuiltWorld.util.RegistryHandler;
import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.play.server.SChangeGameStatePacket;
import net.minecraft.network.play.server.SSpawnObjectPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.*;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public abstract class AbstractSickShotEntity extends Entity implements IProjectile {
    private static final DataParameter<Byte> CRITICAL = EntityDataManager.createKey(AbstractSickShotEntity.class, DataSerializers.BYTE);
    protected static final DataParameter<Optional<UUID>> field_212362_a = EntityDataManager.createKey(AbstractSickShotEntity.class, DataSerializers.OPTIONAL_UNIQUE_ID);
    private static final DataParameter<Float> PIERCE_LEVEL = EntityDataManager.createKey(AbstractSickShotEntity.class, DataSerializers.FLOAT);
    @Nullable
    private BlockState inBlockState;
    protected boolean inGround;
    protected int timeInGround;
    public AbstractSickShotEntity.PickupStatus pickupStatus = AbstractSickShotEntity.PickupStatus.DISALLOWED;
    public int arrowShake;
    public UUID shootingEntity;
    private int ticksInGround;
    private int ticksInAir;
    private double damage = 2.0D;
    private int knockbackStrength;
    private SoundEvent hitSound = this.getHitEntitySound();
    private IntOpenHashSet piercedEntities;
    private List<Entity> hitEntities;

    protected AbstractSickShotEntity(EntityType<? extends AbstractSickShotEntity> type, World worldIn) {
        super(type, worldIn);
    }

    protected AbstractSickShotEntity(EntityType<? extends AbstractSickShotEntity> type, double x, double y, double z, World worldIn) {
        this(type, worldIn);
        this.setPosition(x, y, z);
    }

    protected AbstractSickShotEntity(EntityType<? extends AbstractSickShotEntity> type, LivingEntity shooter, World worldIn) {
        this(type, shooter.getPosX(), shooter.getPosYEye() - (double)0.1F, shooter.getPosZ(), worldIn);
        this.setShooter(shooter);
        if (shooter instanceof PlayerEntity) {
            this.pickupStatus = AbstractSickShotEntity.PickupStatus.ALLOWED;
        }

    }


    public void setHitSound(SoundEvent soundIn) {
        this.hitSound = soundIn;
    }

    /**
     * Checks if the entity is in range to render.
     */
    @OnlyIn(Dist.CLIENT)
    public boolean isInRangeToRenderDist(double distance) {
        double d0 = this.getBoundingBox().getAverageEdgeLength() * 10.0D;
        if (Double.isNaN(d0)) {
            d0 = 1.0D;
        }

        d0 = d0 * 64.0D * getRenderDistanceWeight();
        return distance < d0 * d0;
    }

    protected void registerData() {
        this.dataManager.register(CRITICAL, (byte)0);
        this.dataManager.register(field_212362_a, Optional.empty());
        this.dataManager.register(PIERCE_LEVEL, (float)0);
    }

    public void shoot(Entity shooter, float pitch, float yaw, float p_184547_4_, float velocity, float inaccuracy) {
        float f = -MathHelper.sin(yaw * ((float)Math.PI / 180F)) * MathHelper.cos(pitch * ((float)Math.PI / 180F));
        float f1 = -MathHelper.sin(pitch * ((float)Math.PI / 180F));
        float f2 = MathHelper.cos(yaw * ((float)Math.PI / 180F)) * MathHelper.cos(pitch * ((float)Math.PI / 180F));
        this.shoot((double)f, (double)f1, (double)f2, velocity, inaccuracy);
        this.setMotion(this.getMotion().add(shooter.getMotion().x, shooter.onGround ? 0.0D : shooter.getMotion().y, shooter.getMotion().z));
    }

    /**
     * Similar to setArrowHeading, it's point the throwable entity to a x, y, z direction.
     */
    public void shoot(double x, double y, double z, float velocity, float inaccuracy) {
        Vec3d vec3d = (new Vec3d(x, y, z)).normalize().add(this.rand.nextGaussian() * (double)0.0075F * (double)inaccuracy, this.rand.nextGaussian() * (double)0.0075F * (double)inaccuracy, this.rand.nextGaussian() * (double)0.0075F * (double)inaccuracy).scale((double)velocity);
        this.setMotion(vec3d);
        float f = MathHelper.sqrt(horizontalMag(vec3d));
        this.rotationYaw = (float)(MathHelper.atan2(vec3d.x, vec3d.z) * (double)(180F / (float)Math.PI));
        this.rotationPitch = (float)(MathHelper.atan2(vec3d.y, (double)f) * (double)(180F / (float)Math.PI));
        this.prevRotationYaw = this.rotationYaw;
        this.prevRotationPitch = this.rotationPitch;
        this.ticksInGround = 0;
    }

    /**
     * Sets a target for the client to interpolate towards over the next few ticks
     */
    @OnlyIn(Dist.CLIENT)
    public void setPositionAndRotationDirect(double x, double y, double z, float yaw, float pitch, int posRotationIncrements, boolean teleport) {
        this.setPosition(x, y, z);
        this.setRotation(yaw, pitch);
    }

    /**
     * Updates the entity motion clientside, called by packets from the server
     */
    @OnlyIn(Dist.CLIENT)
    public void setVelocity(double x, double y, double z) {
        this.setMotion(x, y, z);
        if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F) {
            float f = MathHelper.sqrt(x * x + z * z);
            this.rotationPitch = (float)(MathHelper.atan2(y, (double)f) * (double)(180F / (float)Math.PI));
            this.rotationYaw = (float)(MathHelper.atan2(x, z) * (double)(180F / (float)Math.PI));
            this.prevRotationPitch = this.rotationPitch;
            this.prevRotationYaw = this.rotationYaw;
            this.setLocationAndAngles(this.getPosX(), this.getPosY(), this.getPosZ(), this.rotationYaw, this.rotationPitch);
            this.ticksInGround = 0;
        }

    }

    /**
     * Called to update the entity's position/logic.
     */
    public void tick() {
        super.tick();
        boolean flag = this.getNoClip();
        Vec3d vec3d = this.getMotion();
        if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F) {
            float f = MathHelper.sqrt(horizontalMag(vec3d));
            this.rotationYaw = (float)(MathHelper.atan2(vec3d.x, vec3d.z) * (double)(180F / (float)Math.PI));
            this.rotationPitch = (float)(MathHelper.atan2(vec3d.y, (double)f) * (double)(180F / (float)Math.PI));
            this.prevRotationYaw = this.rotationYaw;
            this.prevRotationPitch = this.rotationPitch;
        }

        BlockPos blockpos = new BlockPos(this);
        BlockState blockstate = this.world.getBlockState(blockpos);
        if (!blockstate.isAir(this.world, blockpos) && !flag) {
            VoxelShape voxelshape = blockstate.getCollisionShape(this.world, blockpos);
            if (!voxelshape.isEmpty()) {
                Vec3d vec3d1 = this.getPositionVec();

                for(AxisAlignedBB axisalignedbb : voxelshape.toBoundingBoxList()) {
                    if (axisalignedbb.offset(blockpos).contains(vec3d1)) {
                        this.inGround = true;
                        break;
                    }
                }
            }
        }

        if (this.arrowShake > 0) {
            --this.arrowShake;
        }

        if (this.isWet()) {
            this.extinguish();
        }

        if (this.inGround && !flag) {
            if (this.inBlockState != blockstate && this.world.hasNoCollisions(this.getBoundingBox().grow(0.06D))) {
                this.inGround = false;
                this.setMotion(vec3d.mul((double)(this.rand.nextFloat() * 0.2F), (double)(this.rand.nextFloat() * 0.2F), (double)(this.rand.nextFloat() * 0.2F)));
                this.ticksInGround = 0;
                this.ticksInAir = 0;
            } else if (!this.world.isRemote) {
                this.func_225516_i_();
            }

            ++this.timeInGround;
        } else {
            this.timeInGround = 0;
            ++this.ticksInAir;
            Vec3d vec3d2 = this.getPositionVec();
            Vec3d vec3d3 = vec3d2.add(vec3d);
            RayTraceResult raytraceresult = this.world.rayTraceBlocks(new RayTraceContext(vec3d2, vec3d3, RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, this));
            if (raytraceresult.getType() != RayTraceResult.Type.MISS) {
                vec3d3 = raytraceresult.getHitVec();
            }

            while(!this.removed) {
                EntityRayTraceResult entityraytraceresult = this.rayTraceEntities(vec3d2, vec3d3);
                if (entityraytraceresult != null) {
                    raytraceresult = entityraytraceresult;
                }

                if (raytraceresult != null && raytraceresult.getType() == RayTraceResult.Type.ENTITY) {
                    Entity entity = ((EntityRayTraceResult)raytraceresult).getEntity();
                    Entity entity1 = this.getShooter();
                    if (entity instanceof PlayerEntity && entity1 instanceof PlayerEntity && !((PlayerEntity)entity1).canAttackPlayer((PlayerEntity)entity)) {
                        raytraceresult = null;
                        entityraytraceresult = null;
                    }
                }

                if (raytraceresult != null && raytraceresult.getType() != RayTraceResult.Type.MISS && !flag && !net.minecraftforge.event.ForgeEventFactory.onProjectileImpact(this, raytraceresult)) {
                    this.onHit(raytraceresult);
                    this.isAirBorne = true;
                }

                if (entityraytraceresult == null || this.getPierceLevel() <= 0) {
                    break;
                }

                raytraceresult = null;
            }

            vec3d = this.getMotion();
            double d3 = vec3d.x;
            double d4 = vec3d.y;
            double d0 = vec3d.z;
            if (this.getIsCritical()) {
                for(int i = 0; i < 4; ++i) {
                    this.world.addParticle(ParticleTypes.CRIT, this.getPosX() + d3 * (double)i / 4.0D, this.getPosY() + d4 * (double)i / 4.0D, this.getPosZ() + d0 * (double)i / 4.0D, -d3, -d4 + 0.2D, -d0);
                }
            }

            double d5 = this.getPosX() + d3;
            double d1 = this.getPosY() + d4;
            double d2 = this.getPosZ() + d0;
            float f1 = MathHelper.sqrt(horizontalMag(vec3d));
            if (flag) {
                this.rotationYaw = (float)(MathHelper.atan2(-d3, -d0) * (double)(180F / (float)Math.PI));
            } else {
                this.rotationYaw = (float)(MathHelper.atan2(d3, d0) * (double)(180F / (float)Math.PI));
            }

            for(this.rotationPitch = (float)(MathHelper.atan2(d4, (double)f1) * (double)(180F / (float)Math.PI)); this.rotationPitch - this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F) {
                ;
            }

            while(this.rotationPitch - this.prevRotationPitch >= 180.0F) {
                this.prevRotationPitch += 360.0F;
            }

            while(this.rotationYaw - this.prevRotationYaw < -180.0F) {
                this.prevRotationYaw -= 360.0F;
            }

            while(this.rotationYaw - this.prevRotationYaw >= 180.0F) {
                this.prevRotationYaw += 360.0F;
            }

            this.rotationPitch = MathHelper.lerp(0.2F, this.prevRotationPitch, this.rotationPitch);
            this.rotationYaw = MathHelper.lerp(0.2F, this.prevRotationYaw, this.rotationYaw);
            float f2 = 0.99F;
            float f3 = 0.05F;
            if (this.isInWater()) {
                for(int j = 0; j < 4; ++j) {
                    float f4 = 0.25F;
                    this.world.addParticle(ParticleTypes.BUBBLE, d5 - d3 * 0.25D, d1 - d4 * 0.25D, d2 - d0 * 0.25D, d3, d4, d0);
                }

                f2 = this.getWaterDrag();
            }

            this.setMotion(vec3d.scale((double)f2));
            if (!this.hasNoGravity() && !flag) {
                Vec3d vec3d4 = this.getMotion();
                this.setMotion(vec3d4.x, vec3d4.y - (double)0.05F, vec3d4.z);
            }

            this.setPosition(d5, d1, d2);
            this.doBlockCollisions();
        }
    }

    protected void func_225516_i_() {
        ++this.ticksInGround;
        if (this.ticksInGround >= 1200) {
            this.remove();
        }

    }

    /**
     * Called when the arrow hits a block or an entity
     */
    protected void onHit(RayTraceResult raytraceResultIn) {
        RayTraceResult.Type raytraceresult$type = raytraceResultIn.getType();
        if (raytraceresult$type == RayTraceResult.Type.ENTITY) {
            this.onEntityHit((EntityRayTraceResult)raytraceResultIn);
        } else if (raytraceresult$type == RayTraceResult.Type.BLOCK) {
            BlockRayTraceResult blockraytraceresult = (BlockRayTraceResult)raytraceResultIn;
            BlockState blockstate = this.world.getBlockState(blockraytraceresult.getPos());
            this.inBlockState = blockstate;
            Vec3d vec3d = blockraytraceresult.getHitVec().subtract(this.getPosX(), this.getPosY(), this.getPosZ());
            this.setMotion(vec3d);
            Vec3d vec3d1 = vec3d.normalize().scale((double)0.05F);
            this.setRawPosition(this.getPosX() - vec3d1.x, this.getPosY() - vec3d1.y, this.getPosZ() - vec3d1.z);
            this.playSound(this.getHitGroundSound(), 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
            this.inGround = true;
            this.arrowShake = 7;
            this.world.createExplosion(this,this.getPosX(),this.getPosY(),this.getPosZ(),10 , Explosion.Mode.BREAK);
            this.setPierceLevel((byte)0);
            this.setHitSound(RegistryHandler.BULLET_HIT.get());
            this.func_213870_w();
            blockstate.onProjectileCollision(this.world, blockstate, blockraytraceresult, this);
        }

    }

    private void func_213870_w() {
        if (this.hitEntities != null) {
            this.hitEntities.clear();
        }

        if (this.piercedEntities != null) {
            this.piercedEntities.clear();
        }

    }

    /**
     * Called when the arrow hits an entity
     */
    protected void onEntityHit(EntityRayTraceResult target) {
        Entity entity = target.getEntity();
        float f = (float)this.getMotion().length();
        int i = MathHelper.ceil(Math.max((double)f * this.damage, 0.0D));
        if (this.getPierceLevel() > 0) {
            if (this.piercedEntities == null) {
                this.piercedEntities = new IntOpenHashSet(5);
            }

            if (this.hitEntities == null) {
                this.hitEntities = Lists.newArrayListWithCapacity(5);
            }

            if (this.piercedEntities.size() >= this.getPierceLevel() + 1) {
                this.remove();
                return;
            }

            this.piercedEntities.add(entity.getEntityId());
        }

        if (this.getIsCritical()) {
            i += this.rand.nextInt(i / 2 + 2);
        }

        Entity entity1 = this.getShooter();
        DamageSource damagesource;
        if (entity1 == null) {
            damagesource = MoreDamageSource.causeCannonBallDamage(this, this);
        } else {
            damagesource = MoreDamageSource.causeCannonBallDamage(this, entity1);
            if (entity1 instanceof LivingEntity) {
                ((LivingEntity)entity1).setLastAttackedEntity(entity);
            }
        }

        boolean flag = entity.getType() == EntityType.ENDERMAN;
        int j = entity.getFireTimer();
        entity.setFire(1);


        if (entity.attackEntityFrom(damagesource, (float)i)) {
            if (flag) {
                return;
            }

            if (entity instanceof LivingEntity) {
                LivingEntity livingentity = (LivingEntity)entity;
                /*MoreLivingEntity specialEntity = new MoreLivingEntity((EntityType<? extends LivingEntity>) livingentity.getType(), entity.world.getWorld());
                if (!this.world.isRemote) {
                    specialEntity.setLaserBurnCountInEntity(specialEntity.getLaserBurnCountInEntity() + 1) ;
                }

                 */

                if (this.knockbackStrength > 0) {
                    Vec3d vec3d = this.getMotion().mul(1.0D, 0.0D, 1.0D).normalize().scale((double)this.knockbackStrength * 0.6D);
                    if (vec3d.lengthSquared() > 0.0D) {
                        livingentity.addVelocity(vec3d.x, 0.1D, vec3d.z);
                    }
                }

                if (!this.world.isRemote && entity1 instanceof LivingEntity) {
                    EnchantmentHelper.applyThornEnchantments(livingentity, entity1);
                    EnchantmentHelper.applyArthropodEnchantments((LivingEntity)entity1, livingentity);
                }

                this.bulletHit(livingentity);
                if (entity1 != null && livingentity != entity1 && (LivingEntity)livingentity instanceof PlayerEntity && entity1 instanceof ServerPlayerEntity) {
                    ((ServerPlayerEntity)entity1).connection.sendPacket(new SChangeGameStatePacket(6, 0.0F));
                }

                if (!entity.isAlive() && this.hitEntities != null) {
                    this.hitEntities.add(livingentity);
                }

            }

            this.playSound(this.hitSound, 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
            if (this.getPierceLevel() <= 0) {
                this.remove();
            }
        } else {
            entity.setFireTimer(2);
            this.setMotion(this.getMotion().scale(-0.1D));
            this.rotationYaw += 180.0F;
            this.prevRotationYaw += 180.0F;
            this.ticksInAir = 0;
            if (!this.world.isRemote && this.getMotion().lengthSquared() < 1.0E-7D) {
                this.remove();
            }
        }

    }

    /**
     * The sound made when an entity is hit by this projectile
     */
    protected SoundEvent getHitEntitySound() {
        return RegistryHandler.BULLET_HIT.get();
    }

    protected final SoundEvent getHitGroundSound() {
        return this.hitSound;
    }

    protected void bulletHit(LivingEntity living) {
    }

    /**
     * Gets the EntityRayTraceResult representing the entity hit
     */
    @Nullable
    protected EntityRayTraceResult rayTraceEntities(Vec3d startVec, Vec3d endVec) {
        return ProjectileHelper.rayTraceEntities(this.world, this, startVec, endVec, this.getBoundingBox().expand(this.getMotion()).grow(1.0D), (target) -> {
            return !target.isSpectator() && target.isAlive() && target.canBeCollidedWith() && (target != this.getShooter() || this.ticksInAir >= 5) && (this.piercedEntities == null || !this.piercedEntities.contains(target.getEntityId()));
        });
    }

    public void writeAdditional(CompoundNBT compound) {
        compound.putShort("life", (short)this.ticksInGround);
        if (this.inBlockState != null) {
            compound.put("inBlockState", NBTUtil.writeBlockState(this.inBlockState));
        }

        compound.putByte("shake", (byte)this.arrowShake);
        compound.putBoolean("inGround", this.inGround);
        compound.putByte("pickup", (byte)this.pickupStatus.ordinal());
        compound.putDouble("damage", this.damage);
        compound.putBoolean("crit", this.getIsCritical());
        compound.putFloat("PierceLevel", this.getPierceLevel());
        if (this.shootingEntity != null) {
            compound.putUniqueId("OwnerUUID", this.shootingEntity);
        }

        compound.putString("SoundEvent", Registry.SOUND_EVENT.getKey(this.hitSound).toString());
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readAdditional(CompoundNBT compound) {
        this.ticksInGround = compound.getShort("life");
        if (compound.contains("inBlockState", 10)) {
            this.inBlockState = NBTUtil.readBlockState(compound.getCompound("inBlockState"));
        }

        this.arrowShake = compound.getByte("shake") & 255;
        this.inGround = compound.getBoolean("inGround");
        if (compound.contains("damage", 99)) {
            this.damage = compound.getDouble("damage");
        }


            this.pickupStatus = PickupStatus.DISALLOWED;
        if (compound.contains("SoundEvent", 8)) {
            this.hitSound = Registry.SOUND_EVENT.getValue(new ResourceLocation(compound.getString("SoundEvent"))).orElse(this.getHitEntitySound());
        }

    }

    public void setShooter(@Nullable Entity entityIn) {
        this.shootingEntity = entityIn == null ? null : entityIn.getUniqueID();
        if (entityIn instanceof PlayerEntity) {
            this.pickupStatus = PickupStatus.DISALLOWED;
        }

    }

    @Nullable
    public Entity getShooter() {
        return this.shootingEntity != null && this.world instanceof ServerWorld ? ((ServerWorld)this.world).getEntityByUuid(this.shootingEntity) : null;
    }

    /**
     * Called by a player entity when they collide with an entity
     */
    public void onCollideWithPlayer(PlayerEntity entityIn) {
        if (!this.world.isRemote && (this.inGround || this.getNoClip()) && this.arrowShake <= 0) {
            boolean flag = this.pickupStatus == AbstractSickShotEntity.PickupStatus.ALLOWED || this.pickupStatus == AbstractSickShotEntity.PickupStatus.CREATIVE_ONLY && entityIn.abilities.isCreativeMode || this.getNoClip() && this.getShooter().getUniqueID() == entityIn.getUniqueID();
            if (this.pickupStatus == AbstractSickShotEntity.PickupStatus.ALLOWED && !entityIn.inventory.addItemStackToInventory(this.getBulletStack())) {
                flag = false;
            }

            if (flag) {
                entityIn.onItemPickup(this, 1);
                this.remove();
            }

        }
    }

    protected abstract ItemStack getBulletStack();

    protected boolean canTriggerWalking() {
        return false;
    }

    public void setDamage(double damageIn) {
        this.damage = damageIn;
    }

    public double getDamage() {
        return this.damage;
    }

    /**
     * Sets the amount of knockback the arrow applies when it hits a mob.
     */
    public void setKnockbackStrength(int knockbackStrengthIn) {
        this.knockbackStrength = knockbackStrengthIn;
    }

    /**
     * Returns true if it's possible to attack this entity with an item.
     */
    public boolean canBeAttackedWithItem() {
        return false;
    }

    protected float getEyeHeight(Pose poseIn, EntitySize sizeIn) {
        return 0.0F;
    }


    public void setPierceLevel(float level) {
        this.dataManager.set(PIERCE_LEVEL, level);
    }

    private void setArrowFlag(int p_203049_1_, boolean p_203049_2_) {
        byte b0 = this.dataManager.get(CRITICAL);
        if (p_203049_2_) {
            this.dataManager.set(CRITICAL, (byte)(b0 | p_203049_1_));
        } else {
            this.dataManager.set(CRITICAL, (byte)(b0 & ~p_203049_1_));
        }

    }

    /**
     * Whether the arrow has a stream of critical hit particles flying behind it.
     */
    public boolean getIsCritical() {
        byte b0 = this.dataManager.get(CRITICAL);
        return (b0 & 1) != 0;
    }


    public float getPierceLevel() {
        return this.dataManager.get(PIERCE_LEVEL);
    }

    protected float getWaterDrag() {
        return 0.2F;
    }

    /**
     * Sets if this arrow can noClip
     */
    public void setNoClip(boolean noClipIn) {
        this.noClip = noClipIn;
        this.setArrowFlag(2, noClipIn);
    }

    /**
     * Whether the arrow can noClip
     */
    public boolean getNoClip() {
        if (!this.world.isRemote) {
            return this.noClip;
        } else {
            return (this.dataManager.get(CRITICAL) & 2) != 0;
        }
    }


    public IPacket<?> createSpawnPacket() {
        Entity entity = this.getShooter();
        return new SSpawnObjectPacket(this, entity == null ? 0 : entity.getEntityId());
    }

    public static enum PickupStatus {
        DISALLOWED,
        ALLOWED,
        CREATIVE_ONLY;

        public static AbstractSickShotEntity.PickupStatus getByOrdinal(int ordinal) {
            if (ordinal < 0 || ordinal > values().length) {
                ordinal = 0;
            }

            return values()[ordinal];
        }
    }
}

