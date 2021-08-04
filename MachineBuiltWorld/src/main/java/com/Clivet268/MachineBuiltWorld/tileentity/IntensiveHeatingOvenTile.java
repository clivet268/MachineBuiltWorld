package com.Clivet268.MachineBuiltWorld.tileentity;

import com.Clivet268.MachineBuiltWorld.inventory.Containers.IntensiveHeatingOvenContainer;
import com.Clivet268.MachineBuiltWorld.inventory.crafting.IMoreRecipeType;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import static com.Clivet268.MachineBuiltWorld.util.RegistryHandler.INTENSIVE_HEATING_OVEN_TILE;

public class IntensiveHeatingOvenTile extends AbstractIntensiveHeatingOvenTile {

    public IntensiveHeatingOvenTile() {
        super(INTENSIVE_HEATING_OVEN_TILE.get(), IMoreRecipeType.MoreCokes.COKEING);


    }
    protected ITextComponent getDefaultName() {
        return new TranslationTextComponent("container.coke_oven");
    }

    protected Container createMenu(int id, PlayerInventory player) {
        return new IntensiveHeatingOvenContainer(id, world, player, this, this.furnaceData, this.pos);
    }


}
