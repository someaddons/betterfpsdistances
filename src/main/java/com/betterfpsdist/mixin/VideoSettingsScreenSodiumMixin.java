package com.betterfpsdist.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.options.OptionsSubScreen;
import net.minecraft.client.gui.screens.options.VideoSettingsScreen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(VideoSettingsScreen.class)
public abstract class VideoSettingsScreenSodiumMixin extends OptionsSubScreen
{
    public VideoSettingsScreenSodiumMixin(final Screen screen, final Options options, final Component component)
    {
        super(screen, options, component);
    }

    @Override
    protected void addFooter()
    {
        this.layout.addToFooter(Button.builder(Component.translatable("options.button.sodium"),
                    (button) -> Minecraft.getInstance().setScreen(net.caffeinemc.mods.sodium.client.gui.VideoSettingsScreen.createScreen(this.lastScreen)))
                .width(layout.getWidth() / 3)
                .build(),
            s -> s.alignHorizontally(0.8f));
        this.layout.addToFooter(Button.builder(CommonComponents.GUI_DONE, (button) -> this.onClose()).width(layout.getWidth() / 3).build(), s -> s.alignHorizontally(0.2f));
    }
}