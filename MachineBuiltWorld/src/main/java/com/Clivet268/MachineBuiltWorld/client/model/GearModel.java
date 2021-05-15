package com.Clivet268.MachineBuiltWorld.client.model;

import com.Clivet268.MachineBuiltWorld.MachineBuiltWorld;
import com.Clivet268.MachineBuiltWorld.entity.GearEntity;
import com.Clivet268.MachineBuiltWorld.entity.LaserEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.ResourceLocation;

public class GearModel<T extends GearEntity> extends Model{
    private final ModelRenderer bb_main;

    public GearModel() {
        super(RenderType::getEntitySolid);
        textureWidth = 16;
        textureHeight = 16;

        bb_main = new ModelRenderer(this);
        bb_main.setRotationPoint(0.0F, 24.0F, 0.0F);
        bb_main.setTextureOffset(0, 0).addBox(-1.5F, 0.0F, -1.5F, 3.0F, 0.0F, 3.0F, 0.0F, false);
    }

    @Override
    public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        bb_main.render(matrixStack, buffer, packedLight, packedOverlay);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
