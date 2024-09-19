#version 150

uniform sampler2D InSampler;

in vec2 texCoord;
in vec2 oneTexel;

out vec4 fragColor;

void main(){
    vec4 u  = texture(InSampler, texCoord + vec2(        0.0, -oneTexel.y));
    vec4 d  = texture(InSampler, texCoord + vec2(        0.0,  oneTexel.y));
    vec4 l  = texture(InSampler, texCoord + vec2(-oneTexel.x,         0.0));
    vec4 r  = texture(InSampler, texCoord + vec2( oneTexel.x,         0.0));

    vec4 v1 = min(l, r);
    vec4 v2 = min(u, d);
    vec4 v3 = min(v1, v2);

    vec4 ul = texture(InSampler, texCoord + vec2(-oneTexel.x, -oneTexel.y));
    vec4 dr = texture(InSampler, texCoord + vec2( oneTexel.x,  oneTexel.y));
    vec4 dl = texture(InSampler, texCoord + vec2(-oneTexel.x,  oneTexel.y));
    vec4 ur = texture(InSampler, texCoord + vec2( oneTexel.x, -oneTexel.y));

    vec4 v4 = min(ul, dr);
    vec4 v5 = min(ur, dl);
    vec4 v6 = min(v4, v5);

    vec4 v7 = min(v3, v6);

    vec4 uu = texture(InSampler, texCoord + vec2(              0.0, -oneTexel.y * 2.0));
    vec4 dd = texture(InSampler, texCoord + vec2(              0.0,  oneTexel.y * 2.0));
    vec4 ll = texture(InSampler, texCoord + vec2(-oneTexel.x * 2.0,               0.0));
    vec4 rr = texture(InSampler, texCoord + vec2( oneTexel.x * 2.0,               0.0));

    vec4 v8 = min(uu, dd);
    vec4 v9 = min(ll, rr);
    vec4 v10 = min(v8, v9);

    vec4 v11 = min(v7, v10);

    vec4 c  = texture(InSampler, texCoord);
    vec4 color = min(c, v11);
    fragColor = vec4(color.rgb, 1.0);
}
