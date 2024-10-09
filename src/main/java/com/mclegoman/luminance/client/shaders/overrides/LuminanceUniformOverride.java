package com.mclegoman.luminance.client.shaders.overrides;

import java.util.ArrayList;
import java.util.List;

public class LuminanceUniformOverride implements UniformOverride {
    public final List<OverrideSource> overrideSources;

    protected final List<Float> values;

    public LuminanceUniformOverride(List<String> overrideStrings) {
        values = new ArrayList<>(overrideStrings.size());
        overrideSources = new ArrayList<>(overrideStrings.size());

        for (String string : overrideStrings) {
            values.add(null);
            overrideSources.add(sourceFromString(string));
        }
    }

    @Override
    public List<Float> getOverride() {
        updateValues();
        return values;
    }

    protected void updateValues() {
        for (int i = 0; i < values.size(); i++) {
            OverrideSource overrideSource = overrideSources.get(i);
            values.set(i, overrideSource != null ? overrideSource.get() : null);
        }
    }

    public List<String> getStrings() {
        List<String> strings = new ArrayList<>(overrideSources.size());
        for (OverrideSource source : overrideSources) {
            strings.add(source != null ? source.getString() : null);
        }
        return strings;
    }

    public static OverrideSource sourceFromString(String string) {
        try {
            float value = Float.parseFloat(string);
            return new FixedValueSource(value);
        } catch (Exception ignored) {
            return new UniformSource(string);
        }
    }
}
