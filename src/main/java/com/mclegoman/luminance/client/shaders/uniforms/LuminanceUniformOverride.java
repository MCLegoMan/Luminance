package com.mclegoman.luminance.client.shaders.uniforms;

import java.util.ArrayList;
import java.util.List;

public class LuminanceUniformOverride implements UniformOverride {
    protected final List<Float> overrideTemp;

    public LuminanceUniformOverride(List<String> overrideStrings) {
        //TODO: use dynamic uniforms when applicable
        overrideTemp = new ArrayList<>(overrideStrings.size());

        for (String string : overrideStrings) {
            overrideTemp.add(Float.parseFloat(string));
        }
    }

    @Override
    public List<Float> getOverride() {
        return overrideTemp;
    }
}
