package com.Clivet268.MachineBuiltWorld.tileentity;

import com.Clivet268.MachineBuiltWorld.inventory.Containers.CrusherContainer;
import com.Clivet268.MachineBuiltWorld.inventory.crafting.IMoreRecipeType;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.util.Direction;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import static com.Clivet268.MachineBuiltWorld.util.RegistryHandler.CRUSHER_TILE;
import static com.Clivet268.MachineBuiltWorld.util.RegistryHandler.DISASSOCIATED_ATOM_CONTAINER_TILE;

public class DisassociatedAtomContainerTile extends AbstractDisassociatedAtomContainerTile {

    public DisassociatedAtomContainerTile() {
        super(DISASSOCIATED_ATOM_CONTAINER_TILE.get());


    }
    protected ITextComponent getDefaultName() {
        return new TranslationTextComponent("container.crusher");
    }

    @Override
    protected Container createMenu(int id, PlayerInventory player) {
        return null;
    }


    @Override
    public int[] getSlotsForFace(Direction side) {
        return new int[0];
    }
}
