/*
    Luminance
    Contributor(s): MCLegoMan
    Github: https://github.com/MCLegoMan/Luminance
    Licence: GNU LGPLv3
*/

package com.mclegoman.luminance.client.keybindings;

import com.mclegoman.luminance.client.data.ClientData;
import com.mclegoman.luminance.client.events.GameRenderEvents;
import com.mclegoman.luminance.client.screen.config.ConfigScreen;
import com.mclegoman.luminance.client.shaders.Shader;
import com.mclegoman.luminance.client.translation.Translation;
import com.mclegoman.luminance.common.data.Data;
import com.mclegoman.luminance.common.util.Couple;
import com.mclegoman.luminance.common.util.LogType;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;

import java.util.Objects;

public class Keybindings {
	public static final KeyBinding adjustAlpha;
	public static final KeyBinding openConfig;
	public static final KeyBinding[] allKeybindings;
	static {
		allKeybindings = new KeyBinding[]{
				adjustAlpha = KeybindingHelper.getKeybinding(Data.version.getID(), Data.version.getID(), "adjust_alpha", GLFW.GLFW_KEY_J),
				openConfig = KeybindingHelper.getKeybinding(Data.version.getID(), Data.version.getID(), "open_config", GLFW.GLFW_KEY_UNKNOWN)
		};
	}
	public static void init() {
		Data.version.sendToLog(LogType.INFO, Translation.getString("Initializing keybindings!"));
	}
	public static void tick() {
		if (openConfig.wasPressed()) {
			ClientData.minecraft.setScreen(new ConfigScreen(ClientData.minecraft.currentScreen, false));
		}
		if (adjustAlpha.wasPressed()) {
			if (!GameRenderEvents.ShaderRender.exists(new Couple<>(Data.version.getID(), "main"))) GameRenderEvents.ShaderRender.add(new Couple<>(Data.version.getID(), "main"), new Shader(new Identifier("minecraft", "shaders/post/creeper.json"), Shader.RenderType.GAME));
			else {
				if (Objects.requireNonNull(GameRenderEvents.ShaderRender.get(new Couple<>(Data.version.getID(), "main"))).getRenderType().equals(Shader.RenderType.GAME)) Objects.requireNonNull(GameRenderEvents.ShaderRender.get(new Couple<>(Data.version.getID(), "main"))).setRenderType(Shader.RenderType.WORLD);
				else if (Objects.requireNonNull(GameRenderEvents.ShaderRender.get(new Couple<>(Data.version.getID(), "main"))).getRenderType().equals(Shader.RenderType.WORLD)) Objects.requireNonNull(GameRenderEvents.ShaderRender.get(new Couple<>(Data.version.getID(), "main"))).setRenderType(Shader.RenderType.GAME);
			}
		}
	}
}