package com.betterfpsdist.config;

import com.betterfpsdist.betterfpsdistMod;
import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.loading.FMLPaths;

import java.nio.file.Path;

public class Configuration
{
    /**
     * Loaded everywhere, not synced
     */
    private final CommonConfiguration commonConfig;

    /**
     * Loaded clientside, not synced
     */
    // private final ClientConfiguration clientConfig;

    /**
     * Builds configuration tree.
     */
    public Configuration()
    {
        commonConfig = new CommonConfiguration(new ForgeConfigSpec.Builder());
        loadConfig(commonConfig.ForgeConfigSpecBuilder, FMLPaths.CONFIGDIR.get().resolve(betterfpsdistMod.MODID + "-common.toml"));
    }

    public static void loadConfig(ForgeConfigSpec spec, Path path)
    {

        final CommentedFileConfig configData = CommentedFileConfig.builder(path)
                                                 .sync()
                                                 .preserveInsertionOrder()
                                                 .autosave()
                                                 .writingMode(WritingMode.REPLACE)
                                                 .build();
        configData.load();
        spec.setConfig(configData);
    }

    public CommonConfiguration getCommonConfig()
    {
        return commonConfig;
    }
}
