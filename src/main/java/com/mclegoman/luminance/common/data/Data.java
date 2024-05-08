/*
    Luminance
    Contributor(s): MCLegoMan
    Github: https://github.com/MCLegoMan/Luminance
    Licence: GNU LGPLv3
*/

package com.mclegoman.luminance.common.data;

import com.mclegoman.luminance.client.translation.Translation;
import com.mclegoman.luminance.common.util.LogType;
import com.mclegoman.luminance.common.util.ReleaseType;
import com.mclegoman.luminance.common.util.Version;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import org.apache.commons.lang3.StringUtils;

public class Data {
	public static final Version version = Version.create("Luminance", "luminance", 1, 0, 0, ReleaseType.ALPHA, 2, "EBTw0O1c");
	public static boolean isModInstalled(String MOD_ID) {
		return FabricLoader.getInstance().isModLoaded(MOD_ID);
	}
	public static boolean isModInstalledVersionOrHigher(String modId, String requiredVersion, boolean substring) {
		try {
			if (isModInstalled(modId)) {
				return checkModVersion(getModContainer(modId).getMetadata().getVersion().getFriendlyString(), requiredVersion, substring);
			}
		} catch (Exception error) {
			version.sendToLog(LogType.ERROR, Translation.getString("Failed to check mod version for " + modId + ": {}", error));
		}
		return false;
	}
	public static boolean checkModVersion(String currentVersion, String requiredVersion, boolean substring) {
		try {
			return net.fabricmc.loader.api.Version.parse(requiredVersion).compareTo(net.fabricmc.loader.api.Version.parse(substring ? StringUtils.substringBefore(currentVersion, "-") : currentVersion)) <= 0;
		} catch (Exception error) {
			version.sendToLog(LogType.ERROR, Translation.getString("Failed to check mod version!"));
		}
		return false;
	}
	public static ModContainer getModContainer(String modId) {
		return FabricLoader.getInstance().getModContainer(modId).get();
	}
}