package com.tomtom2211.modid;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class ExampleModClient implements ClientModInitializer {
    static boolean enabled = false;
    @Override
    public void onInitializeClient() {
        KeyBinding NightVision = KeyBindingHelper.registerKeyBinding(new KeyBinding("Apply/Remove night vision", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_H, "tomtom2211 mod"));
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (NightVision.wasPressed()) {
                if (enabled){
                    client.player.networkHandler.sendChatCommand("effect give @s night_vision 60 1");
                }
                else {
                    client.player.networkHandler.sendChatCommand("effect clear");
                }
                enabled = !enabled;
            }
        });
    }
}

