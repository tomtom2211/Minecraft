package com.tomtom2211.modid;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public class ExampleModClient implements ClientModInitializer {
    static boolean enabled = false;
    @Override
    public void onInitializeClient() {
        KeyBinding ToggleSprint = KeyBindingHelper.registerKeyBinding(new KeyBinding("Toggle sprint", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_H, "tomtom2211 mod"));
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null) return;

            // Toggling logic
            if (ToggleSprint.wasPressed()) {
                enabled = !enabled;
                client.player.sendMessage(Text.of(enabled ? "Sprinting ON" : "Sprinting OFF"), true);
            }

            // Apply sprint state every tick if enabled
            if (enabled) {
                client.player.setSprinting(true);
            }
        });
        HudRenderCallback.EVENT.register((context, tickDelta) -> {
            MinecraftClient client = MinecraftClient.getInstance();
            if (enabled){
                context.drawText(client.textRenderer, "Sprinting enabled", 1,1,0xFFFFFFFF, true);
            }
        });
    }
}

