package com.mclegoman.luminance.mixin.client.shaders;

import com.mclegoman.luminance.client.shaders.ShaderProgramInterface;
import net.minecraft.client.gl.GlUniform;
import net.minecraft.client.gl.ShaderProgram;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import java.util.ArrayList;
import java.util.List;

@Mixin(ShaderProgram.class)
public class ShaderProgramMixin implements ShaderProgramInterface {
    @Shadow
    private List<GlUniform> uniforms = null;

    @Unique
    private List<String> uniformNames = null;

    @Override public List<String> luminance$getUniformNames() {
        if (uniformNames == null) {
            uniformNames = new ArrayList<>();
            for (GlUniform glUniform : uniforms) {
                uniformNames.add(glUniform.getName());
            }
        }
        return uniformNames;
    }
}
