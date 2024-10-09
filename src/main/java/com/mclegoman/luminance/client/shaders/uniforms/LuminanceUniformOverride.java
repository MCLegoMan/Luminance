package com.mclegoman.luminance.client.shaders.uniforms;

import com.mclegoman.luminance.client.data.ClientData;
import com.mclegoman.luminance.client.events.Events;

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



    public interface OverrideSource {
        Float get();

        String getString();
    }

    public static class FixedValueSource implements OverrideSource {
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

    public static class UniformSource implements OverrideSource {
        protected final String name;

        protected Uniform uniform = null;
        protected int type = 0;

        protected static final String[] types = new String[] {"", "_prev", "_delta", "_smooth", "_smoothPrev", "_smoothDelta"};

        public UniformSource(String name) {
            this.name = name;
        }

        @Override
        public Float get() {
            if (uniform == null) {
                Events.ShaderUniform.registry.forEach((id, uniform) -> {
                    String s = id.toUnderscoreSeparatedString();
                    if (s.startsWith(name)) {
                        for (int i = 0; i < types.length; i++) {
                            String type = types[i];
                            if (s.equals(name+type)) {
                                this.uniform = uniform;
                                this.type = i;
                            }
                        }
                    }
                });
                if (uniform == null) {
                    return null;
                }
            }

            return switch (type) {
                default -> null;
                case 0 -> uniform.get();
                case 1 -> uniform.getPrev();
                case 2 -> uniform.getDelta();
                case 3 -> uniform.getSmooth(ClientData.minecraft.getRenderTickCounter().getTickDelta(true));
                case 4 -> uniform.getSmoothPrev();
                case 5 -> uniform.getSmoothDelta();
            };
        }

        @Override
        public String getString() {
            return name;
        }

        public boolean isValid() {
            return uniform != null;
        }
    }
}
