package com.betterfpsdist.event;

import com.betterfpsdist.BetterfpsdistMod;
import com.betterfpsdist.config.ConfigurationCache;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.config.ModConfig;

public class ModEventHandler
{
    @SubscribeEvent
    public static void onConfigChanged(ModConfig.ModConfigEvent event)
    {
        ConfigurationCache.stretch = BetterfpsdistMod.config.getCommonConfig().stretch.get();
    }
}
