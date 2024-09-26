/*
    Luminance
    Contributor(s): MCLegoMan
    Github: https://github.com/MCLegoMan/Luminance
    Licence: GNU LGPLv3
*/

package com.mclegoman.luminance.client.util;

import com.mclegoman.luminance.client.translation.Translation;
import com.mclegoman.luminance.common.data.Data;
import com.mclegoman.luminance.common.util.LogType;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ResourcePacks {
	/**
	 * To add a resource pack to this project, please follow these guidelines:
	 * 1. When registering your resource pack, ensure you include the resource pack's name, and the contributor(s) in the following format:
	 * - Resource Pack Name
	 * - Contributor(s): _________
	 * - Licence: _________
	 * - Notes: _________
	 * You only need to include the licence in your comment if it is not GNU LGPLv3.
	 */
	public static void init() {
		/*
            Super Secret Settings
            Contributor(s): Mojang Studios, Microsoft Corporation, MCLegoMan, Nettakrim
            Licence: Minecraft EULA
            Notes: These shaders have been modified to work with the latest version of minecraft, and also contain new code.
        */
		register(Identifier.of("super_secret_settings"), Data.getModContainer("luminance"), Translation.getTranslation(Data.version.getID(), "resource_pack.super_secret_settings"), ResourcePackActivationType.DEFAULT_ENABLED);
		/*
            Luminance: Default
            Contributor(s): MCLegoMan
            Licence: GNU LGPLv3
        */
		register(Identifier.of("luminance_default"), Data.getModContainer("luminance"), Translation.getTranslation(Data.version.getID(), "resource_pack.luminance_default"), ResourcePackActivationType.DEFAULT_ENABLED);
	}
	public static void register(Identifier id, ModContainer container, Text text, ResourcePackActivationType activationType) {
		try {
			Data.version.sendToLog(LogType.INFO, Translation.getString("Registering resource pack: {}", id.getPath()));
			ResourceManagerHelper.registerBuiltinResourcePack(id, container, text, activationType);
		} catch (Exception error) {
			Data.version.sendToLog(LogType.ERROR, Translation.getString("Failed to register resource pack: {}", error));
		}
	}
}