package com.wackyman.entityhighlighter.mixin;

import com.wackyman.entityhighlighter.EntityHighlighterHud;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(targets = "com.example.playerhighlighter.PlayerHighlighterHud")
public abstract class PlayerHighlighterHudMixin {
    @Inject(method = "render", at = @At("RETURN"))
    private static void entityHighlighterAddon$renderMobText(
            GuiGraphicsExtractor graphics,
            CallbackInfo ci
    ) {
        EntityHighlighterHud.renderInPlayerHud(graphics);
    }
}
