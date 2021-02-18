package com.Clivet268.MachineBuiltWorld.tools;

import com.Clivet268.MachineBuiltWorld.util.RegistryHandler;
import com.google.common.collect.ImmutableSet;
import net.minecraft.block.Block;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ToolItem;

import java.util.Set;

public class MultimeterItem extends ToolItem {
    private static final Set<Block> EFFECTIVE_ON = ImmutableSet.of(RegistryHandler.WIRE.get());

  //  ToolType CRAFTING_TOOLS = get("crafting_tools");

    public MultimeterItem(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties builder) {
        super((float) attackDamageIn, attackSpeedIn, tier, EFFECTIVE_ON, builder.group(ItemGroup.MATERIALS));
    }
}