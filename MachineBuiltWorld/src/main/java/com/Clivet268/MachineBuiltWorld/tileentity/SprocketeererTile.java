package com.Clivet268.MachineBuiltWorld.tileentity;

import com.Clivet268.MachineBuiltWorld.inventory.Containers.SprocketeererContainer;
import com.Clivet268.MachineBuiltWorld.inventory.crafting.IMoreRecipeType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import javax.annotation.Nonnull;

import static com.Clivet268.MachineBuiltWorld.util.RegistryHandler.SPROCKETEER_TILE;

public class SprocketeererTile extends AbstractSprocketeererTile {

    public SprocketeererTile() {
        super(SPROCKETEER_TILE.get(), IMoreRecipeType.MoreSprocketeers.SPROCKETEER, null);


    }

    public SprocketeererTile(PlayerEntity piin) {
        super(SPROCKETEER_TILE.get(), IMoreRecipeType.MoreSprocketeers.SPROCKETEER, piin);


    }
    protected ITextComponent getDefaultName() {
        return new TranslationTextComponent("container.sprocketeerer");
    }

    @Nonnull
    protected Container createMenu(int id, @Nonnull PlayerInventory player) {
        return new SprocketeererContainer(id, world, player, this, this.furnaceData, this.pos);
    }


}
