/*
    Luminance
    Contributor(s): Nettakrim
    Github: https://github.com/MCLegoMan/Luminance
    Licence: GNU LGPLv3
*/

package com.mclegoman.luminance.mixin.client.shaders;

import net.minecraft.client.gl.JsonEffectShaderProgram;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(priority = 100, value = JsonEffectShaderProgram.class)
public abstract class ShaderNamespaceFix {
	@Redirect(method = "loadEffect", at = @At(value = "NEW", target = "(Ljava/lang/String;)Lnet/minecraft/util/Identifier;"))
	private static Identifier luminance$loadEffect(String id) {
		return luminance$get(id);
	}

	@Unique
	private static Identifier luminance$get(String id) {
		if (id.contains(":")) {
			String[] shader = id.substring(16).split(":");
			return new Identifier(shader[0], "shaders/program/" + shader[1]);
		} else {
			return new Identifier(id);
		}
	}

	@Redirect(method = "<init>", at = @At(value = "NEW", target = "(Ljava/lang/String;)Lnet/minecraft/util/Identifier;"))
	private Identifier luminance$init(String id) {
		return luminance$get(id);
	}
}