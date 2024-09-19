// This class is a temporary fix for vanilla time.

package com.mclegoman.luminance.client.temp;

import com.mclegoman.luminance.client.events.Events;
import com.mclegoman.luminance.client.shaders.uniforms.LuminanceUniform;
import net.minecraft.util.Identifier;

public class ShaderTime {
	public static float prev = 0.0F;
	public static float time = 0.0F;
	public static void init() {
		Events.ShaderUniform.register(Identifier.of("luFix", "time"), new LuminanceUniform((tickDelta) -> getTime(tickDelta) / 20.0F));
	}
	public static float getTime(float tickDelta) {
		if (tickDelta < prev) {
			time += 1.0F - prev;
			time += tickDelta;
		} else time += tickDelta - prev;
		prev = tickDelta;
		while (time > 20.0F) time = 0.0F;
		return time;
	}
}
