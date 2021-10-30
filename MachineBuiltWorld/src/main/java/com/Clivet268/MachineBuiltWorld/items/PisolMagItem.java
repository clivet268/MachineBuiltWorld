package com.Clivet268.MachineBuiltWorld.items;

import com.Clivet268.MachineBuiltWorld.util.RegistryHandler;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class PisolMagItem extends Item{

    int initammoAmnt = 8;

    public Item[] bulletTypes= new Item[8];
    @OnlyIn(Dist.CLIENT)
    public double ammo;

        public PisolMagItem(Properties builder) {
            super(builder);
        }


    public void rollitdown(){
        Item[] ea= new Item[8];
        int iii = 1;
        for(Item i : bulletTypes)
        {
            if (iii <7){
                ea[iii] = bulletTypes[iii];
                iii++;
            }
        }
        bulletTypes = ea;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        if (!worldIn.isRemote) {
            if(playerIn.getHeldItem(handIn).getCount() != 1){
                return ActionResult.resultFail(playerIn.getHeldItem(handIn));
            }
            int ammoAmnt = getBulletAmount(playerIn.getHeldItem(handIn));
            if (playerIn.isSneaking()) {

                if(playerIn.getHeldItemOffhand().getItem().isIn(RegistryHandler.Tags.BULLETS))
                {
                    if(getBulletAmount(playerIn.getHeldItem(handIn)) < 8)
                    {
                        if(playerIn.getHeldItemOffhand().getCount() > 0) {
                            if(playerIn.getHeldItemOffhand().getItem() instanceof AbstractBulletItem)
                            playerIn.getHeldItemOffhand().setCount(playerIn.getHeldItemOffhand().getCount() - 1);
                            ammoAmnt ++;
                             setBulletAmount(playerIn.getHeldItem(handIn), ammoAmnt);
                             rollitdown();
                             this.bulletTypes[0] = playerIn.getHeldItemOffhand().getItem();
                             System.out.println(getBulletAmount(playerIn.getHeldItem(handIn)));
                             ammo = ammoAmnt;
                            return ActionResult.resultSuccess(playerIn.getHeldItem(handIn));
                        }
                        return ActionResult.resultFail(playerIn.getHeldItem(handIn));
                    }
                }
            }}
        return ActionResult.resultFail(playerIn.getHeldItem(handIn));
    }

    public void setBulletAmount(ItemStack mag, int bullets) {
        //Store the tool's mode in NBT as a string
        CompoundNBT tagCompound = mag.getOrCreateTag();
        tagCompound.putInt("ammo", bullets);
    }

    public int getBulletAmount(ItemStack mag) {
        CompoundNBT tagCompound = mag.getOrCreateTag();
        return tagCompound.getInt("ammo");
    }


    @Override
    public void addInformation(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
        super.addInformation(stack, world, tooltip, flag);
        tooltip.add(new StringTextComponent("Ammo " + getBulletAmount(stack)));
    }
}





