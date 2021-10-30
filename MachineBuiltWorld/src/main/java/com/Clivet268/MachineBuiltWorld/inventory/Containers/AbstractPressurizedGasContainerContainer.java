package com.Clivet268.MachineBuiltWorld.inventory.Containers;

import com.Clivet268.MachineBuiltWorld.tileentity.AbstractPressurizedGasContainerTile;
import com.Clivet268.MachineBuiltWorld.util.RegistryHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

public abstract class AbstractPressurizedGasContainerContainer extends Container {

    private final IInventory genoratorInv;
    private IItemHandler playerInventory;
    public AbstractPressurizedGasContainerTile tileEntity;


    public AbstractPressurizedGasContainerContainer(World wworld, int id, PlayerInventory playerInventoryIn, BlockPos pos) {
        this(id, wworld, pos, playerInventoryIn, new Inventory(1));
    }

    public AbstractPressurizedGasContainerContainer(int windowId, World world, BlockPos pos, PlayerInventory playerInventory, IInventory genaratorInvIn) {
        super(RegistryHandler.PRESSURIZED_GAS_CONTAINER_CONTAINER.get(), windowId);
        this.tileEntity = (AbstractPressurizedGasContainerTile) world.getTileEntity(pos);
        this.genoratorInv = genaratorInvIn;
        this.playerInventory = new InvWrapper(playerInventory);
        if (tileEntity != null) {
            addSlot(new Slot(this.tileEntity, 0, 56, 17));
        }
        layoutPlayerInventorySlots(8, 70);
    }
    @OnlyIn(Dist.CLIENT)
    public int getSize() {
        return 1;
    }

    /**
     * Determines whether supplied player can use this container
     */
    public boolean canInteractWith(PlayerEntity playerIn) {
        return this.genoratorInv.isUsableByPlayer(playerIn);
    }

    /**
     * Handle when the stack in slot {@code index} is shift-clicked. Normally this moves the stack between the player
     * inventory and the other inventory(s).
     */
    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);
        if (slot != null && slot.getHasStack()) {
            ItemStack stack = slot.getStack();
            itemstack = stack.copy();
            if (index == 0) {
                if (!this.mergeItemStack(stack, 1, 37, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onSlotChange(stack, itemstack);
            } else {
                if (this.isFuel(stack)) {
                    if (!this.mergeItemStack(stack, 0, 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index < 28) {
                    if (!this.mergeItemStack(stack, 28, 37, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index < 37 && !this.mergeItemStack(stack, 1, 28, false)) {
                    return ItemStack.EMPTY;
                }
            }

            if (stack.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }

            if (stack.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(playerIn, stack);
        }

        return itemstack;
    }

    public static boolean isFuel(ItemStack stack) {
        return ForgeHooks.getBurnTime(stack) > 0;
    }


    private void layoutPlayerInventorySlots(int leftCol, int topRow) {
        // Player inventory
        addSlotBox(playerInventory, 9, leftCol, topRow, 9, 18, 3, 18);

        // Hotbar
        topRow += 58;
        addSlotRange(playerInventory, 0, leftCol, topRow, 9, 18);
    }

    private int addSlotRange(IItemHandler handler, int index, int x, int y, int amount, int dx) {
        for (int i = 0 ; i < amount ; i++) {
            addSlot(new SlotItemHandler(handler, index, x, y));
            x += dx;
            index++;
        }
        return index;
    }

    private int addSlotBox(IItemHandler handler, int index, int x, int y, int horAmount, int dx, int verAmount, int dy) {
        for (int j = 0 ; j < verAmount ; j++) {
            index = addSlotRange(handler, index, x, y, horAmount, dx);
            y += dy;
        }
        return index;
    }

}