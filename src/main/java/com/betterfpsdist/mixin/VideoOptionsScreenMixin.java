package com.betterfpsdist.mixin;

import com.betterfpsdist.BetterfpsdistMod;
import com.betterfpsdist.event.ClientEventHandler;
import net.minecraft.client.gui.screen.option.VideoOptionsScreen;
import net.minecraft.client.option.Option;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Mixin(VideoOptionsScreen.class)
public class VideoOptionsScreenMixin
{
    @Shadow
    @Final
    @Mutable
    private static Option[] OPTIONS;

    @Inject(method = "<clinit>", at = @At("RETURN"))
    private static void on(final CallbackInfo ci)
    {
        try
        {
            final List<Option> options = new ArrayList<>(Arrays.asList(OPTIONS));
            options.add(options.indexOf(Option.GUI_SCALE) + 1, ClientEventHandler.RenderSizeStretch);
            OPTIONS = options.toArray(new Option[0]);
        }
        catch (Throwable e)
        {
            BetterfpsdistMod.LOGGER.error("Error trying to add an option Button to video settings, likely optifine is present which removes vanilla functionality required."
                                            + " The mod still works, but you'll need to manually adjust the config to get different Render distance stretch values as the button could not be added.");
        }
    }
}
