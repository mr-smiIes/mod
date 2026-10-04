package com.wackyman.entityhighlighter;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.hud.VanillaHudElements;
import net.minecraft.resources.Identifier;

public class EntityHighlighterAddon implements ClientModInitializer {
    public static final String MOD_ID = "entity-highlighter-addon";

    @Override
    public void onInitializeClient() {
        HudElementRegistry.attachElementBefore(
                VanillaHudElements.CHAT,
                Identifier.fromNamespaceAndPath(MOD_ID, "mob_hud"),
                EntityHighlighterHud::render
        );

        HudElementRegistry.attachElementBefore(
                VanillaHudElements.CHAT,
                Identifier.fromNamespaceAndPath(MOD_ID, "mob_icons"),
                EntityTargetIconRenderer::render
        );

        System.out.println("[Entity Highlighter Addon] Loaded");
    }

    public static boolean isActive() {
        try {
            Class<?> modClass = Class.forName("com.example.playerhighlighter.PlayerHighlighterMod");
            return (Boolean) modClass.getMethod("isHighlightActive").invoke(null);
        } catch (ReflectiveOperationException e) {
            return false;
        }
    }
}
