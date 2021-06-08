package com.Clivet268.MachineBuiltWorld.client.renderer;

import com.Clivet268.MachineBuiltWorld.entity.GearEntity;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.loading.FMLEnvironment;

public class GearFactory implements IRenderFactory<GearEntity> {
        public static final GearFactory instance = new GearFactory();

        @Override
        public EntityRenderer<? super GearEntity> createRenderFor(EntityRendererManager manager) {
            if (FMLEnvironment.dist.isDedicatedServer())
                throw new IllegalStateException("Only run this on client!");

            return new GearRenderer(manager);
        }
    }

