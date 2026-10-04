package com.wackyman.entityhighlighter.mixin;

import com.wackyman.entityhighlighter.EntityHighlighterAddon;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class LivingEntityGlowingMixin {
    @Inject(method = "isCurrentlyGlowing", at = @At("HEAD"), cancellable = true)
    private void entityHighlighterAddon$glowLivingEntities(CallbackInfoReturnable<Boolean> cir) {
        Entity self = (Entity) (Object) this;

        if (self instanceof LivingEntity && !(self instanceof Player) && EntityHighlighterAddon.isActive()) {
            cir.setReturnValue(true);
        }
    }
}
