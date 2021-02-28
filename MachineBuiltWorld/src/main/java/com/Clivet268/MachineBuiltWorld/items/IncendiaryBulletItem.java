package com.Clivet268.MachineBuiltWorld.items;

import com.Clivet268.MachineBuiltWorld.entity.AbstractBulletEntity;
import com.Clivet268.MachineBuiltWorld.entity.BulletEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class IncendiaryBulletItem extends Item {
    public IncendiaryBulletItem(Properties builder) {
        super(builder);
    }

    public AbstractBulletEntity createBullet(World worldIn, ItemStack stack, LivingEntity shooter) {
        BulletEntity bulletEntity = new BulletEntity(worldIn, shooter);
        return bulletEntity;
    }
}
