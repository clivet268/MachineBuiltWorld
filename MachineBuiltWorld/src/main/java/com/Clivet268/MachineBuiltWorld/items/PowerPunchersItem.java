package com.Clivet268.MachineBuiltWorld.items;

import net.minecraft.entity.Entity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;

import static com.Clivet268.MachineBuiltWorld.items.armor.ModArmorMaterial.PUNCH;

public class PowerPunchersItem  extends ArmorItem {

       // private static final GasMaskMaterial GAS_MASK_MATERIAL = new GasMaskMaterial();

        public PowerPunchersItem(Properties properties) {
            super(PUNCH, EquipmentSlotType.HEAD, properties.setNoRepair());
        }

        @Override
        public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
            return "machineBuiltWorld:armor/scuba_set.png";
        }


    }

