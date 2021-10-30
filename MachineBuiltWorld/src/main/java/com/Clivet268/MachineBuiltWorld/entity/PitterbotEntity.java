package com.Clivet268.MachineBuiltWorld.entity;

import com.Clivet268.MachineBuiltWorld.entity.ai.StationGoal;
import com.Clivet268.MachineBuiltWorld.util.RegistryHandler;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

public class PitterbotEntity extends MachineEntity {


    private float headRotationCourse;
    private float headRotationCourseOld;
    private boolean isWet;
    protected StationGoal sitGoal;

    public PitterbotEntity(EntityType<? extends PitterbotEntity> type, World worldIn) {
        super(type, worldIn);
        this.setTamed(false);
    }

    protected void registerGoals() {
        this.sitGoal = new StationGoal(this);
        this.goalSelector.addGoal(1, new SwimGoal(this));
        this.goalSelector.addGoal(2, this.sitGoal);
        this.goalSelector.addGoal(4, new LeapAtTargetGoal(this, 0.4F));
        this.goalSelector.addGoal(5, new MeleeAttackGoal(this, 1.0D, true));
        //this.goalSelector.addGoal(6, new FollowOwnerGoal(this, 1.0D, 10.0F, 2.0F, false));
        this.goalSelector.addGoal(8, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
        this.goalSelector.addGoal(10, new LookAtGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.addGoal(10, new LookRandomlyGoal(this));
        //this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
        //this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
        this.targetSelector.addGoal(3, (new HurtByTargetGoal(this)).setCallsForHelp());
    }

    protected void registerAttributes() {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue((double)0.3F);
        if (this.isAligned()
        ) {
            this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0D);
        } else {
            this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(8.0D);
        }

        this.getAttributes().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(2.0D);
    }

    /**
     * Sets the active target the Task system uses for tracking
     */
    public void setAttackTarget(@Nullable LivingEntity entitylivingbaseIn) {
        super.setAttackTarget(entitylivingbaseIn);
        if (entitylivingbaseIn == null) {
            this.setAngry(false);
        } else if (!this.isAligned()) {
            this.setAngry(true);
        }

    }

    protected void registerData() {
        super.registerData();
    }

    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(RegistryHandler.SPROCKETEER_STEP.get(), 0.15F, 1.0F);
    }

    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.putBoolean("Angry", this.isAngry());
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        this.setAngry(compound.getBoolean("Angry"));

    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.ENTITY_WOLF_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_WOLF_DEATH;
    }

    /**
     * Returns the volume for the sounds this mob makes.
     */
    protected float getSoundVolume() {
        return 0.4F;
    }

    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    public void livingTick() {
        super.livingTick();
        if (!this.world.isRemote && this.isWet && !this.hasPath() && this.onGround) {
            this.world.setEntityState(this, (byte)8);
        }

        if (!this.world.isRemote && this.getAttackTarget() == null && this.isAngry()) {
            this.setAngry(false);
        }

    }

    /**
     * Called to update the entity's position/logic.
     */
    public void tick() {
        super.tick();
        if (this.isAlive()) {
            this.headRotationCourseOld = this.headRotationCourse;
            this.headRotationCourse += (0.0F - this.headRotationCourse) * 0.4F;


            if (this.isInWaterRainOrBubbleColumn()) {
                this.isWet = true;
           /* } else if ((this.isWet || this.isShaking) && this.isShaking) {
                if (this.timeWolfIsShaking == 0.0F) {
                    this.playSound(SoundEvents.ENTITY_WOLF_SHAKE, this.getSoundVolume(), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
                }

                this.prevTimeWolfIsShaking = this.timeWolfIsShaking;
                this.timeWolfIsShaking += 0.05F;
                if (this.prevTimeWolfIsShaking >= 2.0F) {
                    this.isWet = false;
                    this.isShaking = false;
                    this.prevTimeWolfIsShaking = 0.0F;
                    this.timeWolfIsShaking = 0.0F;
                }

                if (this.timeWolfIsShaking > 0.4F) {
                    float f = (float)this.getPosY();
                    int i = (int)(MathHelper.sin((this.timeWolfIsShaking - 0.4F) * (float)Math.PI) * 7.0F);
                    Vec3d vec3d = this.getMotion();

                    for(int j = 0; j < i; ++j) {
                        float f1 = (this.rand.nextFloat() * 2.0F - 1.0F) * this.getWidth() * 0.5F;
                        float f2 = (this.rand.nextFloat() * 2.0F - 1.0F) * this.getWidth() * 0.5F;
                        this.world.addParticle(ParticleTypes.SPLASH, this.getPosX() + (double)f1, (double)(f + 0.8F), this.getPosZ() + (double)f2, vec3d.x, vec3d.y, vec3d.z);
                    }

            */
                }
            }


    }

    /**
     * Called when the mob's health reaches 0.
     */
    public void onDeath(DamageSource cause) {
        this.isWet = false;
        super.onDeath(cause);
    }

    /**
     * True if the wolf is wet
     */
    @OnlyIn(Dist.CLIENT)
    public boolean isMachineWet() {
        return this.isWet;
    }


    protected float getStandingEyeHeight(Pose poseIn, EntitySize sizeIn) {
        return sizeIn.height * 0.8F;
    }

    /**
     * Called when the entity is attacked.
     */
    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (this.isInvulnerableTo(source)) {
            return false;
        } else {
            Entity entity = source.getTrueSource();
            if (this.sitGoal != null) {
                this.sitGoal.setSitting(false);
            }

            if (entity != null && !(entity instanceof PlayerEntity) && !(entity instanceof AbstractArrowEntity)) {
                amount = (amount + 1.0F) / 2.0F;
            }

            return super.attackEntityFrom(source, amount);
        }
    }

    public boolean attackEntityAsMob(Entity entityIn) {
        boolean flag = entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), (float)((int)this.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getValue()));
        if (flag) {
            this.applyEnchantments(this, entityIn);
        }

        return flag;
    }


    public boolean processInteract(PlayerEntity player, Hand hand) {
        ItemStack itemstack = player.getHeldItem(hand);
        Item item = itemstack.getItem();
        if (itemstack.getItem() instanceof SpawnEggItem) {
            return super.processInteract(player, hand);
        } else if (this.world.isRemote) {
            return this.isAlignedLeader(player) || item == Items.BONE && !this.isAngry();
        } else {
            if (this.isAligned()) {
                if (item.isFood() && item.getFood().isMeat() && this.getHealth() < this.getMaxHealth()) {
                    if (!player.abilities.isCreativeMode) {
                        itemstack.shrink(1);
                    }

                    this.heal((float)item.getFood().getHealing());
                    return true;
                }


                if (this.isAlignedLeader(player) && !this.isBreedingItem(itemstack)) {
                    this.sitGoal.setSitting(!this.isSitting());
                    this.isJumping = false;
                    this.navigator.clearPath();
                    this.setAttackTarget((LivingEntity)null);
                }
            } else if (item == Items.BONE && !this.isAngry()) {
                if (!player.abilities.isCreativeMode) {
                    itemstack.shrink(1);
                }

                if (this.rand.nextInt(3) == 0
                        //&& !net.minecraftforge.event.ForgeEventFactory.onAnimalTame(this, player))
                ){
                    this.setAlignedLeader(player);
                    this.navigator.clearPath();
                    this.setAttackTarget((LivingEntity)null);
                    this.sitGoal.setSitting(true);
                    this.world.setEntityState(this, (byte)7);
                } else {
                    this.world.setEntityState(this, (byte)6);
                }

                return true;
            }

            return super.processInteract(player, hand);
        }
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


    /**
     * Checks if the parameter is an item which this animal can be fed to breed it (wheat, carrots or seeds depending on
     * the animal type)
     */
    public boolean isBreedingItem(ItemStack stack) {
        Item item = stack.getItem();
        return item.isFood() && item.getFood().isMeat();
    }

    /**
     * Will return how many at most can spawn in a chunk at once.
     */
    public int getMaxSpawnedInChunk() {
        return 8;
    }

    /**
     * Determines whether this wolf is angry or not.
     */
    public boolean isAngry() {
        return (this.dataManager.get(ALLEGIANCE) & 2) != 0;
    }

    /**
     * Sets whether this wolf is angry or not.
     */
    public void setAngry(boolean angry) {
        byte b0 = this.dataManager.get(ALLEGIANCE);
        if (angry) {
            this.dataManager.set(ALLEGIANCE, (byte)(b0 | 2));
        } else {
            this.dataManager.set(ALLEGIANCE, (byte)(b0 & -3));
        }

    }


    public boolean canBeLeashedTo(PlayerEntity player) {
        return false;
        /*return !this.isAngry() && super.canBeLeashedTo(player);

         */
    }

}
