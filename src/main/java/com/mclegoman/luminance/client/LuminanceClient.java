/*
    Luminance
    Contributor(s): MCLegoMan
    Github: https://github.com/MCLegoMan/Luminance
    Licence: GNU LGPLv3
*/

package com.mclegoman.luminance.client;

import com.mclegoman.luminance.client.events.Events;
import com.mclegoman.luminance.client.keybindings.Keybindings;
import com.mclegoman.luminance.client.shaders.Shader;
import com.mclegoman.luminance.client.shaders.ShaderDataloader;
import com.mclegoman.luminance.client.shaders.Shaders;
import com.mclegoman.luminance.client.temp.ShaderTime;
import com.mclegoman.luminance.client.translation.Translation;
import com.mclegoman.luminance.client.util.CompatHelper;
import com.mclegoman.luminance.client.util.ResourcePacks;
import com.mclegoman.luminance.client.util.Tick;
import com.mclegoman.luminance.common.data.Data;
import com.mclegoman.luminance.common.util.Couple;
import com.mclegoman.luminance.common.util.LogType;
import com.mclegoman.luminance.config.ConfigHelper;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.util.Identifier;

import java.util.List;

public class LuminanceClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		try {
			Data.version.sendToLog(LogType.INFO, Translation.getString("Initializing {}", Data.version.getName()));
			ConfigHelper.init();
			Keybindings.init();
			ResourcePacks.init();
			CompatHelper.init();
			Shaders.init();
			Tick.init();

			// This is a temp fix!
			ShaderTime.init();

			Events.ShaderRender.register(Identifier.of(Data.version.getID(), "test"), List.of(new Couple<>("test", null)));
		} catch (Exception error) {
			Data.version.sendToLog(LogType.ERROR, Translation.getString("Failed to run onInitializeClient: {}", error));
		}
	}
}