package com.mclegoman.luminance.client.shaders;

import com.mclegoman.luminance.client.shaders.uniforms.UniformOverride;

import java.util.Map;

public interface PostEffectPassInterface {
    Map<String, UniformOverride> luminance$getUniformOverrides();
}
