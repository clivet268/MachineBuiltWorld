package com.Clivet268.MachineBuiltWorld.tileentity;

import com.Clivet268.MachineBuiltWorld.inventory.Containers.GeneratorContainer;
import com.Clivet268.MachineBuiltWorld.inventory.Containers.IntensiveHeatingOvenContainer;
import com.Clivet268.MachineBuiltWorld.inventory.crafting.IMoreRecipeType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import static com.Clivet268.MachineBuiltWorld.util.RegistryHandler.GENERATOR_TILE;
import static com.Clivet268.MachineBuiltWorld.util.RegistryHandler.INTENSIVE_HEATING_OVEN_TILE;

public class GeneratorTile extends AbstractGeneratorTile {

    public GeneratorTile() {
        super(GENERATOR_TILE.get());


    }
    protected ITextComponent getDefaultName() {
        return new TranslationTextComponent("container.generator");
    }

    protected Container createMenu(int id, PlayerInventory player) {
        return new GeneratorContainer(id, world, this.pos, player, this);
    }


}
