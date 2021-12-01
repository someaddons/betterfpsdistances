package com.betterfpsdist;

import com.betterfpsdist.config.Configuration;
import com.betterfpsdist.event.ClientEventHandler;
import com.betterfpsdist.event.EventHandler;
import com.betterfpsdist.event.ModEventHandler;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.network.FMLNetworkConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Random;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(BetterfpsdistMod.MODID)
public class BetterfpsdistMod
{
    public static final String        MODID  = "betterfpsdist";
    public static final Logger        LOGGER = LogManager.getLogger();
    public static       Configuration config = new Configuration();
    public static       Random        rand   = new Random();

    public BetterfpsdistMod()
    {
        ModLoadingContext.get()
          .registerExtensionPoint(ExtensionPoint.DISPLAYTEST, () -> org.apache.commons.lang3.tuple.Pair.of(() -> FMLNetworkConstants.IGNORESERVERONLY, (a, b) -> true));

        Mod.EventBusSubscriber.Bus.MOD.bus().get().register(ModEventHandler.class);
        Mod.EventBusSubscriber.Bus.FORGE.bus().get().register(EventHandler.class);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);
    }

    @SubscribeEvent
    public void clientSetup(FMLClientSetupEvent event)
    {
        // Side safe client event handler
        Mod.EventBusSubscriber.Bus.FORGE.bus().get().register(ClientEventHandler.class);
        ;
    }

    private void setup(final FMLCommonSetupEvent event)
    {
        LOGGER.info(MODID + " mod initialized");
    }
}
