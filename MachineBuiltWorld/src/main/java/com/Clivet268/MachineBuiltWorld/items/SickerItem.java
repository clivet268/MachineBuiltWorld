package com.Clivet268.MachineBuiltWorld.items;

import com.Clivet268.MachineBuiltWorld.entity.AbstractSickShotEntity;
import com.Clivet268.MachineBuiltWorld.entity.SickShotEntity;
import com.Clivet268.MachineBuiltWorld.util.RegistryHandler;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ShootableItem;
import net.minecraft.item.UseAction;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.stats.Stats;
import net.minecraft.util.*;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Predicate;

import static com.Clivet268.MachineBuiltWorld.MachineBuiltWorld.LOGGER;

//TODO balance
//TODO do
//TODO make sickly
//TODO sounds
public class SickerItem extends ShootableItem implements IReloadable{


    private static boolean donodo = true;
    public SickerItem(Properties builder) {
        super(builder);
    }

    public boolean isReloadable(PlayerEntity player){
        return true;
    }


    @Deprecated
    public Predicate<ItemStack> getInventoryAmmoPredicate() {
        return null;
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.CROSSBOW;
    }


    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, LivingEntity entityLiving, int timeLeft) {
        int i = this.getUseDuration(stack) - timeLeft;
        LOGGER.info("it worked");
        if(getPower(stack) == 0 && getLoaded(stack)) {
            setPower(stack, 10);
        }

    }

    @Override
    public boolean isCrossbow(ItemStack stack) {
        return stack.getItem() == RegistryHandler.SICKER.get() || stack.getItem() == Items.CROSSBOW;
    }


    public float getBulletVelocity() {
        return 10.0f;
    }


    protected double calcDamage(ItemStack bowStack){
        double baseDamage = 2.0D;
        return baseDamage + 0.5D;
    }

    @Nullable
    public ItemStack findAmmo(PlayerEntity player) {
        for (int i = 0; i < player.inventory.getSizeInventory(); ++i) {
            ItemStack itemstack1 = player.inventory.getStackInSlot(i);
            if (itemstack1.getItem() == (RegistryHandler.SICK_SHOT.get())) {
                return itemstack1;
            }
        }
        return new ItemStack(Items.AIR);
    }



    /**
     * How long it takes to use or consume an item
     */
    public int getUseDuration() {
        return 100000;
    }

    public void reload(PlayerEntity playerIn) {
        if(!getLoaded(playerIn.getHeldItemMainhand())) {
            ItemStack ammoStack = findAmmo(playerIn);
            if (ammoStack.getItem() == RegistryHandler.SICK_SHOT.get()) {
                setLoaded(playerIn.getHeldItemMainhand(), true);
                playerIn.world.playSound(playerIn, playerIn.getPosition(), RegistryHandler.SPROCKETEER_STEP.get(), SoundCategory.PLAYERS, 100, 1);
                ammoStack.shrink(1);

            }
        }
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        LOGGER.info("Power level:" + getPower(itemstack));
        if (getPower(itemstack) > 0 && getLoaded(itemstack)) {
            System.out.println("sickershot:)");
            //TODO simple creative thingy inf ammo
            boolean flag = playerIn.abilities.isCreativeMode;
            ItemStack stack = itemstack;
            float f = getBulletVelocity();

            if (!worldIn.isRemote) {
                AbstractSickShotEntity laserEntity = new SickShotEntity(worldIn, playerIn);
                laserEntity.setDamage(calcDamage(stack));
                laserEntity.shoot(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, f, 1.0F);
                stack.damageItem(1, playerIn, (p_220009_1_) -> {
                    p_220009_1_.sendBreakAnimation(playerIn.getActiveHand());
                });
                //System.out.println(worldIn.addEntity(laserEntity));
                worldIn.addEntity(laserEntity);
            }

            worldIn.playSound(playerIn, playerIn.getPosX(), playerIn.getPosY(), playerIn.getPosZ(), RegistryHandler.LASER_PISTOL_SHOOT.get(), SoundCategory.PLAYERS, 1.0F, 1.0F / (random.nextFloat() * 0.4F + 1.2F) + f * 0.5F);

            setLoaded(itemstack, false);
            setPower(itemstack, 0);
            playerIn.addStat(Stats.ITEM_USED.get(this));
            System.out.println("balteg");
            return ActionResult.resultConsume(itemstack);
        }
        if(getPower(itemstack) == 0) {
            System.out.println("baltege");
            playerIn.setActiveHand(handIn);
            return ActionResult.resultConsume(itemstack);
        }
        System.out.println(getPower(itemstack));
        return ActionResult.resultFail(itemstack);
    }



    public static int getPower(ItemStack pewpew) {
        CompoundNBT tagCompound = pewpew.getOrCreateTag();
        return tagCompound.getInt("power");
    }
    public static boolean getLoaded(ItemStack pewpew) {
        CompoundNBT tagCompound = pewpew.getOrCreateTag();
        return tagCompound.getBoolean("loaded");
    }
    public static void setPower(ItemStack pewpew, int pow) {
        CompoundNBT tagCompound = pewpew.getOrCreateTag();
        tagCompound.putInt("power", pow);
    }
    public static void setLoaded(ItemStack pewpew, boolean loded) {
        CompoundNBT tagCompound = pewpew.getOrCreateTag();
        tagCompound.putBoolean("loaded",loded);
    }




    @Override
    public void addInformation(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
        super.addInformation(stack, world, tooltip, flag);
        tooltip.add(new StringTextComponent("Power:" + getPower(stack)));
    }

}

