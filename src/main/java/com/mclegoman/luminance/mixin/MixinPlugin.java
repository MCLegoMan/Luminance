/*
    Luminance
    Author: MCLegoMan
    Github: https://github.com/MCLegoMan/Luminance
    Licence: GNU LGPLv3
*/

package com.mclegoman.luminance.mixin;

import com.llamalad7.mixinextras.MixinExtrasBootstrap;
import com.mclegoman.luminance.common.data.Data;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

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
		return switch (mixinClassName) {
			case "com.mclegoman.luminance.mixin.client.shaders.ShaderNamespaceFix" ->
					!(Data.isModInstalled("architectury") || Data.isModInstalled("satin") || (Data.isModInstalled("souper_secret_settings") && !Data.isModInstalledVersionOrHigher("souper_secret_settings", "v1.0.9", true)) || (Data.isModInstalled("perspective") && !Data.isModInstalledVersionOrHigher("perspective", "1.3.0-alpha.7", true, "+")));
			case "com.mclegoman.luminance.mixin.client.shaders.ShaderTextureNamespaceFix" ->
					!((Data.isModInstalled("souper_secret_settings") && !Data.isModInstalledVersionOrHigher("souper_secret_settings", "v1.0.9", true)) || (Data.isModInstalled("perspective") && !Data.isModInstalledVersionOrHigher("perspective", "1.3.0-alpha.7", true, "+")));
			case "com.mclegoman.luminance.mixin.compat.modmenu.FabricModMixin", "com.mclegoman.luminance.mixin.compat.modmenu.ModsScreenMixin" ->
					Data.isModInstalled("modmenu");
			default -> true;
		};
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