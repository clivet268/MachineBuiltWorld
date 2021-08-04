package com.Clivet268.MachineBuiltWorld.client.model.entity;

import com.Clivet268.MachineBuiltWorld.entity.SprocketeerEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
//TODO arm clipping
public class SprocketeerModel<T extends SprocketeerEntity> extends EntityModel<T> {
    private final ModelRenderer bb_main;
    private final ModelRenderer cube_r1;
    private final ModelRenderer cube_r2;
    private final ModelRenderer cube_r3;
    private final ModelRenderer cube_r5;
    private final ModelRenderer head;
    private final ModelRenderer frl;
    private final ModelRenderer fll;
    private final ModelRenderer bll;
    private final ModelRenderer brl;

    public SprocketeerModel() {
        textureWidth = 32;
        textureHeight = 32;

        bb_main = new ModelRenderer(this);
        bb_main.setRotationPoint(0.0F, 24.0F, 0.0F);


        cube_r1 = new ModelRenderer(this);
        cube_r1.setRotationPoint(-6.567F, -6.5F, 0.5F);
        bb_main.addChild(cube_r1);
        setRotationAngle(cube_r1, -0.1745F, 0.0F, 0.5236F);
        cube_r1.setTextureOffset(0, 4).addBox(0.657F, -0.53F, -4.32F, 0.0F, 1.0F, 5.0F, 0.0F, false);

        cube_r2 = new ModelRenderer(this);
        cube_r2.setRotationPoint(6.433F, -6.5F, 0.5F);
        bb_main.addChild(cube_r2);
        setRotationAngle(cube_r2, -0.1745F, 0.0F, -0.5236F);
        cube_r2.setTextureOffset(0, 3).addBox(-0.043F, -0.335F, -4.47F, 0.0F, 1.0F, 5.0F, 0.0F, false);

        cube_r3 = new ModelRenderer(this);
        cube_r3.setRotationPoint(-3.5F, -3.5F, 1.0F);
        bb_main.addChild(cube_r3);
        setRotationAngle(cube_r3, -0.1745F, 0.0F, 0.5236F);
        cube_r3.setTextureOffset(10, 8).addBox(-3.5F, -1.5F, 0.0F, 6.0F, 1.0F, 0.0F, 0.0F, false);

        cube_r5 = new ModelRenderer(this);
        cube_r5.setRotationPoint(3.5F, -4.5F, 1.0F);
        bb_main.addChild(cube_r5);
        setRotationAngle(cube_r5, -0.1745F, 0.0F, -0.5236F);
        cube_r5.setTextureOffset(0, 10).addBox(-2.5F, -0.5F, 0.0F, 6.0F, 1.0F, 0.0F, 0.0F, false);

        head = new ModelRenderer(this);
        head.setRotationPoint(0.0F, -3.5F, 0.0F);
        bb_main.addChild(head);
        setRotationAngle(head, -0.1745F, 0.0F, 0.0F);
        head.setTextureOffset(0, 0).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F, 0.0F, false);

        frl = new ModelRenderer(this);
        frl.setRotationPoint(-1.75F, 24.0F, -1.75F);
        frl.setTextureOffset(0, 0).addBox(-0.5F, -2.0F, -1.0F, 1.0F, 2.0F, 1.0F, 0.0F, false);

        fll = new ModelRenderer(this);
        fll.setRotationPoint(1.75F, 24.0F, -1.75F);
        fll.setTextureOffset(0, 11).addBox(-0.5F, -2.0F, -1.0F, 1.0F, 2.0F, 1.0F, 0.0F, false);

        bll = new ModelRenderer(this);
        bll.setRotationPoint(1.75F, 24.0F, 1.25F);
        bll.setTextureOffset(4, 11).addBox(-0.5F, -2.0F, 0.0F, 1.0F, 2.0F, 1.0F, 0.0F, false);

        brl = new ModelRenderer(this);
        brl.setRotationPoint(-1.75F, 24.0F, 1.25F);
        brl.setTextureOffset(8, 11).addBox(-0.5F, -2.0F, 0.0F, 1.0F, 2.0F, 1.0F, 0.0F, false);
    }

    @Override
    public void setRotationAngles(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
        //previously the render function, render code was moved to a method below
    }

    @Override
    public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        bb_main.render(matrixStack, buffer, packedLight, packedOverlay);
        frl.render(matrixStack, buffer, packedLight, packedOverlay);
        fll.render(matrixStack, buffer, packedLight, packedOverlay);
        bll.render(matrixStack, buffer, packedLight, packedOverlay);
        brl.render(matrixStack, buffer, packedLight, packedOverlay);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}



