package com.Clivet268.MachineBuiltWorld.util.Renderer;

import com.Clivet268.MachineBuiltWorld.entity.LaserEntity;
import com.Clivet268.MachineBuiltWorld.util.RegistryHandler;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class LaserRenderer extends AbstractLaserRenderer<LaserEntity> {
    public static final ResourceLocation RES_BULLET = new ResourceLocation("textures/entity/projectiles/bullet.png");
    //public static final ResourceLocation RES_TIPPED_ARROW = new ResourceLocation("textures/entity/projectiles/tipped_arrow.png");

    public LaserRenderer(RegistryHandler.MoreEntityRendererManager manager) {
        super(manager);
    }

    /**
     * Returns the location of an entity's texture.
     */
    @Override
    public ResourceLocation getEntityTexture(LaserEntity entity) {
        //return entity.getColor() > 0 ? RES_TIPPED_ARROW : RES_ARROW;
        return RES_BULLET;
    }

}

