package com.Clivet268.MachineBuiltWorld.items.tools;

import net.minecraft.item.Item;

public class WireCuttersItem extends Item {
    public WireCuttersItem(Item.Properties builder) {
        super(builder);
    }
}
  /*  public ItemStack getCraftingResult(CraftingInventory inv) {
        ItemStack itemstack = ItemStack.EMPTY;
        ItemStack itemstack1 = ItemStack.EMPTY;

        for(int i = 0; i < inv.getSizeInventory(); ++i) {
            ItemStack itemstack2 = inv.getStackInSlot(i);
            if (!itemstack2.isEmpty()) {
                if (itemstack2.getItem() instanceof RegistryHandler.WIRE) {
                    itemstack = itemstack2;
                } else if (itemstack2.getItem() == Items.SHIELD) {
                    itemstack1 = itemstack2.copy();
                }
            }
        }

        if (itemstack1.isEmpty()) {
            return itemstack1;
        } else {
            CompoundNBT compoundnbt = itemstack.getChildTag("BlockEntityTag");
            CompoundNBT compoundnbt1 = compoundnbt == null ? new CompoundNBT() : compoundnbt.copy();
            compoundnbt1.putInt("Base", ((BannerItem)itemstack.getItem()).getColor().getId());
            itemstack1.setTagInfo("BlockEntityTag", compoundnbt1);
            return itemstack1;
        }

    }
}

/*

    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, LivingEntity entityLiving) {

        if (entityLiving instanceof ServerPlayerEntity) {
            ServerPlayerEntity serverplayerentity = (ServerPlayerEntity)entityLiving;
            CriteriaTriggers.CONSUME_ITEM.trigger(serverplayerentity, stack);
            serverplayerentity.addStat(Stats.ITEM_USED.get(this));
        }

        if (entityLiving instanceof PlayerEntity && !((PlayerEntity)entityLiving).abilities.isCreativeMode) {
            stack.shrink(1);
        }

        return stack.isEmpty() ? new ItemStack(Items.BUCKET) : stack;
    }


    public int getUseDuration(ItemStack stack) {
        return 32;
    }


    public UseAction getUseAction(ItemStack stack) {
        return UseAction.DRINK;
    }


    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        playerIn.setActiveHand(handIn);
        return ActionResult.resultSuccess(playerIn.getHeldItem(handIn));


       item use or consuming^
        */
