package com.Clivet268.MachineBuiltWorld.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.HandSide;
import net.minecraft.world.World;

public class MoreLivingEntity extends LivingEntity {
    public static final DataParameter<Integer> LASER_MARK_COUNT_IN_ENTITY = EntityDataManager.createKey(LivingEntity.class, DataSerializers.VARINT);

    public MoreLivingEntity(EntityType<? extends LivingEntity> type, World worldIn) {
        super(type, worldIn);
    }

    @Override
    public void registerData() {
        super.registerData();
        super.dataManager.register(LASER_MARK_COUNT_IN_ENTITY, 0);
    }

    @Override
    public Iterable<ItemStack> getArmorInventoryList() {
        return this.getArmorInventoryList();
    }

    @Override
    public ItemStack getItemStackFromSlot(EquipmentSlotType slotIn) {
        return this.getItemStackFromSlot(slotIn);
    }

    @Override
    public void setItemStackToSlot(EquipmentSlotType slotIn, ItemStack stack) {
        this.setItemStackToSlot(slotIn, stack);
    }

    @Override
    public HandSide getPrimaryHand() {
        return this.getPrimaryHand();
    }

    /**
     * counts the amount of arrows stuck in the entity. getting hit by arrows increases this, used in rendering
     */
    public final int getLaserBurnCountInEntity() {
        System.out.println(super.dataManager.get(LASER_MARK_COUNT_IN_ENTITY));
        return super.dataManager.get(LASER_MARK_COUNT_IN_ENTITY);
    }

    /**
     * sets the amount of arrows stuck in the entity. used for rendering those
     */
    public final void setLaserBurnCountInEntity(int count) {
        super.dataManager.set(LASER_MARK_COUNT_IN_ENTITY, count);
    }
}
