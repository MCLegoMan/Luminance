package com.mclegoman.luminance.mixin.client.shaders;

import net.minecraft.client.gl.PostEffectPass;
import net.minecraft.client.gl.PostEffectPipeline;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(PostEffectPass.class)
public interface PostEffectPassAccessor {
    @Accessor("id")
    String getID();

    @Accessor("uniforms")
    List<PostEffectPipeline.Uniform> getUniforms();
}
