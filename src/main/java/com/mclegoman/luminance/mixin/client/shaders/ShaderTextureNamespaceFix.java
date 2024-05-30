/*
    Luminance
    Contributor(s): Nettakrim, MCLegoMan
    Github: https://github.com/MCLegoMan/Luminance
    Licence: GNU LGPLv3
*/

package com.mclegoman.luminance.mixin.client.shaders;

import net.minecraft.client.gl.PostEffectProcessor;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(priority = 100, value = PostEffectProcessor.class)
public abstract class ShaderTextureNamespaceFix {
	@Redirect(method = "parsePass", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Identifier;ofVanilla(Ljava/lang/String;)Lnet/minecraft/util/Identifier;"))
	private Identifier luminance$loadTexture(String path) {
		return luminance$get(path);
	}
	@Unique
	private static Identifier luminance$get(String id) {
		if (id.contains(":")) {
			String[] shader = id.substring(16).split(":");
			return Identifier.of(shader[0], "textures/effect/" + shader[1]);
		} else {
			return Identifier.of(id);
		}
	}
}