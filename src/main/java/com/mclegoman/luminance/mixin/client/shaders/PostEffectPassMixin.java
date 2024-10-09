/*
    Luminance
    Contributor(s): MCLegoMan
    Github: https://github.com/MCLegoMan/Luminance
    Licence: GNU LGPLv3
*/

package com.mclegoman.luminance.mixin.client.shaders;

import com.mclegoman.luminance.client.events.Events;
import com.mclegoman.luminance.client.shaders.PostEffectPassInterface;
import com.mclegoman.luminance.client.shaders.ShaderProgramInterface;
import com.mclegoman.luminance.client.shaders.uniforms.UniformOverride;
import net.minecraft.client.gl.*;
import net.minecraft.client.render.FrameGraphBuilder;
import net.minecraft.client.util.Handle;
import net.minecraft.util.Identifier;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mixin(priority = 100, value = PostEffectPass.class)
public abstract class PostEffectPassMixin implements PostEffectPassInterface {
	@Shadow @Final private ShaderProgram program;

	@Shadow @Final private List<PostEffectPipeline.Uniform> uniforms;

	@Inject(method = "render", at = @At("HEAD"))
	private void luminance$beforeRender(FrameGraphBuilder frameGraphBuilder, Map<Identifier, Handle<Framebuffer>> map, Matrix4f matrix4f, CallbackInfo ci) {
		Events.BeforeShaderRender.registry.forEach(((id, runnable) -> runnable.run(program)));
	}
	@Inject(method = "render", at = @At("TAIL"))
	private void luminance$afterRender(FrameGraphBuilder frameGraphBuilder, Map<Identifier, Handle<Framebuffer>> map, Matrix4f matrix4f, CallbackInfo ci) {
		Events.AfterShaderRender.registry.forEach(((id, runnable) -> runnable.run(program)));
	}

	@Inject(method = "method_62257", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gl/Framebuffer;setClearColor(FFFF)V"))
	private void setUniformOverrides(Handle handle, Map<Identifier, Handle<Framebuffer>> map, Matrix4f matrix4f, CallbackInfo ci) {
		uniformOverrides.forEach((name, override) -> {
			GlUniform glUniform = program.getUniform(name);
			if (glUniform == null) {
				return;
			}

			//the double looping of the same array here is to avoid needing to call luminance$getCurrentUniformValues unless its needed
			List<Float> values = override.getOverride();
            for (Float value : values) {
                if (value == null) {
                    List<Float> current = ((ShaderProgramInterface)program).luminance$getCurrentUniformValues(name);
					for (int i = 0; i < values.size(); i++) {
						if (values.get(i) == null) {
							values.set(i, current.get(i));
						}
					}
                    break;
                }
            }

			glUniform.method_65016(values, values.size());
		});
	}

	@Unique private final Map<String, UniformOverride> uniformOverrides = new HashMap<>();

	@Override
	public Map<String, UniformOverride> luminance$getUniformOverrides() {
		return uniformOverrides;
	}
}