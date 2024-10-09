package com.mclegoman.luminance.client.shaders.overrides;

public class FixedValueSource implements OverrideSource {
    public float value;

    public FixedValueSource(float value) {
        this.value = value;
    }

    @Override
    public Float get() {
        return value;
    }

    @Override
    public String getString() {
        return Float.toString(value);
    }
}
