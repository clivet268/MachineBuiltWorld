package com.Clivet268.MachineBuiltWorld.entity;

import com.Clivet268.MachineBuiltWorld.util.RegistryHandler;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

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


    public void setPotionEffect(ItemStack stack) {
        /*
        if (stack.getItem() == Items.TIPPED_ARROW) {
            this.potion = PotionUtils.getPotionFromItem(stack);
            Collection<EffectInstance> collection = PotionUtils.getFullEffectsFromItem(stack);
            if (!collection.isEmpty()) {
                for(EffectInstance effectinstance : collection) {
                    this.customPotionEffects.add(new EffectInstance(effectinstance));
                }
            }

            int i = getCustomColor(stack);
            if (i == -1) {
                this.refreshColor();
            } else {
                this.setFixedColor(i);
            }
        } else if (stack.getItem() == RegistryHandler.BULLET.get()) {
            this.potion = Potions.EMPTY;
            this.customPotionEffects.clear();
            this.dataManager.set(COLOR, -1);
        }

         */
        //this.potion = Potions.EMPTY;
        //this.customPotionEffects.clear();
        //this.dataManager.set(COLOR, -1);
    }

    public static int getCustomColor(ItemStack p_191508_0_) {
        CompoundNBT compoundnbt = p_191508_0_.getTag();
        return compoundnbt != null && compoundnbt.contains("CustomPotionColor", 99) ? compoundnbt.getInt("CustomPotionColor") : -1;
    }

    /*
    private void refreshColor() {
        this.fixedColor = false;
        if (this.potion == Potions.EMPTY && this.customPotionEffects.isEmpty()) {
            this.dataManager.set(COLOR, -1);
        } else {
            this.dataManager.set(COLOR, PotionUtils.getPotionColorFromEffectList(PotionUtils.mergeEffects(this.potion, this.customPotionEffects)));
        }

    }


     */

    public void addEffect(EffectInstance effect) {
        //this.customPotionEffects.add(effect);
        //this.getDataManager().set(COLOR, PotionUtils.getPotionColorFromEffectList(PotionUtils.mergeEffects(this.potion, this.customPotionEffects)));
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

    private void spawnPotionParticles(int particleCount) {
        //int i = this.getColor();
      //  if (i != -1 && particleCount > 0) {
      //      double d0 = (double)(i >> 16 & 255) / 255.0D;
       //     double d1 = (double)(i >> 8 & 255) / 255.0D;
       //     double d2 = (double)(i >> 0 & 255) / 255.0D;

          //  for(int j = 0; j < particleCount; ++j) {
           //     this.world.addParticle(ParticleTypes.ENTITY_EFFECT, this.getPosXRandom(0.5D), this.getPosYRandom(), this.getPosZRandom(0.5D), 225, 222, 22);
          //  }

        //}
    }

    public int getColor() {
        //return this.dataManager.get(COLOR);
        return 0;
    }

    private void setFixedColor(int p_191507_1_) {
        //this.fixedColor = true;
        //this.dataManager.set(COLOR, p_191507_1_);
    }

    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        /*
        if (this.potion != Potions.EMPTY && this.potion != null) {
            compound.putString("Potion", Registry.POTION.getKey(this.potion).toString());
        }

        if (this.fixedColor) {
            compound.putInt("Color", this.getColor());
        }

        if (!this.customPotionEffects.isEmpty()) {
            ListNBT listnbt = new ListNBT();

            for(EffectInstance effectinstance : this.customPotionEffects) {
                listnbt.add(effectinstance.write(new CompoundNBT()));
            }

            compound.put("CustomPotionEffects", listnbt);
        }

         */

    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */

    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        /*
        if (compound.contains("Potion", 8)) {
            this.potion = PotionUtils.getPotionTypeFromNBT(compound);
        }

        for (EffectInstance effectinstance : PotionUtils.getFullEffectsFromTag(compound)) {
            this.addEffect(effectinstance);
        }

        if (compound.contains("Color", 99)) {
            this.setFixedColor(compound.getInt("Color"));
        } else {
            this.refreshColor();
        }

         */

    }


    protected void bulletHit(LivingEntity living) {
        super.bulletHit(living);

        }



    protected ItemStack getBulletStack() {
            return new ItemStack(RegistryHandler.BULLET.get());
    }

    /**
     * Handler for {@link World#setEntityState}
     */
    @OnlyIn(Dist.CLIENT)
    public void handleStatusUpdate(byte id) {
        /*if (id == 0) {
            int i = this.getColor();
            if (i != -1) {
                double d0 = (double)(i >> 16 & 255) / 255.0D;
                double d1 = (double)(i >> 8 & 255) / 255.0D;
                double d2 = (double)(i >> 0 & 255) / 255.0D;

                for(int j = 0; j < 20; ++j) {
                    this.world.addParticle(ParticleTypes.ENTITY_EFFECT, this.getPosXRandom(0.5D), this.getPosYRandom(), this.getPosZRandom(0.5D), d0, d1, d2);
                }
            }
        } else {
            super.handleStatusUpdate(id);
        }

         */
        super.handleStatusUpdate(id);
    }
}

