/*
    Luminance
    Contributor(s): MCLegoMan
    Github: https://github.com/MCLegoMan/Luminance
    Licence: GNU LGPLv3
*/

package com.mclegoman.luminance.client.shaders.uniforms;

import com.mclegoman.luminance.client.events.Callables;
import com.mclegoman.luminance.common.data.Data;
import com.mclegoman.luminance.common.util.LogType;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class LuminanceUniform implements Uniform {
	private final Identifier identifier;
	private final Callables.ShaderRender<Float> callable;
	private float previous;
	private float current;
	private float smooth;
	public LuminanceUniform(Identifier identifier, Callables.ShaderRender<Float> callable) {
		this.identifier = identifier;
		this.callable = callable;
	}
	public Identifier getId() {
		return this.identifier;
	}
	public float get() {
		return this.current;
	}
	public float getPrev() {
		return this.previous;
	}
	public float getDelta() {
		return this.current - this.previous;
	}
	public float getSmooth() {
		return this.smooth;
	}
	public void update(float delta) {
		try {
			this.previous = this.current;
			this.current = this.callable.call(delta);
			this.smooth = MathHelper.lerp(delta, this.previous, this.current);
		} catch (Exception error) {
			Data.version.sendToLog(LogType.ERROR, error.getLocalizedMessage());
		}
	}
}
