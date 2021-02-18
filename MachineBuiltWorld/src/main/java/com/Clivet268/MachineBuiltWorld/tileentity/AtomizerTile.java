package com.Clivet268.MachineBuiltWorld.tileentity;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static com.Clivet268.MachineBuiltWorld.util.RegistryHandler.ATOMIZER_TILE;

//import static com.Clivet268.MachineBuiltWorld.tileentity.TileEntityHandler.ATOMIZERTILE;

public class AtomizerTile extends TileEntity implements ITickableTileEntity {


    private int counter;
    private ItemStackHandler itemHandler = createHandler();
    private LazyOptional<IItemHandler> handler = LazyOptional.of(() -> itemHandler);


    public AtomizerTile() {
        super(ATOMIZER_TILE.get());
        // super(TileEntityRegistry.ATOMIZER);

    }
    private Entity getThePlayer(LivingEvent.LivingJumpEvent event)
    {
        Entity player = event.getEntity();
        return player;
    }

    public void tick() {
        //if (this.world != null && !this.world.isRemote && this.world.getGameTime() % 20L == 0L) {
          //  this.spawnParticles();


            if (counter <= 0) {
                ItemStack stack = itemHandler.getStackInSlot(0);
                if (stack.getItem() == Items.DIAMOND) {
                    itemHandler.extractItem(0, 1, false);
                    counter = 200;
                            //Config.FIRSTBLOCK_TICKS.get();
                    markDirty();

                }
            }
            if (counter > 0) {
                counter--;
                markDirty();
            }
            if (counter > 0){


                    String msg = TextFormatting.DARK_AQUA + "Shwoop";
                    System.out.println(msg);

                }
            }
         //   }



    @Override
    public void read(CompoundNBT tag) {
        itemHandler.deserializeNBT(tag.getCompound("inv"));

        counter = tag.getInt("counter");
        super.read(tag);
    }

    @Override
    public CompoundNBT write(CompoundNBT tag) {
        tag.put("inv", itemHandler.serializeNBT());

        tag.putInt("counter", counter);
        return super.write(tag);
    }
    private ItemStackHandler createHandler() {
        return new ItemStackHandler(1) {

            @Override
            protected void onContentsChanged(int slot) {
                // To make sure the TE persists when the chunk is saved later we need to
                // mark it dirty every time the item handler changes
                markDirty();
            }

            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                return stack.getItem() == Items.DIAMOND;
            }

            @Nonnull
            @Override
            public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
                if (stack.getItem() != Items.DIAMOND) {
                    return stack;
                }
                return super.insertItem(slot, stack, simulate);
            }
        };
    }
    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return handler.cast();
        }

        return super.getCapability(cap, side);
    }
}
    /*
    private void spawnParticles() {
        Random random = this.world.rand;

        Vec3d vec3d = new Vec3d((double)this.pos.getX() + 0.5D, (double)this.pos.getY() + 1.5D, (double)this.pos.getZ() + 0.5D);

        for(BlockPos blockpos : this.prismarinePositions) {
            if (random.nextInt(50) == 0) {
                float f = -0.5F + random.nextFloat();
                float f1 = -2.0F + random.nextFloat();
                float f2 = -0.5F + random.nextFloat();
                BlockPos blockpos1 = blockpos.subtract(this.pos);
                Vec3d vec3d1 = (new Vec3d((double)f, (double)f1, (double)f2)).add((double)blockpos1.getX(), (double)blockpos1.getY(), (double)blockpos1.getZ());
                this.world.addParticle(ParticleTypes.NAUTILUS, vec3d.x, vec3d.y, vec3d.z, vec3d1.x, vec3d1.y, vec3d1.z);
            }
        }

        if (this.target != null) {
            Vec3d vec3d2 = new Vec3d(this.target.getPosX(), this.target.getPosYEye(), this.target.getPosZ());
            float f3 = (-0.5F + random.nextFloat()) * (3.0F + this.target.getWidth());
            float f4 = -1.0F + random.nextFloat() * this.target.getHeight();
            float f5 = (-0.5F + random.nextFloat()) * (3.0F + this.target.getWidth());
            Vec3d vec3d3 = new Vec3d((double)f3, (double)f4, (double)f5);
            this.world.addParticle(ParticleTypes.NAUTILUS, vec3d2.x, vec3d2.y, vec3d2.z, vec3d3.x, vec3d3.y, vec3d3.z);
        }

    }
    private LivingEntity findExistingTarget() {
        List<LivingEntity> list = this.world.getEntitiesWithinAABB(LivingEntity.class, this.getAreaOfEffect(), (p_205032_1_) -> {
            return p_205032_1_.getUniqueID().equals(this.targetUuid);
        });
        return list.size() == 1 ? list.get(0) : null;
    }
}

*/
