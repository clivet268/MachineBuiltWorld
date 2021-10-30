package com.Clivet268.MachineBuiltWorld.items;

import com.Clivet268.MachineBuiltWorld.util.MachineBuiltWorldItemGroup;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Supplier;

public class ResinBucketItem extends BucketItem {
    int qt;
    public ResinBucketItem(int q, Supplier<? extends Fluid> supplierfluid) {
        super(supplierfluid, new Item.Properties().group(MachineBuiltWorldItemGroup.instance).containerItem(Items.BUCKET).maxStackSize(1));
        this.qt = q;
    }
    public int getQuality()
    {
        return this.qt;
    }
    public void setQuality(int a)
    {
        this.qt = a;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        if(this.qt < 3) {
            tooltip.add(new StringTextComponent("Quality: Low"));
        } else if(this.qt < 6) {
            tooltip.add(new StringTextComponent("Quality: High"));
        }else if(this.qt < 9) {
            tooltip.add(new StringTextComponent("Quality: Super"));
        }
    }
}
