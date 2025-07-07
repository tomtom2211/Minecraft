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
    static double counter = 0;

    @Override
    public void onInitializeClient() {
        KeyBinding sneakCounter = KeyBindingHelper.registerKeyBinding(new KeyBinding("Toggle sneak counter", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_L, "tomtom2211"));
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (sneakCounter.wasPressed() && client.player != null){
                enabled = !enabled;
                counter = 0;
                client.player.sendMessage(Text.of(enabled? "Counter enabled!":"Counter disabled!"), true);
            }
            if (client.player != null && enabled){
                if (client.player.isSneaking()) {
                    counter++;
                }
            }
        });
        HudRenderCallback.EVENT.register((context, tickCounter) -> {
            Double sneakTime = counter / 20;
            String sneakTimeString = String.format("%.1f", sneakTime);
            MinecraftClient client = MinecraftClient.getInstance();
            if(enabled) {
                context.drawText(client.textRenderer, sneakTimeString + " s", 1, 1, 0xFFFFFFFF, false);
            }
        });
    }
}

