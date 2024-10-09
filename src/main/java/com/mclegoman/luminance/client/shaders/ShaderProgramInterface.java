package com.mclegoman.luminance.client.shaders;

import java.util.List;

public interface ShaderProgramInterface {
    List<String> luminance$getUniformNames();

    List<Float> luminance$getCurrentUniformValues(String uniform);
}
