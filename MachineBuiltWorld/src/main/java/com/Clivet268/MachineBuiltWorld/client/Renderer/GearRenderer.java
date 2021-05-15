package com.Clivet268.MachineBuiltWorld.client.Renderer;

import com.Clivet268.MachineBuiltWorld.MachineBuiltWorld;
import com.Clivet268.MachineBuiltWorld.entity.GearEntity;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GearRenderer extends EntityRenderer<GearEntity> {
   public static final ResourceLocation RES_GEAR = new ResourceLocation(MachineBuiltWorld.MOD_ID,"textures/entity/projectiles/gear.png");
   public static final ResourceLocation RES_GEAR_SPIN = new ResourceLocation(MachineBuiltWorld.MOD_ID,"textures/entity/projectiles/gear_spin.png");




   public GearRenderer(EntityRendererManager renderManagerIn) {
      super(renderManagerIn);
   }

   @Override
   public ResourceLocation getEntityTexture(GearEntity entity) {
      return entity.isInGround() ? RES_GEAR : RES_GEAR_SPIN;
   }

}
