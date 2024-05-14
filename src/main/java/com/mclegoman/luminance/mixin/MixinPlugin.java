/*
    Luminance
    Author: MCLegoMan
    Github: https://github.com/MCLegoMan/Luminance
    Licence: GNU LGPLv3
*/

package com.mclegoman.luminance.mixin;

import com.llamalad7.mixinextras.MixinExtrasBootstrap;
import com.mclegoman.luminance.client.translation.Translation;
import com.mclegoman.luminance.common.data.Data;
import com.mclegoman.luminance.common.util.LogType;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.Version;
import net.fabricmc.loader.impl.util.version.StringVersion;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MixinPlugin implements IMixinConfigPlugin {
	@Override
	public void onLoad(String mixinPackage) {
		MixinExtrasBootstrap.init();
	}

	@Override
	public String getRefMapperConfig() {
		return null;
	}

	@Override
	public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
		String mixin = mixinClassName.replaceFirst("com.mclegoman.luminance.mixin.", "");
		switch (mixin) {
			case "client.shaders.ShaderNamespaceFix" -> {
				boolean isArchitecturyInstalled = Data.isModInstalled("architectury");
				boolean isSatinInstalled = Data.isModInstalled("satin");
				boolean isSoupInstalled = (Data.isModInstalled("souper_secret_settings") && !Data.isModInstalledVersionOrHigher("souper_secret_settings", "v1.0.9", true));
				boolean isPerspectiveInstalled = (Data.isModInstalled("perspective") && !Data.isModInstalledVersionOrHigher("perspective", "1.3.0-alpha.7"));
				List<String> modsInstalled = new ArrayList<>();
				if (isArchitecturyInstalled) modsInstalled.add("architectury");
				if (isSatinInstalled) modsInstalled.add("satin");
				if (isSoupInstalled) modsInstalled.add("souper_secret_settings");
				if (isPerspectiveInstalled) modsInstalled.add("perspective");
				if (!modsInstalled.isEmpty()) Data.version.sendToLog(LogType.INFO, Translation.getString("Disabling {}: {}", mixin, modsInstalled));
				return modsInstalled.isEmpty();
			}
			case "client.shaders.ShaderTextureNamespaceFix" -> {
				boolean isSoupInstalled = (Data.isModInstalled("souper_secret_settings") && !Data.isModInstalledVersionOrHigher("souper_secret_settings", "v1.0.9", true));
				boolean isPerspectiveInstalled = Data.isModInstalled("perspective") && !Data.isModInstalledVersionOrHigher("perspective", "1.3.0-alpha.7");
				List<String> modsInstalled = new ArrayList<>();
				if (isSoupInstalled) modsInstalled.add("souper_secret_settings");
				if (isPerspectiveInstalled) modsInstalled.add("perspective");
				if (!modsInstalled.isEmpty()) Data.version.sendToLog(LogType.INFO, Translation.getString("Disabling {}: {}", mixin, modsInstalled));
				return modsInstalled.isEmpty();
			}
			case "compat.modmenu.FabricModMixin", "compat.modmenu.ModsScreenMixin" -> {
				boolean isModMenuInstalled = Data.isModInstalled("modmenu");
				List<String> modsInstalled = new ArrayList<>();
				if (isModMenuInstalled) modsInstalled.add("modmenu");
				if (!modsInstalled.isEmpty()) Data.version.sendToLog(LogType.INFO, Translation.getString("Enabling {}: {}", mixin, modsInstalled));
				return !modsInstalled.isEmpty();
			}
			default -> {
				return true;
			}
		}
	}

	@Override
	public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {
	}

	@Override
	public List<String> getMixins() {
		return null;
	}

	@Override
	public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
	}

	@Override
	public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
	}
}