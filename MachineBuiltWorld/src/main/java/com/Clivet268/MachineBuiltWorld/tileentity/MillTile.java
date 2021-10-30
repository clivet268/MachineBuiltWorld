package com.Clivet268.MachineBuiltWorld.tileentity;

import com.Clivet268.MachineBuiltWorld.inventory.Containers.MillContainer;
import com.Clivet268.MachineBuiltWorld.inventory.crafting.IMoreRecipeType;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import javax.annotation.Nonnull;

import static com.Clivet268.MachineBuiltWorld.util.RegistryHandler.MILL_TILE;

public class MillTile extends AbstractMillTile {

    public MillTile() {
        super(MILL_TILE.get(), IMoreRecipeType.MoreMillings.MILLING);


    }
    protected ITextComponent getDefaultName() {
        return new TranslationTextComponent("container.mill");
    }

    @Nonnull
    protected Container createMenu(int id, @Nonnull PlayerInventory player) {
        return new MillContainer(id, world, player, this, this.furnaceData, this.pos);
    }


}
