package com.mclegoman.luminance.mixin.client.shaders;

import net.minecraft.client.gl.GlUniform;
import net.minecraft.client.gl.ShaderProgram;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(ShaderProgram.class)
public interface ShaderProgramAccessor {
    @Accessor("uniforms")
    List<GlUniform> getUniforms();
}
