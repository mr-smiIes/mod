# Entity Highlighter Addon

A separate Fabric 26.3 client addon for **Player Highlighter 1.2.1**.

It does not modify or replace the original mod. It adds:

- glowing outlines for non-player LivingEntity mobs
- mob names in the HUD
- mob distance and health in the HUD
- target icons for mobs
- the same activation state as Player Highlighter (its Hold key / Keep setting)

## Install

Put both the original **Player Highlighter 1.2.1** and this addon in the `mods` folder.

## Build

The included GitHub Actions workflow uses Java 25, Gradle 9.6 and builds the addon automatically.

The generated JAR is uploaded as the workflow artifact.
