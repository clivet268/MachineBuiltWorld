package com.Clivet268.MachineBuiltWorld.items;

import com.Clivet268.MachineBuiltWorld.MachineBuiltWorld;
import com.Clivet268.MachineBuiltWorld.util.RegistryHandler;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class LaserPisolMagItem extends Item{

    int initammoAmnt = 8;

    @OnlyIn(Dist.CLIENT)
    public double ammo;

        public LaserPisolMagItem(Item.Properties builder) {
            super(builder);
            this.addPropertyOverride(new ResourceLocation(MachineBuiltWorld.MOD_ID, "ammo"), new IItemPropertyGetter() {
                        @Override
                        public float call(ItemStack p_call_1_, @Nullable World p_call_2_, @Nullable LivingEntity p_call_3_) {
                            switch(getBulletAmount(p_call_1_))
                            {
                                case 1:
                                    return 0.1F;
                                case 2:
                                    return 0.2F;
                                case 3:
                                    return 0.3F;
                                case 4:
                                    return 0.4F;
                                case 5:
                                    return 0.5F;
                                case 6:
                                    return 0.6F;
                                case 7:
                                    return 0.7F;
                                case 8:
                                    return 0.8F;
                                default:
                                    return 0.0F;
                            }
                        }
                    });


        }



    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        if (!worldIn.isRemote) {
            if(playerIn.getHeldItem(handIn).getCount() != 1){
                return ActionResult.resultFail(playerIn.getHeldItem(handIn));
            }
            double ammoAmnt = getBulletAmount(playerIn.getHeldItem(handIn));
            if (playerIn.isSneaking()) {

                if(playerIn.getHeldItemOffhand().getItem() == RegistryHandler.LASER_SHELL.get())
                {
                    if(getBulletAmount(playerIn.getHeldItem(handIn)) < 8)
                    {
                        if(playerIn.getHeldItemOffhand().getCount() > 0) {
                            playerIn.getHeldItemOffhand().setCount(playerIn.getHeldItemOffhand().getCount() - 1);
                            ammoAmnt ++;
                             setBulletAmount(playerIn.getHeldItem(handIn), ammoAmnt);
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

    private static void setBulletAmount(ItemStack mag, double bullets) {
        //Store the tool's mode in NBT as a string
        CompoundNBT tagCompound = mag.getOrCreateTag();
        tagCompound.putDouble("ammo", bullets);
    }

    public static int getBulletAmount(ItemStack mag) {
        CompoundNBT tagCompound = mag.getOrCreateTag();
        return tagCompound.getInt("ammo");
    }


    @Override
    public void addInformation(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
        super.addInformation(stack, world, tooltip, flag);
        tooltip.add(new StringTextComponent("Ammo " + getBulletAmount(stack)));
    }
}





