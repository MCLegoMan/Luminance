package com.mclegoman.luminance.mixin.client.shaders;

import com.mclegoman.luminance.client.shaders.ShaderProgramInterface;
import net.minecraft.client.gl.GlUniform;
import net.minecraft.client.gl.ShaderProgram;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import java.util.ArrayList;
import java.util.List;

@Mixin(ShaderProgram.class)
public class ShaderProgramMixin implements ShaderProgramInterface {
    @Final
    @Shadow
    private List<GlUniform> uniforms;

    @Unique
    private List<String> uniformNames;

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
