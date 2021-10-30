package com.Clivet268.MachineBuiltWorld.tileentity;

import com.Clivet268.MachineBuiltWorld.inventory.Containers.CrusherContainer;
import com.Clivet268.MachineBuiltWorld.inventory.crafting.IMoreRecipeType;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import static com.Clivet268.MachineBuiltWorld.util.RegistryHandler.CRUSHER_TILE;

public class CrusherTile extends AbstractCrusherTile {

    public CrusherTile() {
        super(CRUSHER_TILE.get(), IMoreRecipeType.MoreCrushes.CRUSHING);


    }
    protected ITextComponent getDefaultName() {
        return new TranslationTextComponent("container.crusher");
    }

    protected Container createMenu(int id, PlayerInventory player) {
        return new CrusherContainer(id, world, player, this, this.crusherData, this.pos);
    }


}
