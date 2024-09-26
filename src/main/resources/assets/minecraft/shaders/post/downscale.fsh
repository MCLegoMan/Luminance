#version 150

uniform sampler2D InSampler;

in vec2 texCoord;
in vec2 oneTexel;

out vec4 fragColor;

void main() {
    fragColor = vec4(( texture(InSampler, texCoord).rgb + texture(InSampler, texCoord + vec2(oneTexel.x, 0.0)).rgb + texture(InSampler, texCoord + vec2(0.0, oneTexel.y)).rgb + texture(InSampler, texCoord + oneTexel).rgb) * 0.25, 1.0);
}
