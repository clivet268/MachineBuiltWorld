package com.Clivet268.MachineBuiltWorld.client.renderer;

import com.Clivet268.MachineBuiltWorld.entity.BulletEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

    @OnlyIn(Dist.CLIENT)
    public class BulletRenderer extends AbstractBulletRenderer<BulletEntity> {
        public static final ResourceLocation RES_BULLET = new ResourceLocation("textures/entity/projectiles/bullet.png");
        //public static final ResourceLocation RES_TIPPED_ARROW = new ResourceLocation("textures/entity/projectiles/tipped_arrow.png");

        public BulletRenderer(EntityRendererManager manager) {
            super(manager);
        }

        /**
         * Returns the location of an entity's texture.
         */
        public ResourceLocation getEntityTexture(BulletEntity entity) {
            //return entity.getColor() > 0 ? RES_TIPPED_ARROW : RES_ARROW;
            return RES_BULLET;
        }
    }

