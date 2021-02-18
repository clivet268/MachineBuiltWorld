package com.Clivet268.MachineBuiltWorld;

import net.minecraft.client.renderer.RenderState;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

    public class MachineBuiltWorldRenderType extends RenderType {

        private static final AlphaState CUBOID_ALPHA = new RenderState.AlphaState(0.1F);

        //Ignored
        private MachineBuiltWorldRenderType(String name, VertexFormat format, int p_i225992_3_, int p_i225992_4_, boolean p_i225992_5_, boolean p_i225992_6_, Runnable runnablePre, Runnable runnablePost) {
            super(name, format, p_i225992_3_, p_i225992_4_, p_i225992_5_, p_i225992_6_, runnablePre, runnablePost);
        }

        public static RenderType renderDefault(ResourceLocation resourceLocation) {
            RenderType.State state = RenderType.State.getBuilder()
                    .texture(new RenderState.TextureState(resourceLocation, false, false))//Texture state
                    .shadeModel(SHADE_ENABLED)//shadeModel(GL11.GL_SMOOTH)
                    .alpha(ZERO_ALPHA)//disableAlphaTest
                    .transparency(TRANSLUCENT_TRANSPARENCY)//enableBlend/blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA)
                    .build(true);
            return makeType("render_default", DefaultVertexFormats.ENTITY, GL11.GL_QUADS, 256, true, false, state);
        }
}
