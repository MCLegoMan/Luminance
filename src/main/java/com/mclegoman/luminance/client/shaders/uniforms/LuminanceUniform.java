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
import net.minecraft.util.math.MathHelper;

public class LuminanceUniform implements Uniform {
	private final Callables.ShaderRender<Float> callable;
	private float prev;
	private float current;
	private float prevSmooth;
	private float smooth;
	public LuminanceUniform(Callables.ShaderRender<Float> callable) {
		this.callable = callable;
	}
	public float get() {
		return this.current;
	}
	public float getPrev() {
		return this.prev;
	}
	public float getDelta() {
		return this.current - this.prev;
	}
	public float getSmooth(float delta) {
		return this.smooth;
	}
	public float getSmoothPrev() {
		return this.prevSmooth;
	}
	public float getSmoothDelta() {
		return this.smooth - this.prevSmooth;
	}
	public void tick(float delta) {
		try {
			this.prevSmooth = this.smooth;
			this.smooth = (this.prevSmooth + this.current) * 0.5F;
		} catch (Exception error) {
			Data.version.sendToLog(LogType.ERROR, error.getLocalizedMessage());
		}
	}
	public void call(float delta) {
		try {
			this.prev = this.current;
			this.current = this.callable.call(delta);
			this.smooth = MathHelper.lerp(delta, this.prevSmooth, this.current);
		} catch (Exception error) {
			Data.version.sendToLog(LogType.ERROR, error.getLocalizedMessage());
		}
	}
}
