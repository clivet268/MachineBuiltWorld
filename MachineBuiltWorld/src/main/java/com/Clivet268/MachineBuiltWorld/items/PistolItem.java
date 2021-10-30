package com.Clivet268.MachineBuiltWorld.items;

import com.Clivet268.MachineBuiltWorld.entity.AbstractBulletEntity;
import com.Clivet268.MachineBuiltWorld.entity.BulletEntity;
import com.Clivet268.MachineBuiltWorld.util.RegistryHandler;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.function.Predicate;
//TODO make work/exist
public class PistolItem extends ShootableItem {
    public PistolItem(Properties builder) {
        super(builder);
    }

    public Item[] bulletTypes= new Item[8];
    public static final Predicate<ItemStack> BULLETS = (p_220002_0_) -> {
        //
        //return p_220002_0_.getItem().isIn(RegistryHandler.Tags.BULLETS);
        return p_220002_0_.getItem() == RegistryHandler.BULLET.get();
    };

    public void rollitup(){
        Item[] ea= new Item[8];
        boolean ii = false;
        int iii = 0;
        for(Item i : bulletTypes)
        {
            if (ii){
                ea[iii] = bulletTypes[iii];
                iii++;
            }
            else{
                ii = true;
            }
        }
        bulletTypes = ea;
    }
    @Override
    public Predicate<ItemStack> getInventoryAmmoPredicate() {
        return BULLETS;
    }
    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.NONE;
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, LivingEntity entityLiving, int timeLeft) {
        if (entityLiving instanceof PlayerEntity) {

            PlayerEntity playerentity = (PlayerEntity)entityLiving;
            boolean flag = playerentity.abilities.isCreativeMode;
            //ItemStack itemstack = playerentity.findAmmo(stack);

            int i = this.getUseDuration(stack) - timeLeft;

            PlayerEntity player = (PlayerEntity)entityLiving;

            ItemStack ammoStack = findAmmo(player, stack);
            boolean requiresAmmo = true;
            // No ammo
            if (ammoStack.getItem() == Items.AIR) return;
            System.out.println(ammoStack);
            float f = getBulletVelocity();

            //AbstractBulletEntity bulletEntity = getBulletEntity(worldIn, player, stack, ammoStack);
            //bulletEntity.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, 5.5F, 1.0F);
            //if (velocity == 1.0F) arrowEntity.setIsCritical(true);  // just effects particles

            if (!worldIn.isRemote) {
                BulletItem bulletitem = (BulletItem) (ammoStack.getItem() instanceof BulletItem ? ammoStack.getItem() : RegistryHandler.BULLET.get());

                AbstractBulletEntity abstractBulletEntity = bulletitem.createBullet(worldIn, ammoStack, playerentity);
                abstractBulletEntity = customBullet(abstractBulletEntity);
                abstractBulletEntity.setDamage(calcDamage(stack));
                abstractBulletEntity.shoot(playerentity, playerentity.rotationPitch, playerentity.rotationYaw, 0.0F, f, 1.0F);
                stack.damageItem(1, playerentity, (p_220009_1_) -> {
                    p_220009_1_.sendBreakAnimation(playerentity.getActiveHand());
                });
                worldIn.addEntity(abstractBulletEntity);


            }
            //bulletEntity.setDamage(calcDamage(stack));

            // use ammo
            if(!player.abilities.isCreativeMode){
                ammoStack.shrink(1);
            }
            worldIn.playSound((PlayerEntity)null, player.getPosX(), player.getPosY(), player.getPosZ(), RegistryHandler.PISTOL_SHOOT.get(), SoundCategory.PLAYERS, 1.0F, 1.0F / (random.nextFloat() * 0.4F + 1.2F) + f * 0.5F);


            // reduce durability
            stack.damageItem(1, player, (onBroken) -> {
                onBroken.sendBreakAnimation(player.getActiveHand());
            });
            player.addStat(Stats.ITEM_USED.get(this));

        }
    }



    public static float getBulletVelocity() {
        float f = 10.5F;

        return f;
    }


    protected double calcDamage(ItemStack bowStack){
        double baseDamage = 2.0D;
        return baseDamage + 0.5D;
    }


    public static ItemStack getHeldAmmo(LivingEntity living, Predicate<ItemStack> isAmmo) {
        if (isAmmo.test(living.getHeldItem(Hand.OFF_HAND))) {
            return living.getHeldItem(Hand.OFF_HAND);
        } else {
            return isAmmo.test(living.getHeldItem(Hand.MAIN_HAND)) ? living.getHeldItem(Hand.MAIN_HAND) : ItemStack.EMPTY;
        }
    }

    @Nullable
    protected ItemStack findAmmo(PlayerEntity player, ItemStack shootable) {
        Predicate<ItemStack> predicate = ((PistolItem) shootable.getItem()).getInventoryAmmoPredicate();
        ItemStack itemstack = PistolItem.getHeldAmmo(player, predicate);
        if (!(itemstack.getItem() == Items.AIR) || !itemstack.isEmpty()) {
            return itemstack;
        } else {
            predicate = ((PistolItem) shootable.getItem()).getInventoryAmmoPredicate();
            for (int i = 0; i < player.inventory.getSizeInventory(); ++i) {
                ItemStack itemstack1 = player.inventory.getStackInSlot(i);
                if (predicate.test(itemstack1)) {
                    return itemstack1;
                }
            }

            return  new ItemStack(RegistryHandler.BULLET.get());
        }
    }



    /**
     * How long it takes to use or consume an item
     */
    public int getUseDuration(ItemStack stack) {
        return 72000;
    }



    public AbstractBulletEntity customBullet(AbstractBulletEntity bullet) {
        return bullet;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        ItemStack ammoStack = findAmmo(playerIn, itemstack);

        //ActionResult<ItemStack> ret = net.minecraftforge.event.ForgeEventFactory.onArrowNock(itemstack, worldIn, playerIn, handIn, ammoStack != null);
        //if (ret != null) return ret;

        if (!hasInfinity(playerIn, itemstack, ammoStack) && ammoStack == null) {
            return ActionResult.resultFail(itemstack);
        } else {
            playerIn.setActiveHand(handIn);
            return ActionResult.resultConsume(itemstack);
        }
    }

    protected boolean hasInfinity(PlayerEntity player, ItemStack bowStack, @Nullable ItemStack ammoStack){
        return player.abilities.isCreativeMode;
    }


    protected AbstractBulletEntity getBulletEntity(World world, PlayerEntity player, ItemStack bowStack, ItemStack ammoStack){
        BulletEntity bulletEntity = new BulletEntity(world, player);
        //arrowentity.setPotionEffect(ammoStack);
        return bulletEntity;
    }


}

