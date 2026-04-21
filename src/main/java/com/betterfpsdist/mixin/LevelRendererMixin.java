package com.betterfpsdist.mixin;

import com.betterfpsdist.BetterfpsdistMod;
import com.betterfpsdist.event.ClientEventHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.SectionOcclusionGraph;
import net.minecraft.client.renderer.chunk.SectionRenderDispatcher;
import net.minecraft.client.renderer.culling.Frustum;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(SectionOcclusionGraph.class)
public class LevelRendererMixin
{
    @Inject(method = "addSectionsInFrustum", at = @At(value = "RETURN"))
    public void on(
        final Frustum frustum,
        final List<SectionRenderDispatcher.RenderSection> visibleSections,
        final List<SectionRenderDispatcher.RenderSection> nearbyVisibleSection,
        final CallbackInfo ci)
    {
        visibleSections.removeIf(renderSection -> {
            final Minecraft minecraft = Minecraft.getInstance();
            if (minecraft.getCameraEntity() != null
                && ClientEventHandler.adjustedDistance(minecraft.getCameraEntity().blockPosition(), renderSection.getRenderOrigin()) > ClientEventHandler.maxSqDist)
            {
                if (BetterfpsdistMod.config.getCommonConfig().debugMode)
                {
                    ClientEventHandler.hiddenSections.add(renderSection.getRenderOrigin());
                }

                return true;
            }
            return false;
        });
    }
}
