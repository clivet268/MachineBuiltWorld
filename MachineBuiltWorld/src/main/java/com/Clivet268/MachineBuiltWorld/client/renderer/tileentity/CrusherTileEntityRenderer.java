package com.Clivet268.MachineBuiltWorld.client.renderer.tileentity;

import com.Clivet268.MachineBuiltWorld.MachineBuiltWorld;
import com.Clivet268.MachineBuiltWorld.tileentity.CrusherTile;
import com.Clivet268.MachineBuiltWorld.util.RegistryHandler;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.Material;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.ClientRegistry;

@OnlyIn(Dist.CLIENT)
//TODO fix this idk lol
    public class CrusherTileEntityRenderer extends TileEntityRenderer<CrusherTile> {
        public static final Material BELL_BODY_TEXTURE = new Material(AtlasTexture.LOCATION_BLOCKS_TEXTURE, new ResourceLocation(MachineBuiltWorld.MOD_ID, "entity/tileentity/crusher"));
        private final  ModelRenderer bone = new ModelRenderer(64,64,0,0);
        private final ModelRenderer cube_r1= new ModelRenderer(64,64,0,0);;
        private final ModelRenderer cube_r2= new ModelRenderer(64,64,0,0);;
        private final ModelRenderer cube_r3= new ModelRenderer(64,64,0,0);;
        public CrusherTileEntityRenderer(TileEntityRendererDispatcher p_i226005_1_) {
            super(p_i226005_1_);
            bone.setRotationPoint(-4.5F, 13.5F, 10.0F);
            bone.setTextureOffset(0, 0).addBox(-1.5F, -1.5F, -17.0F, 3.0F, 3.0F, 14.0F, 0.0F, false);
            bone.setTextureOffset(12, 17).addBox(1.5F, -0.75F, -16.0F, 1.0F, 2.0F, 2.0F, 0.0F, false);
            bone.setTextureOffset(6, 17).addBox(1.5F, -1.0F, -11.0F, 1.0F, 2.0F, 2.0F, 0.0F, false);
            bone.setTextureOffset(0, 17).addBox(1.5F, -1.25F, -6.0F, 1.0F, 2.0F, 2.0F, 0.0F, false);

            cube_r1.setRotationPoint(0.0F, 0.0F, 0.0F);
            bone.addChild(cube_r1);
            setRotationAngle(cube_r1, 0.0F, 0.0F, 1.5708F);
            cube_r1.setTextureOffset(0, 0).addBox(1.5F, -1.25F, -6.0F, 1.0F, 2.0F, 2.0F, 0.0F, false);
            cube_r1.setTextureOffset(0, 4).addBox(1.5F, -1.0F, -11.0F, 1.0F, 2.0F, 2.0F, 0.0F, false);
            cube_r1.setTextureOffset(4, 2).addBox(1.5F, -0.75F, -16.0F, 1.0F, 2.0F, 2.0F, 0.0F, false);

            cube_r2.setRotationPoint(0.0F, 0.0F, 0.0F);
            bone.addChild(cube_r2);
            setRotationAngle(cube_r2, 0.0F, 0.0F, -3.1416F);
            cube_r2.setTextureOffset(4, 6).addBox(1.5F, -1.25F, -6.0F, 1.0F, 2.0F, 2.0F, 0.0F, false);
            cube_r2.setTextureOffset(0, 8).addBox(1.5F, -1.0F, -11.0F, 1.0F, 2.0F, 2.0F, 0.0F, false);
            cube_r2.setTextureOffset(8, 8).addBox(1.5F, -0.75F, -16.0F, 1.0F, 2.0F, 2.0F, 0.0F, false);

            cube_r3.setRotationPoint(0.0F, 0.0F, 0.0F);
            bone.addChild(cube_r3);
            setRotationAngle(cube_r3, 0.0F, 0.0F, -1.5708F);
            cube_r3.setTextureOffset(8, 0).addBox(1.5F, -1.25F, -6.0F, 1.0F, 2.0F, 2.0F, 0.0F, false);
            cube_r3.setTextureOffset(8, 4).addBox(1.5F, -1.0F, -11.0F, 1.0F, 2.0F, 2.0F, 0.0F, false);
            cube_r3.setTextureOffset(4, 10).addBox(1.5F, -0.75F, -16.0F, 1.0F, 2.0F, 2.0F, 0.0F, false);

        }

        public void render(CrusherTile tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
            //float f = (float)tileEntityIn.ringingTicks + partialTicks;
            float f1 = 0.0F;
            float f2 = 0.0F;
            /*if (tileEntityIn.isRinging) {
                float f3 = MathHelper.sin(f / (float)Math.PI) / (4.0F + f / 3.0F);
                if (tileEntityIn.ringDirection == Direction.NORTH) {
                    f1 = -f3;
                } else if (tileEntityIn.ringDirection == Direction.SOUTH) {
                    f1 = f3;
                } else if (tileEntityIn.ringDirection == Direction.EAST) {
                    f2 = -f3;
                } else if (tileEntityIn.ringDirection == Direction.WEST) {
                    f2 = f3;
                }
            }

             */

            /*this.onecrush.rotateAngleX = f1;
            this.onecrush.rotateAngleZ = f2;

             */
            IVertexBuilder ivertexbuilder = BELL_BODY_TEXTURE.getBuffer(bufferIn, RenderType::getEntitySolid);
            this.bone.render(matrixStackIn, ivertexbuilder, combinedLightIn, combinedOverlayIn);
            this.bone.render(matrixStackIn, ivertexbuilder, combinedLightIn, combinedOverlayIn);
        }
    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
    public static void register() {
        ClientRegistry.bindTileEntityRenderer(RegistryHandler.CRUSHER_TILE.get(), CrusherTileEntityRenderer::new);
    }
}




