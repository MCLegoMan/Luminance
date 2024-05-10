/*
    Luminance
    Contributor(s): MCLegoMan
    Github: https://github.com/MCLegoMan/Luminance
    Licence: GNU LGPLv3
*/

package com.mclegoman.luminance.client.util;

import com.mclegoman.luminance.common.data.Data;
import com.mclegoman.luminance.common.util.Couple;
import net.irisshaders.iris.api.v0.IrisApi;

import java.util.ArrayList;
import java.util.List;

public class CompatHelper {
	public static final List<Couple<String, String>> overriddenModMenuIcons = new ArrayList<>();
	public static boolean isIrisShadersEnabled() {
		return Data.isModInstalled("iris") && IrisApi.getInstance().isShaderPackInUse();
	}
	public static void addOverrideModMenuIcon(String modId, String iconPath) {
		Couple<String, String> mod = new Couple<>(modId, iconPath);
		if (!shouldOverrideModMenuIcon(modId)) overriddenModMenuIcons.add(mod);
	}
	public static void modifyOverrideModMenuIcon(String modId, String iconPath) {
		if (overriddenModMenuIcons.removeIf(mod -> mod.getFirst().equalsIgnoreCase(modId))) overriddenModMenuIcons.add(new Couple<>(modId, iconPath));
	}
	public static void removeOverrideModMenuIcon(String modId) {
		overriddenModMenuIcons.removeIf(mod -> mod.getFirst().equalsIgnoreCase(modId));
	}
	public static boolean shouldOverrideModMenuIcon(String modId) {
		for (Couple<String, String> mod : overriddenModMenuIcons) {
			if (mod.getFirst().equalsIgnoreCase(modId)) return true;
		}
		return false;
	}
	public static String getOverrideModMenuIcon(String modId) {
		for (Couple<String, String> mod : overriddenModMenuIcons) {
			if (mod.getFirst().equalsIgnoreCase(modId)) return mod.getSecond();
		}
		return null;
	}
}
