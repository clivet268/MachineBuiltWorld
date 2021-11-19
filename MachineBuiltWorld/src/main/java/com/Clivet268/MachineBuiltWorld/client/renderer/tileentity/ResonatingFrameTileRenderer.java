package com.Clivet268.MachineBuiltWorld.client.renderer.tileentity;

import com.Clivet268.MachineBuiltWorld.MachineBuiltWorld;
import com.Clivet268.MachineBuiltWorld.client.renderer.MoreRenderType;
import com.Clivet268.MachineBuiltWorld.tileentity.ResonatingFrameTileEntity;
import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Matrix4f;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.tileentity.EndPortalTileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

@OnlyIn(Dist.CLIENT)
public class ResonatingFrameTileRenderer extends TileEntityRenderer<ResonatingFrameTileEntity>{
        public static final ResourceLocation END_SKY_TEXTURE = new ResourceLocation(MachineBuiltWorld.MOD_ID,"textures/environment/tmbw_doom_sky.png");
        public static final ResourceLocation END_PORTAL_TEXTURE = new ResourceLocation(MachineBuiltWorld.MOD_ID,"textures/entity/end_portal.png");
        private static final Random RANDOM = new Random(31100L);
        private static final List<RenderType> RENDER_TYPES = IntStream.range(0, 16).mapToObj((p_228882_0_) -> {
            return MoreRenderType.getResonatingFrame(p_228882_0_ + 1);
        }).collect(ImmutableList.toImmutableList());

        public ResonatingFrameTileRenderer(TileEntityRendererDispatcher rendererDispatcherIn) {
            super(rendererDispatcherIn);
        }

        public void render(ResonatingFrameTileEntity tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
            RANDOM.setSeed(31100L);
            double d0 = tileEntityIn.getPos().distanceSq(this.renderDispatcher.renderInfo.getProjectedView(), true);
            int i = this.getPasses(d0);

            Matrix4f matrix4f = matrixStackIn.getLast().getMatrix();
            this.renderCuboid(tileEntityIn,matrix4f, bufferIn.getBuffer(RENDER_TYPES.get(0)), 0.15F,-8.5F, 44F, 24.5F, 46F,7F,9.5F);
            this.renderCuboid(tileEntityIn,matrix4f, bufferIn.getBuffer(RENDER_TYPES.get(0)), 0.15F,-8.5F, 2F,-6.5F, 44, 7F,9.5F);
            this.renderCuboid(tileEntityIn,matrix4f, bufferIn.getBuffer(RENDER_TYPES.get(0)), 0.15F,22.5F, 2F,24.5F, 44F,7F,9.5F);
            this.renderCuboid(tileEntityIn,matrix4f, bufferIn.getBuffer(RENDER_TYPES.get(0)), 0.15F,-8.5F, 0F,24.5F, 2,7F,9.5F);

            for(int j = 1; j < i; ++j) {
                float f = 2.0F/(float)(18-j);
                this.renderCuboid(tileEntityIn,matrix4f, bufferIn.getBuffer(RENDER_TYPES.get(j)), f,-8.5F, 44F, 24.5F, 46F,7F,9.5F);
                this.renderCuboid(tileEntityIn,matrix4f, bufferIn.getBuffer(RENDER_TYPES.get(j)), f,-8.5F, 2F,-6.5F, 44, 7F,9.5F);
                this.renderCuboid(tileEntityIn,matrix4f, bufferIn.getBuffer(RENDER_TYPES.get(j)), f,22.5F, 2F,24.5F, 44F,7F,9.5F);
                this.renderCuboid(tileEntityIn,matrix4f, bufferIn.getBuffer(RENDER_TYPES.get(j)), f,-8.5F, 0F,24.5F, 2,7F,9.5F);
            }



        }
        private void renderCuboid(ResonatingFrameTileEntity tileEntityIn, Matrix4f matrix, IVertexBuilder vertexBuilder, float modif, float x1, float y1, float x2, float y2,float z1, float z2){
            x1/=16.0F;
            x2/=16.0F;
            y2/=16.0F;
            y1/=16.0F;
            z1/=16.0F;
            z2/=16.0F;
            float f = (RANDOM.nextFloat() * 0.5F + 0.1F) * modif;
            float f1 = (RANDOM.nextFloat() * 0.5F + 0.4F) * modif;
            float f2 = (RANDOM.nextFloat() * 0.5F + 0.5F) * modif;
            this.renderFace(tileEntityIn, matrix, vertexBuilder, x1, x2, y1, y2, z2, z2, f, f1, f2, Direction.SOUTH);
            this.renderFace(tileEntityIn, matrix, vertexBuilder, x1, x2, y2, y1, z1, z1, f, f1, f2, Direction.NORTH);
            this.renderFace(tileEntityIn, matrix, vertexBuilder, x2, x2, y2, y1, z1, z2, f, f1, f2, Direction.EAST);
            this.renderFace(tileEntityIn, matrix, vertexBuilder, x1, x1, y1, y2, z1, z2, f, f1, f2, Direction.WEST);
            this.renderFace(tileEntityIn, matrix, vertexBuilder, x1, x2, y1, y1, z1, z2, f, f1, f2, Direction.DOWN);
            this.renderFace(tileEntityIn, matrix, vertexBuilder, x1, x2, y2, y2, z1, z2, f, f1, f2, Direction.UP);
        }


        private void renderFace(ResonatingFrameTileEntity tileEntityIn, Matrix4f matrix, IVertexBuilder vertexBuilder,
                                float x1, float x2, float y1, float y2, float z0,
                                float z1, float red, float green, float blue, Direction p_228884_15_) {
                vertexBuilder.pos(matrix, x1, y1, z0).color(red, green, blue, 1.0F).endVertex();
                vertexBuilder.pos(matrix, x2, y1, z1).color(red, green, blue, 1.0F).endVertex();
                vertexBuilder.pos(matrix, x2, y2, z1).color(red, green, blue, 1.0F).endVertex();
                vertexBuilder.pos(matrix, x1, y2, z0).color(red, green, blue, 1.0F).endVertex();
        }

        protected int getPasses(double p_191286_1_) {
            if (p_191286_1_ > 36864.0D) {
                return 1;
            } else if (p_191286_1_ > 25600.0D) {
                return 3;
            } else if (p_191286_1_ > 16384.0D) {
                return 5;
            } else if (p_191286_1_ > 9216.0D) {
                return 7;
            } else if (p_191286_1_ > 4096.0D) {
                return 9;
            } else if (p_191286_1_ > 1024.0D) {
                return 11;
            } else if (p_191286_1_ > 576.0D) {
                return 13;
            } else {
                return p_191286_1_ > 256.0D ? 14 : 15;
            }
        }

        protected float getOffset() {
            return 0.75F;
        }
    }


