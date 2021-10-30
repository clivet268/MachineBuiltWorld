package com.Clivet268.MachineBuiltWorld.tileentity;

import com.Clivet268.MachineBuiltWorld.inventory.Containers.MeltingPotContainer;
import com.Clivet268.MachineBuiltWorld.inventory.crafting.IMoreRecipeType;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import javax.annotation.Nonnull;

import static com.Clivet268.MachineBuiltWorld.util.RegistryHandler.MELTING_POT_TILE;

public class MeltingPotTile extends AbstractMeltingPotTile {

    public MeltingPotTile() {
        super(MELTING_POT_TILE.get(), IMoreRecipeType.MoreMeltings.MELTING_POT);


    }
    protected ITextComponent getDefaultName() {
        return new TranslationTextComponent("container.melting_pot");
    }

    @Nonnull
    protected Container createMenu(int id, @Nonnull PlayerInventory player) {
        return new MeltingPotContainer(id, world, player, this, this.furnaceData, this.pos);
    }


}
