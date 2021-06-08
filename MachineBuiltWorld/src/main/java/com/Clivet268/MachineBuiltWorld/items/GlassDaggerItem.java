package com.Clivet268.MachineBuiltWorld.items;

import com.Clivet268.MachineBuiltWorld.MachineBuiltWorld;
import com.Clivet268.MachineBuiltWorld.entity.AbstractLaserEntity;
import com.Clivet268.MachineBuiltWorld.entity.LaserEntity;
import com.Clivet268.MachineBuiltWorld.util.RegistryHandler;
import com.google.common.collect.Multimap;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.stats.Stats;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.client.model.data.IDynamicBakedModel;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Predicate;

public class GlassDaggerItem extends ShootableItem implements IReloadable{
    private final float attackDamage;
    private final float attackSpeed;
    public GlassDaggerItem(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties builder) {
        super(builder);
        this.attackSpeed = attackSpeedIn;
        this.attackDamage = (float)attackDamageIn + tier.getAttackDamage();
        this.addPropertyOverride(new ResourceLocation(MachineBuiltWorld.MOD_ID, "ammo"), new IItemPropertyGetter() {
            @Override
            public float call(ItemStack p_call_1_, @Nullable World p_call_2_, @Nullable LivingEntity p_call_3_) {
                if(getLoaded(p_call_1_)){
                    return 0.1f;
                }
                else{
                    return 0.0f;
                }
            }
        });

    }

    public boolean isReloadable(PlayerEntity player){
        return getLoaded(player.getHeldItemMainhand());
    }

    @Override
    public Multimap<String, AttributeModifier> getAttributeModifiers(EquipmentSlotType equipmentSlot) {
        Multimap<String, AttributeModifier> multimap = super.getAttributeModifiers(equipmentSlot);
        if (equipmentSlot == EquipmentSlotType.MAINHAND) {
            multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", (double)this.attackDamage, AttributeModifier.Operation.ADDITION));
            multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", (double)this.attackSpeed, AttributeModifier.Operation.ADDITION));
        }

        return multimap;
    }
    @Override
    public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.damageItem(1, attacker, (p_220045_0_) -> {
            p_220045_0_.sendBreakAnimation(EquipmentSlotType.MAINHAND);
        });
        return true;
    }

    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state) {
        Block block = state.getBlock();
        if (block == Blocks.COBWEB) {
            return 15.0F;
        } else {
            Material material = state.getMaterial();
            return material != Material.PLANTS && material != Material.TALL_PLANTS && material != Material.CORAL && !state.isIn(BlockTags.LEAVES) && material != Material.GOURD ? 1.0F : 1.5F;
        }
    }
    public static final Predicate<ItemStack> LASER_BULLETS = (p_220002_0_) -> {
        return p_220002_0_.getItem() == RegistryHandler.LASER_SHELL.get();
    };
    @Override
    public Predicate<ItemStack> getInventoryAmmoPredicate() {
        return LASER_BULLETS;
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
            if(!getLoaded(stack))
            {
                System.out.println("no ammo");
                if(!flag){
                    return;
                }
            }
            PlayerEntity player = (PlayerEntity)entityLiving;
            float f = getBulletVelocity();

            if (!worldIn.isRemote) {
                AbstractLaserEntity laserEntity = createLaser(worldIn, playerentity);
                laserEntity.setDamage(calcDamage());
                laserEntity.shoot(playerentity, playerentity.rotationPitch, playerentity.rotationYaw, 0.0F, f, 1.0F);
                stack.damageItem(1, playerentity, (p_220009_1_) -> {
                    p_220009_1_.sendBreakAnimation(playerentity.getActiveHand());
                });
                //System.out.println(worldIn.addEntity(laserEntity));
                worldIn.addEntity(laserEntity);
            }
            //IDynamicBakedModel
            // use ammo
            if(!flag){
                setStuffAmount(stack, false);
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


    protected double calcDamage(){
        return this.attackDamage;
    }

    @Nullable
    public ItemStack findAmmo(PlayerEntity player) {
            for (int i = 0; i < player.inventory.getSizeInventory(); ++i) {
                ItemStack itemstack1 = player.inventory.getStackInSlot(i);
                if (itemstack1.getItem() == RegistryHandler.LASER_SHELL.get()) {
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
        if(getLoaded(playerIn.getHeldItemMainhand())) {
            ItemStack ammoStack = findAmmo(playerIn);
            ItemStack itemstack = playerIn.getHeldItemMainhand();
            if (ammoStack.getItem() == RegistryHandler.LASER_SHELL.get()) {
                setStuffAmount(itemstack,true);
                playerIn.world.playSound(playerIn, playerIn.getPosition(), RegistryHandler.SPROCKETEER_STEP.get(), SoundCategory.PLAYERS, 100, 1);
                ammoStack.shrink(1);

            }
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



    private static void setStuffAmount(ItemStack pewpew, boolean loaded) {
        //Store the tool's mode in NBT as a string
        CompoundNBT tagCompound = pewpew.getOrCreateTag();
        tagCompound.putBoolean("loaded", loaded);
    }

    public static boolean getLoaded(ItemStack pewpew) {
        CompoundNBT tagCompound = pewpew.getOrCreateTag();
        return tagCompound.getBoolean("loaded");
    }


    @Override
    public void addInformation(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
        super.addInformation(stack, world, tooltip, flag);
        int i = 0;
        if ((getLoaded(stack))){
            i=1;
        }
        tooltip.add(new StringTextComponent("Ammo " + i));
    }

}

