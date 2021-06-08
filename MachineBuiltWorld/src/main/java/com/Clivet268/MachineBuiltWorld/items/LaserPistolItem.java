package com.Clivet268.MachineBuiltWorld.items;

import com.Clivet268.MachineBuiltWorld.entity.AbstractLaserEntity;
import com.Clivet268.MachineBuiltWorld.entity.LaserEntity;
import com.Clivet268.MachineBuiltWorld.util.RegistryHandler;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ShootableItem;
import net.minecraft.item.UseAction;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Predicate;

public class LaserPistolItem extends ShootableItem implements IReloadable{
    public LaserPistolItem(Properties builder) {
        super(builder);
    }

    public boolean isReloadable(PlayerEntity player){
        return true;
    }


    public static final Predicate<ItemStack> LASER_MAGS = (p_220002_0_) -> {
        return p_220002_0_.getItem() == RegistryHandler.LASER_PISTOL_MAG.get();
    };
    @Override
    public Predicate<ItemStack> getInventoryAmmoPredicate() {
        return LASER_MAGS;
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.NONE;
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, LivingEntity entityLiving, int timeLeft) {
        if (entityLiving instanceof PlayerEntity) {

            System.out.println("eee");
            PlayerEntity playerentity = (PlayerEntity)entityLiving;
            boolean flag = playerentity.abilities.isCreativeMode;
            if(getBulletAmount(stack) <= 0)
            {
                if(!flag) {
                    System.out.println("no ammo");
                    return;
                }
            }
            PlayerEntity player = (PlayerEntity)entityLiving;
            float f = getBulletVelocity();

            if (!worldIn.isRemote) {
                AbstractLaserEntity laserEntity = createLaser(worldIn, playerentity);
                laserEntity.setDamage(calcDamage(stack));
                laserEntity.shoot(playerentity, playerentity.rotationPitch, playerentity.rotationYaw, 0.0F, f, 1.0F);
                stack.damageItem(1, playerentity, (p_220009_1_) -> {
                    p_220009_1_.sendBreakAnimation(playerentity.getActiveHand());
                });
                System.out.println(getBulletAmount(stack) + " left");
                //System.out.println(worldIn.addEntity(laserEntity));
                worldIn.addEntity(laserEntity);
            }
            // use ammo
            if(!flag){
                setStuffAmount(stack,getBulletAmount(stack) -1, getMagInOrOut(stack));
            }
            worldIn.playSound(null, player.getPosX(), player.getPosY(), player.getPosZ(), RegistryHandler.LASER_PISTOL_SHOOT.get(), SoundCategory.PLAYERS, 1.0F, 1.0F / (random.nextFloat() * 0.4F + 1.2F) + f * 0.5F);


            // reduce durability
            stack.damageItem(1, player, (onBroken) -> {
                onBroken.sendBreakAnimation(player.getActiveHand());
            });
            player.addStat(Stats.ITEM_USED.get(this));

        }
    }



    public static float getBulletVelocity() {

        return 30.5F;
    }


    protected double calcDamage(ItemStack bowStack){
        double baseDamage = 2.0D;
        return baseDamage + 0.5D;
    }

    @Nullable
    public ItemStack findAmmo(PlayerEntity player) {
            for (int i = 0; i < player.inventory.getSizeInventory(); ++i) {
                ItemStack itemstack1 = player.inventory.getStackInSlot(i);
                if (itemstack1.getItem() == ((LaserPisolMagItem)RegistryHandler.LASER_PISTOL_MAG.get())) {
                    if(((LaserPisolMagItem)(itemstack1.getItem())).getBulletAmount(itemstack1) > 0)
                    return  itemstack1;
                }
            }
            return new ItemStack(Items.AIR);
    }



    /**
     * How long it takes to use or consume an item
     */
    public int getUseDuration(ItemStack stack) {
        return 7200;
    }

    public void reload(PlayerEntity playerIn) {
        ItemStack ammoStack = findAmmo(playerIn);
        ItemStack itemstack = playerIn.getHeldItemMainhand();
        if (ammoStack.getItem() == RegistryHandler.LASER_PISTOL_MAG.get()) {
            if (getMagInOrOut(itemstack)) {
                ItemStack itemstack1 = new ItemStack(RegistryHandler.LASER_PISTOL_MAG.get());
                ((LaserPisolMagItem) itemstack1.getItem()).setBulletAmount(itemstack1, getBulletAmount(itemstack));
                setStuffAmount(itemstack, ((LaserPisolMagItem) ammoStack.getItem()).getBulletAmount(ammoStack), true);
                System.out.println(":)");
                playerIn.dropItem(itemstack1, false);
            } else {
                setStuffAmount(itemstack, ((LaserPisolMagItem) ammoStack.getItem()).getBulletAmount(ammoStack), true);
            }
            playerIn.world.playSound(playerIn,playerIn.getPosition(), RegistryHandler.SPROCKETEER_STEP.get(), SoundCategory.PLAYERS, 100, 1);
            ammoStack.shrink(1);

        }
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
            playerIn.setActiveHand(handIn);
            return ActionResult.resultSuccess(playerIn.getHeldItem(handIn));
    }


    public AbstractLaserEntity createLaser(World worldIn, LivingEntity shooter) {
        return new LaserEntity(worldIn, shooter);
    }



    private static void setStuffAmount(ItemStack pewpew, int bullets, boolean maginout) {
        //Store the tool's mode in NBT as a string
        CompoundNBT tagCompound = pewpew.getOrCreateTag();
        tagCompound.putInt("ammo", bullets);
        tagCompound.putBoolean("mag", maginout);
    }

    public static int getBulletAmount(ItemStack pewpew) {
        CompoundNBT tagCompound = pewpew.getOrCreateTag();
        return tagCompound.getInt("ammo");
    }
    public static boolean getMagInOrOut(ItemStack pewpew) {
        CompoundNBT tagCompound = pewpew.getOrCreateTag();
        return tagCompound.getBoolean("mag");
    }


    @Override
    public void addInformation(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
        super.addInformation(stack, world, tooltip, flag);
        tooltip.add(new StringTextComponent("Ammo " + getBulletAmount(stack)));
    }

}

