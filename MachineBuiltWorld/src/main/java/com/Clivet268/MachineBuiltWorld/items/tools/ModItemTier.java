package com.Clivet268.MachineBuiltWorld.items.tools;

import com.Clivet268.MachineBuiltWorld.util.RegistryHandler;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;

import java.util.function.Supplier;

public enum ModItemTier implements IItemTier {

    /**
     * unga bunga logic, yes hit da glass thing on an anvil to fix it
     */
    GLASS_DAGGER(0,150, 5.0f, 3.5f, 16, () -> {return Ingredient.fromItems(RegistryHandler.GLASH_SHARDS.get());}),

    STRENGTHENED_GLASS_DAGGER(0,750, 5.0f, 3.5f, 16, () -> {return Ingredient.fromItems(RegistryHandler.GLASH_SHARDS.get());}),

    CRAFTING_TOOLS(-1,100, 2.0f, 1.0f, 3, () -> {return Ingredient.fromItems(Items.IRON_NUGGET);}),

    MULTIMETER(-1,100, 2.0f, 1.0f, 3, () -> {return Ingredient.fromItems(Items.IRON_NUGGET);}),

    LASER_PISTOL(-1,1200, 2.0f, 1.0f, 3, () -> {return Ingredient.fromItems(RegistryHandler.STEEL_INGOT.get());});

    private final int harvestLevel;
    private final int maxUses;
    private final float efficiency;
    private final float attackDamage;
    private final int enchantability;
    private final Supplier<Ingredient> repairMaterial;


    ModItemTier(int harvestLevel, int maxUses, float efficiency, float attackDamage, int enchantability, Supplier<Ingredient> repairMaterial) {
        this.harvestLevel = harvestLevel;
        this.maxUses = maxUses;
        this.efficiency = efficiency;
        this.attackDamage = attackDamage;
        this.enchantability = enchantability;
        this.repairMaterial = repairMaterial;
    }

    @Override
    public int getMaxUses() {
        return maxUses;
    }

    @Override
    public float getEfficiency() {
        return efficiency;
    }

    @Override
    public float getAttackDamage() {
        return attackDamage;
    }

    @Override
    public int getHarvestLevel() {
        return harvestLevel;
    }

    @Override
    public int getEnchantability() {
        return enchantability;
    }

    @Override
    public Ingredient getRepairMaterial() {
        return repairMaterial.get();
    }
}
