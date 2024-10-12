/*
    Luminance
    Contributor(s): MCLegoMan, Nettakrim
    Github: https://github.com/MCLegoMan/Luminance
    Licence: GNU LGPLv3
*/

package com.mclegoman.luminance.mixin.client.shaders;

import com.mclegoman.luminance.client.events.Events;
import com.mclegoman.luminance.client.shaders.interfaces.PipelineUniformInterface;
import com.mclegoman.luminance.client.shaders.interfaces.PostEffectPassInterface;
import com.mclegoman.luminance.client.shaders.interfaces.ShaderProgramInterface;
import com.mclegoman.luminance.client.shaders.overrides.LuminanceUniformOverride;
import com.mclegoman.luminance.client.shaders.overrides.UniformOverride;
import net.minecraft.client.gl.*;
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
import java.util.Objects;

@Mixin(priority = 100, value = PostEffectPass.class)
public abstract class PostEffectPassMixin implements PostEffectPassInterface {
	@Shadow @Final private String id;

	@Shadow @Final private ShaderProgram program;

	@Shadow @Final private List<PostEffectPipeline.Uniform> uniforms;

	@Unique private final Map<String, UniformOverride> uniformOverrides = new HashMap<String, UniformOverride>();

	@Inject(method = "method_62257", at = @At("HEAD"))
	private void luminance$beforeRender(Handle<Framebuffer> handle, Map<Identifier, Handle<Framebuffer>> map, Matrix4f matrix4f, CallbackInfo ci) {
		Events.BeforeShaderRender.registry.forEach(((id, runnable) -> runnable.run((PostEffectPass)(Object)this)));
	}
	@Inject(method = "method_62257", at = @At("TAIL"))
	private void luminance$afterRender(Handle<Framebuffer> handle, Map<Identifier, Handle<Framebuffer>> map, Matrix4f matrix4f, CallbackInfo ci) {
		Events.AfterShaderRender.registry.forEach(((id, runnable) -> runnable.run((PostEffectPass)(Object)this)));
	}

	@Inject(method = "<init>", at = @At("TAIL"))
	private void initialiseUniformOverrides(String id, ShaderProgram program, Identifier outputTargetId, List<PostEffectPipeline.Uniform> uniforms, CallbackInfo ci) {
		for (PostEffectPipeline.Uniform uniform : uniforms) {
			((PipelineUniformInterface)(Object)uniform).luminance$getOverride().ifPresent((override) -> uniformOverrides.put(uniform.name(), new LuminanceUniformOverride(override)));
		}
	}

	@Inject(method = "method_62257", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gl/Framebuffer;setClearColor(FFFF)V"))
	private void setUniformOverrides(Handle<Framebuffer> handle, Map<Identifier, Handle<Framebuffer>> map, Matrix4f matrix4f, CallbackInfo ci) {
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

			glUniform.set(values, values.size());
		});
	}

	@Override
	public String luminance$getID() {
		return id;
	}

	@Override
	public ShaderProgram luminance$getProgram() {
		return program;
	}

	@Override
	public List<PostEffectPipeline.Uniform> luminance$getUniforms() {
		return uniforms;
	}

	@Override
	public UniformOverride luminance$getUniformOverride(String uniform) {
		return uniformOverrides.get(uniform);
	}

	@Override
	public UniformOverride luminance$addUniformOverride(String uniform, UniformOverride override) {
		return uniformOverrides.put(uniform, override);
	}

	@Override
	public UniformOverride luminance$removeUniformOverride(String uniform) {
		// removing a uniformOverride for a uniform which is only defined in the shader program and not also the pass causes the value to be left as it was
		// to fix this the uniform is just forcefully reset
		resetUniform(uniform);

		return uniformOverrides.remove(uniform);
	}

	@Unique
	private void resetUniform(String uniformName) {
		// NOTE: this sets it to the value in the shaderprogram, not the posteffectpass
		// this should never cause an issue, since the posteffectpass uniforms are set halfway through method_62257, and used moments later, so the window of time where it can cause a desync is like 15 lines of code

		GlUniform glUniform = program.getUniform(uniformName);
		if (glUniform == null) return;

		List<Float> values = Objects.requireNonNull(program.getUniformDefinition(uniformName)).values();
		glUniform.set(values, values.size());
	}
}