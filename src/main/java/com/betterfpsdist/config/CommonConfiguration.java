package com.betterfpsdist.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class CommonConfiguration
{
    public final ForgeConfigSpec                     ForgeConfigSpecBuilder;
    public final ForgeConfigSpec.ConfigValue<Double> stretch;

    protected CommonConfiguration(final ForgeConfigSpec.Builder builder)
    {
        builder.push("Config category");

        builder.comment("The amount by which the chunk render distance sphere is stretched in horizontal direction."
                          + " default:3");
        stretch = builder.defineInRange("stretch", 3.0, 0.5, 10d);

        // Escapes the current category level
        builder.pop();
        ForgeConfigSpecBuilder = builder.build();
    }
}
