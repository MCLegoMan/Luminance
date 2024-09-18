/*
    Luminance
    Contributor(s): MCLegoMan
    Github: https://github.com/MCLegoMan/Luminance
    Licence: GNU LGPLv3
*/

package com.mclegoman.luminance.client.shaders.uniforms;

import net.minecraft.util.Identifier;

public interface Uniform {
	Identifier getId();
	float get();
	float getPrev();
	float getDelta();
	float getSmooth();
	void update(float tickDelta);
}
