package com.Clivet268.MachineBuiltWorld.client.renderer;

import com.Clivet268.MachineBuiltWorld.entity.LaserEntity;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.loading.FMLEnvironment;

public class LaserRenderFactory implements IRenderFactory<LaserEntity> {
        public static final LaserRenderFactory instance = new LaserRenderFactory();

        @Override
        public EntityRenderer<? super LaserEntity> createRenderFor(EntityRendererManager manager) {
            if (FMLEnvironment.dist.isDedicatedServer())
                throw new IllegalStateException("Only run this on client!");

            return new LaserRenderer<>(manager);
        }
    }

