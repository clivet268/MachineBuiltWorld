package com.Clivet268.MachineBuiltWorld;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

@Mod.EventBusSubscriber
public class Config {

    //public static final String CATEGORY_GENERAL = "general";
    public static final String CATEGORY_POWER = "power";
    public static final String SUBCATEGORY_BATTERY = "battery";

    public static ForgeConfigSpec SERVER_CONFIG;
    public static ForgeConfigSpec CLIENT_CONFIG;

    public static ForgeConfigSpec.IntValue BATTERY_POT_MAXPOWER;
    public static ForgeConfigSpec.IntValue BATTERY_POT_GENERATE;
    public static ForgeConfigSpec.IntValue BATTERY_POT_SEND;
    public static ForgeConfigSpec.IntValue BATTERY_POT_TICKS;
    public static ForgeConfigSpec.IntValue TTTBATTERY_MAXPOWER;
    public static ForgeConfigSpec.IntValue TTTBATTERY_GENERATE;
    public static ForgeConfigSpec.IntValue TTTBATTERY_SEND;
    public static ForgeConfigSpec.IntValue TTTBATTERY_RECEIVE;
    public static ForgeConfigSpec.IntValue TTTBATTERY_TICKS;
    public static ForgeConfigSpec.IntValue TTBATTERY_MAXPOWER;
    public static ForgeConfigSpec.IntValue TTBATTERY_GENERATE;
    public static ForgeConfigSpec.IntValue TTBATTERY_SEND;
    public static ForgeConfigSpec.IntValue TTBATTERY_TICKS;
    public static ForgeConfigSpec.IntValue TBATTERY_MAXPOWER;
    public static ForgeConfigSpec.IntValue TBATTERY_GENERATE;
    public static ForgeConfigSpec.IntValue TBATTERY_SEND;
    public static ForgeConfigSpec.IntValue TBATTERY_TICKS;
    public static ForgeConfigSpec.IntValue MIXER_MAXPOWER;
    public static ForgeConfigSpec.IntValue MIXER_RECEIVE;
    public static ForgeConfigSpec.IntValue CRUSHER_MAXPOWER;
    public static ForgeConfigSpec.IntValue CRUSHER_RECEIVE;

    static {

        ForgeConfigSpec.Builder SERVER_BUILDER = new ForgeConfigSpec.Builder();
        ForgeConfigSpec.Builder CLIENT_BUILDER = new ForgeConfigSpec.Builder();

       // CLIENT_BUILDER.comment("General settings").push(CATEGORY_GENERAL);
       // ROTATION_SPEED = CLIENT_BUILDER.comment("Rotation speed of the magic block").defineInRange("rotationSpeed", 100.0, 0.0, 1000000.0);
       // CLIENT_BUILDER.pop();

        SERVER_BUILDER.comment("Power settings").push(CATEGORY_POWER);

        setupBatteriesConfig(SERVER_BUILDER, CLIENT_BUILDER);

        SERVER_BUILDER.pop();


        SERVER_CONFIG = SERVER_BUILDER.build();
        CLIENT_CONFIG = CLIENT_BUILDER.build();
    }

    private static void setupBatteriesConfig(ForgeConfigSpec.Builder SERVER_BUILDER, ForgeConfigSpec.Builder CLIENT_BUILDER) {
        SERVER_BUILDER.comment("Battery settings").push(SUBCATEGORY_BATTERY);

        TTTBATTERY_MAXPOWER = SERVER_BUILDER.comment("Maximum power for the FirstBlock generator")
                .defineInRange("maxPower", 100000, 0, Integer.MAX_VALUE);
        TTTBATTERY_GENERATE = SERVER_BUILDER.comment("Power generation per diamond")
                .defineInRange("generate", 1000, 0, Integer.MAX_VALUE);
        TTTBATTERY_SEND = SERVER_BUILDER.comment("Power generation to send per tick")
                .defineInRange("send", 100, 0, Integer.MAX_VALUE);
        TTTBATTERY_RECEIVE = SERVER_BUILDER.comment("Power to recive per tick")
                .defineInRange("receive", 100, 0, Integer.MAX_VALUE);
        TTTBATTERY_TICKS = SERVER_BUILDER.comment("Ticks per diamond")
                .defineInRange("ticks", 20, 0, Integer.MAX_VALUE);
        TTBATTERY_MAXPOWER = SERVER_BUILDER.comment("Maximum power for the FirstBlock generator")
                .defineInRange("maxPower", 100000, 0, Integer.MAX_VALUE);
        TTBATTERY_GENERATE = SERVER_BUILDER.comment("Power generation per diamond")
                .defineInRange("generate", 1000, 0, Integer.MAX_VALUE);
        TTBATTERY_SEND = SERVER_BUILDER.comment("Power generation to send per tick")
                .defineInRange("send", 100, 0, Integer.MAX_VALUE);
        TTBATTERY_TICKS = SERVER_BUILDER.comment("Ticks per diamond")
                .defineInRange("ticks", 20, 0, Integer.MAX_VALUE);
        TBATTERY_MAXPOWER = SERVER_BUILDER.comment("Maximum power for the FirstBlock generator")
                .defineInRange("maxPower", 100000, 0, Integer.MAX_VALUE);
        TBATTERY_GENERATE = SERVER_BUILDER.comment("Power generation per diamond")
                .defineInRange("generate", 1000, 0, Integer.MAX_VALUE);
        TBATTERY_SEND = SERVER_BUILDER.comment("Power generation to send per tick")
                .defineInRange("send", 100, 0, Integer.MAX_VALUE);
        TBATTERY_TICKS = SERVER_BUILDER.comment("Ticks per diamond")
                .defineInRange("ticks", 20, 0, Integer.MAX_VALUE);
        BATTERY_POT_MAXPOWER = SERVER_BUILDER.comment("Maximum power for the FirstBlock generator")
                .defineInRange("maxPower", 100000, 0, Integer.MAX_VALUE);
        BATTERY_POT_GENERATE = SERVER_BUILDER.comment("Power generation per diamond")
                .defineInRange("generate", 1000, 0, Integer.MAX_VALUE);
        BATTERY_POT_SEND = SERVER_BUILDER.comment("Power generation to send per tick")
                .defineInRange("send", 100, 0, Integer.MAX_VALUE);
        BATTERY_POT_TICKS = SERVER_BUILDER.comment("Ticks per diamond")
                .defineInRange("ticks", 20, 0, Integer.MAX_VALUE);
        MIXER_MAXPOWER = SERVER_BUILDER.comment("Maximum power for the FirstBlock generator")
                .defineInRange("maxPower", 100000, 0, Integer.MAX_VALUE);
        MIXER_RECEIVE = SERVER_BUILDER.comment("Power to recive per tick")
                .defineInRange("receive", 100, 0, Integer.MAX_VALUE);
        CRUSHER_MAXPOWER = SERVER_BUILDER.comment("Maximum power for the FirstBlock generator")
                .defineInRange("maxPower", 100000, 0, Integer.MAX_VALUE);
        CRUSHER_RECEIVE = SERVER_BUILDER.comment("Power to recive per tick")
                .defineInRange("receive", 100, 0, Integer.MAX_VALUE);

        SERVER_BUILDER.pop();
    }

    @SubscribeEvent
    public static void onLoad(final ModConfig.Loading configEvent) {

    }

    @SubscribeEvent
    public static void onReload(final ModConfig.Reloading configEvent) {
    }


}

