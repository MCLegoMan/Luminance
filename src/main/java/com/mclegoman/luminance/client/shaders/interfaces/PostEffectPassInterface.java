package com.mclegoman.luminance.client.shaders.interfaces;

import com.mclegoman.luminance.client.shaders.overrides.UniformOverride;
import net.minecraft.client.gl.PostEffectPipeline;
import net.minecraft.client.gl.ShaderProgram;

import java.util.List;
import java.util.Map;

public interface PostEffectPassInterface {
    String luminance$getID();

    ShaderProgram luminance$getProgram();

    List<PostEffectPipeline.Uniform> luminance$getUniforms();

    Map<String, UniformOverride> luminance$getUniformOverrides();
}
