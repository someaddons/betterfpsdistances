package com.betterfpsdist;

import com.betterfpsdist.config.Configuration;
import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Random;

// The value here should match an entry in the META-INF/mods.toml file
public class BetterfpsdistMod implements ModInitializer
{
    public static final String        MODID  = "betterfpsdist";
    public static final Logger        LOGGER = LogManager.getLogger();
    public static       Configuration config;
    public static       Random        rand   = new Random();

    @Override
    public void onInitialize()
    {
        config = new Configuration();
        config.load();
        LOGGER.info(MODID + " mod initialized");
    }
}
