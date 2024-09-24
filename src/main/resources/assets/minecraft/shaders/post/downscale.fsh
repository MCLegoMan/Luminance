#version 150

uniform sampler2D InSampler;

in vec2 texCoord;
in vec2 oneTexel;

uniform vec2 InSize;

out vec4 fragColor;

void main() {
    vec3 Texel0 = texture(InSampler, texCoord).rgb;
    vec3 Texel1 = texture(InSampler, texCoord + vec2(oneTexel.x, 0.0)).rgb;
    vec3 Texel2 = texture(InSampler, texCoord + vec2(0.0, oneTexel.y)).rgb;
    vec3 Texel3 = texture(InSampler, texCoord + oneTexel).rgb;

    fragColor = vec4((Texel0 + Texel1 + Texel2 + Texel3) * 0.25, 1.0);
}
