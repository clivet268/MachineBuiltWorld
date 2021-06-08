package com.Clivet268.MachineBuiltWorld.client.renderer;

import com.Clivet268.MachineBuiltWorld.MachineBuiltWorld;
import com.Clivet268.MachineBuiltWorld.entity.SprocketeerEntity;
import com.Clivet268.MachineBuiltWorld.client.model.SprocketeerModel;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SprocketeerRenderer extends MobRenderer<SprocketeerEntity, SprocketeerModel<SprocketeerEntity>> {
   private static final ResourceLocation TEXTURES = new ResourceLocation(MachineBuiltWorld.MOD_ID,"textures/entity/sprocketeer.png");

   public SprocketeerRenderer(EntityRendererManager renderManagerIn) {
      super(renderManagerIn, new SprocketeerModel<>(),0.6f);
   }

   @Override
   public ResourceLocation getEntityTexture(SprocketeerEntity entity) {
      return TEXTURES;
   }

}
