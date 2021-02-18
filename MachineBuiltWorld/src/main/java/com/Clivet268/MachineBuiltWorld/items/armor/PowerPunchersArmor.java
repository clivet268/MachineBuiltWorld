package com.Clivet268.MachineBuiltWorld.items.armor;

import com.Clivet268.MachineBuiltWorld.ModelPowerPunchers;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.LivingEntity;

import javax.annotation.Nonnull;

public class PowerPunchersArmor extends BipedModel<LivingEntity> {
   // public boolean babayHead =

        public static final PowerPunchersArmor POWER_PUNCHERS = new PowerPunchersArmor(0.5F);
        private static final ModelPowerPunchers model = new ModelPowerPunchers();

        public PowerPunchersArmor(float size) {
            super(size);
        }

        //@Override
        public void render(@Nonnull MatrixStack matrix, @Nonnull IRenderTypeBuffer renderer, int light, int overlayLight, boolean hasEffect) {
            if (!bipedHead.showModel) {
                //If the head model shouldn't show don't bother displaying it
                return;
            }
            else {
                renderMask(matrix, renderer, light, overlayLight, hasEffect);
            }
        }

        private void renderMask(@Nonnull MatrixStack matrix, @Nonnull IRenderTypeBuffer renderer, int light, int overlayLight, boolean hasEffect) {
            matrix.push();
            bipedHead.translateRotate(matrix);
            matrix.translate(0, 0, 0.01);
            model.render(matrix, renderer, light, overlayLight, hasEffect);
            matrix.pop();
        }
    }

