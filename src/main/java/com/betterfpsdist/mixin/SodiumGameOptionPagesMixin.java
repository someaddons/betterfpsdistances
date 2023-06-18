package com.betterfpsdist.mixin;

import com.betterfpsdist.BetterfpsdistMod;
import me.jellysquid.mods.sodium.client.gui.SodiumGameOptionPages;
import me.jellysquid.mods.sodium.client.gui.options.*;
import me.jellysquid.mods.sodium.client.gui.options.control.ControlValueFormatter;
import me.jellysquid.mods.sodium.client.gui.options.control.SliderControl;
import me.jellysquid.mods.sodium.client.gui.options.storage.MinecraftOptionsStorage;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(SodiumGameOptionPages.class)
public class SodiumGameOptionPagesMixin
{
    @Shadow(remap = false)
    @Final
    private static MinecraftOptionsStorage vanillaOpts;

    @Redirect(method = "general", at = @At(value = "INVOKE", target = "Lme/jellysquid/mods/sodium/client/gui/options/OptionGroup$Builder;add(Lme/jellysquid/mods/sodium/client/gui/options/Option;)Lme/jellysquid/mods/sodium/client/gui/options/OptionGroup$Builder;", ordinal = 2), remap = false, require = 0)
    private static OptionGroup.Builder initCompat(final OptionGroup.Builder instance, final Option<?> optionparam)
    {
        instance.add(optionparam);

        instance.add(OptionImpl.createBuilder(Integer.TYPE, vanillaOpts)
          .setName(Component.literal("Render Distance y-stretch"))
          .setTooltip(Component.literal("Reduces the distance at which chunks beneath/above are shown"))
          .setControl(option -> new SliderControl(option, 50, 500, 1, ControlValueFormatter.percentage()))
          .setBinding(
            (options, value) -> {
                BetterfpsdistMod.config.getCommonConfig().stretch = value / 100d;
                BetterfpsdistMod.config.save();
            },
            options -> (int) BetterfpsdistMod.config.getCommonConfig().stretch * 100
          )
          .setImpact(OptionImpact.LOW)
          .setFlags(new OptionFlag[] {OptionFlag.REQUIRES_RENDERER_RELOAD})
          .build());
        return instance;
    }
}
