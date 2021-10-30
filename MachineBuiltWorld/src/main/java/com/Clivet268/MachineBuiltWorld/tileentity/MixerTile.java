package com.Clivet268.MachineBuiltWorld.tileentity;

import com.Clivet268.MachineBuiltWorld.inventory.Containers.MixerContainer;
import com.Clivet268.MachineBuiltWorld.inventory.crafting.IMoreRecipeType;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import javax.annotation.Nonnull;

import static com.Clivet268.MachineBuiltWorld.util.RegistryHandler.MIXER_TILE;

public class MixerTile extends AbstractMixerTile {

    public MixerTile() {
        super(MIXER_TILE.get(), IMoreRecipeType.MoreMixings.MIXING);


    }
    protected ITextComponent getDefaultName() {
        return new TranslationTextComponent("container.mixer");
    }

    @Nonnull
    protected Container createMenu(int id, @Nonnull PlayerInventory player) {
        return new MixerContainer(id, world, player, this, this.furnaceData, this.pos);
    }


}
