package com.tomtom2211.modid;

import net.fabricmc.api.ClientModInitializer;

public class ExampleModClient implements ClientModInitializer {
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
