package com.wackyman.entityhighlighter;

import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

public final class EntityTargetIconRenderer {
    private static final Identifier ICON = Identifier.fromNamespaceAndPath(
            "player-highlighter", "textures/gui/target.png"
    );

    private EntityTargetIconRenderer() {}

    public static void render(GuiGraphicsExtractor graphics, DeltaTracker tickCounter) {
        if (!EntityHighlighterAddon.isActive()) return;

        Minecraft client = Minecraft.getInstance();
        if (client.player == null || client.level == null) return;

        int width = graphics.guiWidth();
        int height = graphics.guiHeight();
        Entity camera = client.getCameraEntity();
        if (camera == null) return;

        Vec3 cameraPos = camera.position();
        Vec3 forward = camera.getViewVector(1.0F);
        GameRenderer renderer = client.gameRenderer;
        int size = 9;
        float halfW = width * 0.5F;
        float halfH = height * 0.5F;

        for (Entity rawEntity : client.level.entitiesForRendering()) {
            if (!(rawEntity instanceof LivingEntity entity)) continue;
            if (entity == camera || entity instanceof Player || entity.isRemoved() || entity.isDeadOrDying()) continue;

            double px = entity.getX();
            double py = entity.getEyeY();
            double pz = entity.getZ();
            double dx = px - cameraPos.x;
            double dy = py - cameraPos.y;
            double dz = pz - cameraPos.z;

            if (forward.x * dx + forward.y * dy + forward.z * dz <= 0.0) continue;

            Vec3 projected = renderer.projectPointToScreen(new Vec3(px, py, pz));
            if (projected == null || !Double.isFinite(projected.x) || !Double.isFinite(projected.y)) continue;

            float x = (float) ((projected.x + 1.0) * halfW);
            float y = (float) ((1.0 - projected.y) * halfH);
            if (x < 0 || x > width || y < 0 || y > height) continue;

            graphics.blit(RenderPipelines.GUI_TEXTURED, ICON,
                    Math.round(x - size / 2.0F), Math.round(y - size / 2.0F),
                    0.0F, 0.0F, size, size, size, size);
        }
    }
}
