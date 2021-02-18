package com.Clivet268.MachineBuiltWorld.items;

import com.Clivet268.MachineBuiltWorld.util.RegistryHandler;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;

import java.util.function.Predicate;

public abstract class MoreShootableItem extends Item {
    public static final Predicate<ItemStack> BULLETS = (isBullet) -> {
        return isBullet.getItem().isIn(RegistryHandler.Tags.BULLETS);
    };
    /*
    public static final Predicate<ItemStack> ARROWS_OR_FIREWORKS = ARROWS.or((p_220003_0_) -> {
        return p_220003_0_.getItem() == Items.FIREWORK_ROCKET;
    });

     */

    public MoreShootableItem(Item.Properties properties) {
        super(properties);
    }

    public Predicate<ItemStack> getAmmoPredicate() {
        return this.getInventoryAmmoPredicate();
    }

    /**
     * Get the predicate to match ammunition when searching the player's inventory, not their main/offhand
     */
    public abstract Predicate<ItemStack> getInventoryAmmoPredicate();

    public static ItemStack getHeldAmmo(LivingEntity living, Predicate<ItemStack> isAmmo) {
        if (isAmmo.test(living.getHeldItem(Hand.OFF_HAND))) {
            return living.getHeldItem(Hand.OFF_HAND);
        } else {
            return isAmmo.test(living.getHeldItem(Hand.MAIN_HAND)) ? living.getHeldItem(Hand.MAIN_HAND) : ItemStack.EMPTY;
        }
    }

    /**
     * Return the enchantability factor of the item, most of the time is based on material.
     */
    public int getItemEnchantability() {
        return 1;
    }
}