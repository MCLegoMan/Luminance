/*
    Luminance
    Contributor(s): MCLegoMan
    Github: https://github.com/MCLegoMan/Luminance
    Licence: GNU LGPLv3
*/

package com.mclegoman.luminance.client.shaders;

import com.google.gson.JsonObject;
import com.mclegoman.luminance.common.util.IdentifierHelper;
import net.minecraft.util.Identifier;

public class ShaderRegistry {
	private final String id;
	private final boolean translatable;
	private final boolean disableGameRendertype;
	private final JsonObject custom;
	private ShaderRegistry(String namespace, String key, boolean translatable, boolean disableGameRendertype, JsonObject custom) {
		this.id = namespace + ":" + key;
		this.translatable = translatable;
		this.disableGameRendertype = disableGameRendertype;
		this.custom = custom;
	}
	public static Builder builder(String namespace, String path) {
		return new Builder(namespace, path);
	}
	public static class Builder {
		private final String namespace;
		private final String path;
		private boolean translatable;
		private boolean disableGameRendertype;
		private JsonObject custom;
		public Builder(String namespace, String path) {
			this.namespace = namespace;
			this.path = path;
			this.translatable = false;
			this.disableGameRendertype = false;
			this.custom = new JsonObject();
		}
		public Builder translatable(boolean translatable) {
			this.translatable = translatable;
			return this;
		}
		public Builder disableGameRendertype(boolean disableGameRendertype) {
			this.disableGameRendertype = disableGameRendertype;
			return this;
		}
		public Builder custom(JsonObject custom) {
			this.custom = custom;
			return this;
		}
		public ShaderRegistry build() {
			return new ShaderRegistry(this.namespace, this.path, this.translatable, this.disableGameRendertype, this.custom);
		}
	}
	public String getNamespace() {
		return IdentifierHelper.getStringPart(IdentifierHelper.Type.NAMESPACE, this.id);
	}
	public String getKey() {
		return IdentifierHelper.getStringPart(IdentifierHelper.Type.KEY, this.id);
	}
	public Identifier getPostEffect() {
		return Shaders.getPostShader(IdentifierHelper.identifierFromString(this.id));
	}
	public boolean getTranslatable() {
		return this.translatable;
	}
	public boolean getDisableGameRendertype() {
		return this.disableGameRendertype;
	}
	public JsonObject getCustom() {
		return this.custom;
	}
}
