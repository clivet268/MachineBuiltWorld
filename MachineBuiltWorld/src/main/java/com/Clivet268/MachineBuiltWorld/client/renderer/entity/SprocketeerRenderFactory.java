package com.Clivet268.MachineBuiltWorld.client.renderer.entity;

import com.Clivet268.MachineBuiltWorld.entity.SprocketeerEntity;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.loading.FMLEnvironment;

public class SprocketeerRenderFactory implements IRenderFactory<SprocketeerEntity> {
        public static final SprocketeerRenderFactory instance = new SprocketeerRenderFactory();

        @Override
        public EntityRenderer<? super SprocketeerEntity> createRenderFor(EntityRendererManager manager) {
            if (FMLEnvironment.dist.isDedicatedServer())
                throw new IllegalStateException("Only run this on client!");

            return new SprocketeerRenderer(manager);
        }
    }

