package com.Clivet268.MachineBuiltWorld.items.armor;

import com.Clivet268.MachineBuiltWorld.MachineBuiltWorld;
import com.Clivet268.MachineBuiltWorld.util.RegistryHandler;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.function.Supplier;

public enum ModArmorMaterial implements IArmorMaterial {
    COPPER(MachineBuiltWorld.MOD_ID + ":copper", 13, new int[] {2, 4, 6 ,2}, 15,
            SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 0.0F, () ->{return Ingredient.fromItems(RegistryHandler.COPPER_INGOT.get());}),
    PUNCH(MachineBuiltWorld.MOD_ID + ":scuba_set", 13, new int[] {2, 4, 6 ,2}, 15,
    SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 0.0F, () ->{return Ingredient.fromItems(RegistryHandler.COPPER_INGOT.get());});

    private static final int[] MAX_DAMAGE_ARRAY = new int[] {11, 16, 15, 13};
    private final String name;
    private final int maxDamageFactor;
    private final int[] damegeReductionAmountArray;
    private final int enchantablilty;
    private final SoundEvent soundEvent;
    private final float toughness;
    private final Supplier<Ingredient> repairMaterial;

    ModArmorMaterial(String name, int maxDamageFactor, int[] damegeReductionAmountArray, int enchantablilty,
                     SoundEvent soundEvent, float toughness, Supplier<Ingredient> repairMaterial)
    {
        this.name = name;
        this.damegeReductionAmountArray =damegeReductionAmountArray;
        this.maxDamageFactor = maxDamageFactor;
        this.enchantablilty = enchantablilty;
        this.soundEvent = soundEvent;
        this.toughness = toughness;
        this.repairMaterial = repairMaterial;
    }

    @Override
    public int getDurability(EquipmentSlotType slotIn) {
        return MAX_DAMAGE_ARRAY[slotIn.getIndex()] * this.maxDamageFactor;
    }

    @Override
    public int getDamageReductionAmount(EquipmentSlotType slotIn) {
        return this.damegeReductionAmountArray[slotIn.getIndex()];
    }

    @Override
    public int getEnchantability() {
        return this.enchantablilty;
    }

    @Override
    public SoundEvent getSoundEvent() {
        return this.soundEvent;
    }

    @Override
    public Ingredient getRepairMaterial() {
        return this.repairMaterial.get();
    }
    @OnlyIn(Dist.CLIENT)
    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public float getToughness() {
        return this.toughness;
    }

    /*protected static class GasMaskMaterial implements Supplier<ModArmorMaterial> {

        private GasMaskMaterial(String name, int maxDamageFactor, int[] damegeReductionAmountArray, int enchantablilty, SoundEvent soundEvent, float toughness, Supplier<Ingredient> repairMaterial) {
            super();
        }

       // @Override
        public int getDamageReductionAmount(EquipmentSlotType slotType) {
            return 0;
        }

       // @Override
        public String getName() {
            return MachineBuiltWorld.MOD_ID + ":power_punchers";
        }

        //@Override
        public float getToughness() {
            return 0;
        }

        @Override
        public ModArmorMaterial get() {
            return null;
        }
    }

     */
}
