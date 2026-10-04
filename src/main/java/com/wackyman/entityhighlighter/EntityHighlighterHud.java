package com.wackyman.entityhighlighter;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.minecraft.util.Mth;

public final class EntityHighlighterHud {
    private EntityHighlighterHud() {}

    /**
     * Draws mob information at the TOP of Player Highlighter's existing text HUD.
     * Player Highlighter itself draws player information from the bottom upward.
     */
    public static void renderInPlayerHud(GuiGraphicsExtractor graphics) {
        if (!EntityHighlighterAddon.isActive()) return;

        Minecraft client = Minecraft.getInstance();
        ClientLevel world = client.level;
        Entity camera = client.getCameraEntity();
        if (world == null || camera == null || client.gui.hud.isHidden()) return;

        int x = 8;
        int y = 8;
        int lineHeight = 11;
        int rendered = 0;
        int maxMobs = 12;

        for (Entity rawEntity : world.entitiesForRendering()) {
            if (!(rawEntity instanceof LivingEntity entity)) continue;
            if (entity == camera || entity instanceof Player || entity.isRemoved() || entity.isDeadOrDying()) {
                continue;
            }

            Vec3 pos = entity.position();
            double dx = pos.x - camera.getX();
            double dz = pos.z - camera.getZ();
            int distance = (int) Math.sqrt(dx * dx + dz * dz);

            String name = entity.getName().getString();
            if (name.isBlank() || name.equals("entity." + entity.getType().getDescriptionId())) {
                name = entity.getType().getDescription().getString();
            }

            String health = String.format("%.1f/%.1f", entity.getHealth(), entity.getMaxHealth());
            String arrow = getDirectionArrow(camera, entity);

            String text = arrow + " " + name + "  " + distance + "m  " + health + " HP";

            graphics.text(client.font, text, x, y, 0xFFF0F0F0, false);

            y += lineHeight;
            rendered++;

            if (rendered >= maxMobs) break;
        }
    }

    /**
     * Uses the same 8-direction arrow logic as Player Highlighter,
     * but points from the camera/player toward the mob.
     */
    private static String getDirectionArrow(Entity camera, Entity target) {
        double dx = target.getX() - camera.getX();
        double dz = target.getZ() - camera.getZ();

        double targetYaw = Math.toDegrees(Math.atan2(-dx, dz));
        float cameraYaw = Mth.wrapDegrees(camera.getYRot());
        double diff = Mth.wrapDegrees(cameraYaw - targetYaw);

        if (diff >= -22.5 && diff < 22.5) return "↑";
        if (diff >= 22.5 && diff < 67.5) return "↖";
        if (diff >= 67.5 && diff < 112.5) return "←";
        if (diff >= 112.5 && diff < 157.5) return "↙";
        if (diff >= -67.5 && diff < -22.5) return "↗";
        if (diff >= -112.5 && diff < -67.5) return "→";
        if (diff >= -157.5 && diff < -112.5) return "↘";
        return "↓";
    }
}
