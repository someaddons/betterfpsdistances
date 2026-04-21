package com.betterfpsdist.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.options.OptionsScreen;
import net.minecraft.client.gui.screens.options.VideoSettingsScreen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Supplier;

@Mixin(OptionsScreen.class)
public class OptionsScreenMixin
{
    @Shadow
    @Final
    private static Component VIDEO;

    @Shadow
    @Final
    private Options options;

    @Inject(method = "openScreenButton", at = @At("HEAD"), cancellable = true)
    private void restoreOriginal(final Component message, final Supplier<Screen> screenToScreen, final CallbackInfoReturnable<Button> cir)
    {
        if (message == VIDEO)
        {
            cir.setReturnValue(Button.builder(message,
                (button) -> Minecraft.getInstance().setScreen(new VideoSettingsScreen((OptionsScreen) (Object) this, Minecraft.getInstance(), options))).build());
        }
    }
}
